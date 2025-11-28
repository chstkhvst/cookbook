package vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.myapp.api.models.RegisterModel

data class RegisterState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

class RegisterViewModel : ViewModel() {

    private val accountApi = ApiProvider.accountApi

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun register(username: String, password: String) {
        viewModelScope.launch {
            _state.value = RegisterState(isLoading = true)

            try {
                val body = RegisterModel(
                    userName = username,
                    password = password
                )

                val response = withContext(Dispatchers.IO) {
                    accountApi.apiAccountRegisterPost(body).execute()
                }

                if (response.isSuccessful) {
                    _state.value = RegisterState(
                        isLoading = false,
                        success = true
                    )
                } else {
                    val errorMessage = when (response.code()) {
                        400 -> "Некорректные данные. Проверьте введенные данные."
                        409 -> "Пользователь с таким логином уже существует."
                        else -> "Ошибка регистрации: код ${response.code()}"
                    }
                    _state.value = RegisterState(
                        isLoading = false,
                        error = errorMessage
                    )
                }

            } catch (e: Exception) {
                _state.value = RegisterState(
                    isLoading = false,
                    error = "Ошибка соединения: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}