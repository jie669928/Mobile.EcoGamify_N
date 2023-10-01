package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Button
import android.widget.Toast

class HotCommunityActivity : AppCompatActivity() {
    private var currentUser: FirebaseUser? = null
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hot_community)

        currentUser = FirebaseAuth.getInstance().currentUser
        firestore = FirebaseFirestore.getInstance()

        // Create separate variables for follow state for each card
        var isFollowed1 = false
        var isFollowed2 = false
        var isFollowed3 = false

        val followButton1 = findViewById<Button>(R.id.followButton01)
        val followButton2 = findViewById<Button>(R.id.followButton02)
        val followButton3 = findViewById<Button>(R.id.followButton03)

        followButton1.setOnClickListener {
            val cardTitle = "Power Cycling"

            if (isFollowed1) {
                // Handle unfollowing logic if needed
                isFollowed1 = false
                followButton1.text = "Follow"
                showToast("Unfollowed")
                unfollowCard(cardTitle)
            } else {
                isFollowed1 = true
                followButton1.text = "Followed"
                showToast("Followed")
                followCard(cardTitle, "Riding Green, Planting Roots – Ride sustainably, plant native species, and contribute to a healthier environment.")
            }
        }

        followButton2.setOnClickListener {
            val cardTitle = "Beach Cleaning"

            if (isFollowed2) {
                isFollowed2 = false
                followButton2.text = "Follow"
                showToast("Unfollowed")
                unfollowCard(cardTitle)
            } else {
                isFollowed2 = true
                followButton2.text = "Followed"
                showToast("Followed")
                followCard(cardTitle, "Riding Green, Planting Roots – Ride sustainably, plant native species, and contribute to a healthier environment.")
            }
        }

        followButton3.setOnClickListener {
            val cardTitle = "Planting"

            if (isFollowed3) {
                isFollowed3 = false
                followButton3.text = "Follow"
                showToast("Unfollowed")
                unfollowCard(cardTitle)
            } else {
                isFollowed3 = true
                followButton3.text = "Followed"
                showToast("Followed")
                followCard(cardTitle, "Riding Green, Planting Roots – Ride sustainably, plant native species, and contribute to a healthier environment.")
            }
        }

        val followingButton = findViewById<Button>(R.id.followingButton0)

        followingButton.setOnClickListener {
            // Create an Intent to navigate to the FollowingActivity
            val intent = Intent(this, FollowingActivity::class.java)

            // Start the FollowingActivity
            startActivity(intent)
        }

        val createCardButton = findViewById<Button>(R.id.createCardButton)

        createCardButton.setOnClickListener {
            // Create an Intent to navigate to the FollowingActivity
            val intent = Intent(this, AddCommunityActivity::class.java)

            // Start the FollowingActivity
            startActivity(intent)
        }

        val myCommunityButton = findViewById<Button>(R.id.myCommunityButton)

        myCommunityButton.setOnClickListener {
            val intent = Intent(this, NewCommunityActivity::class.java)
            // Add any extra data you want to pass to the NewCommunityActivity here
            startActivity(intent)
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun followCard(cardTitle: String, cardDescription: String) {
        val user = currentUser
        if (user != null) {
            val userId = user.uid

            val userFollowedCardsCollection = firestore.collection("user_followed_cards").document(userId)
                .collection("followed_cards")

            val card = hashMapOf(
                "title" to cardTitle,
                "description" to cardDescription,
                "userId" to userId
            )

            userFollowedCardsCollection.add(card)
                .addOnSuccessListener { documentReference ->
                    // Handle success
                }
                .addOnFailureListener { e ->
                    // Handle failure
                }
        } else {
            // Handle the case where the user is not authenticated
        }
    }

    private fun unfollowCard(cardTitle: String) {
        val user = currentUser
        if (user != null) {
            val userId = user.uid

            val userFollowedCardsCollection = firestore.collection("user_followed_cards").document(userId)
                .collection("followed_cards")

            userFollowedCardsCollection
                .whereEqualTo("title", cardTitle)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        document.reference.delete()
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                }
        } else {
            // Handle the case where the user is not authenticated
        }
    }

}