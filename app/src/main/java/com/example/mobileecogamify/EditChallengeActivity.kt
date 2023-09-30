package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore

class EditChallengeActivity : AppCompatActivity() {
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_challenge)

        // Retrieve challenge data from intent
        val challengeData = intent.getParcelableExtra<ChallengeData>("challengeData")

        // Populate UI elements with challenge data
        val titleEditText = findViewById<EditText>(R.id.editTitleEditText)
        val descriptionEditText = findViewById<EditText>(R.id.editDescriptionEditText)

        titleEditText.setText(challengeData?.title)
        descriptionEditText.setText(challengeData?.description)

        // Handle save button click
        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            // Get updated data from UI elements
            val updatedTitle = titleEditText.text.toString()
            val updatedDescription = descriptionEditText.text.toString()

            // Update the challenge data in Firestore
            challengeData?.let { challenge ->
                updateChallengeInFirestore(challenge, updatedTitle, updatedDescription)
            }

            // Return to the main activity
            finish()
        }
    }

    private fun updateChallengeInFirestore(
        challenge: ChallengeData,
        updatedTitle: String,
        updatedDescription: String
    ) {
        val challengesCollection = firestore.collection("events")

        // Create a map to update the title and description
        val updatedData = mapOf(
            "title" to updatedTitle,
            "description" to updatedDescription
        )

        challengesCollection.document(challenge.id)
            .update(updatedData)
            .addOnSuccessListener {
                // Update successful
            }
            .addOnFailureListener { exception ->
                // Handle update failure
            }
    }
}
