package com.example.dompetku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    // State
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Colors (mirip Tailwind config)
    val backgroundLight = Color(0xFFF6F7F8)
    val primaryColor = Color(0xFF137FEC)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundLight)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
                .widthIn(max = 400.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ---------- LOGO ----------
            AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuA_bsNA9-pFCZjaYk8PjNuRsaOYThssO5LLDMKG9_BdDvxYaxlAQj90ESJlfTmMiqbz-ZoCb4tPmkQqvCP3V1rFaJNzpiqgeYGBglFDC_yVqUBzXZB1jEFMIFFQJYbBg-LXusa97APxTBWWKl2I-6vaCOsJ0Ek89iQLILATW2nvkEv29nGDP1Ycpus685PW2vzdD5j8y56WFcbJNmDyeRADILPouSZfkvbUkrwFE_p1cBCNOTrsP3Mgxx44NGCyvuk5YYiwpeohBaA",
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- HEADLINE ----------
            Text(
                text = "Selamat Datang Kembali",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 6.dp),
            )

            Text(
                text = "Masuk untuk mengelola keuangan Anda",
                fontSize = 16.sp,
                color = Color(0xFF666666)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ---------- EMAIL ----------
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email atau Username") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    focusedLabelColor = primaryColor
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // ---------- PASSWORD ----------
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Kata Sandi") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle Password",
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    focusedLabelColor = primaryColor
                ),
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None else PasswordVisualTransformation()
            )

            // ---------- Forgot password ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onForgotPasswordClick() }) {
                    Text(
                        text = "Lupa Kata Sandi?",
                        color = primaryColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---------- BUTTON LOGIN ----------
            Button(
                onClick = { onLoginClick(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF269AFD),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Masuk", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ---------- REGISTER ----------
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Belum punya akun?",
                    color = Color(0xFF666666),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.width(6.dp))

                TextButton(
                    onClick = { onRegisterClick() },
                    contentPadding = PaddingValues(0.dp),    // ⬅️ Hilangkan padding default
                    modifier = Modifier.height(IntrinsicSize.Min)
                ) {
                    Text(
                        "Daftar",
                        color = primaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        Surface {
            LoginScreen()
        }
    }
}

