package com.example.dompetku.data.repository

import com.example.dompetku.data.remote.RetrofitInstance
import com.example.dompetku.data.dto.LoginRequest
import com.example.dompetku.data.dto.LoginResponse
import com.example.dompetku.data.dto.RegisterRequest
import com.example.dompetku.data.dto.RegisterResponse
import com.example.dompetku.data.remote.ApiService
class AuthRepository {
    private val api = RetrofitInstance.api

    suspend fun login(email: String, password: String): LoginResponse {
        return RetrofitInstance.api.login(LoginRequest(email, password))
    }

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return RetrofitInstance.api.register(RegisterRequest(name, email, password))
    }

//    suspend fun getProfile(token: String): LoginResponse {
//        return api.getProfile("Bearer $token")
//    }
}