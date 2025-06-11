package com.example.gymjournal.presentations.goals

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gymjournal.presentations.components.TopNavBar

@Composable
fun AddEditGoalScreen(
    navController: NavController,
    viewModel: GoalsViewModel,
    existingGoal: Goal? = null
) {
    var name by remember { mutableStateOf(existingGoal?.name ?: "") }
    var detail by remember { mutableStateOf(existingGoal?.detail ?: "") }
    var deadline by remember { mutableStateOf(existingGoal?.deadline ?: "") }

    Scaffold(
        topBar = { TopNavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Goal Name")
            TextField(value = name, onValueChange = { name = it })

            Spacer(Modifier.height(8.dp))
            Text("Goal Detail")
            TextField(value = detail, onValueChange = { detail = it })

            Spacer(Modifier.height(8.dp))
            Text("Deadline")
            TextField(value = deadline, onValueChange = { deadline = it })

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    if (existingGoal != null) {
                        viewModel.editGoal(existingGoal.copy(name = name, detail = detail, deadline = deadline))
                    } else {
                        viewModel.addGoal(name, detail, deadline)
                    }
                    navController.popBackStack()
                }
            ) {
                Text(if (existingGoal != null) "Update Goal" else "Add Goal")
            }

            if (existingGoal != null) {
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        viewModel.removeGoal(existingGoal)
                        navController.popBackStack()
                    }
                ) {
                    Text("Delete Goal")
                }
            }
        }
    }
}
