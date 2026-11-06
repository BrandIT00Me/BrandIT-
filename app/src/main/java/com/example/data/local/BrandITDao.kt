package com.example.data.local

import androidx.room.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BrandITDao {

    // Enquiries
    @Query("SELECT * FROM enquiries ORDER BY dateSubmitted DESC")
    fun getAllEnquiries(): Flow<List<EnquiryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnquiry(enquiry: EnquiryEntity): Long

    @Update
    suspend fun updateEnquiry(enquiry: EnquiryEntity)

    @Delete
    suspend fun deleteEnquiry(enquiry: EnquiryEntity)

    // Scripts
    @Query("SELECT * FROM scripts WHERE isPublished = 1 ORDER BY id DESC")
    fun getPublishedScripts(): Flow<List<ScriptItemEntity>>

    @Query("SELECT * FROM scripts ORDER BY id DESC")
    fun getAllScripts(): Flow<List<ScriptItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScript(script: ScriptItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllScripts(scripts: List<ScriptItemEntity>)

    @Update
    suspend fun updateScript(script: ScriptItemEntity)

    @Delete
    suspend fun deleteScript(script: ScriptItemEntity)

    // Content Samples
    @Query("SELECT * FROM content_samples WHERE isPublished = 1 ORDER BY id DESC")
    fun getPublishedContentSamples(): Flow<List<ContentSampleEntity>>

    @Query("SELECT * FROM content_samples ORDER BY id DESC")
    fun getAllContentSamples(): Flow<List<ContentSampleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContentSample(sample: ContentSampleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllContentSamples(samples: List<ContentSampleEntity>)

    @Update
    suspend fun updateContentSample(sample: ContentSampleEntity)

    @Delete
    suspend fun deleteContentSample(sample: ContentSampleEntity)

    // Media Items
    @Query("SELECT * FROM media_items WHERE isPublished = 1 ORDER BY id DESC")
    fun getPublishedMediaItems(): Flow<List<MediaStudioItemEntity>>

    @Query("SELECT * FROM media_items ORDER BY id DESC")
    fun getAllMediaItems(): Flow<List<MediaStudioItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaItem(item: MediaStudioItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMediaItems(items: List<MediaStudioItemEntity>)

    @Update
    suspend fun updateMediaItem(item: MediaStudioItemEntity)

    @Delete
    suspend fun deleteMediaItem(item: MediaStudioItemEntity)

    // Portfolio
    @Query("SELECT * FROM portfolio_projects ORDER BY id DESC")
    fun getAllPortfolioProjects(): Flow<List<PortfolioProjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPortfolioProject(project: PortfolioProjectEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPortfolioProjects(projects: List<PortfolioProjectEntity>)

    @Delete
    suspend fun deletePortfolioProject(project: PortfolioProjectEntity)

    // Campaigns
    @Query("SELECT * FROM campaigns ORDER BY id DESC")
    fun getAllCampaigns(): Flow<List<CampaignEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCampaign(campaign: CampaignEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCampaigns(campaigns: List<CampaignEntity>)

    @Update
    suspend fun updateCampaign(campaign: CampaignEntity)

    @Delete
    suspend fun deleteCampaign(campaign: CampaignEntity)

    // Announcements
    @Query("SELECT * FROM announcements ORDER BY isPinned DESC, id DESC")
    fun getAllAnnouncements(): Flow<List<AnnouncementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncement(announcement: AnnouncementEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAnnouncements(announcements: List<AnnouncementEntity>)

    // Admin Config
    @Query("SELECT * FROM admin_config WHERE id = 1")
    fun getAdminConfig(): Flow<AdminConfigEntity?>

    @Query("SELECT * FROM admin_config WHERE id = 1")
    suspend fun getAdminConfigSync(): AdminConfigEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAdminConfig(config: AdminConfigEntity)
}
