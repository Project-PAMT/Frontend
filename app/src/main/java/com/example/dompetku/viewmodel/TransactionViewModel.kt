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
    val transactionDetail = mutableStateOf<TransactionData?>(null)

    fun createTransaction(token: String, request: TransactionRequest) {
        viewModelScope.launch {
            loading.value = true
            success.value = null
            error.value = null

            try {
                val res = repo.createTransaction(token, request)

                if (res.data != null) {
                    success.value = res.message.ifEmpty { "Transaksi berhasil ditambahkan!" }
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

    fun getTransactionDetail(token: String, transactionId: Int) {
        viewModelScope.launch {
            loading.value = true
            error.value = null

            try {
                val res = repo.getTransactionDetail(token, transactionId)

                if (res.data != null) {
                    transactionDetail.value = res.data
                    error.value = null
                } else {
                    error.value = res.message
                }

            } catch (e: Exception) {
                error.value = e.message ?: "Gagal memuat detail transaksi"
            } finally {
                loading.value = false
            }
        }
    }

    fun deleteTransaction(
        token: String,
        transactionId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val res = repo.deleteTransaction(token, transactionId)

                if (res.message.contains("success", ignoreCase = true) ||
                    res.message.contains("deleted", ignoreCase = true)) {
                    // Reload transactions setelah delete
                    getTransactions(token)
                    onSuccess()
                } else {
                    onError(res.message ?: "Gagal menghapus transaksi")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}