package com.example.mobileecogamify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class BookKidsCategory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_kids_category)

        val kidbook1 = findViewById<ImageButton>(R.id.kidbook1)


        kidbook1.setOnClickListener {
            // Open the details page for event 01
            val intent = Intent(this, KidBook1Activity::class.java)
            startActivity(intent)
        }
    }
}