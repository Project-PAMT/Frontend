package com.example.dompetku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dompetku.screen.*
import com.example.dompetku.ui.TransaksiScreen
import com.example.dompetku.utils.Prefs
import com.example.dompetku.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val viewModel = remember { AuthViewModel() }
    val context = LocalContext.current
    val prefs = remember { Prefs(context) }

    // Cek apakah user sudah login
    val isLoggedIn = prefs.isLoggedIn()
    val startDestination = if (isLoggedIn) NavDestination.Home else NavDestination.Login

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(
                    NavDestination.Home,
                    NavDestination.Transaksi,
                    NavDestination.Aktivitas,
                    NavDestination.Profil
                )
            ) {
                BottomNavbar(
                    selected = when (currentRoute) {
                        NavDestination.Home -> NavItem.Beranda
                        NavDestination.Transaksi -> NavItem.Transaksi
                        NavDestination.Aktivitas -> NavItem.Aktivitas
                        NavDestination.Profil -> NavItem.Profil
                        else -> NavItem.Beranda
                    },
                    onSelected = { item ->
                        when (item) {
                            NavItem.Beranda -> navController.navigate(NavDestination.Home)
                            NavItem.Transaksi -> navController.navigate(NavDestination.Transaksi)
                            NavItem.Aktivitas -> navController.navigate(NavDestination.Aktivitas)
                            NavItem.Profil -> navController.navigate(NavDestination.Profil)
                        }
                    }
                )
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(padding)
        ) {

            // ---------- LOGIN ----------
            composable(NavDestination.Login) {
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {
                        navController.navigate(NavDestination.Home) {
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
                        navController.navigate(NavDestination.Home) {
                            popUpTo(NavDestination.Daftar) { inclusive = true }
                        }
                    },
                    onLoginClick = {
                        navController.navigate(NavDestination.Login)
                    }
                )
            }

            // ---------- HOME / DASHBOARD ----------
            composable(NavDestination.Home) {
                DashboardScreen(
                    onAddClick = {
                        navController.navigate(NavDestination.Transaksi)
                    }
                )
            }

            // ---------- TRANSAKSI ----------
            composable(NavDestination.Transaksi) {
                TransaksiScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            // ---------- AKTIVITAS ----------
            composable(NavDestination.Aktivitas) {
                AktivitasScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            // ---------- PROFIL ----------
            composable(NavDestination.Profil) {
                ProfileScreen(
                    onBack = { navController.popBackStack() },
                    onEditProfile = {
                        // TODO: Navigate to edit profile
                    },
                    onChangePassword = {
                        // TODO: Navigate to change password
                    },
                    onLogout = {
                        // Clear user data
                        viewModel.logout(prefs)
                        
                        // Navigate to login
                        navController.navigate(NavDestination.Login) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}