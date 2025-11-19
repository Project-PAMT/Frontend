package com.example.dompetku.data.dto

data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserData
)
data class UserData(
    val id: Int,
    val name: String,
    val email: String
)