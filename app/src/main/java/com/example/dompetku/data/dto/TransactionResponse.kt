package com.example.dompetku.data.dto

data class TransactionResponse(
    val success: Boolean,
    val message: String,
    val data: TransactionData?
)

data class TransactionData(
    val id: Int,
    val user_id: Int,
    val title: String,
    val amount: Int,
    val type: String,
    val category_id: Int?,
    val date: String,         // TAMBAHAN
    val created_at: String
)
