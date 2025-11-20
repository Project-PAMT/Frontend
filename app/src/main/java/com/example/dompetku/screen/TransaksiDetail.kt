package com.example.dompetku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dompetku.data.dto.TransactionData
import com.example.dompetku.utils.Prefs
import com.example.dompetku.viewmodel.TransactionViewModel
import java.text.NumberFormat
import java.util.*
import java.text.SimpleDateFormat

@Composable
fun TransactionDetailScreen(
    transactionId: Int,
    onBack: () -> Unit = {},
    onEdit: (Int) -> Unit = {},
    viewModel: TransactionViewModel = viewModel()
) {
    val context = LocalContext.current
    val prefs = Prefs(context)
    val token = prefs.getToken().orEmpty()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }

    val headerBlue = Color(0xFF3B9EF8)
    val backgroundGray = Color(0xFFF5F5F5)
    val redExpense = Color(0xFFFF5C5C)
    val blueIncome = Color(0xFF3B9EF8)

    // Load detail transaksi saat screen pertama kali muncul
    LaunchedEffect(transactionId) {
        viewModel.getTransactionDetail(token, transactionId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(headerBlue)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
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
                    text = "Detail Transaksi",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Content
            if (viewModel.loading.value) {
                // Loading state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        .background(backgroundGray),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CircularProgressIndicator(color = headerBlue)
                        Text("Memuat detail transaksi...", color = Color.Gray)
                    }
                }
            } else if (viewModel.error.value != null) {
                // Error state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        .background(backgroundGray),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            viewModel.error.value ?: "Terjadi kesalahan",
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = { viewModel.getTransactionDetail(token, transactionId) }) {
                            Text("Coba Lagi")
                        }
                    }
                }
            } else {
                // Content loaded
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        .background(backgroundGray)
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    viewModel.transactionDetail.value?.let { trans ->
                        // Card Jumlah
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (trans.type == "income") blueIncome else redExpense
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = if (trans.type == "income") "Pemasukan" else "Pengeluaran",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = formatRupiah(trans.amount),
                                    color = Color.White,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Card Detail Informasi
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    text = "Informasi Detail",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )

                                DetailInfoRow(label = "Nama", value = trans.title)
                                Divider(color = Color(0xFFE5E7EB))

                                DetailInfoRow(
                                    label = "Kategori",
                                    value = trans.category_name ?: "Tidak ada kategori"
                                )
                                Divider(color = Color(0xFFE5E7EB))

                                DetailInfoRow(
                                    label = "Tanggal",
                                    value = formatDate(trans.date)
                                )
                                Divider(color = Color(0xFFE5E7EB))

                                DetailInfoRow(
                                    label = "Deskripsi",
                                    value = trans.description?.ifEmpty { "Tidak ada deskripsi" }
                                        ?: "Tidak ada deskripsi"
                                )
                            }
                        }

                        // Tombol Edit dan Hapus
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { onEdit(trans.id) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = headerBlue
                                )
                            ) {
                                Icon(
                                    Icons.Outlined.Edit,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text("Edit", fontWeight = FontWeight.SemiBold)
                            }

                            Button(
                                onClick = { showDeleteDialog = true },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = redExpense
                                ),
                                enabled = !isDeleting
                            ) {
                                if (isDeleting) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Icon(
                                        Icons.Outlined.Delete,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text("Hapus", fontWeight = FontWeight.SemiBold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Dialog Konfirmasi Hapus
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            icon = {
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = null,
                    tint = redExpense,
                    modifier = Modifier.size(32.dp)
                )
            },
            title = {
                Text(
                    "Hapus Transaksi?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("Apakah Anda yakin ingin menghapus transaksi ini? Tindakan ini tidak dapat dibatalkan.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        isDeleting = true
                        viewModel.deleteTransaction(
                            token = token,
                            transactionId = transactionId,
                            onSuccess = {
                                isDeleting = false
                                showDeleteDialog = false
                                onBack()
                            },
                            onError = { error ->
                                isDeleting = false
                                showDeleteDialog = false
                                android.util.Log.e("DeleteTransaction", "Error: $error")
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = redExpense
                    )
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal", color = Color.Gray)
                }
            }
        )
    }
}

@Composable
private fun DetailInfoRow(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )
    }
}

private fun formatRupiah(amount: Int): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return formatter.format(amount).replace("Rp", "Rp ")
}

private fun formatDate(dateString: String?): String {
    return try {
        if (dateString.isNullOrEmpty()) return "-"
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateString ?: "-"
    }
}