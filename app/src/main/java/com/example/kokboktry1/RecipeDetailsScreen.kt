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
import vm.RecipeDetailViewModel
import com.example.kokboktry1.ui.theme.Pink
import com.example.kokboktry1.ui.theme.WhitePink
import com.example.kokboktry1.ui.theme.BrightPink
import com.example.kokboktry1.ui.theme.LightPink
import coil.compose.AsyncImage

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    onBack: () -> Unit,
    onNavigateHome: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onNavigateFavorites: () -> Unit = {},
    onNavigateProfile: () -> Unit = {}
) {
    val viewModel: RecipeDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(containerColor = Pink) {
                IconButton(onClick = onNavigateHome, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Home, contentDescription = "Главная", tint = BrightPink)
                }
                IconButton(onClick = onNavigateSearch, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Search, contentDescription = "Поиск", tint = BrightPink)
                }
                IconButton(onClick = onNavigateFavorites, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Favorite, contentDescription = "Избранное", tint = BrightPink)
                }
                IconButton(onClick = onNavigateProfile, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Person, contentDescription = "Профиль", tint = BrightPink)
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
                .background(LightPink)
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
                color = BrightPink
            )

            Spacer(Modifier.height(8.dp))

            // Название рецепта
            Text(
                text = state.recipe?.name ?: "",
                fontFamily = montserrat,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
                color = BrightPink
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrightPink
                    )
                ) {
                    Text("Назад")
                }
            }

            Spacer(Modifier.height(16.dp))


            // Фото рецепта
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Pink),
                contentAlignment = Alignment.Center
            ) {
                val imageUrl = state.recipe?.mainImagePath

                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                }
            }

            Spacer(Modifier.height(12.dp))


            // Количество порций

            var servings by remember { mutableIntStateOf(1) }

            LaunchedEffect(state.recipe?.portions) {
                val newValue = state.recipe?.portions
                if (newValue != null) servings = newValue
            }

            LaunchedEffect(servings) {
                viewModel.updatePortions(servings)
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Порции:",
                    color = BrightPink,
                    fontFamily = montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )

                Spacer(Modifier.width(10.dp))

                IconButton(onClick = { if (servings > 1) servings-- }) {
                    Text("-", fontSize = 28.sp, color = BrightPink, fontWeight = FontWeight.SemiBold)
                }

                Text(
                    "$servings",
                    fontSize = 18.sp,
                    color = BrightPink,
                    fontFamily = montserrat,
                    fontWeight = FontWeight.SemiBold
                )

                IconButton(onClick = { servings++ }) {
                    Text("+", fontSize = 28.sp, color = BrightPink)
                }
            }

            Spacer(Modifier.height(16.dp))


            // Ингредиенты
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WhitePink, RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Column {

                    Text(
                        "Ингредиенты",
                        color = BrightPink,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )

                    Spacer(Modifier.height(8.dp))

                    val ingredients = state.recipe?.ingredients ?: emptyList()
                    ingredients.forEach { ing ->
                        Text(
                            "• ${ing.name}, ${ing.weight} гр.",
                            color = BrightPink,
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
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
                    .background(WhitePink, RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Column {

                    Text(
                        "Пошаговый рецепт",
                        color = BrightPink,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )

                    Spacer(Modifier.height(12.dp))

                    val steps = state.recipe?.steps ?: emptyList()

                    steps.forEachIndexed { index, step ->
                        Column {

                            Text(
                                "${index + 1}. ${step.description}",
                                color = BrightPink,
                                fontFamily = montserrat,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )

                            Spacer(Modifier.height(8.dp))


                            val stepImage = step.stepImagePath
                            if (stepImage != null) {
                                AsyncImage(
                                    model = stepImage,
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
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsScreenPreview() {
    Kokboktry1Theme {
        RecipeDetailsScreen(
            recipeId = 1,
            onBack = { }
    )
    }
}
