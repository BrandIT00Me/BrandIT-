package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// BrandIT Official Design System Colors
val BrandBackground = Color(0xFF050505)
val BrandSurface = Color(0xFF101014)
val BrandCard = Color(0xFF17171D)
val BrandCardElevated = Color(0xFF1F1F26)
val BrandBorder = Color(0xFF262632)

// Vibrant Accent Colors
val BrandCyan = Color(0xFF00F0FF)
val BrandElectricBlue = Color(0xFF0070F3)
val BrandMagenta = Color(0xFFFF007F)
val BrandViolet = Color(0xFF8B5CF6)
val BrandOrange = Color(0xFFFF5E00)
val BrandYellow = Color(0xFFFFD600)

// Text Colors
val BrandTextPrimary = Color(0xFFFFFFFF)
val BrandTextSecondary = Color(0xFFA5A5B5)
val BrandTextMuted = Color(0xFF6B6B7C)

// Gradient Brushes
val BrandGradientHero = Brush.horizontalGradient(
    listOf(BrandCyan, BrandElectricBlue, BrandViolet, BrandMagenta)
)

val BrandGradientAccent = Brush.linearGradient(
    listOf(BrandCyan, BrandMagenta)
)

val BrandGradientCard = Brush.verticalGradient(
    listOf(Color(0xFF1C1C24), Color(0xFF14141A))
)

val BrandGradientGold = Brush.linearGradient(
    listOf(BrandYellow, BrandOrange)
)
