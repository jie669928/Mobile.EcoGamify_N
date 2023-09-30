package com.example.mobileecogamify

import android.net.Uri
import com.google.firebase.Timestamp
import android.os.Parcel
import android.os.Parcelable

data class ChallengeData(
    val id: String, // Add an 'id' property to your ChallengeData class
    val title: String,
//    val date: String, // Assuming you store date as Timestamp
    val timeHour: Int,
    val timeMinute: Int,
    val description: String,
    val eventType: String,
    val imageUrl: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeInt(timeHour)
        parcel.writeInt(timeMinute)
        parcel.writeString(description)
        parcel.writeString(eventType)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChallengeData> {
        override fun createFromParcel(parcel: Parcel): ChallengeData {
            return ChallengeData(parcel)
        }

        override fun newArray(size: Int): Array<ChallengeData?> {
            return arrayOfNulls(size)
        }
    }
}
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
