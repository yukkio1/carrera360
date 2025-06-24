package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.apiregistroModulo.RetrofitClient
import com.example.inlab.databinding.FragmentModule7Binding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.inlab.viewmodel.UsuarioViewModel
class Module7Fragment : Fragment(R.layout.fragment_module7) {
    private var _binding: FragmentModule7Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentModule7Binding.bind(view)

        // 👇 Llamada a la API: Registro del módulo 2
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: 0
        val idModulo = 13 // Módulo correspondiente a habilidades

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

        setupNavigation()
        setupButtons()
    }

    private fun setupNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        findNavController().navigate(Module7FragmentDirections.actionModule7ToHome())
                        true
                    }
                    R.id.learn_fragment -> {
                        findNavController().navigate(Module7FragmentDirections.actionModule7ToLearn())
                        true
                    }
                    R.id.profile_fragment -> {
                        findNavController().navigate(Module7FragmentDirections.actionModule7ToProfile())
                        true
                    }
                    else -> false
                }
            }
    }

    private fun setupButtons() {
        binding.btnStartEvaluation7.setOnClickListener {
            findNavController().navigate(Module7FragmentDirections.actionModule7ToEvaluation7())
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(Module7FragmentDirections.actionModule7ToHome())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
