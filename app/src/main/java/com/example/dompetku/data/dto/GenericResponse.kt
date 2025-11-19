package com.example.dompetku.data.dto


data class GenericResponse<T>(
    val message: String,
    val data: T?
)
