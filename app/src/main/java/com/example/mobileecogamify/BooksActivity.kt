package com.example.mobileecogamify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class BooksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        val kids = findViewById<TextView>(R.id.Kids)

        kids.setOnClickListener {
            // Open the details page for event 01
            val intent = Intent(this, BookKidsCategory::class.java)
            startActivity(intent)
        }
    }
}