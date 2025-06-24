package com.example.inlab.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentHabilidadesBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.inlab.apiregistroModulo.RetrofitClient
import com.example.inlab.apiregistroModulo.RegistroModuloResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.example.inlab.viewmodel.UsuarioViewModel
import com.google.android.exoplayer2.ui.PlayerView

class HabilidadesFragment : Fragment(R.layout.fragment_habilidades) {
    private var _binding: FragmentHabilidadesBinding? = null
    private val binding get() = _binding!!
    private var player1: ExoPlayer? = null
    private var player2: ExoPlayer? = null
    private var player3: ExoPlayer? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabilidadesBinding.bind(view)

        // 👇 Llamada a la API: Registro del módulo 2
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: 0
        val idModulo = 2 // Módulo correspondiente a habilidades

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

        setupBottomNavigation()
        setupBackButton()
        setupPlayers()
        ajustarProporcion(binding.importantehab)
        ajustarProporcion2(binding.desarrollohab)
        ajustarProporcion(binding.errorhab)
    }
    private fun setupPlayers() {

        player1 = configurePlayer(binding.importantehab, "importantehab")
        player2 = configurePlayer(binding.desarrollohab, "desarrollohab")
        player3 = configurePlayer(binding.errorhab, "errorhab")
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
        val screenHeight = (screenWidth / 9) * 16

        playerView.layoutParams.width = screenWidth
        playerView.layoutParams.height = screenHeight
    }

    private fun ajustarProporcion2(playerView: PlayerView) {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = (screenWidth / 16) * 9

        playerView.layoutParams.width = screenWidth
        playerView.layoutParams.height = screenHeight
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            val action = HabilidadesFragmentDirections.actionHabilidadesToHome()
            findNavController().navigate(action)
        }
    }

    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        findNavController().navigate(HabilidadesFragmentDirections.actionHabilidadesToHome())
                        true
                    }
                    R.id.learn_fragment -> {
                        findNavController().navigate(HabilidadesFragmentDirections.actionHabilidadesToLearn())
                        true
                    }
                    R.id.profile_fragment -> {
                        findNavController().navigate(HabilidadesFragmentDirections.actionHabilidadesToProfile())
                        true
                    }
                    else -> false
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player1?.release()
        player2?.release()
        player3?.release()
        _binding = null
    }
}