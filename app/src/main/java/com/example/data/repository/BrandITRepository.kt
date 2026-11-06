package com.example.data.repository

import com.example.data.local.BrandITDao
import com.example.data.local.SeedData
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest
import kotlin.random.Random

class BrandITRepository(private val dao: BrandITDao) {

    // Enquiries
    val allEnquiries: Flow<List<EnquiryEntity>> = dao.getAllEnquiries()

    suspend fun submitEnquiry(enquiry: EnquiryEntity): Long {
        return dao.insertEnquiry(enquiry)
    }

    suspend fun updateEnquiryStatus(enquiry: EnquiryEntity, newStatus: String) {
        dao.updateEnquiry(enquiry.copy(status = newStatus))
    }

    suspend fun deleteEnquiry(enquiry: EnquiryEntity) {
        dao.deleteEnquiry(enquiry)
    }

    // Scripts
    val publishedScripts: Flow<List<ScriptItemEntity>> = dao.getPublishedScripts()
    val allScripts: Flow<List<ScriptItemEntity>> = dao.getAllScripts()

    suspend fun addScript(script: ScriptItemEntity): Long {
        return dao.insertScript(script)
    }

    suspend fun updateScript(script: ScriptItemEntity) {
        dao.updateScript(script)
    }

    suspend fun deleteScript(script: ScriptItemEntity) {
        dao.deleteScript(script)
    }

    // Content Samples
    val publishedContentSamples: Flow<List<ContentSampleEntity>> = dao.getPublishedContentSamples()
    val allContentSamples: Flow<List<ContentSampleEntity>> = dao.getAllContentSamples()

    suspend fun addContentSample(sample: ContentSampleEntity): Long {
        return dao.insertContentSample(sample)
    }

    suspend fun updateContentSample(sample: ContentSampleEntity) {
        dao.updateContentSample(sample)
    }

    suspend fun deleteContentSample(sample: ContentSampleEntity) {
        dao.deleteContentSample(sample)
    }

    // Media Studio
    val publishedMediaItems: Flow<List<MediaStudioItemEntity>> = dao.getPublishedMediaItems()
    val allMediaItems: Flow<List<MediaStudioItemEntity>> = dao.getAllMediaItems()

    suspend fun addMediaItem(item: MediaStudioItemEntity): Long {
        return dao.insertMediaItem(item)
    }

    suspend fun updateMediaItem(item: MediaStudioItemEntity) {
        dao.updateMediaItem(item)
    }

    suspend fun deleteMediaItem(item: MediaStudioItemEntity) {
        dao.deleteMediaItem(item)
    }

    // Portfolio
    val portfolioProjects: Flow<List<PortfolioProjectEntity>> = dao.getAllPortfolioProjects()

    suspend fun addPortfolioProject(project: PortfolioProjectEntity): Long {
        return dao.insertPortfolioProject(project)
    }

    suspend fun deletePortfolioProject(project: PortfolioProjectEntity) {
        dao.deletePortfolioProject(project)
    }

    // Campaigns
    val campaigns: Flow<List<CampaignEntity>> = dao.getAllCampaigns()

    suspend fun addCampaign(campaign: CampaignEntity): Long {
        return dao.insertCampaign(campaign)
    }

    suspend fun updateCampaign(campaign: CampaignEntity) {
        dao.updateCampaign(campaign)
    }

    suspend fun deleteCampaign(campaign: CampaignEntity) {
        dao.deleteCampaign(campaign)
    }

    // Announcements
    val announcements: Flow<List<AnnouncementEntity>> = dao.getAllAnnouncements()

    suspend fun addAnnouncement(announcement: AnnouncementEntity): Long {
        return dao.insertAnnouncement(announcement)
    }

    // Admin & Auth
    val adminConfig: Flow<AdminConfigEntity?> = dao.getAdminConfig()

    suspend fun ensureAdminConfigInitialized() {
        val existing = dao.getAdminConfigSync()
        if (existing == null) {
            dao.setAdminConfig(SeedData.adminConfig)
        }
    }

    suspend fun verifyAdminLogin(password: String): Boolean {
        ensureAdminConfigInitialized()
        val config = dao.getAdminConfigSync() ?: return false
        val hash = hashPassword(password)
        return config.passwordHash.equals(hash, ignoreCase = true)
    }

    suspend fun updateAdminPassword(newPassword: String): Boolean {
        val config = dao.getAdminConfigSync() ?: return false
        val newHash = hashPassword(newPassword)
        dao.setAdminConfig(
            config.copy(
                passwordHash = newHash,
                isFirstTimeSetupDone = true,
                activeOtp = null,
                otpExpiryTime = 0L
            )
        )
        return true
    }

    // OTP Recovery Process
    suspend fun generateOtpForAdmin(email: String): OtpRequestResult {
        ensureAdminConfigInitialized()
        val config = dao.getAdminConfigSync()
            ?: return OtpRequestResult.Error("Admin configuration not found")

        if (!config.adminEmail.equals(email.trim(), ignoreCase = true)) {
            return OtpRequestResult.Error("Unauthorized email. Only the official administrator can request recovery.")
        }

        // Generate 6-digit OTP
        val otpCode = String.format("%06d", Random.nextInt(100000, 999999))
        val expiryTime = System.currentTimeMillis() + (5 * 60 * 1000) // 5 minutes

        dao.setAdminConfig(
            config.copy(
                activeOtp = otpCode,
                otpExpiryTime = expiryTime
            )
        )

        return OtpRequestResult.Success(
            otp = otpCode,
            mobile = config.recoveryMobile,
            expiryMinutes = 5
        )
    }

    suspend fun verifyOtp(enteredOtp: String): Boolean {
        val config = dao.getAdminConfigSync() ?: return false
        val currentTime = System.currentTimeMillis()
        if (config.activeOtp == null || config.otpExpiryTime < currentTime) {
            return false
        }
        return config.activeOtp == enteredOtp.trim()
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}

sealed class OtpRequestResult {
    data class Success(val otp: String, val mobile: String, val expiryMinutes: Int) : OtpRequestResult()
    data class Error(val message: String) : OtpRequestResult()
}
