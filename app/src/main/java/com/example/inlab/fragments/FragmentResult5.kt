package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentResult5Binding
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentResult5 : Fragment(R.layout.fragment_result5) {
    private var _binding: FragmentResult5Binding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResult5Binding.bind(view)

        setupNavigation()
        setupButtons()
        showEvaluationResults()
    }

    private fun setupNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> findNavController().navigate(FragmentResult5Directions.actionResult5ToHome())
                    R.id.learn_fragment -> findNavController().navigate(FragmentResult5Directions.actionResult5ToLearn())
                    R.id.profile_fragment -> findNavController().navigate(FragmentResult5Directions.actionResult5ToProfile())
                    else -> false
                }
                true
            }
    }

    private fun setupButtons() {
        binding.btnBackToModule5.setOnClickListener {
            findNavController().navigate(FragmentResult5Directions.actionResult5ToModule5())
        }
    }

    private fun showEvaluationResults() {
        val selectedOptions = arguments?.getStringArray("selectedOptions") ?: arrayOf("No seleccionaste ninguna opción")

        val formattedText = selectedOptions.joinToString("\n\n") { option -> "Tu elección: $option" }
        val reflectionMessage = generateReflectionMessage(selectedOptions)

        binding.resultTextView.text = formattedText
        binding.reflectionText.text = reflectionMessage
    }

    private fun generateReflectionMessage(selectedOptions: Array<String>): String {
        val reflections = mutableListOf<String>()

        selectedOptions.forEachIndexed { index, option ->
            when (index) {
                0 -> reflections.add(interpretQuestion1(option))  // Pregunta 1
                1 -> reflections.add(interpretQuestion2(option))  // Pregunta 2
                2 -> reflections.add(interpretQuestion3(option))  // Pregunta 3
                else -> reflections.add(getString(R.string.reflection_general))
            }
        }

        return reflections.joinToString("\n\n")
    }

    private fun interpretQuestion1(option: String): String {
        return when (option) {
            getString(R.string.option1_evaluation5) -> getString(R.string.reflection_risk_taking)
            getString(R.string.option2_evaluation5) -> getString(R.string.reflection_cautious_decision)
            else -> getString(R.string.reflection_general)
        }
    }

    private fun interpretQuestion2(option: String): String {
        return when (option) {
            getString(R.string.option1_question2) -> getString(R.string.reflection_internal_growth)
            getString(R.string.option2_question2) -> getString(R.string.reflection_learning_growth)
            else -> getString(R.string.reflection_general)
        }
    }

    private fun interpretQuestion3(option: String): String {
        return when (option) {
            getString(R.string.option1_question3) -> getString(R.string.reflection_adaptability)
            getString(R.string.option2_question3) -> getString(R.string.reflection_market_analysis)
            else -> getString(R.string.reflection_general)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
