package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentMarketingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.inlab.apiregistroModulo.RetrofitClient
import com.example.inlab.apiregistroModulo.RegistroModuloResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import com.example.inlab.viewmodel.UsuarioViewModel
class MarketingFragment : Fragment(R.layout.fragment_marketing) {
    private var _binding: FragmentMarketingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMarketingBinding.bind(view)

        // 👇 Llamada a la API (una sola vez al entrar al fragmento)
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: 0
        val idModulo = 1 // ID único del módulo que se está visualizando

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.registroModuloApi.registrarModulo(idUsuario, idModulo)

                if (response.isSuccessful && response.body() != null) {
                    val resultado = response.body()
                    println("API Response: ${resultado?.mensaje}")
                } else {
                    println("Error en la API: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error al llamar a la API: ${e.message}")
            }
        }

        setupBottomNavigation()
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            val action = MarketingFragmentDirections.actionMarketingToHome()
            findNavController().navigate(action)
        }
    }

    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = MarketingFragmentDirections.actionMarketingToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = MarketingFragmentDirections.actionMarketingToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = MarketingFragmentDirections.actionMarketingToProfile()
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