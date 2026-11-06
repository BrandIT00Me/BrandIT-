package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ContentSampleEntity
import com.example.data.model.ScriptItemEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BrandITViewModel

@Composable
fun CreativeStudioScreen(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    val activeTab by viewModel.creativeStudioTab.collectAsState()
    val publishedScripts by viewModel.publishedScripts.collectAsState()
    val contentSamples by viewModel.publishedContentSamples.collectAsState()
    val context = LocalContext.current

    val tabs = listOf(
        "Scriptwriting Studio",
        "Content Creation",
        "The Creative Mind",
        "Behind the Scenes"
    )

    Box(modifier = modifier.fillMaxSize().background(BrandBackground)) {
        CinematicAmbientBackground()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("creative_studio_screen_list"),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Header
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(
                        tag = "Ideation & Storytelling",
                        title = "Creative Studio",
                        subtitle = "Where research, psychology, and narrative craft meet production"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Sub Tabs
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(tabs.indices.toList()) { index ->
                            FilterChipBadge(
                                label = tabs[index],
                                isSelected = activeTab == index,
                                onClick = { viewModel.setCreativeStudioTab(index) }
                            )
                        }
                    }
                }
            }

            // Tab 0: Scriptwriting Studio
            if (activeTab == 0) {
                item {
                    // Philosophy Banner
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, BrandGradientAccent, RoundedCornerShape(16.dp)),
                        color = BrandCardElevated
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "THE SCRIPTWRITING PHILOSOPHY",
                                color = BrandCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "EVERY GREAT VIDEO BEGINS WITH A GREAT IDEA.\nEVERY GREAT IDEA DESERVES A GREAT SCRIPT.",
                                color = BrandTextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                lineHeight = 22.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "We engineer audience retention through psychological story arcs, contrarian hooks, pacing modulation, and irresistible conversion triggers.",
                                color = BrandTextSecondary,
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }

                // Scriptwriting Methodology Framework
                item {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "THE BRANDIT SCRIPTWRITING BLUEPRINT",
                        color = BrandTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val steps = listOf(
                        Pair("01. Audience Forensics", "Uncovering pain points, search intent, and cognitive biases before typing a word."),
                        Pair("02. The 3-Second Pattern Interrupt", "Crafting arresting visual and audio hooks that prevent audience drop-off."),
                        Pair("03. Retention Architecture", "Planting open curiosity loops throughout the script to maximize completion rate."),
                        Pair("04. Emotional Call-to-Action", "Translating attention into concrete business outcomes and community action.")
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        steps.forEach { step ->
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                color = BrandCard,
                                border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text(text = step.first, color = BrandCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    Text(text = step.second, color = BrandTextSecondary, fontSize = 11.sp, lineHeight = 15.sp)
                                }
                            }
                        }
                    }
                }

                // Published Scripts Showcase
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "PUBLISHED SCRIPT REPERTOIRE",
                        color = BrandTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }

                items(publishedScripts) { script ->
                    BrandITCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .testTag("script_item_${script.id}"),
                        onClick = { viewModel.selectScript(script) }
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
                                    text = script.category.uppercase(),
                                    color = BrandCyan,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(text = "Lang: ${script.language}", color = BrandTextMuted, fontSize = 11.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = script.title, color = BrandTextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = script.description,
                            color = BrandTextSecondary,
                            fontSize = 12.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        if (script.hook.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = BrandSurface,
                                border = androidx.compose.foundation.BorderStroke(1.dp, BrandMagenta.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "Hook: \"${script.hook}\"",
                                    color = BrandMagenta,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Read Full Script →", color = BrandElectricBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Button(
                                onClick = { viewModel.navigateTo(AppScreen.ENQUIRY) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = BrandCyan),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("Order Script", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Tab 1: Content Creation Studio
            if (activeTab == 1) {
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(14.dp),
                        color = BrandCardElevated,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrandMagenta.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "CONTENT CREATION REPOSITORY",
                                color = BrandMagenta,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Bespoke social media assets, multi-slide carousels, infographics, and viral short-form templates.",
                                color = BrandTextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                items(contentSamples) { sample ->
                    BrandITCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .testTag("content_sample_${sample.id}")
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = when (sample.platform) {
                                    "Instagram" -> Color(0xFFE1306C).copy(alpha = 0.2f)
                                    "YouTube" -> Color(0xFFFF0000).copy(alpha = 0.2f)
                                    else -> BrandElectricBlue.copy(alpha = 0.2f)
                                }
                            ) {
                                Text(
                                    text = "${sample.platform.uppercase()} • ${sample.mediaType.uppercase()}",
                                    color = BrandCyan,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                            Text(text = sample.category, color = BrandTextMuted, fontSize = 11.sp)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = sample.title, color = BrandTextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = sample.description, color = BrandTextSecondary, fontSize = 12.sp, lineHeight = 17.sp)

                        if (sample.caption.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = BrandBackground,
                                border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder)
                            ) {
                                Text(
                                    text = "💬 Caption: \"${sample.caption}\"",
                                    color = BrandTextSecondary,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            OutlinedButton(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sample.mediaUrl))
                                    context.startActivity(intent)
                                },
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, BrandCyan),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.Launch, contentDescription = null, tint = BrandCyan, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("View on ${sample.platform}", color = BrandCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Tab 2: The Creative Mind
            if (activeTab == 2) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "THE BRANDIT CREATIVE MIND",
                            color = BrandCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Demonstrating how BrandIT turns raw insights into award-worthy digital campaigns.",
                            color = BrandTextSecondary,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        val mindSections = listOf(
                            Pair("1. Our Stories", "Transforming routine brand facts into visceral emotional memories that customers remember for years."),
                            Pair("2. Our Scripts", "Mastering rhythm, micro-hooks, and dialogue that feels effortless on camera."),
                            Pair("3. Our Creative Concepts", "Developing unconventional visual metaphors (e.g. juxtaposition of titanium kinetic motion with biological pulses)."),
                            Pair("4. Our Campaign Ideas", "Cross-platform viral mechanics where every comment, share, and repost triggers algorithmic compounding."),
                            Pair("5. Our Promotional Work", "Commercial video edits that make products look indispensable and luxurious.")
                        )

                        mindSections.forEach { sec ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = BrandCard,
                                border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(text = sec.first, color = BrandMagenta, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = sec.second, color = BrandTextPrimary, fontSize = 12.sp, lineHeight = 17.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Tab 3: Behind the Scenes
            if (activeTab == 3) {
                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "BEHIND THE SCENES AT BRANDIT",
                            color = BrandYellow,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Take an exclusive look into our filming stages, DaVinci Resolve color grading suites, script table-reads, and audio design workflows.",
                            color = BrandTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        val btsItems = listOf(
                            Triple("Color Science Lab", "Full ACES color managed pipeline delivering cinema-grade tones for OLED displays.", "DaVinci Resolve Studio"),
                            Triple("Audio & Foley Suite", "Custom sound effects library designed to trigger subconscious dopamine response.", "Spatial 32-bit Float"),
                            Triple("Scriptwriters Room", "Intense table-read sessions where every syllable is timed against B-roll pacing.", "Final Draft & Notion"),
                            Triple("Camera Rigging & Lighting", "High-speed probe lenses, anamorphic optics, and custom RGB volumetric light walls.", "RED & Sony FX")
                        )

                        btsItems.forEach { item ->
                            BrandITCard(modifier = Modifier.padding(vertical = 6.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = item.first, color = BrandTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                                    Text(text = item.third, color = BrandCyan, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = item.second, color = BrandTextSecondary, fontSize = 12.sp, lineHeight = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
