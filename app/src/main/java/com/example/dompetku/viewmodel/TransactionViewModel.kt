package com.example.dompetku.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dompetku.data.dto.TransactionData
import com.example.dompetku.data.dto.TransactionRequest
import com.example.dompetku.data.repository.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val repo: TransactionRepository = TransactionRepository()
) : ViewModel() {

    val loading = mutableStateOf(false)
    val success = mutableStateOf<String?>(null)
    val error = mutableStateOf<String?>(null)
    val transactions = mutableStateOf<List<TransactionData>>(emptyList())

    fun createTransaction(token: String, request: TransactionRequest) {
        viewModelScope.launch {
            loading.value = true
            success.value = null
            error.value = null

            try {
                val res = repo.createTransaction(token, request)

                // Backend tidak mengembalikan success → cek berdasarkan data saja
                if (res.data != null) {
                    success.value = res.message.ifEmpty { "Transaksi berhasil ditambahkan!" }

                    // Refresh daftar transaksi
                    getTransactions(token)
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

    fun getTransactions(token: String) {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            try {
                val res = repo.getTransactions(token)

                if (res.data != null) {
                    transactions.value = res.data
                    error.value = null
                } else {
                    error.value = res.message
                }

            } catch (e: Exception) {
                error.value = e.message ?: "Gagal memuat transaksi"
            } finally {
                loading.value = false
            }
        }
    }
}
