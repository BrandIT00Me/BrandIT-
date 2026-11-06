package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BrandITViewModel

@Composable
fun AboutScreen(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize().background(BrandBackground)) {
        CinematicAmbientBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(bottom = 96.dp)
                .testTag("about_screen")
        ) {
            SectionHeader(
                tag = "About BrandIT",
                title = "CREATIVITY IS OUR LANGUAGE. GROWTH IS OUR GOAL.",
                subtitle = "Transforming ideas into meaningful digital experiences"
            )

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, BrandBorder, RoundedCornerShape(20.dp)),
                color = BrandCard
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    BrandITLogoBadge(size = 46.dp, showText = true, showTagline = true)
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "BrandIT is a creative and digital growth studio that transforms ideas into meaningful digital experiences. We combine creative writing, professional scriptwriting, content creation, video production, digital marketing, advertising and brand development to help businesses and creators communicate effectively, build stronger identities and expand their digital presence.",
                        color = BrandTextPrimary,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val coreSections = listOf(
                Triple("OUR VISION", "To become a trusted creative and digital growth partner for businesses, entrepreneurs, creators and emerging brands.", BrandCyan),
                Triple("OUR MISSION", "To transform ideas into impactful stories, engaging content, creative experiences and effective digital solutions.", BrandMagenta),
                Triple("OUR EXPERTISE", "Scriptwriting, storytelling, content creation, video production, editing, digital marketing, social media management, advertisement management and brand growth.", BrandViolet),
                Triple("OUR APPROACH", "Understand the idea, develop the strategy, write the story, create the content, produce the media, promote the brand and measure the progress.", BrandElectricBlue),
                Triple("OUR COMMITMENT", "Creativity, quality, professionalism, innovation and continuous improvement.", BrandYellow)
            )

            coreSections.forEach { sec ->
                BrandITCard(modifier = Modifier.padding(vertical = 6.dp)) {
                    Text(text = sec.first, color = sec.third, fontSize = 12.sp, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = sec.second, color = BrandTextPrimary, fontSize = 13.sp, lineHeight = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { viewModel.navigateTo(AppScreen.ENQUIRY) },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandCyan)
            ) {
                Text("Start Your Project with BrandIT", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ContactScreen(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize().background(BrandBackground)) {
        CinematicAmbientBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .padding(bottom = 96.dp)
                .testTag("contact_screen")
        ) {
            SectionHeader(
                tag = "Get In Touch",
                title = "Contact BrandIT Studio",
                subtitle = "Direct lines to our creative directors and production team"
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Contact Channels
            val channels = listOf(
                Triple("Official YouTube Channel", "@BrandIT_for • Video productions & masterclasses", "https://www.youtube.com/@BrandIT_for"),
                Triple("Official Instagram", "@brandit_me • Creative reels & design updates", "https://www.instagram.com/brandit_me?psln=MXhrN2F0aGxkMWlncw=="),
                Triple("Official Administrator Email", "brandit00me@gmail.com • Direct project briefs & queries", "mailto:brandit00me@gmail.com"),
                Triple("Direct WhatsApp & Mobile", "+91 9580289877 • Quick chat & consultation", "https://wa.me/919580289877?text=Hello%20BrandIT")
            )

            channels.forEach { ch ->
                BrandITCard(
                    modifier = Modifier.padding(vertical = 6.dp),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ch.third))
                        context.startActivity(intent)
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = ch.first, color = BrandCyan, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = ch.second, color = BrandTextSecondary, fontSize = 12.sp)
                        }
                        Icon(Icons.Default.ArrowOutward, contentDescription = null, tint = BrandTextSecondary, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { viewModel.navigateTo(AppScreen.ENQUIRY) },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandCyan)
            ) {
                Icon(Icons.Default.Send, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Submit Project Enquiry Form", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}
