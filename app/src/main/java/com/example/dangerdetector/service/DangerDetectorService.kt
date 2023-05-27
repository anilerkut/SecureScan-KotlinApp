package com.example.dangerdetector.service

import com.example.dangerdetector.model.LoginInfo
import com.example.dangerdetector.model.RegisterInfo
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface DangerDetectorService {

    @POST("api/v1/auth/register")
    fun register(@Body jsonObject : JsonObject) : Call<RegisterInfo>

    @POST("api/v1/auth/login")
    fun login(@Body jsonObject : JsonObject) : Call<LoginInfo>
}