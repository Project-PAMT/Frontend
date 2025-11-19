package com.example.dompetku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// =====================================================
//                    WARNA
// =====================================================
val Primary = Color(0xFF0F766E)
val BackgroundLight = Color(0xFFF6F7F8)
val GreenIncome = Color(0xFF10B981)
val RedExpense = Color(0xFFEF4444)
val BlueIncome = Color(0xFF1E88E5)
val HeaderBlueStart = Color(0xFF2F80ED)
val HeaderBlueEnd = Color(0xFF56CCF2)


// =====================================================
//                    DASHBOARD SCREEN
// =====================================================
@Composable
fun DashboardScreen(
    onAddClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        Brush.verticalGradient(listOf(HeaderBlueStart, HeaderBlueEnd))
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text("Selamat Datang,", color = Color.White, fontSize = 16.sp)
                        Text(
                            "Keonho Kartanegara!",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BalanceCard(
                    balance = "Rp 800.000",
                    income = "Rp 1.000.000",
                    expense = "Rp 200.000"
                )

                TransactionsCard()
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = HeaderBlueEnd,
                modifier = Modifier.padding(end = 24.dp, bottom = 24.dp)
            ) {
                Icon(Icons.Outlined.Edit, contentDescription = null, tint = Color.White)
            }
        }
    }
}


// =====================================================
//                    SUMMARY CARD (White with Border)
// =====================================================
@Composable
fun SummaryCard(title: String, amount: String, color: Color, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // Bagian Kiri: Icon & Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                title,
                color = Color.DarkGray,
                fontSize = 16.sp
            )
        }

        // Bagian Kanan: Amount
        Text(
            amount,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}



// =====================================================
//                      PREVIEW
// =====================================================
@Composable
fun BalanceCard(balance: String, income: String, expense: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Saldo Anda", color = Color.Gray, fontSize = 14.sp)
        Spacer(Modifier.height(4.dp))
        Text(balance, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Pemasukan", color = Color.DarkGray, fontSize = 14.sp)
                Text(income, color = BlueIncome, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Pengeluaran", color = Color.DarkGray, fontSize = 14.sp)
                Text(expense, color = RedExpense, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TransactionRow(title: String, category: String, amount: String, isIncome: Boolean) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                Text(category, fontSize = 12.sp, color = Color.Gray)
            }
            Text(amount, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (isIncome) BlueIncome else RedExpense)
        }
        Spacer(Modifier.height(12.dp))
    }
}

@Composable
fun TransactionsCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Transaksi Hari Ini", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
            Column(horizontalAlignment = Alignment.End) {
                Text("Rp 100.000", color = BlueIncome, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text("Rp 49.000", color = RedExpense, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.height(12.dp))
        TransactionRow("Oyi Chicken Butter Milk", "Makanan", "Rp 24.000", isIncome = false)
        TransactionRow("Transfer Ibu", "Pemasukan", "Rp 100.000", isIncome = true)
        TransactionRow("Beli Bensin", "Transportasi", "Rp 25.000", isIncome = false)
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    DashboardScreen()
}
