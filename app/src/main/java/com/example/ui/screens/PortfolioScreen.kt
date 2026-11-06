package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PortfolioProjectEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BrandITViewModel

@Composable
fun PortfolioScreen(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    val projects by viewModel.portfolioProjects.collectAsState()
    val campaigns by viewModel.campaigns.collectAsState()

    Box(modifier = modifier.fillMaxSize().background(BrandBackground)) {
        CinematicAmbientBackground()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("portfolio_screen_list"),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Header
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(
                        tag = "Proven Track Record",
                        title = "Client Portfolio",
                        subtitle = "Real client transformations, creative breakthroughs, and campaign results"
                    )
                }
            }

            // Projects List
            items(projects) { project ->
                BrandITCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .testTag("portfolio_card_${project.id}")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = BrandCyan.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = project.category.uppercase(),
                                color = BrandCyan,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Text(text = "Client: ${project.clientName}", color = BrandTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = project.title, color = BrandTextPrimary, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = project.description, color = BrandTextSecondary, fontSize = 12.sp, lineHeight = 17.sp)

                    Spacer(modifier = Modifier.height(12.dp))

                    // Objectives & Approach cards
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = BrandSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "🎯 Objective", color = BrandCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = project.objectives, color = BrandTextPrimary, fontSize = 12.sp, lineHeight = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "💡 Creative Approach", color = BrandMagenta, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = project.creativeApproach, color = BrandTextPrimary, fontSize = 12.sp, lineHeight = 16.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = BrandCardElevated,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrandElectricBlue.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = BrandElectricBlue, modifier = Modifier.size(16.dp))
                            Text(text = "Final Output: ${project.finalOutput}", color = BrandTextPrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            // Campaigns section
            item {
                Spacer(modifier = Modifier.height(20.dp))
                SectionHeader(
                    tag = "Performance & ROAS",
                    title = "Advertisement Campaigns",
                    subtitle = "Strategic distribution and conversion tracking",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            items(campaigns) { campaign ->
                BrandITCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("campaign_card_${campaign.id}")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = BrandOrange.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = campaign.platform.uppercase(),
                                color = BrandOrange,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = if (campaign.status == "Active") BrandCyan.copy(alpha = 0.2f) else BrandCard
                        ) {
                            Text(
                                text = "Status: ${campaign.status}",
                                color = if (campaign.status == "Active") BrandCyan else BrandTextMuted,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = campaign.title, color = BrandTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = campaign.description, color = BrandTextSecondary, fontSize = 12.sp, lineHeight = 16.sp)

                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = BrandSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrandYellow.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = BrandYellow, modifier = Modifier.size(16.dp))
                            Text(text = "Metrics: ${campaign.performanceSummary}", color = BrandYellow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Bottom CTA
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { viewModel.navigateTo(AppScreen.ENQUIRY) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("portfolio_start_project_cta"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandCyan)
                    ) {
                        Icon(Icons.Default.RocketLaunch, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Create Your Success Story With Us", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
