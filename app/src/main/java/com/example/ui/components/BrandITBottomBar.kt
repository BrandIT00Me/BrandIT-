package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BrandITViewModel

data class NavItem(
    val screen: AppScreen,
    val label: String,
    val icon: ImageVector,
    val activeIcon: ImageVector,
    val isPrimary: Boolean = false
)

@Composable
fun BrandITBottomBar(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    val navItems = listOf(
        NavItem(AppScreen.HOME, "Home", Icons.Outlined.Home, Icons.Filled.Home),
        NavItem(AppScreen.SERVICES, "Services", Icons.Outlined.Widgets, Icons.Filled.Widgets),
        NavItem(AppScreen.CREATIVE_STUDIO, "Creative", Icons.Outlined.AutoAwesome, Icons.Filled.AutoAwesome),
        NavItem(AppScreen.MEDIA_STUDIO, "Media", Icons.Outlined.PlayCircle, Icons.Filled.PlayCircle),
        NavItem(AppScreen.PORTFOLIO, "Projects", Icons.Outlined.Work, Icons.Filled.Work),
        NavItem(AppScreen.ENQUIRY, "Enquire", Icons.Outlined.Send, Icons.Filled.Send, isPrimary = true)
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = BrandSurface.copy(alpha = 0.96f),
        border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { item ->
                val isSelected = currentScreen == item.screen

                if (item.isPrimary) {
                    // Highlighted primary action button
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                if (isSelected) BrandGradientHero else BrandGradientAccent
                            )
                            .clickable { viewModel.navigateTo(item.screen) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .testTag("nav_${item.label.lowercase()}"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = item.label,
                                color = Color.Black,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { viewModel.navigateTo(item.screen) }
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                            .testTag("nav_${item.label.lowercase()}"),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .height(26.dp)
                                .clip(RoundedCornerShape(13.dp))
                                .background(
                                    if (isSelected) BrandCyan.copy(alpha = 0.16f) else Color.Transparent
                                )
                                .padding(horizontal = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.activeIcon else item.icon,
                                contentDescription = item.label,
                                tint = if (isSelected) BrandCyan else BrandTextMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.label,
                            color = if (isSelected) BrandCyan else BrandTextMuted,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
