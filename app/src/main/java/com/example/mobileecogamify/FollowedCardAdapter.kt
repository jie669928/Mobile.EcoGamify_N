package com.example.mobileecogamify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FollowedCardAdapter (private var followedCards: List<FollowedCard>) : RecyclerView.Adapter<FollowedCardAdapter.FollowedCardViewHolder>() {

    inner class FollowedCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.cardTitleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.cardDescriptionTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowedCardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.followed_card_item, parent, false)
        return FollowedCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FollowedCardViewHolder, position: Int) {
        val card = followedCards[position]
        holder.titleTextView.text = card.title
        holder.descriptionTextView.text = card.description

//        // Handle Follow button click
//        holder.followButton.setOnClickListener {
//            // Implement follow logic here
//            // You can use card.title, card.description, etc.
//        }
//
//        // Handle Delete button click
//        holder.deleteButton.setOnClickListener {
//            // Implement delete logic here
//            // You can use card.title, card.description, etc.
//        }
    }

    override fun getItemCount(): Int {
        return followedCards.size
    }

    fun updateData(newData: List<FollowedCard>) {
        followedCards = newData
        notifyDataSetChanged()
    }

}

