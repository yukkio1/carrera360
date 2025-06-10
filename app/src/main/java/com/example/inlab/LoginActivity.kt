package com.example.inlab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameField = findViewById<EditText>(R.id.usernameField)
        val passwordField = findViewById<EditText>(R.id.passwordField)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val guestButton = findViewById<Button>(R.id.guestButton)
        val termsConditions = findViewById<TextView>(R.id.termsConditions)

        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                startActivity(Intent(this, Question1Activity::class.java)) // Ahora dirige al cuestionario
            } else {
                usernameField.error = "Ingresa tu usuario"
                passwordField.error = "Ingresa tu contraseña"
            }
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        guestButton.setOnClickListener {
            startActivity(Intent(this, Question1Activity::class.java)) // Ahora dirige al cuestionario
        }

        termsConditions.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://tusitio.com/terminos"))
            startActivity(intent)
        }

        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}


