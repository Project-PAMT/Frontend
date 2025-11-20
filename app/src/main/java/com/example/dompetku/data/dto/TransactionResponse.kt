package com.example.dompetku.data.dto

// Single Response (untuk create/update transaction)
data class TransactionResponse(
    val message: String,
    val data: TransactionData?
)

// List Response (untuk get all transactions)
data class TransactionListResponse(
    val message: String,
    val data: List<TransactionData>?
)

// Data model yang dipakai bersama
data class TransactionData(
    val id: Int,
    val user_id: Int,
    val title: String,
    val amount: Int,
    val type: String,
    val category_id: Int?,
    val category_name: String? = null,
    val date: String,
    val description: String? = null,
    val created_at: String
)