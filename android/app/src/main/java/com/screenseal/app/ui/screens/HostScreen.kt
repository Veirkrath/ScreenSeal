package com.screenseal.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.outlined.Monitor
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.screenseal.app.R
import com.screenseal.app.ui.theme.AccentCyan
import com.screenseal.app.ui.theme.AccentGradientEnd
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
fun HostScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDeep)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Top App Bar ──────────────────────────────────────────────────
        HostTopBar()

        Spacer(modifier = Modifier.height(12.dp))

        // ── Connection status card ───────────────────────────────────────
        ConnectionCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── Main action card ─────────────────────────────────────────────
        MainActionCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── Quick preview card ───────────────────────────────────────────
        QuickPreviewCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ── Top Bar ──────────────────────────────────────────────────────────────────

@Composable
private fun HostTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Avatar placeholder
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(BackgroundSurface),
            contentAlignment = Alignment.Center,
        ) {
            androidx.compose.foundation.Image(
                painter            = painterResource(id = R.drawable.seal_logo),
                contentDescription = "Logo",
                modifier           = Modifier.size(38.dp).clip(CircleShape),
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text       = "ScreenSeal",
                    color      = TextPrimary,
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.width(6.dp))
                ProBadge()
            }
            Text(
                text     = "HOST MONITOR PRINCIPAL",
                color    = TextMuted,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
            )
        }

        // FPS indicator
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(GreenDot)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text     = "60 FPS",
                color    = GreenDot,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // User avatar
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(AccentPurple),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "U", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ProBadge() {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(listOf(AccentPurple, AccentGradientEnd)),
                shape = RoundedCornerShape(4.dp),
            )
            .padding(horizontal = 6.dp, vertical = 2.dp),
    ) {
        Text(
            text       = "PRO",
            color      = Color.White,
            fontSize   = 10.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

// ── Connection Card ───────────────────────────────────────────────────────────

@Composable
private fun ConnectionCard(modifier: Modifier = Modifier) {
    Surface(
        modifier      = modifier,
        shape         = RoundedCornerShape(16.dp),
        color         = BackgroundCard,
        tonalElevation = 0.dp,
    ) {
        Row(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment   = Alignment.CenterVertically,
        ) {
            // App icon placeholder
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(BackgroundSurface),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector        = Icons.Default.Cast,
                    contentDescription = null,
                    tint               = AccentCyan,
                    modifier           = Modifier.size(22.dp),
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "ScreenSeal", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(text = "Conexión directa", color = TextSecondary, fontSize = 12.sp)
            }

            // USB Directo badge
            Row(
                modifier = Modifier
                    .border(1.dp, GreenStable, RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(GreenDot))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "USB Directo", color = GreenStable, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = {}) {
                Icon(
                    imageVector        = Icons.Default.Settings,
                    contentDescription = "Ajustes",
                    tint               = TextSecondary,
                )
            }
        }
    }
}

// ── Main Action Card ──────────────────────────────────────────────────────────

@Composable
private fun MainActionCard(modifier: Modifier = Modifier) {
    Surface(
        modifier       = modifier,
        shape          = RoundedCornerShape(16.dp),
        color          = BackgroundCard,
        tonalElevation = 0.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Origin row
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .background(BackgroundSurface, RoundedCornerShape(10.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector        = Icons.Default.DesktopWindows,
                    contentDescription = null,
                    tint               = TextSecondary,
                    modifier           = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "ORIGEN", color = TextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                    Text(
                        text     = "PC Trabajo (Wi...",
                        color    = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Icon(
                    imageVector        = Icons.Default.Cast,
                    contentDescription = null,
                    tint               = TextSecondary,
                    modifier           = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "100% Estable", color = GreenStable, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "0 dropouts", color = TextSecondary, fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ready state
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(BackgroundSurface),
                    contentAlignment = Alignment.Center,
                ) {
                    androidx.compose.foundation.Image(
                        painter            = painterResource(id = R.drawable.seal_logo),
                        contentDescription = null,
                        modifier           = Modifier.size(52.dp).clip(CircleShape),
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text       = "Todo listo para proyectar",
                        color      = TextPrimary,
                        fontSize   = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text     = "Tu teléfono está vinculado como monitor de ultra-baja latencia.",
                        color    = TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Primary button — gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.horizontalGradient(listOf(AccentCyan, AccentPurple, AccentGradientEnd))
                    )
                    .clickable(onClick = {}),
                contentAlignment = Alignment.Center,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector        = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint               = Color.White,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text       = "Iniciar pantalla extendida",
                        color      = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 16.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Secondary buttons
            Row(
                modifier            = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                OutlinedButton(
                    onClick  = {},
                    modifier = Modifier.weight(1f),
                    shape    = RoundedCornerShape(10.dp),
                    colors   = ButtonDefaults.outlinedButtonColors(contentColor = AccentCyan),
                    border   = BorderStroke(1.dp, AccentCyan),
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Modo Extender", fontSize = 13.sp, fontFamily = FontFamily.Monospace)
                }
                OutlinedButton(
                    onClick  = {},
                    modifier = Modifier.weight(1f),
                    shape    = RoundedCornerShape(10.dp),
                    colors   = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                    border   = BorderStroke(1.dp, BorderSubtle),
                ) {
                    Icon(imageVector = Icons.Default.CopyAll, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Modo Espejo", fontSize = 13.sp, fontFamily = FontFamily.Monospace)
                }
            }
        }
    }
}

// ── Quick Preview Card ────────────────────────────────────────────────────────

@Composable
private fun QuickPreviewCard(modifier: Modifier = Modifier) {
    Surface(
        modifier       = modifier,
        shape          = RoundedCornerShape(16.dp),
        color          = BackgroundCard,
        tonalElevation = 0.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header
            Row(
                modifier          = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector        = Icons.Outlined.Monitor,
                    contentDescription = null,
                    tint               = AccentCyan,
                    modifier           = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text       = "Vista rápida",
                    color      = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize   = 15.sp,
                    modifier   = Modifier.weight(1f),
                )
                Text(text = "1920 × 1080", color = TextMuted, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Preview area - "Waiting for signal"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(BackgroundSurface, RoundedCornerShape(12.dp))
                    .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(BackgroundCard, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector        = Icons.Outlined.Monitor,
                            contentDescription = null,
                            tint               = TextMuted,
                            modifier           = Modifier.size(28.dp),
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Esperando señal", color = TextSecondary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text     = "Pulsa iniciar para transferir el escritorio al instante",
                        color    = TextMuted,
                        fontSize = 12.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Stats row
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                StatItem(icon = Icons.Default.Cast,      label = "60 Hz",   sub = "Fluidez")
                StatItem(icon = Icons.Outlined.Monitor,  label = "1080p",   sub = "Calidad")
                StatItem(icon = Icons.Default.TouchApp,  label = "Táctil",  sub = "Activo")
            }
        }
    }
}

@Composable
private fun StatItem(icon: ImageVector, label: String, sub: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = null, tint = GreenStable, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        Text(text = sub, color = TextMuted, fontSize = 11.sp)
    }
}
