package vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.myapp.api.models.LoginModel
import ApiProvider
import com.example.myapp.api.TokenManager

data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
data class LoginResponse(
    val token: String,
    val userName: String,
    val userRole: String
)

class LoginViewModel : ViewModel() {

    private val accountApi = ApiProvider.accountApi


    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState(isLoading = true)

            try {
                val body = LoginModel(
                    userName = username,
                    password = password
                )

                val response = withContext(Dispatchers.IO) {
                    accountApi.apiAccountLoginPost(body).execute()
                }

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.token?.let { token ->
                        ApiProvider.saveToken(token)
                    }
                    _state.value = LoginState(
                        isLoading = false,
                        success = true
                    )
                }
                else {
                    _state.value = LoginState(
                        isLoading = false,
                        error = "Ошибка входа: код ${response.code()}"
                    )
                }

            } catch (e: Exception) {
                _state.value = LoginState(
                    isLoading = false,
                    error = "Ошибка: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
