package com.example.mobileecogamify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeCategoryActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    lateinit var fab: FloatingActionButton

    // Track the current visibility state of the FAB
    private var isFabVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_category)
        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        fab = findViewById(R.id.floatingActionButton) as FloatingActionButton
        // Select the "Home" menu item by default
        bottomNav.selectedItemId = R.id.home
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search -> {
//                    val intent = Intent(this, EventActivity::class.java)
//                    startActivity(intent)
//                    true
                    loadFragment(SearchFragment())
                    // Hide the FAB when navigating to other fragments
                    toggleFabVisibility(false)
                    true
                }
                R.id.education -> {
                    loadFragment(EducationFragment())
                    // Hide the FAB when navigating to other fragments
                    toggleFabVisibility(false)
                    true
                }
                R.id.home -> {
                    // Check if the current fragment is the HomeFragment
                    if (!isCurrentFragmentHome()) {
                        // If not on the HomeFragment, load the HomeFragment
                        loadFragment(HomeFragment())
                    } else {
                        // Toggle the visibility of the FAB when the Home button is clicked
                        toggleFabVisibility(!isFabVisible)
                    }
                    true
                }
                R.id.community -> {
                    loadFragment(CommunityFragment())
                    // Hide the FAB when navigating to other fragments
                    toggleFabVisibility(false)
                    true
                }
                R.id.account -> {
                    loadFragment(AccountFragment())
                    // Hide the FAB when navigating to other fragments
                    toggleFabVisibility(false)
                    true
                }
                else -> false
            }
        }

        fab.setOnClickListener {
            // Handle the click event for the FloatingActionButton (createNewEvent)
            createNewEvent()
        }
    }

    private fun isCurrentFragmentHome(): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        return currentFragment is HomeFragment
    }

    private fun toggleFabVisibility(visible: Boolean) {
        // Toggle the visibility of the FAB based on the specified value
        if (visible) {
            fab.visibility = View.VISIBLE
        } else {
            fab.visibility = View.INVISIBLE
        }
        isFabVisible = visible // Update the state
    }

    private fun createNewEvent() {
        val intent = Intent(this, EventCreationActivity::class.java)
        startActivity(intent)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
