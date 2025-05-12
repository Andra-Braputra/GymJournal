package com.example.gymjournal.profile

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymjournal.components.BottomNavBar
import com.example.gymjournal.components.TopNavBar
import com.example.gymjournal.navigations.Routes
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
fun ProfileScreen(navController: NavController) {
    val userName = "Andra"
    val height = "175 cm"
    val weight = "75 kg"
    val gender = "Male"
    val dateOfBirth = "24 April, 2005 "

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
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Photo")
                }
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    ProfileInfoItem(
                        label = "Username",
                        value = userName,
                        icon = Icons.Default.Person
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    ProfileInfoItem(
                        label = "Height",
                        value = height,
                        icon = Icons.Default.Height
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    ProfileInfoItem(
                        label = "Weight",
                        value = weight,
                        icon = Icons.Default.MonitorWeight
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    ProfileInfoItem(
                        label = "Gender",
                        value = gender,
                        icon = Icons.Default.Face
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    ProfileInfoItem(
                        label = "Date of Birth",
                        value = dateOfBirth,
                        icon = Icons.Default.Cake
                    )
                }
            }
            Button(
                onClick = {
                    navController.navigate(Routes.ProfileSetting) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(top = 16.dp)
            ) {
                Text("Edit Profile")
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