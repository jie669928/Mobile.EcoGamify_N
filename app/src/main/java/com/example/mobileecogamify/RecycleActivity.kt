package com.example.mobileecogamify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.widget.AppCompatButton

class RecycleActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle)
        bottomNav = findViewById(R.id.bottomNav)

// Select the "Home" menu item by default
        bottomNav.selectedItemId = R.id.home
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.search -> {
//                    loadFragment(SearchFragment())
                    val intent = Intent(this, EventActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.education -> {
                    loadFragment(EducationFragment())
                    true
                }
                R.id.home -> {
//                    // Create an Intent to launch the HomeCategoryActivity
//                    val intent = Intent(this, HomeCategoryActivity::class.java)
//                    startActivity(intent)
                    loadFragment(HomeFragment())
                    true
                }
                R.id.community ->{
                    loadFragment(CommunityFragment())
                    true
                }
                R.id.account ->{
                    loadFragment(AccountFragment())
                    true
                }

                else -> false
            }
        }

        // Add OnClickListener to event buttons
        val event01Button = findViewById<AppCompatButton>(R.id.recycle_event01)
        val event02Button = findViewById<AppCompatButton>(R.id.recycle_event02)

        event01Button.setOnClickListener {
            // Open the details page for event 01
            val intent = Intent(this, R1DetailsActivity::class.java)
            startActivity(intent)
        }

        event02Button.setOnClickListener {
            // Open the details page for event 02
            val intent = Intent(this, R2DetailsActivity::class.java)
            startActivity(intent)
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}