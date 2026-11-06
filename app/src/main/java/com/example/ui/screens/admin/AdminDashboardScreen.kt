package com.example.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.screens.formTextFieldColors
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BrandITViewModel

@Composable
fun AdminDashboardScreen(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    val activeTab by viewModel.adminActiveTab.collectAsState()
    val allEnquiries by viewModel.allEnquiries.collectAsState()
    val allScripts by viewModel.allScripts.collectAsState()
    val allContentSamples by viewModel.allContentSamples.collectAsState()
    val allMediaItems by viewModel.allMediaItems.collectAsState()
    val portfolioProjects by viewModel.portfolioProjects.collectAsState()
    val campaigns by viewModel.campaigns.collectAsState()
    val announcements by viewModel.announcements.collectAsState()

    val dashboardTabs = listOf(
        "Overview",
        "Enquiries",
        "Scripts",
        "Content",
        "Videos & Media",
        "Portfolio",
        "Campaigns",
        "Social Connect",
        "Settings"
    )

    Box(modifier = modifier.fillMaxSize().background(BrandBackground)) {
        CinematicAmbientBackground()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("admin_dashboard_screen"),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Dashboard Top Bar
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = BrandSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(BrandMagenta.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = BrandMagenta, modifier = Modifier.size(20.dp))
                            }
                            Column {
                                Text("BrandIT Control Center", color = BrandTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text("brandit00me@gmail.com", color = BrandCyan, fontSize = 11.sp)
                            }
                        }

                        IconButton(
                            onClick = { viewModel.logoutAdmin() },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(BrandCard)
                                .border(1.dp, BrandBorder, CircleShape)
                        ) {
                            Icon(Icons.Default.Logout, contentDescription = "Logout", tint = Color(0xFFFF5555), modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }

            // Dashboard Horizontal Tabs
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(dashboardTabs.indices.toList()) { index ->
                        FilterChipBadge(
                            label = dashboardTabs[index],
                            isSelected = activeTab == index,
                            onClick = { viewModel.setAdminActiveTab(index) }
                        )
                    }
                }
            }

            // Tab 0: OVERVIEW
            if (activeTab == 0) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "METRICS & ACTIVITY OVERVIEW",
                            color = BrandCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        val stats = listOf(
                            Triple("Total Enquiries", allEnquiries.size.toString(), BrandCyan),
                            Triple("Published Videos", allMediaItems.size.toString(), BrandMagenta),
                            Triple("Script Repertoire", allScripts.size.toString(), BrandViolet),
                            Triple("Content Samples", allContentSamples.size.toString(), BrandElectricBlue),
                            Triple("Portfolio Projects", portfolioProjects.size.toString(), BrandYellow),
                            Triple("Active Campaigns", campaigns.filter { it.status == "Active" }.size.toString(), BrandOrange)
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            stats.chunked(2).forEach { rowStats ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    rowStats.forEach { stat ->
                                        Surface(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(14.dp))
                                                .border(1.dp, stat.third.copy(alpha = 0.4f), RoundedCornerShape(14.dp)),
                                            color = BrandCard
                                        ) {
                                            Column(modifier = Modifier.padding(14.dp)) {
                                                Text(text = stat.first, color = BrandTextSecondary, fontSize = 11.sp)
                                                Spacer(modifier = Modifier.height(6.dp))
                                                Text(text = stat.second, color = stat.third, fontSize = 24.sp, fontWeight = FontWeight.Black)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Recent Activities Log
                        Text("RECENT CLIENT ENQUIRIES", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))

                        if (allEnquiries.isEmpty()) {
                            Text("No enquiries yet.", color = BrandTextMuted, fontSize = 12.sp)
                        } else {
                            allEnquiries.take(3).forEach { enq ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    color = BrandCard,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(text = "${enq.customerName} (${enq.companyName})", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                            Text(text = "${enq.serviceRequired} • Ref: ${enq.referenceId}", color = BrandTextSecondary, fontSize = 11.sp)
                                        }
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = BrandCyan.copy(alpha = 0.15f)
                                        ) {
                                            Text(text = enq.status, color = BrandCyan, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Tab 1: ENQUIRIES MANAGEMENT
            if (activeTab == 1) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("CLIENT PROJECT BRIEFS", color = BrandCyan, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text("Count: ${allEnquiries.size}", color = BrandTextMuted, fontSize = 12.sp)
                        }
                    }
                }

                if (allEnquiries.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                            Text("No customer briefs received yet.", color = BrandTextMuted)
                        }
                    }
                } else {
                    items(allEnquiries) { enquiry ->
                        BrandITCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = enquiry.referenceId, color = BrandMagenta, fontSize = 12.sp, fontWeight = FontWeight.Black)
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = BrandCyan.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = enquiry.status,
                                        color = BrandCyan,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "${enquiry.customerName} • ${enquiry.companyName}", color = BrandTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Email: ${enquiry.email} | Mobile: ${enquiry.mobile}", color = BrandTextSecondary, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Service: ${enquiry.serviceRequired} | Target: ${enquiry.preferredPlatform}", color = BrandCyan, fontSize = 12.sp)
                            Text(text = "Budget: ${enquiry.budgetRange} | Timeline: ${enquiry.timeline}", color = BrandTextMuted, fontSize = 11.sp)

                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = BrandSurface,
                                border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder)
                            ) {
                                Text(
                                    text = "Brief: \"${enquiry.projectDescription}\"",
                                    color = BrandTextPrimary,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Status Update Controls
                            Text("Update Workflow Status:", color = BrandTextMuted, fontSize = 11.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            val statuses = listOf("New", "Contacted", "Discussion", "In Progress", "Completed", "Archived")
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(statuses) { st ->
                                    Surface(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .clickable { viewModel.updateEnquiryStatus(enquiry, st) },
                                        color = if (enquiry.status == st) BrandCyan.copy(alpha = 0.25f) else BrandSurface,
                                        border = androidx.compose.foundation.BorderStroke(1.dp, if (enquiry.status == st) BrandCyan else BrandBorder)
                                    ) {
                                        Text(
                                            text = st,
                                            color = if (enquiry.status == st) BrandCyan else BrandTextSecondary,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Tab 2: SCRIPTS MANAGEMENT
            if (activeTab == 2) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("SCRIPTWRITING REPOSITORY", color = BrandCyan, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Button(
                                onClick = {
                                    val newScript = ScriptItemEntity(
                                        title = "New Viral Hook Script",
                                        category = "YouTube Video Scriptwriting",
                                        description = "Brand new bespoke script draft.",
                                        hook = "The truth about audience retention in 2026...",
                                        excerpt = "[ACT 1: START]\nNarrator speaks with intense pacing.",
                                        fullScript = "[ACT 1]\nNarrator: Stop scrolling...",
                                        datePublished = "Nov 2026",
                                        isPublished = true
                                    )
                                    viewModel.saveScript(newScript)
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("New Script", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                items(allScripts) { script ->
                    BrandITCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = script.title, color = BrandTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Switch(
                                checked = script.isPublished,
                                onCheckedChange = { viewModel.toggleScriptPublish(script) },
                                colors = SwitchDefaults.colors(checkedThumbColor = BrandCyan)
                            )
                        }
                        Text(text = "${script.category} • Lang: ${script.language}", color = BrandCyan, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = script.description, color = BrandTextSecondary, fontSize = 12.sp)

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = { viewModel.deleteScript(script) }, modifier = Modifier.size(32.dp)) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFF5555), modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }

            // Tab 3: CONTENT MANAGEMENT
            if (activeTab == 3) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("CONTENT CREATION MANAGEMENT", color = BrandMagenta, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Button(
                                onClick = {
                                    val newContent = ContentSampleEntity(
                                        title = "Strategic Reel Format #7",
                                        category = "Reels and Shorts",
                                        description = "Engineered for high comment engagement.",
                                        platform = "Instagram",
                                        mediaType = "Reel",
                                        mediaUrl = "https://www.instagram.com/brandit_me",
                                        caption = "Never post without a clear curiosity gap.",
                                        isPublished = true
                                    )
                                    viewModel.saveContentSample(newContent)
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandMagenta),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add Content", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                items(allContentSamples) { sample ->
                    BrandITCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        Text(text = sample.title, color = BrandTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text(text = "${sample.platform} • ${sample.mediaType} • ${sample.category}", color = BrandMagenta, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = sample.description, color = BrandTextSecondary, fontSize = 12.sp)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            IconButton(onClick = { viewModel.deleteContentSample(sample) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFF5555), modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }

            // Tab 4: VIDEO & MEDIA STUDIO MANAGEMENT
            if (activeTab == 4) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("MEDIA STUDIO MANAGEMENT", color = BrandCyan, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Button(
                                onClick = {
                                    val newItem = MediaStudioItemEntity(
                                        title = "BrandIT Production Highlight",
                                        platform = "YouTube",
                                        videoUrl = "https://www.youtube.com/@BrandIT_for",
                                        thumbnailUrl = "https://images.unsplash.com/photo-1574717024653-61fd2cf4d44d?w=800&q=80",
                                        duration = "03:15",
                                        viewsCount = "10.4K",
                                        publishedDate = "2026-11-06",
                                        category = "Cinematic Edit",
                                        description = "Newly published video release.",
                                        isPublished = true
                                    )
                                    viewModel.saveMediaItem(newItem)
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add Video Link", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                items(allMediaItems) { media ->
                    BrandITCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = media.title, color = BrandTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text(text = media.platform, color = BrandCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "URL: ${media.videoUrl}", color = BrandTextMuted, fontSize = 11.sp, maxLines = 1)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Duration: ${media.duration} • Views: ${media.viewsCount}", color = BrandTextSecondary, fontSize = 11.sp)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            IconButton(onClick = { viewModel.deleteMediaItem(media) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFF5555), modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }

            // Tab 5: PORTFOLIO MANAGEMENT
            if (activeTab == 5) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("PORTFOLIO CASE STUDIES", color = BrandElectricBlue, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
                items(portfolioProjects) { proj ->
                    BrandITCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        Text(text = proj.title, color = BrandTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Client: ${proj.clientName} • ${proj.category}", color = BrandElectricBlue, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = proj.description, color = BrandTextSecondary, fontSize = 12.sp)
                    }
                }
            }

            // Tab 6: CAMPAIGNS MANAGEMENT
            if (activeTab == 6) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("ADVERTISING CAMPAIGNS", color = BrandOrange, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
                items(campaigns) { camp ->
                    BrandITCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = camp.title, color = BrandTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(text = camp.status, color = BrandOrange, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Text(text = "Platform: ${camp.platform} • ${camp.category}", color = BrandTextMuted, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "ROAS / Metrics: ${camp.performanceSummary}", color = BrandYellow, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Tab 7: SOCIAL MEDIA INTEGRATION
            if (activeTab == 7) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("SOCIAL MEDIA SYNC & STATUS", color = BrandCyan, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(12.dp))

                        val socialIntegrations = listOf(
                            Triple("YouTube Data API v3", "Channel: @BrandIT_for • Synchronized", BrandCyan),
                            Triple("Meta Instagram Graph API", "Account: @brandit_me • Connected", Color(0xFFE1306C)),
                            Triple("Google Drive Cloud Studio", "12 Production Folders • OAuth Active", BrandYellow),
                            Triple("MSG91 SMS Gateway", "Recovery Endpoint: +91 9580289877", BrandElectricBlue)
                        )

                        socialIntegrations.forEach { item ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = BrandCard,
                                border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder)
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = item.first, color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                        Text(text = item.second, color = BrandTextSecondary, fontSize = 11.sp)
                                    }
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = item.third, modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Tab 8: SETTINGS & ADMIN PROFILE
            if (activeTab == 8) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("APPLICATION SETTINGS", color = BrandCyan, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(12.dp))

                        BrandITCard {
                            Text("Official Brand Details", color = BrandTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Brand Name: BrandIT", color = BrandTextSecondary, fontSize = 12.sp)
                            Text("Official Tagline: YOUR VISION. OUR CREATION.", color = BrandMagenta, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("Administrator: brandit00me@gmail.com", color = BrandTextSecondary, fontSize = 12.sp)
                            Text("Recovery Mobile: +91 9580289877", color = BrandTextSecondary, fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { viewModel.logoutAdmin() },
                            modifier = Modifier.fillMaxWidth().height(46.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCC2222))
                        ) {
                            Icon(Icons.Default.Logout, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Log Out Administrator", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
