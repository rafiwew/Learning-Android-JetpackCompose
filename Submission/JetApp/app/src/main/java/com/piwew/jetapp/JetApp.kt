package com.piwew.jetapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.piwew.jetapp.navigation.Screen
import com.piwew.jetapp.ui.components.TopAppBar
import com.piwew.jetapp.ui.screen.detail.DetailScreen
import com.piwew.jetapp.ui.screen.favorite.FavoriteScreen
import com.piwew.jetapp.ui.screen.home.HomeScreen
import com.piwew.jetapp.ui.theme.JetAppTheme

@Composable
fun JetApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        topBar = {
            if (currentRoute != Screen.DetailHero.route) {
                TopAppBar()
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToDetail = { heroId ->
                    navController.navigate(Screen.DetailHero.createRoute(heroId))
                })
            }
            composable(
                route = Screen.Favorite.route
            ) {
                FavoriteScreen()
            }
            composable(
                route = Screen.DetailHero.route,
                arguments = listOf(navArgument("heroId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("heroId") ?: ""
                DetailScreen(
                    heroId = id,
                    navigateBack = { navController.navigateUp() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJetApp() {
    JetAppTheme {
        JetApp()
    }
}