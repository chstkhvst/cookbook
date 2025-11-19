package vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileState(
    val userName: String = "",
    val userRecipes: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val showAddRecipeForm: Boolean = false
)
data class AddRecipe(
    val name: String = "",
    val servings: Int = 1,
    val time: Int = 0,
    val category: String = "",
    val difficulty: String = "",
    val cuisine: String = ""
)
class ProfileViewModel : ViewModel( ){
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    fun addRecipe() {

        _state.value = _state.value.copy(
            userRecipes = _state.value.userRecipes + "r.name"
        )
    }
    fun editRecipe() {

    }
    fun deleteRecipe(recipeName: String) {
        _state.value = _state.value.copy(
            userRecipes = _state.value.userRecipes - recipeName
        )
    }
}