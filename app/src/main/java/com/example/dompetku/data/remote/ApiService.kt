package com.example.dompetku.data.remote

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Header
import com.example.dompetku.data.dto.LoginRequest
import com.example.dompetku.data.dto.LoginResponse
import com.example.dompetku.data.dto.RegisterRequest
import com.example.dompetku.data.dto.RegisterResponse
import com.example.dompetku.data.dto.TransactionResponse
import com.example.dompetku.data.dto.GenericResponse

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

}

