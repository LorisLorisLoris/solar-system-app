package com.example.kotlinplanetsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kotlinplanetsapi.router.Routes
import com.example.kotlinplanetsapi.ui.screens.BodyDetailsScreen
import com.example.kotlinplanetsapi.ui.screens.SearchScreen
import com.example.kotlinplanetsapi.ui.theme.CustomTheme
import com.example.kotlinplanetsapi.viewmodel.MainViewModel

// MainActivity.kt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomTheme(darkTheme = isSystemInDarkTheme())  {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    // Appeler loadPlanets au lancement de AppNavigation
    LaunchedEffect(key1 = true) {
        viewModel.loadBodies()
    }

    NavHost(navController = navController, startDestination = Routes.SearchScreen.route) {
        composable(Routes.SearchScreen.route) {
            SearchScreen(navController = navController)
        }

        composable(
            route = "${Routes.BodyDetailsScreen.route}/{planetId}",
            arguments = listOf(navArgument("planetId") { type = NavType.StringType })
        ) {
            val planetId = it.arguments?.getString("planetId") ?: ""
            BodyDetailsScreen(planetId = planetId, navHostController = navController, viewModel = viewModel)
        }
    }
}