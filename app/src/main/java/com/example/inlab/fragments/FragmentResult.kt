package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inlab.R
import com.example.inlab.databinding.FragmentResultBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentResult : Fragment(R.layout.fragment_result) {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentResultArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResultBinding.bind(view)

        displayResult()
        setupBottomNavigation()
        setupReturnButton()
    }

    private fun displayResult() {
        binding.tvFinalResult.text = args.finalResult
    }

    private fun setupReturnButton() {
        binding.btnReturnToModule.setOnClickListener {
            val action = FragmentResultDirections.actionResultToModule1()
            findNavController().navigate(action)
        }
    }

    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = FragmentResultDirections.actionResultToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = FragmentResultDirections.actionResultToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = FragmentResultDirections.actionResultToProfile()
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
