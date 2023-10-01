package com.example.mobileecogamify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class CertificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certification)

        // Get the data passed from MainActivity
        val totalGarbage = intent.getStringExtra("totalGarbage")
        val currentTime = intent.getStringExtra("currentTime")

        val shareButton = findViewById<Button>(R.id.btnShare)
        val shareDialog = findViewById<View>(R.id.shareDialog)
        val whatsappButton = findViewById<ImageButton>(R.id.shareWhatsApp)
        val facebookButton = findViewById<ImageButton>(R.id.shareFaceBook)
        val instagramButton = findViewById<ImageButton>(R.id.shareIns)
        val gmailButton = findViewById<ImageButton>(R.id.shareGmail)
        val doneButton = findViewById<Button>(R.id.btnDone)


        // Find the TextViews for time_use and total_number in the layout
        val timeUseTextView = findViewById<TextView>(R.id.usingTime)
        val totalNumberTextView = findViewById<TextView>(R.id.total_number)

        // Set the text content for time_use and total_number
        timeUseTextView.text = currentTime
        totalNumberTextView.text = totalGarbage

        shareButton.setOnClickListener {
            if (shareDialog.visibility == View.VISIBLE) {
                shareDialog.visibility = View.GONE
            } else {
                shareDialog.visibility = View.VISIBLE
            }
        }

        facebookButton.setOnClickListener {
            val facebookLink =
                "https://www.facebook.com" // Replace with your Facebook link

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, facebookLink)

            // Specify Facebook package name (com.facebook.katana)
            shareIntent.setPackage("com.facebook")

            startActivity(shareIntent)
        }

        whatsappButton.setOnClickListener {
            val whatsappLink =
                "https://www.whatsapp.com" // Replace with your WhatsApp link

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, whatsappLink)

            // Specify WhatsApp package name (com.whatsapp)
            shareIntent.setPackage("com.whatsapp")

            startActivity(shareIntent)
        }

        instagramButton.setOnClickListener {
            val instagramLink =
                "https://www.facebook.com/your-page-link" // Replace with your Facebook link

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, instagramLink)

            // Specify Facebook package name (com.facebook.katana)
            shareIntent.setPackage("com.facebook.katana")

            startActivity(shareIntent)
        }

        gmailButton.setOnClickListener {
            val gmailLink = "https://www.gmail.com"
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, gmailLink)

            // Specify Gmail package name (com.google.android.gm)
            shareIntent.setPackage("com.google.android.gm")

            startActivity(shareIntent)
        }


        // Handle Done button click (close dialog)
        doneButton.setOnClickListener {
            // 创建一个 Intent，用于启动主活动（Main Activity）
            val intent = Intent(this, HomeCategoryActivity::class.java)

            // 启动主活动
            startActivity(intent)

            // 关闭当前活动（即当前的活动将被销毁）
            finish()
        }
    }

}