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
import com.example.dangerdetector.databinding.ActivityLoginBinding
import com.example.dangerdetector.model.LoginInfo
import com.example.dangerdetector.service.DangerDetectorService
import com.google.gson.JsonObject
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    lateinit var dangerService: DangerDetectorService
    lateinit var editTextLoginEmail: EditText
    lateinit var editTextLoginPassword: EditText
    lateinit var loginBtn : Button
    lateinit var signUpText : TextView
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        signUpText = binding.dontHaveAccountText
        loginBtn = binding.loginButton
        editTextLoginEmail = binding.emailLoginEditText
        editTextLoginPassword = binding.passwordLoginEditText

        dangerService = APIClient.getClient().create(DangerDetectorService::class.java)
        loginBtn.setOnClickListener(loginBtnClick)
        signUpText.setOnClickListener(signUpClick)
    }


    val loginBtnClick = View.OnClickListener {
        checkUser()
    }

    val signUpClick = View.OnClickListener {
        var goToRegisterintent = Intent(this,RegisterActivity::class.java)
        startActivity(goToRegisterintent)
    }

    fun checkUser()
    {
        var userEmail : String = editTextLoginEmail.text.toString()
        var userPassword : String = editTextLoginPassword.text.toString()
        var jsonObject = JsonObject()
        jsonObject.addProperty("email",userEmail)
        jsonObject.addProperty("password",userPassword)
        dangerService.login(jsonObject).enqueue(object : Callback<LoginInfo>{
            override fun onResponse(call: Call<LoginInfo>, response: Response<LoginInfo>) {
                val logResponse = response.body()
                if(logResponse!=null){
                    if(logResponse.status=="OK"){
                        Toast.makeText(applicationContext,logResponse.message, Toast.LENGTH_LONG).show()
                        var userToken = logResponse.result.token
                        var mainIntent = Intent(applicationContext,MainActivity::class.java)
                        mainIntent.putExtra("token",userToken)
                        mainIntent.putExtra("email",userEmail)
                        startActivity(mainIntent)
                    }
                    else{
                        Toast.makeText(applicationContext,logResponse.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginInfo>, t: Throwable) {
                Toast.makeText(applicationContext,"Something went wrong", Toast.LENGTH_LONG).show()
            }
        })

        editTextLoginEmail.text.clear()
        editTextLoginPassword.text.clear()
    }
}