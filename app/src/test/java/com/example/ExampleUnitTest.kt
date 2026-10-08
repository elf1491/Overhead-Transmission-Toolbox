package com.example

import com.example.model.*
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testCatenaryCalculation() {
        val result = LineDesignEngine.calculateCatenary(
            spanLength = 800.0,
            conductorWeightPerUnit = 1.094, // Drake
            horizontalTension = 6300.0,
            supportHeight = 65.0
        )
        // Sag S = w * L^2 / (8 * H) approx = 1.094 * 640000 / (8 * 6300) ~ 13.89 ft
        assertTrue("Sag should be approx 13.9 ft", result.sag in 13.0..15.0)
        assertTrue("Min ground clearance should be approx 51 ft", result.minGroundClearance in 50.0..52.0)
        assertTrue("Max tension should exceed horizontal tension", result.maxTension > 6300.0)
    }

    @Test
    fun testRulingSpanCalculation() {
        val spans = listOf(500.0, 700.0, 900.0)
        val rulingSpan = LineDesignEngine.calculateRulingSpan(spans)
        assertTrue("Ruling span should be between 700 and 800 ft", rulingSpan in 700.0..800.0)
    }

    @Test
    fun testBlowoutCalculation() {
        val blowout = LineDesignEngine.calculateBlowout(
            conductorDiameterInches = 1.108,
            conductorWeightLbPerFt = 1.094,
            windSpeedMph = 60.0,
            spanSagFt = 20.0
        )
        assertTrue("Blowout angle should be positive", blowout.blowoutAngleDegrees > 15.0)
        assertTrue("Displacement should be positive", blowout.horizontalDisplacementFt > 5.0)
    }

    @Test
    fun testIeee738Ampacity() {
        val ampacity = LineDesignEngine.calculateAmpacityIeee738(
            conductorDiameterMm = 28.1,
            conductorResistance75cOhmPerKm = 0.072,
            maxAllowableTempC = 75.0,
            ambientTempC = 35.0,
            windSpeedMetersPerSec = 0.61,
            solarIrradianceWPerM2 = 1000.0
        )
        assertTrue("Drake ampacity at 75C should be > 350 A", ampacity.ampacityAmperes > 350.0)
        assertTrue("3-phase 230kV MVA rating should be > 100 MVA", ampacity.mvaRating3Phase230kV > 100.0)
    }

    @Test
    fun testCorrosionZincLife() {
        val result = CorrosionEngine.calculateGalvanizingLife(
            coatingThicknessUm = 85.0,
            corrosivityCategory = IsoCorrosivityCategory.C3
        )
        // In C3, zinc loss is 1.5 um/yr => 85 / 1.5 ~ 56.6 years
        assertTrue("Estimated years to steel exposure should be ~56 years", result.estimatedYearsToSteelExposure in 50.0..60.0)
    }

    @Test
    fun testWoodPoleStrength() {
        val result = InspectionEngine.calculateWoodPoleCapacity(
            originalCircumferenceInches = 42.0,
            soundShellThicknessInches = 3.5,
            groundLineDecayDepthInches = 0.5
        )
        assertTrue("Remaining moment capacity should be > 50%", result.remainingMomentCapacityPercent > 50.0)
    }

    @Test
    fun testFootingResistanceAndBackflashover() {
        val footing = LightningGroundingEngine.calculateFootingResistance(
            soilResistivityOhmM = 250.0,
            numberOfRods = 4,
            rodLengthMeters = 3.05,
            counterpoiseLengthMeters = 30.0
        )
        assertTrue("Low freq resistance should be calculated", footing.lowFrequencyResistanceOhms > 0.0)
        assertTrue("Impulse resistance should be lower than low freq R due to ionization", footing.impulseSurgeResistanceOhms < footing.lowFrequencyResistanceOhms)

        val bfo = LightningGroundingEngine.calculateBackflashover(
            insulatorCfoKv = 1200.0,
            footingResistanceOhms = footing.lowFrequencyResistanceOhms
        )
        assertTrue("Critical stroke current should be calculated", bfo.criticalCurrentKa > 30.0)
    }

    @Test
    fun testConductorDatabaseAndDamper() {
        val drake = STANDARD_CONDUCTORS.find { it.codeWord == "Drake" }
        assertNotNull("Drake conductor must exist in database", drake)
        assertEquals(795.0, drake!!.sizeKcmil, 0.1)

        val damper = ConductorHardwareEngine.calculateVibrationAndDamper(
            conductorDiameterMm = drake.diameterMm,
            conductorWeightKgPerM = drake.weightKgPerM,
            everydayTensionKn = 28.0,
            rbsKn = drake.rbsKn
        )
        assertTrue("Strouhal frequency should be between 10 and 35 Hz", damper.strouhalFrequencyHz in 10.0..35.0)
        assertTrue("Damper position should be positive and under 150 inches", damper.recommendedDamperDistanceInches in 30.0..150.0)
    }

    @Test
    fun testEmissivityCalculator() {
        val newAl = EmissivityEngine.calculateEmissivityImpact(
            surfaceCondition = ConductorSurfaceCondition.NEW_UNWEATHERED
        )
        assertEquals(0.23, newAl.emissivity, 0.01)
        assertEquals(0.23, newAl.solarAbsorptivity, 0.01)
        assertTrue("New shiny conductor should have lower ampacity due to reduced radiative cooling", newAl.ampacityVariancePercentFromDefault < 0.0)

        val coated = EmissivityEngine.calculateEmissivityImpact(
            surfaceCondition = ConductorSurfaceCondition.HIGH_EMISSIVITY_COATED
        )
        assertEquals(0.92, coated.emissivity, 0.01)
        assertEquals(0.35, coated.solarAbsorptivity, 0.01)
        assertTrue("High emissivity coated conductor should have positive ampacity boost", coated.ampacityVariancePercentFromDefault > 0.0)
    }

    @Test
    fun testNescRule232Clearance() {
        // 230 kV over road at 1000 ft altitude
        val clearance = RegulatoryClearanceEngine.calculateNescClearance(
            terrainType = NescTerrainType.ROADS_HIGHWAYS,
            phaseToPhaseVoltageKv = 230.0,
            altitudeFt = 1000.0,
            actualMidspanClearanceFt = 30.0
        )
        assertEquals(18.5, clearance.baseClearanceFt, 0.1)
        // Voltage adder = (230 - 22) * 0.4 / 12 = 208 * 0.4 / 12 = 6.93 ft
        assertTrue("Voltage adder should be ~6.93 ft", clearance.voltageAdderFt in 6.8..7.1)
        assertTrue("Total clearance required should be ~25.4 ft", clearance.totalRequiredClearanceFt in 25.0..26.0)
        assertTrue("30 ft actual clearance should pass", clearance.compliancePassed)
    }

    @Test
    fun testOshaMad() {
        val mad = RegulatoryClearanceEngine.calculateOshaMad(
            phaseToPhaseVoltageKv = 230.0,
            altitudeFt = 1000.0
        )
        assertTrue("230 kV phase to ground MAD should be in 3 to 8 ft range (OSHA Table R-6)", mad.phaseToGroundMadFt in 3.0..8.0)
        assertTrue("Phase to phase MAD should exceed phase to ground MAD", mad.phaseToPhaseMadFt > mad.phaseToGroundMadFt)
    }

    @Test
    fun testFercOrder881Aar() {
        val aarResult = FercAarEngine.calculateAar(
            ambientTempC = 15.0,
            isDaytime = false // Nighttime rating
        )
        assertTrue("AAR rating at 15C at night should exceed 40C static design rating", aarResult.aarRatingAmps > aarResult.staticBaseRatingAmps)
        assertTrue("Capacity gain should be positive", aarResult.capacityGainPercent > 0.0)
        assertTrue("MVA capacity gain should be positive", aarResult.capacityGainMva > 0.0)
    }

    @Test
    fun testNercFac003Mvcd() {
        val mvcd = NercMvcdEngine.calculateMvcd(
            lineVoltageKv = 230.0,
            transientFactorT = 2.4,
            altitudeFt = 1500.0,
            observedVegetationClearanceFt = 12.0
        )
        assertTrue("MVCD electrical clearance should be between 3 and 10 ft", mvcd.electricalClearanceMvcdFt in 3.0..10.0)
        assertTrue("Total MVCD should include wind buffer", mvcd.totalMvcdRequiredFt > mvcd.electricalClearanceMvcdFt)
        assertTrue("12 ft distance should be compliant", mvcd.complianceStatus.contains("COMPLIANT"))
    }

    @Test
    fun testFaaPart77Obstruction() {
        val faa = FaaObstructionEngine.evaluateFaaObstruction(
            structureHeightAglFt = 220.0,
            proximityToRunwayFt = 15000.0,
            isInstrumentRunway = true
        )
        assertTrue("Structure over 200 ft must trigger FAA obstruction", faa.isObstructionTriggered)
        assertTrue("Structure over 200 ft requires aviation paint bands", faa.requiresAviationMarkingPaint)
        assertEquals(7, faa.paintBandsCount)
    }

    @Test
    fun testExpandedGlossaryRepository() {
        assertTrue("Glossary should have over 50 comprehensive regulatory terms", GlossaryRepository.allTerms.size >= 50)
        val madTerm = GlossaryRepository.searchTerms("Minimum Approach Distance")
        assertTrue("MAD term must be found in glossary", madTerm.isNotEmpty())
        val nesc232 = GlossaryRepository.searchTerms("Rule 232")
        assertTrue("NESC 232 must be found in glossary", nesc232.isNotEmpty())
        val fercTerm = GlossaryRepository.searchTerms("Order 881")
        assertTrue("FERC Order 881 must be found in glossary", fercTerm.isNotEmpty())
        val mvcdTerm = GlossaryRepository.searchTerms("MVCD")
        assertTrue("MVCD must be found in glossary", mvcdTerm.isNotEmpty())
    }

    @Test
    fun testFlashcardsRepositoryAndQuizzes() {
        assertTrue("Flashcard repository should have at least 20 curated cards", FlashcardRepository.cards.size >= 20)
        assertTrue("Flashcard repository quiz questions should exist", FlashcardRepository.quizQuestions.size >= 10)

        // Verify concise definitions and formula takeaways
        FlashcardRepository.cards.forEach { card ->
            assertTrue("Card ${card.id} must have a non-empty concise definition", card.shortDefinition.isNotBlank())
            assertTrue("Card ${card.id} must cite a standard", card.standardRef.isNotBlank())
            assertTrue("Card ${card.id} must have a formula/takeaway", card.keyTakeawayOrFormula.isNotBlank())
        }

        // Test filtering by category
        val lineCards = FlashcardRepository.cardsForCategory("Line Design")
        assertTrue("Line Design cards should be populated", lineCards.isNotEmpty())

        val lightningCards = FlashcardRepository.cardsForCategory("Lightning and Grounding")
        assertTrue("Lightning cards should be populated", lightningCards.isNotEmpty())

        // Test quiz answer bounds
        FlashcardRepository.quizQuestions.forEach { q ->
            assertTrue("Quiz options must be at least 3", q.options.size >= 3)
            assertTrue("Correct index must be within range", q.correctIndex in 0 until q.options.size)
            assertTrue("Explanation must be non-empty", q.explanation.isNotBlank())
        }
    }

    @Test
    fun testTransmissionChatKnowledgeBase() {
        // Test offline responses and tool navigations
        val emissivityResponse = TransmissionKnowledgeBase.getLocalOfflineResponse("What is conductor emissivity?")
        assertTrue("Emissivity response should contain IEEE 738", emissivityResponse.answer.contains("IEEE Std 738"))
        assertNotNull("Emissivity should map to tool", emissivityResponse.navigation)
        assertEquals("emissivity", emissivityResponse.navigation?.toolId)

        val nescResponse = TransmissionKnowledgeBase.getLocalOfflineResponse("NESC 232 road clearance")
        assertTrue("NESC response should cite Rule 232", nescResponse.answer.contains("Rule 232"))
        assertEquals("nesc_clearance", nescResponse.navigation?.toolId)

        val oshaResponse = TransmissionKnowledgeBase.getLocalOfflineResponse("OSHA Minimum Approach Distance calculation")
        assertTrue("OSHA response should cite 1910.269", oshaResponse.answer.contains("1910.269"))
        assertEquals("osha_mad", oshaResponse.navigation?.toolId)

        val catenaryResponse = TransmissionKnowledgeBase.getLocalOfflineResponse("How to calculate catenary sag?")
        assertTrue("Catenary response should contain sag formula", catenaryResponse.answer.contains("8 · H"))
        assertEquals("catenary", catenaryResponse.navigation?.toolId)

        val zincResponse = TransmissionKnowledgeBase.getLocalOfflineResponse("ISO 9223 zinc coating life")
        assertTrue("Zinc response should cite ISO 9223", zincResponse.answer.contains("ISO 9223"))
        assertEquals("zinc_life", zincResponse.navigation?.toolId)
    }
}
