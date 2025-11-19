package com.example.dompetku.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dompetku.data.dto.CategoryResponse
import com.example.dompetku.data.remote.ApiService
import com.example.dompetku.data.remote.RetrofitInstance
import com.example.dompetku.data.remote.RetrofitInstance.api
import com.example.dompetku.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repo: CategoryRepository = CategoryRepository()
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryResponse>>(emptyList())
    val categories = _categories

    val loading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    fun loadCategories(token: String) {
        viewModelScope.launch {
            try {
                loading.value = true
                error.value = null

                Log.d("CategoryVM", "Memulai request dengan token: Bearer $token")
                val result = api.getCategories("Bearer $token")
                Log.d("CategoryVM", "Response message: ${result.message}")
                Log.d("CategoryVM", "Jumlah kategori: ${result.data.size}")

                _categories.value = result.data
                Log.d("CategoryVM", "Categories berhasil di-set: ${_categories.value.size}")

            } catch (e: Exception) {
                Log.e("CategoryVM", "Error loading categories", e)
                error.value = e.message ?: "Terjadi kesalahan"
            } finally {
                loading.value = false
            }
        }
    }
}
