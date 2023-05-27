package com.example.dangerdetector.model

data class LoginInfo (
val status: String,
val message: String,
val result: Result
)

data class Result (
    val type: String,
    val token: String
)

