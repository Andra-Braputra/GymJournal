package com.example.gymjournal.presentations.goals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gymjournal.domain.model.Goal
import com.example.gymjournal.presentations.components.BottomNavBar
import com.example.gymjournal.presentations.components.TopNavBar

@Composable
fun GoalsScreen(
    goals: List<Goal>,
    onGoalClick: (Goal) -> Unit,
    onAddClick: () -> Unit,
    navController: NavHostController
) {
    Scaffold(
        topBar = { TopNavBar(navController = navController) },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            // Header
            item {
                Text(
                    text = "Goals",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Goals list
            items(goals) { goal ->
                GoalsCard(goal = goal, onClick = { onGoalClick(goal) })
            }

            // Add Goal button
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier
                        .width(340.dp)
                        .padding(bottom = 10.dp)
                        .clickable { onAddClick() }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Add",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Add Goal", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun GoalsCard(goal: Goal, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier
            .width(340.dp)
            .padding(bottom = 10.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(goal.name, style = MaterialTheme.typography.titleLarge)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text("Detail: ${goal.detail}", style = MaterialTheme.typography.bodyMedium)
            Text("Deadline: ${goal.deadline}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

