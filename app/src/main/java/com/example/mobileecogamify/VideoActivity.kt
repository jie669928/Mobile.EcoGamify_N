package com.example.mobileecogamify

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.example.mobileecogamify.R

class VideoActivity : AppCompatActivity() {

    data class VideoInfo(val name: String, val url: String)

    private lateinit var videoView: VideoView
    private lateinit var videoNameTextView: TextView
    private var currentVideoIndex = 0
    // Create a list of video URLs and their corresponding names
    private val videos = listOf(
        VideoInfo("What is Environment And How To Keep It Clean?", "https://www.youtube.com/watch?v=gEk6JLJNg0U"),
        VideoInfo("10 Ways to Take Care of the Environment", "https://www.youtube.com/watch?v=X2YgM1Zw4_E"),
        VideoInfo("How to Take Care of the Environment", "https://www.youtube.com/watch?v=belXC_IoW4o")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        videoView = findViewById(R.id.videoView)
        videoNameTextView = findViewById(R.id.VideoNames)
        val previousButton = findViewById<Button>(R.id.previousButton)
        val nextButton = findViewById<Button>(R.id.submitButton)

        playVideo(currentVideoIndex)

        previousButton.setOnClickListener {
            if (currentVideoIndex > 0) {
                currentVideoIndex--
                playVideo(currentVideoIndex)
            }
        }

        nextButton.setOnClickListener {
            if (currentVideoIndex < videos.size - 1) {
                currentVideoIndex++
                playVideo(currentVideoIndex)
            }
        }
    }

    private fun playVideo(index: Int) {
        val videoInfo = videos[index]
        val videoUri = Uri.parse(videoInfo.url)

        videoView.setVideoURI(videoUri)
        videoView.start()

        videoNameTextView.text = videoInfo.name
    }
}
















