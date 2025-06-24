package com.example.inlab.fragments
import com.example.inlab.viewmodel.UsuarioViewModel
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.inlab.R
import com.example.inlab.databinding.FragmentEvaluation7Binding
import com.example.inlab.apievaluaciones.ApiClient
import com.example.inlab.apievaluaciones.EvaluacionRequest
import com.example.inlab.apievaluaciones.MensajeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentEvaluation7 : Fragment(R.layout.fragment_evaluation7) {
    private var _binding: FragmentEvaluation7Binding? = null
    private val binding get() = _binding!!
    private val selectedAnswers = mutableMapOf<Int, String>()
    private val usuarioViewModel: UsuarioViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEvaluation7Binding.bind(view)

        setupEvaluation()
        setupSubmitButton()
    }

    private fun setupEvaluation() {
        binding.radioGroupQuestion1.setOnCheckedChangeListener { _, checkedId ->
            selectedAnswers[1] = getAnswerText(checkedId)
        }
        binding.radioGroupQuestion2.setOnCheckedChangeListener { _, checkedId ->
            selectedAnswers[2] = getAnswerText(checkedId)
        }
        binding.radioGroupQuestion3.setOnCheckedChangeListener { _, checkedId ->
            selectedAnswers[3] = getAnswerText(checkedId)
        }
        binding.radioGroupQuestion4.setOnCheckedChangeListener { _, checkedId ->
            selectedAnswers[4] = getAnswerText(checkedId)
        }
        binding.radioGroupQuestion5.setOnCheckedChangeListener { _, checkedId ->
            selectedAnswers[5] = getAnswerText(checkedId)
        }
    }

    private fun getAnswerText(checkedId: Int): String {
        return when (checkedId) {
            R.id.answer_1A -> getString(R.string.response_1A)
            R.id.answer_1B -> getString(R.string.response_1B)
            R.id.answer_1C -> getString(R.string.response_1C)
            R.id.answer_2A -> getString(R.string.response_2A)
            R.id.answer_2B -> getString(R.string.response_2B)
            R.id.answer_2C -> getString(R.string.response_2C)
            R.id.answer_3A -> getString(R.string.response_3A)
            R.id.answer_3B -> getString(R.string.response_3B)
            R.id.answer_3C -> getString(R.string.response_3C)
            R.id.answer_4A -> getString(R.string.response_4A)
            R.id.answer_4B -> getString(R.string.response_4B)
            R.id.answer_4C -> getString(R.string.response_4C)
            R.id.answer_5A -> getString(R.string.response_5A)
            R.id.answer_5B -> getString(R.string.response_5B)
            R.id.answer_5C -> getString(R.string.response_5C)
            else -> ""
        }
    }

    private fun setupSubmitButton() {
        binding.btnSubmitEvaluation7.setOnClickListener {
            val answersArray = selectedAnswers.values.toTypedArray()
            registrarEvaluacionRealizada()
            val action = FragmentEvaluation7Directions.actionEvaluation7ToResult7(answersArray)
            findNavController().navigate(action)
        }
    }
    private fun registrarEvaluacionRealizada() {
        val idUsuario = usuarioViewModel.idUsuario.value
        val idEvaluacion = 7

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
