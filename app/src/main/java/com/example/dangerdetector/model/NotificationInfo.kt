package com.example.dangerdetector.model

data class NotificationInfo(
    val status: String,
    val message: String,
    val result: MutableList<NotificationModel>

)
