package com.example.inlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.inlab.apiregistro.RetrofitClient
import com.example.inlab.apiregistro.Usuario
import com.example.inlab.apiregistro.UsuarioResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnBack = findViewById<ImageButton>(R.id.btn_Back)
        val btnRegister = findViewById<Button>(R.id.registrarseButton)
        val usernameField = findViewById<EditText>(R.id.usuario)
        val emailField = findViewById<EditText>(R.id.correoelec)
        val passwordField = findViewById<EditText>(R.id.passwordField)
        val nameField = findViewById<EditText>(R.id.nombre)
        val lastNameField = findViewById<EditText>(R.id.apellido)
        val ageField = findViewById<EditText>(R.id.edad)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val errorTextView = findViewById<TextView>(R.id.errorTextView)

        btnBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnRegister.setOnClickListener {
            val usuario = usernameField.text.toString().trim()
            val correo = emailField.text.toString().trim()
            val contrasena = passwordField.text.toString().trim()
            val nombre = nameField.text.toString().trim()
            val apellido = lastNameField.text.toString().trim()
            val edadStr = ageField.text.toString().trim()

            if (usuario.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || edadStr.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos ❌", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val edad = edadStr.toIntOrNull() ?: run {
                Toast.makeText(this, "Edad inválida ❌", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevoUsuario = Usuario(correo, usuario, contrasena, nombre, apellido, edad)

            progressBar.visibility = View.VISIBLE
            btnRegister.isEnabled = false

            RetrofitClient.instance.registrarUsuario(nuevoUsuario).enqueue(object : Callback<UsuarioResponse> {
                override fun onResponse(call: Call<UsuarioResponse>, response: Response<UsuarioResponse>) {
                    progressBar.visibility = View.GONE
                    btnRegister.isEnabled = true

                    if (response.isSuccessful && response.body() != null) {
                        val respuesta = response.body()!!
                        Toast.makeText(this@RegisterActivity, respuesta.mensaje, Toast.LENGTH_SHORT).show()

                        if (respuesta.exito) {
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            errorTextView.text = respuesta.mensaje
                            errorTextView.visibility = View.VISIBLE
                        }
                    } else {
                        errorTextView.text = "Error inesperado en la API ❌"
                        errorTextView.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    btnRegister.isEnabled = true
                    errorTextView.text = "❌ Error de conexión: ${t.message}"
                    errorTextView.visibility = View.VISIBLE
                }
            })
        }
    }
}
