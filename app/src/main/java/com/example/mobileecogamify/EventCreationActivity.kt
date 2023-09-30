package com.example.mobileecogamify
//
//import android.content.Intent
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.view.View
//import android.widget.*
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.storage.FirebaseStorage
//import java.util.UUID
//
//class EventCreationActivity : AppCompatActivity() {
//
//    private lateinit var imageInputButton: Button
//    private lateinit var imageLauncher: ActivityResultLauncher<Intent>
//    private lateinit var calendarView: CalendarView
//    private lateinit var editText: EditText
//    private var stringDateSelected: String? = null
//    private lateinit var eventTypeRadioGroup: RadioGroup
//    private lateinit var selectedImage: ImageView
//    private val PICK_IMAGE_REQUEST = 1
//    private var selectedImageUri: Uri? = null
//    private val db = FirebaseFirestore.getInstance() // Define the Firestore reference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_event_creation)
//
//        val inputImageBtn = findViewById<Button>(R.id.imageInputButton)
//        val selectedImageView = findViewById<ImageView>(R.id.selectedImage)
//        val saveEventBtn = findViewById<Button>(R.id.save_event_btn)
//
//        calendarView = findViewById(R.id.calendarView)
//        editText = findViewById(R.id.challenge_name)
//        eventTypeRadioGroup = findViewById(R.id.eventTypeRadioGroup)
//
//        // Handle image upload button click
//        inputImageBtn.setOnClickListener {
//            openImageChooser()
//        }
//
//        // Set a listener for radio button selection
//        eventTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
//            when (checkedId) {
//                R.id.radioCleaning -> {
//                    // Handle "Cleaning up the environment" event type selection
//                }
//                R.id.radioPlanting -> {
//                    // Handle "Plant native species" event type selection
//                }
//                R.id.radioReduceReuseRecycle -> {
//                    // Handle "Reduce, reuse, repair and recycle" event type selection
//                }
//            }
//        }
//
//        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
//            stringDateSelected = "$year${month + 1}$dayOfMonth"
//            calendarClicked()
//        }
//
//        // Initialize the image launcher
//        imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK && result.data != null) {
//                val uri = result.data?.data
//                if (uri != null) {
//                    selectedImageUri = uri
//                    selectedImageView.setImageURI(uri)
//                }
//            }
//        }
//
//        // Handle "Save Event" button click
//        saveEventBtn.setOnClickListener {
//            val title = editText.text.toString()
//            val description = findViewById<EditText>(R.id.description_input).text.toString()
//
//            // Ensure all necessary fields are filled
//            if (title.isNotEmpty() && description.isNotEmpty() && selectedImageUri != null) {
//                // Upload the image to Firebase Storage and save the event data to Firestore
//                uploadImageToStorageAndSaveEvent(title, description)
//            } else {
//                Toast.makeText(this, "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun calendarClicked() {
//        // You can implement any desired behavior here without Firebase
//        editText.setText("Date selected: $stringDateSelected")
//    }
//
//    private fun uploadImageToStorageAndSaveEvent(title: String, description: String) {
//        val storageRef = FirebaseStorage.getInstance().reference
//        val imageRef = storageRef.child("event_images/${UUID.randomUUID()}")
//
//        val uploadTask = imageRef.putFile(selectedImageUri!!)
//
//        uploadTask.addOnSuccessListener { taskSnapshot ->
//            // Image uploaded successfully, now save the event data to Firestore
//            imageRef.downloadUrl.addOnSuccessListener { uri ->
//                val imageUrl = uri.toString() // Get the Uri of the image as a string
//                saveEventToFirestore(title, description, imageUrl)
//            }.addOnFailureListener { e ->
//                // Handle the failure to get the download URL
//                Toast.makeText(this, "Failed to get image URL.", Toast.LENGTH_SHORT).show()
//            }
//        }.addOnFailureListener { e ->
//            // Handle the failure to upload the image
//            Toast.makeText(this, "Failed to upload image.", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun saveEventToFirestore(title: String, description: String, imageUrl: String) {
//        // Define the Firestore collection for events
//        val eventsCollection = db.collection("events")
//
//        val event = hashMapOf(
//            "title" to title,
//            "description" to description,
//            "imageUrl" to imageUrl, // Store the image URL
//            // Add other event data as needed
//        )
//
//        eventsCollection.add(event)
//            .addOnSuccessListener { documentReference ->
//                // Event data saved successfully
//                Toast.makeText(this, "Event saved successfully.", Toast.LENGTH_SHORT).show()
//
//                // You can navigate to another activity or perform further actions here if needed
//            }
//            .addOnFailureListener { e ->
//                // Handle the failure to save event data
//                Toast.makeText(this, "Failed to save event.", Toast.LENGTH_SHORT).show()
//            }
//    }
//
//    private fun openImageChooser() {
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        imageLauncher.launch(intent)
//    }
//}

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class EventCreationActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_creation) // Replace with the correct layout resource

        val uploadImageButton = findViewById<Button>(R.id.imageInputButton)
        val selectedImageView = findViewById<ImageView>(R.id.selectedImage)
        val saveEventButton = findViewById<Button>(R.id.save_event_btn)
        val challengeNameInput = findViewById<EditText>(R.id.challenge_name)
        val descriptionInput = findViewById<EditText>(R.id.description_input)

        uploadImageButton.setOnClickListener {
            openImageChooser()
        }

        saveEventButton.setOnClickListener {
            val title = challengeNameInput.text.toString()
            val description = descriptionInput.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty() && selectedImageUri != null) {
                uploadImageToStorageAndSaveEvent(title, description)
            } else {
                Toast.makeText(this, "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImageToStorageAndSaveEvent(title: String, description: String) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("eventData_images/${UUID.randomUUID()}")

        val uploadTask = imageRef.putFile(selectedImageUri!!)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri
                saveEventToFirestore(title, description, imageUrl)
            }.addOnFailureListener { e ->
                // Handle the failure to get the download URL
            }
        }.addOnFailureListener { e ->
            // Handle the failure to upload the image
        }
    }

    private fun saveEventToFirestore(title: String, description: String, imageUrl: Uri) {
        val eventsCollection = db.collection("events")

        val eventData = hashMapOf(
            "title" to title,
            "description" to description,
            "imageUrl" to imageUrl.toString()
        )

        eventsCollection.add(eventData)
            .addOnSuccessListener { documentReference ->
                val eventId = documentReference.id

                // You can navigate to another activity or perform further actions here
                // For example, navigate to the EventDetailsActivity
                val intent = Intent(this, MyChallengeActivity::class.java)
                intent.putExtra("eventId", eventId)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                // Handle the failure to save data
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
                val imageView = findViewById<ImageView>(R.id.selectedImage)
                imageView.setImageURI(uri)
            }
        }
    }
}
