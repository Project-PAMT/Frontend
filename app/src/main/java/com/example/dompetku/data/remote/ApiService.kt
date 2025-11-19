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
import com.example.dompetku.data.dto.TransactionRequest
import com.example.dompetku.data.dto.TransactionResponse
import retrofit2.http.Headers

interface ApiService {

//    @Headers("Content-Type: application/json")
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

    @GET("categories")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): CategoryListResponse

//
//    @POST("categories")
//    suspend fun createCategory(
//        @Header("Authorization") token: String,
//        @Body body: Map<String, String>
//    ): CategoryResponse
//
//    @PUT("categories/{id}")
//    suspend fun updateCategory(
//        @Header("Authorization") token: String,
//        @Path("id") id: Int,
//        @Body body: Map<String, String>
//    ): Map<String, String>
//
//    @DELETE("categories/{id}")
//    suspend fun deleteCategory(
//        @Header("Authorization") token: String,
//        @Path("id") id: Int
//    ): Map<String, String>
}


