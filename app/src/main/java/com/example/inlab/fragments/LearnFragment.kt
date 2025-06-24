package com.example.inlab.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inlab.R
import com.example.inlab.adapters.CarreraAdapter
import com.example.inlab.databinding.FragmentLearnBinding
import com.example.inlab.modelo.Carrera
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.inlab.viewmodel.UsuarioViewModel
class LearnFragment : Fragment(R.layout.fragment_learn) {
    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLearnBinding.bind(view)

        setupRecyclerViews()
        setupBottomNavigation()
    }

    private fun setupRecyclerViews() {
        val tituloModulo1 = getString(R.string.module_1_title)
        val tituloModulo2 = getString(R.string.module_2_title)
        val tituloModulo3 = getString(R.string.module_3_title)
        val tituloModulo4 = getString(R.string.module_4_title)
        val tituloModulo5 = getString(R.string.module_5_title)
        val tituloModulo6 = getString(R.string.module_6_title)
        val tituloModulo7 = getString(R.string.module_7_title)

        // ✅ Lista de carreras con imágenes
        val listaConsejos = listOf(
            Carrera(tituloModulo1, "---", R.drawable.ic_learning),
            Carrera(tituloModulo2, "----", R.drawable.ic_adapter),
            Carrera(tituloModulo3, "---", R.drawable.ic_creci),
            Carrera(tituloModulo4, "---", R.drawable.ic_riesgos),
            Carrera(tituloModulo5, "----", R.drawable.ic_evolucion),
            Carrera(tituloModulo6, "----", R.drawable.ic_empleo),
            Carrera(tituloModulo7, "---", R.drawable.ic_actu_hab)
        )

        // ✅ Configuración del RecyclerView de consejos con navegación usando Safe Args
        binding.recyclerCarreras.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CarreraAdapter(listaConsejos) { carrera ->
                when (carrera.nombre) {
                    tituloModulo1 -> {
                        val action = LearnFragmentDirections.actionLearnToModule1()
                        findNavController().navigate(action)
                    }
                    tituloModulo2 -> {
                        val action = LearnFragmentDirections.actionLearnToModule2()
                        findNavController().navigate(action)
                    }
                    tituloModulo3 -> {
                        val action = LearnFragmentDirections.actionLearnToModule3()
                        findNavController().navigate(action)
                    }
                    tituloModulo4 -> {
                        val action = LearnFragmentDirections.actionLearnToModule4()
                        findNavController().navigate(action)
                    }
                    tituloModulo5 -> {
                        val action = LearnFragmentDirections.actionLearnToModule5()
                        findNavController().navigate(action)
                    }
                    tituloModulo6 -> {
                        val action = LearnFragmentDirections.actionLearnToModule6()
                        findNavController().navigate(action)
                    }
                    tituloModulo7 -> {
                        val action = LearnFragmentDirections.actionLearnToModule7()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    private fun setupBottomNavigation() {
        // ✅ Manejo de navegación con BottomNavigationView
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = LearnFragmentDirections.actionLearnToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = LearnFragmentDirections.actionLearnSelf()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = LearnFragmentDirections.actionLearnToProfile()
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
