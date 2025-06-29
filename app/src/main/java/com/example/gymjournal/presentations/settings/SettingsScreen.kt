package com.example.gymjournal.presentations.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.presentations.components.BottomNavBar
import com.example.gymjournal.presentations.components.TopNavBar
import com.example.gymjournal.presentations.profile.ProfileImage


@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState(initial = false)

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
                    navController.navigate(Routes.SETTINGS)
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
            Box(modifier = Modifier.padding(vertical = 16.dp)) {
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
                Column(modifier = Modifier.padding(10.dp)) {

                    Text(
                        text = "Change Password",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(Routes.CHANGE_PASSWORD)
                            }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp))

                    // 🌙 Dark Mode Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dark Mode",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        )
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = {
                                settingsViewModel.toggleTheme()
                            }
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp))

                    Box(modifier = Modifier.fillMaxSize()) {
                        Button(
                            onClick = {
                                settingsViewModel.logout()
                                navController.navigate(Routes.LOGIN) { popUpTo(0) }
                            },
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Center)
                        ) {
                            Text(text = "Logout")
                        }
                    }
                }
            }
        }
    }
}
