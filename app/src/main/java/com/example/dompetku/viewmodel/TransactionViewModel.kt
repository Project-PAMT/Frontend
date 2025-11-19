package com.example.dompetku.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dompetku.data.dto.TransactionRequest
import com.example.dompetku.data.repository.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val repo: TransactionRepository = TransactionRepository()
) : ViewModel() {

    val loading = mutableStateOf(false)
    val success = mutableStateOf<String?>(null)
    val error = mutableStateOf<String?>(null)

    fun createTransaction(token: String, request: TransactionRequest) {
        viewModelScope.launch {
            loading.value = true
            success.value = null
            error.value = null
            try {
                val res = repo.createTransaction(token, request)
                if (res.success) {
                    success.value = res.message.ifEmpty { "Transaksi berhasil ditambahkan!" }
                } else {
                    error.value = res.message
                }
            } catch (e: Exception) {
                error.value = e.message ?: "Terjadi kesalahan"
            } finally {
                loading.value = false
            }
        }
    }
}
