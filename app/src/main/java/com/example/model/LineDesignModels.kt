package com.example.model

import kotlin.math.*

enum class NescLoadingDistrict(val label: String, val iceInches: Double, val windPsf: Double, val tempF: Double, val kConstant: Double) {
    HEAVY("NESC Heavy", 0.50, 4.0, 0.0, 0.30),
    MEDIUM("NESC Medium", 0.25, 4.0, 15.0, 0.20),
    LIGHT("NESC Light", 0.00, 9.0, 30.0, 0.05),
    EXTREME_WIND("Extreme Wind (ASCE 7)", 0.0, 16.0, 60.0, 0.00),
    CUSTOM("Custom Environmental", 0.0, 0.0, 60.0, 0.0)
}

data class CatenaryResult(
    val sag: Double, // ft or m
    val maxTension: Double, // lbf or kN
    val catenaryParameterC: Double, // ft or m
    val conductorArcLength: Double, // ft or m
    val minGroundClearance: Double, // ft or m
    val tensionPercentRts: Double, // %
    val points: List<Pair<Double, Double>> // normalized or scaled (x, y) for curve drawing
)

object LineDesignEngine {
    /**
     * Computes level span catenary sag and tensions.
     * Inputs in Imperial or Metric.
     */
    fun calculateCatenary(
        spanLength: Double, // ft (or m)
        conductorWeightPerUnit: Double, // lb/ft (or kg/m -> convert to force)
        horizontalTension: Double, // lbf (or kN)
        supportHeight: Double, // ft (or m)
        ratedBreakingStrength: Double = 31500.0, // lbf
        isMetric: Boolean = false
    ): CatenaryResult {
        val span = if (spanLength <= 0) 100.0 else spanLength
        val w = if (conductorWeightPerUnit <= 0) 1.09 else conductorWeightPerUnit
        val h = if (horizontalTension <= 0) 5000.0 else horizontalTension

        // wForce: if metric, kg/m * 9.80665 N/m = kN/m
        val wEffective = if (isMetric) w * 0.00980665 else w
        val hEffective = if (isMetric) h else h // if kN, h is already kN

        val c = hEffective / wEffective
        val sag = c * (cosh(span / (2.0 * c)) - 1.0)
        val maxTension = hEffective + (wEffective * sag)
        val arcLength = 2.0 * c * sinh(span / (2.0 * c))
        val minClearance = max(0.0, supportHeight - sag)
        val tensionPct = if (ratedBreakingStrength > 0) (maxTension / ratedBreakingStrength) * 100.0 else 0.0

        // Sample 21 points along span from x = -span/2 to +span/2
        val points = mutableListOf<Pair<Double, Double>>()
        val steps = 20
        for (i in 0..steps) {
            val x = -span / 2.0 + (span * i / steps)
            val y = c * (cosh(x / c) - 1.0) // 0 at mid-span, sag at ends
            points.add(Pair(x + span / 2.0, y))
        }

        return CatenaryResult(
            sag = sag,
            maxTension = maxTension,
            catenaryParameterC = c,
            conductorArcLength = arcLength,
            minGroundClearance = minClearance,
            tensionPercentRts = tensionPct,
            points = points
        )
    }

    /**
     * Ruling Span calculation (Equivalent Span):
     * S_r = sqrt( sum(S_i^3) / sum(S_i) )
     */
    fun calculateRulingSpan(spans: List<Double>): Double {
        if (spans.isEmpty()) return 0.0
        val sumCubes = spans.sumOf { it.pow(3) }
        val sumSpans = spans.sum()
        return if (sumSpans > 0) sqrt(sumCubes / sumSpans) else 0.0
    }

    /**
     * Conductor Blowout Angle and Horizontal Displacement
     */
    fun calculateBlowout(
        conductorDiameterInches: Double, // in
        conductorWeightLbPerFt: Double, // lb/ft
        windSpeedMph: Double, // mph
        spanSagFt: Double, // ft
        iceThicknessInches: Double = 0.0
    ): BlowoutResult {
        val totalDiameter = conductorDiameterInches + 2.0 * iceThicknessInches
        // Wind pressure P = 0.00256 * V^2 (psf)
        val windPressurePsf = 0.00256 * windSpeedMph.pow(2)
        // Transverse wind force per foot (lb/ft) = pressure * (d/12)
        val windForceLbPerFt = windPressurePsf * (totalDiameter / 12.0)
        // Ice weight: density ~ 57 lb/ft3
        val iceWeightLbPerFt = (PI / 4.0) * ((totalDiameter / 12.0).pow(2) - (conductorDiameterInches / 12.0).pow(2)) * 57.0
        val totalVerticalWeight = conductorWeightLbPerFt + iceWeightLbPerFt

        val blowoutAngleRad = atan2(windForceLbPerFt, totalVerticalWeight)
        val blowoutAngleDeg = Math.toDegrees(blowoutAngleRad)
        val horizontalDisplacementFt = spanSagFt * sin(blowoutAngleRad)
        val resultantLoadLbFt = sqrt(windForceLbPerFt.pow(2) + totalVerticalWeight.pow(2))

        return BlowoutResult(
            blowoutAngleDegrees = blowoutAngleDeg,
            horizontalDisplacementFt = horizontalDisplacementFt,
            windForcePerFt = windForceLbPerFt,
            verticalWeightPerFt = totalVerticalWeight,
            resultantLoadPerFt = resultantLoadLbFt
        )
    }

    /**
     * IEEE Std 738 Steady-State Thermal Ampacity Rating
     * Heat balance: qc + qr = I^2 * R(Tc) + qs => I = sqrt( (qc + qr - qs) / R(Tc) )
     */
    fun calculateAmpacityIeee738(
        conductorDiameterMm: Double = 28.1, // mm (e.g., Drake 795 kcmil ACSR)
        conductorResistance75cOhmPerKm: Double = 0.072, // ohm/km at 75°C
        maxAllowableTempC: Double = 75.0, // °C
        ambientTempC: Double = 35.0, // °C
        windSpeedMetersPerSec: Double = 0.61, // 2 ft/s = ~0.61 m/s
        solarIrradianceWPerM2: Double = 1000.0, // W/m^2
        solarAbsorptivity: Double = 0.8,
        emissivity: Double = 0.8
    ): AmpacityResult {
        val dMeters = conductorDiameterMm / 1000.0
        val deltaT = max(1.0, maxAllowableTempC - ambientTempC)
        val tFilm = (maxAllowableTempC + ambientTempC) / 2.0 + 273.15 // K

        // Convection heat loss (Simplified forced convection IEEE 738)
        val airDensity = 1.293 - (0.00152 * (tFilm - 273.15))
        val v = max(0.2, windSpeedMetersPerSec)
        val reynolds = (dMeters * v * airDensity) / (1.81e-5)
        val qc = (1.01 + 0.371 * reynolds.pow(0.52)) * 0.0242 * deltaT // W/m

        // Radiative heat loss qr = pi * D * epsilon * sigma * (Tc^4 - Ta^4)
        val sigma = 5.670374e-8
        val tcK = maxAllowableTempC + 273.15
        val taK = ambientTempC + 273.15
        val qr = PI * dMeters * emissivity * sigma * (tcK.pow(4) - taK.pow(4))

        // Solar heat gain qs = alpha * Qs * D
        val qs = solarAbsorptivity * solarIrradianceWPerM2 * dMeters

        // Conductor resistance per meter
        val rPerMeter = conductorResistance75cOhmPerKm / 1000.0

        val netHeatLoss = max(0.0, qc + qr - qs)
        val ampacity = sqrt(netHeatLoss / rPerMeter)

        return AmpacityResult(
            ampacityAmperes = ampacity,
            convectionLossWPerM = qc,
            radiationLossWPerM = qr,
            solarGainWPerM = qs,
            mvaRating3Phase230kV = (sqrt(3.0) * 230.0 * ampacity) / 1000.0,
            mvaRating3Phase500kV = (sqrt(3.0) * 500.0 * ampacity) / 1000.0
        )
    }
}

data class BlowoutResult(
    val blowoutAngleDegrees: Double,
    val horizontalDisplacementFt: Double,
    val windForcePerFt: Double,
    val verticalWeightPerFt: Double,
    val resultantLoadPerFt: Double
)

data class AmpacityResult(
    val ampacityAmperes: Double,
    val convectionLossWPerM: Double,
    val radiationLossWPerM: Double,
    val solarGainWPerM: Double,
    val mvaRating3Phase230kV: Double,
    val mvaRating3Phase500kV: Double
)
