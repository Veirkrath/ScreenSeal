package com.screenseal.app.ui.navigation

/** Rutas de navegación principal (bottom nav) */
sealed class Screen(val route: String) {
    data object Host      : Screen("host")
    data object Display   : Screen("display")
    data object Client    : Screen("client")
    data object Telemetry : Screen("telemetry")
}
