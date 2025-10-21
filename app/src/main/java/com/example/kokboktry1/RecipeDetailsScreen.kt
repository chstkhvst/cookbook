package com.example.kokboktry1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kokboktry1.ui.theme.Kokboktry1Theme

@Composable
fun RecipeDetailsScreen(
    recipeName: String = "Рецепт",
    servingsInitial: Int = 10,
    ingredients: List<String> = listOf(
        ""
    ),
    steps: List<Pair<String, Int?>> = listOf(
        "" to R.drawable.ic_launcher_background
    ),
    onNavigateHome: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onNavigateFavorites: () -> Unit = {},
    onNavigateProfile: () -> Unit = {}
) {
    var servings by remember { mutableStateOf(servingsInitial) }

    Scaffold(
        bottomBar = {
            BottomAppBar(containerColor = Color(0xFFFF9FBA)) {
                IconButton(onClick = onNavigateHome, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Home, contentDescription = "Главная", tint = Color(0xFFFF0090))
                }
                IconButton(onClick = onNavigateSearch, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Search, contentDescription = "Поиск", tint = Color(0xFFFF0090))
                }
                IconButton(onClick = onNavigateFavorites, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Favorite, contentDescription = "Избранное", tint = Color(0xFFFF0090))
                }
                IconButton(onClick = onNavigateProfile, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Person, contentDescription = "Профиль", tint = Color(0xFFFF0090))
                }
            }
        }
    ) { innerPadding ->
        val montserrat = FontFamily(Font(R.font.montserrat))
        val abril = FontFamily(Font(R.font.abrilfatface))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFC7DD))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Заголовок
            Text(
                text = "cookbook",
                fontFamily = abril,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = Color(0xFFFF0090)
            )

            Spacer(Modifier.height(8.dp))

            // Название рецепта
            Text(
                text = recipeName,
                fontFamily = montserrat,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
                color = Color(0xFFFF0090)
            )

            Spacer(Modifier.height(16.dp))

            // Фото рецепта
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFF9FBA)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background), ///////////
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )
            }

            Spacer(Modifier.height(12.dp))

            // Количество порций
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Порции:", color = Color(0xFFFF0090), fontFamily = montserrat, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Spacer(Modifier.width(10.dp))
                IconButton(onClick = { if (servings > 1) servings-- }) {
                    Text("-", fontSize = 28.sp, color = Color(0xFFFF0090), fontWeight = FontWeight.SemiBold)
                }
                Text("$servings", fontSize = 18.sp, color = Color(0xFFFF0090), fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                IconButton(onClick = { servings++ }) {
                    Text("+", fontSize = 28.sp, color = Color(0xFFFF0090))
                }
            }

            Spacer(Modifier.height(16.dp))

            // Ингредиенты
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFDFEC), RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        "Ингредиенты",
                        color = Color(0xFFFF0090),
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    ingredients.forEach { ingredient ->
                        Text(
                            "• $ingredient",
                            color = Color(0xFFFF0090),
                            fontFamily = montserrat,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Пошаговый рецепт
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFDFEC), RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        "Пошаговый рецепт",
                        color = Color(0xFFFF0090),
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.height(12.dp))

                    steps.forEachIndexed { index, (step, imageRes) ->
                        Column {
                            Text(
                                "${index + 1}. $step",
                                color = Color(0xFFFF0090),
                                fontFamily = montserrat,
                                fontSize = 16.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            imageRes?.let {
                                Image(
                                    painter = painterResource(id = it),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                )
                                Spacer(Modifier.height(12.dp))
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsScreenPreview() {
    Kokboktry1Theme {
        RecipeDetailsScreen()
    }
}
