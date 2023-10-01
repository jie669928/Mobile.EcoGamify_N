package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileHistoryActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private lateinit var recyclerView: RecyclerView
    private lateinit var profileHistoryAdapter: ProfileHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_history)

        recyclerView = findViewById(R.id.profileRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        profileHistoryAdapter = ProfileHistoryAdapter()
        recyclerView.adapter = profileHistoryAdapter

        retrieveProfileHistory()
    }

    private fun retrieveProfileHistory() {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            val userProfilesCollection = db.collection("user_profile").document(userId)
                .collection("profile")

            userProfilesCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    val profileList = mutableListOf<UserProfile>()

                    for (documentSnapshot in querySnapshot) {
                        val name = documentSnapshot.getString("name") ?: ""
                        val email = documentSnapshot.getString("email") ?: ""
                        val gender = documentSnapshot.getString("gender") ?: ""
                        val imageUrl = documentSnapshot.getString("imageUrl") ?:""
                        val profile = UserProfile(name, email, gender, imageUrl)
                        profileList.add(profile)
                    }

                    profileHistoryAdapter.setData(profileList)
                }
                .addOnFailureListener { exception ->
                    // Handle errors
                }
        }
    }
}
