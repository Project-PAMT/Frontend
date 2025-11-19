package com.example.dompetku.viewmodel

data class AuthState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)