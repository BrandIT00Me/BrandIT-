package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BrandITColorScheme = darkColorScheme(
    primary = BrandCyan,
    onPrimary = Color(0xFF00141A),
    primaryContainer = Color(0xFF003644),
    onPrimaryContainer = Color(0xFFB3F5FF),

    secondary = BrandElectricBlue,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF00224F),
    onSecondaryContainer = Color(0xFFD4E3FF),

    tertiary = BrandMagenta,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF5B002B),
    onTertiaryContainer = Color(0xFFFFD9E2),

    background = BrandBackground,
    onBackground = BrandTextPrimary,

    surface = BrandSurface,
    onSurface = BrandTextPrimary,
    surfaceVariant = BrandCard,
    onSurfaceVariant = BrandTextSecondary,

    outline = BrandBorder,
    outlineVariant = Color(0xFF1E1E26)
)

@Composable
fun BrandITTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BrandITColorScheme,
        typography = Typography,
        content = content
    )
}
