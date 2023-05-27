package com.example.dangerdetector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.dangerdetector.databinding.ActivityLoginBinding
import com.example.dangerdetector.databinding.ActivityMainBinding
import com.example.dangerdetector.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragment(HomeFragment())

        val userToken = intent.getStringExtra("token")
        val userEmail = intent.getStringExtra("email")
        val user = User(userEmail!!,userToken!!)

        binding.bottomNavigationView.setOnItemSelectedListener  {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                else->{}
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}