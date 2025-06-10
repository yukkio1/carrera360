package com.example.inlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class Question1Activity : AppCompatActivity() {
    private lateinit var nextButton: Button
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question1)

        nextButton = findViewById(R.id.nextButton1)
        radioGroup = findViewById(R.id.radioGroup1)

        nextButton.visibility = View.GONE

        // Detectar selección en el RadioGroup para hacer visible el botón
        radioGroup.setOnCheckedChangeListener { _, _ ->
            nextButton.visibility = View.VISIBLE
            nextButton.invalidate()
            nextButton.requestLayout()
            Log.d("Debug", "Botón ahora es visible en la interfaz")
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, Question2Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
