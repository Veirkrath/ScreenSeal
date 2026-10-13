package com.screenseal.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ScreenSealColorScheme = darkColorScheme(
    primary          = AccentCyan,
    secondary        = AccentPurple,
    tertiary         = GreenStable,
    background       = BackgroundDeep,
    surface          = BackgroundCard,
    surfaceVariant   = BackgroundSurface,
    onPrimary        = BackgroundDeep,
    onSecondary      = TextPrimary,
    onBackground     = TextPrimary,
    onSurface        = TextPrimary,
    outline          = BorderSubtle,
)

@Composable
fun ScreenSealTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ScreenSealColorScheme,
        typography  = Typography,
        content     = content
    )
}