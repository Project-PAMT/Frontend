//package com.example.dompetku.screen
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.dompetku.data.dto.TransactionResponse
//
//@Composable
//fun TransactionsCard(transactions: List<TransactionResponse>) {
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text("Transaksi Terakhir", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//
//        Spacer(Modifier.height(8.dp))
//
//        transactions.forEach { trx ->
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.White)
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(12.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Column {
//                        Text(trx.note, fontWeight = FontWeight.Bold)
//                        Text(trx.date, fontSize = 12.sp, color = Color.Gray)
//                    }
//
//                    Text(
//                        text = "Rp ${trx.amount}",
//                        color = if (trx.type == "income") Color(0xFF1E88E5) else Color(0xFFEF4444),
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
//        }
//    }
//}
