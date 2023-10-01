package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class NewCommunityActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var communityAdapter: CommunityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_community)

        val recyclerView = findViewById<RecyclerView>(R.id.communityRecyclerView)

        communityAdapter = CommunityAdapter(
            mutableListOf(),
            this::followCommunity,
            this::deleteCommunity
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = communityAdapter

        retrieveCommunityData()
    }

    private fun retrieveCommunityData() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            val userCommunitiesCollection = db.collection("user_communities").document(userId).collection("communities")

            userCommunitiesCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    val communityList = mutableListOf<Card>()

                    for (document in querySnapshot.documents) {
                        val imageUrl = document["imageUrl"] as String
                        val title = document["title"] as String
                        val description = document["description"] as String
                        val card = Card(Uri.parse(imageUrl), title, description)
                        communityList.add(card)
                    }

                    communityAdapter.setData(communityList)
                }
                .addOnFailureListener { exception ->
                    // Handle any errors when retrieving data
                }
        }
    }



    private fun followCommunity(card: Card) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid

            // Define the Firestore collection for user-specific communities
            val userCommunitiesCollection = db.collection("user_followed_cards").document(userId).collection("followed_cards")

            // Create a map of data to upload to the user's collection
            val communityData = mapOf(
                "title" to card.title,
                "description" to card.description,
                "imageUrl" to card.imageUrl.toString()
            )

            userCommunitiesCollection.add(communityData)
                .addOnSuccessListener { documentReference ->
                    // Handle success
                    // You may want to show a message that the community is followed
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    // You can display an error message
                }
        } else {
            // Handle the case where there's no authenticated user
        }
    }

    private fun deleteCommunity(card: Card) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid

            // Define the Firestore collection for user-specific communities
            val userCommunitiesCollection = db.collection("user_communities").document(userId).collection("communities")

            // Delete the community document
            userCommunitiesCollection.whereEqualTo("title", card.title)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        document.reference.delete()
                    }
                }
                .addOnFailureListener { e ->
                    // Handle errors
                    // You can display an error message
                }
        }

        // Unfollow the deleted community from user's followed communities
        val userFollowedCommunitiesCollection = db.collection("user_followed_cards").document().collection("followed_cards")
        userFollowedCommunitiesCollection.whereEqualTo("title", card.title)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    document.reference.delete()
                }
            }
            .addOnFailureListener { e ->
                // Handle errors
            }
    }

}
