package com.example.mobileecogamify

import android.net.Uri
import com.google.firebase.Timestamp

data class ChallengeData(
    val id: String, // Add an 'id' property to your ChallengeData class
    val title: String,
//    val date: String, // Assuming you store date as Timestamp
    val timeHour: Int,
    val timeMinute: Int,
    val description: String,
    val eventType: String,
    val imageUrl: String
)
//
//package com.example.mobileecogamify
//
//import android.net.Uri
//import com.google.firebase.Timestamp
//
//data class ChallengeData(
//    val title: String,
//    val date: String, // Assuming you store date as Timestamp
//    val timeHour: Int,
//    val timeMinute: Int,
//    val description: String,
//    val eventType: String,
//    val imageUrl: String
//) {
//    var id: String = "" // Add an ID field to the ChallengeData class
//}
