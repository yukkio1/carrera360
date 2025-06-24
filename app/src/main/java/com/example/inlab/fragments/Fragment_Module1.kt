package com.example.inlab.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ExoPlayer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.apiregistroModulo.RetrofitClient
import com.example.inlab.databinding.FragmentModule1Binding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.inlab.viewmodel.UsuarioViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class Module1Fragment : Fragment(R.layout.fragment_module1) {
    private var _binding: FragmentModule1Binding? = null
    private val binding get() = _binding!!
    private var player1: ExoPlayer? = null
    private var player2: ExoPlayer? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentModule1Binding.bind(view)

        // 👇 Llamada a la API: Registro del módulo 2
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: 0
        val idModulo = 7 // Módulo correspondiente a habilidades


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
        setupPlayers()
        setupButtons()
        ajustarProporcion(binding.definirobjetivo)
        ajustarProporcion(binding.planificavideo)
        setupBottomNavigation()
    }

    private fun setupButtons() {
        binding.btnRespondEvaluation.setOnClickListener {
            val finalResult = "Resultado inicial" // Ajusta esto según tu lógica
            val action = Module1FragmentDirections.actionModule1ToEvaluation(finalResult)
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            val action = Module1FragmentDirections.actionModule1ToLearn()
            findNavController().navigate(action)
        }
    }
    private fun setupPlayers() {
        player1 = configurePlayer(binding.definirobjetivo, "definirobjetivo")
        player2 = configurePlayer(binding.planificavideo, "planificavideo")
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
    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = Module1FragmentDirections.actionModule1ToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = Module1FragmentDirections.actionModule1ToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = Module1FragmentDirections.actionModule1ToProfile()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
    }
    private fun ajustarProporcion(playerView: PlayerView) {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = (screenWidth / 9) * 16 // Calcula la altura basada en 9:16

        val params = playerView.layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = screenHeight
        playerView.layoutParams = params
    }
    override fun onDestroyView() {
        super.onDestroyView()
        player1?.release()
        player2?.release()
        _binding = null
    }
}
