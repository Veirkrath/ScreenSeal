package com.screenseal.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.screenseal.app.ui.navigation.ScreenSealNavGraph
import com.screenseal.app.ui.theme.ScreenSealTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScreenSealTheme {
                ScreenSealNavGraph()
            }
        }
    }
}