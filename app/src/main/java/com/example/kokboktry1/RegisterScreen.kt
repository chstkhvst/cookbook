package com.example.kokboktry1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kokboktry1.ui.theme.Kokboktry1Theme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},          // Колбэк при успешном входе
    onNavigateToLogin: () -> Unit = {}     // Колбэк для перехода к регистрации
) {
    // Состояния для текстовых полей
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatpassword by remember { mutableStateOf("") }

    val Pink = Color(0xFFFF9FBA)
    val WhitePink = Color(0xFFFFDFEC)
    val BrightPink = Color(0xFFFF0090)
    val LightPink = Color(0xFFFFC7DD)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightPink)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
            text = "РЕГИСТРАЦИЯ",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.montserrat)),
            color = BrightPink,
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = login,
            onValueChange = { newText -> login = newText },
            label = { Text("логин",
                 fontFamily = FontFamily(Font(R.font.montserrat)),
                fontWeight = FontWeight.SemiBold,
                ) },
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
            onValueChange = { newText ->
                password = newText
            },
            label = { Text("пароль",
                fontFamily = FontFamily(Font(R.font.montserrat)),
                fontWeight = FontWeight.SemiBold,) },
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(), // Точки вместо текста
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Pink,
                focusedContainerColor = Pink,
                unfocusedTextColor = BrightPink,
                focusedTextColor = BrightPink,
                unfocusedLabelColor = BrightPink,
                focusedLabelColor = BrightPink,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(30.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value = repeatpassword,
            onValueChange = { newText ->
                repeatpassword = newText
            },
            label = { Text("повторите пароль",
                fontFamily = FontFamily(Font(R.font.montserrat)),
                fontWeight = FontWeight.SemiBold,) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(), // Точки вместо текста
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Pink,
                focusedContainerColor = Pink,
                unfocusedTextColor = BrightPink,
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
                if (login.isNotEmpty() && password.isNotEmpty() && password == repeatpassword) {
                    onRegisterSuccess()
                }
            },
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BrightPink
            )
        ) {
            Text(
                "Зарегистрироваться",
                color = Pink,
                fontFamily = FontFamily(Font(R.font.montserrat)),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
        }

        // переход к регситрации
        TextButton(onClick = onNavigateToLogin) {
            Text("Уже есть аккаунт? Войти",
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
fun RegisterScreenPreview() {
    Kokboktry1Theme {
        RegisterScreen()
    }
}