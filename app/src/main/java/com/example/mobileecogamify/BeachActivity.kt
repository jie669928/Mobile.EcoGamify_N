package com.example.mobileecogamify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.widget.AppCompatButton


class BeachActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beach)

        // Enable the "Up" button in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Add OnClickListener to event buttons
        val event01Button = findViewById<AppCompatButton>(R.id.beach_event01)
        val event02Button = findViewById<AppCompatButton>(R.id.beach_event02)

        event01Button.setOnClickListener {
            // Open the details page for event 01
            val intent = Intent(this, B1DetailsActivity::class.java)
            startActivity(intent)
        }

        event02Button.setOnClickListener {
            // Open the details page for event 02
            val intent = Intent(this, B2DetailsActivity::class.java)
            startActivity(intent)
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}