package com.example.dompetku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dompetku.screen.BottomNavbar
import com.example.dompetku.screen.NavItem
import com.example.dompetku.screen.DashboardScreen
import com.example.dompetku.screen.TransaksiScreen
import com.example.dompetku.screen.AktivitasScreen
import com.example.dompetku.screen.ProfileScreen

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

    // untuk mengetahui screen aktif (agar bottom navbar ikut berubah)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavbar(
                selected = when (currentRoute) {
                    NavDestination.Beranda -> NavItem.Beranda
                    NavDestination.Transaksi -> NavItem.Transaksi
                    NavDestination.Aktivitas -> NavItem.Aktivitas
                    NavDestination.Profil -> NavItem.Profil
                    else -> NavItem.Beranda
                },
                onSelected = { item ->
                    when (item) {
                        NavItem.Beranda -> navController.navigate(NavDestination.Beranda)
                        NavItem.Transaksi -> navController.navigate(NavDestination.Transaksi)
                        NavItem.Aktivitas -> navController.navigate(NavDestination.Aktivitas)
                        NavItem.Profil -> navController.navigate(NavDestination.Profil)
                    }
                }
            )
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = NavDestination.Beranda,
            modifier = Modifier.padding(padding)
        ) {

            composable(NavDestination.Beranda) {
                DashboardScreen(
                    onAddClick = {
                        // FAB klik → masuk TransaksiScreen
                        navController.navigate(NavDestination.Transaksi)
                    }
                )
            }

            composable(NavDestination.Transaksi) {
                TransaksiScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(NavDestination.Aktivitas) {
                AktivitasScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable(NavDestination.Profil) {
                ProfileScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
