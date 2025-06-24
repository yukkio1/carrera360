package com.example.inlab.fragments
import com.example.inlab.viewmodel.UsuarioViewModel
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
import com.example.inlab.databinding.FragmentEvaluation3Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Evaluation3Fragment : Fragment(R.layout.fragment_evaluation3) {
    private var _binding: FragmentEvaluation3Binding? = null
    private val binding get() = _binding!!
    private val usuarioViewModel: UsuarioViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEvaluation3Binding.bind(view)

        Log.d("Evaluation", "Fragmento Evaluation3Fragment iniciado correctamente.")

        setupButtons()
    }

    private fun setupButtons() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(Evaluation3FragmentDirections.actionEvaluation3ToModule3())
        }

        binding.btnSubmitEvaluation3.setOnClickListener {
            evaluarRespuesta()
        }
    }

    private fun evaluarRespuesta() {
        val selectedOptionId = binding.radioGroupEvaluation3.checkedRadioButtonId
        if (selectedOptionId == -1) {
            Log.e("Evaluation", "❌ No se ha seleccionado ninguna opción.")
            return
        }

        val selectedOption = requireView().findViewById<RadioButton>(selectedOptionId)
        val respuesta = selectedOption.text.toString()

        Log.d("Evaluation", "✅ Respuesta seleccionada: $respuesta")
        registrarEvaluacionRealizada()
        val action = Evaluation3FragmentDirections.actionEvaluation3ToResult3(respuesta)
        findNavController().navigate(action)
    }
    private fun registrarEvaluacionRealizada() {
        val idUsuario = usuarioViewModel.idUsuario.value
        val idEvaluacion = 3

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
            Toast.makeText(requireContext(), "ID de usuario no disponible", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
