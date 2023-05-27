package com.example.dangerdetector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.dangerdetector.configs.APIClient
import com.example.dangerdetector.databinding.ActivityRegisterBinding
import com.example.dangerdetector.model.RegisterInfo
import com.example.dangerdetector.model.User
import com.example.dangerdetector.service.DangerDetectorService
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {

    lateinit var dangerService: DangerDetectorService
    lateinit var editTextRegisterEmail: EditText
    lateinit var editTextRegisterUsername: EditText
    lateinit var editTextRegisterPassword: EditText
    lateinit var editTextRegisterPasswordAgain: EditText
    lateinit var registerBtn: Button
    lateinit var signInText: TextView
    lateinit var goToLoginIntent : Intent
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        editTextRegisterEmail = binding.emailRegisterEditText
        editTextRegisterUsername = binding.usernameRegisterEditText
        editTextRegisterPassword = binding.passwordRegisterEditText
        editTextRegisterPasswordAgain = binding.passwordAgainRegisterEditText
        registerBtn = binding.registerButton
        signInText = binding.alreadyAccountTextButton

        goToLoginIntent = Intent(this, LoginActivity::class.java)
        dangerService = APIClient.getClient().create(DangerDetectorService::class.java)
        signInText.setOnClickListener(signInClick)
        registerBtn.setOnClickListener(registerBtnClick)
    }

    val signInClick = View.OnClickListener {
        startActivity(goToLoginIntent)
    }

    val registerBtnClick = View.OnClickListener {
        register()
    }

    fun register() {
        var userEmail: String = editTextRegisterEmail.text.toString()
        var userPassword: String = editTextRegisterPassword.text.toString()
        var userPasswordAgain: String = editTextRegisterPasswordAgain.text.toString()
        var username: String = editTextRegisterUsername.text.toString()
        var name = "Anil"

        if (userPassword == userPasswordAgain || (!userPassword.isEmpty() && !userPasswordAgain.isEmpty())) {

            var jsonObject = JsonObject()
            jsonObject.addProperty("email",userEmail)
            jsonObject.addProperty("password",userPassword)
            jsonObject.addProperty("name",name)
            jsonObject.addProperty("username",username)
            dangerService.register(jsonObject)
                .enqueue(object : Callback<RegisterInfo> {
                    override fun onResponse(call: Call<RegisterInfo>, response: Response<RegisterInfo>) {
                        val regResponse = response.body()
                        if (regResponse != null) {
                            if(regResponse.status == "OK"){
                                Log.d("BURADA","BEN BURADAYIMMMMM")
                                startActivity(goToLoginIntent)
                                finish()
                                Toast.makeText(applicationContext,regResponse.message,Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    override fun onFailure(call: Call<RegisterInfo>, t: Throwable) {
                        Toast.makeText(applicationContext,"Something went wrong",Toast.LENGTH_LONG).show()
                    }
                })
        } else {
            Toast.makeText(
                applicationContext,
                "Passwords are not match or empty ",
                Toast.LENGTH_LONG
            ).show()
        }
    }


}