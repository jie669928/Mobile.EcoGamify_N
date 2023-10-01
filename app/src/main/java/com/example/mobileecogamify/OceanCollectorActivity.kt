package com.example.mobileecogamify

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore

class OceanCollectorActivity : AppCompatActivity() {

    private val garbageTotalAmount = IntArray(6)
    private lateinit var garbageAmountTextViews: List<TextView>
    private lateinit var totalGarbageTextView: TextView
    private lateinit var timerTextView: TextView
    private lateinit var handler: Handler
    private var seconds = 0
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocean_collector)

        initializeViews()

        handler = Handler(Looper.getMainLooper())
        startTimer()
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

        // Set click event listener for the "Submit" button
        val submitButton = findViewById<Button>(R.id.submit_garbage_result)
        // Call this code after clicking the "Submit" button
        submitButton.setOnClickListener {
            // Create an AlertDialog.Builder
            AlertDialog.Builder(this)
                .setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit the data?")
                .setPositiveButton("Sure") { dialog, which ->
                    // Get the total garbage and time
                    val totalGarbage = totalGarbageTextView.text.toString()
                    val currentTime = timerTextView.text.toString()

                    // Create a HashMap containing garbage collection data
                    val data = hashMapOf(
                        "totalGarbage" to totalGarbage,
                        "currentTime" to currentTime
                    )

                    // Upload the data to Firebase Firestore
                    db.collection("ocean_event_data")
                        .add(data)
                        .addOnSuccessListener { documentReference ->
                            // Data added successfully
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            // Handle success
                        }
                        .addOnFailureListener { e ->
                            // Handle failure, log the exception
                            Log.e(TAG, "Error adding document", e)
                        }

                    // Create an intent to start the CertificationActivity
                    val intent = Intent(this, CertificationActivity::class.java)
                    // Attach data to the intent
                    intent.putExtra("totalGarbage", totalGarbage)
                    intent.putExtra("currentTime", currentTime)
                    // Start the CertificationActivity
                    startActivity(intent)

                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    // Close the dialog without performing submission
                    dialog.dismiss()
                }
                .create()
                .show()
        }


        // Set click event listener for the "Clear" button
        val clearButton = findViewById<Button>(R.id.clear_btn)
        clearButton.setOnClickListener {
            // Create an AlertDialog.Builder
            AlertDialog.Builder(this)
                .setTitle("Confirm Clear")
                .setMessage("Are you sure you want to clear the data?")
                .setPositiveButton("Sure") { dialog, which ->
                    // Perform data clearing operation here, e.g., reset TextView texts
                    for (i in garbageTotalAmount.indices) {
                        garbageTotalAmount[i] = 0
                        updateGarbageDisplay(i)
                    }
                    calculateTotalGarbage()

                    // Reset the timer to "00:00:00"
                    seconds = 0
                    timerTextView.text = "00:00:00"

                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    // Close the dialog without performing data clearing operation
                    dialog.dismiss()
                }
                .create()
                .show()
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