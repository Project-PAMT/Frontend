package com.example.dompetku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dompetku.data.dto.TransactionData
import com.example.dompetku.utils.Prefs
import com.example.dompetku.viewmodel.TransactionViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

val Primary = Color(0xFF3B9EF8)
val BackgroundLight = Color(0xFFEFF3F6)
val BlueIncome = Color(0xFF3B9EF8)
val RedExpense = Color(0xFFFF5C5C)


private fun formatRupiah(amount: Int): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return formatter.format(amount).replace("Rp", "Rp ")
}

@Composable
fun DashboardScreen(
    onAddClick: () -> Unit = {},
    viewModel: TransactionViewModel = viewModel()
) {
    val context = LocalContext.current
    val prefs = Prefs(context)

    val token = prefs.getToken().orEmpty()
    val userName = prefs.getUserName().orEmpty()

    // Load data saat pertama masuk
    LaunchedEffect(Unit) {
        viewModel.getTransactions(token)
    }

    val transactions = viewModel.transactions.value
    val loading = viewModel.loading.value
    val error = viewModel.error.value

    // Hitung saldo
    val totalIncome = transactions.filter { it.type == "income" }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == "expense" }.sumOf { it.amount }
    val balance = totalIncome - totalExpense

    // Filtering transaksi hari ini
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val todayTransactions = transactions.filter { it.date == today }

    val todayIncome = todayTransactions.filter { it.type == "income" }.sumOf { it.amount }
    val todayExpense = todayTransactions.filter { it.type == "expense" }.sumOf { it.amount }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(
                            Primary,
                            RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            Text(
                                text = "Selamat Datang,",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "$userName!",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-70).dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                BalanceCard(
                    balance = formatRupiah(balance),
                    income = formatRupiah(totalIncome),
                    expense = formatRupiah(totalExpense)
                )

                when {
                    loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Primary)
                        }
                    }

                    error != null -> {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Error: $error", color = RedExpense)
                                Spacer(Modifier.height(8.dp))
                                Button(
                                    onClick = { viewModel.getTransactions(token) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                                ) {
                                    Text("Coba Lagi")
                                }
                            }
                        }
                    }

                    else -> {
                        TransactionsCard(
                            todayIncome = formatRupiah(todayIncome),
                            todayExpense = formatRupiah(todayExpense),
                            transactions = todayTransactions
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = onAddClick,
                shape = CircleShape,
                containerColor = Primary,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(Icons.Outlined.Edit, contentDescription = null, tint = Color.White)
            }
        }
    }
}

@Composable
fun BalanceCard(balance: String, income: String, expense: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp))
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Saldo Anda", color = Color.Gray)
        Spacer(Modifier.height(8.dp))
        Text(balance, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Pemasukan", color = Color.DarkGray)
                Text(income, color = BlueIncome, fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Pengeluaran", color = Color.DarkGray)
                Text(expense, color = RedExpense, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TransactionsCard(
    todayIncome: String,
    todayExpense: String,
    transactions: List<TransactionData>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp))
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Transaksi Hari Ini", fontWeight = FontWeight.Bold)

            Column(horizontalAlignment = Alignment.End) {
                Text(todayIncome, color = BlueIncome, fontWeight = FontWeight.Bold)
                Text(todayExpense, color = RedExpense, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(20.dp))

        if (transactions.isEmpty()) {
            Text("Belum ada transaksi hari ini", color = Color.Gray)
        } else {
            transactions.forEach { t ->
                TransactionRow(
                    title = t.title,
                    category = t.category_name ?: "Lainnya",
                    amount = formatRupiah(t.amount),
                    isIncome = t.type == "income"
                )
            }
        }
    }
}

@Composable
fun TransactionRow(title: String, category: String, amount: String, isIncome: Boolean) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(category, fontSize = 13.sp, color = Color.Gray)
            }
            Text(
                amount,
                fontWeight = FontWeight.Bold,
                color = if (isIncome) BlueIncome else RedExpense
            )
        }
        Spacer(Modifier.height(16.dp))
    }
}