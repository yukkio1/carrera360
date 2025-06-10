package com.example.inlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class Question4Activity : AppCompatActivity() {
    private lateinit var nextButton: Button
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question4)

        nextButton = findViewById(R.id.nextButton4)
        radioGroup = findViewById(R.id.radioGroup4)

        nextButton.visibility = View.GONE

        // Hacer visible el botón si el usuario selecciona una opción
        radioGroup.setOnCheckedChangeListener { _, _ ->
            nextButton.visibility = View.VISIBLE
            nextButton.invalidate()
            nextButton.requestLayout()
            Log.d("Debug", "Botón ahora es visible en la interfaz")
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, Question5Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
