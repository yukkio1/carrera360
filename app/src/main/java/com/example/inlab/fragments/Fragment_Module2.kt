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
import com.example.inlab.databinding.FragmentModule2Binding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.inlab.viewmodel.UsuarioViewModel

class Module2Fragment : Fragment(R.layout.fragment_module2) {
    private var _binding: FragmentModule2Binding? = null
    private val binding get() = _binding!!
    private var player1: ExoPlayer? = null
    private var player2: ExoPlayer? = null
    private var player3: ExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentModule2Binding.bind(view)

        Log.d("ExoPlayer", "Fragmento Module2Fragment iniciado correctamente.")

        // 👇 Llamada a la API: Registro del módulo 2
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: 0
        val idModulo = 8 // Módulo correspondiente a habilidades

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

        setupButtons()
        setupBottomNavigation()
        setupPlayers()
        ajustarProporcion(binding.exoplayerParte1)
        ajustarProporcion(binding.exoplayerParte2)
        ajustarProporcion(binding.exoplayerParte3)
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            val action = Module2FragmentDirections.actionModule2ToLearn()
            findNavController().navigate(action)
        }
    }

    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        findNavController().navigate(Module2FragmentDirections.actionModule2ToHome())
                        true
                    }
                    R.id.learn_fragment -> {
                        findNavController().navigate(Module2FragmentDirections.actionModule2ToLearn())
                        true
                    }
                    R.id.profile_fragment -> {
                        findNavController().navigate(Module2FragmentDirections.actionModule2ToProfile())
                        true
                    }
                    else -> false
                }
            }
    }

    private fun setupPlayers() {
        player1 = configurePlayer(binding.exoplayerParte1, "module2parte1")
        player2 = configurePlayer(binding.exoplayerParte2, "module2parte2")
        player3 = configurePlayer(binding.exoplayerParte3, "module2parte3")
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
        val screenHeight = (screenWidth / 16) * 9 // Calcula la altura basada en 16:9

        val params = playerView.layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = screenHeight
        playerView.layoutParams = params
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player1?.release()
        player2?.release()
        player3?.release()
        _binding = null
    }
}
