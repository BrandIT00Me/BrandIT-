package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.BrandITDatabase
import com.example.data.repository.BrandITRepository
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.screens.admin.AdminDashboardScreen
import com.example.ui.screens.admin.AdminLoginScreen
import com.example.ui.theme.BrandBackground
import com.example.ui.theme.BrandITTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.BrandITViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = BrandITDatabase.getDatabase(applicationContext, lifecycleScope)
        val repository = BrandITRepository(database.brandITDao())

        val viewModelFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BrandITViewModel(repository) as T
            }
        }

        setContent {
            BrandITTheme {
                val viewModel: BrandITViewModel = viewModel(factory = viewModelFactory)
                val currentScreen by viewModel.currentScreen.collectAsState()
                val selectedScript by viewModel.selectedScript.collectAsState()
                val selectedMediaItem by viewModel.selectedMediaItem.collectAsState()
                val notification by viewModel.notification.collectAsState()
                val snackbarHostState = remember { SnackbarHostState() }

                // System Back Handler
                BackHandler {
                    val handled = viewModel.handleBack()
                    if (!handled) {
                        finish()
                    }
                }

                // Show Notifications
                LaunchedEffect(notification) {
                    notification?.let {
                        snackbarHostState.showSnackbar(it.message)
                        viewModel.clearNotification()
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BrandBackground),
                    topBar = {
                        if (currentScreen != AppScreen.SPLASH && currentScreen != AppScreen.ADMIN_DASHBOARD) {
                            BrandITTopBar(viewModel = viewModel)
                        }
                    },
                    bottomBar = {
                        if (currentScreen != AppScreen.SPLASH &&
                            currentScreen != AppScreen.ADMIN_LOGIN &&
                            currentScreen != AppScreen.ADMIN_DASHBOARD
                        ) {
                            BrandITBottomBar(viewModel = viewModel)
                        }
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier.padding(bottom = 80.dp)
                        )
                    },
                    containerColor = BrandBackground
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (currentScreen) {
                            AppScreen.SPLASH -> SplashScreen(viewModel = viewModel)
                            AppScreen.HOME -> HomeScreen(viewModel = viewModel)
                            AppScreen.SERVICES -> ServicesScreen(viewModel = viewModel)
                            AppScreen.CREATIVE_STUDIO -> CreativeStudioScreen(viewModel = viewModel)
                            AppScreen.MEDIA_STUDIO -> MediaStudioScreen(viewModel = viewModel)
                            AppScreen.PORTFOLIO -> PortfolioScreen(viewModel = viewModel)
                            AppScreen.ABOUT -> AboutScreen(viewModel = viewModel)
                            AppScreen.CONTACT -> ContactScreen(viewModel = viewModel)
                            AppScreen.ENQUIRY -> EnquiryScreen(viewModel = viewModel)
                            AppScreen.ADMIN_LOGIN -> AdminLoginScreen(viewModel = viewModel)
                            AppScreen.ADMIN_DASHBOARD -> AdminDashboardScreen(viewModel = viewModel)
                        }

                        // Detail Dialogs
                        selectedScript?.let { script ->
                            ScriptDetailDialog(
                                script = script,
                                viewModel = viewModel,
                                onDismiss = { viewModel.selectScript(null) }
                            )
                        }

                        selectedMediaItem?.let { media ->
                            MediaDetailDialog(
                                media = media,
                                viewModel = viewModel,
                                onDismiss = { viewModel.selectMediaItem(null) }
                            )
                        }
                    }
                }
            }
        }
    }
}
