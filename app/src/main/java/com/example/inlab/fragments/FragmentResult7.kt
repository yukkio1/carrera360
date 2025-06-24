package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentResult7Binding

class FragmentResult7 : Fragment(R.layout.fragment_result7) {
    private var _binding: FragmentResult7Binding? = null
    private val binding get() = _binding!!
    private val args: FragmentResult7Args by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResult7Binding.bind(view)

        displayResults()
        setupBackButton()
    }

    private fun displayResults() {
        val userResponses = args.selectedOptions.joinToString("\n")
        binding.tvUserResults.text = getString(R.string.results_summary, userResponses)
    }

    private fun setupBackButton() {
        binding.btnBackToModule7.setOnClickListener {
            findNavController().navigate(FragmentResult7Directions.actionResult7ToModule7())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
