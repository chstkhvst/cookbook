package vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.api.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

data class EditRecipeState(
    val recipeId: Int = 0,
    val name: String = "",
    val portions: Int = 1,
    val cookingTime: Int = 0,
    val mainImagePath: String? = null,

    val selectedType: RecipeType? = null,
    val selectedDifficulty: RecipeDifficulty? = null,
    val selectedCuisine: RecipeCuisine? = null,

    val types: List<RecipeType> = emptyList(),
    val difficulties: List<RecipeDifficulty> = emptyList(),
    val cuisines: List<RecipeCuisine> = emptyList(),

    val ingredients: List<IngredientInput> = emptyList(),
    val steps: List<StepInput> = emptyList(),

    val isLoading: Boolean = false,
    val isSending: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)

class EditRecipeViewModel(
    private val app: Application,
    private val recipeId: Int
) : AndroidViewModel(app) {

    private val catalogApi = ApiProvider.catalogApi
    private val recipeApi = ApiProvider.recipeApi

    private val _state = MutableStateFlow(EditRecipeState(recipeId = recipeId))
    val state = _state.asStateFlow()

    init {
        loadCatalogs()
    }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val recipe = withContext(Dispatchers.IO) {
                    recipeApi.apiRecipeIdGet(recipeId).execute().body()
                }

                if (recipe == null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Рецепт не найден"
                    )
                    return@launch
                }

                val ingredientsList = recipe.ingredients?.map {
                    IngredientInput(
                        name = it.name.orEmpty(),
                        weight = it.weight
                    )
                } ?: emptyList()

                val stepsList = recipe.steps?.map {
                    StepInput(
                        description = it.description.orEmpty(),
                        imagePath = it.stepImagePath
                    )
                } ?: emptyList()

                _state.value = _state.value.copy(
                    name = recipe.name.orEmpty(),
                    portions = recipe.portions ?: 1,
                    cookingTime = recipe.cookingTime ?: 0,
                    mainImagePath = recipe.mainImagePath,
                    selectedType = recipe.recipeType,
                    selectedDifficulty = recipe.difficulty,
                    selectedCuisine = recipe.cuisine,
                    ingredients = ingredientsList,
                    steps = stepsList,
                    isLoading = false
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки рецепта: ${e.message}"
                )
            }
        }
    }


    private fun uriToBase64(uriString: String): String? {
        return try {
            val uri = android.net.Uri.parse(uriString)
            val mime = app.contentResolver.getType(uri) ?: return null
            val bytes = app.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                ?: return null

            val b64 = android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP)
            "data:$mime;base64,$b64"

        } catch (e: Exception) {
            Log.e("EditRecipeVM", "Base64 conversion failed", e)
            null
        }
    }

    private fun loadCatalogs() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    Triple(
                        catalogApi.recipetypesGet().execute().body() ?: emptyList(),
                        catalogApi.difficultiesGet().execute().body() ?: emptyList(),
                        catalogApi.cuisinesGet().execute().body() ?: emptyList()
                    )
                }

                _state.value = _state.value.copy(
                    types = data.first,
                    difficulties = data.second,
                    cuisines = data.third
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Ошибка загрузки каталогов: ${e.message}"
                )
            }
        }
    }

    private fun buildUpdateDTO(): CreateRecipeDTO {
        val s = _state.value

        val ingredientsDto = s.ingredients.map {
            IngredientDTO(
                name = it.name,
                weight = it.weight
            )
        }

        val stepsDto = s.steps.map {
            StepDTO(
                description = it.description,
                stepImagePath = it.imagePath
            )
        }

        return CreateRecipeDTO(
            id = s.recipeId,
            name = s.name,
            portions = s.portions,
            cookingTime = s.cookingTime,
            typeId = s.selectedType?.id,
            difficultyId = s.selectedDifficulty?.id,
            cuisineId = s.selectedCuisine?.id,
            userId = "", // сервер сам определит из токена
            ingredients = ingredientsDto,
            steps = stepsDto,
            mainImagePath = s.mainImagePath
        )
    }

    fun updateRecipe() {
        val s = _state.value
        if (s.isSending) return

        if (s.name.isBlank()
            || s.selectedType == null
            || s.selectedDifficulty == null
            || s.selectedCuisine == null
            || s.portions <= 0
            || s.cookingTime <= 0
            || s.mainImagePath.isNullOrBlank()
            || s.ingredients.isEmpty()
            || s.steps.isEmpty()
            || s.ingredients.any { it.name.isBlank() }
            || s.steps.any { it.description.isBlank() }
        ) {
            _state.value = s.copy(error = "Заполните корректно обязательные поля")
            return
        }

        _state.value = s.copy(isSending = true, error = null)
        val dto = buildUpdateDTO()

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    recipeApi.apiRecipeIdPut(recipeId, dto).execute()
                }

                if (response.isSuccessful) {
                    _state.value = _state.value.copy(
                        isSending = false,
                        success = true
                    )
                } else {
                    _state.value = _state.value.copy(
                        isSending = false,
                        error = "Ошибка обновления: ${response.code()} ${response.message()}"
                    )
                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isSending = false,
                    error = "Ошибка запроса: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    fun addIngredient() {
        val s = _state.value
        _state.value = s.copy(
            ingredients = s.ingredients + IngredientInput()
        )
    }

    fun addStep() {
        val s = _state.value
        _state.value = s.copy(
            steps = s.steps + StepInput()
        )
    }

    fun updateIngredientName(i: Int, v: String) {
        val s = _state.value
        if (i !in s.ingredients.indices) return
        val u = s.ingredients.toMutableList()
        u[i] = u[i].copy(name = v)
        _state.value = s.copy(ingredients = u)
    }

    fun updateIngredientWeight(i: Int, w: Int?) {
        val s = _state.value
        if (i !in s.ingredients.indices) return
        val u = s.ingredients.toMutableList()
        u[i] = u[i].copy(weight = w)
        _state.value = s.copy(ingredients = u)
    }

    fun updateStepDescription(i: Int, v: String) {
        val s = _state.value
        if (i !in s.steps.indices) return
        val u = s.steps.toMutableList()
        u[i] = u[i].copy(description = v)
        _state.value = s.copy(steps = u)
    }

    fun updateStepImage(i: Int, uri: String) {
        val b64 = uriToBase64(uri)
        val s = _state.value
        if (i !in s.steps.indices) return
        val u = s.steps.toMutableList()
        u[i] = u[i].copy(imagePath = b64)
        _state.value = s.copy(steps = u)
    }

    fun updateMainImage(uri: String) {
        val b64 = uriToBase64(uri)
        _state.value = _state.value.copy(mainImagePath = b64)
    }

    fun removeStep(i: Int) {
        val s = _state.value
        if (i !in s.steps.indices) return
        val u = s.steps.toMutableList()
        u.removeAt(i)
        _state.value = s.copy(steps = u)
    }

    fun removeIngredient(i: Int) {
        val s = _state.value
        if (i !in s.ingredients.indices) return
        val u = s.ingredients.toMutableList()
        u.removeAt(i)
        _state.value = s.copy(ingredients = u)
    }

    fun updateName(newName: String) {
        _state.value = _state.value.copy(name = newName)
    }

    fun updatePortions(newPortions: Int) {
        _state.value = _state.value.copy(portions = newPortions)
    }

    fun updateCookingTime(newTime: Int) {
        _state.value = _state.value.copy(cookingTime = newTime)
    }
    fun updateSelectedType(type: RecipeType) {
        _state.value = _state.value.copy(selectedType = type)
    }

    fun updateSelectedDifficulty(diff: RecipeDifficulty) {
        _state.value = _state.value.copy(selectedDifficulty = diff)
    }

    fun updateSelectedCuisine(cuisine: RecipeCuisine) {
        _state.value = _state.value.copy(selectedCuisine = cuisine)
    }

}
class EditRecipeViewModelFactory(
    private val app: Application,
    private val recipeId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditRecipeViewModel::class.java)) {
            return EditRecipeViewModel(app, recipeId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
