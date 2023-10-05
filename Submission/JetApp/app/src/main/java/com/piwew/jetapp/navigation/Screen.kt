package com.piwew.jetapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen ("about")
    object DetailHero : Screen("home/{heroId}") {
        fun createRoute(heroId: String) = "home/$heroId"
    }
}
