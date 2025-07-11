package com.example.inlab.fragments
import com.example.inlab.apiperfil.PerfilDTO
import com.example.inlab.apiperfil.RetrofitClient
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inlab.R
import com.example.inlab.databinding.FragmentProfileBinding
import com.example.inlab.viewmodel.UsuarioViewModel
import com.example.inlab.apimodulofaltante.ModuloFaltanteDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.temporal.ChronoUnit
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.inlab.adapters.ModuloFaltanteAdapter
import com.example.inlab.apiresiduo.adapters.ResiduoAdapter
import com.example.inlab.apiresiduo.model.ResiduoResponse
import com.example.inlab.apiresiduo.api.RetrofitClient3
import com.example.inlab.apiresiduo.api.ResiduoApi
import com.example.inlab.apiresiduo.model.ResiduoDTO

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    // Guardamos 'perfil' aquí para que sea accesible en todos los métodos
    private lateinit var perfil: PerfilDTO

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: return
        binding.usernameTextView.text = usuarioViewModel.nombre.value ?: "Nombre del usuario"

        // Cargar perfil y residuos
        fetchAndDisplayPerfil(idUsuario)
        fetchAndDisplayResiduo(idUsuario)
        // Calcular días transcurridos
        val fechaCreacion = usuarioViewModel.fechaCreacion.value
        if (!fechaCreacion.isNullOrEmpty()) {
            val diasTranscurridos = calcularDiasDesdeCreacion(fechaCreacion)
            binding.daysSinceStartedTextView.text = diasTranscurridos.toString()
        } else {
            binding.daysSinceStartedTextView.text = "N/A"
        }
            // Configurar el botón "CERRAR SESIÓN"
        binding.logoutButton.setOnClickListener {
            // Cerrar la aplicación al presionar "CERRAR SESIÓN"
            requireActivity().finishAffinity()
        }
        setupBottomNavigation()
        fetchAndDisplayModulosFaltantes()
        // Configurar RecyclerView
        setupRecyclerView()
        setupButtons()


    }
    private fun setupButtons() {

        binding.logoutButton2.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileToCreditos()
            findNavController().navigate(action)
        }
    }
    private fun calcularDiasDesdeCreacion(fechaCreacionStr: String): Long {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME // Formato ISO 8601 con zona horaria
        val fechaCreacion = LocalDateTime.parse(fechaCreacionStr, formatter)
        val hoy = LocalDateTime.now()
        return ChronoUnit.DAYS.between(fechaCreacion, hoy).coerceAtLeast(0)
    }

    private fun setupBottomNavigation() {
        // ✅ No se modifica esta parte
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        findNavController().navigate(ProfileFragmentDirections.actionProfileToHome())
                        true
                    }
                    R.id.learn_fragment -> {
                        findNavController().navigate(ProfileFragmentDirections.actionProfileToLearn())
                        true
                    }
                    R.id.profile_fragment -> {
                        findNavController().navigate(ProfileFragmentDirections.actionProfileSelf())
                        true
                    }
                    else -> false
                }
            }
    }

    private fun fetchAndDisplayModulosFaltantes() {
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: return

        RetrofitClient.moduloFaltanteApi.getModulosFaltantes(idUsuario)
            .enqueue(object : Callback<List<ModuloFaltanteDTO>> {
                override fun onResponse(
                    call: Call<List<ModuloFaltanteDTO>>,
                    response: Response<List<ModuloFaltanteDTO>>
                ) {
                    if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                        val modulosFaltantes = response.body()!!

                        activity?.runOnUiThread {
                            with(binding) {
                                modulesRecyclerView.apply {
                                    layoutManager = LinearLayoutManager(context)
                                    adapter = ModuloFaltanteAdapter(modulosFaltantes)
                                }
                                emptyModulesText.visibility = View.GONE
                            }
                        }
                    } else {
                        Log.e("API", "No hay módulos faltantes")
                        activity?.runOnUiThread {
                            binding.modulesRecyclerView.adapter = null
                            binding.emptyModulesText.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onFailure(call: Call<List<ModuloFaltanteDTO>>, t: Throwable) {
                    Log.e("API", "Error al llamar a la API: ${t.message}")
                }
            })
    }

    private fun fetchAndDisplayPerfil(idUsuario: Int) {
        RetrofitClient.perfilApi.getPerfil(idUsuario)
            .enqueue(object : Callback<PerfilDTO> {
                override fun onResponse(call: Call<PerfilDTO>, response: Response<PerfilDTO>) {
                    if (response.isSuccessful && response.body() != null) {
                        perfil = response.body()!!

                        activity?.runOnUiThread {
                            with(binding) {
                                completedEvaluationsTextView.text =
                                    "${perfil.cantidadEvaluacionesCompletadas}/${perfil.cantidadEvaluaciones}"
                                cantidadmodulosleidos.text =
                                    "${perfil.cantidadModulosLeidos}/${perfil.cantidadModulos}"

                                // Llamar a fetchAndDisplayResiduo después de que 'perfil' esté listo
                                fetchAndDisplayResiduo(idUsuario)
                            }
                        }
                    } else {
                        Log.e("API", "Error al obtener el perfil")
                    }
                }

                override fun onFailure(call: Call<PerfilDTO>, t: Throwable) {
                    Log.e("API", "Error al llamar a la API: ${t.message}")
                }
            })
    }

    private fun fetchAndDisplayResiduo(idUsuario: Int) {
        RetrofitClient3.residuoApi.getResiduos(idUsuario).enqueue(object : Callback<ResiduoResponse> {
            override fun onResponse(
                call: Call<ResiduoResponse>,
                response: Response<ResiduoResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val residuoResponse = response.body()!!
                    val adapter = binding.recyclerViewResiduos.adapter as ResiduoAdapter
                    adapter.updateData(residuoResponse)

                    // Mostrar cantidad de logros
                    if (::perfil.isInitialized) {
                        binding.achievementsTextView.text = "${residuoResponse.cantidadHecho}/${perfil.cantidadLogros}"
                    } else {
                        binding.achievementsTextView.text = "${residuoResponse.cantidadHecho}/?"
                    }
                } else {
                    Log.e("ProfileFragment", "Error al obtener los residuos")
                }
            }

            override fun onFailure(call: Call<ResiduoResponse>, t: Throwable) {
                Log.e("ProfileFragment", "Fallo al llamar a la API: ${t.message}")
            }
        })
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerViewResiduos
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ResiduoAdapter(ResiduoResponse(cantidadHecho = 0, nombreLogro = emptyList()))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}