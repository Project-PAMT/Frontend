package com.example.dompetku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.dompetku.screen.LoginScreen
import com.example.dompetku.screen.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    RegisterScreen()  // ⬅️ Panggil Compose kamu di sini
                }
            }
        }
    }
}
