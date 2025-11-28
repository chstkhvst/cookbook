package com.example.kokboktry1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import vm.AddRecipeViewModel
import vm.FavoritesViewModel
import com.example.kokboktry1.ui.theme.Pink
import com.example.kokboktry1.ui.theme.WhitePink
import com.example.kokboktry1.ui.theme.BrightPink
import com.example.kokboktry1.ui.theme.LightPink
@Composable
fun FavoritesScreen(
    onNavigateHome: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onNavigateFavorites: () -> Unit = {},
    onNavigateProfile: () -> Unit = {},
    onRecipeDetails: (Int) -> Unit = {},
    viewModel: FavoritesViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Pink,
                tonalElevation = 4.dp
            ) {
                IconButton(onClick = onNavigateHome, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Home, "Главная", tint = BrightPink)
                }
                IconButton(onClick = onNavigateFavorites, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Favorite, "Избранное", tint = BrightPink)
                }
                IconButton(onClick = onNavigateProfile, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Person, "Профиль", tint = BrightPink)
                }
            }
        },
        contentWindowInsets = WindowInsets(0)
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(LightPink)
                .padding(6.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightPink)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Заголовок
                Text(
                    text = "cookbook",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.abrilfatface)),
                    color = BrightPink
                )

                Spacer(modifier = Modifier.height(12.dp))


                Text(
                    text = "ИЗБРАННОЕ",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrightPink,
                    fontFamily = FontFamily(Font(R.font.montserrat))
                )

                Spacer(modifier = Modifier.height(12.dp))

                state.favoriteRecipes.forEach { recipe ->
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(WhitePink, RoundedCornerShape(20.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val imageUrl = recipe.mainImagePath
                                if (!imageUrl.isNullOrEmpty()) {
                                    //Coil для загрузки изображения
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Фото рецепта",
                                        modifier = Modifier
                                            .size(100.dp, 70.dp)
                                            .clip(RoundedCornerShape(10.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    // Плейсхолдер если изображения нет
                                    Box(
                                        modifier = Modifier
                                            .size(100.dp, 70.dp)
                                            .background(Color.LightGray, RoundedCornerShape(10.dp))
                                    ) {
                                        Text(
                                            text = "Нет фото",
                                            modifier = Modifier.align(Alignment.Center),
                                            color = Color.DarkGray
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    recipe.name?.let {
                                        Text(
                                            text = it,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = BrightPink,
                                            fontFamily = FontFamily(Font(R.font.montserrat))
                                        )
                                    }

                                    Text("Категория: ${recipe.recipeType?.typeName ?: "-"}", color = BrightPink)
                                    Text("Сложность: ${recipe.difficulty?.difficultyName ?: "-"}", color = BrightPink)
                                    Text("Кухня: ${recipe.cuisine?.cuisineName ?: "-"}", color = BrightPink)
                                    Text("Порций: ${recipe.portions}", color = BrightPink)
                                    Text("Время: ${recipe.cookingTime} мин", color = BrightPink)
                                }
                            }

                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Button(
                                    onClick = { onRecipeDetails(recipe.id ?: return@Button) },
                                    shape = RoundedCornerShape(20.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Pink)
                                ) {
                                    Text(
                                        "Подробнее",
                                        color = BrightPink,
                                        fontFamily = FontFamily(Font(R.font.montserrat)),
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp
                                    )
                                }

                                IconButton(onClick = {
                                    viewModel.toggleFavorite(recipe.id ?: return@IconButton)
                                }) {
                                    Icon(
                                        imageVector = if (recipe.isFav == true) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                                        contentDescription = "Избранное",
                                        tint = if (recipe.isFav == true) BrightPink else Pink.copy(alpha = 0.6f)
                                    )
                                }
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