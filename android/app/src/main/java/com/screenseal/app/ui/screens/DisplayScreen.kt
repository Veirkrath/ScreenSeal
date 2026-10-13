package com.screenseal.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Hd
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.outlined.Monitor
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.screenseal.app.ui.theme.AccentCyan
import com.screenseal.app.ui.theme.BackgroundCard
import com.screenseal.app.ui.theme.BackgroundDeep
import com.screenseal.app.ui.theme.BackgroundSurface
import com.screenseal.app.ui.theme.BorderSubtle
import com.screenseal.app.ui.theme.GreenStable
import com.screenseal.app.ui.theme.TextMuted
import com.screenseal.app.ui.theme.TextPrimary
import com.screenseal.app.ui.theme.TextSecondary

@Composable
fun DisplayScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDeep)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        // Title
        Text(
            text       = "Display",
            color      = TextPrimary,
            fontSize   = 22.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text     = "CONFIGURACIÓN DE PANTALLA",
            color    = TextMuted,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Resolution section
        SectionCard(title = "Resolución & Frecuencia") {
            DisplayOptionRow(
                icon  = Icons.Default.Hd,
                label = "Resolución",
                value = "1920 × 1080",
            )
            DisplayOptionRow(
                icon  = Icons.Default.Speed,
                label = "Frecuencia de refresco",
                value = "60 Hz",
            )
            DisplayOptionRow(
                icon  = Icons.Default.FullscreenExit,
                label = "Modo pantalla",
                value = "Extendida",
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Adjustments section
        SectionCard(title = "Ajustes de imagen") {
            var brightness by remember { mutableFloatStateOf(0.8f) }
            var contrast   by remember { mutableFloatStateOf(0.5f) }

            AdjustmentSlider(
                icon  = Icons.Default.Brightness6,
                label = "Brillo",
                value = brightness,
                onValueChange = { brightness = it },
            )
            AdjustmentSlider(
                icon  = Icons.Default.Contrast,
                label = "Contraste",
                value = contrast,
                onValueChange = { contrast = it },
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Active display preview
        SectionCard(title = "Vista previa") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(BackgroundSurface, RoundedCornerShape(10.dp))
                    .border(1.dp, AccentCyan.copy(alpha = 0.3f), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Outlined.Monitor, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Monitor #1 — Activo", color = AccentCyan, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun DisplayOptionRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(BackgroundSurface, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, color = TextSecondary, modifier = Modifier.weight(1f), fontSize = 14.sp)
        Text(text = value, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp, fontFamily = FontFamily.Monospace)
    }
}

@Composable
private fun AdjustmentSlider(
    icon: ImageVector,
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, color = TextSecondary, fontSize = 13.sp, modifier = Modifier.weight(1f))
            Text(text = "${(value * 100).toInt()}%", color = AccentCyan, fontSize = 13.sp, fontFamily = FontFamily.Monospace)
        }
        Slider(
            value         = value,
            onValueChange = onValueChange,
            colors        = SliderDefaults.colors(
                thumbColor      = AccentCyan,
                activeTrackColor = AccentCyan,
                inactiveTrackColor = BorderSubtle,
            ),
        )
    }
}

@Composable
internal fun SectionCard(title: String, content: @Composable () -> Unit) {
    Surface(
        shape          = RoundedCornerShape(16.dp),
        color          = BackgroundCard,
        tonalElevation = 0.dp,
        modifier       = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text       = title,
                color      = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize   = 14.sp,
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}
