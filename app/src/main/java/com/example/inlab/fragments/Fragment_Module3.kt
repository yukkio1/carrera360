package com.example.inlab.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.apiregistroModulo.RetrofitClient
import com.example.inlab.databinding.FragmentModule3Binding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.inlab.viewmodel.UsuarioViewModel
class Module3Fragment : Fragment(R.layout.fragment_module3) {
    private var _binding: FragmentModule3Binding? = null
    private val binding get() = _binding!!
    private var player1: ExoPlayer? = null
    private var player2: ExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentModule3Binding.bind(view)

        Log.d("ExoPlayer", "Fragmento Module3Fragment iniciado correctamente.")

        // 👇 Llamada a la API: Registro del módulo 2
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: 0
        val idModulo = 9 // Módulo correspondiente a habilidades

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

        ajustarProporcion(binding.exoplayerParte2)
    }

    private fun setupNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        findNavController().navigate(Module3FragmentDirections.actionModule3ToHome())
                        true
                    }
                    R.id.learn_fragment -> {
                        findNavController().navigate(Module3FragmentDirections.actionModule3ToLearn())
                        true
                    }
                    R.id.profile_fragment -> {
                        findNavController().navigate(Module3FragmentDirections.actionModule3ToProfile())
                        true
                    }
                    else -> false
                }
            }
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(Module3FragmentDirections.actionModule3ToLearn())
        }
        binding.btnResponder.setOnClickListener {
            findNavController().navigate(Module3FragmentDirections.actionModule3ToEvaluation3())
        }
    }

    private fun setupPlayers() {

        player2 = configurePlayer(binding.exoplayerParte2, "module3parte2")
    }

    private fun configurePlayer(playerView: com.google.android.exoplayer2.ui.PlayerView, nombreArchivo: String): ExoPlayer {
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

    private fun ajustarProporcion(playerView: com.google.android.exoplayer2.ui.PlayerView) {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = (screenWidth / 16) * 9

        playerView.layoutParams.width = screenWidth
        playerView.layoutParams.height = screenHeight
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player1?.release()
        player2?.release()
        _binding = null
    }
}
