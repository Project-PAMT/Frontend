package com.example.dompetku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dompetku.screen.*
import com.example.dompetku.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val viewModel = AuthViewModel()

            NavHost(
                navController = navController,
                startDestination = NavDestination.Login
            ) {

                // ---------- LOGIN ----------
                composable(NavDestination.Login) {
                    LoginScreen(
                        viewModel = viewModel,
                        onLoginSuccess = {
                            navController.navigate("home") {
                                popUpTo(NavDestination.Login) { inclusive = true }
                            }
                        },
                        onRegisterClick = {
                            navController.navigate(NavDestination.Daftar)
                        }
                    )
                }

                // ---------- REGISTER ----------
                composable(NavDestination.Daftar) {
                    RegisterScreen(
                        viewModel = viewModel,
                        onRegisterSuccess = {
                            navController.navigate("home") {
                                popUpTo(NavDestination.Daftar) { inclusive = true }
                            }
                        },
                        onLoginClick = {
                            navController.navigate(NavDestination.Login)
                        }
                    )
                }

                // ---------- HOME ----------
                composable("home") {
                    DashboardScreen(
                        onAddClick = { /* tambah transaksi soon */ }
                    )
                }
            }
        }
    }
}
