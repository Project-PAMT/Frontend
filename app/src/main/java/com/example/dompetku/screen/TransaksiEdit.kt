package com.example.dompetku.screen

import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
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
import com.example.dompetku.data.dto.TransactionRequest
import com.example.dompetku.utils.Prefs
import com.example.dompetku.utils.convertToYMD
import com.example.dompetku.viewmodel.CategoryViewModel
import com.example.dompetku.viewmodel.TransactionViewModel
import java.util.Calendar

@Composable
fun TransaksiEditScreen(
    transactionId: Int,
    onBack: () -> Unit = {}
) {
    val categoryVM: CategoryViewModel = viewModel()
    val transactionVM: TransactionViewModel = viewModel()

    val primaryColor = Color(0xFF0D6EFD)
    val context = LocalContext.current
    val prefs = Prefs(context)
    val token = prefs.getToken()

    var isIncome by remember { mutableStateOf(true) }
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedCategoryName by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var categoryExpanded by remember { mutableStateOf(false) }
    var dateText by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Load transaction detail dan populate form
    LaunchedEffect(transactionId) {
        token?.let { safeToken ->
            transactionVM.getTransactionDetail(safeToken, transactionId)
        }
    }

    LaunchedEffect(transactionVM.transactionDetail.value) {
        transactionVM.transactionDetail.value?.let { trans ->
            name = trans.title
            amount = trans.amount.toString()
            selectedCategoryName = trans.category_name ?: ""
            selectedCategoryId = trans.category_id
            isIncome = trans.type == "income"
            dateText = trans.date
            description = trans.description ?: ""
        }
    }

    LaunchedEffect(token) {
        token?.let { safeToken ->
            categoryVM.loadCategories(safeToken)
        }
    }

    LaunchedEffect(transactionVM.updateSuccess.value) {
        transactionVM.updateSuccess.value?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            transactionVM.updateSuccess.value = null
            onBack()
        }
    }

    LaunchedEffect(transactionVM.error.value) {
        transactionVM.error.value?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E90FF))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Edit Transaksi",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(Color(0xFFF5F5F5))
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { isIncome = true }
                        .background(if (isIncome) Color(0xFF1E90FF) else Color.White)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Pemasukan",
                        color = if (isIncome) Color.White else Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { isIncome = false }
                        .background(if (!isIncome) Color(0xFF1E90FF) else Color.White)
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Pengeluaran",
                        color = if (!isIncome) Color.White else Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(22.dp))

            // Name
            Text("Nama", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Masukkan Nama") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(Modifier.height(14.dp))

            // Amount
            Text("Jumlah", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it.filter { ch -> ch.isDigit() } },
                leadingIcon = { Text("Rp") },
                placeholder = { Text("0") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(Modifier.height(14.dp))

            // Category Dropdown
            Text("Kategori", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Box {
                OutlinedTextField(
                    value = selectedCategoryName,
                    onValueChange = {},
                    placeholder = { Text("Pilih Kategori") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { categoryExpanded = true }) {
                            Icon(
                                imageVector = if (categoryExpanded)
                                    Icons.Default.ArrowDropUp
                                else
                                    Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown"
                            )
                        }
                    },
                    shape = RoundedCornerShape(14.dp)
                )

                DropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    when {
                        categoryVM.loading.value -> {
                            DropdownMenuItem(
                                text = { Text("Memuat...") },
                                onClick = {}
                            )
                        }

                        categoryVM.categories.value.isEmpty() -> {
                            DropdownMenuItem(
                                text = { Text("Tidak ada kategori") },
                                onClick = {}
                            )
                        }

                        else -> {
                            categoryVM.categories.value.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item.name) },
                                    onClick = {
                                        selectedCategoryName = item.name
                                        selectedCategoryId = item.id
                                        categoryExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // Date picker
            Text("Tanggal", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                modifier = Modifier.clickable {
                    val cal = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        { _, y, m, d ->
                            dateText = "$d/${m + 1}/$y"
                        },
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Text(
                        if (dateText.isEmpty()) "Masukkan Tanggal" else dateText,
                        color = Color.Black
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // Description
            Text("Deskripsi", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Masukkan Deskripsi") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(Modifier.height(26.dp))

            // Submit Button
            Button(
                onClick = {
                    if (name.isBlank() || amount.isBlank() ||
                        selectedCategoryId == null || dateText.isBlank()
                    ) {
                        Toast.makeText(context, "Lengkapi semua field", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val request = TransactionRequest(
                        title = name,
                        amount = amount.toInt(),
                        type = if (isIncome) "income" else "expense",
                        category_id = selectedCategoryId,
                        date = convertToYMD(dateText),
                        description = description
                    )

                    token?.let {
                        transactionVM.updateTransaction(
                            it,
                            transactionId,
                            request,
                            onSuccess = { /* KOSONG AJA */ },
                            onError = { errorMsg ->
                                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                            }
                        )
                    } ?: Toast.makeText(context, "User belum login", Toast.LENGTH_SHORT).show()
                },
                enabled = !transactionVM.loading.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White
                )
            ) {
                Text(
                    if (transactionVM.loading.value) "Memproses..." else "Simpan Perubahan",
                    fontWeight = FontWeight.Bold
                )
            }

            // Error handling
            categoryVM.error.value?.let {
                Text(it, color = Color.Red)
            }

            transactionVM.error.value?.let {
                Text(it, color = Color.Red)
            }
        }
    }
}