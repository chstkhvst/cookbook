package vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.api.models.RecipeDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import withFullImageUrl

data class RecipeDetailState(
    val recipe: RecipeDTO? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class RecipeDetailViewModel : ViewModel() {

    private val recipeApi = ApiProvider.recipeApi

    private val _state = MutableStateFlow(RecipeDetailState())
    val state = _state.asStateFlow()

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            _state.value = RecipeDetailState(isLoading = true)

            try {
                val recipe: RecipeDTO? = withContext(Dispatchers.IO) {
                    recipeApi.apiRecipeIdGet(recipeId)
                        .execute()
                        .body()
                        ?.withFullImageUrl()
                }

                _state.value = RecipeDetailState(
                    recipe = recipe,
                    isLoading = false
                )

            } catch (e: Exception) {
                _state.value = RecipeDetailState(
                    recipe = null,
                    isLoading = false,
                    error = "Ошибка загрузки: ${e.message}"
                )
            }
        }
    }
    fun updatePortions(newPortions: Int) {
        val current = _state.value.recipe ?: return
        val original = current.portions ?: return

        val ratio = newPortions.toFloat() / original.toFloat()

        val updatedIngredients = current.ingredients?.map { ing ->
            ing.copy(
                weight = ((ing.weight ?: 0) * ratio).toInt()
            )
        }

        _state.value = _state.value.copy(
            recipe = current.copy(
                portions = newPortions,
                ingredients = updatedIngredients
            )
        )
    }


    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
