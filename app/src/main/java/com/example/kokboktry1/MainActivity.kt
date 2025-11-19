package com.example.kokboktry1

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
import com.example.kokboktry1.ui.theme.Kokboktry1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
            setContent {
                Kokboktry1Theme {
                    var currentScreen by remember { mutableStateOf("login") }

                    when (currentScreen) {
                        "login" -> LoginScreen(
                            onLoginSuccess = {
                                currentScreen = "home"
                                //currentScreen = "details"
                            },
                            onNavigateToRegister = {
                                currentScreen = "register"
                            }
                        )
                        "register" -> RegisterScreen(
                            onRegisterSuccess = {
                                currentScreen = "home"
                            },
                            onNavigateToLogin = {
                                currentScreen = "login"
                            }
                        )
                        "profile" -> ProfileScreen(
                            onNavigateHome = { currentScreen = "home" },
                            onNavigateFavorites = {currentScreen = "favorites"},
                            onAddClick = {currentScreen = "form"}
                        )
                        "home" -> HomepageScreen(
                            onNavigateProfile = {currentScreen = "profile"},
                            onNavigateFavorites = {currentScreen = "favorites"}
                        )
                        "favorites" -> FavoritesScreen(
                            onNavigateProfile = {currentScreen = "profile"},
                            onNavigateHome = { currentScreen = "home" },
                        )
                        "form" -> AddRecipeForm(
                            onExitClick = {currentScreen = "profile"}
                        )
                        "details" -> RecipeDetailsScreen (

                        )
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