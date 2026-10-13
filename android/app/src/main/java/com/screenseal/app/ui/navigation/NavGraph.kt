package com.screenseal.app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.screenseal.app.ui.screens.ClientScreen
import com.screenseal.app.ui.screens.DisplayScreen
import com.screenseal.app.ui.screens.HostScreen
import com.screenseal.app.ui.screens.TelemetryScreen
import com.screenseal.app.ui.theme.BackgroundCard
import com.screenseal.app.ui.theme.BackgroundDeep
import com.screenseal.app.ui.theme.BorderSubtle
import com.screenseal.app.ui.theme.NavSelected
import com.screenseal.app.ui.theme.NavUnselected

private data class NavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector,
)

private val navItems = listOf(
    NavItem(Screen.Host,      "Host",       Icons.Default.Monitor),
    NavItem(Screen.Display,   "Display",    Icons.Default.DeviceHub),
    NavItem(Screen.Client,    "Cliente",    Icons.Default.Smartphone),
    NavItem(Screen.Telemetry, "Telemetría", Icons.Default.BarChart),
)

@Composable
fun ScreenSealNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        containerColor = BackgroundDeep,
        bottomBar = {
            NavigationBar(
                containerColor = BackgroundCard,
                tonalElevation = 0.dp,
            ) {
                navItems.forEach { item ->
                    val selected = currentDestination?.hierarchy
                        ?.any { it.route == item.screen.route } == true

                    NavigationBarItem(
                        selected  = selected,
                        onClick   = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState    = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector        = item.icon,
                                contentDescription = item.label,
                            )
                        },
                        label = {
                            Text(
                                text       = item.label,
                                fontSize   = 10.sp,
                                fontFamily = FontFamily.Monospace,
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor   = NavSelected,
                            selectedTextColor   = NavSelected,
                            unselectedIconColor = NavUnselected,
                            unselectedTextColor = NavUnselected,
                            indicatorColor      = Color(0xFF1A2236),
                        ),
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController    = navController,
            startDestination = Screen.Host.route,
            modifier         = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            composable(Screen.Host.route)      { HostScreen() }
            composable(Screen.Display.route)   { DisplayScreen() }
            composable(Screen.Client.route)    { ClientScreen() }
            composable(Screen.Telemetry.route) { TelemetryScreen() }
        }
    }
}
