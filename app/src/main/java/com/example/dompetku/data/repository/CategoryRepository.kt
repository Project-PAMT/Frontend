package com.example.dompetku.data.repository

import com.example.dompetku.data.remote.RetrofitInstance

class CategoryRepository {
    private val api = RetrofitInstance.api

    suspend fun getCategories(token: String) =
        api.getCategories("Bearer $token")
}
