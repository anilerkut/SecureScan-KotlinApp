package com.example.dangerdetector.service

import com.example.dangerdetector.model.LoginInfo
import com.example.dangerdetector.model.NotificationInfo
import com.example.dangerdetector.model.RegisterInfo
import com.example.dangerdetector.model.UpdateUsernameInfo
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface DangerDetectorService {

    @POST("api/v1/auth/register")
    fun register(@Body jsonObject : JsonObject) : Call<RegisterInfo>

    @POST("api/v1/auth/login")
    fun login(@Body jsonObject : JsonObject) : Call<LoginInfo>

    @GET("api/v1/note")
    fun getNotifications(@Header("Authorization") auth : String) : Call<NotificationInfo>

    @PUT("api/v1/user")
    fun updateUsername(@Header("Authorization") auth : String,@Body jsonObject: JsonObject) : Call<UpdateUsernameInfo>

    @PUT("api/v1/user")
    fun updatePassword(@Header("Authorization") auth : String,@Body jsonObject: JsonObject) : Call<UpdateUsernameInfo>

}