package com.example.dangerdetector.model


data class UpdateUsernameInfo (
    val status: String,
    val message: String,
    val result: Result
)

data class ResultUsername (
    val username: String,
    val name: String,
    val email: String
)

