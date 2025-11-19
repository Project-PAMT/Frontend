package com.example.dompetku.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*


enum class NavItem { Beranda, Transaksi, Kategori, Profil }

@Composable
fun BottomNavbar(
    selected: NavItem,
    onSelected: (NavItem) -> Unit
) {
    val activeColor = HeaderBlueStart
    val inactiveColor = Color(0xFF9AA6B2)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavBarItem(
                label = "Beranda",
                icon = Icons.Outlined.Home,
                selected = selected == NavItem.Beranda,
                activeColor = activeColor,
                inactiveColor = inactiveColor,
                onClick = { onSelected(NavItem.Beranda) }
            )
            NavBarItem(
                label = "Transaksi",
                icon = Icons.Outlined.AccountBalanceWallet,
                selected = selected == NavItem.Transaksi,
                activeColor = activeColor,
                inactiveColor = inactiveColor,
                onClick = { onSelected(NavItem.Transaksi) }
            )
            NavBarItem(
                label = "Aktivitas",
                icon = Icons.Outlined.Category,
                selected = selected == NavItem.Kategori,
                activeColor = activeColor,
                inactiveColor = inactiveColor,
                onClick = { onSelected(NavItem.Kategori) }
            )
            NavBarItem(
                label = "Profil",
                icon = Icons.Outlined.Person,
                selected = selected == NavItem.Profil,
                activeColor = activeColor,
                inactiveColor = inactiveColor,
                onClick = { onSelected(NavItem.Profil) }
            )
        }
    }
}

@Composable
private fun NavBarItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    activeColor: Color,
    inactiveColor: Color,
    onClick: () -> Unit
) {
    val tint = if (selected) activeColor else inactiveColor
    Column(
        modifier = Modifier

            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = tint)
        Text(text = label, color = tint)
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavbarPreview() {
    var selected by remember { mutableStateOf(NavItem.Beranda) }
    BottomNavbar(selected = selected, onSelected = { selected = it })
}