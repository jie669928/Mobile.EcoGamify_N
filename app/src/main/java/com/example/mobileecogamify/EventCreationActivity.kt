package com.example.mobileecogamify
//
//import android.content.Intent
//import android.graphics.Bitmap
//import android.os.Bundle
//import android.provider.MediaStore
//import android.view.View
//import android.widget.*
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//
//class EventCreationActivity : AppCompatActivity() {
//
//    private lateinit var imageInputButton: Button
//    private lateinit var imageLauncher: ActivityResultLauncher<Intent>
//    private lateinit var calendarView: CalendarView
//    private lateinit var editText: EditText
//    private var stringDateSelected: String? = null
//    private lateinit var eventTypeRadioGroup: RadioGroup
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_event_creation)
//
//        calendarView = findViewById(R.id.calendarView)
//        editText = findViewById(R.id.challenge_name_input)
//        eventTypeRadioGroup = findViewById(R.id.eventTypeRadioGroup)
//
//        // Set up the image selection launcher
//        imageInputButton = findViewById(R.id.imageInputButton)
//        imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK) {
//                val intentData: Intent? = result.data
//                val imageBitmap: Bitmap? = intentData?.extras?.get("data") as Bitmap?
//                // You can use the imageBitmap here as needed
//                // For example, you can display it in an ImageView
//            }
//        }
//
//        // Set a listener for radio button selection
//        eventTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
//            when (checkedId) {
//                R.id.radioCleaning -> {
//                    // Handle "Cleaning up the environment" event type selection
//                    // You can access this selection using 'radioCleaning.isChecked'
//                }
//                R.id.radioPlanting -> {
//                    // Handle "Plant native species" event type selection
//                    // You can access this selection using 'radioPlanting.isChecked'
//                }
//                R.id.radioReduceReuseRecycle -> {
//                    // Handle "Reduce, reuse, repair and recycle" event type selection
//                    // You can access this selection using 'radioReduceReuseRecycle.isChecked'
//                }
//            }
//        }
//
//        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
//            stringDateSelected = "$year${month + 1}$dayOfMonth"
//            calendarClicked()
//        }
//    }
//
//    private fun calendarClicked() {
//        // You can implement any desired behavior here without Firebase
//        editText.setText("Date selected: $stringDateSelected")
//    }
//
//    fun buttonSaveEvent(view: View) {
//        // Implement the save event logic here if needed
//    }
//
//    fun selectImage(view: View) {
//        // Launch the camera to capture an image
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        imageLauncher.launch(takePictureIntent)
//    }
//}
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class EventCreationActivity : AppCompatActivity() {

    private lateinit var imageInputButton: Button
    private lateinit var imageLauncher: ActivityResultLauncher<Intent>
    private lateinit var calendarView: CalendarView
    private lateinit var editText: EditText
    private var stringDateSelected: String? = null
    private lateinit var eventTypeRadioGroup: RadioGroup
    private lateinit var selectedImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_creation)

        calendarView = findViewById(R.id.calendarView)
        editText = findViewById(R.id.challenge_name_input)
        eventTypeRadioGroup = findViewById(R.id.eventTypeRadioGroup)
//        selectedImage = findViewById(R.id.selectedImage) // Add this line to reference your ImageView
//
//        // Set up the image selection launcher
//        imageInputButton = findViewById(R.id.imageInputButton)
//        imageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK) {
//                val intentData: Intent? = result.data
//                val selectedImageUri = intentData?.data
//                // You can use the selectedImageUri to load and display the image
//                selectedImage.setImageURI(selectedImageUri)
//            }
//        }

        // Set a listener for radio button selection
        eventTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioCleaning -> {
                    // Handle "Cleaning up the environment" event type selection
                }
                R.id.radioPlanting -> {
                    // Handle "Plant native species" event type selection
                }
                R.id.radioReduceReuseRecycle -> {
                    // Handle "Reduce, reuse, repair and recycle" event type selection
                }
            }
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            stringDateSelected = "$year${month + 1}$dayOfMonth"
            calendarClicked()
        }
    }

    private fun calendarClicked() {
        // You can implement any desired behavior here without Firebase
        editText.setText("Date selected: $stringDateSelected")
    }

    fun buttonSaveEvent(view: View) {
        // Implement the save event logic here if needed
    }

    fun selectImage(view: View) {
        // Launch the image gallery to select an image
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imageLauncher.launch(intent)
    }
}

