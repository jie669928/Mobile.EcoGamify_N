package com.example.mobileecogamify

data class UserProfile(
    val name: String,
    val email: String,
    val gender: String,
    val dateOfBirth: String, // Add date of birth field
    val imageUrl: String
)
