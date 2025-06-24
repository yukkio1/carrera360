package com.example.inlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.inlab.apiconfirmacion.ApiConfirmacionService
import com.example.inlab.apiconfirmacion.ConfirmacionResponse
import com.example.inlab.apiconfirmacion.RetrofitClient
import com.carrera360.app_carrera360.apiencuesta.ApiEncuestaRequest
import com.carrera360.app_carrera360.apiencuesta.ApiEncuestaResponse
import com.carrera360.app_carrera360.apiencuesta.ApiEncuestaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Question1Activity : AppCompatActivity() {

    private lateinit var nextButton: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var progressBar: ProgressBar
    private var solicitudEnviada = false // ✅ Evita múltiples envíos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question1)

        Log.e("QuestionAPI", "🚀 Iniciando Question1Activity.kt...")

        nextButton = findViewById(R.id.nextButton1)
        radioGroup = findViewById(R.id.radioGroup1)
        progressBar = findViewById(R.id.progressBar1)

        nextButton.visibility = View.GONE

        val idUsuario = intent.getIntExtra("idUsuarioLogueado", -1)
        val nombreUsuario = intent.getStringExtra("nombre") ?: "Desconocido"
        val apellidoUsuario = intent.getStringExtra("apellido") ?: "Desconocido"
        val edadUsuario = intent.getIntExtra("edad", -1)
        val correoUsuario = intent.getStringExtra("correo") ?: "Sin correo"
        val usuario = intent.getStringExtra("usuarioLogueado") ?: "Sin usuario"
        val fechaCreacion = intent.getStringExtra("fechaCreacion") ?: "Fecha desconocida"

        Log.e("QuestionAPI", "📌 Datos completos -> Usuario: $usuario, Nombre: $nombreUsuario, Apellido: $apellidoUsuario, ID: $idUsuario, Edad: $edadUsuario, Correo: $correoUsuario, Fecha de creación: $fechaCreacion")

        // ✅ Ejecutamos la verificación de la encuesta SOLO una vez
        verificarEstadoCuestionario(idUsuario, nombreUsuario, apellidoUsuario, edadUsuario, correoUsuario, usuario, fechaCreacion)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            nextButton.visibility = View.VISIBLE
        }

        nextButton.setOnClickListener {
            val selectedOptionId = radioGroup.checkedRadioButtonId
            val selectedOptionText = findViewById<android.widget.RadioButton>(selectedOptionId)?.text.toString()

            enviarRespuestaEncuesta(idUsuario, selectedOptionText, nombreUsuario, apellidoUsuario, edadUsuario, correoUsuario, usuario, fechaCreacion)
        }
    }

    private fun verificarEstadoCuestionario(idUsuario: Int, nombreUsuario: String, apellidoUsuario: String,
                                            edadUsuario: Int, correoUsuario: String, usuario: String, fechaCreacion: String) {
        progressBar.visibility = View.VISIBLE
        val apiService = RetrofitClient.instance.create(ApiConfirmacionService::class.java)

        apiService.verificarEncuesta(idUsuario).enqueue(object : Callback<ConfirmacionResponse> {
            override fun onResponse(call: Call<ConfirmacionResponse>, response: Response<ConfirmacionResponse>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body()?.completado == true) {
                    Log.e("QuestionAPI", "✅ Usuario tiene registros. Redirigiendo a `MainActivity.kt`.")
                    redirigirAMainActivity(idUsuario, nombreUsuario, apellidoUsuario, edadUsuario, correoUsuario, usuario, fechaCreacion)
                } else {
                    Log.e("QuestionAPI", "ℹ️ Usuario aún no ha completado el cuestionario.")
                }
            }

            override fun onFailure(call: Call<ConfirmacionResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("QuestionAPI", "❌ Error de conexión: ${t.message}")
            }
        })
    }

    private fun enviarRespuestaEncuesta(idUsuario: Int, textoAlternativa: String,
                                        nombreUsuario: String, apellidoUsuario: String,
                                        edadUsuario: Int, correoUsuario: String, usuario: String, fechaCreacion: String) {
        if (solicitudEnviada) return // ✅ Evita múltiples envíos
        solicitudEnviada = true

        progressBar.visibility = View.VISIBLE
        val apiService = RetrofitClient.instance.create(ApiEncuestaService::class.java)
        val request = ApiEncuestaRequest(idUsuario, textoAlternativa)

        apiService.registrarRespuesta(request).enqueue(object : Callback<ApiEncuestaResponse> {
            override fun onResponse(call: Call<ApiEncuestaResponse>, response: Response<ApiEncuestaResponse>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    Log.e("QuestionAPI", "✅ Respuesta registrada: ${response.body()?.mensaje}")
                    redirigirAQuestion2(idUsuario, nombreUsuario, apellidoUsuario, edadUsuario, correoUsuario, usuario, fechaCreacion)
                } else {
                    Log.e("QuestionAPI", "❌ Error al registrar respuesta: ${response.errorBody()?.string()}")
                    solicitudEnviada = false // ✅ Permitir reintento si hay fallo
                }
            }

            override fun onFailure(call: Call<ApiEncuestaResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("QuestionAPI", "❌ Error de conexión: ${t.message}")
                solicitudEnviada = false // ✅ Permitir reintento si hay fallo
            }
        })
    }

    private fun redirigirAQuestion2(idUsuario: Int, nombreUsuario: String, apellidoUsuario: String,
                                    edadUsuario: Int, correoUsuario: String, usuario: String, fechaCreacion: String) {
        val intent = Intent(this, Question2Activity::class.java).apply {
            putExtra("idUsuario", idUsuario)
            putExtra("nombre", nombreUsuario)
            putExtra("apellido", apellidoUsuario)
            putExtra("edad", edadUsuario)
            putExtra("correo", correoUsuario)
            putExtra("usuario", usuario)
            putExtra("fechaCreacion", fechaCreacion)
        }
        Log.e("QuestionAPI", "🚀 Pasando datos completos a `Question2Activity.kt`")
        startActivity(intent)
        finish()
    }

    private fun redirigirAMainActivity(idUsuario: Int, nombreUsuario: String, apellidoUsuario: String,
                                       edadUsuario: Int, correoUsuario: String, usuario: String, fechaCreacion: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("idUsuario", idUsuario)
            putExtra("nombre", nombreUsuario)
            putExtra("apellido", apellidoUsuario)
            putExtra("edad", edadUsuario)
            putExtra("correo", correoUsuario)
            putExtra("usuario", usuario)
            putExtra("fechaCreacion", fechaCreacion)
        }
        Log.e("QuestionAPI", "🚀 Usuario ya completó el cuestionario, redirigiendo a `MainActivity.kt` con todos los datos.")
        startActivity(intent)
        finish()
    }
}
