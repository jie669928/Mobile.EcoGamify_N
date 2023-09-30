package com.example.mobileecogamify

import android.net.Uri

data class EventData(
    var challengeName : String ?= null,

    var descriptionInput : String ?= null,

    val imageUrl: Uri,
    val title: String,
    val description: String

)
