package com.example.gymjournal.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gymjournal.goals.GoalsScreen
import com.example.gymjournal.home.HomeScreen
import com.example.gymjournal.moves.MovesScreen
import com.example.gymjournal.onboarding.OnboardingPageOne
import com.example.gymjournal.profile.ProfileScreen
import com.example.gymjournal.profile.ProfileSetting
import com.example.gymjournal.routine.RoutineScreen
import com.example.gymjournal.settings.SettingsScreen
import com.example.gymjournal.start.RegisterScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.onBoarding,
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
        composable(Routes.onBoarding) {
            OnboardingPageOne(
                navController = navController
            )
        }
        composable(Routes.Goal) {
            GoalsScreen(
                navController = navController
            )
        }
    }
}
