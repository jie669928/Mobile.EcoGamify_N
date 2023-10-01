package com.example.mobileecogamify

data class Post(
    val postId: String, // Unique ID for the post
    val content: String,
    val imageUrl: String? // Nullable in case there's no image
)
