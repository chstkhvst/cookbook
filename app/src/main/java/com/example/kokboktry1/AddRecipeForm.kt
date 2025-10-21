package com.example.kokboktry1

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.kokboktry1.ui.theme.Kokboktry1Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kokboktry1.ui.theme.Kokboktry1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeForm(
    onNavigateHome: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onNavigateFavorites: () -> Unit = {},
    onNavigateProfile: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFFF9FBA)
            ) {
                IconButton(onClick = onNavigateHome, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Home, "Главная", tint = Color(0xFFFF0090))
                }
                IconButton(onClick = onNavigateSearch, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Search, "Поиск", tint = Color(0xFFFF0090))
                }
                IconButton(onClick = onNavigateFavorites, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Favorite, "Избранное", tint = Color(0xFFFF0090))
                }
                IconButton(onClick = onNavigateProfile, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Person, "Профиль", tint = Color(0xFFFF0090))
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFC7DD))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = "cookbook",
                fontSize = 40.sp,
                color = Color(0xFFFF0090),
                fontFamily = FontFamily(Font(R.font.abrilfatface))
            )

            Spacer(Modifier.height(8.dp))
            Text(
                text = "Новый рецепт",
                fontSize = 28.sp,
                color = Color(0xFFFF0090),
                fontFamily = FontFamily(Font(R.font.montserrat)),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFDFEC), RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    val montserrat = FontFamily(Font(R.font.montserrat))

                    var name by remember { mutableStateOf("") }
                    var servings by remember { mutableStateOf(1) }
                    var time by remember { mutableStateOf(0) }

                    var category by remember { mutableStateOf("") }
                    var difficulty by remember { mutableStateOf("") }
                    var cuisine by remember { mutableStateOf("") }

                    // Поле: Название
                    Text("Название", color = Color(0xFFFF0090), fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFFFC7DD),
                            unfocusedContainerColor = Color(0xFFFFC7DD),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color(0xFFFF0090)
                        )

                    )

                    Spacer(Modifier.height(8.dp))

                    // Категория (ComboBox)
                    Text("Категория", color = Color(0xFFFF0090), fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
                    ComboBox(label = "Выберите категорию", options = listOf("1", "2", "3")) {
                        category = it
                    }

                    Spacer(Modifier.height(8.dp))

                    // Порции
                    Text("Порции", color = Color(0xFFFF0090), fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (servings > 1) servings-- }) {
                            Text("-", fontSize = 24.sp, color = Color(0xFFFF0090), fontWeight = FontWeight.SemiBold)
                        }
                        Text(servings.toString(), color = Color(0xFFFF0090), fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                        IconButton(onClick = { servings++ }) {
                            Text("+", fontSize = 24.sp, color = Color(0xFFFF0090), fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text("Ингредиенты", color = Color(0xFFFF0090), fontSize = 20.sp, fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { /* TODO: add ingredient */ },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9FBA)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("+ добавить ингредиент", color = Color(0xFFFF0090), fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(16.dp))

                    // Сложность, Кухня, Время
                    Text("Сложность", color = Color(0xFFFF0090), fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    ComboBox("Выберите сложность", listOf("1", "2", "3")) { difficulty = it }

                    Spacer(Modifier.height(8.dp))

                    Text("Кухня", color = Color(0xFFFF0090), fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    ComboBox("Выберите кухню", listOf("1", "3", "2")) { cuisine = it }

                    Spacer(Modifier.height(8.dp))

                    Text("Время (мин)", color = Color(0xFFFF0090), fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (time > 0) time -= 5 }) {
                            Text("-", fontSize = 24.sp, color = Color(0xFFFF0090))
                        }
                        Text("$time", color = Color(0xFFFF0090))
                        IconButton(onClick = { time += 5 }) {
                            Text("+", fontSize = 24.sp, color = Color(0xFFFF0090))
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text("Пошаговый рецепт", color = Color(0xFFFF0090), fontSize = 20.sp, fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { /* TODO: новый шаг */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9FBA)),
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+ новый шаг", color = Color(0xFFFF0090), fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                        }
                        Button(
                            onClick = { /* TODO: добавить фото */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9FBA)),
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+ фото", color = Color(0xFFFF0090), fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBox(label: String, options: List<String>, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            label = { Text(label, color = Color(0xFFFF0090)) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFC7DD),
                unfocusedContainerColor = Color(0xFFFFC7DD),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color(0xFFFF0090)
            )
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it, color = Color(0xFFFF0090)) },
                    onClick = {
                        selected = it
                        expanded = false
                        onSelected(it)
                    }
                )
            }
        }
    }
}

// Преview для визуализации в Android Studio
@Preview(showBackground = true)
@Composable
fun AddRecipeFormPreview() {
    Kokboktry1Theme {
        AddRecipeForm()
    }
}


