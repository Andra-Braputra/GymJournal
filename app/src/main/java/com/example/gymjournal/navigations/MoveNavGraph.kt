package com.example.gymjournal.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.presentations.moves.AddMoveScreen
import com.example.gymjournal.presentations.moves.MoveDetailScreen
import com.example.gymjournal.presentations.moves.MovesScreen

fun NavGraphBuilder.movesNavGraph(navController: NavHostController) {

    composable(Routes.MOVES) {
        MovesScreen(navController = navController)
    }

    composable(Routes.ADD_MOVE) {
        AddMoveScreen(navController = navController)
    }

    composable(
        route = Routes.DETAIL_MOVE,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { backStackEntry ->
        val moveId = backStackEntry.arguments?.getInt("id") ?: return@composable
        MoveDetailScreen(navController = navController, exerciseId = moveId)
    }
}
