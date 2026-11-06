package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

@Composable
fun BrandITLogoBadge(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    showText: Boolean = true,
    showTagline: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(size * 0.28f))
                .background(BrandSurface)
                .border(1.dp, BrandCyan.copy(alpha = 0.5f), RoundedCornerShape(size * 0.28f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_brandit_logo),
                contentDescription = "BrandIT Logo",
                modifier = Modifier
                    .size(size * 0.82f)
                    .padding(2.dp)
            )
        }

        if (showText) {
            Column(verticalArrangement = Arrangement.Center) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Brand",
                        color = Color.White,
                        fontSize = (size.value * 0.44f).sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "IT",
                        color = BrandCyan,
                        fontSize = (size.value * 0.44f).sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-0.5).sp
                    )
                }
                if (showTagline) {
                    Text(
                        text = "YOUR VISION. OUR CREATION.",
                        color = BrandMagenta,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CinematicAmbientBackground(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ambient_glow")
    val animOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Top cyan ambient glow
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(BrandCyan.copy(alpha = 0.08f), Color.Transparent),
                center = Offset(width * 0.2f + animOffset * 0.1f, height * 0.15f),
                radius = 340f
            )
        )

        // Center magenta ambient pulse
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(BrandMagenta.copy(alpha = 0.07f), Color.Transparent),
                center = Offset(width * 0.85f - animOffset * 0.1f, height * 0.45f),
                radius = 380f
            )
        )

        // Bottom violet ambient accent
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(BrandViolet.copy(alpha = 0.09f), Color.Transparent),
                center = Offset(width * 0.5f, height * 0.85f),
                radius = 420f
            )
        )
    }
}

@Composable
fun SectionHeader(
    tag: String,
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(BrandCyan)
            )
            Text(
                text = tag.uppercase(),
                color = BrandCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            color = BrandTextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 28.sp
        )
        if (!subtitle.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                color = BrandTextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun FilterChipBadge(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .testTag("filter_chip_${label.lowercase().replace(" ", "_")}"),
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) BrandCyan.copy(alpha = 0.18f) else BrandCard,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) BrandCyan else BrandBorder
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            icon?.invoke()
            Text(
                text = label,
                color = if (isSelected) BrandCyan else BrandTextSecondary,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

@Composable
fun BrandITCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    borderGradient: Brush? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (borderGradient != null) {
                    Modifier.border(1.dp, borderGradient, RoundedCornerShape(16.dp))
                } else {
                    Modifier.border(1.dp, BrandBorder, RoundedCornerShape(16.dp))
                }
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else Modifier
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BrandCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            content = content
        )
    }
}
