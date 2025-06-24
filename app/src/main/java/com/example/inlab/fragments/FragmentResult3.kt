package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentResult3Binding

class FragmentResult3 : Fragment(R.layout.fragment_result3) {
    private var _binding: FragmentResult3Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResult3Binding.bind(view)

        setupButtons()
        showEvaluationResult()
    }

    private fun setupButtons() {
        binding.btnBackToModule3.setOnClickListener {
            findNavController().navigate(FragmentResult3Directions.actionResult3ToModule3())
        }
    }

    private fun showEvaluationResult() {
        val selectedOption = arguments?.getString("selectedOption") ?: "No seleccionaste ninguna opción"

        binding.resultTextView.text = "Tu elección: $selectedOption\n\nReflexiona si esta opción te acerca a tus objetivos profesionales."
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
