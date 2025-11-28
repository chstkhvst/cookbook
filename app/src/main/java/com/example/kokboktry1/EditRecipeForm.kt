package com.example.kokboktry1

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import vm.EditRecipeViewModel
import com.example.kokboktry1.ui.theme.Pink
import com.example.kokboktry1.ui.theme.WhitePink
import com.example.kokboktry1.ui.theme.BrightPink
import com.example.kokboktry1.ui.theme.LightPink
import vm.EditRecipeViewModelFactory
import android.app.Application
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeForm(
    recipeId: Int,
    onExitClick: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onNavigateFavorites: () -> Unit = {},
    onNavigateProfile: () -> Unit = {},
) {
    val context = LocalContext.current.applicationContext as Application
    val factory = EditRecipeViewModelFactory(context, recipeId)
    val viewModel: EditRecipeViewModel = viewModel(factory = factory)
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)
    }

    LaunchedEffect(state.success) {
        if (state.success) onExitClick()
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(16.dp))
            Text(
                text = "cookbook",
                fontSize = 40.sp,
                color = BrightPink,
                fontFamily = abril
            )

            Spacer(Modifier.height(8.dp))
            Text(
                text = "Редактирование рецепта",
                fontSize = 28.sp,
                color = BrightPink,
                fontFamily = montserrat,
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

                    // Главное фото
                    val mainImagePicker = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.OpenDocument(),
                        onResult = { uri ->
                            if (uri != null) viewModel.updateMainImage(uri.toString())
                        }
                    )

                    Text("Название", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = { viewModel.updateName(it) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = textFieldColors()
                    )

                    Spacer(Modifier.height(10.dp))

                    Text("Категория", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
                    ComboBox(
                        label = "Выберите категорию",
                        options = state.types.map { it.typeName },
                        selected =state.selectedType?.typeName
                    ) { selectedName ->
                        val obj = state.types.first { it.typeName == selectedName }
                        viewModel.updateSelectedType(obj)
                    }

                    Spacer(Modifier.height(10.dp))

                    Text("Порции", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            if (state.portions > 1) viewModel.updatePortions(state.portions - 1)
                        }) {
                            Text("-", fontSize = 24.sp, color = BrightPink)
                        }
                        Text(state.portions.toString(), color = BrightPink)
                        IconButton(onClick = { viewModel.updatePortions(state.portions + 1) }) {
                            Text("+", fontSize = 24.sp, color = BrightPink)
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Text("Фото для обложки", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
                    Button(
                        onClick = { mainImagePicker.launch(arrayOf("image/*")) },
                        colors = ButtonDefaults.buttonColors(containerColor = Pink),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (state.mainImagePath == null) "Выбрать фото" else "Фото выбрано",
                            color = BrightPink
                        )
                    }

                    Spacer(Modifier.height(15.dp))

                    // INGREDIENTS
                    Text("Ингредиенты", color = BrightPink, fontSize = 20.sp, fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = { viewModel.addIngredient() },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Pink),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("+ добавить ингредиент", color = BrightPink)
                    }

                    Spacer(Modifier.height(16.dp))

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

                            OutlinedTextField(
                                value = ingredient.name,
                                onValueChange = { viewModel.updateIngredientName(index, it) },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Название", color = BrightPink) },
                                shape = RoundedCornerShape(20.dp),
                                colors = textFieldColors()
                            )

                            Spacer(Modifier.height(6.dp))

                            OutlinedTextField(
                                value = ingredient.weight?.toString() ?: "",
                                onValueChange = {
                                    val w = it.toIntOrNull()
                                    viewModel.updateIngredientWeight(index, w)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Вес (г)", color = BrightPink) },
                                shape = RoundedCornerShape(20.dp),
                                colors = textFieldColors()
                            )

                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = { viewModel.removeIngredient(index) }) {
                                    Text("Удалить", color = BrightPink)
                                }
                            }
                        }
                        Spacer(Modifier.height(6.dp))
                    }

                    Spacer(Modifier.height(16.dp))

                    Text("Сложность", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
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
                    Spacer(Modifier.height(16.dp))

                    Text("Кухня", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
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

                    Spacer(Modifier.height(16.dp))

                    Text("Время (мин)", color = BrightPink, fontFamily = montserrat, fontWeight = FontWeight.SemiBold,)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            if (state.cookingTime > 0) viewModel.updateCookingTime(state.cookingTime - 5)
                        }) {
                            Text("-", fontSize = 24.sp, color = BrightPink)
                        }
                        Text(state.cookingTime.toString(), color = BrightPink)
                        IconButton(onClick = { viewModel.updateCookingTime(state.cookingTime + 5) }) {
                            Text("+", fontSize = 24.sp, color = BrightPink)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // STEPS
                    Text(
                        "Пошаговый рецепт",
                        color = BrightPink,
                        fontSize = 20.sp,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.height(12.dp))

                    var stepImageIndex by remember { mutableStateOf(-1) }

                    val stepImagePicker = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.OpenDocument(),
                        onResult = { uri ->
                            if (uri != null && stepImageIndex >= 0) {
                                viewModel.updateStepImage(stepImageIndex, uri.toString())
                            }
                            stepImageIndex = -1
                        }
                    )

                    Button(
                        onClick = { viewModel.addStep() },
                        colors = ButtonDefaults.buttonColors(containerColor = Pink),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("+ новый шаг", color = BrightPink)
                    }

                    Spacer(Modifier.height(12.dp))

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

                            OutlinedTextField(
                                value = step.description,
                                onValueChange = { viewModel.updateStepDescription(index, it) },
                                label = { Text("Описание шага", color = BrightPink) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(20.dp),
                                colors = textFieldColors(),
                                minLines = 2
                            )

                            Spacer(Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    stepImageIndex = index
                                    stepImagePicker.launch(arrayOf("image/*"))
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Pink),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = if (step.imagePath == null)
                                        "+ фото"
                                    else "Фото выбрано",
                                    color = BrightPink
                                )
                            }

                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = { viewModel.removeStep(index) }) {
                                    Text("Удалить", color = BrightPink)
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = { viewModel.updateRecipe() },
                        enabled = !state.isSending,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrightPink,
                            disabledContainerColor = BrightPink.copy(alpha = 0.5f)
                        )
                    ) {
                        Text("Сохранить изменения", color = Color.White)
                    }
                    state.error?.let {
                        Text(it, color = Color.Red, modifier = Modifier.padding(top = 10.dp))
                    }

                }
            }
        }
    }
}

@Composable
private fun textFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = LightPink,
    unfocusedContainerColor = LightPink,
    unfocusedTextColor = BrightPink,
    focusedTextColor = BrightPink,
    unfocusedLabelColor = BrightPink,
    focusedLabelColor = BrightPink,
    unfocusedIndicatorColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent
)
