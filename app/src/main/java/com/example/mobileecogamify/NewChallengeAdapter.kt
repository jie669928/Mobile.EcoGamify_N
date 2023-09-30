package com.example.mobileecogamify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.firestore.FirebaseFirestore

class NewChallengeAdapter(
    private var challengeList: List<ChallengeData>,
    private val onDeleteClickListener: (ChallengeData) -> Unit
) : RecyclerView.Adapter<NewChallengeAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val challengeImageView: ImageView = itemView.findViewById(R.id.challengeImageView)
//        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
////        val dateAndTimeTextView: TextView = itemView.findViewById(R.id.dateAndTimeTextView) // Add this line
//        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
//        val eventTypeTextView: TextView = itemView.findViewById(R.id.eventTypeTextView)
//        val imageView: ImageView = itemView.findViewById(R.id.challengeImageView) // Example: Replace with actual IDs
        val challengeImageView: ImageView = itemView.findViewById(R.id.challengeImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val eventTypeTextView: TextView = itemView.findViewById(R.id.eventTypeTextView)
        val deleteButton: AppCompatButton = itemView.findViewById(R.id.deleteButton)

        init {
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val challenge = challengeList[position]
                    onDeleteClickListener(challenge)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate your item layout and return a ViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent, false)
        return ViewHolder(view)
    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        // Bind data to your UI elements for each item
//        val challenge = challengeList[position]
//
//        // Set values for TextViews, ImageViews, etc.
//        holder.titleTextView.text = challenge.title
////        holder.dateAndTimeTextView.text = challenge.date
//        holder.descriptionTextView.text = challenge.description
//        holder.eventTypeTextView.text = challenge.eventType
//
//        // Load image using an image loading library like Glide or Picasso
//        // Example using Glide (make sure to add the dependency):
//        // Glide.with(holder.itemView.context).load(challenge.imageUrl).into(holder.imageView)
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val challenge = challengeList[position]

        holder.titleTextView.text = challenge.title
        holder.descriptionTextView.text = challenge.description
        holder.eventTypeTextView.text = challenge.eventType

        // Load the image using Glide
        Glide.with(holder.itemView.context)
            .load(challenge.imageUrl)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.challengeImageView)

//        // Set up a click listener to handle challenge deletion
//        holder.itemView.setOnClickListener {
//            deleteChallenge(challenge)
//        }
    }


    fun updateData(newChallengeList: List<ChallengeData>) {
        challengeList = newChallengeList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        // Return the number of items in the list
        return challengeList.size
    }


//    private fun deleteChallenge(challenge: ChallengeData) {
//        val firestore = FirebaseFirestore.getInstance()
//        val challengesCollection = firestore.collection("events")
//
//        challengesCollection.document(challenge.id)
//            .delete()
//            .addOnSuccessListener {
//                // Challenge deleted successfully
//                // You can also update your local challengeList and notify the adapter
//                Toast.makeText(
//                    context,
//                    "Challenge deleted successfully.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            .addOnFailureListener { exception ->
//                // Handle delete failure and show an error message to the user
//                Toast.makeText(
//                    context,
//                    "Error deleting challenge: ${exception.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//    }
}