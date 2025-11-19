package com.example.dompetku.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dompetku.data.repository.AuthRepository
import com.example.dompetku.utils.Prefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    var token: String? = null
    var fullName: String = ""

    private val _loginState = MutableStateFlow(AuthState())
    val loginState: StateFlow<AuthState> = _loginState

    private val _registerState = MutableStateFlow(AuthState())
    val registerState: StateFlow<AuthState> = _registerState

    fun login(email: String, password: String, prefs: Prefs) {
        viewModelScope.launch {
            _loginState.value = AuthState(loading = true)
            try {
                Log.d("AuthViewModel", "=== LOGIN REQUEST ===")
                Log.d("AuthViewModel", "Email: $email")
                Log.d("AuthViewModel", "Password length: ${password.length}")

                val res = repository.login(email, password)

                Log.d("AuthViewModel", "=== LOGIN SUCCESS ===")
                Log.d("AuthViewModel", "Token: ${res.token}")
                Log.d("AuthViewModel", "User: ${res.user.name}")

                // SIMPAN DATA
                token = res.token
                fullName = res.user.name

                // SIMPAN TOKEN KE SHAREDPREFERENCES
                prefs.saveToken(res.token)
                Log.d("AuthViewModel", "Token saved to SharedPreferences")

                _loginState.value = AuthState(success = true)

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("AuthViewModel", "=== HTTP ERROR ===")
                Log.e("AuthViewModel", "Code: ${e.code()}")
                Log.e("AuthViewModel", "Message: ${e.message()}")
                Log.e("AuthViewModel", "Error Body: $errorBody")

                _loginState.value = AuthState(
                    error = "Login gagal: ${e.message()} - $errorBody"
                )

            } catch (e: IOException) {
                Log.e("AuthViewModel", "=== NETWORK ERROR ===")
                Log.e("AuthViewModel", "Message: ${e.message}")

                _loginState.value = AuthState(
                    error = "Koneksi gagal: ${e.message}"
                )

            } catch (e: Exception) {
                Log.e("AuthViewModel", "=== UNKNOWN ERROR ===", e)

                _loginState.value = AuthState(
                    error = "Error: ${e.message}"
                )
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
                Log.e("AuthViewModel", "Register failed", e)
                _registerState.value = AuthState(error = e.message)
            }
        }
    }
}