package vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.api.models.RecipeCuisine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.myapp.api.models.RecipeDTO
import com.example.myapp.api.models.RecipeDifficulty
import com.example.myapp.api.models.RecipeType
import withFullImageUrl



import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class InitialData(
    val recipes: List<com.example.myapp.api.models.RecipeDTO>,
    val types: List<com.example.myapp.api.models.RecipeType>,
    val difficulties: List<com.example.myapp.api.models.RecipeDifficulty>,
    val cuisines: List<com.example.myapp.api.models.RecipeCuisine>
)

data class HomepageState(
    val recipes: List<RecipeDTO> = emptyList(), // Используем RecipeDTO напрямую
    val filteredRecipes: List<RecipeDTO> = emptyList(),
    val searchQuery: String = "",
    val selectedType: RecipeType? = null,
    val selectedDifficulty: RecipeDifficulty? = null,
    val selectedCuisine: RecipeCuisine? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val recipeTypes: List<RecipeType> = emptyList(),
    val difficulties: List<RecipeDifficulty> = emptyList(),
    val cuisines: List<RecipeCuisine> = emptyList(),

    val sortNewest: Boolean = false,
    val filterIngr7: Boolean = false,
    val filterIngr10: Boolean = false,
    val filterTime30: Boolean = false,
    val filterTime60: Boolean = false

)

class HomepageViewModel : ViewModel() {
    private val recipeApi = ApiProvider.recipeApi
    private val catalogApi = ApiProvider.catalogApi

    private val _state = MutableStateFlow(HomepageState())
    val state = _state.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val data = withContext(Dispatchers.IO) {

                    val recipesResp = try {
                        recipeApi.apiRecipeGet().execute()
                    } catch (e: Exception) {
                        Log.e("HomepageVM", "apiRecipeGet execute failed", e)
                        null
                    }
                    val fixedRecipes = recipesResp?.body()?.map { it.withFullImageUrl() } ?: emptyList()

                    //Log.d("HomepageVM", "First recipe image url = ${fixedRecipes.firstOrNull()?.mainImagePath}")


                    val typesResp = try {
                        catalogApi.recipetypesGet().execute()
                    } catch (e: Exception) {
                        Log.e("HomepageVM", "recipetypesGet execute failed", e)
                        null
                    }

                    val difficultiesResp = try {
                        catalogApi.difficultiesGet().execute()
                    } catch (e: Exception) {
                        Log.e("HomepageVM", "difficultiesGet execute failed", e)
                        null
                    }

                    val cuisinesResp = try {
                        catalogApi.cuisinesGet().execute()
                    } catch (e: Exception) {
                        Log.e("HomepageVM", "cuisinesGet execute failed", e)
                        null
                    }

                    InitialData(
                        recipes = fixedRecipes,//recipesResp?.body() ?: emptyList(),
                        types = typesResp?.body() ?: emptyList(),
                        difficulties = difficultiesResp?.body() ?: emptyList(),
                        cuisines = cuisinesResp?.body() ?: emptyList()
                    )
                }

                Log.d("HomepageVM", "Loaded recipes count = ${data.recipes.size}")

                _state.value = HomepageState(
                    recipes = data.recipes,
                    filteredRecipes = data.recipes,
                    recipeTypes = data.types,
                    difficulties = data.difficulties,
                    cuisines = data.cuisines,
                    isLoading = false
                )

            } catch (e: Exception) {
                Log.e("HomepageVM", "loadInitialData top-level failure", e)
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки: ${e.message}"
                )
            }
        }
    }


    fun searchRecipes(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        applyFilters()
    }

    fun filterByType(type: RecipeType?) {
        _state.value = _state.value.copy(selectedType = type)
        applyFilters()
    }

    fun filterByDifficulty(difficulty: RecipeDifficulty?) {
        _state.value = _state.value.copy(selectedDifficulty = difficulty)
        applyFilters()
    }

    fun filterByCuisine(cuisine: RecipeCuisine?) {
        _state.value = _state.value.copy(selectedCuisine = cuisine)
        applyFilters()
    }

    private fun applyFilters() {
        val currentState = _state.value
        var filtered = currentState.recipes

        // Текстовый поиск
        if (currentState.searchQuery.isNotBlank()) {
            filtered = filtered.filter { recipe ->
                recipe.name?.contains(currentState.searchQuery, ignoreCase = true) == true
            }
        }

        // Фильтр по типу
        currentState.selectedType?.let { selectedType ->
            filtered = filtered.filter { recipe ->
                recipe.recipeType?.id == selectedType.id
            }
        }

        // Фильтр по сложности
        currentState.selectedDifficulty?.let { selectedDifficulty ->
            filtered = filtered.filter { recipe ->
                recipe.difficulty?.id == selectedDifficulty.id
            }
        }

        // Фильтр по кухне
        currentState.selectedCuisine?.let { selectedCuisine ->
            filtered = filtered.filter { recipe ->
                recipe.cuisine?.id == selectedCuisine.id
            }
        }

        _state.value = currentState.copy(filteredRecipes = filtered)
    }
    fun toggleFilter(tag: String) {
        val s = _state.value

        when (tag) {
            "сначала новые" ->
                _state.value = s.copy(sortNewest = true)

            "сначала старые" ->
                _state.value = s.copy(sortNewest = false)

            "до 7 ингредиентов" ->
                _state.value = s.copy(
                    filterIngr7 = !s.filterIngr7,
                    filterIngr10 = if (!s.filterIngr7) false else s.filterIngr10
                )

            "до 10 ингредиентов" ->
                _state.value = s.copy(
                    filterIngr10 = !s.filterIngr10,
                    filterIngr7 = if (!s.filterIngr10) false else s.filterIngr7
                )

            "до 30 мин" ->
                _state.value = s.copy(
                    filterTime30 = !s.filterTime30,
                    filterTime60 = if (!s.filterTime30) false else s.filterTime60
                )

            "< 1 часа" ->
                _state.value = s.copy(
                    filterTime60 = !s.filterTime60,
                    filterTime30 = if (!s.filterTime60) false else s.filterTime30
                )
        }

        applyAdvancedFilters()
    }
    private fun applyAdvancedFilters() {
        val s = _state.value
        var list = s.recipes

        list = if (s.sortNewest) list.reversed() else list

        val ingredientLimit =
            when {
                s.filterIngr7 -> 7
                s.filterIngr10 -> 10
                else -> null
            }

        ingredientLimit?.let { max ->
            list = list.filter { it.ingredients?.size ?: 0 < max }
        }

        val timeLimit =
            when {
                s.filterTime30 -> 30
                s.filterTime60 -> 60
                else -> null
            }

        timeLimit?.let { max ->
            list = list.filter { (it.cookingTime ?: 10000) <= max }
        }

        _state.value = s.copy(filteredRecipes = list)
    }

    fun toggleFavorite(recipe: RecipeDTO) {
//        viewModelScope.launch {
//            try {
//                // Обновляем локальное состояние
//                val updatedRecipes = _state.value.recipes.map {
//                    if (it.id == recipe.id) {
//                        it.copy(isFav = !it.isFav)
//                    } else {
//                        it
//                    }
//                }
//
//                val updatedFilteredRecipes = _state.value.filteredRecipes.map {
//                    if (it.id == recipe.id) {
//                        it.copy(isFav = !it.isFav)
//                    } else {
//                        it
//                    }
//                }
//
//                _state.value = _state.value.copy(
//                    recipes = updatedRecipes,
//                    filteredRecipes = updatedFilteredRecipes
//                )
//            } catch (e: Exception) {
//                _state.value = _state.value.copy(
//                    error = "Ошибка обновления избранного: ${e.message}"
//                )
//            }
//        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    fun refresh() {
        loadInitialData()
    }
}