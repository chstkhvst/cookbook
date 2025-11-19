package vm
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddRecipeViewModel : ViewModel() {
    private val _state = MutableStateFlow(AddRecipeState())
    val state: StateFlow<AddRecipeState> = _state.asStateFlow()

    fun saveRecipe(
        name: String,
        servings: Int,
        time: Int,
        category: String,
        difficulty: String,
        cuisine: String
    ) {
        _state.value = _state.value.copy(
            name = name,
            servings = servings,
            time = time,
            category = category,
            difficulty = difficulty,
            cuisine = cuisine
        )
    }
}

data class AddRecipeState(
    val name: String = "",
    val servings: Int = 1,
    val time: Int = 0,
    val category: String = "",
    val difficulty: String = "",
    val cuisine: String = ""
)