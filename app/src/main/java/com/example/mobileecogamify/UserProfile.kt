package com.example.mobileecogamify

data class UserProfile(
    val name: String,
    val email: String,
    val gender: String,
    val imageUrl: String // This property is required to store the image URL
)
