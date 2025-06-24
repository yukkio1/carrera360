package com.example.inlab.fragments
import com.example.inlab.viewmodel.UsuarioViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentEvaluation5Binding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.inlab.apievaluaciones.ApiClient
import com.example.inlab.apievaluaciones.EvaluacionRequest
import com.example.inlab.apievaluaciones.MensajeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Evaluation5Fragment : Fragment(R.layout.fragment_evaluation5) {
    private var _binding: FragmentEvaluation5Binding? = null
    private val binding get() = _binding!!
    private val usuarioViewModel: UsuarioViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEvaluation5Binding.bind(view)

        Log.d("Evaluation", "Fragmento Evaluation5Fragment iniciado correctamente.")

        setupNavigation()
        setupButtons()
    }

    private fun setupNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        findNavController().navigate(Evaluation5FragmentDirections.actionEvaluation5ToHome())
                        true
                    }
                    R.id.learn_fragment -> {
                        findNavController().navigate(Evaluation5FragmentDirections.actionEvaluation5ToLearn())
                        true
                    }
                    R.id.profile_fragment -> {
                        findNavController().navigate(Evaluation5FragmentDirections.actionEvaluation5ToProfile())
                        true
                    }
                    else -> false
                }
            }
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(Evaluation5FragmentDirections.actionEvaluation5ToModule5())
        }

        binding.btnSubmitEvaluation5.setOnClickListener {
            evaluarRespuestas()
        }
    }

    private fun evaluarRespuestas() {
        val respuestasSeleccionadas = mutableListOf<String>()

        val radioGroups = listOf(
            binding.radioGroupQuestion1,
            binding.radioGroupQuestion2,
            binding.radioGroupQuestion3
        )

        radioGroups.forEachIndexed { index, group ->
            val selectedOptionId = group.checkedRadioButtonId
            if (selectedOptionId == -1) {
                Log.e("Evaluation", "❌ No se ha seleccionado ninguna opción en la pregunta ${index + 1}.")
                return
            }
            val selectedOption = requireView().findViewById<RadioButton>(selectedOptionId)
            respuestasSeleccionadas.add(selectedOption.text.toString())
        }
        registrarEvaluacionRealizada()
        Log.d("Evaluation", "✅ Respuestas seleccionadas: $respuestasSeleccionadas")

        val action = Evaluation5FragmentDirections.actionEvaluation5ToResult5(respuestasSeleccionadas.toTypedArray())
        findNavController().navigate(action)
    }
    private fun registrarEvaluacionRealizada() {
        val idUsuario = usuarioViewModel.idUsuario.value
        val idEvaluacion = 5

        if (idUsuario != null) {
            val request = EvaluacionRequest(idUsuario, idEvaluacion)

            ApiClient.apiService.registrarEvaluacion(request)
                .enqueue(object : Callback<MensajeResponse> {
                    override fun onResponse(
                        call: Call<MensajeResponse>,
                        response: Response<MensajeResponse>
                    ) {
                        val mensaje = response.body()?.mensaje
                        Toast.makeText(requireContext(), mensaje ?: "Respuesta sin mensaje", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<MensajeResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        } else {
            Toast.makeText(requireContext(), "ID de usuario no disponible", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
