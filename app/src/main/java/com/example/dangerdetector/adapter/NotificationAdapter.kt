package com.example.dangerdetector.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.dangerdetector.R
import com.example.dangerdetector.model.NotificationModel

class NotificationAdapter(private val context: Activity, private val list: MutableList<NotificationModel>) : ArrayAdapter<NotificationModel>(context,
    R.layout.notification_list_item,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rootView = context.layoutInflater.inflate(R.layout.notification_list_item,null,true)

        val notificationTextView = rootView.findViewById<TextView>(R.id.notificationItemTitleTextView)
        val notificationDateTextView = rootView.findViewById<TextView>(R.id.notificationItemDateTextView)
        val notificationImageView = rootView.findViewById<ImageView>(R.id.notificationItemImageView)

        val notificationModel = list.get(position)
        val notTitle = notificationModel.name
        if (notTitle != null) {
            if(notTitle.lowercase().contains("knife")){
                notificationImageView.setImageResource(R.drawable.knife)
            }
            else if(notTitle.lowercase().contains("gun")){
                notificationImageView.setImageResource(R.drawable.gun2)
            }
            else if(notTitle.lowercase().contains("bomb")){
                notificationImageView.setImageResource(R.drawable.bomb)
            }
            else if(notTitle.lowercase().contains("rifle")){
                notificationImageView.setImageResource(R.drawable.rifle)
            }
            else if(notTitle.lowercase().contains("drug")){
                notificationImageView.setImageResource(R.drawable.drugs)
            }
            else if(notTitle.lowercase().contains("bag")){
                notificationImageView.setImageResource(R.drawable.briefcase)
            }
        }
        notificationTextView.text = notTitle
        var replacedDate = notificationModel.createdAt.replace('T',' ')
        notificationDateTextView.text = replacedDate.subSequence(0,19)
            return rootView
    }
}