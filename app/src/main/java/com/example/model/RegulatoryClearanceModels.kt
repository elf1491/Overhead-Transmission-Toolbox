package com.example.model

import kotlin.math.*

enum class NescTerrainType(
    val label: String,
    val baseClearanceFt: Double, // for <= 22 kV
    val nescTableReference: String
) {
    ROADS_HIGHWAYS(
        "Roads, Streets, & Highways subject to truck traffic",
        18.5,
        "NESC C2-2023 Table 232-1, Item 1.a"
    ),
    RAILROAD_TRACKS(
        "Railroad tracks (except electrified systems)",
        26.5,
        "NESC C2-2023 Table 232-1, Item 2"
    ),
    DRIVEWAYS_PARKING(
        "Driveways, commercial parking lots, & alleys",
        18.5,
        "NESC C2-2023 Table 232-1, Item 1.b"
    ),
    PEDESTRIAN_ONLY(
        "Spaces & ways accessible to pedestrians only",
        14.5,
        "NESC C2-2023 Table 232-1, Item 4"
    ),
    CULTIVATED_FIELDS(
        "Cultivated fields, grazing land, & orchards",
        18.5,
        "NESC C2-2023 Table 232-1, Item 1.c"
    ),
    WATER_SAILBOATS_SMALL(
        "Waterways / lakes (20 to 200 acres sailboat area)",
        29.5,
        "NESC C2-2023 Table 232-1, Item 3"
    )
}

data class NescClearanceResult(
    val baseClearanceFt: Double,
    val voltageAdderFt: Double,
    val altitudeAdderFt: Double,
    val totalRequiredClearanceFt: Double,
    val totalRequiredClearanceMeters: Double,
    val actualConductorClearanceFt: Double,
    val compliancePassed: Boolean,
    val marginFt: Double,
    val regulatoryCitation: String
)

data class OshaMadResult(
    val voltageKvPhaseToPhase: Double,
    val perUnitTransientT: Double,
    val altitudeFt: Double,
    val altitudeCorrectionFactorA: Double,
    val phaseToGroundMadFt: Double,
    val phaseToGroundMadMeters: Double,
    val phaseToPhaseMadFt: Double,
    val phaseToPhaseMadMeters: Double,
    val oshaCitation: String
)

object RegulatoryClearanceEngine {

    /**
     * NESC Rule 232 Minimum Vertical Clearance over Ground, Road, or Water
     * Base clearance + Voltage adder (0.4 in / kV over 22 kV) + Altitude adder (3% per 1000 ft over 3300 ft)
     */
    fun calculateNescClearance(
        terrainType: NescTerrainType,
        phaseToPhaseVoltageKv: Double = 230.0,
        altitudeFt: Double = 1000.0,
        actualMidspanClearanceFt: Double = 32.0
    ): NescClearanceResult {
        val baseFt = terrainType.baseClearanceFt

        // Voltage adder: 0.4 in per kV over 22 kV (NESC Rule 232-C-1-a)
        val excessKv = max(0.0, phaseToPhaseVoltageKv - 22.0)
        val voltageAdderInches = excessKv * 0.4
        val voltageAdderFt = voltageAdderInches / 12.0

        // Subtotal before altitude
        val subtotalFt = baseFt + voltageAdderFt

        // Altitude adjustment: for elevations > 3300 ft (1000 m), increase by 3% per 1000 ft above 3300 ft (NESC Rule 232-D)
        val excessAltitudeFt = max(0.0, altitudeFt - 3300.0)
        val altitudeFactor = (excessAltitudeFt / 1000.0) * 0.03
        val altitudeAdderFt = subtotalFt * altitudeFactor

        val totalRequiredFt = subtotalFt + altitudeAdderFt
        val totalRequiredMeters = totalRequiredFt * 0.3048

        val margin = actualMidspanClearanceFt - totalRequiredFt
        val passed = margin >= 0.0

        val citation = "NESC C2-2023 Rule 232, Table 232-1 & Rule 232-D"

        return NescClearanceResult(
            baseClearanceFt = baseFt,
            voltageAdderFt = voltageAdderFt,
            altitudeAdderFt = altitudeAdderFt,
            totalRequiredClearanceFt = totalRequiredFt,
            totalRequiredClearanceMeters = totalRequiredMeters,
            actualConductorClearanceFt = actualMidspanClearanceFt,
            compliancePassed = passed,
            marginFt = margin,
            regulatoryCitation = citation
        )
    }

    /**
     * OSHA 1910.269 & IEEE Std 516 Minimum Approach Distance (MAD) for Live-Line Work
     * Calculates electrical component D_E + ergonomic component D_M adjusted for transient overvoltage T and altitude A.
     */
    fun calculateOshaMad(
        phaseToPhaseVoltageKv: Double = 230.0,
        transientOvervoltageT: Double? = null,
        altitudeFt: Double = 1000.0
    ): OshaMadResult {
        // Standard per-unit transient overvoltages (OSHA 1910.269 App B Table R-9 / IEEE 516):
        val defaultT = when {
            phaseToPhaseVoltageKv <= 242.0 -> 3.0
            phaseToPhaseVoltageKv <= 362.0 -> 2.4
            phaseToPhaseVoltageKv <= 550.0 -> 2.0
            else -> 1.8
        }
        val t = transientOvervoltageT ?: defaultT

        // Altitude correction factor A (IEEE 516 Table 1):
        // A = 1.0 for <= 2950 ft (900 m)
        val a = when {
            altitudeFt <= 2950.0 -> 1.00
            altitudeFt <= 3940.0 -> 1.04
            altitudeFt <= 4920.0 -> 1.07
            altitudeFt <= 5900.0 -> 1.11
            altitudeFt <= 6890.0 -> 1.15
            altitudeFt <= 7870.0 -> 1.19
            altitudeFt <= 9840.0 -> 1.28
            else -> 1.35
        }

        val vPeakPhaseToGroundKv = (phaseToPhaseVoltageKv * sqrt(2.0) / sqrt(3.0)) * t
        val vPeakPhaseToPhaseKv = (phaseToPhaseVoltageKv * sqrt(2.0)) * t

        // Electrical component D_E in meters (OSHA / IEEE formula)
        // For rod-plane gap (approximate IEEE 516 D_E = (V_peak / 500)^1.35 * A)
        val dePhaseToGroundM = (vPeakPhaseToGroundKv / 520.0).pow(1.35) * a * 0.45
        val dePhaseToPhaseM = (vPeakPhaseToPhaseKv / 520.0).pow(1.35) * a * 0.45

        // Inadvertent movement ergonomic adder D_M = 0.61 m (2.0 ft) for transmission live-line work
        val dm = 0.61
        val madGroundM = max(0.9, dePhaseToGroundM + dm)
        val madPhaseM = max(1.2, dePhasePhaseM(dePhaseToPhaseM, dm))

        val madGroundFt = madGroundM * 3.28084
        val madPhaseFt = madPhaseM * 3.28084

        return OshaMadResult(
            voltageKvPhaseToPhase = phaseToPhaseVoltageKv,
            perUnitTransientT = t,
            altitudeFt = altitudeFt,
            altitudeCorrectionFactorA = a,
            phaseToGroundMadFt = madGroundFt,
            phaseToGroundMadMeters = madGroundM,
            phaseToPhaseMadFt = madPhaseFt,
            phaseToPhaseMadMeters = madPhaseM,
            oshaCitation = "OSHA 29 CFR 1910.269(l)(3) & IEEE Std 516-2021"
        )
    }

    private fun dePhasePhaseM(de: Double, dm: Double): Double {
        return de * 1.35 + dm
    }
}

data class FercAarResult(
    val ambientTempC: Double,
    val isDaytime: Boolean,
    val staticBaseRatingAmps: Double,
    val aarRatingAmps: Double,
    val capacityGainPercent: Double,
    val capacityGainMva: Double,
    val maxOperatingTempC: Double,
    val economicBenefitAssessment: String,
    val fercCitation: String
)

object FercAarEngine {
    /**
     * FERC Order 881 Ambient-Adjusted Rating (AAR) Calculation
     * Calculates hourly thermal rating vs static nameplate basis per IEEE Std 738.
     */
    fun calculateAar(
        ambientTempC: Double = 20.0,
        isDaytime: Boolean = true,
        conductorDiameterMm: Double = 28.1, // Drake 795 ACSR
        conductorResistance75cOhmPerKm: Double = 0.072,
        maxOperatingTempC: Double = 75.0,
        lineVoltageKv: Double = 230.0,
        windSpeedMPerS: Double = 0.61, // ~2 ft/s
        solarIrradianceWPerM2: Double = 1000.0,
        staticAmbientDesignC: Double = 40.0
    ): FercAarResult {
        val dMeters = conductorDiameterMm / 1000.0
        val rPerMeter = conductorResistance75cOhmPerKm / 1000.0
        val sigma = 5.670374e-8
        val eps = 0.8
        val alpha = 0.8

        // 1. Static Rating (Standard conservative planning: 40°C, full solar, wind 0.61 m/s)
        val deltaTStatic = max(1.0, maxOperatingTempC - staticAmbientDesignC)
        val airDensityStatic = 1.293 - (0.00152 * (maxOperatingTempC + staticAmbientDesignC) / 2.0)
        val reynoldsStatic = (dMeters * windSpeedMPerS * airDensityStatic) / 1.81e-5
        val qcStatic = (1.01 + 0.371 * reynoldsStatic.pow(0.52)) * 0.0242 * deltaTStatic
        val qrStatic = PI * dMeters * eps * sigma * ((maxOperatingTempC + 273.15).pow(4) - (staticAmbientDesignC + 273.15).pow(4))
        val qsStatic = alpha * solarIrradianceWPerM2 * dMeters
        val staticAmp = sqrt(max(10.0, qcStatic + qrStatic - qsStatic) / rPerMeter)

        // 2. Ambient-Adjusted Rating (AAR)
        val actualDeltaT = max(1.0, maxOperatingTempC - ambientTempC)
        val actualAirDensity = 1.293 - (0.00152 * (maxOperatingTempC + ambientTempC) / 2.0)
        val actualReynolds = (dMeters * windSpeedMPerS * actualAirDensity) / 1.81e-5
        val qcAar = (1.01 + 0.371 * actualReynolds.pow(0.52)) * 0.0242 * actualDeltaT
        val qrAar = PI * dMeters * eps * sigma * ((maxOperatingTempC + 273.15).pow(4) - (ambientTempC + 273.15).pow(4))
        val qsAar = if (isDaytime) (alpha * solarIrradianceWPerM2 * dMeters) else 0.0 // No solar at night
        val aarAmp = sqrt(max(10.0, qcAar + qrAar - qsAar) / rPerMeter)

        val gainPct = ((aarAmp - staticAmp) / staticAmp) * 100.0
        val deltaCurrent = aarAmp - staticAmp
        // 3-phase MVA capacity gain: sqrt(3) * V_kv * delta_I / 1000
        val gainMva = (sqrt(3.0) * lineVoltageKv * deltaCurrent) / 1000.0

        val assessment = when {
            gainPct >= 20.0 ->
                "MAJOR CAPACITY UNLOCKED (+${String.format("%.1f", gainPct)}%): Cool temperatures and absence of solar heating release substantial hidden transmission capacity, eliminating curtailment without new line construction."
            gainPct >= 5.0 ->
                "MODERATE CAPACITY GAIN (+${String.format("%.1f", gainPct)}%): Favorable ambient conditions yield an additional ${String.format("%.1f", gainMva)} MVA of throughput headroom per FERC Order 881 hourly lookup."
            gainPct in -5.0..5.0 ->
                "NOMINAL RATING: Real-time conditions are close to design basis assumptions. Static rating remains within 5% of actual thermal capacity."
            else ->
                "MANDATORY DERATING (${String.format("%.1f", gainPct)}%): Extreme heat wave conditions exceed 40°C design basis. FERC 881 mandates derating line flow to prevent sag clearance violations."
        }

        return FercAarResult(
            ambientTempC = ambientTempC,
            isDaytime = isDaytime,
            staticBaseRatingAmps = staticAmp,
            aarRatingAmps = aarAmp,
            capacityGainPercent = gainPct,
            capacityGainMva = gainMva,
            maxOperatingTempC = maxOperatingTempC,
            economicBenefitAssessment = assessment,
            fercCitation = "FERC Order 881 (18 CFR § 35.28) & IEEE Std 738-2012"
        )
    }
}

data class NercMvcdResult(
    val lineVoltageKv: Double,
    val transientOvervoltageFactorT: Double,
    val altitudeFt: Double,
    val altitudeCorrectionFactorA: Double,
    val electricalClearanceMvcdFt: Double,
    val windSwayBufferFt: Double,
    val totalMvcdRequiredFt: Double,
    val totalMvcdRequiredMeters: Double,
    val complianceStatus: String,
    val nercStandardCitation: String
)

object NercMvcdEngine {
    /**
     * NERC Reliability Standard FAC-003-4 Minimum Vegetation Clearance Distance (MVCD)
     * Uses Gallet switching surge flashover equation with air density altitude correction factor A.
     */
    fun calculateMvcd(
        lineVoltageKv: Double = 230.0,
        transientFactorT: Double = 2.4,
        altitudeFt: Double = 1500.0,
        windSwayBufferFt: Double = 3.0,
        observedVegetationClearanceFt: Double = 12.0
    ): NercMvcdResult {
        // Altitude factor per Gallet / IEEE 516
        val a = when {
            altitudeFt <= 1000.0 -> 1.00
            altitudeFt <= 3000.0 -> 1.04
            altitudeFt <= 5000.0 -> 1.09
            altitudeFt <= 7000.0 -> 1.15
            altitudeFt <= 9000.0 -> 1.22
            else -> 1.30
        }

        // Peak switching surge voltage to ground in kV
        val vPeakKv = (lineVoltageKv * sqrt(2.0) / sqrt(3.0)) * transientFactorT

        // Gallet air gap equation for conductor-to-vegetation (gap factor k ~ 1.3):
        // V = 3400 / (1 + 8 / D) * A
        // Solving for D (meters):
        // (1 + 8 / D) = 3400 * A / V => 8 / D = (3400 * A / V) - 1 => D = 8 / ((3400 * A / V) - 1)
        val ratio = (3400.0 * a) / max(10.0, vPeakKv)
        val dMeters = if (ratio > 1.05) (8.0 / (ratio - 1.0)) else 5.0
        val electricalMvcdFt = max(1.5, dMeters * 3.28084)
        val totalMvcdFt = electricalMvcdFt + windSwayBufferFt
        val totalMvcdM = totalMvcdFt * 0.3048

        val status = if (observedVegetationClearanceFt >= totalMvcdFt) {
            "COMPLIANT: Tree distance (${observedVegetationClearanceFt} ft) exceeds MVCD (${String.format("%.2f", totalMvcdFt)} ft). Margin: +${String.format("%.2f", observedVegetationClearanceFt - totalMvcdFt)} ft."
        } else {
            "FAC-003 VIOLATION ALERT: Vegetation breach! Distance (${observedVegetationClearanceFt} ft) is below required MVCD (${String.format("%.2f", totalMvcdFt)} ft). Immediate trim required under R1/R2."
        }

        return NercMvcdResult(
            lineVoltageKv = lineVoltageKv,
            transientOvervoltageFactorT = transientFactorT,
            altitudeFt = altitudeFt,
            altitudeCorrectionFactorA = a,
            electricalClearanceMvcdFt = electricalMvcdFt,
            windSwayBufferFt = windSwayBufferFt,
            totalMvcdRequiredFt = totalMvcdFt,
            totalMvcdRequiredMeters = totalMvcdM,
            complianceStatus = status,
            nercStandardCitation = "NERC Reliability Standard FAC-003-4 (Table 2 & Gallet Equation)"
        )
    }
}

data class FaaObstructionResult(
    val structureHeightAglFt: Double,
    val proximityToRunwayFt: Double,
    val runwaySlopeRatio: Double,
    val slopeLimitHeightFt: Double,
    val isObstructionTriggered: Boolean,
    val requiresAviationMarkingPaint: Boolean,
    val paintBandsCount: Int,
    val bandHeightFt: Double,
    val requiresCatenaryMarkerBalls: Boolean,
    val markerBallDiameterInches: Int,
    val markerBallSpacingFt: Double,
    val lightingSpecification: String,
    val faaCitation: String
)

object FaaObstructionEngine {
    /**
     * FAA 14 CFR Part 77 & Advisory Circular AC 70/7460-1M
     * Evaluates obstacle lighting and marking standards for transmission towers and spans.
     */
    fun evaluateFaaObstruction(
        structureHeightAglFt: Double = 160.0,
        proximityToRunwayFt: Double = 15000.0,
        isInstrumentRunway: Boolean = true,
        isWaterOrCanyonCrossing: Boolean = false,
        spanLengthFt: Double = 1200.0
    ): FaaObstructionResult {
        // Slope criteria: 100:1 for runways > 3200 ft, 50:1 for shorter runways within 20,000 ft
        val slopeRatio = if (isInstrumentRunway) 100.0 else 50.0
        val slopeLimitHeightFt = if (proximityToRunwayFt <= 20000.0) {
            proximityToRunwayFt / slopeRatio
        } else {
            200.0 // General 200 ft AGL threshold outside 20,000 ft
        }

        val exceedsSlope = structureHeightAglFt > slopeLimitHeightFt
        val exceeds200Ft = structureHeightAglFt >= 200.0
        val isObstruction = exceedsSlope || exceeds200Ft

        val markingPaint = structureHeightAglFt >= 200.0 || (isObstruction && proximityToRunwayFt <= 10000.0)
        val bandsCount = if (markingPaint) 7 else 0
        val bandHeight = if (bandsCount > 0) structureHeightAglFt / bandsCount else 0.0

        val markerBallsRequired = isWaterOrCanyonCrossing || (isObstruction && spanLengthFt >= 800.0)
        val ballSpacing = if (markerBallsRequired) 200.0 else 0.0

        val lighting = when {
            structureHeightAglFt >= 500.0 ->
                "L-856 High-Intensity White Flashing Lights (Day & Night)"
            structureHeightAglFt >= 200.0 || exceedsSlope ->
                "L-864 Medium-Intensity Red Flashing Beacons (Night) with L-865 White Flashing (Day)"
            else ->
                "No FAA lighting mandated (below 200 ft AGL and clear of runway slope)"
        }

        return FaaObstructionResult(
            structureHeightAglFt = structureHeightAglFt,
            proximityToRunwayFt = proximityToRunwayFt,
            runwaySlopeRatio = slopeRatio,
            slopeLimitHeightFt = slopeLimitHeightFt,
            isObstructionTriggered = isObstruction,
            requiresAviationMarkingPaint = markingPaint,
            paintBandsCount = bandsCount,
            bandHeightFt = bandHeight,
            requiresCatenaryMarkerBalls = markerBallsRequired,
            markerBallDiameterInches = 36,
            markerBallSpacingFt = ballSpacing,
            lightingSpecification = lighting,
            faaCitation = "FAA 14 CFR Part 77.9 & Advisory Circular AC 70/7460-1M Chapters 3, 5, 12"
        )
    }
}
