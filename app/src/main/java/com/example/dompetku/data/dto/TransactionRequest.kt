package com.example.dompetku.data.dto

data class TransactionRequest(
    val title: String,
    val amount: Int,
    val type: String,        // "income" atau "expense"
    val category_id: Int?,   // kalau backend pakai id kategori
    val date: String,        // format "YYYY-MM-DD"
    val description: String?
)
