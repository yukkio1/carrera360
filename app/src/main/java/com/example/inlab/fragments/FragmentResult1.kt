package com.example.inlab.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentResult1Binding

class FragmentResult1 : Fragment(R.layout.fragment_result1) {
    private var _binding: FragmentResult1Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResult1Binding.bind(view)

        setupButtons()
        showEvaluationResult()
    }

    private fun setupButtons() {
        binding.btnReturnToModule1.setOnClickListener {
            findNavController().navigate(FragmentResult1Directions.actionResult1ToModule1())
        }
    }

    private fun showEvaluationResult() {
        val selectedOption = arguments?.getString("selectedOption") ?: return

        // Mostrar las respuestas seleccionadas
        binding.resultTextView.text = "Tus respuestas:\n\n$selectedOption"

        // Dividir las tres respuestas por salto de línea
        val options = selectedOption.split("\n").map { it.trim() }

        if (options.size != 3) {
            // Si no hay 3 respuestas, mostrar mensaje por defecto
            binding.reflectionText.text = getString(R.string.default_reflection_message_module1)
            return
        }

        val option1 = options[0]
        val option2 = options[1]
        val option3 = options[2]

        // Mostrar retroalimentación psicológica basada en las respuestas
        when {
            option1 == "A) Currículum y LinkedIn" &&
                    option2 == "A) Completa y profesional" &&
                    option3 == "A) Estrategia planificada" -> {
                binding.reflectionText.text = getString(R.string.reflection_option_A_A_A_module1)
            }

            option1 == "B) Redes sociales profesionales" &&
                    option2 == "B) Básica y en desarrollo" &&
                    option3 == "B) Contactos y relaciones" -> {
                binding.reflectionText.text = getString(R.string.reflection_option_B_B_B_module1)
            }

            option1 == "C) Publicaciones y proyectos personales" &&
                    option2 == "C) Sin presencia activa" &&
                    option3 == "C) Proyectos personales y visibilidad" -> {
                binding.reflectionText.text = getString(R.string.reflection_option_C_C_C_module1)
            }

            // Combinaciones mixtas (ejemplo A-B-C, C-B-A, etc.)
            option1.contains("Currículum") && option2.contains("Básica") && option3.contains("Contactos") -> {
                binding.reflectionText.text = getString(R.string.reflection_option_B_B_B_module1)
            }

            option1.contains("Publicaciones") && option2.contains("Sin") && option3.contains("Proyectos") -> {
                binding.reflectionText.text = getString(R.string.reflection_option_C_C_C_module1)
            }

            option1.contains("Currículum") && option2.contains("Completa") && option3.contains("Estrategia") -> {
                binding.reflectionText.text = getString(R.string.reflection_option_A_A_A_module1)
            }

            else -> {
                binding.reflectionText.text = getString(R.string.default_reflection_message_module1)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}