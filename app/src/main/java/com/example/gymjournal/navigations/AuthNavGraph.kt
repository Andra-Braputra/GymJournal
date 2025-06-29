package com.example.gymjournal.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.presentations.start.LoginScreen
import com.example.gymjournal.presentations.start.RegisterScreen
import com.example.gymjournal.presentations.start.StartScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Routes.AUTH,
        startDestination = Routes.START_SCREEN
    ) {
        composable(Routes.START_SCREEN) {
            StartScreen(navController)
        }
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }
    }
}