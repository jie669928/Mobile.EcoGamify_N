package com.example.mobileecogamify

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileecogamify.HotCommunityActivity
import com.example.mobileecogamify.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddCommunityActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null
    private val db = FirebaseFirestore.getInstance() // Define the Firestore reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_community)

        val uploadImageButton = findViewById<Button>(R.id.uploadImageButton)
        val cardImageView = findViewById<ImageView>(R.id.communityImageView)
        val saveCardButton = findViewById<Button>(R.id.saveCommunityButton)
        val cardTitleInput = findViewById<EditText>(R.id.communityTitleInput)
        val cardDescriptionInput = findViewById<EditText>(R.id.communityDescriptionInput)

        // Handle image upload button click
        uploadImageButton.setOnClickListener {
            openImageChooser()
        }

        // Handle save button click
        saveCardButton.setOnClickListener {
            // Get title and description from EditText fields
            val title = cardTitleInput.text.toString()
            val description = cardDescriptionInput.text.toString()

            // Check if all fields are filled and an image is selected
            if (title.isNotEmpty() && description.isNotEmpty() && selectedImageUri != null) {
                uploadImageToStorage(title, description)
            } else {
                // Show an error message to the user
                Toast.makeText(this, "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show()
            }
        }


    }

    // Rest of your code

    private fun uploadImageToStorage(title: String, description: String) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("card_images/${UUID.randomUUID()}")

        val uploadTask = imageRef.putFile(selectedImageUri!!)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Image uploaded successfully, now save the card data to Firestore
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri // Get the Uri of the image
                saveCardToFirestore(title, description, imageUrl) // After the community is saved, navigate to NewCommunityActivity
            }.addOnFailureListener { e ->
                // Handle the failure to get the download URL
            }
        }.addOnFailureListener { e ->
            // Handle the failure to upload the image
        }
    }

    private fun saveCardToFirestore(title: String, description: String, imageUrl: Uri) {
        // Check if the user is authenticated
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            // Define the Firestore collection for user-specific communities
            val userCommunitiesCollection = db.collection("user_communities").document(userId).collection("communities")

            val community = hashMapOf(
                "title" to title,
                "description" to description,
                "imageUrl" to imageUrl.toString() // Store the image URL as a string
            )

            userCommunitiesCollection.add(community)
                .addOnSuccessListener { documentReference ->
                    // Document ID for the community
                    val communityId = documentReference.id

                    // Show a toast message
                    Toast.makeText(this, "Community created", Toast.LENGTH_SHORT).show()

                    // After displaying the toast message, navigate back to HotCommunityActivity
                    val intent = Intent(this, HotCommunityActivity::class.java)
                    startActivity(intent)
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
                val imageView = findViewById<ImageView>(R.id.communityImageView)
                imageView.setImageURI(uri)
            }
        }
    }

    // Rest of your code
}
