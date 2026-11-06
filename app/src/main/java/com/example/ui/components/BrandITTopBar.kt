package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BrandITViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandITTopBar(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsState()
    val context = LocalContext.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = BrandBackground.copy(alpha = 0.95f),
        border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Brand Logo & Mark
            BrandITLogoBadge(
                size = 38.dp,
                showText = true,
                showTagline = false,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .testTag("topbar_brandit_logo")
            )

            // Actions: Quick Enquiry / WhatsApp / Admin
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // WhatsApp quick action
                IconButton(
                    onClick = {
                        val uri = Uri.parse("https://wa.me/919580289877?text=Hello%20BrandIT,%20I%20would%20like%20to%20discuss%20a%20creative%20project.")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(BrandCard)
                        .border(1.dp, BrandBorder, CircleShape)
                        .testTag("topbar_whatsapp_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = "WhatsApp BrandIT",
                        tint = Color(0xFF25D366),
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Call direct
                IconButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+919580289877"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(BrandCard)
                        .border(1.dp, BrandBorder, CircleShape)
                        .testTag("topbar_call_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call BrandIT",
                        tint = BrandCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Admin Dashboard or Login
                IconButton(
                    onClick = {
                        if (isAdminLoggedIn) {
                            viewModel.navigateTo(AppScreen.ADMIN_DASHBOARD)
                        } else {
                            viewModel.navigateTo(AppScreen.ADMIN_LOGIN)
                        }
                    },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(if (isAdminLoggedIn) BrandMagenta.copy(alpha = 0.2f) else BrandCard)
                        .border(1.dp, if (isAdminLoggedIn) BrandMagenta else BrandBorder, CircleShape)
                        .testTag("topbar_admin_button")
                ) {
                    Icon(
                        imageVector = if (isAdminLoggedIn) Icons.Default.AdminPanelSettings else Icons.Outlined.Lock,
                        contentDescription = "Admin Portal",
                        tint = if (isAdminLoggedIn) BrandMagenta else BrandTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
