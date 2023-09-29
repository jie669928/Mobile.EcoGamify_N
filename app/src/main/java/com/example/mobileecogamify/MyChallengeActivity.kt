package com.example.mobileecogamify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyChallengeActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
//    lateinit var createEventButton: androidx.appcompat.widget.AppCompatButton // Add this line
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_challenge)

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

                R.id.community -> {
                    loadFragment(CommunityFragment())
                    true
                }

                R.id.account -> {
                    loadFragment(AccountFragment())
                    true
                }

                else -> false
            }
        }

        // Find the "Create events" button by its ID
        val createEventButton = findViewById<AppCompatButton>(R.id.create_challenge_btn) // Add this line


        // Set an OnClickListener to the "Create events" button
        createEventButton.setOnClickListener {
            // Handle the click to create a new event
            val intent = Intent(this, EventCreationActivity::class.java)
            startActivity(intent)
        }
    }

        private  fun loadFragment(fragment: Fragment){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container,fragment)
            transaction.commit()
        }
    }