package com.example.dompetku.data.repository

import com.example.dompetku.data.dto.TransactionRequest
import com.example.dompetku.data.dto.TransactionResponse
import com.example.dompetku.data.remote.RetrofitInstance

class TransactionRepository {

    private val api = RetrofitInstance.api

    suspend fun createTransaction(
        token: String,
        request: TransactionRequest
    ): TransactionResponse {
        return api.createTransaction("Bearer $token", request)
    }
}
