package com.example.inlab

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.inlab.apilogin.LoginRequest
import com.example.inlab.apilogin.LoginResponse
import com.example.inlab.apilogin.ApiService
import com.example.inlab.apilogin.RetrofitClient
import com.example.inlab.apiinvitado.ApiInvitadoResponse
import com.example.inlab.apiinvitado.ApiInvitadoService
import com.example.inlab.apiinvitado.RetrofitClientInvitado
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var guestButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameField = findViewById(R.id.usernameField)
        passwordField = findViewById(R.id.passwordField)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        guestButton = findViewById(R.id.guestButton)
        progressBar = findViewById(R.id.progressBar)
        errorTextView = findViewById(R.id.errorTextView)

        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        loginButton.setOnClickListener {
            val usuario = usernameField.text.toString().trim()
            val contrasena = passwordField.text.toString().trim()

            if (usuario.isNotEmpty() && contrasena.isNotEmpty()) {
                loginButton.isEnabled = false
                iniciarSesion(usuario, contrasena)
            } else {
                Toast.makeText(this, "Completa todos los campos ❌", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        guestButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            guestButton.isEnabled = false

            val apiService = RetrofitClientInvitado.instance.create(ApiInvitadoService::class.java)
            apiService.crearInvitado().enqueue(object : Callback<ApiInvitadoResponse> {
                override fun onResponse(call: Call<ApiInvitadoResponse>, response: Response<ApiInvitadoResponse>) {
                    progressBar.visibility = View.GONE
                    guestButton.isEnabled = true

                    if (response.isSuccessful && response.body() != null) {
                        val usuarioInvitado = response.body()!!
                        guardarUsuario("idUsuarioInvitado", usuarioInvitado.idUsuario)
                        redirigirAQuestion(
                            usuarioInvitado.usuario,
                            usuarioInvitado.idUsuario,
                            usuarioInvitado.nombre,
                            usuarioInvitado.apellido,
                            usuarioInvitado.edad,
                            usuarioInvitado.correo,
                            usuarioInvitado.fechaCreacion
                        )
                    } else {
                        mostrarError("Error al crear invitado")
                    }
                }

                override fun onFailure(call: Call<ApiInvitadoResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    guestButton.isEnabled = true
                    mostrarError("Error de conexión: ${t.message}")
                }
            })
        }
    }

    private fun iniciarSesion(usuario: String, contrasena: String) {
        val request = LoginRequest(usuario, contrasena)
        val apiService = RetrofitClient.instance.create(ApiService::class.java)

        apiService.loginUsuario(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                loginButton.isEnabled = true
                if (response.isSuccessful && response.body() != null) {
                    val usuarioLogueado = response.body()!!
                    guardarUsuario("idUsuarioLogueado", usuarioLogueado.idUsuario)
                    redirigirAQuestion(
                        usuarioLogueado.usuario,
                        usuarioLogueado.idUsuario,
                        usuarioLogueado.nombre,
                        usuarioLogueado.apellido,
                        usuarioLogueado.edad,
                        usuarioLogueado.correo,
                        usuarioLogueado.fechaCreacion
                    )
                } else {
                    mostrarError("Usuario o contraseña incorrectos")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginButton.isEnabled = true
                mostrarError("Error de conexión: ${t.message}")
            }
        })
    }

    private fun guardarUsuario(clave: String, idUsuario: Int) {
        getSharedPreferences("UserData", MODE_PRIVATE).edit().putInt(clave, idUsuario).apply()
        Log.e("LoginAPI", "📌 ID guardado en SharedPreferences: $idUsuario")
    }

    private fun redirigirAQuestion(usuario: String, idUsuario: Int, nombre: String, apellido: String, edad: Int, correo: String, fechaCreacion: String) {
        val intent = Intent(this, Question1Activity::class.java).apply {
            putExtra("usuarioLogueado", usuario)
            putExtra("nombre", nombre)
            putExtra("apellido", apellido)
            putExtra("edad", edad)
            putExtra("correo", correo)
            putExtra("idUsuarioLogueado", idUsuario)
            putExtra("fechaCreacion", fechaCreacion)
        }
        Log.e("LoginAPI", "🚀 Pasando datos completos a `Question1Activity.kt`")
        startActivity(intent)
        finish()
    }

    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        Log.e("LoginAPI", "❌ $mensaje")
    }
}
