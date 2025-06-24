package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentRealidadBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.inlab.apiregistroModulo.RetrofitClient
import com.example.inlab.apiregistroModulo.RegistroModuloResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.inlab.viewmodel.UsuarioViewModel
class RealidadFragment : Fragment(R.layout.fragment_realidad) {
    private var _binding: FragmentRealidadBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRealidadBinding.bind(view)

        // 👇 Llamada a la API: Registro del módulo 2
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: 0
        val idModulo = 4 // Módulo correspondiente a habilidades

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.registroModuloApi.registrarModulo(idUsuario, idModulo)

                if (response.isSuccessful && response.body() != null) {
                    val resultado = response.body()
                    println("Registro Módulo $idModulo: ${resultado?.mensaje}")
                } else {
                    println("Error en registro de módulo $idModulo: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error de red o servidor: ${e.message}")
            }
        }

        setupBottomNavigation()
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            val action = RealidadFragmentDirections.actionRealidadToHome()
            findNavController().navigate(action)
        }
    }

    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = RealidadFragmentDirections.actionRealidadToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = RealidadFragmentDirections.actionRealidadToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = RealidadFragmentDirections.actionRealidadToProfile()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
