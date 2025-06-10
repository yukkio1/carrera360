package com.example.inlab

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class Question5Activity : AppCompatActivity() {
    private lateinit var finishButton: Button
    private lateinit var checkBoxes: List<CheckBox>
    private val selectedPriorities = mutableListOf<CheckBox>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question5)

        finishButton = findViewById(R.id.finishButton)
        checkBoxes = listOf(
            findViewById(R.id.option1_5),
            findViewById(R.id.option2_5),
            findViewById(R.id.option3_5),
            findViewById(R.id.option4_5),
            findViewById(R.id.option5_5)
        )

        finishButton.visibility = View.GONE

        for (checkBox in checkBoxes) {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (selectedPriorities.size < 5) {
                        selectedPriorities.add(checkBox)
                    } else {
                        checkBox.isChecked = false // Evitar más de 5 selecciones
                    }
                } else {
                    selectedPriorities.remove(checkBox)
                }

                updatePriorityNumbers()
                finishButton.visibility = if (selectedPriorities.size == 5) View.VISIBLE else View.GONE
            }
        }

        finishButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updatePriorityNumbers() {
        selectedPriorities.forEachIndexed { index, checkBox ->
            checkBox.text = "${index + 1}. ${checkBox.text.toString().substringAfter(". ")}"
        }

        checkBoxes.forEach { checkBox ->
            if (!selectedPriorities.contains(checkBox)) {
                checkBox.text = checkBox.text.toString().substringAfter(". ") // Quitar número si está desmarcado
            }
        }
    }
}
