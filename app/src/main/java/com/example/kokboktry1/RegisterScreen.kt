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
import androidx.lifecycle.viewmodel.compose.viewModel
import vm.RegisterViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    // Состояния для текстовых полей
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatpassword by remember { mutableStateOf("") }

    val viewModel: RegisterViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    // Обработка успешной регистрации
    LaunchedEffect(state.success) {
        if (state.success) onRegisterSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC7DD)) // LightPink
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "cookbook",
            fontSize = 64.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.abrilfatface)),
            color = Color(0xFFFF0090), // BrightPink
        )
        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = "РЕГИСТРАЦИЯ",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.montserrat)),
            color = Color(0xFFFF0090), // BrightPink
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
                unfocusedContainerColor = Color(0xFFFF9FBA), // Pink
                focusedContainerColor = Color(0xFFFF9FBA), // Pink
                unfocusedTextColor = Color(0xFFFF0090), // BrightPink
                focusedTextColor = Color(0xFFFF0090), // BrightPink
                unfocusedLabelColor = Color(0xFFFF0090), // BrightPink
                focusedLabelColor = Color(0xFFFF0090), // BrightPink
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
                unfocusedContainerColor = Color(0xFFFF9FBA), // Pink
                focusedContainerColor = Color(0xFFFF9FBA), // Pink
                unfocusedTextColor = Color(0xFFFF0090), // BrightPink
                focusedTextColor = Color(0xFFFF0090), // BrightPink
                unfocusedLabelColor = Color(0xFFFF0090), // BrightPink
                focusedLabelColor = Color(0xFFFF0090), // BrightPink
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(30.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value = repeatpassword,
            onValueChange = { newText -> repeatpassword = newText },
            label = {
                Text(
                    "повторите пароль",
                    fontFamily = FontFamily(Font(R.font.montserrat)),
                    fontWeight = FontWeight.SemiBold,
                )
            },
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFFF9FBA), // Pink
                focusedContainerColor = Color(0xFFFF9FBA), // Pink
                unfocusedTextColor = Color(0xFFFF0090), // BrightPink
                focusedTextColor = Color(0xFFFF0090), // BrightPink
                unfocusedLabelColor = Color(0xFFFF0090), // BrightPink
                focusedLabelColor = Color(0xFFFF0090), // BrightPink
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(30.dp)
        )

        // Показываем ошибку, если есть
        state.error?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = Color.Red,
                fontFamily = FontFamily(Font(R.font.montserrat)),
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                when {
                    login.isEmpty() || password.isEmpty() || repeatpassword.isEmpty() -> {
                        // Можно показать ошибку через ViewModel или локально
                    }
                    password != repeatpassword -> {
                        // Можно показать ошибку через ViewModel или локально
                    }
                    else -> {
                        viewModel.register(login, password)
                    }
                }
            },
            enabled = !state.isLoading,
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF0090) // BrightPink
            )
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color(0xFFFF9FBA), // Pink
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(
                    "Зарегистрироваться",
                    color = Color(0xFFFF9FBA), // Pink
                    fontFamily = FontFamily(Font(R.font.montserrat)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }
        }

        // Кнопка перехода к логину
        TextButton(onClick = onNavigateToLogin) {
            Text(
                "Уже есть аккаунт? Войти",
                fontFamily = FontFamily(Font(R.font.montserrat)),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFFFF0090), // BrightPink
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