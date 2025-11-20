package com.example.dompetku.data.remote

import com.example.dompetku.data.dto.CategoryListResponse
import com.example.dompetku.data.dto.CategoryResponse
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
import com.example.dompetku.data.dto.TransactionListResponse
import com.example.dompetku.data.dto.TransactionRequest
import com.example.dompetku.data.dto.TransactionResponse

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("transactions")
    suspend fun createTransaction(
        @Header("Authorization") token: String,
        @Body request: TransactionRequest
    ): TransactionResponse

    @GET("transactions")
    suspend fun getTransactions(
        @Header("Authorization") token: String
    ): TransactionListResponse

    @GET("transactions/{id}")
    suspend fun getTransactionDetail(
        @Header("Authorization") token: String,
        @Path("id") transactionId: Int
    ): TransactionResponse

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): TransactionResponse

    @GET("categories")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): CategoryListResponse
}