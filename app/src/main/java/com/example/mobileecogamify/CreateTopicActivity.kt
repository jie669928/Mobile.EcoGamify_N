package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import android.app.Activity
import android.content.Intent
import android.net.Uri

class CreateTopicActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null
    private val db = FirebaseFirestore.getInstance() // Define the Firestore reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_topic)

        val uploadImageButton = findViewById<Button>(R.id.uploadImageButton)
        val cardImageView = findViewById<ImageView>(R.id.topicImageView)
        val saveCardButton = findViewById<Button>(R.id.saveTopicButton)
        val topicTitleInput = findViewById<EditText>(R.id.topicTitleInput)

        // Handle image upload button click
        uploadImageButton.setOnClickListener {
            openImageChooser()
        }

        // Handle save button click
        saveCardButton.setOnClickListener {
            // Get title from EditText field
            val title = topicTitleInput.text.toString()

            // Check if an image is selected and a title is filled
            if (title.isNotEmpty() && selectedImageUri != null) {
                uploadImageToStorage(title)
            } else {
                // Show an error message to the user
                Toast.makeText(this, "Please select an image and enter a title.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImageToStorage(title: String) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("topic_images/${UUID.randomUUID()}")

        val uploadTask = imageRef.putFile(selectedImageUri!!)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Image uploaded successfully, now save the topic data to Firestore
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri // Get the Uri of the image
                saveTopicToFirestore(title, imageUrl)
            }.addOnFailureListener { e ->
                // Handle the failure to get the download URL
            }
        }.addOnFailureListener { e ->
            // Handle the failure to upload the image
        }
    }

    private fun saveTopicToFirestore(title: String, imageUrl: Uri) {
        // Check if the user is authenticated
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            // Define the Firestore collection for user-specific topics
            val userTopicsCollection = db.collection("user_topic").document(userId).collection("topic")

            val topic = hashMapOf(
                "title" to title,
                "imageUrl" to imageUrl.toString() // Store the image URL as a string
            )

            userTopicsCollection.add(topic)
                .addOnSuccessListener { documentReference ->
                    // Document ID for the topic
                    val topicId = documentReference.id

                    // Show a toast message
                    Toast.makeText(this, "Topic created", Toast.LENGTH_SHORT).show()

                    // After displaying the toast message, navigate to HotTopicActivity
                    val intent = Intent(this, HotTopicActivity::class.java)
                    startActivity(intent)
                    finish() // Finish the current activity to prevent going back
                }
                .addOnFailureListener { e ->
                    // Handle the failure to save data
                }
        } else {
            // Handle the case where there's no authenticated user
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
                val imageView = findViewById<ImageView>(R.id.topicImageView)
                imageView.setImageURI(uri)
            }
        }
    }
}
