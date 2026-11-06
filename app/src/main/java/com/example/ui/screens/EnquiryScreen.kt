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
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnquiryScreen(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val serviceOptions = listOf(
        "Scriptwriting Studio",
        "Content Creation",
        "Video Editing",
        "Video Production",
        "Digital Marketing",
        "Social Media Management",
        "Advertisement Management",
        "Branding & Identity",
        "Business Growth Strategy",
        "Other Specialized Service"
    )
    var selectedService by remember { mutableStateOf(serviceOptions[0]) }

    val platformOptions = listOf("YouTube", "Instagram", "Omnichannel", "LinkedIn", "TikTok / Shorts", "Web / Broadcast")
    var selectedPlatform by remember { mutableStateOf(platformOptions[0]) }

    val budgetOptions = listOf("Under $1,000", "$1,000 - $2,500", "$2,500 - $5,000", "$5,000 - $10,000", "$10,000+ Enterprise")
    var selectedBudget by remember { mutableStateOf(budgetOptions[1]) }

    val timelineOptions = listOf("Immediate (< 1 Week)", "2 - 4 Weeks", "1 - 2 Months", "Ongoing Retainer")
    var selectedTimeline by remember { mutableStateOf(timelineOptions[1]) }

    var submittedReference by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize().background(BrandBackground)) {
        CinematicAmbientBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 110.dp)
                .testTag("enquiry_screen")
        ) {
            // Header
            SectionHeader(
                tag = "Commission A Project",
                title = "LET'S CREATE SOMETHING EXTRAORDINARY.",
                subtitle = "Have an idea, a brand to build or a business to grow? Tell us what you have in mind."
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (submittedReference != null) {
                // Success State Card
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, BrandCyan, RoundedCornerShape(20.dp)),
                    color = BrandCardElevated
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(BrandCyan.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = BrandCyan, modifier = Modifier.size(36.dp))
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "PROJECT BRIEF RECEIVED",
                            color = BrandTextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Reference ID: $submittedReference",
                            color = BrandMagenta,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Our creative directors will review your brief within 24 hours. For priority scheduling, connect directly via WhatsApp.",
                            color = BrandTextSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Direct WhatsApp with pre-filled message
                        Button(
                            onClick = {
                                val text = "Hello BrandIT! I have submitted project brief Ref: $submittedReference. Name: $fullName, Service: $selectedService."
                                val encoded = URLEncoder.encode(text, "UTF-8")
                                val uri = Uri.parse("https://wa.me/919580289877?text=$encoded")
                                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                            modifier = Modifier.fillMaxWidth().height(46.dp)
                        ) {
                            Icon(Icons.Default.Chat, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Fast-Track via WhatsApp", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = {
                                submittedReference = null
                                fullName = ""
                                email = ""
                                mobile = ""
                                company = ""
                                description = ""
                            },
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder),
                            modifier = Modifier.fillMaxWidth().height(44.dp)
                        ) {
                            Text("Submit Another Brief", color = BrandTextPrimary, fontSize = 12.sp)
                        }
                    }
                }
            } else {
                // Interactive Brief Form
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.dp, BrandBorder, RoundedCornerShape(20.dp)),
                    color = BrandCard
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {

                        // Full Name
                        Text("Full Name *", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            modifier = Modifier.fillMaxWidth().testTag("enquiry_input_name"),
                            placeholder = { Text("e.g. Alex Morgan", color = BrandTextMuted, fontSize = 13.sp) },
                            shape = RoundedCornerShape(10.dp),
                            colors = formTextFieldColors(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Email
                        Text("Email Address *", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth().testTag("enquiry_input_email"),
                            placeholder = { Text("e.g. alex@brand.com", color = BrandTextMuted, fontSize = 13.sp) },
                            shape = RoundedCornerShape(10.dp),
                            colors = formTextFieldColors(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Mobile Number
                        Text("Mobile / WhatsApp Number *", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = mobile,
                            onValueChange = { mobile = it },
                            modifier = Modifier.fillMaxWidth().testTag("enquiry_input_phone"),
                            placeholder = { Text("e.g. +91 98765 43210", color = BrandTextMuted, fontSize = 13.sp) },
                            shape = RoundedCornerShape(10.dp),
                            colors = formTextFieldColors(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Company / Brand Name
                        Text("Company or Brand Name", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = company,
                            onValueChange = { company = it },
                            modifier = Modifier.fillMaxWidth().testTag("enquiry_input_company"),
                            placeholder = { Text("e.g. Nexus Innovations", color = BrandTextMuted, fontSize = 13.sp) },
                            shape = RoundedCornerShape(10.dp),
                            colors = formTextFieldColors(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Service Required
                        Text("Service Required *", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(6.dp))
                        var serviceExpanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = serviceExpanded,
                            onExpandedChange = { serviceExpanded = !serviceExpanded }
                        ) {
                            OutlinedTextField(
                                value = selectedService,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = serviceExpanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = formTextFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = serviceExpanded,
                                onDismissRequest = { serviceExpanded = false },
                                modifier = Modifier.background(BrandCardElevated)
                            ) {
                                serviceOptions.forEach { opt ->
                                    DropdownMenuItem(
                                        text = { Text(opt, color = BrandTextPrimary, fontSize = 13.sp) },
                                        onClick = {
                                            selectedService = opt
                                            serviceExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Preferred Platform
                        Text("Target Platform", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(6.dp))
                        var platformExpanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = platformExpanded,
                            onExpandedChange = { platformExpanded = !platformExpanded }
                        ) {
                            OutlinedTextField(
                                value = selectedPlatform,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = platformExpanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = formTextFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = platformExpanded,
                                onDismissRequest = { platformExpanded = false },
                                modifier = Modifier.background(BrandCardElevated)
                            ) {
                                platformOptions.forEach { opt ->
                                    DropdownMenuItem(
                                        text = { Text(opt, color = BrandTextPrimary, fontSize = 13.sp) },
                                        onClick = {
                                            selectedPlatform = opt
                                            platformExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Budget Range
                        Text("Budget Range", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(6.dp))
                        var budgetExpanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = budgetExpanded,
                            onExpandedChange = { budgetExpanded = !budgetExpanded }
                        ) {
                            OutlinedTextField(
                                value = selectedBudget,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = budgetExpanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = formTextFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = budgetExpanded,
                                onDismissRequest = { budgetExpanded = false },
                                modifier = Modifier.background(BrandCardElevated)
                            ) {
                                budgetOptions.forEach { opt ->
                                    DropdownMenuItem(
                                        text = { Text(opt, color = BrandTextPrimary, fontSize = 13.sp) },
                                        onClick = {
                                            selectedBudget = opt
                                            budgetExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Expected Timeline
                        Text("Expected Project Timeline", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(6.dp))
                        var timelineExpanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = timelineExpanded,
                            onExpandedChange = { timelineExpanded = !timelineExpanded }
                        ) {
                            OutlinedTextField(
                                value = selectedTimeline,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = timelineExpanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = formTextFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = timelineExpanded,
                                onDismissRequest = { timelineExpanded = false },
                                modifier = Modifier.background(BrandCardElevated)
                            ) {
                                timelineOptions.forEach { opt ->
                                    DropdownMenuItem(
                                        text = { Text(opt, color = BrandTextPrimary, fontSize = 13.sp) },
                                        onClick = {
                                            selectedTimeline = opt
                                            timelineExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Project Description
                        Text("Project Description & Vision *", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .testTag("enquiry_input_description"),
                            placeholder = { Text("Describe the project objectives, desired tone, target audience, and reference links...", color = BrandTextMuted, fontSize = 12.sp) },
                            shape = RoundedCornerShape(10.dp),
                            colors = formTextFieldColors()
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Submit Button
                        Button(
                            onClick = {
                                viewModel.submitProjectEnquiry(
                                    name = fullName,
                                    email = email,
                                    phone = mobile,
                                    company = company,
                                    service = selectedService,
                                    description = description,
                                    platform = selectedPlatform,
                                    budget = selectedBudget,
                                    timeline = selectedTimeline,
                                    onSuccess = { ref ->
                                        submittedReference = ref
                                    }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("submit_enquiry_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrandCyan)
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Submit Creative Brief", color = Color.Black, fontWeight = FontWeight.Black, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Direct Communication Channels
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, BrandBorder, RoundedCornerShape(16.dp)),
                color = BrandCard
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "DIRECT COMMUNICATIONS",
                        color = BrandCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Official WhatsApp", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text("+91 9580289877", color = BrandTextSecondary, fontSize = 12.sp)
                        }
                        Button(
                            onClick = {
                                val uri = Uri.parse("https://wa.me/919580289877?text=Hello%20BrandIT,%20I%20would%20like%20to%20discuss%20a%20creative%20project.")
                                context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Chat", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(color = BrandBorder)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Administrator Email", color = BrandTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text("brandit00me@gmail.com", color = BrandTextSecondary, fontSize = 12.sp)
                        }
                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:brandit00me@gmail.com"))
                                context.startActivity(intent)
                            },
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrandBorder),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Email", color = BrandTextPrimary, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun formTextFieldColors(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedContainerColor = BrandSurface,
        unfocusedContainerColor = BrandSurface,
        focusedBorderColor = BrandCyan,
        unfocusedBorderColor = BrandBorder,
        focusedTextColor = BrandTextPrimary,
        unfocusedTextColor = BrandTextPrimary
    )
}
