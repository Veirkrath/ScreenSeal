package com.screenseal.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.LinkOff
import androidx.compose.material.icons.filled.Usb
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.screenseal.app.ui.theme.AccentCyan
import com.screenseal.app.ui.theme.AccentPurple
import com.screenseal.app.ui.theme.BackgroundCard
import com.screenseal.app.ui.theme.BackgroundDeep
import com.screenseal.app.ui.theme.BackgroundSurface
import com.screenseal.app.ui.theme.BorderSubtle
import com.screenseal.app.ui.theme.GreenDot
import com.screenseal.app.ui.theme.GreenStable
import com.screenseal.app.ui.theme.TextMuted
import com.screenseal.app.ui.theme.TextPrimary
import com.screenseal.app.ui.theme.TextSecondary

@Composable
fun ClientScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDeep)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(text = "Cliente", color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(text = "DISPOSITIVOS CONECTADOS", color = TextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace)

        Spacer(modifier = Modifier.height(20.dp))

        // Active connection
        SectionCard(title = "Conexión activa") {
            ClientDeviceRow(
                name        = "PC Trabajo",
                subtitle    = "USB Directo · 192.168.x.x",
                isConnected = true,
                isUsb       = true,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Known devices
        SectionCard(title = "Dispositivos conocidos") {
            ClientDeviceRow(
                name        = "Laptop Casa",
                subtitle    = "Wi-Fi · Última conexión: hoy",
                isConnected = false,
                isUsb       = false,
            )
            ClientDeviceRow(
                name        = "PC Oficina",
                subtitle    = "USB · Última conexión: ayer",
                isConnected = false,
                isUsb       = true,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add new device
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            shape   = RoundedCornerShape(12.dp),
            colors  = ButtonDefaults.buttonColors(containerColor = BackgroundSurface, contentColor = AccentCyan),
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Agregar dispositivo", fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun ClientDeviceRow(name: String, subtitle: String, isConnected: Boolean, isUsb: Boolean) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(BackgroundSurface, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector        = Icons.Default.Computer,
                contentDescription = null,
                tint               = if (isConnected) AccentCyan else TextMuted,
                modifier           = Modifier.size(22.dp),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isUsb) Icons.Default.Usb else Icons.Default.Wifi,
                    contentDescription = null,
                    tint     = TextMuted,
                    modifier = Modifier.size(12.dp),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = subtitle, color = TextSecondary, fontSize = 12.sp)
            }
        }

        if (isConnected) {
            Row(
                modifier = Modifier
                    .border(1.dp, GreenStable, RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(GreenDot))
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Activo", color = GreenStable, fontSize = 12.sp)
            }
        } else {
            Icon(imageVector = Icons.Default.LinkOff, contentDescription = null, tint = TextMuted, modifier = Modifier.size(20.dp))
        }
    }
}
