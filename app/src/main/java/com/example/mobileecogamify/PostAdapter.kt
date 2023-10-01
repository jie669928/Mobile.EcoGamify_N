package com.example.mobileecogamify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PostAdapter(
    private var posts: List<Post>,
    private val onDeleteClickListener: (Int) -> Unit,
    private val onShareClickListener: (Int) -> Unit // Add the onShareClickListener
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var selectedPost: Post? = null

    fun getSelectedPost(): Post? {
        return selectedPost
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view, onDeleteClickListener, onShareClickListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)

        holder.itemView.setOnClickListener {
            // Update the selected post when an item is clicked
            selectedPost = post
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class PostViewHolder(
        itemView: View,
        onDeleteClickListener: (Int) -> Unit,
        onShareClickListener: (Int) -> Unit // Add onShareClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val textViewPostContent: TextView = itemView.findViewById(R.id.textViewContent)
        private val postImageView: ImageView = itemView.findViewById(R.id.postImageView)
        private val deleteButton: Button = itemView.findViewById(R.id.deletePostButton)
        private val shareButton: Button = itemView.findViewById(R.id.shareButton)

        fun bind(post: Post) {
            textViewPostContent.text = post.content
            if (post.imageUrl != null) {
                // Load the image using Picasso (you need to add the Picasso library to your project)
                Picasso.get().load(post.imageUrl).into(postImageView)
            }

            deleteButton.setOnClickListener {
                onDeleteClickListener(adapterPosition)
            }

            // Handle the "Share" button click
            shareButton.setOnClickListener {
                onShareClickListener(adapterPosition)
            }
        }
    }
}
