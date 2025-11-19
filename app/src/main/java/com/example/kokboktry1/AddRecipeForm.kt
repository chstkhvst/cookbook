package com.example.kokboktry1

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.kokboktry1.ui.theme.Kokboktry1Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kokboktry1.ui.theme.Kokboktry1Theme
import vm.AddRecipeViewModel


val Pink = Color(0xFFFF9FBA)
val WhitePink = Color(0xFFFFDFEC)
val BrightPink = Color(0xFFFF0090)
val LightPink = Color(0xFFFFC7DD)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeForm(
    onNavigateHome: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onNavigateFavorites: () -> Unit = {},
    onNavigateProfile: () -> Unit = {},
    onExitClick: () -> Unit = {},
    viewModel: AddRecipeViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()



    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Pink
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
                    Icon(Icons.Default.Person, "Профиль", tint =BrightPink)
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(LightPink)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(16.dp))
            Text(
                text = "cookbook",
                fontSize = 40.sp,
                color = BrightPink,
                fontFamily = FontFamily(Font(R.font.abrilfatface))
            )

            Spacer(Modifier.height(8.dp))
            Text(
                text = "Новый рецепт",
                fontSize = 28.sp,
                color = BrightPink,
                fontFamily = FontFamily(Font(R.font.montserrat)),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WhitePink, RoundedCornerShape(20.dp))
                    .padding(16.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { onExitClick() },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(24.dp)
                            .background(Pink, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Закрыть",
                            tint = BrightPink
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.Start) {
                    Spacer(Modifier.height(25.dp))
                    val montserrat = FontFamily(Font(R.font.montserrat))

                    var name by remember { mutableStateOf("") }
                    var servings by remember { mutableStateOf(1) }
                    var time by remember { mutableStateOf(0) }

                    var category by remember { mutableStateOf("") }
                    var difficulty by remember { mutableStateOf("") }
                    var cuisine by remember { mutableStateOf("") }

                    Text("Название", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = LightPink,
                            unfocusedContainerColor = LightPink,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            focusedTextColor = BrightPink,
                            unfocusedTextColor = BrightPink
                        )

                    )

                    Spacer(Modifier.height(8.dp))

                    Text("Категория", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
                    ComboBox(label = "Выберите категорию", options = listOf("1", "2", "3")) {
                        category = it
                    }

                    Spacer(Modifier.height(8.dp))


                    Text("Порции", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (servings > 1) servings-- }) {
                            Text("-", fontSize = 24.sp, color = BrightPink, fontWeight = FontWeight.SemiBold)
                        }
                        Text(servings.toString(), color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                        IconButton(onClick = { servings++ }) {
                            Text("+", fontSize = 24.sp, color = BrightPink, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text("Ингредиенты", color = BrightPink, fontSize = 20.sp, fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { /* TODO: add ingredient */ },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Pink),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("+ добавить ингредиент", color = BrightPink, fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(16.dp))

                    Text("Сложность", color = BrightPink, fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    ComboBox("Выберите сложность", listOf("1", "2", "3")) { difficulty = it }

                    Spacer(Modifier.height(8.dp))

                    Text("Кухня", color = BrightPink, fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    ComboBox("Выберите кухню", listOf("1", "3", "2")) { cuisine = it }

                    Spacer(Modifier.height(8.dp))

                    Text("Время (мин)", color = BrightPink, fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (time > 0) time -= 5 }) {
                            Text("-", fontSize = 24.sp, color = BrightPink)
                        }
                        Text("$time", color = BrightPink)
                        IconButton(onClick = { time += 5 }) {
                            Text("+", fontSize = 24.sp, color = BrightPink)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text("Пошаговый рецепт", color =BrightPink, fontSize = 20.sp, fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = Pink),
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+ новый шаг", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                        }
                        Button(
                            onClick = {  },
                            colors = ButtonDefaults.buttonColors(containerColor = Pink),
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+ фото", color =BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            viewModel.saveRecipe(
                                name = name,
                                servings = servings,
                                time = time,
                                category = category,
                                difficulty = difficulty,
                                cuisine = cuisine
                            )

                            onExitClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrightPink)
                    ) {
                        Text(
                            "Сохранить рецепт",
                            color = LightPink,
                            fontFamily = FontFamily(Font(R.font.montserrat)),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,

                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
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
            label = { Text(label, color = BrightPink) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LightPink,
                unfocusedContainerColor = LightPink,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedTextColor = BrightPink
            )
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it, color = BrightPink) },
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


