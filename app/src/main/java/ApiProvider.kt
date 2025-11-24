import android.util.Log
import com.example.myapp.api.endpoints.CatalogsApi
import com.example.myapp.api.endpoints.RecipeApi
import com.example.myapp.api.infrastructure.ApiClient
import com.example.myapp.api.models.RecipeDTO
object ApiProvider {
    private const val BASE_URL = "http://10.0.2.2:5119"
    //private const val BASE_URL = "https://10.0.2.2:7020"
    private val apiClient: ApiClient by lazy {
        ApiClient(
            baseUrl = BASE_URL
        ).apply {
            setLogger {message -> Log.d("ApiClient", message)}
        }
    }

    // API рецептов
    val recipeApi: RecipeApi by lazy {
        apiClient.createService(RecipeApi::class.java)
    }
    val catalogApi: CatalogsApi by lazy {
        apiClient.createService(CatalogsApi::class.java)
    }
    fun buildImageUrl(relativePath: String?): String? {
        if (relativePath.isNullOrBlank()) return null
        return BASE_URL.trimEnd('/') + "/" + relativePath.trimStart('/')
    }
}
fun RecipeDTO.withFullImageUrl(): RecipeDTO =
    this.copy(mainImagePath = ApiProvider.buildImageUrl(this.mainImagePath))