package com.example.data.local

import com.example.data.model.*

object SeedData {

    val adminConfig = AdminConfigEntity(
        id = 1,
        adminEmail = "brandit00me@gmail.com",
        recoveryMobile = "+91 9580289877",
        // Default password "admin123" SHA-256: 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
        passwordHash = "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9",
        isFirstTimeSetupDone = false
    )

    val scripts = listOf(
        ScriptItemEntity(
            title = "The Psychology of Luxury: Why We Buy What We Don't Need",
            category = "YouTube Video Scriptwriting",
            description = "A deep-dive investigative script exploring dopamine triggers, exclusivity marketing, and brand perception in luxury industries.",
            hook = "You didn't buy that watch to tell the time. Your smartphone already does that to the millisecond. So why did you spend $8,000?",
            excerpt = "[SCENE START - MACRO CLOSE-UP OF TICKING TOURBILLON]\n\nNARRATOR (V.O.):\nYou didn't buy that watch to tell time. You bought it so everyone in the room knows what your time is worth.\n\n[CUT TO: ARCHIVAL FOOTAGE - 5TH AVENUE 1964]\nIn 1948, De Beers invented the modern engagement ring with a simple sentence: 'A Diamond is Forever.' It wasn't geology. It was cognitive engineering.",
            fullScript = "[TITLE: THE PSYCHOLOGY OF LUXURY]\n\n[ACT 1: THE INVISIBLE TAX]\n(Visual: Cinematic slow motion, moody low-key studio lighting with neon cyan rims)\n\nNARRATOR (V.O.):\nLook closely at this box. Matte black cardstock, embossed foil typography, 45-degree chamfered edges. Before the customer even sees the product, their neurological reward pathway is already flooded with dopamine.\n\n[ACT 2: SCARCITY AS ART]\n(Visual: Fast jump cuts of waitlists, VIP lounges, velvet ropes)\n\nNARRATOR (V.O.):\nReal luxury isn't about quality. Quality is the baseline ticket to enter. Luxury is about friction. The harder it is to obtain, the more identity it confers upon the wearer.\n\n[CALL TO ACTION]\nIf your brand is still competing on price, you're playing the wrong game. Let's write the story that makes your vision irreplaceable. Welcome to BrandIT.",
            language = "English",
            datePublished = "Oct 2026",
            relatedVideoUrl = "https://www.youtube.com/@BrandIT_for",
            isFeatured = true,
            isPublished = true
        ),
        ScriptItemEntity(
            title = "Zero to 100k Followers: The 3-Second Viral Hook Blueprint",
            category = "Instagram Reel Scriptwriting",
            description = "High-retention short-form video script engineered for maximum watch time, dynamic micro-hooks, and comment loop triggers.",
            hook = "Stop scrolling. If your first 3 seconds don't provoke curiosity or disagreement, your video is already dead.",
            excerpt = "[FAST ZOOM ON SPEAKER - METALLIC GLITCH SOUND]\n\nSPEAKER:\nMost creators spend 4 hours editing a Reel that 98% of people swipe away from in 1.4 seconds. Here is the 'Contrarian Opening' rule used by BrandIT to generate 1.2M impressions without ad spend.",
            fullScript = "[AUDIO: PUNCHY BASS DROP]\n[TEXT ON SCREEN: 99% OF REELS FAIL HERE]\n\nSPEAKER (Direct to lens, intense energy):\nStop scrolling. If you're creating content in 2026 and your watch time graph drops like a cliff at second two, you're making this fatal error.\n\nYou're introducing yourself before you give value. Nobody cares who you are until they know what you can solve for them.\n\nHere's the 3-part BrandIT formula:\n1. The Negative Pattern Interrupt\n2. The High-Stakes Thesis\n3. The Irreversible Solution\n\nSave this Reel and write your next hook in the comments.",
            language = "English",
            datePublished = "Nov 2026",
            relatedVideoUrl = "https://www.instagram.com/brandit_me?psln=MXhrN2F0aGxkMWlncw==",
            isFeatured = true,
            isPublished = true
        ),
        ScriptItemEntity(
            title = "Titanium Future: Quantum EV Launch Commercial",
            category = "Advertisement Scriptwriting",
            description = "High-energy cinematic ad commercial script written for television, YouTube bumper, and digital billboard placements.",
            hook = "Silence is not the absence of power. Silence is power perfected.",
            excerpt = "[SFX: HIGH-FREQUENCY ELECTRIC HUM BUILDING UP]\n\nWIDE AERIAL SHOT - ICELANDIC BLACK SAND BEACH.\nA solitary aerodynamic silhouette cuts through mist, leaving glowing ion trails.\n\nSUPER: SILENCE IS POWER PERFECTED.\n\nVOICEOVER (Deep, commanding resonance):\nWe didn't design the next generation to be faster. We designed it to make everything before it feel obsolete.",
            fullScript = "[COMMERCIAL SCRIPT: TITANIUM FUTURE - 45s TVC]\n\n[00:00 - 00:05] DARKNESS. SFX: Heartbeat syncs with pulse lights.\n[00:06 - 00:15] Drone sweeps over winding mountain pass at dawn. The chassis reflects dawn's amber light.\nVO: 'Speed is easy. Control is discipline. Vision is rare.'\n[00:16 - 00:30] Rapid cuts: hand gripping carbon steering wheel, digital telemetry booting, tire gripping wet asphalt.\n[00:31 - 00:45] Vehicle halts at the edge of an urban skyline overlooking midnight neon.\nSUPER: BRANDIT PRODUCTION x TITANIUM MOTORS\nVO: 'Your vision. Our creation.'",
            language = "English",
            datePublished = "Nov 2026",
            relatedVideoUrl = "https://www.youtube.com/@BrandIT_for",
            isFeatured = true,
            isPublished = true
        ),
        ScriptItemEntity(
            title = "The Architecture of Tomorrow: Green Energy Docuseries",
            category = "Documentary Scriptwriting",
            description = "Narrative documentary script blending historical context, scientific breakthroughs, and human resilience.",
            hook = "500 miles above the Arctic circle, engineers are racing against the sunrise to build humanity's thermal vault.",
            excerpt = "[ARCHIVAL STILLS - SVALBARD 1920]\n\nNARRATOR:\nFor a century, humanity extracted energy from beneath the crust. Today, beneath that same permafrost, we are storing the keys to our survival.",
            fullScript = "[ACT 1: THE TUNDRA HORIZON]\nFootsteps crunching on deep ice. Frost misting the camera lens.\nNARRATOR: When the permafrost melts, history unlocks. But what if the solution wasn't finding more energy, but rethinking how energy remembers its origin?\nInterview: Dr. Sarah Lin, Lead Cryo-Architect.\n'We aren't fighting nature. We're asking nature for a blueprint.'",
            language = "English",
            datePublished = "Oct 2026",
            relatedVideoUrl = "https://www.youtube.com/@BrandIT_for",
            isFeatured = false,
            isPublished = true
        )
    )

    val contentSamples = listOf(
        ContentSampleEntity(
            title = "Brand Evolution: The 5 Stages of Digital Rebranding",
            category = "Carousel Content",
            description = "10-slide interactive educational carousel breaking down visual identity, voice cohesion, typography scales, and campaign rollout.",
            platform = "Instagram",
            mediaType = "Carousel",
            mediaUrl = "https://www.instagram.com/brandit_me?psln=MXhrN2F0aGxkMWlncw==",
            caption = "Rebranding is not just changing your logo. It is transforming the mental real estate you own in your customer's mind. Slide 4 is where 90% of brands fail.",
            isFeatured = true,
            isPublished = true
        ),
        ContentSampleEntity(
            title = "Storytelling Masterclass: The Hero's Journey in 60 Seconds",
            category = "Reels and Shorts",
            description = "Fast-paced kinetic typography reel illustrating how consumer brands position the customer as Luke Skywalker and the brand as Yoda.",
            platform = "Instagram",
            mediaType = "Reel",
            mediaUrl = "https://www.instagram.com/brandit_me?psln=MXhrN2F0aGxkMWlncw==",
            caption = "You are not the hero of your brand story. Your customer is. When you master this shift, your conversions 3x overnight.",
            isFeatured = true,
            isPublished = true
        ),
        ContentSampleEntity(
            title = "2026 Video Production Benchmark Report",
            category = "Infographic Content",
            description = "Sleek dark-mode infographic detailing retention curves, audio design impact, and pacing analysis across 500+ commercial video projects.",
            platform = "LinkedIn",
            mediaType = "Infographic",
            mediaUrl = "https://www.youtube.com/@BrandIT_for",
            caption = "We analyzed 500 high-performing promotional videos. The verdict? Sound design contributes 60% of emotional retention. Full infographic breakdown enclosed.",
            isFeatured = false,
            isPublished = true
        ),
        ContentSampleEntity(
            title = "Cinematic Noir: Cyberpunk Promotional Posters",
            category = "Creative Posters",
            description = "Series of 3 high-concept promotional posters with neon typography, volumetric smoke, and chromatic aberration.",
            platform = "Instagram",
            mediaType = "Poster",
            mediaUrl = "https://www.instagram.com/brandit_me?psln=MXhrN2F0aGxkMWlncw==",
            caption = "Concept art for our upcoming digital series. Developed in-house at the BrandIT Design Studio. Your vision. Our creation.",
            isFeatured = true,
            isPublished = true
        )
    )

    val mediaItems = listOf(
        MediaStudioItemEntity(
            title = "BrandIT Showreel 2026 | Cinematic Vision & Creative Direction",
            platform = "YouTube",
            videoUrl = "https://www.youtube.com/@BrandIT_for",
            thumbnailUrl = "https://images.unsplash.com/photo-1574717024653-61fd2cf4d44d?w=800&q=80",
            duration = "02:45",
            viewsCount = "14.2K",
            publishedDate = "2026-10-18",
            category = "Cinematic Showreel",
            description = "Official BrandIT creative showreel demonstrating video production, commercial color grading, 3D motion graphics, and narrative editing.",
            isFeatured = true,
            isPublished = true
        ),
        MediaStudioItemEntity(
            title = "How Great Scripts Turn Small Brands into Giants",
            platform = "YouTube",
            videoUrl = "https://www.youtube.com/@BrandIT_for",
            thumbnailUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?w=800&q=80",
            duration = "12:30",
            viewsCount = "8.9K",
            publishedDate = "2026-10-25",
            category = "Educational Masterclass",
            description = "Behind the scenes with BrandIT scriptwriters explaining structure, emotional hooks, and audience psychology.",
            isFeatured = true,
            isPublished = true
        ),
        MediaStudioItemEntity(
            title = "The Secret Behind Viral Video Pacing | Editing Breakdown",
            platform = "Instagram",
            videoUrl = "https://www.instagram.com/brandit_me?psln=MXhrN2F0aGxkMWlncw==",
            thumbnailUrl = "https://images.unsplash.com/photo-1536240478700-b869070f9279?w=800&q=80",
            duration = "00:58",
            viewsCount = "45.1K",
            publishedDate = "2026-11-01",
            category = "Reel Breakdown",
            description = "Why jump cuts without dynamic sound effects fail. Watch how BrandIT constructs micro-hooks to retain 85% of viewers past 30 seconds.",
            isFeatured = true,
            isPublished = true
        ),
        MediaStudioItemEntity(
            title = "BrandIT Studio Workflow: Script to Final Grade",
            platform = "BrandIT Originals",
            videoUrl = "https://www.youtube.com/@BrandIT_for",
            thumbnailUrl = "https://images.unsplash.com/photo-1492691527719-9d1e07e534b4?w=800&q=80",
            duration = "05:15",
            viewsCount = "19.8K",
            publishedDate = "2026-11-04",
            category = "Behind The Scenes",
            description = "A raw, unfiltered look at our multi-camera shoot, DaVinci Resolve color suites, and voiceover recording stages.",
            isFeatured = true,
            isPublished = true
        ),
        MediaStudioItemEntity(
            title = "BrandIT Media Cloud: 01 Video Production Master Vault",
            platform = "Google Drive",
            videoUrl = "https://drive.google.com",
            thumbnailUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800&q=80",
            duration = "Cloud Folder",
            viewsCount = "Protected",
            publishedDate = "2026-11-05",
            category = "Cloud Studio",
            driveFolder = "01 Video Production",
            description = "Master 4K ProRes deliverables, color-graded footage, and project archives synced with BrandIT Cloud Studio.",
            isFeatured = false,
            isPublished = true
        ),
        MediaStudioItemEntity(
            title = "BrandIT Media Cloud: 06 Scriptwriting Vault",
            platform = "Google Drive",
            videoUrl = "https://drive.google.com",
            thumbnailUrl = "https://images.unsplash.com/photo-1455390582262-044cdead277a?w=800&q=80",
            duration = "Cloud Folder",
            viewsCount = "Protected",
            publishedDate = "2026-11-05",
            category = "Cloud Studio",
            driveFolder = "06 Scriptwriting",
            description = "Approved production scripts, storyboards, voiceover cue sheets, and campaign copy documents.",
            isFeatured = false,
            isPublished = true
        )
    )

    val portfolioProjects = listOf(
        PortfolioProjectEntity(
            title = "Zenith Robotics: Next-Gen Automation Rebrand",
            clientName = "Zenith AI Systems",
            category = "Brand Identity & Video Production",
            description = "Full brand identity overhaul including 3D product animations, cinematic launch video, and YouTube documentary scriptwriting.",
            objectives = "Reposition Zenith from an industrial hardware vendor to a futuristic intelligent robotics brand with global appeal.",
            creativeApproach = "Crafted an evocative narrative juxtaposing organic human movement with sleek titanium kinetic art. Created a custom soundtrack and voiceover.",
            finalOutput = "3-minute cinematic 4K launch film, 15 social media reels, 6 tech explainer scripts, and comprehensive brand guideline book.",
            datePublished = "Oct 2026",
            isFeatured = true
        ),
        PortfolioProjectEntity(
            title = "Neon Velocity: High-Performance Streetwear Campaign",
            clientName = "Velocity Apparel",
            category = "Social Media & Ad Campaigns",
            description = "30-day viral multi-platform blitz across Instagram Reels and YouTube Shorts featuring kinetic typography and high-energy music sync.",
            objectives = "Drive 500k impressions, sell out the limited drop within 48 hours, and increase brand search volume by 250%.",
            creativeApproach = "Dark cyberpunk visuals, anamorphic lens flares, and fast-paced scripts focusing on streetwear culture and individuality.",
            finalOutput = "Collection sold out in 19 hours; 1.4 million total organic impressions across social platforms.",
            datePublished = "Sep 2026",
            isFeatured = true
        ),
        PortfolioProjectEntity(
            title = "Echo Financial: Demystifying Algorithmic Trading",
            clientName = "Echo Wealth Technologies",
            category = "Scriptwriting & Content Strategy",
            description = "Comprehensive educational content series with 12 YouTube scripts, 24 educational carousels, and investor pitch scripts.",
            objectives = "Simplify complex quantitative hedge fund algorithms for retail investors without losing credibility.",
            creativeApproach = "Used relatable analogies, interactive graphics, and structured three-act narrative frameworks for each script.",
            finalOutput = "YouTube channel grew from 2,000 to 85,000 subscribers in 90 days with an average retention rate of 62%.",
            datePublished = "Aug 2026",
            isFeatured = false
        )
    )

    val campaigns = listOf(
        CampaignEntity(
            title = "Quantum Shift: Global Tech Summits 2026",
            platform = "YouTube & Meta Ads",
            objective = "High-intent delegate registrations and brand prestige positioning",
            category = "Commercial Advertising",
            status = "Active",
            startDate = "2026-10-01",
            endDate = "2026-12-15",
            description = "Multi-tier video ads targeting C-level executives, founders, and venture capitalists across North America and Europe.",
            performanceSummary = "4.2x ROAS, 18,400 qualified attendee inquiries, 78% video completion rate on YouTube bumper ads."
        ),
        CampaignEntity(
            title = "BrandIT Creators Lab: Organic Reels Sprint",
            platform = "Instagram",
            objective = "Organic community growth, creator authority, and client acquisition",
            category = "Social Media Growth",
            status = "Active",
            startDate = "2026-11-01",
            endDate = "2026-11-30",
            description = "Daily 45-second high-production reels teaching visual storytelling, scripting secrets, and camera techniques.",
            performanceSummary = "+320% profile interactions, 45 new project inquiries submitted through BrandIT app in 14 days."
        )
    )

    val announcements = listOf(
        AnnouncementEntity(
            title = "Welcome to the BrandIT Platform",
            content = "BrandIT has launched our unified Android creative and business solutions app. Explore our Scriptwriting Studio, Content Creation gallery, Media Studio, and submit project briefs directly to our creative directors.",
            category = "Product Release",
            date = "Nov 2026",
            isPinned = true
        ),
        AnnouncementEntity(
            title = "New Episode Premiering on YouTube @BrandIT_for",
            content = "Tune in to our official YouTube channel for an in-depth creative masterclass on high-conversion video hooks and cinematic color science.",
            category = "Media Announcement",
            date = "Nov 2026",
            isPinned = false
        )
    )

    val initialEnquiries = listOf(
        EnquiryEntity(
            referenceId = "BRD-2026-1001",
            customerName = "Marcus Vance",
            email = "m.vance@vancemedia.com",
            mobile = "+1 415 892 4432",
            companyName = "Vance Media Labs",
            serviceRequired = "Scriptwriting",
            projectDescription = "Need 8 high-retention YouTube video scripts on artificial intelligence developments and hardware reviews.",
            preferredPlatform = "YouTube",
            budgetRange = "$2,500 - $5,000",
            timeline = "2 - 4 Weeks",
            dateSubmitted = System.currentTimeMillis() - 86400000L * 2,
            status = "In Progress"
        ),
        EnquiryEntity(
            referenceId = "BRD-2026-1002",
            customerName = "Priya Sharma",
            email = "priya@novabrand.in",
            mobile = "+91 98200 12345",
            companyName = "Nova Health & Wellness",
            serviceRequired = "Video Production",
            projectDescription = "Cinematic brand commercial for our D2C beverage launch, including 30s TVC and 10 Instagram cutdowns.",
            preferredPlatform = "Instagram & YouTube",
            budgetRange = "$5,000 - $10,000",
            timeline = "1 Month",
            dateSubmitted = System.currentTimeMillis() - 86400000L * 5,
            status = "Discussion"
        )
    )
}
