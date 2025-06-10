package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentModule1Binding
import com.google.android.material.bottomnavigation.BottomNavigationView

class Module1Fragment : Fragment(R.layout.fragment_module1) {
    private var _binding: FragmentModule1Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentModule1Binding.bind(view)

        setupButtons()
        setupBottomNavigation()
    }

    private fun setupButtons() {
        binding.btnRespondEvaluation.setOnClickListener {
            val finalResult = "Resultado inicial" // Ajusta esto según tu lógica
            val action = Module1FragmentDirections.actionModule1ToEvaluation(finalResult)
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            val action = Module1FragmentDirections.actionModule1ToLearn()
            findNavController().navigate(action)
        }
    }

    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = Module1FragmentDirections.actionModule1ToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = Module1FragmentDirections.actionModule1ToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = Module1FragmentDirections.actionModule1ToProfile()
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
