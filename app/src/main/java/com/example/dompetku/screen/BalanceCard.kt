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
//
//@Composable
//fun BalanceCard(balance: Double, income: Double, expense: Double) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text("Saldo", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//            Text("Rp $balance", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//
//            Spacer(Modifier.height(12.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text("Income: Rp $income", color = Color(0xFF1E88E5))
//                Text("Expense: Rp $expense", color = Color(0xFFEF4444))
//            }
//        }
//    }
//}
