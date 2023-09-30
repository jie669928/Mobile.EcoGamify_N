package com.example.mobileecogamify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.widget.AppCompatButton

class PlantNativeActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_native)
//        bottomNav = findViewById(R.id.bottomNav)
//
//// Select the "Home" menu item by default
//        bottomNav.selectedItemId = R.id.home
//        bottomNav.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.search -> {
////                    loadFragment(SearchFragment())
//                    val intent = Intent(this, EventActivity::class.java)
//                    startActivity(intent)
//                    true
//                }
//                R.id.education -> {
//                    loadFragment(EducationFragment())
//                    true
//                }
//                R.id.home -> {
////                    // Create an Intent to launch the HomeCategoryActivity
////                    val intent = Intent(this, HomeCategoryActivity::class.java)
////                    startActivity(intent)
//                    loadFragment(HomeFragment())
//                    true
//                }
//                R.id.community ->{
//                    loadFragment(CommunityFragment())
//                    true
//                }
//                R.id.account ->{
//                    loadFragment(AccountFragment())
//                    true
//                }
//
//                else -> false
//            }
//        }

        // Add OnClickListener to event buttons
        val event01Button = findViewById<AppCompatButton>(R.id.plant_event01)
        val event02Button = findViewById<AppCompatButton>(R.id.plant_event02)
        val event03Button = findViewById<AppCompatButton>(R.id.plant_event03)
        val event04Button = findViewById<AppCompatButton>(R.id.plant_event04)

        event01Button.setOnClickListener {
            // Open the details page for event 01
            val intent = Intent(this, Pn1DetailsActivity::class.java)
            startActivity(intent)
        }

        event02Button.setOnClickListener {
            // Open the details page for event 02
            val intent = Intent(this, Pn2DetailsActivity::class.java)
            startActivity(intent)
        }

        event03Button.setOnClickListener {
            // Open the details page for event 03
            val intent = Intent(this, Pn3DetailsActivity::class.java)
            startActivity(intent)
        }

        event04Button.setOnClickListener {
            // Open the details page for event 04
            val intent = Intent(this, Pn4DetailsActivity::class.java)
            startActivity(intent)
        }
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }
}