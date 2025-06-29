package com.example.gymjournal.navigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.presentations.home.HomeScreen
import com.example.gymjournal.presentations.viewmodel.GoalViewModel
import com.example.gymjournal.presentations.workout.StartWorkoutScreen


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable(Routes.HOME) {
        HomeNavContent(navController)
    }
    composable(Routes.START_WORKOUT) {
        StartWorkoutScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HomeNavContent(navController: NavHostController) {
    val viewModel: GoalViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        navController = navController,
        goals = uiState.goals
    )
}
