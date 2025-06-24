package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentResult4Binding
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentResult4 : Fragment(R.layout.fragment_result4) {
    private var _binding: FragmentResult4Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResult4Binding.bind(view)

        setupNavigation()
        setupButtons()
        showEvaluationResult()
    }

    private fun setupNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        findNavController().navigate(FragmentResult4Directions.actionResult4ToHome())
                        true
                    }
                    R.id.learn_fragment -> {
                        findNavController().navigate(FragmentResult4Directions.actionResult4ToLearn())
                        true
                    }
                    R.id.profile_fragment -> {
                        findNavController().navigate(FragmentResult4Directions.actionResult4ToProfile())
                        true
                    }
                    else -> false
                }
            }
    }

    private fun setupButtons() {
        binding.btnBackToModule4.setOnClickListener {
            findNavController().navigate(FragmentResult4Directions.actionResult4ToModule4())
        }
    }

    private fun showEvaluationResult() {
        val selectedOption = arguments?.getString("selectedOption") ?: "No seleccionaste ninguna opción"
        binding.resultTextView.text = "Tu elección: $selectedOption\n\nAnaliza si esta estrategia te permite gestionar eficazmente los riesgos profesionales."
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
