package com.example.gymjournal.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.presentations.routine.AddRoutineExerciseScreen
import com.example.gymjournal.presentations.routine.EditRoutineExerciseScreen
import com.example.gymjournal.presentations.routine.RoutineDetailScreen
import com.example.gymjournal.presentations.routine.RoutinesScreen
import com.example.gymjournal.presentations.routine.SelectMoveScreen

fun NavGraphBuilder.routineNavGraph(navController: NavHostController) {
    composable(Routes.ROUTINE) {
        RoutinesScreen(navController = navController)
    }

    composable(
        route = "${Routes.ROUTINE_DETAIL}/{routineId}",
        arguments = listOf(navArgument("routineId") { type = NavType.IntType })
    ) { backStackEntry ->
        val routineId = backStackEntry.arguments?.getInt("routineId") ?: return@composable
        RoutineDetailScreen(
            navController = navController,
            routineId = routineId
        )
    }

    composable(
        route = "${Routes.SELECT_MOVE}/{dayId}",
        arguments = listOf(navArgument("dayId") { type = NavType.IntType })
    ) { backStackEntry ->
        val dayId = backStackEntry.arguments?.getInt("dayId") ?: return@composable
        SelectMoveScreen(
            navController = navController,
            dayId = dayId
        )
    }

    composable(
        route = "add_routine_exercise/{dayId}/{exerciseId}",
        arguments = listOf(
            navArgument("dayId") { type = NavType.IntType },
            navArgument("exerciseId") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val dayId = backStackEntry.arguments?.getInt("dayId") ?: return@composable
        val exerciseId = backStackEntry.arguments?.getInt("exerciseId") ?: return@composable

        AddRoutineExerciseScreen(
            navController = navController,
            routineId = 0, // optional or can be removed if not used
            dayId = dayId,
            exerciseId = exerciseId
        )
    }

    composable(
        route = "${Routes.EDIT_EXERCISE}/{exerciseId}",
        arguments = listOf(navArgument("exerciseId") { type = NavType.IntType })
    ) { backStackEntry ->
        val exerciseId = backStackEntry.arguments?.getInt("exerciseId") ?: return@composable
        EditRoutineExerciseScreen(
            navController = navController,
            exerciseId = exerciseId
        )
    }
}

