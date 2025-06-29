package com.example.gymjournal.presentations.goals

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.gymjournal.domain.model.Goal
import com.example.gymjournal.presentations.components.TopNavBar
import com.example.gymjournal.presentations.viewmodel.GoalViewModel

@Composable
fun EditGoalScreen(
    navController: NavHostController,
    goalId: Int,
    viewModel: GoalViewModel = hiltViewModel()
) {
    val goal by produceState<Goal?>(initialValue = null) {
        value = viewModel.getGoalById(goalId)
    }

    var name by rememberSaveable { mutableStateOf("") }
    var detail by rememberSaveable { mutableStateOf("") }
    var deadline by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(goal) {
        goal?.let {
            name = it.name
            detail = it.detail
            deadline = it.deadline
        }
    }

    Scaffold(
        topBar = { TopNavBar(navController = navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Goal Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = detail,
                    onValueChange = { detail = it },
                    label = { Text("Detail") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = deadline,
                    onValueChange = { deadline = it },
                    label = { Text("Deadline") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        goal?.copy(name = name, detail = detail, deadline = deadline)?.let {
                            viewModel.updateGoal(it)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Goal")
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        goal?.let {
                            viewModel.deleteGoal(it)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text("Delete Goal")
                }
            }
        }
    }
}
