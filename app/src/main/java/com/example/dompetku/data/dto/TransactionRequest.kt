package com.example.dompetku.data.dto

data class TransactionRequest(
    val title: String,
    val amount: Int,
    val type: String,
    val category_id: Int?,
    val date: String,
    val description: String?
)
