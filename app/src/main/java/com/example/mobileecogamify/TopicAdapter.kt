package com.example.mobileecogamify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class TopicAdapter(private var topics: List<Topic>, private val onDeleteClickListener: (Int) -> Unit) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topic_item, parent, false)
        return TopicViewHolder(view, onDeleteClickListener)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topics[position]
        holder.bind(topic)
    }

    override fun getItemCount(): Int {
        return topics.size
    }

    inner class TopicViewHolder(itemView: View, onDeleteClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val topicTitle: TextView = itemView.findViewById(R.id.topicTitle)
        private val topicImage: ImageView = itemView.findViewById(R.id.topicImage)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteTopicButton)

        fun bind(topic: Topic) {
            topicTitle.text = topic.content
            if (topic.imageUrl != null) {
                // Load the image using Picasso (you need to add the Picasso library to your project)
                Picasso.get().load(topic.imageUrl).into(topicImage)
            }

            deleteButton.setOnClickListener {
                onDeleteClickListener(adapterPosition)
            }
        }
    }
}
