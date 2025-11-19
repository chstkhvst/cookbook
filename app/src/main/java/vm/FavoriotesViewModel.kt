package vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Recipe(
    val name: String = "",
    val servings: Int = 1,
    val time: Int = 0,
    val category: String = "",
    val difficulty: String = "",
    val cuisine: String = ""
)
data class FavoritesState(
    val favoriteRecipes: List<Recipe> = emptyList(), // список избранных
)
class FavoritesViewModel : ViewModel( ){
    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    init {
        loadRecipes()
    }
    fun loadRecipes() {
        // обращение к слою модели
        _state.value = FavoritesState(
            favoriteRecipes = listOf(
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
    fun onLikeClick(r: Recipe) {
        //
        val updatedRecipes = _state.value.favoriteRecipes.toMutableList().apply {
            removeAll { it.name == r.name }
        }
        _state.value = _state.value.copy(favoriteRecipes = updatedRecipes)
    }

}