import android.util.Log
import com.example.myapp.api.endpoints.CatalogsApi
import com.example.myapp.api.endpoints.RecipeApi
import com.example.myapp.api.endpoints.AccountApi
import com.example.myapp.api.infrastructure.ApiClient
import com.example.myapp.api.models.RecipeDTO
import android.content.Context
import com.example.myapp.api.AuthInterceptor
import com.example.myapp.api.TokenManager
import com.example.myapp.api.endpoints.FavRecipeApi

object ApiProvider {
    private const val BASE_URL = "http://10.0.2.2:5119"
    //private const val BASE_URL = "https://10.0.2.2:7020"

    private lateinit var tokenManager: TokenManager
    private lateinit var apiClient: ApiClient
    private var isInitialized = false
    fun initialize(context: Context) {
        tokenManager = TokenManager(context)
        isInitialized = true
        apiClient = ApiClient(
            baseUrl = BASE_URL
        ).apply {
            setLogger { message -> Log.d("ApiClient", message) }
            addAuthorization("jwt", AuthInterceptor(tokenManager))
        }
    }
    val recipeApi: RecipeApi get() = apiClient.createService(RecipeApi::class.java)
    val catalogApi: CatalogsApi get() = apiClient.createService(CatalogsApi::class.java)
    val accountApi: AccountApi get() = apiClient.createService(AccountApi::class.java)
    val favrecipeApi: FavRecipeApi get() = apiClient.createService(FavRecipeApi::class.java)
    fun buildImageUrl(relativePath: String?): String? {
        if (relativePath.isNullOrBlank()) return null
        return BASE_URL.trimEnd('/') + "/" + relativePath.trimStart('/')
    }
    fun saveToken(token: String) {
        tokenManager.saveToken(token)
    }
}
//fun RecipeDTO.withFullImageUrl(): RecipeDTO =
//    this.copy(mainImagePath = ApiProvider.buildImageUrl(this.mainImagePath))

fun RecipeDTO.withFullImageUrl(): RecipeDTO =
    this.copy(
        mainImagePath = ApiProvider.buildImageUrl(this.mainImagePath),
        steps = this.steps?.map { step ->
            step.copy(
                stepImagePath = ApiProvider.buildImageUrl(step.stepImagePath)
            )
        }
    )