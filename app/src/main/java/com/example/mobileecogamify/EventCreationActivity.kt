package com.example.mobileecogamify

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import com.google.firebase.Timestamp
import java.util.Date


class EventCreationActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_creation)

        val uploadImageButton = findViewById<Button>(R.id.imageInputButton)
        val selectedImageView = findViewById<ImageView>(R.id.selectedImage)
        val saveEventButton = findViewById<Button>(R.id.save_event_btn)
        val challengeNameInput = findViewById<EditText>(R.id.challenge_name)
        val descriptionInput = findViewById<EditText>(R.id.description_input)
        val timePicker = findViewById<TimePicker>(R.id.timePicker1)
        val eventTypeRadioGroup = findViewById<RadioGroup>(R.id.eventTypeRadioGroup)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        uploadImageButton.setOnClickListener {
            openImageChooser()
        }

        saveEventButton.setOnClickListener {
            val title = challengeNameInput.text.toString()
            val description = descriptionInput.text.toString()
            val dateSelected = calendarView.date // Get the selected date in milliseconds
            val timeHour = timePicker.hour // Get the selected hour
            val timeMinute = timePicker.minute // Get the selected minute

            // Determine which radio button is selected for eventType
            val eventTypeRadioButtonId = eventTypeRadioGroup.checkedRadioButtonId
            var eventType = ""

            when (eventTypeRadioButtonId) {
                R.id.radioCleaning -> eventType = "Cleaning up the environment"
                R.id.radioPlanting -> eventType = "Plant native species"
                R.id.radioReduceReuseRecycle -> eventType = "Reduce, reuse, repair and recycle"
            }

            if (title.isNotEmpty() && description.isNotEmpty() && selectedImageUri != null) {
                uploadImageToStorageAndSaveEvent(title, description, dateSelected, timeHour, timeMinute, eventType)
            } else {
                Toast.makeText(this, "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadImageToStorageAndSaveEvent(
        title: String,
        description: String,
        dateSelected: Long,
        timeHour: Int,
        timeMinute: Int,
        eventType: String
    ) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("eventData_images/${UUID.randomUUID()}")

        val uploadTask = imageRef.putFile(selectedImageUri!!)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri
                saveEventToFirestore(title, description, dateSelected, timeHour, timeMinute, eventType, imageUrl)
            }.addOnFailureListener { e ->
                // Handle the failure to get the download URL
            }
        }.addOnFailureListener { e ->
            // Handle the failure to upload the image
        }
    }

    private fun saveEventToFirestore(
        title: String,
        description: String,
        dateSelected: Long,
        timeHour: Int,
        timeMinute: Int,
        eventType: String,
        imageUrl: Uri
    ) {
        val eventsCollection = db.collection("events")

        // Convert the Unix timestamp (milliseconds) to a Firestore Timestamp
        val dateTimestamp = Timestamp(Date(dateSelected))

        val eventData = hashMapOf(
            "title" to title,
            "description" to description,
            "date" to dateTimestamp, // Store as Timestamp
            "timeHour" to timeHour,
            "timeMinute" to timeMinute,
            "eventType" to eventType,
            "imageUrl" to imageUrl.toString()
        )

        eventsCollection.add(eventData)
            .addOnSuccessListener { documentReference ->
                val eventId = documentReference.id

                // You can navigate to another activity or perform further actions here
                // For example, navigate to the EventDetailsActivity
                val intent = Intent(this, HomeCategoryActivity::class.java)
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