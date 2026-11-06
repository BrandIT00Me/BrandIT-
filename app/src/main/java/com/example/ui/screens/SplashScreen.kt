package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.CinematicAmbientBackground
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BrandITViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    var startAnimation by remember { mutableStateOf(false) }

    val logoAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "logo_alpha"
    )

    val logoScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.85f,
        animationSpec = tween(durationMillis = 1200, easing = OvershootInterpolator(1.2f)),
        label = "logo_scale"
    )

    val textAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 900, delayMillis = 400, easing = LinearOutSlowInEasing),
        label = "text_alpha"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2200)
        viewModel.navigateTo(AppScreen.HOME)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BrandBackground)
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        CinematicAmbientBackground()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Illuminated BrandIT Emblem
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .scale(logoScale)
                    .alpha(logoAlpha)
                    .clip(RoundedCornerShape(36.dp))
                    .background(BrandSurface)
                    .border(
                        2.dp,
                        Brush.linearGradient(
                            listOf(BrandCyan, BrandElectricBlue, BrandViolet, BrandMagenta)
                        ),
                        RoundedCornerShape(36.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_brandit_logo),
                    contentDescription = "BrandIT Logo",
                    modifier = Modifier.size(105.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Brand Typography
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.alpha(textAlpha)
            ) {
                Text(
                    text = "Brand",
                    color = Color.White,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1).sp
                )
                Text(
                    text = "IT",
                    color = BrandCyan,
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1).sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Official Tagline
            Text(
                text = "YOUR VISION. OUR CREATION.",
                color = BrandMagenta,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(textAlpha)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Introduction line
            Text(
                text = "Welcome to BrandIT – Where Ideas Become Impact.",
                color = BrandTextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(textAlpha)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Elegant Loading Indicator
            CircularProgressIndicator(
                modifier = Modifier
                    .size(28.dp)
                    .alpha(textAlpha),
                color = BrandCyan,
                strokeWidth = 2.5.dp
            )
        }
    }
}

private class OvershootInterpolator(private val tension: Float = 2.0f) : Easing {
    override fun transform(fraction: Float): Float {
        val t = fraction - 1.0f
        return t * t * ((tension + 1) * t + tension) + 1.0f
    }
}
