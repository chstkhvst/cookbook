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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import vm.HomepageViewModel
import com.example.myapp.api.models.RecipeDifficulty
import com.example.myapp.api.models.RecipeType
import com.example.myapp.api.models.RecipeCuisine
import com.example.kokboktry1.ui.theme.Pink
import com.example.kokboktry1.ui.theme.WhitePink
import com.example.kokboktry1.ui.theme.BrightPink
import com.example.kokboktry1.ui.theme.LightPink
import androidx.compose.material.icons.outlined.Favorite
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomepageScreen(
    onNavigateHome: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onNavigateFavorites: () -> Unit = {},
    onNavigateProfile: () -> Unit = {},
    onRecipeDetails: (Int) -> Unit = {},
    viewModel: HomepageViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var searchText by remember { mutableStateOf("") }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(LightPink)
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Заголовок
            Text(
                text = "cookbook",
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.abrilfatface)),
                color = BrightPink
            )

            Spacer(modifier = Modifier.height(12.dp))


            OutlinedTextField(
                value = searchText,
                onValueChange = {searchText = it
                    viewModel.searchRecipes(it)
                                },
                placeholder = { Text("поиск по названию", color = BrightPink) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Поиск", tint = BrightPink) },
                modifier = Modifier
                    .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Pink,
                        unfocusedContainerColor = Pink,
                        focusedTextColor = BrightPink,
                        unfocusedTextColor = BrightPink,
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
                RecipeTypeComboBox(
                    types = state.recipeTypes,
                    selectedType = state.selectedType,
                    onTypeSelected = { viewModel.filterByType(it) }
                )
                RecipeDifficultyComboBox(
                    difficulties = state.difficulties,
                    selectedDifficulty = state.selectedDifficulty,
                    onDifficultySelected = { viewModel.filterByDifficulty(it) }
                )
                RecipeCuisineComboBox(
                    cuisines = state.cuisines,
                    selectedCuisine = state.selectedCuisine,
                    onCuisineSelected = { viewModel.filterByCuisine(it) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))


            // Кнопки фильтров
            HorizontalScrollableRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                FilterButton("сначала новые")       { viewModel.toggleFilter(it) }
                FilterButton("сначала старые")      { viewModel.toggleFilter(it) }
                FilterButton("до 7 ингредиентов")   { viewModel.toggleFilter(it) }
                FilterButton("до 10 ингредиентов")  { viewModel.toggleFilter(it) }
                FilterButton("до 30 мин")           { viewModel.toggleFilter(it) }
                FilterButton("< 1 часа")            { viewModel.toggleFilter(it) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Карточка рецепта
            state.filteredRecipes.forEach { recipe ->
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
                                // Используем Coil для загрузки изображения
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
@Composable
fun HorizontalScrollableRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(scrollState) //scroll
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        content()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTypeComboBox(
    types: List<RecipeType>,
    selectedType: RecipeType?,
    onTypeSelected: (RecipeType?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = selectedType?.typeName ?: "тип",
            onValueChange = {},
            label = { Text("тип", color = BrightPink, fontSize = 12.sp) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Pink,
                unfocusedContainerColor = Pink,
                focusedTextColor = BrightPink,
                unfocusedTextColor = BrightPink,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(30.dp)
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Все типы", fontSize = 12.sp, color = BrightPink) },
                onClick = { onTypeSelected(null) }
            )
            types.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.typeName, fontSize = 12.sp, color = BrightPink) },
                    onClick = { onTypeSelected(type) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDifficultyComboBox(
    difficulties: List<RecipeDifficulty>,
    selectedDifficulty: RecipeDifficulty?,
    onDifficultySelected: (RecipeDifficulty?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = selectedDifficulty?.difficultyName ?: "сложность",
            onValueChange = {},
            label = { Text("сложность", color = BrightPink, fontSize = 12.sp) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Pink,
                unfocusedContainerColor = Pink,
                focusedTextColor = BrightPink,
                unfocusedTextColor = BrightPink,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(30.dp)
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Все сложности", fontSize = 12.sp, color = BrightPink) },
                onClick = { onDifficultySelected(null) }
            )
            difficulties.forEach { difficulty ->
                DropdownMenuItem(
                    text = { Text(difficulty.difficultyName, fontSize = 12.sp, color = BrightPink) },
                    onClick = { onDifficultySelected(difficulty) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCuisineComboBox(
    cuisines: List<RecipeCuisine>,
    selectedCuisine: RecipeCuisine?,
    onCuisineSelected: (RecipeCuisine?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = selectedCuisine?.cuisineName ?: "кухня",
            onValueChange = {},
            label = { Text("кухня", color = BrightPink, fontSize = 12.sp) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Pink,
                unfocusedContainerColor = Pink,
                focusedTextColor = BrightPink,
                unfocusedTextColor = BrightPink,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(30.dp)
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Все кухни", fontSize = 12.sp, color = BrightPink) },
                onClick = { onCuisineSelected(null) }
            )
            cuisines.forEach { cuisine ->
                DropdownMenuItem(
                    text = { Text(cuisine.cuisineName, fontSize = 12.sp, color = BrightPink) },
                    onClick = { onCuisineSelected(cuisine) }
                )
            }
        }
    }
}
@Composable
fun FilterButton(    label: String,
                     onClick: (String) -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    Button(
        onClick = { isPressed = !isPressed
            onClick(label) },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) Pink else WhitePink
        )
    ) {
        Text(
            label,
            color =  BrightPink,
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