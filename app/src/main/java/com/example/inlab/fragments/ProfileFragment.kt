package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        // ✅ Manejo de navegación con BottomNavigationView
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = ProfileFragmentDirections.actionProfileToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = ProfileFragmentDirections.actionProfileToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = ProfileFragmentDirections.actionProfileSelf()
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
