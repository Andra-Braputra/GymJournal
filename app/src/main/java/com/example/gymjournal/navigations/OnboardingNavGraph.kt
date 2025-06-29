package com.example.gymjournal.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.presentations.onboarding.OnboardingPager

fun NavGraphBuilder.onboardingNavGraph(navController: NavHostController) {
    composable(Routes.ONBOARDING) {
        OnboardingPager(navController)
    }
}
