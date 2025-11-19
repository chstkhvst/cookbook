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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import vm.HomepageViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomepageScreen(
    onNavigateHome: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onNavigateFavorites: () -> Unit = {},
    onNavigateProfile: () -> Unit = {},
    onRecipeDetails: () -> Unit = {},
    onToggleFavorite: () -> Unit = {},
    viewModel: HomepageViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val Pink = Color(0xFFFF9FBA)
    val WhitePink = Color(0xFFFFDFEC)
    val BrightPink = Color(0xFFFF0090)
    val LightPink = Color(0xFFFFC7DD)

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
                IconButton(onClick = onNavigateSearch, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Search, "Поиск", tint = BrightPink)
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
                                    text = recipe.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BrightPink,
                                    fontFamily = FontFamily(Font(R.font.montserrat))
                                )
                                Text("Категория: ${recipe.category}", color = BrightPink)
                                Text("Сложность: ${recipe.difficulty}", color = BrightPink)
                                Text("Кухня: ${recipe.cuisine}", color = BrightPink)
                                Text("Количество порций: ${recipe.servings}", color = BrightPink)
                                Text("Время приготовления: ${recipe.time} мин", color = BrightPink)
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
                                colors = ButtonDefaults.buttonColors(containerColor = com.example.kokboktry1.Pink)
                            ) {
                                Text(
                                    "Подробнее",
                                    color = com.example.kokboktry1.BrightPink,
                                    fontFamily = FontFamily(Font(R.font.montserrat)),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp
                                )
                            }
                            IconButton(onClick = onToggleFavorite) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Избранное",
                                    tint = com.example.kokboktry1.BrightPink
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
                    color = BrightPink,
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
                focusedContainerColor = Pink,
                unfocusedContainerColor = Pink,
                focusedTextColor = BrightPink,
                unfocusedTextColor = BrightPink,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(30.dp)
        )

        //menu
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
                            color = BrightPink
                        )
                    },
                    onClick = {
                        selectedText = option
                        expanded = false //закрываем меню
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
            containerColor = if (isPressed) Pink else WhitePink
        )
    ) {
        Text(
            text,
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