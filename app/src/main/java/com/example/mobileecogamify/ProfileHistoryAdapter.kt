package com.example.mobileecogamify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProfileHistoryAdapter : RecyclerView.Adapter<ProfileHistoryAdapter.ProfileViewHolder>() {

    private val profiles: MutableList<UserProfile> = mutableListOf()

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        val genderTextView: TextView = itemView.findViewById(R.id.genderTextView)
        val dateOfBirthTextView: TextView = itemView.findViewById(R.id.dateOfBirthTextView) // Add dateOfBirth field
        val profileImageView: ImageView = itemView.findViewById(R.id.profileImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.profile_list_item, parent, false)
        return ProfileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = profiles[position]

        holder.nameTextView.text = profile.name
        holder.emailTextView.text = profile.email
        holder.genderTextView.text = profile.gender
        holder.dateOfBirthTextView.text = profile.dateOfBirth // Display dateOfBirth

        // Use Picasso to load and display the image
        Picasso.get().load(profile.imageUrl).into(holder.profileImageView)
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    fun setData(profiles: List<UserProfile>) {
        this.profiles.clear()
        this.profiles.addAll(profiles)
        notifyDataSetChanged()
    }
}