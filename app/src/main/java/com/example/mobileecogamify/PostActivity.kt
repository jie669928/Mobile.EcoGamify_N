package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileecogamify.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class PostActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter
    private lateinit var postCreatedText: TextView
    private val db = FirebaseFirestore.getInstance() // Reference to Firestore
    private val storage = FirebaseStorage.getInstance() // Reference to Firebase Storage
    private val auth = FirebaseAuth.getInstance() // Firebase Authentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        recyclerView = findViewById(R.id.recyclerViewPosts)
        postCreatedText = findViewById(R.id.textViewPostCreated)

        // Set up RecyclerView and adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch posts from Firestore and display them
        fetchPostsFromFirestore()

        // Check if a new post was created and show the "Post Created" message
        val postCreated = intent.getBooleanExtra("postCreated", false)
        if (postCreated) {
            postCreatedText.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        // Hide the "Post Created" message when the activity is resumed (re-entered)
        postCreatedText.visibility = View.GONE
    }

    private fun fetchPostsFromFirestore() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            db.collection("user_posts")
                .document(userId)
                .collection("posts")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val posts = ArrayList<Post>()

                    for (document in querySnapshot.documents) {
                        val postId = document.id // Get the unique document ID
                        val content = document.getString("content") ?: ""
                        val imageUrl = document.getString("imageUrl") ?: ""
                        posts.add(Post(postId, content, imageUrl))
                    }

                    // Initialize the adapter with the retrieved posts
                    adapter = PostAdapter(posts) { position ->
                        // Handle post deletion
                        val post = posts[position]
                        deletePostFromFirestore(post.postId) // Pass the postId for deletion
                        deleteImageFromStorage(post.imageUrl)
                    }
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = adapter
                }
                .addOnFailureListener { e ->
                    // Handle the failure to fetch posts
                }
        }
    }



    private fun deletePostFromFirestore(postId: String) {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            db.collection("user_posts")
                .document(userId)
                .collection("posts")
                .document(postId)
                .delete()
                .addOnSuccessListener {
                    // Post deleted successfully
                    showToast("Post deleted successfully")
                }
                .addOnFailureListener { e ->
                    // Handle the failure to delete the post
                    showToast("Failed to delete the post")
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
            val storageRef = storage.reference.child("post_images/$fileName")
            storageRef.delete()
                .addOnSuccessListener {
                    // Handle successful image deletion
                }
                .addOnFailureListener { e ->
                    // Handle the failure to delete the image
                }
        }
    }
}
