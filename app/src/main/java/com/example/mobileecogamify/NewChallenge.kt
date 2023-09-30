//package com.example.mobileecogamify

//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.QuerySnapshot
//
//class NewChallenge : AppCompatActivity() {
//    private val firestore = FirebaseFirestore.getInstance()
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: NewChallengeAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_new_challenge)
//
//        recyclerView = findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        // Create an empty adapter initially and set it to the RecyclerView
//        val adapter = NewChallengeAdapter(emptyList()) { challenge ->
//            // Handle the delete button click for a specific challenge
//            deleteChallenge(challenge)
//        }
//        recyclerView.adapter = adapter
//
//        // Set up the Firestore listener for real-time updates
//        setUpFirestoreListener()
//    }

//        private fun deleteChallenge(challenge: ChallengeData) {
//        val challengesCollection = firestore.collection("events")
//
//        // Use Firestore's delete method to delete the document by its ID
//        challengesCollection.document(challenge.id)
//            .delete()
//            .addOnSuccessListener {
//                // Challenge deleted successfully
//                // No need to update the adapter here, it will be updated in real-time
//                Toast.makeText(this, "Challenge deleted successfully.", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener { exception ->
//                // Handle delete failure and show an error message to the user
//                Toast.makeText(
//                    this,
//                    "Error deleting challenge: ${exception.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//    }
//
//    private fun setUpFirestoreListener() {
//        val challengesCollection = firestore.collection("events")
//
//        challengesCollection.addSnapshotListener { querySnapshot, _ ->
//            if (querySnapshot != null) {
//                val challengeList = mutableListOf<ChallengeData>()
//                for (document in querySnapshot.documents) {
//                    val title = document.getString("title") ?: ""
//                    val id = document.id // Get the document ID
//                    val timeHour = document.getLong("timeHour")?.toInt() ?: 0
//                    val timeMinute = document.getLong("timeMinute")?.toInt() ?: 0
//                    val description = document.getString("description") ?: ""
//                    val eventType = document.getString("eventType") ?: ""
//                    val imageUrl = document.getString("imageUrl") ?: ""
//
//                    val challenge = ChallengeData(
//                        id, // Pass the document ID
//                        title,
//                        timeHour,
//                        timeMinute,
//                        description,
//                        eventType,
//                        imageUrl
//                    )
//
//                    challengeList.add(challenge)
//                }
//
//                // Update the adapter with the new data
//                adapter.updateData(challengeList)
//            }
//        }
//    }
//}
package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class NewChallenge : AppCompatActivity() {
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NewChallengeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_challenge)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create an empty adapter initially and set it to the RecyclerView
        adapter = NewChallengeAdapter(emptyList()) { challenge ->
            // Handle the delete button click for a specific challenge
            deleteChallenge(challenge)
        }
        recyclerView.adapter = adapter

        // Set up the Firestore listener for real-time updates
        setUpFirestoreListener()
    }

    private fun deleteChallenge(challenge: ChallengeData) {
        val challengesCollection = firestore.collection("events")

        // Use Firestore's delete method to delete the document by its ID
        challengesCollection.document(challenge.id)
            .delete()
            .addOnSuccessListener {
                // Challenge deleted successfully
                // No need to update the adapter here, it will be updated in real-time
                Toast.makeText(this, "Challenge deleted successfully.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                // Handle delete failure and show an error message to the user
                Toast.makeText(
                    this,
                    "Error deleting challenge: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun setUpFirestoreListener() {
        val challengesCollection = firestore.collection("events")

        challengesCollection.addSnapshotListener { querySnapshot, _ ->
            if (querySnapshot != null) {
                val challengeList = mutableListOf<ChallengeData>()
                for (document in querySnapshot.documents) {
                    val title = document.getString("title") ?: ""
                    val id = document.id // Get the document ID
                    val timeHour = document.getLong("timeHour")?.toInt() ?: 0
                    val timeMinute = document.getLong("timeMinute")?.toInt() ?: 0
                    val description = document.getString("description") ?: ""
                    val eventType = document.getString("eventType") ?: ""
                    val imageUrl = document.getString("imageUrl") ?: ""

                    val challenge = ChallengeData(
                        id, // Pass the document ID
                        title,
                        timeHour,
                        timeMinute,
                        description,
                        eventType,
                        imageUrl
                    )

                    challengeList.add(challenge)
                }

                // Update the adapter with the new data
                adapter.updateData(challengeList)
            }
        }
    }
}
