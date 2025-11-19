// c:\Users\WINDOWS 11\AndroidStudioProjects\Dompetku\app\src\main\java\com\example\dompetku\screen\RiwayatTransaksi.kt
package com.example.dompetku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

data class TransactionItemData(
    val title: String,
    val category: String,
    val amount: String,
    val isIncome: Boolean
)

@Composable
fun RiwayatTransaksiScreen(onBack: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Brush.verticalGradient(listOf(HeaderBlueStart, HeaderBlueEnd)))
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            ) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = null, tint = Color.White)
            }
            Text(
                text = "Riwayat Transaksi",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransactionSection(
                title = "Hari Ini",
                items = listOf(
                    TransactionItemData("Oyi Chicken Butter Milk", "Makanan", "Rp 24.000", false),
                    TransactionItemData("Transfer Ibu", "Pemasukan", "Rp 100.000", true),
                    TransactionItemData("Beli Bensin", "Transportasi", "Rp 25.000", false)
                )
            )
            TransactionSection(
                title = "Kemarin",
                items = listOf(
                    TransactionItemData("Creamy Pesenkopi", "Minuman", "Rp 18.000", false),
                    TransactionItemData("Warung Pojok", "Makanan", "Rp 12.000", false)
                )
            )
            TransactionSection(
                title = "17 Oktober 2025",
                items = listOf(
                    TransactionItemData("Latte CW Coffee", "Minuman", "Rp 18.000", false),
                    TransactionItemData("Warung Pojok", "Makanan", "Rp 10.000", false)
                )
            )
        }
    }
}

@Composable
fun TransactionSection(title: String, items: List<TransactionItemData>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items.forEach { TransactionListItem(it) }
        }
    }
}

@Composable
fun TransactionListItem(item: TransactionItemData) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(item.title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                Text(item.category, fontSize = 12.sp, color = Color.Gray)
            }
            Text(
                item.amount,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (item.isIncome) BlueIncome else RedExpense
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RiwayatTransaksiPreview() {
    RiwayatTransaksiScreen()
}