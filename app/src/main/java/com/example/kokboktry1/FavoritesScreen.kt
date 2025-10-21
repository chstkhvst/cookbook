package com.example.kokboktry1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kokboktry1.ui.theme.Kokboktry1Theme
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
@Composable
fun FavoritesScreen(
    onNavigateHome: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onNavigateFavorites: () -> Unit = {},
    onNavigateProfile: () -> Unit = {}

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
    ){ innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFC7DD))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFC7DD))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Заголовок
                Text(
                    text = "cookbook",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.abrilfatface)),
                    color = Color(0xFFFF0090)
                )

                Spacer(modifier = Modifier.height(12.dp))


                Text(
                    text = "ИЗБРАННОЕ",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF0090),
                    fontFamily = FontFamily(Font(R.font.montserrat))
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Блок рецепта
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
                                Text(
                                    text = "Категория: десерт",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFFF0090),
                                    fontFamily = FontFamily(Font(R.font.montserrat))
                                )
                                Text(
                                    text = "Сложность: 5★",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFFF0090),
                                    fontFamily = FontFamily(Font(R.font.montserrat))
                                )
                                Text(
                                    text = "Кухня: французская",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFFF0090),
                                    fontFamily = FontFamily(Font(R.font.montserrat))
                                )
                                Text(
                                    text = "Количество порций: 10",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFFF0090),
                                    fontFamily = FontFamily(Font(R.font.montserrat))
                                )
                            }
                        }
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = onNavigateFavorites,
                                    shape = RoundedCornerShape(20.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9FBA)),

                                    ) {
                                    Text(
                                        "Подробнее",
                                        color = Color(0xFFFF0090),
                                        fontFamily = FontFamily(Font(R.font.montserrat)),
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp
                                    )
                                }
                                IconButton(onClick = onNavigateFavorites) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Избранное",
                                        tint = Color(0xFFFF0090),

                                        )
                                }
                            }
                    }

                    }
                }

            }
        }

    }

// Преview для визуализации в Android Studio
@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    Kokboktry1Theme {
        FavoritesScreen()
    }
}