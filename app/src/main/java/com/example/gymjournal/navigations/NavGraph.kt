package com.example.gymjournal.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gymjournal.presentations.home.HomeScreen
import com.example.gymjournal.presentations.moves.MovesScreen
import com.example.gymjournal.presentations.onboarding.OnboardingPager
import com.example.gymjournal.presentations.profile.ProfileScreen
import com.example.gymjournal.presentations.profile.ProfileSetting
import com.example.gymjournal.presentations.routine.RoutineScreen
import com.example.gymjournal.presentations.settings.SettingsScreen
import com.example.gymjournal.presentations.start.RegisterScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.OnBoarding,
    ) {
        composable(Routes.Home) {
            HomeScreen(
                navController = navController
            )
        }
        composable(Routes.Routine) {
            RoutineScreen(
                navController = navController
            )
        }
        composable(Routes.Moves) {
            MovesScreen(
                navController = navController,
            )
        }
        composable(Routes.Profile) {
            ProfileScreen(
                navController = navController
            )
        }
        composable(Routes.Settings) {
            SettingsScreen(
                navController = navController
            )
        }
        composable(Routes.ProfileSetting) {
            ProfileSetting(
                navController = navController
            )
        }
        composable(Routes.Register) {
            RegisterScreen(
                navController = navController
            )
        }
        composable(Routes.OnBoarding) {
            OnboardingPager(
                navController = navController
            )
        }
    }
}
