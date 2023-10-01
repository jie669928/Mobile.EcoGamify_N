package com.example.mobileecogamify

data class Topic(
    val topicId: String, // Unique ID for the post
    val content: String,
    val imageUrl: String? // Nullable in case there's no image
)
