package com.example.kokboktry1

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.kokboktry1.ui.theme.Kokboktry1Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomepageScreen(
    onNavigateHome: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onNavigateFavorites: () -> Unit = {},
    onNavigateProfile: () -> Unit = {},
    onRecipeDetails: () -> Unit = {},
    onToggleFavorite: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFFF9FBA),
                tonalElevation = 4.dp
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
        },
        contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFC7DD))
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Заголовок
            Text(
                text = "cookbook",
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.abrilfatface)),
                color = Color(0xFFFF0090)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Поиск
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("поиск по названию", color = Color(0xFFFF0090)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Поиск", tint = Color(0xFFFF0090)) },
                modifier = Modifier
                    .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFFF9FBA),
                        unfocusedContainerColor = Color(0xFFFF9FBA),
                        focusedTextColor = Color(0xFFFF0090),
                        unfocusedTextColor = Color(0xFFFF0090),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                shape = RoundedCornerShape(30.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Комбобоксы
            HorizontalScrollableRow(
                modifier = Modifier.fillMaxWidth()

            ) {
                ComboBoxPlaceholder("категория")
                ComboBoxPlaceholder("сложность")
                ComboBoxPlaceholder("кухня")

            }

            Spacer(modifier = Modifier.height(10.dp))


            // Кнопки фильтров
            HorizontalScrollableRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                FilterButton("сначала новые")
                FilterButton("сначала старые")
                FilterButton("до 7 ингредиентов")
                FilterButton("до 10 ингредиентов")
                FilterButton("до 30 мин")
                FilterButton("< 1 часа")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Карточка рецепта
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFDFEC), RoundedCornerShape(20.dp))
                    .padding(12.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(100.dp, 70.dp)
                                .background(Color.LightGray, RoundedCornerShape(10.dp))
                        ) {
                            Text(
                                text = "Фото",
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.DarkGray
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Эклеры",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF0090),
                                fontFamily = FontFamily(Font(R.font.montserrat))
                            )
                            Text("Категория: десерт", color = Color(0xFFFF0090))
                            Text("Сложность: 5★", color = Color(0xFFFF0090))
                            Text("Кухня: французская", color = Color(0xFFFF0090))
                            Text("Количество порций: 10", color = Color(0xFFFF0090))
                            Text("Время приготовления: 2 часа", color = Color(0xFFFF0090))
                        }

                }

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Button(
                            onClick = onRecipeDetails,
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9FBA))
                        ) {
                            Text(
                                "Подробнее",
                                color = Color(0xFFFF0090),
                                fontFamily = FontFamily(Font(R.font.montserrat)),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp
                            )
                        }
                        IconButton(onClick = onToggleFavorite) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Избранное",
                                tint = Color(0xFFFF0090)
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun HorizontalScrollableRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(scrollState) // ГОРИЗОНТАЛЬНЫЙ СКРОЛЛ
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp) // РАССТОЯНИЕ МЕЖДУ КОМБОБОКСАМИ
    ) {
        content()
    }
}
//@Composable
//fun ComboBoxPlaceholder(label: String) {
//    var expanded by remember { mutableStateOf(false) }
//    var selectedText by remember { mutableStateOf("") }
//
//    Box {
//        OutlinedTextField(
//            value = selectedText,
//            onValueChange = {},
//            label = { Text(label,
//                    color = Color(0xFFFF0090),
//
//                ) },
//            trailingIcon = {
//                Icon(
//                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                    contentDescription = null
//                )
//            },
//            readOnly = true,
//            modifier = Modifier.width(110.dp),
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = Color(0xFFFF9FBA),
//                unfocusedContainerColor = Color(0xFFFF9FBA),
//                unfocusedIndicatorColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//            )
//        )
//    }
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBoxPlaceholder(label: String) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    val options = listOf("Вариант 1", "Вариант 2", "Вариант 3") // варианты выбора

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            label = {
                Text(
                    label,
                    color = Color(0xFFFF0090),
                    fontSize = 12.sp
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
//                .width()
                .menuAnchor(), //  связывает поле с меню
            readOnly = true, //  только чтение
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFF9FBA),
                unfocusedContainerColor = Color(0xFFFF9FBA),
                focusedTextColor = Color(0xFFFF0090),
                unfocusedTextColor = Color(0xFFFF0090),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(30.dp)
        )

        // ВЫПАДАЮЩЕЕ МЕНЮ
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            fontSize = 12.sp,
                            color = Color(0xFFFF0090)
                        )
                    },
                    onClick = {
                        selectedText = option // сохраняем выбор
                        expanded = false // закрываем меню
                    }
                )
            }
        }
    }
}
@Composable
fun FilterButton(text: String) {
    var isPressed by remember { mutableStateOf(false) }

    Button(
        onClick = { isPressed = !isPressed },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) Color(0xFFFF9FBA) else Color(0xFFFFDFEC)
        )
    ) {
        Text(
            text,
            color =  Color(0xFFFF0090) ,
            fontFamily = FontFamily(Font(R.font.montserrat)),
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomepageScreenPreview() {
    Kokboktry1Theme {
        HomepageScreen()
    }
}