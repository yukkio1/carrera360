package com.example.inlab

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.inlab.apirecuperacion.ApiRecuperacionService
import com.example.inlab.apirecuperacion.RecuperacionRequest
import com.example.inlab.apirecuperacion.RetrofitClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnBack = findViewById<ImageButton>(R.id.btn_Back)
        val recoverButton = findViewById<Button>(R.id.recoverButton)
        val emailField = findViewById<EditText>(R.id.emailField)
        val nameField = findViewById<EditText>(R.id.nombre)
        val lastNameField = findViewById<EditText>(R.id.apellido)
        val ageField = findViewById<EditText>(R.id.edad)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val errorTextView = findViewById<TextView>(R.id.errorTextView)
        val passwordTextView = findViewById<TextView>(R.id.passwordTextView)

        btnBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        recoverButton.setOnClickListener {
            val correo = emailField.text.toString().trim()
            val nombre = nameField.text.toString().trim()
            val apellido = lastNameField.text.toString().trim()
            val edadStr = ageField.text.toString().trim()

            if (correo.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || edadStr.isEmpty()) {
                errorTextView.text = "Completa todos los campos."
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val edad = edadStr.toIntOrNull()
            if (edad == null) {
                errorTextView.text = "Edad inválida."
                errorTextView.visibility = View.VISIBLE
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            errorTextView.visibility = View.GONE
            passwordTextView.visibility = View.GONE

            val request = RecuperacionRequest(correo, nombre, apellido, edad)
            val apiService = RetrofitClient.retrofit.create(ApiRecuperacionService::class.java)

            apiService.recuperarCuenta(request).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful && response.body() != null) {
                        val responseString = response.body()!!.string()
                        val jsonObject = JSONObject(responseString)
                        val password = jsonObject.getString("mensaje")
                        passwordTextView.text = "Tu contraseña es: $password"
                        passwordTextView.visibility = View.VISIBLE

                        Handler(Looper.getMainLooper()).postDelayed({
                            passwordTextView.visibility = View.GONE
                        }, 12000)

                        Log.d("RecuperacionAPI", "✅ Contraseña mostrada.")
                    } else {
                        errorTextView.text = "No se encontró una cuenta con esos datos."
                        errorTextView.visibility = View.VISIBLE
                        Log.e("RecuperacionAPI", "❌ Usuario no encontrado.")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    errorTextView.text = "Error de conexión: ${t.message}"
                    errorTextView.visibility = View.VISIBLE
                    Log.e("RecuperacionAPI", "❌ ${t.message}")
                }
            })
        }
    }
}
