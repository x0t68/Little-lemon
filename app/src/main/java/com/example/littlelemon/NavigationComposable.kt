package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationComposable(navController: NavHostController, context: Context) {
    val sharedPref = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPref.contains("firstName")

    val startDestination = if (isLoggedIn) Home.route else Onboarding.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) { OnboardingScreen(navController, context) }
        composable(Home.route) { HomeScreen(navController) }
        composable(Profile.route) { ProfileScreen(navController, context) }
    }
}
