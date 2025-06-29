package com.example.gymjournal.presentations.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymjournal.R
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.domain.model.Goal
import com.example.gymjournal.domain.model.Routine
import com.example.gymjournal.domain.model.RoutineDay
import com.example.gymjournal.presentations.components.BottomNavBar
import com.example.gymjournal.presentations.viewmodel.RoutineViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    goals: List<Goal>
) {
    val routineViewModel: RoutineViewModel = hiltViewModel()
    val selectedRoutine by routineViewModel.selectedRoutine.collectAsState()
    val routineDays by routineViewModel.routineDays.collectAsState()

    val todayName = LocalDate.now().dayOfWeek.name.lowercase()
        .replaceFirstChar { it.uppercase() }
    val today = routineDays.find { it.name == todayName }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.nav_home)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Routes.PROFILE)
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile"
                        )
                    }
                }
            )
        },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            item {
                GoalCard(
                    navController = navController,
                    goals = goals,
                    modifier = Modifier
                        .width(320.dp)
                        .wrapContentHeight()
                        .padding(vertical = 8.dp)
                )
            }

            item {
                WorkoutCard(
                    navController = navController,
                    selectedRoutine = selectedRoutine,
                    today = today,
                    modifier = Modifier
                        .width(320.dp)
                        .wrapContentHeight()
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun GoalCard(
    navController: NavController,
    goals: List<Goal>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Goals", style = MaterialTheme.typography.titleLarge)

            if (goals.isEmpty()) {
                Text(
                    text = "No goals yet",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                val goal = goals.first()
                Text(
                    text = goal.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )

                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Text(
                    text = goal.detail.ifBlank { "No details provided" },
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 8.dp),
                    maxLines = 3
                )
            }

            Button(
                onClick = { navController.navigate(Routes.GOAL_SCREEN) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            ) {
                Text("See All Goals")
            }
        }
    }
}

@Composable
fun WorkoutCard(
    selectedRoutine: Routine?,
    today: RoutineDay?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = today?.name ?: "Today",
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                text = selectedRoutine?.name ?: "No Routine Selected",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp)
            )

            Button(
                onClick = { navController.navigate(Routes.START_WORKOUT) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Start Workout")
            }
        }
    }
}