package com.example.inlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class Question3Activity : AppCompatActivity() {
    private lateinit var nextButton: Button
    private lateinit var checkBoxes: List<CheckBox>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question3)

        nextButton = findViewById(R.id.nextButton3)
        checkBoxes = listOf(
            findViewById(R.id.option1_3),
            findViewById(R.id.option2_3),
            findViewById(R.id.option3_3),
            findViewById(R.id.option4_3),
            findViewById(R.id.option5_3),
            findViewById(R.id.option6_3)
        )

        nextButton.visibility = View.GONE

        // Hacer visible el botón si al menos un CheckBox es seleccionado
        for (checkBox in checkBoxes) {
            checkBox.setOnCheckedChangeListener { _, _ ->
                nextButton.visibility = if (checkBoxes.any { it.isChecked }) View.VISIBLE else View.GONE
                nextButton.invalidate()
                nextButton.requestLayout()
                Log.d("Debug", "Botón ahora es visible en la interfaz")
            }
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, Question4Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
