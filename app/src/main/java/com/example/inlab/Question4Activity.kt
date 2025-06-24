package com.example.inlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.carrera360.app_carrera360.apiencuesta.ApiEncuestaRequest
import com.carrera360.app_carrera360.apiencuesta.ApiEncuestaResponse
import com.carrera360.app_carrera360.apiencuesta.ApiEncuestaService
import com.example.inlab.apiconfirmacion.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Question4Activity : AppCompatActivity() {

    private lateinit var nextButton: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var progressBar: ProgressBar
    private var solicitudEnviada = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question4)

        nextButton = findViewById(R.id.nextButton4)
        radioGroup = findViewById(R.id.radioGroup4)
        progressBar = findViewById(R.id.progressBar4)

        nextButton.visibility = View.GONE

        // Recuperar datos del intent
        val idUsuario = intent.getIntExtra("idUsuario", -1)
        val nombre = intent.getStringExtra("nombre") ?: ""
        val apellido = intent.getStringExtra("apellido") ?: ""
        val edad = intent.getIntExtra("edad", -1)
        val correo = intent.getStringExtra("correo") ?: ""
        val usuario = intent.getStringExtra("usuario") ?: ""
        val fechaCreacion = intent.getStringExtra("fechaCreacion") ?: ""

        radioGroup.setOnCheckedChangeListener { _, _ ->
            nextButton.visibility = View.VISIBLE
        }

        nextButton.setOnClickListener {
            val selectedRadioId = radioGroup.checkedRadioButtonId
            val textoAlternativa = findViewById<RadioButton>(selectedRadioId)?.text.toString()

            if (textoAlternativa.isNotBlank()) {
                enviarRespuestaEncuesta(
                    idUsuario, textoAlternativa,
                    nombre, apellido, edad, correo, usuario, fechaCreacion
                )
            }
        }
    }

    private fun enviarRespuestaEncuesta(
        idUsuario: Int,
        textoAlternativa: String,
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

        val apiService = RetrofitClient.instance.create(ApiEncuestaService::class.java)
        val request = ApiEncuestaRequest(idUsuario, textoAlternativa)

        apiService.registrarRespuesta(request).enqueue(object : Callback<ApiEncuestaResponse> {
            override fun onResponse(call: Call<ApiEncuestaResponse>, response: Response<ApiEncuestaResponse>) {
                progressBar.visibility = View.GONE
                val mensaje = response.body()?.mensaje ?: "Sin mensaje"
                Toast.makeText(this@Question4Activity, mensaje, Toast.LENGTH_SHORT).show()
                Log.d("EncuestaAPI", "✔️ Respuesta enviada: $mensaje")

                redirigirAQuestion5(idUsuario, nombre, apellido, edad, correo, usuario, fechaCreacion)
            }

            override fun onFailure(call: Call<ApiEncuestaResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                solicitudEnviada = false
                Toast.makeText(this@Question4Activity, "❌ Error al enviar la respuesta", Toast.LENGTH_SHORT).show()
                Log.e("EncuestaAPI", "❌ Fallo de red: ${t.message}")
            }
        })
    }

    private fun redirigirAQuestion5(
        idUsuario: Int,
        nombre: String,
        apellido: String,
        edad: Int,
        correo: String,
        usuario: String,
        fechaCreacion: String
    ) {
        val intent = Intent(this, Question5Activity::class.java).apply {
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
