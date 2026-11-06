package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.BrandITRepository
import com.example.data.repository.OtpRequestResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

enum class AppScreen {
    SPLASH,
    HOME,
    SERVICES,
    CREATIVE_STUDIO,
    MEDIA_STUDIO,
    PORTFOLIO,
    ABOUT,
    CONTACT,
    ENQUIRY,
    ADMIN_LOGIN,
    ADMIN_DASHBOARD
}

data class UiNotification(
    val message: String,
    val isError: Boolean = false
)

class BrandITViewModel(
    private val repository: BrandITRepository
) : ViewModel() {

    // Current Navigation State
    private val _currentScreen = MutableStateFlow(AppScreen.SPLASH)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _screenHistory = mutableListOf<AppScreen>()

    // Detail selection
    private val _selectedScript = MutableStateFlow<ScriptItemEntity?>(null)
    val selectedScript: StateFlow<ScriptItemEntity?> = _selectedScript.asStateFlow()

    private val _selectedMediaItem = MutableStateFlow<MediaStudioItemEntity?>(null)
    val selectedMediaItem: StateFlow<MediaStudioItemEntity?> = _selectedMediaItem.asStateFlow()

    private val _selectedPortfolioProject = MutableStateFlow<PortfolioProjectEntity?>(null)
    val selectedPortfolioProject: StateFlow<PortfolioProjectEntity?> = _selectedPortfolioProject.asStateFlow()

    // Global Search Query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Notification / Toast message
    private val _notification = MutableStateFlow<UiNotification?>(null)
    val notification: StateFlow<UiNotification?> = _notification.asStateFlow()

    // Sub-tab selectors
    private val _creativeStudioTab = MutableStateFlow(0) // 0: Scriptwriting, 1: Content Creation, 2: Creative Concepts, 3: Behind the Scenes
    val creativeStudioTab: StateFlow<Int> = _creativeStudioTab.asStateFlow()

    private val _mediaStudioTab = MutableStateFlow(0) // 0: All, 1: YouTube, 2: Instagram, 3: Featured, 4: Originals, 5: Cloud Drive
    val mediaStudioTab: StateFlow<Int> = _mediaStudioTab.asStateFlow()

    private val _adminActiveTab = MutableStateFlow(0)
    val adminActiveTab: StateFlow<Int> = _adminActiveTab.asStateFlow()

    // Database Flows
    val publishedScripts = repository.publishedScripts.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val allScripts = repository.allScripts.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val publishedContentSamples = repository.publishedContentSamples.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val allContentSamples = repository.allContentSamples.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val publishedMediaItems = repository.publishedMediaItems.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val allMediaItems = repository.allMediaItems.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val portfolioProjects = repository.portfolioProjects.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val campaigns = repository.campaigns.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val announcements = repository.announcements.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val allEnquiries = repository.allEnquiries.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val adminConfig = repository.adminConfig.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), null
    )

    // Admin Auth State
    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    private val _adminLoginError = MutableStateFlow<String?>(null)
    val adminLoginError: StateFlow<String?> = _adminLoginError.asStateFlow()

    // OTP Recovery State
    private val _otpState = MutableStateFlow<OtpRecoveryUiState>(OtpRecoveryUiState.Idle)
    val otpState: StateFlow<OtpRecoveryUiState> = _otpState.asStateFlow()

    // Last Submitted Enquiry Reference
    private val _lastEnquiryReference = MutableStateFlow<String?>(null)
    val lastEnquiryReference: StateFlow<String?> = _lastEnquiryReference.asStateFlow()

    init {
        viewModelScope.launch {
            repository.ensureAdminConfigInitialized()
        }
    }

    fun navigateTo(screen: AppScreen) {
        if (_currentScreen.value != screen) {
            _screenHistory.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun handleBack(): Boolean {
        if (_selectedScript.value != null) {
            _selectedScript.value = null
            return true
        }
        if (_selectedMediaItem.value != null) {
            _selectedMediaItem.value = null
            return true
        }
        if (_selectedPortfolioProject.value != null) {
            _selectedPortfolioProject.value = null
            return true
        }
        if (_screenHistory.isNotEmpty()) {
            val previous = _screenHistory.removeAt(_screenHistory.size - 1)
            _currentScreen.value = previous
            return true
        }
        if (_currentScreen.value != AppScreen.HOME && _currentScreen.value != AppScreen.SPLASH) {
            _currentScreen.value = AppScreen.HOME
            return true
        }
        return false
    }

    fun setCreativeStudioTab(tab: Int) {
        _creativeStudioTab.value = tab
    }

    fun setMediaStudioTab(tab: Int) {
        _mediaStudioTab.value = tab
    }

    fun setAdminActiveTab(tab: Int) {
        _adminActiveTab.value = tab
    }

    fun selectScript(script: ScriptItemEntity?) {
        _selectedScript.value = script
    }

    fun selectMediaItem(item: MediaStudioItemEntity?) {
        _selectedMediaItem.value = item
    }

    fun selectPortfolioProject(project: PortfolioProjectEntity?) {
        _selectedPortfolioProject.value = project
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearNotification() {
        _notification.value = null
    }

    fun showNotification(message: String, isError: Boolean = false) {
        _notification.value = UiNotification(message, isError)
    }

    // Enquiry Submission
    fun submitProjectEnquiry(
        name: String,
        email: String,
        phone: String,
        company: String,
        service: String,
        description: String,
        platform: String,
        budget: String,
        timeline: String,
        onSuccess: (String) -> Unit
    ) {
        if (name.isBlank() || email.isBlank() || phone.isBlank() || description.isBlank()) {
            showNotification("Please fill in all required fields (Name, Email, Mobile, Description)", true)
            return
        }

        viewModelScope.launch {
            val refNumber = "BRD-${SimpleDateFormat("yyyy", Locale.US).format(Date())}-${Random.nextInt(1000, 9999)}"
            val enquiry = EnquiryEntity(
                referenceId = refNumber,
                customerName = name.trim(),
                email = email.trim(),
                mobile = phone.trim(),
                companyName = company.trim().ifBlank { "Independent Brand" },
                serviceRequired = service,
                projectDescription = description.trim(),
                preferredPlatform = platform,
                budgetRange = budget,
                timeline = timeline,
                dateSubmitted = System.currentTimeMillis(),
                status = "New"
            )
            repository.submitEnquiry(enquiry)
            _lastEnquiryReference.value = refNumber
            showNotification("Enquiry submitted successfully! Ref: $refNumber")
            onSuccess(refNumber)
        }
    }

    fun updateEnquiryStatus(enquiry: EnquiryEntity, newStatus: String) {
        viewModelScope.launch {
            repository.updateEnquiryStatus(enquiry, newStatus)
            showNotification("Enquiry status updated to '$newStatus'")
        }
    }

    fun deleteEnquiry(enquiry: EnquiryEntity) {
        viewModelScope.launch {
            repository.deleteEnquiry(enquiry)
            showNotification("Enquiry deleted")
        }
    }

    // Admin Authentication
    fun loginAdmin(password: String) {
        viewModelScope.launch {
            val success = repository.verifyAdminLogin(password)
            if (success) {
                _isAdminLoggedIn.value = true
                _adminLoginError.value = null
                navigateTo(AppScreen.ADMIN_DASHBOARD)
                showNotification("Welcome back, Administrator")
            } else {
                _adminLoginError.value = "Invalid password. Access restricted to BrandIT Administrator."
            }
        }
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
        _currentScreen.value = AppScreen.HOME
        showNotification("Logged out from Admin Dashboard")
    }

    // OTP Password Recovery Flow
    fun requestOtp(email: String) {
        viewModelScope.launch {
            _otpState.value = OtpRecoveryUiState.Loading
            when (val result = repository.generateOtpForAdmin(email)) {
                is OtpRequestResult.Success -> {
                    _otpState.value = OtpRecoveryUiState.OtpSent(
                        maskedMobile = "+91 95802***77",
                        actualOtpForAdminTesting = result.otp
                    )
                    showNotification("6-digit recovery OTP dispatched to +91 9580289877")
                }
                is OtpRequestResult.Error -> {
                    _otpState.value = OtpRecoveryUiState.Error(result.message)
                    showNotification(result.message, true)
                }
            }
        }
    }

    fun verifyOtpAndAllowReset(enteredOtp: String) {
        viewModelScope.launch {
            val valid = repository.verifyOtp(enteredOtp)
            if (valid) {
                _otpState.value = OtpRecoveryUiState.Verified
                showNotification("OTP verified. Please set your new password.")
            } else {
                _otpState.value = OtpRecoveryUiState.Error("Invalid or expired OTP. Please verify and try again.")
            }
        }
    }

    fun resetPassword(newPass: String) {
        if (newPass.length < 6) {
            showNotification("Password must be at least 6 characters", true)
            return
        }
        viewModelScope.launch {
            repository.updateAdminPassword(newPass)
            _otpState.value = OtpRecoveryUiState.Success
            showNotification("Password reset successful! Please log in with your new credentials.")
        }
    }

    fun resetOtpState() {
        _otpState.value = OtpRecoveryUiState.Idle
    }

    // Admin Content Management Operations
    fun saveScript(script: ScriptItemEntity) {
        viewModelScope.launch {
            if (script.id == 0L) {
                repository.addScript(script)
                showNotification("New script created successfully")
            } else {
                repository.updateScript(script)
                showNotification("Script updated successfully")
            }
        }
    }

    fun toggleScriptPublish(script: ScriptItemEntity) {
        viewModelScope.launch {
            repository.updateScript(script.copy(isPublished = !script.isPublished))
            showNotification("Script publish status toggled")
        }
    }

    fun deleteScript(script: ScriptItemEntity) {
        viewModelScope.launch {
            repository.deleteScript(script)
            showNotification("Script removed")
        }
    }

    fun saveContentSample(sample: ContentSampleEntity) {
        viewModelScope.launch {
            if (sample.id == 0L) {
                repository.addContentSample(sample)
                showNotification("Content sample added")
            } else {
                repository.updateContentSample(sample)
                showNotification("Content sample updated")
            }
        }
    }

    fun deleteContentSample(sample: ContentSampleEntity) {
        viewModelScope.launch {
            repository.deleteContentSample(sample)
            showNotification("Content sample removed")
        }
    }

    fun saveMediaItem(item: MediaStudioItemEntity) {
        viewModelScope.launch {
            if (item.id == 0L) {
                repository.addMediaItem(item)
                showNotification("Media item added to BrandIT Studio")
            } else {
                repository.updateMediaItem(item)
                showNotification("Media item updated")
            }
        }
    }

    fun deleteMediaItem(item: MediaStudioItemEntity) {
        viewModelScope.launch {
            repository.deleteMediaItem(item)
            showNotification("Media item deleted")
        }
    }

    fun saveCampaign(campaign: CampaignEntity) {
        viewModelScope.launch {
            if (campaign.id == 0L) {
                repository.addCampaign(campaign)
                showNotification("Campaign record created")
            } else {
                repository.updateCampaign(campaign)
                showNotification("Campaign record updated")
            }
        }
    }

    fun deleteCampaign(campaign: CampaignEntity) {
        viewModelScope.launch {
            repository.deleteCampaign(campaign)
            showNotification("Campaign removed")
        }
    }

    fun savePortfolioProject(project: PortfolioProjectEntity) {
        viewModelScope.launch {
            repository.addPortfolioProject(project)
            showNotification("Portfolio project published")
        }
    }

    fun deletePortfolioProject(project: PortfolioProjectEntity) {
        viewModelScope.launch {
            repository.deletePortfolioProject(project)
            showNotification("Portfolio project removed")
        }
    }
}

sealed class OtpRecoveryUiState {
    object Idle : OtpRecoveryUiState()
    object Loading : OtpRecoveryUiState()
    data class OtpSent(val maskedMobile: String, val actualOtpForAdminTesting: String) : OtpRecoveryUiState()
    object Verified : OtpRecoveryUiState()
    object Success : OtpRecoveryUiState()
    data class Error(val message: String) : OtpRecoveryUiState()
}
