package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class FollowingActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_following)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch and display followed cards from Firestore
        fetchFollowedCards()
    }

    private fun fetchFollowedCards() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            val userFollowedCardsCollection = db.collection("user_followed_cards").document(userId)
                .collection("followed_cards")

            userFollowedCardsCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    val followedCards = mutableListOf<FollowedCard>()

                    for (document in querySnapshot.documents) {
                        val title = document.getString("title")
                        val description = document.getString("description")
                        if (title != null && description != null) {
                            val card = FollowedCard(title, description)
                            followedCards.add(card)
                        }
                    }

                    // Set up the adapter and populate the RecyclerView
                    val adapter = FollowedCardAdapter(followedCards)
                    recyclerView.adapter = adapter
                }
                .addOnFailureListener { e ->
                    // Handle failure
                }
        }
    }

}
