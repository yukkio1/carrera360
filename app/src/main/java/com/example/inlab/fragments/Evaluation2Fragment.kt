package com.example.inlab.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.apievaluaciones.ApiClient
import com.example.inlab.apievaluaciones.EvaluacionRequest
import com.example.inlab.apievaluaciones.MensajeResponse
import com.example.inlab.databinding.FragmentEvaluation2Binding
import com.example.inlab.viewmodel.UsuarioViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Evaluation2Fragment : Fragment(R.layout.fragment_evaluation2) {
    private var _binding: FragmentEvaluation2Binding? = null
    private val binding get() = _binding!!
    private val usuarioViewModel: UsuarioViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEvaluation2Binding.bind(view)

        Log.d("Evaluation", "Fragmento Evaluation2 iniciado correctamente.")

        setupButtons()
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(Evaluation2FragmentDirections.actionEvaluation2ToModule2())
        }

        binding.btnSubmitEvaluation2.setOnClickListener {
            evaluarRespuesta()
        }
    }

    private fun evaluarRespuesta() {
        // Validar pregunta 1
        val selectedOptionId1 = binding.radioGroupQuestion1.checkedRadioButtonId
        if (selectedOptionId1 == -1) {
            Toast.makeText(requireContext(), R.string.evaluation_error, Toast.LENGTH_SHORT).show()
            return
        }

        // Obtener respuesta 1
        val option1 = requireView().findViewById<RadioButton>(selectedOptionId1).text.toString()

        // Validar pregunta 2
        val selectedOptionId2 = binding.radioGroupQuestion2.checkedRadioButtonId
        if (selectedOptionId2 == -1) {
            Toast.makeText(requireContext(), R.string.evaluation_error, Toast.LENGTH_SHORT).show()
            return
        }

        // Obtener respuesta 2
        val option2 = requireView().findViewById<RadioButton>(selectedOptionId2).text.toString()

        // Validar pregunta 3
        val selectedOptionId3 = binding.radioGroupQuestion3.checkedRadioButtonId
        if (selectedOptionId3 == -1) {
            Toast.makeText(requireContext(), R.string.evaluation_error, Toast.LENGTH_SHORT).show()
            return
        }

        // Obtener respuesta 3
        val option3 = requireView().findViewById<RadioButton>(selectedOptionId3).text.toString()

        // Combinar opciones o usar solo una si prefieres
        val combinedOptions = "$option1\n$option2\n$option3"

        Log.d("Evaluation", "✅ Respuestas seleccionadas:\n$combinedOptions")

        registrarEvaluacionRealizada()
        val action = Evaluation2FragmentDirections.actionEvaluation2ToResult2(combinedOptions)
        findNavController().navigate(action)
    }

    private fun registrarEvaluacionRealizada() {
        val idUsuario = usuarioViewModel.idUsuario.value
        val idEvaluacion = 2 // Evaluación correspondiente al módulo 2

        if (idUsuario != null) {
            val request = EvaluacionRequest(idUsuario, idEvaluacion)

            ApiClient.apiService.registrarEvaluacion(request)
                .enqueue(object : Callback<MensajeResponse> {
                    override fun onResponse(
                        call: Call<MensajeResponse>,
                        response: Response<MensajeResponse>
                    ) {
                        val mensaje = response.body()?.mensaje
                        Toast.makeText(requireContext(), mensaje ?: "Respuesta sin mensaje", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<MensajeResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        } else {
            Toast.makeText(requireContext(), R.string.user_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}