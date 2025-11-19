package com.example.dompetku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dompetku.data.dto.TransactionData
import com.example.dompetku.viewmodel.TransactionViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AktivitasScreen(
    token: String,
    onBack: () -> Unit,
    viewModel: TransactionViewModel = viewModel()
) {
    val headerBlue = Color(0xFF1E90FF)
    val backgroundGray = Color(0xFFF5F5F5)
    val redExpense = Color(0xFFE74C3C)
    val blueIncome = Color(0xFF3498DB)

    val transactions by viewModel.transactions
    val loading by viewModel.loading
    val error by viewModel.error

    // Load data saat pertama kali dibuka
    LaunchedEffect(Unit) {
        println("DEBUG: Token yang dikirim = $token")
        if (token.isNotEmpty()) {
            viewModel.getTransactions(token)
        } else {
            viewModel.error.value = "Token tidak ditemukan. Silakan login kembali."
        }
    }

    // Grouping transaksi berdasarkan tanggal
    val groupedTransactions = remember(transactions) {
        transactions.groupBy { transaction ->
            formatDateHeader(transaction.date)
        }.toSortedMap(compareByDescending { parseDate(it) })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(headerBlue)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(headerBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Riwayat Transaksi",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                    .background(backgroundGray)
            ) {
                when {
                    loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = headerBlue)
                        }
                    }
                    error != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = error ?: "Terjadi kesalahan",
                                    color = redExpense,
                                    fontSize = 14.sp
                                )
                                Button(
                                    onClick = { viewModel.getTransactions(token) },
                                    colors = ButtonDefaults.buttonColors(containerColor = headerBlue)
                                ) {
                                    Text("Coba Lagi")
                                }
                            }
                        }
                    }
                    transactions.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Belum ada transaksi",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    }
                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            groupedTransactions.forEach { (dateHeader, transactionList) ->
                                TransactionSection(
                                    title = dateHeader,
                                    items = transactionList,
                                    redExpense = redExpense,
                                    blueIncome = blueIncome
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionSection(
    title: String,
    items: List<TransactionData>,
    redExpense: Color,
    blueIncome: Color
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C3E50)
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items.forEach { transaction ->
                TransactionListItem(
                    transaction = transaction,
                    redExpense = redExpense,
                    blueIncome = blueIncome
                )
            }
        }
    }
}

@Composable
fun TransactionListItem(
    transaction: TransactionData,
    redExpense: Color,
    blueIncome: Color
) {
    val isIncome = transaction.type.lowercase() == "income"

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = transaction.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2C3E50)
                )
                Text(
                    text = transaction.category_name ?: "Lainnya",
                    fontSize = 13.sp,
                    color = Color(0xFF7F8C8D)
                )
            }

            Text(
                text = formatCurrency(transaction.amount),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = if (isIncome) blueIncome else redExpense
            )
        }
    }
}

// Helper functions
fun formatCurrency(amount: Int): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return formatter.format(amount).replace("Rp", "Rp ")
}

fun formatDateHeader(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(dateString) ?: return dateString

        val calendar = Calendar.getInstance()
        val today = calendar.time
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = calendar.time

        val dateCalendar = Calendar.getInstance().apply { time = date }
        val todayCalendar = Calendar.getInstance().apply { time = today }
        val yesterdayCalendar = Calendar.getInstance().apply { time = yesterday }

        when {
            isSameDay(dateCalendar, todayCalendar) -> "Hari Ini"
            isSameDay(dateCalendar, yesterdayCalendar) -> "Kemarin"
            else -> {
                val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
                outputFormat.format(date)
            }
        }
    } catch (e: Exception) {
        dateString
    }
}

fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

fun parseDate(dateHeader: String): Date {
    return try {
        when (dateHeader) {
            "Hari Ini" -> Calendar.getInstance().time
            "Kemarin" -> Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, -1)
            }.time
            else -> {
                val format = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
                format.parse(dateHeader) ?: Date(0)
            }
        }
    } catch (e: Exception) {
        Date(0)
    }
}