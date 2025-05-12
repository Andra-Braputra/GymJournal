package com.example.gymjournal.Workout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.gymjournal.components.TopNavBar
import com.example.gymjournal.ui.theme.AppTheme

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO, name = "Light Mode")
@Composable
fun WorkoutPreview() {
    AppTheme {
        WorkoutScreen(navController = NavController(LocalContext.current))
    }
}

@Composable
fun WorkoutScreen(navController: NavController) {
    Scaffold(
        topBar = { TopNavBar(navController = navController)}
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ){

        }

    }
}