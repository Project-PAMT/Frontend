package com.example.dompetku

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
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

    val isLoggedIn = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val savedToken = prefs.getToken()
        if (savedToken != null) {
            viewModel.token = savedToken
            isLoggedIn.value = true
        }
    }

    // START DESTINATION otomatis
    val startRoute = if (isLoggedIn.value) NavDestination.Login else NavDestination.Login

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
            startDestination = startRoute,
            modifier = Modifier.padding(padding)
        ) {

            // ---------- LOGIN ----------
            composable(NavDestination.Login) {
                LoginScreen(
                    viewModel = viewModel,
                    onLoginSuccess = {
                        // Simpan token otomatis melalui Prefs di ViewModel
                        navController.navigate(NavDestination.Home) {
                            popUpTo(0) { inclusive = true } // 🔥 Tidak bisa balik ke Login
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
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onLoginClick = {
                        navController.navigate(NavDestination.Login)
                    }
                )
            }

            // ---------- HOME / DASHBOARD ----------
            composable(NavDestination.Home) {
                val sharedPreferences = context.getSharedPreferences("DompetkuPrefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("token", "") ?: ""
                val userName = sharedPreferences.getString("user_name", "User") ?: "User"

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
                val context = LocalContext.current
                val prefs = Prefs(context)
                val token = prefs.getToken() ?: ""

                AktivitasScreen(
                    token = token,
                    onBack = { navController.popBackStack() },
                    onTransactionClick = { transactionId ->
                        navController.navigate("transaction_detail/$transactionId")
                    }
                )
            }

            // ---------- DETAIL TRANSAKSI (BARU) ----------
            composable(
                route = "transaction_detail/{transactionId}",
                arguments = listOf(navArgument("transactionId") { type = NavType.IntType })
            ) { backStackEntry ->
                val transactionId = backStackEntry.arguments?.getInt("transactionId") ?: 0
                TransactionDetailScreen(
                    transactionId = transactionId,
                    onBack = { navController.popBackStack() },
                    onEdit = { id ->
                        navController.navigate("edit_transaction/$id")
                    }
                )
            }

            composable("edit_transaction/{transactionId}") { backStackEntry ->
                val transactionId = backStackEntry.arguments?.getString("transactionId")?.toInt() ?: 0
                TransaksiEditScreen(
                    transactionId = transactionId,
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
