package com.example.mobileecogamify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CommunityAdapter(
    private var cards: List<Card>,
    private val followClickListener: (Card) -> Unit,
    private val deleteClickListener: (Card) -> Unit
) :
    RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    fun setData(cards: List<Card>) {
        this.cards = cards
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.community_item, parent, false)
        return CommunityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val card = cards[position]
        holder.bind(card)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    inner class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val communityImageView: ImageView = itemView.findViewById(R.id.communityImageView)
        private val communityTitleTextView: TextView =
            itemView.findViewById(R.id.communityTitleTextView)
        private val communityDescriptionTextView: TextView =
            itemView.findViewById(R.id.communityDescriptionTextView)
        private val followButton: Button = itemView.findViewById(R.id.followButton)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(card: Card) {
            Picasso.get().load(card.imageUrl).into(communityImageView)
            communityTitleTextView.text = card.title
            communityDescriptionTextView.text = card.description

            followButton.setOnClickListener {
                followClickListener(card)
            }

            deleteButton.setOnClickListener {
                deleteClickListener(card)
            }
        }
    }
}
