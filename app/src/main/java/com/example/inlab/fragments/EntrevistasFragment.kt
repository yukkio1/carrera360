package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentEntrevistasBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class EntrevistasFragment : Fragment(R.layout.fragment_entrevistas) {
    private var _binding: FragmentEntrevistasBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEntrevistasBinding.bind(view)

        setupBottomNavigation()
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            val action = EntrevistasFragmentDirections.actionEntrevistasToHome()
            findNavController().navigate(action)
        }
    }

    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = EntrevistasFragmentDirections.actionEntrevistasToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = EntrevistasFragmentDirections.actionEntrevistasToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = EntrevistasFragmentDirections.actionEntrevistasToProfile()
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
