package com.example.dompetku.data.dto

data class CategoryResponse(
    val id: Int,
    val name: String,
    val created_by: String,
    val created_at: String? = null
)

data class CategoryListResponse(
    val message: String,
    val data: List<CategoryResponse>
)
