package com.example.mobileecogamify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class OceanCollectorActivity : AppCompatActivity() {
    private val garbageTotalAmount = IntArray(6)
    private lateinit var garbageAmountTextViews: List<TextView>
    private lateinit var totalGarbageTextView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var handler: Handler
    private var seconds = 0
    private lateinit var bottomNav: BottomNavigationView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocean_collector)
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

        initializeViews()

        handler = Handler(Looper.getMainLooper())
        startTimer()
    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

    private fun initializeViews() {
        garbageAmountTextViews = listOf(
            findViewById(R.id.gerbage1_amount),
            findViewById(R.id.gerbage2_amount),
            findViewById(R.id.gerbage3_amount),
            findViewById(R.id.gerbage4_amount),
            findViewById(R.id.gerbage5_amount),
            findViewById(R.id.gerbage6_amount)
        )

        totalGarbageTextView = findViewById(R.id.total_number_item)
        timerTextView = findViewById(R.id.timerTextView)

        val garbageButtons = arrayOf(
            Pair(R.id.garbage1_click, R.id.ocean_less_1),
            Pair(R.id.garbage2_click, R.id.ocean_less_2),
            Pair(R.id.garbage3_click, R.id.ocean_less_3),
            Pair(R.id.garbage4_click, R.id.ocean_less_4),
            Pair(R.id.garbage5_click, R.id.ocean_less_5),
            Pair(R.id.garbage6_click, R.id.ocean_less_6)
        )

        for (i in 0 until garbageButtons.size) {
            val increaseButton = findViewById<Button>(garbageButtons[i].first)
            val decreaseButton = findViewById<Button>(garbageButtons[i].second)

            increaseButton.setOnClickListener { increaseGarbageTotal(i) }
            decreaseButton.setOnClickListener { decreaseGarbageTotal(i) }
        }

    }

    private fun increaseGarbageTotal(index: Int) {
        garbageTotalAmount[index]++
        updateGarbageDisplay(index)
        calculateTotalGarbage()
    }

    private fun decreaseGarbageTotal(index: Int) {
        if (garbageTotalAmount[index] > 0) {
            garbageTotalAmount[index]--
        } else {
            // If the quantity is already 0, don't decrease further
            garbageTotalAmount[index] = 0
        }
        updateGarbageDisplay(index)
        calculateTotalGarbage()
    }

    private fun updateGarbageDisplay(index: Int) {
        val displayInteger = garbageAmountTextViews[index]
        displayInteger.text = garbageTotalAmount[index].toString()
    }

    private fun calculateTotalGarbage() {
        val totalGarbage = garbageTotalAmount.sum()
        totalGarbageTextView.text = totalGarbage.toString()
    }

    private fun startTimer() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                seconds++
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val secs = seconds % 60

                val timeText = String.format("%02d:%02d:%02d", hours, minutes, secs)
                timerTextView.text = timeText

                // Continue the timer
                handler.postDelayed(this, 1000)
            }
        }, 1000) // 1000 milliseconds = 1 second
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }
}