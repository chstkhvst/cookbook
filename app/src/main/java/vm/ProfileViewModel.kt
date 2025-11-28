package vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.api.endpoints.AccountApi
import com.example.myapp.api.endpoints.RecipeApi
import com.example.myapp.api.models.UpdateProfileModel
import com.example.myapp.api.models.RecipeDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import withFullImageUrl

data class ProfileState(
    val userName: String = "",
    val userRecipes: List<RecipeDTO> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

class ProfileViewModel : ViewModel() {

    private val accountApi = ApiProvider.accountApi
    private val recipeApi = ApiProvider.recipeApi

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        loadProfile()
    }
    private fun loadProfile() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                val profile = withContext(Dispatchers.IO) {
                    val resp = accountApi.apiAccountProfileGet().execute()
                    if (!resp.isSuccessful) {
                        throw Exception("Ошибка загрузки профиля: ${resp.code()}")
                    }
                    resp.body()
                }

                if (profile == null) throw Exception("Пустой ответ от сервера")

                _state.value = _state.value.copy(
                    userName = profile.userName ?: "",
                    userRecipes = profile.recipes?.map { it.withFullImageUrl() } ?: emptyList(),
                    isLoading = false
                )

            } catch (e: Exception) {
                Log.e("ProfileVM", "loadProfile failed", e)
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка загрузки профиля"
                )
            }
        }
    }

    fun deleteRecipe(recipeId: Int) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val resp = recipeApi.apiRecipeIdDelete(recipeId).execute()
                    if (!resp.isSuccessful) throw Exception("Ошибка удаления рецепта")
                }

                _state.value = _state.value.copy(
                    userRecipes = _state.value.userRecipes.filterNot { it.id == recipeId }
                )

            } catch (e: Exception) {
                Log.e("ProfileVM", "deleteRecipe failed", e)
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    fun clearSuccess() {
        _state.value = _state.value.copy(success = false)
    }
}
