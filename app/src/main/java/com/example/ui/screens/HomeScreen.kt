package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BrandITViewModel

@Composable
fun HomeScreen(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    val publishedScripts by viewModel.publishedScripts.collectAsState()
    val publishedMedia by viewModel.publishedMediaItems.collectAsState()
    val portfolioProjects by viewModel.portfolioProjects.collectAsState()
    val campaigns by viewModel.campaigns.collectAsState()
    val announcements by viewModel.announcements.collectAsState()
    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize().background(BrandBackground)) {
        CinematicAmbientBackground()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("home_screen_list"),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // 1. Announcements Banner
            if (announcements.isNotEmpty()) {
                item {
                    val latest = announcements.first()
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = BrandCard,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrandCyan.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Campaign,
                                contentDescription = "Announcement",
                                tint = BrandCyan,
                                modifier = Modifier.size(20.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = latest.title,
                                    color = BrandTextPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = latest.content,
                                    color = BrandTextSecondary,
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }

            // 2. Hero Section
            item {
                HomeHeroSection(
                    onExploreCreativity = { viewModel.navigateTo(AppScreen.CREATIVE_STUDIO) },
                    onDiscoverServices = { viewModel.navigateTo(AppScreen.SERVICES) },
                    onStartProject = { viewModel.navigateTo(AppScreen.ENQUIRY) }
                )
            }

            // 3. What We Do - Six Core Pillars
            item {
                SectionHeader(
                    tag = "Our Core Formula",
                    title = "What We Do",
                    subtitle = "From a spark of imagination to broadcast-grade digital execution",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )

                val pillars = listOf(
                    PillarItem("THINK", "Creative strategy, audience psychology, and disruptive positioning.", BrandCyan, Icons.Default.Psychology),
                    PillarItem("WRITE", "High-retention scriptwriting, viral hooks, and brand narratives.", BrandMagenta, Icons.Default.EditNote),
                    PillarItem("CREATE", "Original social media content, carousels, infographics, and posters.", BrandViolet, Icons.Default.Palette),
                    PillarItem("PRODUCE", "Cinematic video production, 4K camera workflows, and motion design.", BrandElectricBlue, Icons.Default.MovieFilter),
                    PillarItem("PROMOTE", "Multi-platform ad campaigns, YouTube SEO, and growth engineering.", BrandOrange, Icons.Default.TrendingUp),
                    PillarItem("GROW", "Measurable brand equity, authority, and high-converting audiences.", BrandYellow, Icons.Default.RocketLaunch)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    pillars.chunked(2).forEach { rowPillars ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            rowPillars.forEach { pillar ->
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(1.dp, pillar.accentColor.copy(alpha = 0.35f), RoundedCornerShape(16.dp)),
                                    color = BrandCard
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Box(
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(pillar.accentColor.copy(alpha = 0.15f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(pillar.icon, contentDescription = null, tint = pillar.accentColor, modifier = Modifier.size(18.dp))
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = pillar.title,
                                            color = BrandTextPrimary,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            letterSpacing = 0.5.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = pillar.desc,
                                            color = BrandTextSecondary,
                                            fontSize = 11.sp,
                                            lineHeight = 15.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 4. Scriptwriting Studio Spotlight
            item {
                Spacer(modifier = Modifier.height(20.dp))
                SectionHeader(
                    tag = "Flagship Discipline",
                    title = "Scriptwriting Studio",
                    subtitle = "Every great video begins with a great idea. Every great idea deserves a great script.",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                if (publishedScripts.isNotEmpty()) {
                    val featuredScript = publishedScripts.firstOrNull { it.isFeatured } ?: publishedScripts.first()
                    BrandITCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .testTag("featured_script_card"),
                        borderGradient = BrandGradientHero,
                        onClick = { viewModel.selectScript(featuredScript) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = BrandMagenta.copy(alpha = 0.18f)
                            ) {
                                Text(
                                    text = featuredScript.category.uppercase(),
                                    color = BrandMagenta,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Text(text = "⭐ FEATURED SCRIPT", color = BrandYellow, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = featuredScript.title,
                            color = BrandTextPrimary,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "\"${featuredScript.hook}\"",
                            color = BrandCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Read Screenplay Excerpt →",
                                color = BrandElectricBlue,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Button(
                                onClick = { viewModel.navigateTo(AppScreen.ENQUIRY) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Order Script", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // 5. Featured Videos & Media Studio
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SectionHeader(
                        tag = "Streaming & Content",
                        title = "BrandIT Media Studio",
                        subtitle = "Official YouTube & Instagram productions",
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { viewModel.navigateTo(AppScreen.MEDIA_STUDIO) }) {
                        Text("View All", color = BrandCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(publishedMedia) { media ->
                        Surface(
                            modifier = Modifier
                                .width(260.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .border(1.dp, BrandBorder, RoundedCornerShape(16.dp))
                                .clickable { viewModel.selectMediaItem(media) },
                            color = BrandCard
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp)
                                        .background(BrandSurface),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (media.thumbnailUrl.isNotBlank()) {
                                        AsyncImage(
                                            model = media.thumbnailUrl,
                                            contentDescription = media.title,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Black.copy(alpha = 0.35f))
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(42.dp)
                                            .clip(CircleShape)
                                            .background(BrandCyan.copy(alpha = 0.9f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.Black, modifier = Modifier.size(24.dp))
                                    }
                                    // Duration tag
                                    Surface(
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(8.dp),
                                        shape = RoundedCornerShape(4.dp),
                                        color = Color.Black.copy(alpha = 0.8f)
                                    ) {
                                        Text(
                                            text = media.duration,
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = media.platform.uppercase(),
                                        color = if (media.platform == "YouTube") Color(0xFFFF4444) else Color(0xFFFF3E7D),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = media.title,
                                        color = BrandTextPrimary,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "👁 ${media.viewsCount} • ${media.category}",
                                        color = BrandTextMuted,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 6. Portfolio Showcase
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SectionHeader(
                        tag = "Client Transformations",
                        title = "Featured Projects",
                        subtitle = "High-impact creative & business case studies",
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { viewModel.navigateTo(AppScreen.PORTFOLIO) }) {
                        Text("All Work", color = BrandCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    portfolioProjects.take(2).forEach { project ->
                        BrandITCard(
                            onClick = { viewModel.selectPortfolioProject(project) }
                        ) {
                            Text(
                                text = project.category.uppercase(),
                                color = BrandCyan,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = project.title,
                                color = BrandTextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = project.description,
                                color = BrandTextSecondary,
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = BrandSurface
                            ) {
                                Text(
                                    text = "🎯 Objective: ${project.objectives}",
                                    color = BrandTextPrimary,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 7. Why Choose BrandIT
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader(
                    tag = "The BrandIT Standard",
                    title = "Why Choose BrandIT?",
                    subtitle = "We don't just create content. We build brands and help businesses grow.",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                val benefits = listOf(
                    "Zero Generic Fluff – Every script and video is tailored with deep psychological hooks.",
                    "Full-Cycle Creative Pipeline – From initial napkin concept to 4K broadcast-ready files.",
                    "Engineered for Growth – Optimized for YouTube algorithms, Instagram reels, and paid ROAS.",
                    "Direct Creative Partnership – Direct access to elite scriptwriters and video directors."
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    benefits.forEach { benefit ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = BrandCard,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = BrandCyan, modifier = Modifier.size(18.dp))
                                Text(text = benefit, color = BrandTextPrimary, fontSize = 12.sp, lineHeight = 17.sp)
                            }
                        }
                    }
                }
            }

            // 8. Work Together CTA Banner
            item {
                Spacer(modifier = Modifier.height(28.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, BrandGradientHero, RoundedCornerShape(20.dp)),
                    color = BrandCardElevated
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BrandITLogoBadge(size = 48.dp, showText = true, showTagline = true)
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "LET'S WORK TOGETHER",
                            color = BrandTextPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Have an idea, a brand to build, or a business to grow? Let's turn your vision into an unforgettable digital reality.",
                            color = BrandTextSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 17.sp
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Button(
                            onClick = { viewModel.navigateTo(AppScreen.ENQUIRY) },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("home_start_project_cta")
                        ) {
                            Icon(Icons.Default.RocketLaunch, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Start Your Project Today", color = Color.Black, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
                        }
                    }
                }
            }

            // 9. Official Social Links & Footer
            item {
                Spacer(modifier = Modifier.height(28.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "CONNECT WITH BRANDIT",
                        color = BrandTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@BrandIT_for"))
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF0000).copy(alpha = 0.5f))
                        ) {
                            Text("YouTube @BrandIT_for", color = Color(0xFFFF4444), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/brandit_me?psln=MXhrN2F0aGxkMWlncw=="))
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE1306C).copy(alpha = 0.5f))
                        ) {
                            Text("Instagram @brandit_me", color = Color(0xFFFF3E7D), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Official Admin: brandit00me@gmail.com • +91 9580289877",
                        color = BrandTextMuted,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "© 2026 BrandIT. All Rights Reserved. YOUR VISION. OUR CREATION.",
                        color = BrandTextMuted,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun HomeHeroSection(
    onExploreCreativity: () -> Unit,
    onDiscoverServices: () -> Unit,
    onStartProject: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Animated 3D-styled BrandIT Emblem Showcase
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(BrandCardElevated)
                .border(1.dp, BrandBorder, RoundedCornerShape(24.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Central illuminated logo
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(BrandSurface)
                        .border(
                            2.dp,
                            Brush.linearGradient(listOf(BrandCyan, BrandElectricBlue, BrandViolet, BrandMagenta)),
                            RoundedCornerShape(28.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_brandit_logo),
                        contentDescription = "BrandIT Logo",
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Tagline Pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = BrandMagenta.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BrandMagenta.copy(alpha = 0.6f))
                ) {
                    Text(
                        text = "YOUR VISION. OUR CREATION.",
                        color = BrandMagenta,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Supporting Heading
                Text(
                    text = "WE THINK. WE WRITE. WE CREATE. WE PRODUCE. WE PROMOTE. WE GROW.",
                    color = BrandTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Official Description
                Text(
                    text = "Welcome to BrandIT, your creative partner for scriptwriting, content creation, video production, digital marketing, advertisement management, social media expertise and business growth solutions.",
                    color = BrandTextSecondary,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Three Main CTAs
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onStartProject,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("hero_cta_start_project"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandCyan)
                    ) {
                        Icon(Icons.Default.RocketLaunch, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Start Your Project", color = Color.Black, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = onExploreCreativity,
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("hero_cta_explore_creativity"),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrandMagenta)
                        ) {
                            Text("Explore Creativity", color = BrandMagenta, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }

                        OutlinedButton(
                            onClick = onDiscoverServices,
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("hero_cta_discover_services"),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrandElectricBlue)
                        ) {
                            Text("Discover Services", color = BrandElectricBlue, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

private data class PillarItem(
    val title: String,
    val desc: String,
    val accentColor: Color,
    val icon: ImageVector
)
