package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentRedContactosBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class RedContactosFragment : Fragment(R.layout.fragment_red_contactos) {
    private var _binding: FragmentRedContactosBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRedContactosBinding.bind(view)

        setupBottomNavigation()
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            val action = RedContactosFragmentDirections.actionRedContactosToHome()
            findNavController().navigate(action)
        }
    }

    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = RedContactosFragmentDirections.actionRedContactosToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = RedContactosFragmentDirections.actionRedContactosToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = RedContactosFragmentDirections.actionRedContactosToProfile()
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
