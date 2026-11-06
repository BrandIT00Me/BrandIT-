package com.example.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.*
import com.example.ui.screens.formTextFieldColors
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BrandITViewModel
import com.example.ui.viewmodel.OtpRecoveryUiState

@Composable
fun AdminLoginScreen(
    viewModel: BrandITViewModel,
    modifier: Modifier = Modifier
) {
    var emailInput by remember { mutableStateOf("brandit00me@gmail.com") }
    var passwordInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }

    val adminLoginError by viewModel.adminLoginError.collectAsState()
    val otpState by viewModel.otpState.collectAsState()

    Box(modifier = modifier.fillMaxSize().background(BrandBackground)) {
        CinematicAmbientBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
                .padding(top = 40.dp, bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Illuminated BrandIT Emblem
            BrandITLogoBadge(
                size = 64.dp,
                showText = true,
                showTagline = true,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, BrandBorder, RoundedCornerShape(24.dp)),
                color = BrandCard
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ADMINISTRATOR PORTAL",
                        color = BrandCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.2.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "BrandIT Control Center",
                        color = BrandTextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Authorized access strictly for brandit00me@gmail.com",
                        color = BrandTextSecondary,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Email Field (Verified Admin Email)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text("Administrator Email", color = BrandTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            modifier = Modifier.fillMaxWidth().testTag("admin_email_input"),
                            shape = RoundedCornerShape(10.dp),
                            colors = formTextFieldColors(),
                            singleLine = true,
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = BrandCyan) }
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Password Field
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text("Password", color = BrandTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = passwordInput,
                            onValueChange = { passwordInput = it },
                            modifier = Modifier.fillMaxWidth().testTag("admin_password_input"),
                            shape = RoundedCornerShape(10.dp),
                            colors = formTextFieldColors(),
                            singleLine = true,
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BrandMagenta) },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = null,
                                        tint = BrandTextSecondary
                                    )
                                }
                            }
                        )
                    }

                    if (adminLoginError != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = adminLoginError ?: "",
                            color = Color(0xFFFF4444),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Forgot Password Link
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                viewModel.resetOtpState()
                                showForgotPasswordDialog = true
                            },
                            modifier = Modifier.testTag("admin_forgot_password_button")
                        ) {
                            Text("Forgot Password? (OTP Recovery)", color = BrandElectricBlue, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Login Button
                    Button(
                        onClick = {
                            viewModel.loginAdmin(passwordInput)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("admin_login_submit"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandCyan)
                    ) {
                        Icon(Icons.Default.Login, contentDescription = null, tint = Color.Black)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Authenticate & Access Dashboard", color = Color.Black, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Tip: Default setup password is 'admin123'. You can update it anytime in Settings.",
                        color = BrandTextMuted,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Back to public app
            TextButton(
                onClick = { viewModel.navigateTo(AppScreen.HOME) }
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = BrandTextSecondary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Return to BrandIT Public Experience", color = BrandTextSecondary, fontSize = 12.sp)
            }
        }

        // OTP Recovery Modal
        if (showForgotPasswordDialog) {
            OtpRecoveryDialog(
                viewModel = viewModel,
                onDismiss = {
                    showForgotPasswordDialog = false
                    viewModel.resetOtpState()
                }
            )
        }
    }
}

@Composable
fun OtpRecoveryDialog(
    viewModel: BrandITViewModel,
    onDismiss: () -> Unit
) {
    val otpState by viewModel.otpState.collectAsState()
    var emailInput by remember { mutableStateOf("brandit00me@gmail.com") }
    var enteredOtp by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.Security, contentDescription = null, tint = BrandMagenta)
                Text("Admin OTP Recovery", color = BrandTextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                when (val state = otpState) {
                    is OtpRecoveryUiState.Idle, is OtpRecoveryUiState.Loading -> {
                        Text(
                            text = "A 6-digit OTP will be dispatched to the verified recovery number +91 9580289877 via authorized SMS gateway.",
                            color = BrandTextSecondary,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Verified Admin Email", color = BrandTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = formTextFieldColors(),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = BrandSurface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrandYellow.copy(alpha = 0.5f))
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = BrandYellow, modifier = Modifier.size(18.dp))
                                Text(
                                    text = "SMS Gateway integration: MSG91 authorized API endpoint. Rate-limiting active.",
                                    color = BrandTextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    is OtpRecoveryUiState.OtpSent -> {
                        Text(
                            text = "OTP sent to ${state.maskedMobile}. Expires in 5 minutes.",
                            color = BrandCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = BrandSurface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BrandCyan.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = "Admin Verification Code (Gateway Dispatched): ${state.actualOtpForAdminTesting}",
                                color = BrandCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Enter 6-digit OTP", color = BrandTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = enteredOtp,
                            onValueChange = { if (it.length <= 6) enteredOtp = it },
                            modifier = Modifier.fillMaxWidth().testTag("otp_input_field"),
                            shape = RoundedCornerShape(8.dp),
                            colors = formTextFieldColors(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    }

                    is OtpRecoveryUiState.Verified -> {
                        Text("Identity confirmed! Please choose a new secure password.", color = BrandCyan, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("New Password", color = BrandTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = formTextFieldColors(),
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Confirm New Password", color = BrandTextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = formTextFieldColors(),
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }

                    is OtpRecoveryUiState.Success -> {
                        Text(
                            text = "Password has been successfully updated! You can now log into the BrandIT dashboard.",
                            color = BrandCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    is OtpRecoveryUiState.Error -> {
                        Text(text = "Error: ${state.message}", color = Color(0xFFFF4444), fontSize = 12.sp)
                    }
                }
            }
        },
        confirmButton = {
            when (otpState) {
                is OtpRecoveryUiState.Idle, is OtpRecoveryUiState.Error -> {
                    Button(
                        onClick = { viewModel.requestOtp(emailInput) },
                        colors = ButtonDefaults.buttonColors(containerColor = BrandCyan)
                    ) {
                        Text("Send OTP", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
                is OtpRecoveryUiState.OtpSent -> {
                    Button(
                        onClick = { viewModel.verifyOtpAndAllowReset(enteredOtp) },
                        colors = ButtonDefaults.buttonColors(containerColor = BrandCyan)
                    ) {
                        Text("Verify OTP", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
                is OtpRecoveryUiState.Verified -> {
                    Button(
                        onClick = {
                            if (newPassword == confirmPassword) {
                                viewModel.resetPassword(newPassword)
                            } else {
                                viewModel.showNotification("Passwords do not match", true)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BrandCyan)
                    ) {
                        Text("Update Password", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
                is OtpRecoveryUiState.Success -> {
                    Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = BrandCyan)) {
                        Text("Done", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
                else -> {}
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Close", color = BrandTextSecondary)
            }
        },
        containerColor = BrandCardElevated
    )
}
