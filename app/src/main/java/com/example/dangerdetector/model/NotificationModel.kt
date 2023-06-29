package com.example.dangerdetector.model

data class NotificationModel (
    val id: Long,
    val name: String? = null,
    val description: String? = null,
    val imageUrl: String,
    val createdAt: String,
    val updatedAt: String
)

