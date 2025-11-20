package com.example.dompetku.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.dompetku.data.dto.TransactionListResponse
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

    suspend fun getTransactions(token: String): TransactionListResponse {
        println("DEBUG: Calling API with token = Bearer $token")
        return api.getTransactions("Bearer $token")
    }

    suspend fun getTransactionDetail(
        token: String,
        transactionId: Int
    ): TransactionResponse {
        return api.getTransactionDetail("Bearer $token", transactionId)
    }

    suspend fun updateTransaction(
        token: String,
        transactionId: Int,
        request: TransactionRequest
    ): TransactionResponse {
        Log.d(TAG, "🔄 Updating transaction ID: $transactionId")
        Log.d(TAG, "📤 Request data: $request")
        Log.d(TAG, "🔑 Token: Bearer $token")

        try {
            val response = api.updateTransaction("Bearer $token", transactionId, request)
            Log.d(TAG, "✅ Update success: ${response.message}")
            return response
        } catch (e: Exception) {
            Log.e(TAG, "❌ Update error: ${e.message}", e)
            throw e
        }
    }

    suspend fun deleteTransaction(
        token: String,
        transactionId: Int
    ): TransactionResponse {
        return api.deleteTransaction("Bearer $token", transactionId)
    }

}