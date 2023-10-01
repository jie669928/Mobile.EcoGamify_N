package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Edit_profiles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profiles)

        val backToProfiles = findViewById<ImageButton>(R.id.upBtnProfiles)
        backToProfiles.setOnClickListener {
            // When the back button is clicked, close the EditPage activity and return to MainActivity
            finish() // Close the current activity and return to the previous activity (i.e., MainActivity)
        }

    }
}