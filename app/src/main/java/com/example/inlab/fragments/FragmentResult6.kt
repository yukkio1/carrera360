package com.example.inlab.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentResult6Binding

class FragmentResult6 : Fragment(R.layout.fragment_result6) {
    private var _binding: FragmentResult6Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResult6Binding.bind(view)

        setupButtons()
        showEvaluationResult()
    }

    private fun setupButtons() {
        binding.btnReturnToModule6.setOnClickListener {
            findNavController().navigate(FragmentResult6Directions.actionResult6ToModule6())
        }
    }

    private fun showEvaluationResult() {
        val selectedOption = arguments?.getString("selectedOption") ?: "No seleccionaste ninguna opción"

        // Mostrar las respuestas seleccionadas
        binding.resultTextView.text = "Tus respuestas:\n\n$selectedOption"

        // Mostrar retroalimentación psicológica basada en las respuestas
        when (selectedOption) {
            "B) Revisando mi currículum y esperando preguntas técnicas.\nB) Moderadamente cómodo, pero necesito mejorar en detalles.\nB) Conversacional y dinámica, con espacio para preguntar también." -> {
                binding.reflectionText.text = getString(R.string.reflection_option_B_B_B)
            }
            "B) Revisando mi currículum y esperando preguntas técnicas.\nB) Moderadamente cómodo, pero necesito mejorar en detalles.\nC) Abierta y flexible, dependiendo de cómo fluya la conversación." -> {
                binding.reflectionText.text = getString(R.string.reflection_option_B_B_C)
            }
            "B) Revisando mi currículum y esperando preguntas técnicas.\nC) Totalmente cómodo, sé comunicar mis fortalezas.\nB) Conversacional y dinámica, con espacio para preguntar también." -> {
                binding.reflectionText.text = getString(R.string.reflection_option_B_C_B)
            }
            "B) Revisando mi currículum y esperando preguntas técnicas.\nC) Totalmente cómodo, sé comunicar mis fortalezas.\nC) Abierta y flexible, dependiendo de cómo fluya la conversación." -> {
                binding.reflectionText.text = getString(R.string.reflection_option_B_C_C)
            }
            else -> {
                binding.reflectionText.text = getString(R.string.default_reflection_message)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}