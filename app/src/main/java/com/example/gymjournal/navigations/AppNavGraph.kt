package com.example.gymjournal.navigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.presentations.profile.ProfileScreen
import com.example.gymjournal.presentations.profile.ProfileSettingScreen
import com.example.gymjournal.presentations.settings.ChangePasswordScreen
import com.example.gymjournal.presentations.settings.SettingsScreen
import com.example.gymjournal.presentations.splash.SplashScreen
import goalNavGraph

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }

        onboardingNavGraph(navController)
        authNavGraph(navController)
        homeNavGraph(navController)
        goalNavGraph(navController)
        routineNavGraph(navController)
        movesNavGraph(navController)

        // Global screens
        composable(Routes.SETTINGS) { SettingsScreen(navController) }
        composable(Routes.PROFILE) { ProfileScreen(navController) }
        composable(Routes.PROFILE_SETTING) { ProfileSettingScreen(navController) }
        composable(Routes.CHANGE_PASSWORD) { ChangePasswordScreen(navController = navController) }
    }
}

