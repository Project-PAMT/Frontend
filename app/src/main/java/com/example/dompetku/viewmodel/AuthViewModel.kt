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

    // ======================
    //         LOGIN
    // ======================
    fun login(email: String, password: String, prefs: Prefs) {
        viewModelScope.launch {
            _loginState.value = AuthState(loading = true)

            try {
                Log.d("AuthViewModel", "=== LOGIN REQUEST ===")
                Log.d("AuthViewModel", "Email: $email")

                val res = repository.login(email, password)

                Log.d("AuthViewModel", "=== LOGIN SUCCESS ===")
                Log.d("AuthViewModel", "Token: ${res.token}")
                Log.d("AuthViewModel", "User: ${res.user.name}")
                Log.d("AuthViewModel", "Email: ${res.user.email}")

                // Simpan secara lokal
                token = res.token
                fullName = res.user.name

                // Simpan ke shared preferences
                prefs.saveToken(res.token)
                prefs.saveUserData(
                    userId = res.user.id.toString(),
                    name = res.user.name,
                    email = res.user.email
                )

                Log.d("AuthViewModel", "Token and user data saved to SharedPreferences")

                _loginState.value = AuthState(success = true)

            } catch (e: HttpException) {
                val err = e.response()?.errorBody()?.string()
                Log.e("AuthViewModel", "HTTP Error: $err")

                _loginState.value = AuthState(
                    error = err ?: "Login gagal!"
                )

            } catch (e: IOException) {
                Log.e("AuthViewModel", "Network Error: ${e.message}")
                _loginState.value = AuthState(error = "Koneksi gagal: ${e.message}")

            } catch (e: Exception) {
                Log.e("AuthViewModel", "Unknown Error", e)
                _loginState.value = AuthState(error = e.message)
            }
        }
    }

    // ======================
    //        REGISTER
    // ======================
    fun register(name: String, email: String, password: String, prefs: Prefs) {
        viewModelScope.launch {
            _registerState.value = AuthState(loading = true)

            try {
                Log.d("AuthViewModel", "=== REGISTER REQUEST ===")
                Log.d("AuthViewModel", "Name: $name | Email: $email")

                // Backend hanya mengirim message
                val res = repository.register(name, email, password)

                Log.d("AuthViewModel", "=== REGISTER SUCCESS ===")
                Log.d("AuthViewModel", "Message: ${res.message}")

                // Tidak menyimpan token (karena backend tidak mengirim)
                _registerState.value = AuthState(success = true)

            } catch (e: HttpException) {
                val err = e.response()?.errorBody()?.string()
                Log.e("AuthViewModel", "Register HTTP Error: $err")

                _registerState.value = AuthState(
                    error = err ?: "Register gagal!"
                )

            } catch (e: IOException) {
                Log.e("AuthViewModel", "Register Network Error")
                _registerState.value = AuthState(error = "Koneksi gagal: ${e.message}")

            } catch (e: Exception) {
                Log.e("AuthViewModel", "Register Unknown Error", e)
                _registerState.value = AuthState(error = e.message)
            }
        }
    }

    // ======================
    //          LOGOUT
    // ======================
    fun logout(prefs: Prefs) {
        Log.d("AuthViewModel", "=== LOGOUT ===")
        prefs.clearAllData()
        token = null
        fullName = ""
        _loginState.value = AuthState()
        _registerState.value = AuthState()
    }

    fun resetLoginState() {
        _loginState.value = AuthState()
    }

    fun resetRegisterState() {
        _registerState.value = AuthState()
    }
}
