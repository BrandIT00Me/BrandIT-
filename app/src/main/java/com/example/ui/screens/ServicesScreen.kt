package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BrandITViewModel

data class ServiceCategoryItem(
    val id: String,
    val name: String,
    val headline: String,
    val color: Color,
    val icon: ImageVector,
    val services: List<ServiceDetailItem>
)

data class ServiceDetailItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val tag: String
)

@Composable
fun ServicesScreen(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    val categories = remember { getServicesCatalog() }
    var selectedCategoryIndex by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val activeCategory = categories[selectedCategoryIndex]

    val filteredServices = remember(activeCategory, searchQuery) {
        if (searchQuery.isBlank()) {
            activeCategory.services
        } else {
            activeCategory.services.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.description.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize().background(BrandBackground)) {
        CinematicAmbientBackground()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("services_screen_list"),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Header
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionHeader(
                        tag = "Complete Solutions",
                        title = "Services Directory",
                        subtitle = "Seven specialized creative studios under one unified roof"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Search input
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("services_search_input"),
                        placeholder = { Text("Search services (e.g. YouTube scripts, 3D reels...)", color = BrandTextMuted, fontSize = 13.sp) },
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

            // Category Filter Pills
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 14.dp)
                ) {
                    itemsIndexed(categories) { index, category ->
                        FilterChipBadge(
                            label = category.name,
                            isSelected = selectedCategoryIndex == index,
                            onClick = { selectedCategoryIndex = index },
                            icon = {
                                Icon(
                                    imageVector = category.icon,
                                    contentDescription = null,
                                    tint = if (selectedCategoryIndex == index) BrandCyan else BrandTextMuted,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        )
                    }
                }
            }

            // Active Category Banner
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = BrandCardElevated,
                    border = androidx.compose.foundation.BorderStroke(1.dp, activeCategory.color.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(activeCategory.color.copy(alpha = 0.18f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(activeCategory.icon, contentDescription = null, tint = activeCategory.color, modifier = Modifier.size(26.dp))
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = activeCategory.name.uppercase(),
                                color = activeCategory.color,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = activeCategory.headline,
                                color = BrandTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // Services Grid/List
            items(filteredServices) { service ->
                BrandITCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("service_card_${service.title.take(15).lowercase().replace(" ", "_")}")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(activeCategory.color.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(service.icon, contentDescription = null, tint = activeCategory.color, modifier = Modifier.size(20.dp))
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = service.title,
                                    color = BrandTextPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = BrandSurface
                                ) {
                                    Text(
                                        text = service.tag,
                                        color = BrandCyan,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = service.description,
                                color = BrandTextSecondary,
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.navigateTo(AppScreen.ENQUIRY)
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = activeCategory.color),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                    modifier = Modifier.testTag("request_service_button")
                                ) {
                                    Icon(Icons.Default.Send, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Request This Service", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getServicesCatalog(): List<ServiceCategoryItem> {
    return listOf(
        ServiceCategoryItem(
            id = "scriptwriting",
            name = "Scriptwriting Studio",
            headline = "EVERY GREAT VIDEO BEGINS WITH A GREAT IDEA. EVERY GREAT IDEA DESERVES A GREAT SCRIPT.",
            color = BrandCyan,
            icon = Icons.Default.EditNote,
            services = listOf(
                ServiceDetailItem("YouTube Video Scriptwriting", "In-depth, high-retention long-form scripts structured for algorithmic watch time and emotional hooks.", Icons.Default.VideoLibrary, "Popular"),
                ServiceDetailItem("Instagram Reel Scriptwriting", "High-velocity 30-60s short-form scripts engineered for micro-hooks and comment loops.", Icons.Default.FlashOn, "Viral"),
                ServiceDetailItem("Short-Form Video Scripts", "Punchy narrative copy for TikTok and YouTube Shorts tailored to fast attention spans.", Icons.Default.PhoneAndroid, "Shorts"),
                ServiceDetailItem("Long-Form Video Scripts", "Investigative, educational, and narrative docu-style scripts with multi-act structure.", Icons.Default.Description, "Comprehensive"),
                ServiceDetailItem("Advertisement Scriptwriting", "High-converting TV commercial and paid social ad scripts with psychological purchase triggers.", Icons.Default.MonetizationOn, "High ROAS"),
                ServiceDetailItem("Promotional Video Scripts", "Dynamic launch announcements, event teasers, and brand showcases.", Icons.Default.Campaign, "Commercial"),
                ServiceDetailItem("Brand Storytelling", "Deep narrative frameworks articulating founder journeys, brand philosophy, and culture.", Icons.Default.AutoStories, "Identity"),
                ServiceDetailItem("Corporate Video Scriptwriting", "Executive interviews, stakeholder presentations, and company profile scripts.", Icons.Default.Business, "Enterprise"),
                ServiceDetailItem("Explainer Video Scripts", "Simplifying complex technical, financial, or medical concepts into relatable analogies.", Icons.Default.Lightbulb, "Clarity"),
                ServiceDetailItem("Educational Video Scripts", "Structured masterclass, course, and educational breakdown scripts for experts.", Icons.Default.School, "Education"),
                ServiceDetailItem("Documentary Scriptwriting", "Cinematic investigative narrative scripts with research-backed voiceover.", Icons.Default.Movie, "Cinematic"),
                ServiceDetailItem("Product Advertisement Scripts", "Highlighting product USPs, tactile benefits, and lifestyle integration.", Icons.Default.ShoppingBag, "E-Commerce"),
                ServiceDetailItem("Business Promotional Scripts", "B2B pitch decks, demo videos, and capability overviews.", Icons.Default.Storefront, "B2B"),
                ServiceDetailItem("Voiceover Scripts", "Paced vocal timing, phonetic annotations, and emotional delivery cues.", Icons.Default.Mic, "Audio"),
                ServiceDetailItem("Storytelling & Narrative Writing", "Hero's journey archetypes and character-driven commercial writing.", Icons.Default.HistoryEdu, "Story"),
                ServiceDetailItem("Creative Campaign Concepts", "360-degree cross-platform creative themes and episodic ideas.", Icons.Default.WbIncandescent, "Concept"),
                ServiceDetailItem("Podcast Scripts", "Interview outlines, opening monologues, and thematic discussion questions.", Icons.Default.Podcasts, "Podcast"),
                ServiceDetailItem("Social Media Captions", "Micro-copy, hook captions, and hashtag strategies.", Icons.Default.ShortText, "Copy"),
                ServiceDetailItem("Video Hooks & Opening Lines", "The critical first 3 seconds that prevent the swipe-away.", Icons.Default.Bolt, "Retention"),
                ServiceDetailItem("Call-to-Action Writing", "Irresistible closing hooks driving website visits, orders, and sign-ups.", Icons.Default.TouchApp, "Conversion")
            )
        ),
        ServiceCategoryItem(
            id = "content_creation",
            name = "Content Studio",
            headline = "ORIGINAL, HIGH-IMPACT VISUAL ASSETS AND CAMPAIGNS FOR EVERY PLATFORM.",
            color = BrandMagenta,
            icon = Icons.Default.Palette,
            services = listOf(
                ServiceDetailItem("Social Media Content Creation", "End-to-end bespoke social content tailored for creator and corporate brands.", Icons.Default.Share, "Omnichannel"),
                ServiceDetailItem("Instagram Content Creation", "Aesthetic grid planning, high-engagement story sets, and interactive stickers.", Icons.Default.CameraAlt, "Instagram"),
                ServiceDetailItem("YouTube Content Creation", "Custom high-CTR thumbnails, community posts, and channel assets.", Icons.Default.SmartDisplay, "YouTube"),
                ServiceDetailItem("Facebook Content Creation", "Community engagement posts, video cutdowns, and discussion starters.", Icons.Default.Groups, "Meta"),
                ServiceDetailItem("LinkedIn Content Creation", "Thought leadership carousels, executive summaries, and industry insights.", Icons.Default.WorkOutline, "B2B"),
                ServiceDetailItem("Educational Content", "Visual breakdowns, frameworks, and actionable tips that earn saves and shares.", Icons.Default.MenuBook, "Value"),
                ServiceDetailItem("Informational Content", "Data summaries, industry news updates, and curated takeaways.", Icons.Default.Info, "Data"),
                ServiceDetailItem("Promotional Content", "Seasonal offers, limited product drops, and flash campaign visuals.", Icons.Default.LocalOffer, "Sales"),
                ServiceDetailItem("Brand Storytelling Content", "Visual vignettes connecting customers to the core heart of your brand.", Icons.Default.Favorite, "Engagement"),
                ServiceDetailItem("Reels and Shorts", "Custom vertical video animations, motion typography, and sound design.", Icons.Default.PlayCircle, "Video"),
                ServiceDetailItem("Carousel Content", "Multi-slide swipeable micro-books with progressive value reveal.", Icons.Default.ViewCarousel, "High Saves"),
                ServiceDetailItem("Infographic Content", "Complex datasets visualized into stunning shareable graphics.", Icons.Default.BarChart, "Data"),
                ServiceDetailItem("Creative Posters", "High-concept promotional artwork, festival graphics, and tour posters.", Icons.Default.ArtTrack, "Poster"),
                ServiceDetailItem("Content Calendar Planning", "Month-ahead structured editorial schedules aligned with business goals.", Icons.Default.CalendarMonth, "Schedule"),
                ServiceDetailItem("Content Strategy Development", "Audience persona mapping, content pillars, and KPI benchmarks.", Icons.Default.Insights, "Strategy"),
                ServiceDetailItem("Creative Campaign Content", "Unified assets across digital banners, email headers, and social feeds.", Icons.Default.AllInclusive, "Full Scope")
            )
        ),
        ServiceCategoryItem(
            id = "video_production",
            name = "Video Production",
            headline = "CINEMATIC 4K VIDEO PRODUCTION, SOUND DESIGN, AND EDITING EXCELLENCE.",
            color = BrandElectricBlue,
            icon = Icons.Default.MovieCreation,
            services = listOf(
                ServiceDetailItem("Professional Video Editing", "Seamless pacing, multi-track audio mastering, and broadcast grading.", Icons.Default.ContentCut, "Editing"),
                ServiceDetailItem("Cinematic Video Editing", "Anamorphic aspect ratios, film grain simulation, and dynamic audio curves.", Icons.Default.Videocam, "Cinematic"),
                ServiceDetailItem("Promotional Video Editing", "High-tempo cutdowns crafted to build urgency and product lust.", Icons.Default.Speed, "Promo"),
                ServiceDetailItem("Corporate Video Production", "Multi-camera executive keynote captures and facility walkthroughs.", Icons.Default.CorporateFare, "Corporate"),
                ServiceDetailItem("Advertisement Video Production", "Full commercial production from camera rigs to VFX final output.", Icons.Default.Tv, "Broadcast"),
                ServiceDetailItem("YouTube Video Editing", "Pattern interrupts, B-roll integration, and kinetic sound effects.", Icons.Default.PlayLesson, "Retention"),
                ServiceDetailItem("Instagram Reels Editing", "Speed ramping, beat-synced cuts, and dynamic zoom punch-ins.", Icons.Default.MovieFilter, "Reels"),
                ServiceDetailItem("Motion Graphics", "2D/3D kinetic titles, animated data charts, and vector animations.", Icons.Default.Animation, "Motion"),
                ServiceDetailItem("Intro and Outro Creation", "Custom branded opening stings and subscriber end-screen cards.", Icons.Default.SkipNext, "Branding"),
                ServiceDetailItem("Colour Grading", "DaVinci Resolve color science matching moods from neon cyberpunk to warm organic.", Icons.Default.ColorLens, "Color"),
                ServiceDetailItem("Audio Synchronisation & SFX", "Foley, spatial sound effects, sub-bass drops, and pristine audio cleanup.", Icons.Default.Audiotrack, "Audio"),
                ServiceDetailItem("Subtitle & Caption Integration", "Eye-catching kinetic karaoke captions formatted for sound-off viewing.", Icons.Default.Subtitles, "Accessibility")
            )
        ),
        ServiceCategoryItem(
            id = "digital_marketing",
            name = "Digital Marketing",
            headline = "STRATEGIC REACH, SEARCH DOMINANCE, AND MEASURABLE AUDIENCE EXPANSION.",
            color = BrandOrange,
            icon = Icons.Default.TrendingUp,
            services = listOf(
                ServiceDetailItem("Digital Marketing Strategy", "Comprehensive blueprint uniting organic creative with targeted distribution.", Icons.Default.TrackChanges, "Strategy"),
                ServiceDetailItem("Social Media Marketing", "Building active brand advocates and digital tribes across networks.", Icons.Default.Public, "Social"),
                ServiceDetailItem("Content Marketing", "Evergreen assets driving consistent inbound organic leads.", Icons.Default.Feed, "Inbound"),
                ServiceDetailItem("Brand Awareness Campaigns", "High-frequency impression blitz establishing undeniable category dominance.", Icons.Default.Visibility, "Awareness"),
                ServiceDetailItem("Audience Research & Personas", "Deep demographic, psychographic, and behavioral customer profiling.", Icons.Default.People, "Research"),
                ServiceDetailItem("YouTube SEO", "Title optimization, description metadata, chapter markers, and tag architecture.", Icons.Default.Search, "YouTube"),
                ServiceDetailItem("Instagram SEO & Discoverability", "Keyword-optimized bios, alt text, and audio trend riding.", Icons.Default.Explore, "Instagram"),
                ServiceDetailItem("Marketing Analytics & Reporting", "Transparent dashboards tracking customer acquisition and engagement rate.", Icons.Default.Assessment, "Analytics")
            )
        ),
        ServiceCategoryItem(
            id = "social_media_management",
            name = "Social Management",
            headline = "HANDS-OFF DAILY EXECUTION, COMMUNITY GROWTH, AND CALENDAR CONTROL.",
            color = BrandViolet,
            icon = Icons.Default.ManageAccounts,
            services = listOf(
                ServiceDetailItem("Instagram Account Management", "Daily posting, story updates, highlight curation, and comment replies.", Icons.Default.PhotoCamera, "Full Care"),
                ServiceDetailItem("YouTube Channel Management", "Upload scheduling, community tab engagement, and playlist curation.", Icons.Default.OndemandVideo, "Channel"),
                ServiceDetailItem("Content Scheduling", "Optimized time-slot dispatch maximizing initial algorithmic momentum.", Icons.Default.Schedule, "Timing"),
                ServiceDetailItem("Reels Strategy & Execution", "Weekly trending format analysis, audio pairing, and hook testing.", Icons.Default.Whatshot, "Growth"),
                ServiceDetailItem("Community Engagement", "Active relationship-building with industry peers, influencers, and fans.", Icons.Default.Forum, "Community"),
                ServiceDetailItem("Profile & Bio Optimization", "Conversion-focused link in bio funnels, clean layout, and highlight icons.", Icons.Default.AccountBox, "Optimization")
            )
        ),
        ServiceCategoryItem(
            id = "advertisement_management",
            name = "Ad Management",
            headline = "PRECISION MEDIA BUYING, CREATIVE ITERATION, AND HIGH CONVERSION RATES.",
            color = BrandYellow,
            icon = Icons.Default.Paid,
            services = listOf(
                ServiceDetailItem("Video Advertisement Creation", "Custom creatives designed specifically for paid feed and story ad formats.", Icons.Default.VideoCall, "Creative"),
                ServiceDetailItem("Instagram & Facebook Ads", "Full Meta campaign architecture from prospecting to retargeting funnels.", Icons.Default.AdsClick, "Meta"),
                ServiceDetailItem("YouTube Ads & In-Stream", "Skippable and non-skippable bumper ads driving qualified video views.", Icons.Default.FeaturedVideo, "YouTube"),
                ServiceDetailItem("Advertisement Copywriting", "Psychological copy with high contrast, curiosity, and urgency.", Icons.Default.HistoryEdu, "Copywriting"),
                ServiceDetailItem("Campaign Strategy & Setup", "Pixel configuration, custom conversion tracking, and audience lookalikes.", Icons.Default.SettingsSuggest, "Setup"),
                ServiceDetailItem("Campaign Performance Analysis", "Weekly ROAS, CPC, and CTR reporting with continuous creative iteration.", Icons.Default.QueryStats, "Reporting")
            )
        ),
        ServiceCategoryItem(
            id = "branding_growth",
            name = "Branding & Growth",
            headline = "ICONIC BRAND IDENTITY, DISTINCTIVE POSITIONING, AND EXPANSION STRATEGY.",
            color = Color(0xFF00D2FF),
            icon = Icons.Default.AutoFixHigh,
            services = listOf(
                ServiceDetailItem("Brand Identity Development", "Logomarks, design systems, color harmonies, and typography rules.", Icons.Default.Fingerprint, "Identity"),
                ServiceDetailItem("Brand Strategy & Positioning", "Finding the uncontested market space where competitors cannot follow.", Icons.Default.CompassCalibration, "Strategy"),
                ServiceDetailItem("Logo Animation & Motion Ident", "Dynamic 3D logo resolves for video intros and digital billboards.", Icons.Default.BurstMode, "Motion"),
                ServiceDetailItem("Product Launch Campaigns", "Multi-stage countdown, teaser, and launch day digital orchestration.", Icons.Default.Rocket, "Launch"),
                ServiceDetailItem("Business Growth Consultation", "One-on-one sessions with BrandIT creative directors to unlock growth bottlenecks.", Icons.Default.SupportAgent, "Consulting"),
                ServiceDetailItem("Digital Presence Transformation", "Upgrading outdated digital footprints into prestige luxury brand experiences.", Icons.Default.Upgrade, "Elevation")
            )
        )
    )
}
