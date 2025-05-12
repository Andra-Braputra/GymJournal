package com.example.gymjournal.home

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymjournal.components.BottomNavBar
import com.example.gymjournal.navigations.Routes
import com.example.gymjournal.ui.theme.AppTheme


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun HomePreview() {
    AppTheme {
        HomeScreen(
            navController = NavController(LocalContext.current)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Home") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate(Routes.Profile)
                        }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavBar(navController = navController)
            }
        ) { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                GoalCard(navController = navController)
                WorkoutCard()
            }
        }
    }
}

@Composable
fun GoalCard(navController: NavController) {
    val cardWidth = 340.dp
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier
            .width(cardWidth)
            .padding(top = 10.dp, bottom = 20.dp)
            .clickable { navController.navigate(Routes.Goal) }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Goals",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
            Text(
                text = "Goal Name",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 5.dp, start = 30.dp, end = 16.dp)
            )
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "20kg / 25kg",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp)
            )
        }
    }
}

@Composable
fun WorkoutCard() {
    val cardWidth = 340.dp

    Column {
        Text(
            text = "Schedule's Name",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(5.dp)
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            modifier = Modifier
                .width(cardWidth)
                .padding(bottom = 10.dp)
        ) {
            Column(modifier = Modifier.wrapContentSize()) {
                Text(
                    text = "Days",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp, top = 5.dp)
                )
                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = "Routine Name",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(20.dp)
                )
                Button(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp)
                ) {
                    Text(text = "Start Routine")
                }
            }
        }
    }
}
