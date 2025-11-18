package com.example.dompetku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String) -> Unit = { _, _, _ -> },
    onLoginClick: () -> Unit = {}
) {

    // State
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Colors
    val primary = Color(0xFF0D6EFD)   // biru
    val lightBg = Color(0xFFF8F9FA)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBg)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center)
                .widthIn(max = 400.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ---------- LOGO ----------
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(primary.copy(alpha = 0.2f), shape = RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Logo",
                    tint = primary,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "Buat Akun Baru",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                "Selamat datang! Mari mulai kelola keuanganmu.",
                fontSize = 14.sp,
                color = Color(0xFF6c757d),
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(Modifier.height(32.dp))

            // ---------- FULL NAME ----------
            OutlinedInputField(
                value = fullName,
                onChange = { fullName = it },
                label = "Nama Lengkap",
                icon = Icons.Outlined.Person,
                primary = primary
            )

            Spacer(Modifier.height(16.dp))

            // ---------- EMAIL ----------
            OutlinedInputField(
                value = email,
                onChange = { email = it },
                label = "Email",
                icon = Icons.Outlined.Mail,
                primary = primary
            )

            Spacer(Modifier.height(16.dp))

            // ---------- PASSWORD ----------
            OutlinedPasswordField(
                value = password,
                onChange = { password = it },
                visible = passwordVisible,
                onToggle = { passwordVisible = !passwordVisible },
                label = "Kata Sandi",
                primary = primary
            )

            Spacer(Modifier.height(16.dp))

            // ---------- CONFIRM PASSWORD ----------
            OutlinedPasswordField(
                value = confirmPassword,
                onChange = { confirmPassword = it },
                visible = confirmPasswordVisible,
                onToggle = { confirmPasswordVisible = !confirmPasswordVisible },
                label = "Konfirmasi Kata Sandi",
                primary = primary
            )

            Spacer(Modifier.height(24.dp))

            // ---------- REGISTER BUTTON ----------
            Button(
                onClick = { onRegisterClick(fullName, email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                )
            ) {
                Text("Daftar", fontSize = 18.sp)
            }

            Spacer(Modifier.height(16.dp))

            // ---------- LOGIN LINK ----------
            Row(
                verticalAlignment = Alignment.CenterVertically
            )  {
                Text("Sudah punya akun?", color = Color(0xFF6c757d))
                Spacer(Modifier.width(6.dp))
                TextButton(
                    onClick = onLoginClick,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "Masuk di sini",
                        color = primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// -------------------------- REUSABLE TEXT FIELDS -------------------------

@Composable
fun OutlinedInputField(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    primary: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, null) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primary,
            focusedLabelColor = primary
        )
    )
}

@Composable
fun OutlinedPasswordField(
    value: String,
    onChange: (String) -> Unit,
    visible: Boolean,
    onToggle: () -> Unit,
    label: String,
    primary: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        leadingIcon = { Icon(Icons.Outlined.Lock, null) },
        trailingIcon = {
            IconButton(onClick = onToggle) {
                Icon(
                    if (visible) Icons.Outlined.Visibility
                    else Icons.Outlined.VisibilityOff,
                    contentDescription = null
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primary,
            focusedLabelColor = primary
        ),
        visualTransformation =
            if (visible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewRegister() {
    RegisterScreen()
}
