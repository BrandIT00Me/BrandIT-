package com.example

import com.example.data.local.SeedData
import com.example.data.model.EnquiryEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleRobolectricTest {

    @Test
    fun `verify brandit seed data and identity`() {
        assertEquals("brandit00me@gmail.com", SeedData.adminConfig.adminEmail)
        assertEquals("+91 9580289877", SeedData.adminConfig.recoveryMobile)
        assertTrue(SeedData.scripts.isNotEmpty())
        assertTrue(SeedData.mediaItems.isNotEmpty())
        assertTrue(SeedData.portfolioProjects.isNotEmpty())
    }

    @Test
    fun `verify enquiry entity creation`() {
        val enquiry = EnquiryEntity(
            referenceId = "BRD-2026-9999",
            customerName = "Test Client",
            email = "client@example.com",
            mobile = "+91 9876543210",
            companyName = "Apex Studio",
            serviceRequired = "Scriptwriting",
            projectDescription = "Need commercial YouTube script",
            preferredPlatform = "YouTube",
            budgetRange = "$2,500 - $5,000",
            timeline = "2 - 4 Weeks"
        )
        assertNotNull(enquiry.referenceId)
        assertEquals("New", enquiry.status)
        assertEquals("Scriptwriting", enquiry.serviceRequired)
    }
}
