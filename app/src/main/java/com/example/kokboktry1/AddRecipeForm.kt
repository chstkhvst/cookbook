package com.example.kokboktry1

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.kokboktry1.ui.theme.Pink
import com.example.kokboktry1.ui.theme.WhitePink
import com.example.kokboktry1.ui.theme.BrightPink
import com.example.kokboktry1.ui.theme.LightPink

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
    LaunchedEffect(state.success) {
        if (state.success) {
            onExitClick()
        }
    }
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

                    // launcher для главной обложки
                    val mainImagePicker = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.OpenDocument(),
                        onResult = { uri ->
                            if (uri != null) {
                                viewModel.updateMainImage(uri.toString())
                            }
                        }
                    )

                    Text("Название", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it
                            viewModel.updateName(it)},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Pink,
                            focusedContainerColor = Pink,
                            unfocusedTextColor =BrightPink,
                            focusedTextColor = BrightPink,
                            unfocusedLabelColor = BrightPink,
                            focusedLabelColor = BrightPink,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        )

                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Категория",
                        color = BrightPink,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold
                    )

                    ComboBox(
                        label = "Выберите категорию",
                        options = state.types.map { it.typeName },
                        selected =state.selectedType?.typeName
                    ) { selectedName ->
                        val obj = state.types.first { it.typeName == selectedName }
                        viewModel.updateSelectedType(obj)
                    }


                    Spacer(Modifier.height(8.dp))


                    Text("Порции", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (servings > 1) servings--
                            viewModel.updatePortions(servings)}) {
                            Text("-", fontSize = 24.sp, color = BrightPink, fontWeight = FontWeight.SemiBold)
                        }
                        Text(servings.toString(), color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                        IconButton(onClick = { servings++
                            viewModel.updatePortions(servings)}) {
                            Text("+", fontSize = 24.sp, color = BrightPink, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    // Блок выбора главного фото
                    Text(
                        "Фото для обложки",
                        color = BrightPink,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold
                    )

                    Button(
                        onClick = {
                            mainImagePicker.launch(arrayOf("image/*"))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Pink),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (state.mainImagePath == null) "+ добавить фото" else "Фото добавлено",
                            color = BrightPink,
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(Modifier.height(16.dp))


                    Text("Ингредиенты", color = BrightPink, fontSize = 20.sp, fontFamily = montserrat, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { viewModel.addIngredient() },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Pink),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("+ добавить ингредиент", color = BrightPink, fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(Modifier.height(12.dp))

                    state.ingredients.forEachIndexed { index, ingredient ->

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(WhitePink, RoundedCornerShape(16.dp))
                                .padding(6.dp)
                        ) {

                            Text(
                                "Ингредиент ${index + 1}",
                                color = BrightPink,
                                fontFamily = montserrat,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(Modifier.height(4.dp))

                            // Название ингредиента
                            OutlinedTextField(
                                value = ingredient.name,
                                onValueChange = { viewModel.updateIngredientName(index, it) },
                                label = { Text("Название", color = BrightPink) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = LightPink,
                                    unfocusedContainerColor = LightPink,
                                    unfocusedTextColor = BrightPink,
                                    focusedTextColor = BrightPink,
                                    unfocusedLabelColor = BrightPink,
                                    focusedLabelColor = BrightPink,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent
                                )
                            )

                            Spacer(Modifier.height(6.dp))

                            // Вес
                            OutlinedTextField(
                                value = ingredient.weight?.toString() ?: "",
                                onValueChange = {
                                    val w = it.toIntOrNull()
                                    viewModel.updateIngredientWeight(index, w)
                                },
                                label = { Text("Вес (граммы)", color = BrightPink) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = LightPink,
                                    unfocusedContainerColor = LightPink,
                                    unfocusedTextColor = BrightPink,
                                    focusedTextColor = BrightPink,
                                    unfocusedLabelColor = BrightPink,
                                    focusedLabelColor = BrightPink,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent
                                ),
                                singleLine = true
                            )

                            Spacer(Modifier.height(8.dp))

                            // Кнопка удаления
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                TextButton(onClick = { viewModel.removeIngredient(index) }) {
                                    Text("Удалить", color = BrightPink)
                                }
                            }
                        }

                        Spacer(Modifier.height(6.dp))
                    }

                    Text(
                        "Сложность",
                        color = BrightPink,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold
                    )

                    ComboBox(
                        label = "Выберите сложность",
                        options = state.difficulties.map { it.difficultyName },
                        selected = state.selectedDifficulty?.difficultyName
                    ) { selectedName ->

                        val obj = state.difficulties.firstOrNull { it.difficultyName == selectedName }
                        if (obj != null) {
                            viewModel.updateSelectedDifficulty(obj)
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Кухня",
                        color = BrightPink,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold
                    )

                    ComboBox(
                        label = "Выберите кухню",
                        options = state.cuisines.map { it.cuisineName },
                        selected = state.selectedCuisine?.cuisineName
                    ) { selectedName ->

                        val obj = state.cuisines.firstOrNull { it.cuisineName == selectedName }
                        if (obj != null) {
                            viewModel.updateSelectedCuisine(obj)
                        }
                    }


                    Spacer(Modifier.height(8.dp))

                    Text("Время (мин)", color = BrightPink, fontFamily = montserrat,  fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (time > 0) time -= 5
                            viewModel.updateCookingTime(time)}) {
                            Text("-", fontSize = 24.sp, color = BrightPink)
                        }
                        Text("$time", color = BrightPink)
                        IconButton(onClick = { time += 5
                            viewModel.updateCookingTime(time)}) {
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
                            onClick = { viewModel.addStep() },
                            colors = ButtonDefaults.buttonColors(containerColor = Pink),
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "+ новый шаг",
                                color = BrightPink,
                                fontFamily = montserrat,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                    }
                    var stepIndexForImage by remember { mutableStateOf(-1) }

                        // пкиер через OpenDocument
                    val pickerLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.OpenDocument(),
                        onResult = { uri ->
                            if (uri != null && stepIndexForImage >= 0) {
                                viewModel.updateStepImage(stepIndexForImage, uri.toString())
                            }
                            stepIndexForImage = -1
                        }
                    )
                    //  доступ к URI
                    LaunchedEffect(Unit) {

                    }

                    state.steps.forEachIndexed { index, step ->

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(WhitePink, RoundedCornerShape(16.dp))
                                .padding(6.dp)
                        ) {

                            Text(
                                "Шаг ${index + 1}",
                                color = BrightPink,
                                fontFamily = montserrat,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(Modifier.height(8.dp))

                            OutlinedTextField(
                                value = step.description,
                                onValueChange = { viewModel.updateStepDescription(index, it) },
                                label = { Text("Описание шага", color = BrightPink) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = LightPink,
                                    unfocusedContainerColor = LightPink,
                                    unfocusedTextColor = BrightPink,
                                    focusedTextColor = BrightPink,
                                    unfocusedLabelColor = BrightPink,
                                    focusedLabelColor = BrightPink,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent
                                ),
                                minLines = 2
                            )

                            Spacer(Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    stepIndexForImage = index
                                    pickerLauncher.launch(arrayOf("image/*"))
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Pink),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = if (step.imagePath == null) "+ фото для шага" else "Фото добавлено",
                                    color = BrightPink,
                                    fontFamily = montserrat,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }



                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                TextButton(onClick = { viewModel.removeStep(index) }) {
                                    Text("Удалить", color = BrightPink)
                                }
                            }
                        }

                    }

                    Spacer(Modifier.height(8.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            viewModel.sendRecipe()
                        },
                        enabled = !state.isSending,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrightPink,
                            contentColor = Color.White,
                            disabledContainerColor = BrightPink.copy(alpha = 0.5f)
                        )
                    ) {
                        Text("Сохранить рецепт")
                    }
// Показываем ошибку, если есть
                    state.error?.let { errorMsg ->
                        Text(
                            text = errorMsg,
                            color = Color.Red,
                            fontFamily = montserrat,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 8.dp)
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
fun ComboBox(
    label: String,
    options: List<String>,
    selected: String?,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selected ?: "",
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
                unfocusedTextColor = BrightPink,
                focusedTextColor = BrightPink,
                unfocusedLabelColor = BrightPink,
                focusedLabelColor = BrightPink,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it, color = BrightPink) },
                    onClick = {
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


