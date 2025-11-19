package com.example.dompetku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dompetku.data.dto.LoginRequest
import com.example.dompetku.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()
    var token: String? = null
    var fullName: String = ""

    private val _loginState = MutableStateFlow(AuthState())
    val loginState: StateFlow<AuthState> = _loginState

    private val _registerState = MutableStateFlow(AuthState())
    val registerState: StateFlow<AuthState> = _registerState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState(loading = true)
            try {
                val res = repository.login(email, password)

                // SIMPAN DATA KE VIEWMODEL
                token = res.token
                fullName = res.user.name

                _loginState.value = AuthState(success = true)
            } catch (e: Exception) {
                _loginState.value = AuthState(error = e.message)
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = AuthState(loading = true)
            try {
                repository.register(name, email, password)
                _registerState.value = AuthState(success = true)
            } catch (e: Exception) {
                _registerState.value = AuthState(error = e.message)
            }
        }
    }
}
