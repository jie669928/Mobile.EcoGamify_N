package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
class CreatePostActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null
    private var storageRef: StorageReference? = null // Reference to Firebase Storage
    private val db = FirebaseFirestore.getInstance() // Define the Firestore reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val postEditText = findViewById<EditText>(R.id.editTextPost)
        val createPostButton = findViewById<Button>(R.id.createPostButton)
        val uploadImageButton = findViewById<Button>(R.id.uploadImageButton)

        // Initialize Firebase Storage
        storageRef = FirebaseStorage.getInstance().reference

        // Handle image upload button click
        uploadImageButton.setOnClickListener {
            openImageChooser()
        }

        // Handle create post button click
        createPostButton.setOnClickListener {
            val postContent = postEditText.text.toString()

            if (postContent.isNotEmpty()) {
                if (selectedImageUri != null) {
                    // If an image is selected, upload it to Firebase Storage
                    uploadImageToStorage(postContent)
                } else {
                    // If no image is selected, save the post without an image
                    savePostToFirestore(postContent, null)
                }
            } else {
                Toast.makeText(this, "Please enter post content.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImageToStorage(postContent: String) {
        val imageRef = storageRef?.child("post_images/${UUID.randomUUID()}")

        imageRef?.putFile(selectedImageUri!!)
            ?.addOnSuccessListener { taskSnapshot ->
                // Handle the successful image upload here
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri // Get the Uri of the uploaded image
                    savePostToFirestore(postContent, imageUrl.toString())
                }.addOnFailureListener { e ->
                    // Handle the failure to get the download URL
                }
            }?.addOnFailureListener { e ->
                // Handle the failure to upload the image
                Toast.makeText(this, "Image upload failed. Please try again.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun savePostToFirestore(postContent: String, imageUrl: String?) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userPostsCollection = db.collection("user_posts").document(userId).collection("posts")

            val post = hashMapOf(
                "userId" to userId,
                "content" to postContent,
                "imageUrl" to imageUrl
            )

            userPostsCollection.add(post)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Post created successfully.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to create post. Please try again.", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not authenticated. Please log in.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedImageUri = uri
                val imageView = findViewById<ImageView>(R.id.postImageView)
                imageView.setImageURI(uri)
            }
        }
    }
}
