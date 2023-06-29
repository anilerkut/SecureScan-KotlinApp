package com.example.dangerdetector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.dangerdetector.databinding.ActivityNotificationDetailBinding
import com.squareup.picasso.Picasso

class NotificationDetail : AppCompatActivity() {

    private lateinit var binding : ActivityNotificationDetailBinding
    lateinit var notificationName : TextView
    lateinit var notificationDesc : TextView
    lateinit var notificationImg : ImageView
    lateinit var notificationDate : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        notificationDate = binding.notificationDateTextView
        notificationDesc = binding.notificationDescriptionTextView
        notificationName = binding.notificationTitleTextView
        notificationImg = binding.notificationImageView

        var name = intent.getStringExtra("name")
        var description = intent.getStringExtra("description")
        var imgUrl = intent.getStringExtra("imgUrl")
        var date = intent.getStringExtra("date")


        if (imgUrl != null) {
            Log.d("resim",imgUrl)
        }
        notificationDate.text = date
        notificationDesc.text = description
        notificationName.text = name
        Picasso.get().load(imgUrl).into(notificationImg)

    }
}