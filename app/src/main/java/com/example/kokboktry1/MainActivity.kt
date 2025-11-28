package com.example.kokboktry1

import android.R.attr.type
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation.NavType
import com.example.kokboktry1.ui.theme.Kokboktry1Theme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val PROFILE = "profile"
    const val FAVORITES = "favorites"
    const val FORM = "form"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ApiProvider.initialize(this)
        setContent {
            Kokboktry1Theme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.LOGIN
                ) {
                    composable(Routes.LOGIN) {
                        LoginScreen(
                            onLoginSuccess = { navController.navigate(Routes.HOME) },
                            onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
                        )
                    }

                    composable(Routes.REGISTER) {
                        RegisterScreen(
                            onRegisterSuccess = {
                                navController.navigate(Routes.LOGIN)
                            },
                            onNavigateToLogin = {
                                navController.navigate(Routes.LOGIN)
                            }
                        )
                    }

                    composable(Routes.HOME) {
                        HomepageScreen(
                            onNavigateProfile = { navController.navigate(Routes.PROFILE) },
                            onNavigateFavorites = { navController.navigate(Routes.FAVORITES) },
                            onRecipeDetails = { recipeId ->
                                navController.navigate("details/$recipeId")
                            }

                        )
                    }

                    composable(Routes.PROFILE) {
                        ProfileScreen(
                            onNavigateHome = { navController.navigate(Routes.HOME) },
                            onNavigateFavorites = { navController.navigate(Routes.FAVORITES) },
                            onAddClick = { navController.navigate(Routes.FORM) },
                            onEditClick = { recipeId ->
                                navController.navigate("edit_recipe/$recipeId")
                            }
                        )
                    }

                    composable(Routes.FAVORITES) {
                        FavoritesScreen(
                            onNavigateHome = { navController.navigate(Routes.HOME) },
                            onNavigateFavorites = { navController.navigate(Routes.FAVORITES) },
                            onNavigateProfile = { navController.navigate(Routes.PROFILE) },
                            onRecipeDetails = { recipeId ->
                                navController.navigate("details/$recipeId")
                            }
                        )
                    }

                    composable(Routes.FORM) {
                        AddRecipeForm(
                            onNavigateHome = { navController.navigate(Routes.HOME) },
                            onExitClick = { navController.navigate(Routes.PROFILE) }
                        )
                    }
                    composable(
                        route = "edit_recipe/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) { backStackEntry ->

                        val id = backStackEntry.arguments?.getInt("id") ?: 0

                        EditRecipeForm(
                            recipeId = id,
                            onExitClick = { navController.navigate(Routes.PROFILE) },
                            onNavigateHome = { navController.navigate(Routes.HOME) },
                            onNavigateFavorites = { navController.navigate(Routes.FAVORITES) },
                            onNavigateProfile = { navController.navigate(Routes.PROFILE) }
                        )
                    }

                    composable(
                        route = "details/{recipeId}",
                        arguments = listOf(
                            navArgument("recipeId") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("recipeId") ?: return@composable
                       RecipeDetailsScreen(
                                recipeId = id,
                        onBack = { navController.navigate(Routes.HOME) }
                        )

                    }
                }
            }
        }

    }
    }

@PreviewScreenSizes
@Composable
fun Kokboktry1App() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    FAVORITES("Favorites", Icons.Default.Favorite),
    PROFILE("Profile", Icons.Default.AccountBox),
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "GANG $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Kokboktry1Theme {
        Greeting("Android")
    }
}