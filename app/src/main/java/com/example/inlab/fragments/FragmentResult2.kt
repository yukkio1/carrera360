package com.example.inlab.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentResult2Binding

class FragmentResult2 : Fragment(R.layout.fragment_result2) {
    private var _binding: FragmentResult2Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResult2Binding.bind(view)

        setupButtons()
        showEvaluationResult()
    }

    private fun setupButtons() {
        binding.btnReturnToModule2.setOnClickListener {
            findNavController().navigate(FragmentResult2Directions.actionResult2ToModule2())
        }
    }

    private fun showEvaluationResult() {
        val selectedOption = arguments?.getString("selectedOption") ?: "No seleccionaste ninguna opción"

        // Mostrar las respuestas seleccionadas
        binding.resultTextView.text = "Tus respuestas:\n\n$selectedOption"

        // Mostrar retroalimentación psicológica basada en las respuestas
        when (selectedOption) {
            "Comunicación efectiva\nAlto nivel\nProyectos prácticos" -> {
                binding.reflectionText.text = getString(R.string.reflection_communication_high_level_practical_projects)
            }
            "Pensamiento crítico\nNivel intermedio\nClases teóricas" -> {
                binding.reflectionText.text = getString(R.string.reflection_critical_thinking_intermediate_theoretical_classes)
            }
            "Trabajo en equipo\nNecesito mejorar\nAprender solo sin orientación" -> {
                binding.reflectionText.text = getString(R.string.reflection_teamwork_improve_self_learning)
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