package com.example.inlab.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.apiregistroModulo.RetrofitClient
import com.example.inlab.databinding.FragmentModule5Binding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.inlab.viewmodel.UsuarioViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class Module5Fragment : Fragment(R.layout.fragment_module5) {
    private var _binding: FragmentModule5Binding? = null
    private val binding get() = _binding!!
    private var player1: ExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentModule5Binding.bind(view)

        Log.d("Module5", "Fragmento Module5Fragment iniciado correctamente.")
        Log.d("ExoPlayer", "Fragmento Module5Fragment iniciado correctamente.")
        // 👇 Llamada a la API: Registro del módulo 2
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: 0
        val idModulo = 11 // Módulo correspondiente a habilidades

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.registroModuloApi.registrarModulo(idUsuario, idModulo)

                if (response.isSuccessful && response.body() != null) {
                    val resultado = response.body()
                    println("Registro Módulo $idModulo: ${resultado?.mensaje}")
                } else {
                    println("Error en registro de módulo $idModulo: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error de red o servidor: ${e.message}")
            }
        }

        setupNavigation()
        setupButtons()
        setupPlayers()
        ajustarProporcion(binding.oki)
    }

    private fun setupPlayers() {
        player1 = configurePlayer(binding.oki, "pan")

    }
    private fun configurePlayer(playerView: PlayerView, nombreArchivo: String): ExoPlayer {
        val player = ExoPlayer.Builder(requireContext()).build()
        playerView.player = player

        val resId = resources.getIdentifier(nombreArchivo, "raw", requireContext().packageName)
        if (resId == 0) {
            Log.e("ExoPlayer", "❌ Archivo $nombreArchivo NO encontrado en res/raw")
            return player
        }

        val uri = Uri.parse("android.resource://${requireContext().packageName}/$resId")
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.prepare()

        Log.d("ExoPlayer", "✅ Video $nombreArchivo listo para reproducirse.")
        return player
    }
    private fun ajustarProporcion(playerView: PlayerView) {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = (screenWidth / 9) * 16 // Calcula la altura basada en 16:9

        val params = playerView.layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = screenHeight
        playerView.layoutParams = params
    }


    private fun setupNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        findNavController().navigate(Module5FragmentDirections.actionModule5ToHome())
                        true
                    }
                    R.id.learn_fragment -> {
                        findNavController().navigate(Module5FragmentDirections.actionModule5ToLearn())
                        true
                    }
                    R.id.profile_fragment -> {
                        findNavController().navigate(Module5FragmentDirections.actionModule5ToProfile())
                        true
                    }
                    else -> false
                }
            }
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(Module5FragmentDirections.actionModule5ToLearn())
        }

        binding.btnResponderModule5.setOnClickListener {
            findNavController().navigate(Module5FragmentDirections.actionModule5ToEvaluation5())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
