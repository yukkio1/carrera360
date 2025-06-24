package com.example.inlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.carrera360.app_carrera360.apiencuestamultiple.ApiMultipleRequest
import com.carrera360.app_carrera360.apiencuestamultiple2.ApiMultipleService2
import com.example.inlab.apiconfirmacion.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Question5Activity : AppCompatActivity() {

    private lateinit var finishButton: Button
    private lateinit var checkBoxes: List<CheckBox>
    private lateinit var progressBar: ProgressBar
    private val selectedPriorities = mutableListOf<CheckBox>()
    private var solicitudEnviada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question5)

        finishButton = findViewById(R.id.finishButton)
        progressBar = findViewById(R.id.progressBar5)

        checkBoxes = listOf(
            findViewById(R.id.option1_5),
            findViewById(R.id.option2_5),
            findViewById(R.id.option3_5),
            findViewById(R.id.option4_5),
            findViewById(R.id.option5_5)
        )

        finishButton.visibility = View.GONE

        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (selectedPriorities.size < 5) {
                        selectedPriorities.add(checkBox)
                    } else {
                        checkBox.isChecked = false
                    }
                } else {
                    selectedPriorities.remove(checkBox)
                }
                updatePriorityNumbers()
                finishButton.visibility = if (selectedPriorities.size == 5) View.VISIBLE else View.GONE
            }
        }

        val idUsuario = intent.getIntExtra("idUsuario", -1)
        val nombre = intent.getStringExtra("nombre") ?: ""
        val apellido = intent.getStringExtra("apellido") ?: ""
        val edad = intent.getIntExtra("edad", -1)
        val correo = intent.getStringExtra("correo") ?: ""
        val usuario = intent.getStringExtra("usuario") ?: ""
        val fechaCreacion = intent.getStringExtra("fechaCreacion") ?: ""

        finishButton.setOnClickListener {
            val respuestasOrdenadas = selectedPriorities.map { it.text.toString().substringAfter(". ").trim() }

            if (respuestasOrdenadas.size == 5) {
                enviarRespuestasFinales(
                    idUsuario,
                    respuestasOrdenadas,
                    nombre, apellido, edad, correo, usuario, fechaCreacion
                )
            }
        }
    }

    private fun updatePriorityNumbers() {
        selectedPriorities.forEachIndexed { index, checkBox ->
            checkBox.text = "${index + 1}. ${checkBox.text.toString().substringAfter(". ").trim()}"
        }

        checkBoxes.forEach { checkBox ->
            if (!selectedPriorities.contains(checkBox)) {
                checkBox.text = checkBox.text.toString().substringAfter(". ").trim()
            }
        }
    }

    private fun enviarRespuestasFinales(
        idUsuario: Int,
        alternativas: List<String>,
        nombre: String,
        apellido: String,
        edad: Int,
        correo: String,
        usuario: String,
        fechaCreacion: String
    ) {
        if (solicitudEnviada) return
        solicitudEnviada = true
        progressBar.visibility = View.VISIBLE

        val apiService = RetrofitClient.instance.create(ApiMultipleService2::class.java)
        val request = ApiMultipleRequest(idUsuario, alternativas)

        apiService.registrarRespuestas(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val mensaje = response.body()?.string() ?: "✔️ Respuestas finales registradas"
                    Toast.makeText(this@Question5Activity, mensaje, Toast.LENGTH_SHORT).show()
                    Log.d("FinalAPI", "✅ $mensaje")
                    irAMainActivity(
                        idUsuario, nombre, apellido, edad, correo, usuario, fechaCreacion
                    )
                } else {
                    Toast.makeText(this@Question5Activity, "❌ Error del servidor", Toast.LENGTH_SHORT).show()
                    solicitudEnviada = false
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progressBar.visibility = View.GONE
                solicitudEnviada = false
                Toast.makeText(this@Question5Activity, "❌ Fallo de red", Toast.LENGTH_SHORT).show()
                Log.e("FinalAPI", "❌ ${t.message}")
            }
        })
    }

    private fun irAMainActivity(
        idUsuario: Int,
        nombre: String,
        apellido: String,
        edad: Int,
        correo: String,
        usuario: String,
        fechaCreacion: String
    )
    {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("idUsuario", idUsuario)
            putExtra("nombre", nombre)
            putExtra("apellido", apellido)
            putExtra("edad", edad)
            putExtra("correo", correo)
            putExtra("usuario", usuario)
            putExtra("fechaCreacion", fechaCreacion)
        }
        startActivity(intent)
        finish()
    }
}
