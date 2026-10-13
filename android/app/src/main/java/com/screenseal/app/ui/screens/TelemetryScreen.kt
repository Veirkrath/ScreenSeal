package com.screenseal.app.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
fun TelemetryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDeep)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(text = "Telemetría", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(text = "RENDIMIENTO EN TIEMPO REAL", color = TextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace)

        Spacer(modifier = Modifier.height(20.dp))

        // KPI cards grid
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            KpiCard(modifier = Modifier.weight(1f), icon = Icons.Default.Timer,        label = "Latencia",  value = "4 ms",  sub = "ultra-baja", good = true)
            KpiCard(modifier = Modifier.weight(1f), icon = Icons.Default.BarChart,     label = "FPS",       value = "60",    sub = "estable",    good = true)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            KpiCard(modifier = Modifier.weight(1f), icon = Icons.Default.NetworkCheck, label = "Ancho de banda", value = "320 Mbps", sub = "USB 3.0", good = true)
            KpiCard(modifier = Modifier.weight(1f), icon = Icons.Default.Speed,        label = "Dropouts",  value = "0",     sub = "total",      good = true)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Throughput section
        SectionCard(title = "Rendimiento de canal") {
            TelemetryBar(label = "USB",    value = 0.85f, valueLabel = "320 Mbps", color = AccentCyan)
            Spacer(modifier = Modifier.height(10.dp))
            TelemetryBar(label = "CPU",    value = 0.22f, valueLabel = "22%",      color = GreenStable)
            Spacer(modifier = Modifier.height(10.dp))
            TelemetryBar(label = "GPU enc",value = 0.15f, valueLabel = "15%",      color = GreenStable)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Session info
        SectionCard(title = "Sesión actual") {
            TelemetryInfoRow(label = "Inicio de sesión",  value = "18:10:03")
            TelemetryInfoRow(label = "Tiempo activo",     value = "00:14:27")
            TelemetryInfoRow(label = "Datos transferidos",value = "1.2 GB")
            TelemetryInfoRow(label = "Modo de conexión",  value = "USB Directo")
        }
    }
}

@Composable
private fun KpiCard(modifier: Modifier = Modifier, icon: ImageVector, label: String, value: String, sub: String, good: Boolean) {
    Box(
        modifier = modifier
            .background(BackgroundCard, RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Column {
            Icon(imageVector = icon, contentDescription = null, tint = if (good) GreenStable else AccentCyan, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
            Text(text = label, color = TextSecondary, fontSize = 12.sp)
            Text(text = sub, color = if (good) GreenStable else TextMuted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun TelemetryBar(label: String, value: Float, valueLabel: String, color: Color) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = label, color = TextSecondary, fontSize = 13.sp, modifier = Modifier.width(60.dp))
            LinearProgressIndicator(
                progress        = { value },
                modifier        = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color           = color,
                trackColor      = BorderSubtle,
                strokeCap       = StrokeCap.Round,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = valueLabel, color = color, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
        }
    }
}

@Composable
private fun TelemetryInfoRow(label: String, value: String) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, color = TextSecondary, fontSize = 13.sp, modifier = Modifier.weight(1f))
        Text(text = value, color = TextPrimary,   fontSize = 13.sp, fontWeight = FontWeight.Medium, fontFamily = FontFamily.Monospace)
    }
}
