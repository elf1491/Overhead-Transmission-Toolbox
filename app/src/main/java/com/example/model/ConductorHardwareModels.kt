package com.example.model

import kotlin.math.*

data class ConductorSpec(
    val codeWord: String,
    val type: String, // ACSR, ACSS, AAAC
    val sizeKcmil: Double,
    val strandingAlSt: String,
    val diameterInches: Double,
    val weightLbPerFt: Double,
    val ratedBreakingStrengthLbf: Double,
    val acResistance75cOhmPerMile: Double,
    val standardAmpacityAmps: Double
) {
    val diameterMm: Double get() = diameterInches * 25.4
    val weightKgPerM: Double get() = weightLbPerFt * 1.48816
    val rbsKn: Double get() = ratedBreakingStrengthLbf * 0.00444822
}

val STANDARD_CONDUCTORS = listOf(
    ConductorSpec("Hawk", "ACSR", 477.0, "26/7", 0.858, 0.655, 19500.0, 0.219, 659.0),
    ConductorSpec("Drake", "ACSR", 795.0, "26/7", 1.108, 1.094, 31500.0, 0.139, 907.0),
    ConductorSpec("Cardinal", "ACSR", 954.0, "54/7", 1.196, 1.226, 33800.0, 0.117, 1014.0),
    ConductorSpec("Curlew", "ACSR", 1033.5, "54/7", 1.246, 1.328, 36300.0, 0.108, 1063.0),
    ConductorSpec("Bluejay", "ACSR", 1113.0, "45/7", 1.259, 1.254, 30700.0, 0.103, 1109.0),
    ConductorSpec("Finch", "ACSR", 1113.0, "54/19", 1.293, 1.431, 39100.0, 0.102, 1115.0),
    ConductorSpec("Partridge", "ACSR", 266.8, "26/7", 0.642, 0.367, 11300.0, 0.385, 475.0),
    ConductorSpec("Penguin", "ACSR", 211.6, "6/1", 0.563, 0.291, 8290.0, 0.442, 430.0),
    ConductorSpec("Grosbeak", "ACSR", 636.0, "26/7", 0.990, 0.875, 25200.0, 0.174, 786.0),
    ConductorSpec("Rail", "ACSR", 954.0, "45/7", 1.165, 1.075, 26300.0, 0.120, 990.0),
    ConductorSpec("Drake/ACSS", "ACSS", 795.0, "26/7", 1.108, 1.094, 31500.0, 0.139, 1420.0),
    ConductorSpec("Cardinal/ACSS", "ACSS", 954.0, "54/7", 1.196, 1.226, 33800.0, 0.117, 1590.0),
    ConductorSpec("Greeley", "AAAC", 927.2, "37", 1.108, 0.868, 28200.0, 0.119, 930.0)
)

data class AeolianDamperResult(
    val strouhalFrequencyHz: Double,
    val wavelengthMeters: Double,
    val halfLoopLengthMeters: Double,
    val recommendedDamperDistanceMeters: Double,
    val recommendedDamperDistanceInches: Double,
    val cigreEverydayTensionLimitPassed: Boolean,
    val recommendation: String
)

data class InsulatorCreepageResult(
    val requiredTotalCreepageMm: Double,
    val numberOfStandardDiscs: Int,
    val polymerStringLengthMeters: Double,
    val mechanicalSafetyFactor: Double,
    val mechanicalVerdict: String
)

object ConductorHardwareEngine {

    /**
     * Aeolian Vibration & Stockbridge Damper Placement (IEEE Std 664 & CIGRE SC22)
     * Strouhal: f = (St * V) / d
     * Damper position: x = 0.8 * (lambda / 2)
     */
    fun calculateVibrationAndDamper(
        conductorDiameterMm: Double = 28.14, // Drake mm
        conductorWeightKgPerM: Double = 1.628,
        everydayTensionKn: Double = 28.0, // everyday tension
        rbsKn: Double = 140.1, // RTS in kN
        windSpeedMPerS: Double = 3.5 // typical laminar wind 1 to 7 m/s
    ): AeolianDamperResult {
        val dMeters = conductorDiameterMm / 1000.0
        val strouhal = 0.185 // typical for stranded conductor
        val frequency = (strouhal * windSpeedMPerS) / dMeters

        // Wave velocity V_w = sqrt(T / m)
        val tensionNewtons = everydayTensionKn * 1000.0
        val waveVelocity = sqrt(tensionNewtons / conductorWeightKgPerM)

        val wavelength = waveVelocity / max(1.0, frequency)
        val halfLoop = wavelength / 2.0

        // Recommended damper distance from clamp mouth: 0.8 * halfLoop
        val damperDistM = 0.8 * halfLoop
        val damperDistInches = damperDistM * 39.3701

        val everydayPct = (everydayTensionKn / rbsKn) * 100.0
        val cigrePassed = everydayPct <= 18.0 // CIGRE safe limit without dampers is ~18% RTS

        val rec = when {
            everydayPct > 22.0 -> "High Tension Hazard: Everyday tension (${String.format("%.1f", everydayPct)}% RTS) exceeds 22%. Install dual Stockbridge dampers per span end to prevent fatigue strand breakage."
            everydayPct > 18.0 -> "Vibration Mitigation Required: Everyday tension exceeds 18% RTS CIGRE un-damped limit. One Stockbridge damper required at each span end."
            else -> "Low Vibration Risk: Conductor self-damping adequate under standard terrain."
        }

        return AeolianDamperResult(
            strouhalFrequencyHz = frequency,
            wavelengthMeters = wavelength,
            halfLoopLengthMeters = halfLoop,
            recommendedDamperDistanceMeters = damperDistM,
            recommendedDamperDistanceInches = damperDistInches,
            cigreEverydayTensionLimitPassed = cigrePassed,
            recommendation = rec
        )
    }

    /**
     * Insulator String Sizing & Creepage per IEC 60815
     */
    fun calculateInsulatorString(
        lineVoltageKvLtoL: Double = 230.0,
        pollutionLevel: String = "Heavy (25 mm/kV)", // Light 16, Medium 20, Heavy 25, Very Heavy 31
        insulatorMeRatingLbs: Double = 30000.0, // ANSI Class 52-5
        appliedWorkingLoadLbs: Double = 8500.0
    ): InsulatorCreepageResult {
        val specificCreepageMmPerKv = when {
            pollutionLevel.contains("16") || pollutionLevel.contains("Light") -> 16.0
            pollutionLevel.contains("20") || pollutionLevel.contains("Medium") -> 20.0
            pollutionLevel.contains("25") || pollutionLevel.contains("Heavy") -> 25.0
            else -> 31.0
        }

        // Required creepage = Specific creepage * max line-to-line operating voltage
        val maxOperatingVoltage = lineVoltageKvLtoL * 1.05
        val requiredTotalCreepage = specificCreepageMmPerKv * maxOperatingVoltage

        // Standard ANSI 52-3/5 disc provides ~292 mm (11.5") creepage and 146 mm (5.75") spacing
        val standardDiscCreepage = 292.0
        val numDiscs = ceil(requiredTotalCreepage / standardDiscCreepage).toInt()

        val polymerLengthM = (numDiscs * 146.0) / 1000.0

        val sf = if (appliedWorkingLoadLbs > 0) insulatorMeRatingLbs / appliedWorkingLoadLbs else 5.0
        val verdict = if (sf >= 2.5) {
            "PASS: Mechanical Safety Factor (${String.format("%.2f", sf)}) meets standard 2.5:1 requirement."
        } else {
            "INSUFFICIENT: Safety Factor (${String.format("%.2f", sf)}) is below 2.5:1 under working load."
        }

        return InsulatorCreepageResult(
            requiredTotalCreepageMm = requiredTotalCreepage,
            numberOfStandardDiscs = max(3, numDiscs),
            polymerStringLengthMeters = polymerLengthM,
            mechanicalSafetyFactor = sf,
            mechanicalVerdict = verdict
        )
    }
}
