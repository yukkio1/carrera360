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
import com.example.inlab.databinding.FragmentMarketingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.inlab.apiregistroModulo.RetrofitClient
import com.example.inlab.apiregistroModulo.RegistroModuloResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import com.example.inlab.viewmodel.UsuarioViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class MarketingFragment : Fragment(R.layout.fragment_marketing) {
    private var _binding: FragmentMarketingBinding? = null
    private var player1: ExoPlayer? = null
    private var player2: ExoPlayer? = null
    private var player3: ExoPlayer? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMarketingBinding.bind(view)
        Log.d("ExoPlayer", "Fragmento Marketing iniciado correctamente.")
        // 👇 Llamada a la API (una sola vez al entrar al fragmento)
        val usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        val idUsuario = usuarioViewModel.idUsuario.value ?: 0
        val idModulo = 1 // ID único del módulo que se está visualizando

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.registroModuloApi.registrarModulo(idUsuario, idModulo)

                if (response.isSuccessful && response.body() != null) {
                    val resultado = response.body()
                    println("API Response: ${resultado?.mensaje}")
                } else {
                    println("Error en la API: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error al llamar a la API: ${e.message}")
            }
        }

        setupBottomNavigation()
        setupBackButton()
        setupBtnEvaluacion()
        setupPlayers()
        ajustarProporcion(binding.exoplayerParte1)
        ajustarProporcion2(binding.exoplayerParte2)
        ajustarProporcion2(binding.exoplayerParte3)
    }

    private fun setupPlayers() {
        player1 = configurePlayer(binding.exoplayerParte1, "marca")
        player2 = configurePlayer(binding.exoplayerParte2, "errormarca")
        player3 = configurePlayer(binding.exoplayerParte3, "lecciones")

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
    private fun ajustarProporcion2(playerView: PlayerView) {
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = (screenWidth /9 ) * 16 // Calcula la altura basada en 16:9

        val params = playerView.layoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = screenHeight
        playerView.layoutParams = params
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            val action = MarketingFragmentDirections.actionMarketingToHome()
            findNavController().navigate(action)
        }
    }


    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home_fragment -> {
                        val action = MarketingFragmentDirections.actionMarketingToHome()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.learn_fragment -> {
                        val action = MarketingFragmentDirections.actionMarketingToLearn()
                        findNavController().navigate(action)
                        true
                    }
                    R.id.profile_fragment -> {
                        val action = MarketingFragmentDirections.actionMarketingToProfile()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
    }
    private fun setupBtnEvaluacion() {
        binding.btnIrEvaluacion.setOnClickListener {
            // Aquí debes reemplazar "Respuesta predeterminada" con la lógica real para obtener las respuestas seleccionadas
            val selectedOption = "Respuesta predeterminada"
            findNavController().navigate(
                MarketingFragmentDirections.actionMarketingToEvaluation1(selectedOption)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}