package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class B2DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b2_details)
        val joinButton = findViewById<AppCompatButton>(R.id.join_btn)

        joinButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                // Show a toast message when the button is clicked
                Toast.makeText(applicationContext, "Joined", Toast.LENGTH_SHORT).show()

                // Save the record to a history activity (you need to implement this part)
                // For example, you can start a new activity to show the history or store the data in a database.
            }
        })
    }
}
