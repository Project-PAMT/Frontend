package com.example.dompetku.viewmodel

import com.example.dompetku.data.dto.UserData

data class AuthState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)
