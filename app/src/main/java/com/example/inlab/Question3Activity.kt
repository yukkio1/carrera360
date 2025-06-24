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
import com.carrera360.app_carrera360.apiencuestamultiple.ApiMultipleService
import com.example.inlab.apiconfirmacion.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Question3Activity : AppCompatActivity() {

    private lateinit var checkBoxes: List<CheckBox>
    private lateinit var nextButton: Button
    private lateinit var progressBar: ProgressBar
    private var solicitudEnviada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question3)

        checkBoxes = listOf(
            findViewById(R.id.option1_3),
            findViewById(R.id.option2_3),
            findViewById(R.id.option3_3),
            findViewById(R.id.option4_3),
            findViewById(R.id.option5_3),
            findViewById(R.id.option6_3)
        )

        nextButton = findViewById(R.id.nextButton3)
        progressBar = findViewById(R.id.progressBar3)

        nextButton.visibility = View.GONE

        // Detectar selección para mostrar el botón
        checkBoxes.forEach {
            it.setOnCheckedChangeListener { _, _ ->
                nextButton.visibility = if (checkBoxes.any { it.isChecked }) View.VISIBLE else View.GONE
            }
        }

        // Recuperar datos del intent
        val idUsuario = intent.getIntExtra("idUsuario", -1)
        val nombre = intent.getStringExtra("nombre") ?: ""
        val apellido = intent.getStringExtra("apellido") ?: ""
        val edad = intent.getIntExtra("edad", -1)
        val correo = intent.getStringExtra("correo") ?: ""
        val usuario = intent.getStringExtra("usuario") ?: ""
        val fechaCreacion = intent.getStringExtra("fechaCreacion") ?: ""

        nextButton.setOnClickListener {
            val alternativasSeleccionadas = checkBoxes
                .filter { it.isChecked }
                .map { it.text.toString() }

            if (alternativasSeleccionadas.isEmpty()) return@setOnClickListener

            enviarRespuestasMultiples(idUsuario, alternativasSeleccionadas, nombre, apellido, edad, correo, usuario, fechaCreacion)
        }
    }

    private fun enviarRespuestasMultiples(
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

        val apiService = RetrofitClient.instance.create(ApiMultipleService::class.java)
        val request = ApiMultipleRequest(idUsuario, alternativas)

        apiService.registrarRespuestas(request).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val mensaje = response.body()?.string() ?: "Sin mensaje del servidor"
                    Log.e("MultipleAPI", "📩 Respuesta: $mensaje")
                    Toast.makeText(this@Question3Activity, mensaje, Toast.LENGTH_SHORT).show()
                    redirigirAQuestion4(idUsuario, nombre, apellido, edad, correo, usuario, fechaCreacion)
                } else {
                    Toast.makeText(this@Question3Activity, "❌ Error del servidor", Toast.LENGTH_SHORT).show()
                    solicitudEnviada = false
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progressBar.visibility = View.GONE
                solicitudEnviada = false
                Log.e("MultipleAPI", "❌ Error de red: ${t.message}")
                Toast.makeText(this@Question3Activity, "❌ No se pudo enviar la respuesta", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun redirigirAQuestion4(
        idUsuario: Int,
        nombre: String,
        apellido: String,
        edad: Int,
        correo: String,
        usuario: String,
        fechaCreacion: String
    ) {
        val intent = Intent(this, Question4Activity::class.java).apply {
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
