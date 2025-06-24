package com.example.inlab.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.apiregistroModulo.RetrofitClient
import com.example.inlab.databinding.FragmentModule5Binding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.inlab.viewmodel.UsuarioViewModel
class Module5Fragment : Fragment(R.layout.fragment_module5) {
    private var _binding: FragmentModule5Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentModule5Binding.bind(view)

        Log.d("Module5", "Fragmento Module5Fragment iniciado correctamente.")

        // 👇 Llamada a la API: Registro del módulo 2
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: 0
        val idModulo = 11 // Módulo correspondiente a habilidades

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
                        findNavController().navigate(Module5FragmentDirections.actionModule5ToHome())
                        true
                    }
                    R.id.learn_fragment -> {
                        findNavController().navigate(Module5FragmentDirections.actionModule5ToLearn())
                        true
                    }
                    R.id.profile_fragment -> {
                        findNavController().navigate(Module5FragmentDirections.actionModule5ToProfile())
                        true
                    }
                    else -> false
                }
            }
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(Module5FragmentDirections.actionModule5ToHome())
        }

        binding.btnResponderModule5.setOnClickListener {
            findNavController().navigate(Module5FragmentDirections.actionModule5ToEvaluation5())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
