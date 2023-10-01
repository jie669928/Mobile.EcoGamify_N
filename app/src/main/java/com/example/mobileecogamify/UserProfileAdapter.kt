package com.example.mobileecogamify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

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
        private val bioTextView: TextView = itemView.findViewById(R.id.genderTextView)

        fun bind(profile: UserProfile) {
            nameTextView.text = profile.name
            emailTextView.text = profile.email
            bioTextView.text = profile.gender
        }
    }
}
