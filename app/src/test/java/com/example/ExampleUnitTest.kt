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
}
