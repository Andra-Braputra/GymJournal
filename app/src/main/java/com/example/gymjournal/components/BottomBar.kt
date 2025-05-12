package com.example.gymjournal.components

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gymjournal.navigations.Routes
import com.example.gymjournal.ui.theme.AppTheme


fun getNavigationItems(): List<NavItem> {
    return listOf(
        NavItem("Moves", Icons.Default.FitnessCenter, Routes.Moves),
        NavItem("Home", Icons.Default.Home, Routes.Home),
        NavItem("Routine", Icons.Default.DateRange, Routes.Routine)
    )
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = getNavigationItems()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (currentRoute == item.route)
                            MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.outline
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light Mode")
@Composable
fun BottomNavBarPreview() {
AppTheme {
    BottomNavBar(navController = rememberNavController())
    }
}
