package com.example.inlab.fragments
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentCreditosBinding

import com.google.android.material.bottomnavigation.BottomNavigationView




class CreditosFragment : Fragment(R.layout.fragment_creditos) {
    private var _binding: FragmentCreditosBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreditosBinding.bind(view)

        setupButtons()
        setupBottomNavigation()
    }

    private fun setupButtons() {

        binding.btnBack.setOnClickListener {
            val action = CreditosFragmentDirections.actioncreditosToProfile()
            findNavController().navigate(action)
        }
    }
    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = CreditosFragmentDirections.actioncreditosToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = CreditosFragmentDirections.actioncreditosToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = CreditosFragmentDirections.actioncreditosToProfile()
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




