package com.example.gymjournal.settings

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymjournal.components.BottomNavBar
import com.example.gymjournal.components.TopNavBar
import com.example.gymjournal.navigations.Routes
import com.example.gymjournal.profile.ProfileImage
import com.example.gymjournal.ui.theme.AppTheme


@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopNavBar(navController = navController)
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.Settings)
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                ProfileImage()
            }
            Card(
                colors = androidx.compose.material3.CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Notifications",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {  },
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp))
                    Text(
                        text = "Change Password",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {  },
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp))
                    Row(
                        modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        ){
                        Text(
                            text = "Dark Mode",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        )
                        Switch(
                            checked = false,
                            onCheckedChange = {  }
                        )
                    }
                }
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode")
@Composable
fun SettingsScreenPreview() {
    AppTheme {
        SettingsScreen(navController = NavController(LocalContext.current))
    }
}