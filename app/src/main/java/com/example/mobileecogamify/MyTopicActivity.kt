package com.example.mobileecogamify

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage

class MyTopicActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TopicAdapter
    private lateinit var topicDeletedText: TextView
    private val db = FirebaseFirestore.getInstance() // Reference to Firestore
    private val storage = FirebaseStorage.getInstance() // Reference to Firebase Storage
    private val auth = FirebaseAuth.getInstance() // Firebase Authentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_topic)

        recyclerView = findViewById(R.id.recyclerViewTopics)
        topicDeletedText = findViewById(R.id.textViewTopicCreated) // Updated view ID

        // Set up RecyclerView and adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch topics from Firestore and display them
        fetchTopicsFromFirestore()

        // Check if a topic was deleted and show the "Topic Deleted" message
        val topicDeleted = intent.getBooleanExtra("topicDeleted", false)
        if (topicDeleted) {
            topicDeletedText.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        // Hide the "Topic Deleted" message when the activity is resumed (re-entered)
        topicDeletedText.visibility = View.GONE
    }

    private fun fetchTopicsFromFirestore() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            db.collection("user_topic")
                .document(userId)
                .collection("topic")
                .get()
                .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                    val topics = ArrayList<Topic>()

                    for (document in querySnapshot.documents) {
                        val title = document.getString("title") ?: ""
                        val topicId = document.id // Get the unique document ID
                        val imageUrl = document.getString("imageUrl") ?: ""
                        topics.add(Topic(topicId, title, imageUrl))
                    }

                    // Initialize the adapter
                    adapter = TopicAdapter(topics) { position ->
                        // Handle topic deletion
                        val topic = topics[position]
                        deleteTopicFromFirestore(topic.topicId) // Pass the topicId for deletion
                        deleteImageFromStorage(topic.imageUrl)
                    }
                    recyclerView.adapter = adapter
                }
                .addOnFailureListener { e ->
                    // Handle the failure to fetch topics
                }
        }
    }

    private fun deleteTopicFromFirestore(topicId: String) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            db.collection("user_topic")
                .document(userId)
                .collection("topic")
                .document(topicId)
                .delete()
                .addOnSuccessListener {
                    // Topic deleted successfully
                    showToast("Topic deleted successfully")
                }
                .addOnFailureListener { e ->
                    // Handle the failure to delete the topic
                    showToast("Failed to delete the topic")
                }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun deleteImageFromStorage(imageUrl: String?) {
        if (imageUrl != null) {
            // Extract the image file name from the imageUrl and delete it from Firebase Storage
            val fileName = imageUrl.substringAfterLast("/")
            val storageRef = storage.reference.child("topic_images/$fileName")
            storageRef.delete()
                .addOnSuccessListener {
                    // Handle successful image deletion
                }
                .addOnFailureListener { e ->
                    // Handle the failure to delete the image
                }
        } else {
            // Handle the case where imageUrl is null (no image to delete)
        }
    }

}
