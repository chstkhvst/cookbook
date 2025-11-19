package vm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomepageState(
    val recipes: List<Recipe> = emptyList(), // все рецепты
    val filteredRecipes: List<Recipe> = emptyList(), // отфильтрованные рецепты
    val searchQuery: String = "",
    val selectedCategory: String = "",
    val selectedDifficulty: String = "",
    val selectedCuisine: String = "",
    val isLoading: Boolean = false
)

class HomepageViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomepageState())
    val state = _state.asStateFlow()

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        _state.value = HomepageState(
            recipes = listOf(
                Recipe(
                    name = "Эклеры",
                    servings = 10,
                    time = 120,
                    category = "десерт",
                    difficulty = "5★",
                    cuisine = "французская"
                ),
                Recipe(
                    name = "Тирамису",
                    servings = 6,
                    time = 90,
                    category = "десерт",
                    difficulty = "3★",
                    cuisine = "итальянская"
                ),
                Recipe(
                    name = "Паста Карбонара",
                    servings = 4,
                    time = 30,
                    category = "основное",
                    difficulty = "2★",
                    cuisine = "итальянская"
                )
            ),
            filteredRecipes = listOf(
                Recipe(
                    name = "Эклеры",
                    servings = 10,
                    time = 120,
                    category = "десерт",
                    difficulty = "5★",
                    cuisine = "французская"
                ),
                Recipe(
                    name = "Тирамису",
                    servings = 6,
                    time = 90,
                    category = "десерт",
                    difficulty = "3★",
                    cuisine = "итальянская"
                ),
                Recipe(
                    name = "Паста Карбонара",
                    servings = 4,
                    time = 30,
                    category = "основное",
                    difficulty = "2★",
                    cuisine = "итальянская"
                )
            )
        )
    }

    fun searchRecipes(query: String) {
        val filtered = _state.value.recipes.filter {
            it.name.contains(query, ignoreCase = true)
        }
        _state.value = _state.value.copy(
            searchQuery = query,
            filteredRecipes = filtered
        )
    }

    fun filterByCategory(category: String) {

    }

    fun filterByDifficulty(difficulty: String) {

    }

    fun filterByCuisine(cuisine: String) {

    }

    fun toggleFavorite(recipe: Recipe) {

    }
}