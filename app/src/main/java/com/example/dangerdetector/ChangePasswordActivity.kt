package com.example.dangerdetector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.dangerdetector.configs.APIClient
import com.example.dangerdetector.databinding.ActivityChangeBinding
import com.example.dangerdetector.databinding.ActivityChangePasswordBinding
import com.example.dangerdetector.model.UpdateUsernameInfo
import com.example.dangerdetector.service.DangerDetectorService
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {


    private lateinit var binding : ActivityChangePasswordBinding
    lateinit var dangerService: DangerDetectorService
    lateinit var passwordUpdateText : EditText
    lateinit var changeSaveBtn : Button
    lateinit var activity: MainActivity
    lateinit var newPassword : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        activity = MainActivity()
        dangerService = APIClient.getClient().create(DangerDetectorService::class.java)
        passwordUpdateText = binding.passwordChangeEditText
        changeSaveBtn = binding.changeSaveBtn
        val userToken = intent.getStringExtra("token")

        changeSaveBtn.setOnClickListener {
            val newPassword = passwordUpdateText.text.toString()
            val jsonObject = JsonObject()
            jsonObject.addProperty("password",newPassword)
            dangerService.updateUsername("Bearer "+userToken,jsonObject).enqueue(object :
                Callback<UpdateUsernameInfo> {
                override fun onResponse(call: Call<UpdateUsernameInfo>, response: Response<UpdateUsernameInfo>
                ) {
                    val logResponse = response.body()
                    if(logResponse!=null){
                        if(logResponse.status=="OK"){
                            Toast.makeText(applicationContext,"Password has been changed", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
                }
                override fun onFailure(call: Call<UpdateUsernameInfo>, t: Throwable) {
                    Toast.makeText(applicationContext,"Update Failed", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
