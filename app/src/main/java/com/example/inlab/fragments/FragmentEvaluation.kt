package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentEvaluationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentEvaluation : Fragment(R.layout.fragment_evaluation) {
    private var _binding: FragmentEvaluationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEvaluationBinding.bind(view)

        setupSubmitButton()
        setupBottomNavigation()
    }

    private fun setupSubmitButton() {
        binding.btnSubmitEvaluation.setOnClickListener {
            val selectedOptionId1 = binding.rgOptions1.checkedRadioButtonId
            val selectedOptionId2 = binding.rgOptions2.checkedRadioButtonId
            val selectedOptionId3 = binding.rgOptions3.checkedRadioButtonId

            val interpretationText1 = when (selectedOptionId1) {
                R.id.rbOptionA1 -> getString(R.string.evaluation_result_part1)
                R.id.rbOptionB1 -> getString(R.string.evaluation_result_part2)
                R.id.rbOptionC1 -> getString(R.string.evaluation_result_part3)
                R.id.rbOptionD1 -> getString(R.string.evaluation_result_part4)
                else -> getString(R.string.evaluation_error)
            }

            val interpretationText2 = when (selectedOptionId2) {
                R.id.rbOptionA2 -> getString(R.string.evaluation_result_part5)
                R.id.rbOptionB2 -> getString(R.string.evaluation_result_part6)
                R.id.rbOptionC2 -> getString(R.string.evaluation_result_part7)
                R.id.rbOptionD2 -> getString(R.string.evaluation_result_part8)
                else -> getString(R.string.evaluation_error)
            }

            val interpretationText3 = when (selectedOptionId3) {
                R.id.rbOptionA3 -> getString(R.string.evaluation_result_part9)
                R.id.rbOptionB3 -> getString(R.string.evaluation_result_part10)
                R.id.rbOptionC3 -> getString(R.string.evaluation_result_part11)
                R.id.rbOptionD3 -> getString(R.string.evaluation_result_part12)
                else -> getString(R.string.evaluation_error)
            }

            val finalResult = getString(R.string.evaluation_result, interpretationText1, interpretationText2, interpretationText3)
            val action = FragmentEvaluationDirections.actionEvaluationToResult(finalResult)
            findNavController().navigate(action)
        }
    }

    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = FragmentEvaluationDirections.actionEvaluationToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = FragmentEvaluationDirections.actionEvaluationToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = FragmentEvaluationDirections.actionEvaluationToProfile()
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
