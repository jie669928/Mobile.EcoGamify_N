package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class HotTopicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hot_topic)


        val createTopicButton = findViewById<Button>(R.id.createTopicButton)

        createTopicButton.setOnClickListener {
            // Create an Intent to navigate to the FollowingActivity
            val intent = Intent(this, CreateTopicActivity::class.java)

            // Start the FollowingActivity
            startActivity(intent)
        }

        val myTopicButton = findViewById<Button>(R.id.myTopicButton)

        myTopicButton.setOnClickListener {
            val intent = Intent(this, MyTopicActivity::class.java)
            // Add any extra data you want to pass to the NewCommunityActivity here
            startActivity(intent)
        }

    }
}
