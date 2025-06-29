package com.example.gymjournal.presentations.profile

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gymjournal.core.constant.Routes
import com.example.gymjournal.presentations.components.BottomNavBar
import com.example.gymjournal.presentations.components.TopNavBar
import com.example.gymjournal.ui.theme.AppTheme

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode")
@Composable
fun ProfilePreview(){
    AppTheme {
        ProfileScreen(navController = NavController(LocalContext.current))
    }
}

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.profileState.collectAsState()

    Scaffold(
        topBar = { TopNavBar(navController = navController) },
        bottomBar = { BottomNavBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.SETTINGS) },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ProfileInfoItem("Name", profile.name ?: "-", Icons.Default.Person)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        ProfileInfoItem("Height", profile.height ?: "-", Icons.Default.Height)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        ProfileInfoItem("Weight", profile.weight ?: "-", Icons.Default.MonitorWeight)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        ProfileInfoItem("Gender", profile.gender ?: "-", Icons.Default.Face)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        ProfileInfoItem("Date of Birth", profile.dateOfBirth ?: "-", Icons.Default.Cake)
                    }
                }
            }

            item {
                Button(
                    onClick = { navController.navigate(Routes.PROFILE_SETTING) },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(top = 16.dp)
                ) {
                    Text("Edit Profile")
                }
            }
        }
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun ProfileImage() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(120.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
    ) {
        Icon(
            Icons.Default.Person,
            contentDescription = "Profile Image",
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.size(60.dp)
        )
    }
}