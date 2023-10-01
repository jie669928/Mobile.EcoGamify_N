//package com.example.mobileecogamify
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.content.Intent
//import android.net.Uri
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.storage.FirebaseStorage
//
//class ProfileActivity : AppCompatActivity() {
//    private val auth = FirebaseAuth.getInstance()
//    private val db = FirebaseFirestore.getInstance()
//    private val storage = FirebaseStorage.getInstance()
//
//    private lateinit var profileImage: ImageView
//    private lateinit var uploadImageButton: Button
//    private lateinit var nameEditText: EditText
//    private lateinit var emailEditText: EditText
//    private lateinit var genderEditText: EditText
//    private lateinit var updateProfileButton: Button
//    private var profileImageUri: Uri? = null
//
//    private val getContent =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            if (uri != null) {
//                profileImageUri = uri
//                profileImage.setImageURI(uri)
//            }
//        }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_profile)
//
//        profileImage = findViewById(R.id.profileImage)
//        uploadImageButton = findViewById(R.id.uploadImageButton)
//        nameEditText = findViewById(R.id.nameEditText)
//        emailEditText = findViewById(R.id.emailEditText)
//        genderEditText = findViewById(R.id.genderEditText)
//        updateProfileButton = findViewById(R.id.updateProfileButton)
//
//        uploadImageButton.setOnClickListener {
//            getContent.launch("image/*")
//        }
//
//        updateProfileButton.setOnClickListener {
//            val name = nameEditText.text.toString()
//            val email = emailEditText.text.toString()
//            val gender = genderEditText.text.toString()
//
//            val user = auth.currentUser
//            if (user != null) {
//                val userId = user.uid
//
//                val userProfilesCollection = db.collection("user_profile").document(userId)
//                    .collection("profile")
//
//                val profileData = hashMapOf(
//                    "name" to name,
//                    "email" to email,
//                    "gender" to gender
//                )
//
//                userProfilesCollection.add(profileData)
//                    .addOnSuccessListener { documentReference ->
//                        val profileId = documentReference.id
//                        if (profileImageUri != null) {
//                            // Upload profile image
//                            val storageRef = storage.reference
//                            val imageRef = storageRef.child("profile_images/$userId/$profileId.jpg")
//
//                            imageRef.putFile(profileImageUri!!)
//                                .addOnSuccessListener { taskSnapshot ->
//                                    // Image uploaded successfully
//                                    imageRef.downloadUrl.addOnSuccessListener { uri ->
//                                        val imageUrl = uri.toString()
//
//                                        // Update the profile document with the image URL
//                                        userProfilesCollection.document(profileId)
//                                            .update("imageUrl", imageUrl)
//                                            .addOnSuccessListener {
//                                                // Image URL saved to the user's profile
//
//                                                Toast.makeText(
//                                                    this,
//                                                    "Profile updated successfully",
//                                                    Toast.LENGTH_SHORT
//                                                ).show()
//
//                                                // Navigate to the MainActivity
//                                                val intent = Intent(this, MainActivity::class.java)
//                                                startActivity(intent)
//                                                finish()
//                                            }
//                                    }
//                                }
//                                .addOnFailureListener { e ->
//                                    Toast.makeText(
//                                        this,
//                                        "Failed to upload profile image",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                        } else {
//                            // No image to upload, just show a success message and navigate
//                            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT)
//                                .show()
//
//                            // Navigate to the MainActivity
//                            val intent = Intent(this, MainActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        }
//                    }
//                    .addOnFailureListener { e ->
//                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
//                    }
//            }
//        }
//
//    }
//}
package com.example.mobileecogamify

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.Calendar

class ProfileActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private lateinit var profileImage: ImageView
    private lateinit var uploadImageButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var genderEditText: EditText
    private lateinit var updateProfileButton: Button
    private var profileImageUri: Uri? = null
    private var selectedDateOfBirth: String? = null
    private lateinit var genderSpinner: Spinner
    private lateinit var dateOfBirthButton: Button

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                profileImageUri = uri
                profileImage.setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImage = findViewById(R.id.profileImage)
        uploadImageButton = findViewById(R.id.uploadImageButton)
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        updateProfileButton = findViewById(R.id.updateProfileButton)
        genderEditText = findViewById(R.id.genderEditText)
        genderSpinner = findViewById(R.id.genderSpinner)
        dateOfBirthButton = findViewById(R.id.dateOfBirthButton)

        val genderOptions = arrayOf("Female", "Male", "Other")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = genderAdapter

        // Handle the date of birth selection
        dateOfBirthButton.setOnClickListener {
            showDatePickerDialog()
        }

        uploadImageButton.setOnClickListener {
            getContent.launch("image/*")
        }

        updateProfileButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val selectedGender = genderSpinner.selectedItem.toString()
            val dateOfBirth = dateOfBirthButton.text.toString() // Get the selected date of birth


            val user = auth.currentUser
            if (user != null) {
                val userId = user.uid

                val userProfilesCollection = db.collection("user_profile").document(userId)
                    .collection("profile")

                // Check if an existing profile exists
                userProfilesCollection.get()
                    .addOnSuccessListener { querySnapshot ->
                        for (documentSnapshot in querySnapshot.documents) {
                            // Delete the existing profile data and associated image
                            val profileId = documentSnapshot.id
                            userProfilesCollection.document(profileId).delete()
                            // Delete the old image from Firebase Storage
                            val storageRef = storage.reference
                            storageRef.child("profile_images/$userId/$profileId.jpg").delete()
                        }

                        // Upload the new profile image
                        if (profileImageUri != null) {
                            val storageRef = storage.reference
                            val profileId =
                                userProfilesCollection.document().id // Generate a new ID
                            val imageRef = storageRef.child("profile_images/$userId/$profileId.jpg")

                            imageRef.putFile(profileImageUri!!)
                                .addOnSuccessListener { taskSnapshot ->
                                    // Image uploaded successfully
                                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                                        val imageUrl = uri.toString()

                                        // Create a new document with the updated data
                                        val profileData = hashMapOf(
                                            "name" to name,
                                            "email" to email,
                                            "gender" to selectedGender, // Save selected gender
                                            "dateOfBirth" to dateOfBirth, // Save selected date of birth
                                            "imageUrl" to imageUrl
                                        )

                                        userProfilesCollection.document(profileId).set(profileData)
                                            .addOnSuccessListener {
                                                Toast.makeText(
                                                    this,
                                                    "Profile updated successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                // Delay the navigation to the AccountFragment
                                                Handler().postDelayed({
                                                    // Navigate to the AccountFragment
                                                    val fragmentManager: FragmentManager =
                                                        supportFragmentManager
                                                    val transaction = fragmentManager.beginTransaction()
                                                    transaction.replace(
                                                        R.id.fragment_container, // Replace with your container ID
                                                        AccountFragment()
                                                    )
                                                    transaction.commit()
                                                }, 1000) // Delay for 1 second (adjust as needed)
                                            }
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        this,
                                        "Failed to upload profile image",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            // No new image to upload, just update the other data
                            val profileData = hashMapOf(
                                "name" to name,
                                "email" to email,
                                "gender" to selectedGender, // Save selected gender
                                "dateOfBirth" to dateOfBirth // Save selected date of birth
                            )

                            userProfilesCollection.add(profileData)
                                .addOnSuccessListener { documentReference ->
                                    Toast.makeText(
                                        this,
                                        "Profile updated successfully",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()

                            // Delay the navigation to the AccountFragment
                            Handler().postDelayed({
                                // Navigate to the AccountFragment
                                val fragmentManager: FragmentManager = supportFragmentManager
                                val transaction = fragmentManager.beginTransaction()
                                transaction.replace(
                                    R.id.fragment_container, // Replace with your container ID
                                    AccountFragment()
                                )
                                transaction.commit()
                            }, 1000) // Delay for 1 second (adjust as needed)
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                // Calculate the user's age based on the selected date
                val birthDate = Calendar.getInstance()
                birthDate.set(year, month, dayOfMonth)
                val age = currentYear - birthDate.get(Calendar.YEAR)

                if (age >= 13) {
                    // User is 13 years or older, the date of birth is valid
                    selectedDateOfBirth = "$dayOfMonth/${month + 1}/$year"
                    dateOfBirthButton.text = selectedDateOfBirth
                } else {
                    // User is under 13 years old
                    Toast.makeText(
                        this,
                        "You must be 13 years or older to set your date of birth.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }

}

