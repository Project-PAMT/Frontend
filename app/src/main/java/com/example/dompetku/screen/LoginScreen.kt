package com.example.dompetku.screen

import com.example.dompetku.R
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dompetku.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit = {},
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val state = viewModel.loginState.collectAsState().value

    val primaryColor = Color(0xFF0D6EFD)
    val backgroundLight = Color(0xFFFFFFFF)
    val textGray = Color(0xFF9E9E9E)
    val textBlack = Color(0xFF2C3E50)

    LaunchedEffect(state.success) {
        if (state.success) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundLight)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.TopCenter)
                .widthIn(max = 400.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(32.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_dompetku),
                contentDescription = "Logo DompetKu",
                modifier = Modifier
                    .width(280.dp)
                    .height(120.dp)
            )

            Spacer(Modifier.height(32.dp))

            Text(
                "Email",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = textBlack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Masukkan email Anda", color = Color(0xFFBDBDBD), fontSize = 15.sp) },
                leadingIcon = { Icon(Icons.Outlined.Mail, null, tint = Color(0xFF9E9E9E)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(Modifier.height(12.dp))

            Text(
                "Kata Sandi",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = textBlack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Masukkan kata sandi Anda", color = Color(0xFFBDBDBD), fontSize = 15.sp) },
                leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = Color(0xFF9E9E9E)) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Outlined.Visibility
                            else Icons.Outlined.VisibilityOff,
                            contentDescription = null,
                            tint = Color(0xFF9E9E9E)
                        )
                    }
                },
                visualTransformation =
                    if (passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { viewModel.login(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !state.loading,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor
                )
            ) {
                Text("Masuk", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            if (state.loading) {
                Spacer(Modifier.height(16.dp))
                CircularProgressIndicator(color = primaryColor)
            }

            state.error?.let {
                Spacer(Modifier.height(16.dp))
                Text(it, color = Color.Red)
            }

            Spacer(Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Belum punya akun?", color = textGray, fontSize = 15.sp)
                Spacer(Modifier.width(6.dp))
                TextButton(
                    onClick = onRegisterClick,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "Daftar",
                        color = primaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}