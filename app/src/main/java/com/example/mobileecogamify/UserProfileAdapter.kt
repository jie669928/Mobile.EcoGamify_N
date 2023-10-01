package com.example.mobileecogamify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.Calendar

class UserProfileAdapter(private var profiles: List<UserProfile>) : RecyclerView.Adapter<UserProfileAdapter.UserProfileViewHolder>() {

    // This method allows you to update the data in the adapter
    fun setData(newProfiles: List<UserProfile>) {
        profiles = newProfiles
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_list_item, parent, false)
        return UserProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserProfileViewHolder, position: Int) {
        val profile = profiles[position]
        holder.bind(profile)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    inner class UserProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        private val genderTextView: TextView = itemView.findViewById(R.id.genderTextView)
        private val dateOfBirthTextView: TextView = itemView.findViewById(R.id.dateOfBirthTextView)
        private val profileImageView: ImageView = itemView.findViewById(R.id.profileImageView)

        fun bind(profile: UserProfile) {
            nameTextView.text = profile.name
            emailTextView.text = profile.email
            genderTextView.text = profile.gender
            dateOfBirthTextView.text = profile.dateOfBirth

            // Load and display the user's profile image using Picasso
            if (profile.imageUrl.isNotEmpty()) {
                Picasso.get().load(profile.imageUrl).into(profileImageView)
            }
        }
    }
}

