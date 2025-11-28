package com.example.kokboktry1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import vm.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.kokboktry1.ui.theme.Pink
import com.example.kokboktry1.ui.theme.WhitePink
import com.example.kokboktry1.ui.theme.BrightPink
import com.example.kokboktry1.ui.theme.LightPink
import vm.ProfileState
@Composable
fun ProfileScreen(
    onNavigateHome: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onNavigateFavorites: () -> Unit = {},
    onNavigateProfile: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onEditClick: (Int) -> Unit = {},
    viewModel: ProfileViewModel = viewModel()
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(LightPink)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "cookbook",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.abrilfatface)),
                    color = BrightPink
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Pink, RoundedCornerShape(20.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Аватар",
                        tint = BrightPink,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = state.userName,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat)),
                        fontWeight = FontWeight.SemiBold,
                        color = BrightPink
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onAddClick,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(45.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = BrightPink)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить", tint = LightPink)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "добавить рецепт",
                        color = LightPink,
                        fontFamily = FontFamily(Font(R.font.montserrat)),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Мои рецепты",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BrightPink,
                    fontFamily = FontFamily(Font(R.font.montserrat))
                )

                Spacer(modifier = Modifier.height(12.dp))

                // СПИСОК РЕЦЕПТОв
                state.userRecipes.forEach { recipe ->
                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(WhitePink, RoundedCornerShape(20.dp))
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            AsyncImage(
                                model = recipe.mainImagePath,
                                contentDescription = "Фото",
                                modifier = Modifier
                                    .size(100.dp, 70.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )


                            Spacer(modifier = Modifier.width(10.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = recipe.name ?: "Без названия",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BrightPink,
                                    fontFamily = FontFamily(Font(R.font.montserrat))
                                )
                                Text(
                                    text = "Категория: ${recipe.recipeType?.typeName ?: "-"}",
                                    fontSize = 14.sp,
                                    color = BrightPink
                                )
                                Text(
                                    text = "Сложность: ${recipe.difficulty?.difficultyName ?: "-"}",
                                    fontSize = 14.sp,
                                    color = BrightPink
                                )
                                Text(
                                    text = "Кухня: ${recipe.cuisine?.cuisineName ?: "-"}",
                                    fontSize = 14.sp,
                                    color = BrightPink
                                )
                                Text(
                                    text = "Порции: ${recipe.portions ?: "-"}",
                                    fontSize = 14.sp,
                                    color = BrightPink
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                IconButton(onClick = {
                                    recipe.id?.let { onEditClick(it) }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Изменить",
                                        tint = BrightPink
                                    )
                                }
                                IconButton(onClick = { recipe.id?.let { viewModel.deleteRecipe(it) } }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Удалить",
                                        tint = BrightPink
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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    Kokboktry1Theme {
        ProfileScreen()
    }
}
