package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        EnquiryEntity::class,
        ScriptItemEntity::class,
        ContentSampleEntity::class,
        MediaStudioItemEntity::class,
        PortfolioProjectEntity::class,
        CampaignEntity::class,
        AnnouncementEntity::class,
        AdminConfigEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BrandITDatabase : RoomDatabase() {

    abstract fun brandITDao(): BrandITDao

    companion object {
        @Volatile
        private var INSTANCE: BrandITDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BrandITDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BrandITDatabase::class.java,
                    "brandit_master_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(BrandITDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class BrandITDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.brandITDao())
                    }
                }
            }

            private suspend fun populateDatabase(dao: BrandITDao) {
                dao.setAdminConfig(SeedData.adminConfig)
                dao.insertAllScripts(SeedData.scripts)
                dao.insertAllContentSamples(SeedData.contentSamples)
                dao.insertAllMediaItems(SeedData.mediaItems)
                dao.insertAllPortfolioProjects(SeedData.portfolioProjects)
                dao.insertAllCampaigns(SeedData.campaigns)
                dao.insertAllAnnouncements(SeedData.announcements)
                for (enquiry in SeedData.initialEnquiries) {
                    dao.insertEnquiry(enquiry)
                }
            }
        }
    }
}
