package com.example.mobileecogamify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeCategoryActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_category)
        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
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
                    // Check if the current fragment is the HomeFragment
                    if (isCurrentFragmentHome()) {
                        // If on the HomeFragment, create a new event
                        createNewEvent()
                    } else {
                        // If not on the HomeFragment, load the HomeFragment
                        loadFragment(HomeFragment())
                    }
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
    }

    private fun isCurrentFragmentHome(): Boolean {
        // Implement the logic to determine if the current fragment is the HomeFragment
        // Return true if it's the HomeFragment, false otherwise
        // For example, you can use the fragment's tag or check its class type
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        return currentFragment is HomeFragment
    }

    private fun createNewEvent() {
        // Implement logic to create a new event
        // This can include starting a new activity or showing a dialog
        // For example, you can start an activity for event creation:
        val intent = Intent(this, MyChallengeActivity::class.java)
        startActivity(intent)
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

}