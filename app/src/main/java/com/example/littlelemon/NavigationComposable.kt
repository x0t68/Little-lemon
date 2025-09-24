package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.data.local.MenuItemRoom

@Composable
fun NavigationComposable(
    navController: NavHostController,
    context: Context,
    menuItems: List<MenuItemRoom>
) {
    // لاحظت إنك تحفظ في Onboarding على "MyPrefs" - لذلك نستخدم نفس الاسم هنا
    val sharedPref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPref.contains("firstName")

    val startDestination = if (isLoggedIn) Home.route else Onboarding.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) { OnboardingScreen(navController, context) }
        composable(Home.route) { HomeScreen(navController, menuItems) }
        composable(Profile.route) { ProfileScreen(navController, context) }
    }
}
