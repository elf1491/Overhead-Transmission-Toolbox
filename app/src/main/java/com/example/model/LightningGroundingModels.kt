package com.example.model

import kotlin.math.*

data class FootingResistanceResult(
    val lowFrequencyResistanceOhms: Double,
    val impulseSurgeResistanceOhms: Double,
    val rodsResistanceOhms: Double,
    val counterpoiseResistanceOhms: Double,
    val targetMet10Ohm: Boolean,
    val recommendation: String
)

data class ShieldingAnalysisResult(
    val shieldAngleDegrees: Double,
    val strikingDistanceMeters: Double,
    val maxPenetratingCurrentKa: Double,
    val shieldingEffective: Boolean,
    val expectedSfforPer100kmYr: Double,
    val recommendation: String
)

data class BackflashoverResult(
    val criticalCurrentKa: Double,
    val groundFlashDensityNg: Double, // flashes/km2/year
    val estimatedBforPer100kmYr: Double,
    val totalTripRatePer100kmYr: Double,
    val riskEvaluation: String
)

data class WennerTestResult(
    val pinSpacingMeters: Double,
    val measuredResistanceOhms: Double,
    val apparentResistivityOhmM: Double,
    val soilClassDescription: String
)

object LightningGroundingEngine {

    /**
     * Tower Footing Resistance & Counterpoise Sizing (IEEE Std 80 / IEEE 142)
     */
    fun calculateFootingResistance(
        soilResistivityOhmM: Double = 250.0,
        numberOfRods: Int = 4,
        rodLengthMeters: Double = 3.05, // 10 ft rod
        rodDiameterMm: Double = 16.0, // 5/8" rod
        counterpoiseLengthMeters: Double = 30.0, // buried counterpoise trench
        counterpoiseBurialDepthM: Double = 0.8,
        lightningStrokeCurrentKa: Double = 40.0
    ): FootingResistanceResult {
        val rho = max(5.0, soilResistivityOhmM)
        val lRod = max(1.0, rodLengthMeters)
        val rRodMeters = (rodDiameterMm / 1000.0) / 2.0

        // Single rod Dwight formula: R = (rho / (2 * pi * L)) * (ln(4L / r) - 1)
        val singleRodR = (rho / (2.0 * PI * lRod)) * (ln((4.0 * lRod) / rRodMeters) - 1.0)
        // Multiple rods parallel efficiency factor
        val rodsR = if (numberOfRods > 0) singleRodR / (numberOfRods * 0.8) else 999.0

        // Horizontal buried wire counterpoise R_cp = (rho / (pi * L)) * (ln(2L / sqrt(2*d*r)) - 1)
        val rWireMeters = 0.005 // 10mm wire
        val cpR = if (counterpoiseLengthMeters > 0) {
            (rho / (PI * counterpoiseLengthMeters)) *
                    (ln((2.0 * counterpoiseLengthMeters) / sqrt(2.0 * counterpoiseBurialDepthM * rWireMeters)) - 1.0)
        } else 999.0

        val rLowFreq = (1.0 / ((1.0 / rodsR) + (1.0 / cpR))).coerceAtLeast(0.5)

        // Impulse resistance reduction due to soil ionization at high current (IEEE 1410 / CIGRE)
        // I_g = (rho * E_0) / (2 * pi * R0^2), where E_0 ~ 400 kV/m (soil critical ionization gradient)
        val e0 = 400.0 // kV/m
        val ig = (rho * e0) / (2.0 * PI * rLowFreq.pow(2))
        val rImpulse = rLowFreq / sqrt(1.0 + (lightningStrokeCurrentKa / max(1.0, ig)))

        val meets10Ohm = rLowFreq <= 10.0
        val rec = when {
            rLowFreq <= 10.0 -> "Optimal (<10 Ω). Excellent lightning backflashover performance."
            rLowFreq <= 20.0 -> "Marginal (10-20 Ω). Acceptable for 230kV+ lines with high insulation CFO, but consider additional counterpoise in high keraunic zones."
            else -> "High (>20 Ω). Tower footing resistance elevated; significant risk of backflashover. Install radial counterpoise or deep earth boring."
        }

        return FootingResistanceResult(
            lowFrequencyResistanceOhms = rLowFreq,
            impulseSurgeResistanceOhms = rImpulse,
            rodsResistanceOhms = rodsR,
            counterpoiseResistanceOhms = cpR,
            targetMet10Ohm = meets10Ohm,
            recommendation = rec
        )
    }

    /**
     * Shielding Angle and Electrogeometric Model (Armstrong & Whitehead)
     */
    fun calculateShieldingAngle(
        shieldWireHeightM: Double = 35.0,
        phaseConductorHeightM: Double = 26.0,
        horizontalSeparationM: Double = 4.5,
        targetPeakCurrentKa: Double = 15.0
    ): ShieldingAnalysisResult {
        val deltaH = max(0.5, shieldWireHeightM - phaseConductorHeightM)
        val thetaRad = atan2(horizontalSeparationM, deltaH)
        val thetaDeg = Math.toDegrees(thetaRad)

        // Striking distance r_s = 10 * I^0.65 (meters)
        val rs = 10.0 * targetPeakCurrentKa.pow(0.65)

        // Maximum penetrating current for given geometry
        val maxPenCurrent = ((deltaH + horizontalSeparationM) / 5.0).pow(1.0 / 0.65)
        val effective = thetaDeg <= 30.0 // standard rule of thumb: shield angle <= 30 deg

        val sffor = if (effective) 0.05 else (thetaDeg - 30.0) * 0.15

        val rec = if (effective) {
            "Shielding Effective: Shield angle (${String.format("%.1f", thetaDeg)}°) is within the recommended <=30° envelope."
        } else {
            "Shielding Inadequate: Shield angle (${String.format("%.1f", thetaDeg)}°) exceeds 30°. Conductor vulnerable to direct lightning attachment."
        }

        return ShieldingAnalysisResult(
            shieldAngleDegrees = thetaDeg,
            strikingDistanceMeters = rs,
            maxPenetratingCurrentKa = maxPenCurrent,
            shieldingEffective = effective,
            expectedSfforPer100kmYr = sffor,
            recommendation = rec
        )
    }

    /**
     * Critical Backflashover Current & Outage Rate
     */
    fun calculateBackflashover(
        insulatorCfoKv: Double = 1200.0, // e.g. 230kV suspension string ~ 1100-1300 kV
        footingResistanceOhms: Double = 15.0,
        groundFlashDensityFlPerKm2Yr: Double = 4.0, // GFD flashes/km2/yr
        couplingFactorK: Double = 0.32,
        towerSurgeImpedanceOhms: Double = 150.0
    ): BackflashoverResult {
        val effectiveCoupling = (1.0 - couplingFactorK)
        // I_c = CFO / ( (1 - K) * R_footing + Z_t / 2 )
        val denominator = (effectiveCoupling * footingResistanceOhms) + (towerSurgeImpedanceOhms / 6.0)
        val criticalCurrentKa = insulatorCfoKv / denominator

        // Probability of stroke exceeding critical current: P(I > Ic) = 1 / (1 + (Ic / 31)^2.6) (Anderson-Eriksson)
        val probExceeding = 1.0 / (1.0 + (criticalCurrentKa / 31.0).pow(2.6))
        // BFOR estimate per 100 km / year
        val bfor = groundFlashDensityFlPerKm2Yr * 100.0 * 0.08 * probExceeding

        val evaluation = when {
            criticalCurrentKa >= 120.0 -> "Low Flashover Risk: Insulator withstands strokes up to ${criticalCurrentKa.toInt()} kA."
            criticalCurrentKa >= 80.0 -> "Moderate Risk: Common lightning peak currents (80-120 kA) may cause occasional backflash."
            else -> "High Flashover Risk: Critical current is under 80 kA. Backflashover will occur frequently during summer storm activity."
        }

        return BackflashoverResult(
            criticalCurrentKa = criticalCurrentKa,
            groundFlashDensityNg = groundFlashDensityFlPerKm2Yr,
            estimatedBforPer100kmYr = bfor,
            totalTripRatePer100kmYr = bfor + 0.1, // including small SFFOR
            riskEvaluation = evaluation
        )
    }

    /**
     * Wenner 4-Point Soil Resistivity Test Analysis (IEEE 81)
     * rho = 2 * pi * a * R
     */
    fun calculateWennerResistivity(pinSpacingMeters: Double, measuredResistanceOhms: Double): WennerTestResult {
        val rho = 2.0 * PI * pinSpacingMeters * measuredResistanceOhms
        val desc = when {
            rho < 20.0 -> "Sea marsh / low moor peat (Highly conductive, high corrosion risk)"
            rho < 100.0 -> "Clay, loam, arable soil (Good grounding performance)"
            rho < 500.0 -> "Sandy clay, gravel, mixed subsoil (Moderate grounding)"
            rho < 2000.0 -> "Dry sand, gravel, weathered granite (Poor grounding, requires counterpoise)"
            else -> "Bedrock / solid granite / volcanic rock (Extreme resistivity, specialized grounding wells needed)"
        }
        return WennerTestResult(
            pinSpacingMeters = pinSpacingMeters,
            measuredResistanceOhms = measuredResistanceOhms,
            apparentResistivityOhmM = rho,
            soilClassDescription = desc
        )
    }
}
