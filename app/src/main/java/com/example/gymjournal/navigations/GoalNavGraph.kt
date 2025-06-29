
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.presentations.goals.AddGoalScreen
import com.example.gymjournal.presentations.goals.EditGoalScreen
import com.example.gymjournal.presentations.goals.GoalsScreen
import com.example.gymjournal.presentations.viewmodel.GoalViewModel

fun NavGraphBuilder.goalNavGraph(navController: NavHostController) {
    composable(Routes.GOAL_SCREEN) {
        val viewModel: GoalViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        GoalsScreen(
            navController = navController,
            goals = uiState.goals,
            onGoalClick = { goal -> navController.navigate(Routes.editGoal(goal.id)) },
            onAddClick = { navController.navigate(Routes.ADD_GOAL_SCREEN) }
        )
    }

    composable(Routes.ADD_GOAL_SCREEN) {
        AddGoalScreen(navController = navController)
    }

    composable(Routes.EDIT_GOAL_SCREEN) { backStackEntry ->
        val goalId = backStackEntry.arguments?.getString("goalId")?.toIntOrNull()
        goalId?.let {
            EditGoalScreen(navController = navController, goalId = it)
        }
    }
}

