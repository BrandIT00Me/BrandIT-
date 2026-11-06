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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.MediaStudioItemEntity
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.BrandITViewModel

@Composable
fun MediaStudioScreen(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    val activeTab by viewModel.mediaStudioTab.collectAsState()
    val allMedia by viewModel.allMediaItems.collectAsState()
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }

    val tabs = listOf(
        "All Media",
        "YouTube Videos",
        "Instagram Reels",
        "Featured Content",
        "BrandIT Originals",
        "Google Drive Cloud"
    )

    val driveFolders = listOf(
        "01 Video Production",
        "02 Instagram Reels",
        "03 YouTube Videos",
        "04 Advertisements",
        "05 Promotional Videos",
        "06 Scriptwriting",
        "07 Content Creation",
        "08 Graphic Designing",
        "09 Client Projects",
        "10 Thumbnails",
        "11 Creative Assets",
        "12 Published Content"
    )

    val filteredMedia = remember(allMedia, activeTab, searchQuery) {
        allMedia.filter { item ->
            val matchesTab = when (activeTab) {
                1 -> item.platform == "YouTube"
                2 -> item.platform == "Instagram"
                3 -> item.isFeatured
                4 -> item.platform == "BrandIT Originals"
                5 -> item.platform == "Google Drive"
                else -> true
            }
            val matchesSearch = if (searchQuery.isBlank()) true else {
                item.title.contains(searchQuery, ignoreCase = true) ||
                        item.description.contains(searchQuery, ignoreCase = true) ||
                        item.category.contains(searchQuery, ignoreCase = true)
            }
            matchesTab && matchesSearch
        }
    }

    Box(modifier = modifier.fillMaxSize().background(BrandBackground)) {
        CinematicAmbientBackground()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("media_studio_screen_list"),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Header
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(
                        tag = "Streaming & Media",
                        title = "BrandIT Media Studio",
                        subtitle = "Official releases from YouTube @BrandIT_for & Instagram @brandit_me"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Official Channel Quick Connect Bars
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFFF0000).copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                .clickable {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@BrandIT_for"))
                                    context.startActivity(intent)
                                },
                            color = BrandCard
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.PlayCircle, contentDescription = null, tint = Color(0xFFFF4444), modifier = Modifier.size(20.dp))
                                Column {
                                    Text("YouTube Channel", color = BrandTextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text("@BrandIT_for", color = Color(0xFFFF4444), fontSize = 10.sp)
                                }
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Color(0xFFE1306C).copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                .clickable {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/brandit_me?psln=MXhrN2F0aGxkMWlncw=="))
                                    context.startActivity(intent)
                                },
                            color = BrandCard
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color(0xFFFF3E7D), modifier = Modifier.size(20.dp))
                                Column {
                                    Text("Instagram Profile", color = BrandTextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text("@brandit_me", color = Color(0xFFFF3E7D), fontSize = 10.sp)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Search input
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("media_search_input"),
                        placeholder = { Text("Search videos by title or topic...", color = BrandTextMuted, fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = BrandCyan) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear", tint = BrandTextSecondary)
                                }
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = BrandCard,
                            unfocusedContainerColor = BrandCard,
                            focusedBorderColor = BrandCyan,
                            unfocusedBorderColor = BrandBorder,
                            focusedTextColor = BrandTextPrimary,
                            unfocusedTextColor = BrandTextPrimary
                        ),
                        singleLine = true
                    )
                }
            }

            // Tabs
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 14.dp)
                ) {
                    items(tabs.indices.toList()) { index ->
                        FilterChipBadge(
                            label = tabs[index],
                            isSelected = activeTab == index,
                            onClick = { viewModel.setMediaStudioTab(index) }
                        )
                    }
                }
            }

            // Google Drive Cloud Studio Folder Tree (when tab 5 is active)
            if (activeTab == 5) {
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = BrandCardElevated,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrandYellow.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(Icons.Default.CloudQueue, contentDescription = null, tint = BrandYellow, modifier = Modifier.size(24.dp))
                                Column {
                                    Text("BRANDIT CLOUD STUDIO", color = BrandYellow, fontSize = 13.sp, fontWeight = FontWeight.Black)
                                    Text("Connected Google Drive Architecture (OAuth 2.0)", color = BrandTextSecondary, fontSize = 11.sp)
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Dedicated high-bandwidth repository for master 4K ProRes renders, client deliverable archives, voiceover stems, and final assets.",
                                color = BrandTextSecondary,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "CLOUD STUDIO DIRECTORY STRUCTURE",
                        color = BrandTextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(driveFolders) { folder ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = BrandCard,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(Icons.Default.Folder, contentDescription = null, tint = BrandYellow, modifier = Modifier.size(18.dp))
                                Text(text = folder, color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = BrandSurface
                            ) {
                                Text(
                                    text = "Synced",
                                    color = BrandCyan,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "PUBLIC AUTHORIZED CLOUD MEDIA",
                        color = BrandTextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }

            // Media Items List
            if (filteredMedia.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No media matches the current filter or search.", color = BrandTextMuted, fontSize = 13.sp)
                    }
                }
            } else {
                items(filteredMedia) { media ->
                    BrandITCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .testTag("media_card_${media.id}"),
                        onClick = { viewModel.selectMediaItem(media) }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(12.dp))
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
                                    .background(Color.Black.copy(alpha = 0.38f))
                            )

                            // Play badge
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when (media.platform) {
                                            "YouTube" -> Color(0xFFFF0000).copy(alpha = 0.85f)
                                            "Instagram" -> Color(0xFFE1306C).copy(alpha = 0.85f)
                                            else -> BrandCyan.copy(alpha = 0.85f)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(28.dp))
                            }

                            // Platform pill on top left
                            Surface(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(10.dp),
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Black.copy(alpha = 0.75f)
                            ) {
                                Text(
                                    text = media.platform.uppercase(),
                                    color = when (media.platform) {
                                        "YouTube" -> Color(0xFFFF5555)
                                        "Instagram" -> Color(0xFFFF3E7D)
                                        else -> BrandCyan
                                    },
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            // Duration on bottom right
                            Surface(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(10.dp),
                                shape = RoundedCornerShape(4.dp),
                                color = Color.Black.copy(alpha = 0.85f)
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

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = media.title, color = BrandTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = media.description,
                            color = BrandTextSecondary,
                            fontSize = 12.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "👁 ${media.viewsCount} • ${media.category}",
                                color = BrandTextMuted,
                                fontSize = 11.sp
                            )
                            Text(
                                text = "Watch Media →",
                                color = BrandCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
