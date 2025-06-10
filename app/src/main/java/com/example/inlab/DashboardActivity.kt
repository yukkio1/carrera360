package com.example.inlab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button


class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val cvButton = findViewById<Button>(R.id.cvButton)
        val salaryButton = findViewById<Button>(R.id.salaryButton)

        cvButton.setOnClickListener {
            startActivity(Intent(this, CVActivity::class.java))
        }

        salaryButton.setOnClickListener {
            startActivity(Intent(this, SalaryActivity::class.java))
        }

    }
}
