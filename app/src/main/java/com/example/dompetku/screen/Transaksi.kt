package com.example.dompetku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun TransaksiScreen(onBack: () -> Unit = {}) {
    val context = LocalContext.current
    var isIncome by remember { mutableStateOf(true) }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf<String?>(null) }
    var categoryExpanded by remember { mutableStateOf(false) }
    var dateText by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(HeaderBlueStart, HeaderBlueEnd)
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(Color(0xFF269AFD))
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
                    text = "Transaksi",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(BackgroundLight)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
                        color = if (isIncome) GreenIncome.copy(alpha = 0.4f) else Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                        onClick = { isIncome = true }
                    ) {
                        Box(modifier = Modifier.padding(vertical = 12.dp), contentAlignment = Alignment.Center) {
                            Text("Pemasukan", fontWeight = FontWeight.SemiBold, color = Color.Black)
                        }
                    }
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
                        color = if (!isIncome) RedExpense.copy(alpha = 0.3f) else Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                        onClick = { isIncome = false }
                    ) {
                        Box(modifier = Modifier.padding(vertical = 12.dp), contentAlignment = Alignment.Center) {
                            Text("Pengeluaran", fontWeight = FontWeight.SemiBold, color = Color.Black)
                        }
                    }
                }

                Text("Jumlah", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it.filter { ch -> ch.isDigit() } },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Text("Rp") },
                    placeholder = { Text("0") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Kategori", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                Box {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                        onClick = { categoryExpanded = true }
                    ) {
                        Box(modifier = Modifier.fillMaxWidth().padding(14.dp)) {
                            Text(text = category ?: "Pilih Kategori", color = Color.Black)
                        }
                    }
                    DropdownMenu(expanded = categoryExpanded, onDismissRequest = { categoryExpanded = false }) {
                        listOf("Makan", "Bensin", "Tabungan").forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    category = opt
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }

                Text("Tanggal", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                    onClick = {
                        val cal = Calendar.getInstance()
                        val dlg = android.app.DatePickerDialog(
                            context,
                            { _, y, m, d -> dateText = "$d/${m + 1}/$y" },
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)
                        )
                        dlg.show()
                    }
                ) {
                    Box(modifier = Modifier.fillMaxWidth().padding(14.dp)) {
                        Text(text = if (dateText.isEmpty()) "Masukkan Tanggal" else dateText, color = Color.Black)
                    }
                }

                Text("Deskripsi", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    shape = RoundedCornerShape(12.dp),
                    placeholder = { Text("Masukkan Deskripsi") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (amount.isNotEmpty() && category != null && dateText.isNotEmpty()) {
                            showSuccessDialog = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF269AFD),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Tambah",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }

    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                onBack()
            },
            title = {
                Text(
                    text = "Berhasil!",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("Data Transaksi Telah Ditambahkan")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        onBack()
                    }
                ) {
                    Text("OK")
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransaksiPreview() {
    TransaksiScreen()
}