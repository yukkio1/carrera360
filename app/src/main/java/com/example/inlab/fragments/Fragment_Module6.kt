
package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentModule6Binding

class Module6Fragment : Fragment(R.layout.fragment_module6) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val btnatras: Button = view.findViewById(R.id.btn_Back)
        btnatras.setOnClickListener {
            findNavController().navigate(R.id.action_module6_to_learn)
        }
    }
}