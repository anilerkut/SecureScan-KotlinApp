package com.example.dangerdetector

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.dangerdetector.configs.APIClient
import com.example.dangerdetector.databinding.ActivityChangeBinding
import com.example.dangerdetector.databinding.ActivityLoginBinding
import com.example.dangerdetector.model.UpdateUsernameInfo
import com.example.dangerdetector.service.DangerDetectorService
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChangeBinding
    lateinit var dangerService: DangerDetectorService
    lateinit var usernameUpdateText : EditText
    lateinit var changeSaveBtn : Button
    lateinit var activity: MainActivity
    lateinit var newUsername : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        activity = MainActivity()
        dangerService = APIClient.getClient().create(DangerDetectorService::class.java)
        usernameUpdateText = binding.usernameChangeEditText
        changeSaveBtn = binding.changeSaveBtn
        val userToken = intent.getStringExtra("token")

        changeSaveBtn.setOnClickListener {
            val newUsername = usernameUpdateText.text.toString()
            val jsonObject = JsonObject()
            jsonObject.addProperty("username",newUsername)
            dangerService.updateUsername("Bearer "+userToken,jsonObject).enqueue(object : Callback<UpdateUsernameInfo>{
                override fun onResponse(call: Call<UpdateUsernameInfo>, response: Response<UpdateUsernameInfo>
                ) {
                    val logResponse = response.body()
                    if(logResponse!=null){
                        if(logResponse.status=="OK"){
                            Toast.makeText(applicationContext,logResponse.message,Toast.LENGTH_LONG).show()
                            activity.userName = newUsername
                            finish()
                        }
                    }
                }

                override fun onFailure(call: Call<UpdateUsernameInfo>, t: Throwable) {
                    Toast.makeText(applicationContext,"Update Failed",Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}