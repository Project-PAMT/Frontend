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
val DarkCardBackground = Color(0xFF1E293B) // Warna card gelap


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
            .background(BackgroundLight) // Background tetap putih
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Spacer(Modifier.height(16.dp))

            // ---------------- APP BAR ----------------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )

                    Text(
                        text = "Halo, Selamat Datang",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))


            // ---------------- TOTAL SALDO ----------------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Total Saldo Anda", 
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    Icon(
                        Icons.Outlined.VisibilityOff, 
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    "Rp15.750.000",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(Modifier.height(20.dp))


            // ---------------- SUMMARY CARDS (Dark Style) ----------------
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                SummaryCard(
                    title = "Pemasukan",
                    amount = "Rp8.500.000",
                    color = GreenIncome,
                    icon = Icons.Outlined.ArrowDownward
                )

                SummaryCard(
                    title = "Pengeluaran",
                    amount = "Rp4.250.000",
                    color = RedExpense,
                    icon = Icons.Outlined.ArrowUpward
                )
            }
        }


        // ---------------- FLOATING ACTION BUTTON ----------------
        Box(
    modifier = Modifier
        .fillMaxSize(),
    contentAlignment = Alignment.BottomEnd
) {
    FloatingActionButton(
        onClick = onAddClick,
        containerColor = Primary,
        modifier = Modifier
            .padding(end = 24.dp, bottom = 24.dp)
    ) {
        Icon(Icons.Outlined.Add, contentDescription = null, tint = Color.White)
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
@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    DashboardScreen()
}