package com.example.dangerdetector

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.example.dangerdetector.databinding.ActivityLoginBinding
import com.example.dangerdetector.databinding.ActivityMainBinding
import com.example.dangerdetector.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var button : LinearLayout
     var userToken : String? = null
    var userEmail : String? = null
    var userName : String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        button = binding.btnInvisible
        val view = binding.root
        setContentView(view)
        replaceFragment(HomeFragment())

        userToken = intent.getStringExtra("token")
        userEmail = intent.getStringExtra("email")
        userName = intent.getStringExtra("username")

        binding.bottomNavigationView.setOnItemSelectedListener  {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else->{}
            }
            true
        }

        button.setOnClickListener {
            createNotify("Alert","Some dangerous object has been detected. Please refresh the notification page to see the threat")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotify(title : String, detail: String){
        val notification = NotificationCompat.Builder(this@MainActivity,"userChannel").setSmallIcon(R.drawable.icon).setContentTitle(title).setContentText(detail).setColor(R.color.secondaryBlue).setAutoCancel(true).setPriority(
            NotificationCompat.PRIORITY_DEFAULT)
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pending = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE)
        notification.setContentIntent(pending)

        val channel = NotificationChannel("userChannel","User Channel",NotificationManager.IMPORTANCE_DEFAULT)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
        manager.notify(10000,notification.build())
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    fun getToken() : String?{
        return userToken
    }
}