package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enquiries")
data class EnquiryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val referenceId: String,
    val customerName: String,
    val email: String,
    val mobile: String,
    val companyName: String,
    val serviceRequired: String,
    val projectDescription: String,
    val preferredPlatform: String,
    val budgetRange: String,
    val timeline: String,
    val dateSubmitted: Long = System.currentTimeMillis(),
    val status: String = "New" // New, Contacted, Discussion, In Progress, Completed, Archived
)

@Entity(tableName = "scripts")
data class ScriptItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String,
    val description: String,
    val hook: String,
    val excerpt: String,
    val fullScript: String,
    val language: String = "English",
    val datePublished: String,
    val relatedVideoUrl: String = "",
    val isFeatured: Boolean = false,
    val isPublished: Boolean = true
)

@Entity(tableName = "content_samples")
data class ContentSampleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String,
    val description: String,
    val platform: String, // Instagram, YouTube, LinkedIn, Facebook
    val mediaType: String, // Carousel, Reel, Infographic, Poster
    val mediaUrl: String,
    val caption: String,
    val isFeatured: Boolean = false,
    val isPublished: Boolean = true
)

@Entity(tableName = "media_items")
data class MediaStudioItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val platform: String, // YouTube, Instagram, Google Drive, BrandIT Originals
    val videoUrl: String,
    val thumbnailUrl: String,
    val duration: String,
    val viewsCount: String,
    val publishedDate: String,
    val category: String,
    val driveFolder: String = "",
    val description: String = "",
    val isFeatured: Boolean = false,
    val isPublished: Boolean = true
)

@Entity(tableName = "portfolio_projects")
data class PortfolioProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val clientName: String,
    val category: String,
    val description: String,
    val objectives: String,
    val creativeApproach: String,
    val finalOutput: String,
    val datePublished: String,
    val isFeatured: Boolean = false
)

@Entity(tableName = "campaigns")
data class CampaignEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val platform: String,
    val objective: String,
    val category: String,
    val status: String, // Active, Planned, Completed
    val startDate: String,
    val endDate: String,
    val description: String,
    val performanceSummary: String
)

@Entity(tableName = "announcements")
data class AnnouncementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val category: String,
    val date: String,
    val isPinned: Boolean = false
)

@Entity(tableName = "admin_config")
data class AdminConfigEntity(
    @PrimaryKey
    val id: Long = 1,
    val adminEmail: String = "brandit00me@gmail.com",
    val recoveryMobile: String = "+91 9580289877",
    val passwordHash: String = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", // default initial "password" (SHA-256)
    val isFirstTimeSetupDone: Boolean = false,
    val activeOtp: String? = null,
    val otpExpiryTime: Long = 0L
)
