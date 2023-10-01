package com.example.mobileecogamify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class InformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        val GGBtn = findViewById<TextView>(R.id.GreenGlossory)

        GGBtn.setOnClickListener {
            // Open the details page for event 01
            val intent = Intent(this, GreenGlossoryActivity::class.java)
            startActivity(intent)
        }
    }
}