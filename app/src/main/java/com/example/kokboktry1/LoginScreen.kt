package com.example.kokboktry1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kokboktry1.ui.theme.Kokboktry1Theme


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},          //Колбэк при успешном входе
    onNavigateToRegister: () -> Unit = {}     //Колбэк для перехода к регистрации
) {
    val Pink = Color(0xFFFF9FBA)
    val WhitePink = Color(0xFFFFDFEC)
    val BrightPink = Color(0xFFFF0090)
    val LightPink = Color(0xFFFFC7DD)

    // Состояния для текстовых полей
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .background(LightPink)
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "cookbook",
            fontSize = 64.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.abrilfatface)),
            color = BrightPink,
        )
        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = "ВХОД",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.montserrat)),
            color = BrightPink,
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = login,
            onValueChange = { newText -> login = newText },
            label = {
                Text(
                    "логин",
                    fontFamily = FontFamily(Font(R.font.montserrat)),
                    fontWeight = FontWeight.SemiBold,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Pink,
                focusedContainerColor = Pink,
                unfocusedTextColor = BrightPink,
                focusedTextColor =BrightPink,
                unfocusedLabelColor = BrightPink,
                focusedLabelColor = BrightPink,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(30.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { newText -> password = newText },
            label = {
                Text(
                    "пароль",
                    fontFamily = FontFamily(Font(R.font.montserrat)),
                    fontWeight = FontWeight.SemiBold,
                )
            },
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Pink,
                focusedContainerColor = Pink,
                unfocusedTextColor =BrightPink,
                focusedTextColor = BrightPink,
                unfocusedLabelColor = BrightPink,
                focusedLabelColor = BrightPink,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(30.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (login.isNotEmpty() && password.isNotEmpty()) {
                    onLoginSuccess()
                }
            },
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrightPink
            )
        ) {
            Text(
                "Войти",
                color = Pink,
                fontFamily = FontFamily(Font(R.font.montserrat)),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
        }

        // КНОПКА ПЕРЕХОДА К РЕГИСТРАЦИИ
        TextButton(onClick = onNavigateToRegister) {
            Text(
                "Нет аккаунта? Зарегистрируйтесь",
                fontFamily = FontFamily(Font(R.font.montserrat)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = BrightPink,
            )
        }
    }
}

// Преview для визуализации в Android Studio
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Kokboktry1Theme {
        LoginScreen()
    }
}