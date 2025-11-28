package vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.myapp.api.models.RecipeDTO
import withFullImageUrl
import android.util.Log
import com.example.myapp.api.models.FavRecipeDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class FavoritesState(
    val favoriteRecipes: List<RecipeDTO> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class FavoritesViewModel : ViewModel() {
    private val favrecipeApi = ApiProvider.favrecipeApi

    private val _state = MutableStateFlow(FavoritesState())
    val state = _state.asStateFlow()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val favorites = withContext(Dispatchers.IO) {
                    val response = favrecipeApi.apiFavRecipeUserFavoritesGet().execute()
                    response.body()
                        ?.map { it.withFullImageUrl() }
                        ?.map { it.copy(isFav = true) }
                        ?: emptyList()
                }

                Log.d("FavoritesVM", "Loaded ${favorites.size} favorite recipes")

                _state.value = FavoritesState(
                    favoriteRecipes = favorites,
                    isLoading = false
                )

            } catch (e: Exception) {
                Log.e("FavoritesVM", "Failed to load favorites", e)
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки избранного: ${e.message}"
                )
            }
        }
    }

    fun toggleFavorite(recipeId: Int) {
        viewModelScope.launch {
            try {
                val currentState = _state.value
                val recipe = currentState.favoriteRecipes.find { it.id == recipeId }

                withContext(Dispatchers.IO) {
                    if (recipe != null) {
                        favrecipeApi.apiFavRecipeRecipeRecipeIdDelete(recipeId).execute()
                    } else {
                        // Добавляем в избранное
                        val favRecipeDto = FavRecipeDTO(
                            id = -1,
                            userId = "",
                            recipeId = recipeId
                        )
                        favrecipeApi.apiFavRecipePost(favRecipeDto).execute()
                    }
                }

                if (recipe != null) {
                    // Удаляем из списка
                    val updatedRecipes = currentState.favoriteRecipes.filter { it.id != recipeId }
                    _state.value = currentState.copy(favoriteRecipes = updatedRecipes)
                } else {
                    loadFavorites()
                }

            } catch (e: Exception) {
                Log.e("FavoritesVM", "Failed to toggle favorite", e)
                _state.value = _state.value.copy(
                    error = "Ошибка при обновлении избранного: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}