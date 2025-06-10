package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inlab.R
import com.example.inlab.adapters.ConsejoAdapter
import com.example.inlab.databinding.FragmentHomeBinding
import com.example.inlab.modelo.Consejo

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setupRecyclerViews()
        setupBottomNavigation()
    }

    private fun setupRecyclerViews() {

        // ✅ Lista de consejos con imágenes
        val listaConsejos = listOf(
            Consejo("Marketing personal y visibilidad profesional","---", R.drawable.ic_entrevista),
            Consejo("Desarrollo de habilidades clave", "---",R.drawable.ic_equipo),
            Consejo("Red de contactos y confianza","---", R.drawable.ic_presentacion),
            Consejo("Realidad laboral y adaptación", "---", R.drawable.ic_cv),
            Consejo("Errores que dificultan la inserción laboral", "----", R.drawable.ic_salario),
            Consejo("Dominar entrevistas de trabajo", "---", R.drawable.ic_entrevista_star)

        )

        // ✅ Configuración del RecyclerView de consejos con navegación usando Safe Args
        binding.recyclerConsejos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ConsejoAdapter(listaConsejos) { consejo ->
                when (consejo.nombre) {
                    "Marketing personal y visibilidad profesional" -> {
                        val action = HomeFragmentDirections.actionHomeToMarketing()
                        findNavController().navigate(action)
                    }
                    "Desarrollo de habilidades clave" -> {
                        val action = HomeFragmentDirections.actionHomeToHabilidades()
                        findNavController().navigate(action)
                    }
                    "Red de contactos y confianza" -> {
                        val action = HomeFragmentDirections.actionHomeToRedContactos()
                        findNavController().navigate(action)
                    }
                    "Realidad laboral y adaptación" -> {
                        val action = HomeFragmentDirections.actionHomeToRealidad()
                        findNavController().navigate(action)
                    }
                    "Errores que dificultan la inserción laboral" -> {
                        val action = HomeFragmentDirections.actionHomeToErrores()
                        findNavController().navigate(action)
                    }
                    "Dominar entrevistas de trabajo" -> {
                        val action = HomeFragmentDirections.actionHomeToEntrevistas()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    private fun setupBottomNavigation() {
        // ✅ Manejo de navegación con BottomNavigationView
        requireActivity().findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = HomeFragmentDirections.actionHomeSelf()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = HomeFragmentDirections.actionHomeToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = HomeFragmentDirections.actionHomeToProfile()
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
