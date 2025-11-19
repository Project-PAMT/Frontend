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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dompetku.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val state = viewModel.registerState.collectAsState().value

    val primaryColor = Color(0xFF0D6EFD)
    val backgroundLight = Color(0xFFFFFFFF)
    val textGray = Color(0xFF9E9E9E)
    val textBlack = Color(0xFF2C3E50)

    LaunchedEffect(state.success) {
        if (state.success) {
            onRegisterSuccess()
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

            Spacer(Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_dompetku),
                contentDescription = "Logo DompetKu",
                modifier = Modifier
                    .width(280.dp)
                    .height(120.dp)
            )

            Spacer(Modifier.height(32.dp))

            Text(
                "Nama Lengkap",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = textBlack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedInputField(
                value = fullName,
                onChange = { fullName = it },
                placeholder = "Masukkan nama lengkap Anda",
                icon = Icons.Outlined.Person,
                primary = primaryColor
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Email",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = textBlack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedInputField(
                value = email,
                onChange = { email = it },
                placeholder = "Masukkan email Anda",
                icon = Icons.Outlined.Mail,
                primary = primaryColor
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Kata Sandi",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = textBlack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedPasswordField(
                value = password,
                onChange = { password = it },
                visible = passwordVisible,
                onToggle = { passwordVisible = !passwordVisible },
                placeholder = "Masukkan kata sandi Anda",
                primary = primaryColor
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Konfirmasi Kata Sandi",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = textBlack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedPasswordField(
                value = confirmPassword,
                onChange = { confirmPassword = it },
                visible = confirmPasswordVisible,
                onToggle = { confirmPasswordVisible = !confirmPasswordVisible },
                placeholder = "Masukkan kata sandi Anda",
                primary = primaryColor
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    if (password == confirmPassword) {
                        viewModel.register(fullName, email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor
                ),
                enabled = !state.loading
            ) {
                Text("Daftar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                Text("Sudah punya akun?", color = textGray, fontSize = 15.sp)
                Spacer(Modifier.width(6.dp))
                TextButton(
                    onClick = onLoginClick,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "Masuk",
                        color = primaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun OutlinedInputField(
    value: String,
    onChange: (String) -> Unit,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    primary: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        placeholder = { Text(placeholder, color = Color(0xFFBDBDBD), fontSize = 15.sp) },
        leadingIcon = { Icon(icon, null, tint = Color(0xFF9E9E9E)) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primary,
            unfocusedBorderColor = Color(0xFFE0E0E0),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Composable
fun OutlinedPasswordField(
    value: String,
    onChange: (String) -> Unit,
    visible: Boolean,
    onToggle: () -> Unit,
    placeholder: String,
    primary: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        placeholder = { Text(placeholder, color = Color(0xFFBDBDBD), fontSize = 15.sp) },
        leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = Color(0xFF9E9E9E)) },
        trailingIcon = {
            IconButton(onClick = onToggle) {
                Icon(
                    if (visible) Icons.Outlined.Visibility
                    else Icons.Outlined.VisibilityOff,
                    contentDescription = null,
                    tint = Color(0xFF9E9E9E)
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primary,
            unfocusedBorderColor = Color(0xFFE0E0E0),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        visualTransformation =
            if (visible) VisualTransformation.None else PasswordVisualTransformation()
    )
}
