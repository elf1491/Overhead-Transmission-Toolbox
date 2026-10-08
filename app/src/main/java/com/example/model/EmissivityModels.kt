package com.example.model

import kotlin.math.*

enum class ConductorSurfaceCondition(
    val label: String,
    val defaultEmissivity: Double,
    val defaultAbsorptivity: Double,
    val description: String,
    val standardCitation: String
) {
    NEW_UNWEATHERED(
        "New / Shiny Aluminum (< 6 months)",
        0.23,
        0.23,
        "Bright mill finish, untarnished aluminum. Low thermal radiation emission and low solar absorption.",
        "IEEE Std 738 Clause 6.2 / House & Tuttle"
    ),
    LIGHTLY_WEATHERED(
        "Lightly Weathered (1 - 2 years)",
        0.50,
        0.50,
        "Moderate atmospheric oxidation. Dull matte gray aluminum oxide patina.",
        "IEEE Std 738 Table 3"
    ),
    AGED_RURAL(
        "Aged Rural Environment (> 5 years)",
        0.70,
        0.70,
        "Mature natural oxide film in clean atmosphere with minimal airborne particulates.",
        "CIGRE TB 601 / EPRI Guidelines"
    ),
    INDUSTRIAL_POLLUTED(
        "Industrial / High Pollution (> 5 years)",
        0.85,
        0.90,
        "Darkened soot/chemical deposit coating. High thermal radiation emission but elevated solar heat absorption.",
        "IEEE Std 738 Section 6"
    ),
    SEVERE_CONTAMINATION(
        "Severe Heavy Contamination / Coal / Marine",
        0.90,
        0.95,
        "Very dark, heavily pitted and contaminated surface with maximum radiative and solar coupling.",
        "CIGRE TB 601 Section 3.2"
    ),
    HIGH_EMISSIVITY_COATED(
        "High-Emissivity Low-Solar Engineered Coating",
        0.92,
        0.35,
        "Engineered ceramic/inorganic surface coating for HTLS uprating. Maximum radiative cooling with minimal solar gain.",
        "EPRI Conductor Coating Uprating Studies"
    ),
    STANDARD_DESIGN_DEFAULT(
        "IEEE 738 Standard Design Default",
        0.80,
        0.80,
        "Conservative standard engineering benchmark recommended for planning studies where surface condition is unmeasured.",
        "IEEE Std 738-2012 / FERC Order 881"
    )
}

data class EmissivityResult(
    val emissivity: Double,
    val solarAbsorptivity: Double,
    val radiationHeatLossWPerM: Double,
    val solarHeatGainWPerM: Double,
    val netRadiativeCoolingWPerM: Double,
    val ampacityDrakeAmps: Double,
    val ampacityVariancePercentFromDefault: Double,
    val engineeringAssessment: String
)

object EmissivityEngine {

    /**
     * Calculates surface emissivity and solar absorptivity based on surface condition,
     * exposure duration, and analyzes its thermal impact on steady-state ampacity per IEEE Std 738.
     */
    fun calculateEmissivityImpact(
        surfaceCondition: ConductorSurfaceCondition,
        customEmissivity: Double? = null,
        customAbsorptivity: Double? = null,
        conductorDiameterMm: Double = 28.1, // Drake 795 ACSR
        conductorResistance75cOhmPerKm: Double = 0.072,
        operatingTempC: Double = 75.0,
        ambientTempC: Double = 35.0,
        windSpeedMPerS: Double = 0.61,
        solarIrradianceWPerM2: Double = 1000.0
    ): EmissivityResult {
        val eps = customEmissivity ?: surfaceCondition.defaultEmissivity
        val alpha = customAbsorptivity ?: surfaceCondition.defaultAbsorptivity

        val dMeters = conductorDiameterMm / 1000.0
        val deltaT = max(1.0, operatingTempC - ambientTempC)
        val tFilm = (operatingTempC + ambientTempC) / 2.0 + 273.15 // K

        // Convection (IEEE 738)
        val airDensity = 1.293 - (0.00152 * (tFilm - 273.15))
        val v = max(0.2, windSpeedMPerS)
        val reynolds = (dMeters * v * airDensity) / (1.81e-5)
        val qc = (1.01 + 0.371 * reynolds.pow(0.52)) * 0.0242 * deltaT

        // Radiation: qr = pi * D * epsilon * sigma * (Tc^4 - Ta^4)
        val sigma = 5.670374e-8
        val tcK = operatingTempC + 273.15
        val taK = ambientTempC + 273.15
        val qr = PI * dMeters * eps * sigma * (tcK.pow(4) - taK.pow(4))

        // Solar heat gain: qs = alpha * Qs * D
        val qs = alpha * solarIrradianceWPerM2 * dMeters

        val rPerMeter = conductorResistance75cOhmPerKm / 1000.0
        val netHeatLoss = max(0.0, qc + qr - qs)
        val ampacity = sqrt(netHeatLoss / rPerMeter)

        // Baseline comparison against standard IEEE 738 default (eps=0.8, alpha=0.8)
        val qrBase = PI * dMeters * 0.8 * sigma * (tcK.pow(4) - taK.pow(4))
        val qsBase = 0.8 * solarIrradianceWPerM2 * dMeters
        val baseAmp = sqrt(max(0.0, qc + qrBase - qsBase) / rPerMeter)
        val variancePct = if (baseAmp > 0) ((ampacity - baseAmp) / baseAmp) * 100.0 else 0.0

        val assessment = when {
            surfaceCondition == ConductorSurfaceCondition.NEW_UNWEATHERED ->
                "CAUTION FOR NEW LINES: Shiny aluminum (ε=0.23) radiates ~70% less heat than aged conductors. Under low wind, new conductors run 10-15°C hotter at rated current. Allow 1-2 years of oxidation before applying full aged ratings."
            surfaceCondition == ConductorSurfaceCondition.HIGH_EMISSIVITY_COATED ->
                "OPTIMAL HTLS RATING: High emissivity (ε=0.92) paired with low solar absorptivity (α=0.35) yields a ~10-18% thermal capacity boost, lowering operating temperatures by up to 12°C."
            variancePct < -5.0 ->
                "REDUCED AMPACITY: Lower emissivity reduces radiative cooling capacity by ${String.format("%.1f", abs(variancePct))}%. Adjust thermal rating downward."
            else ->
                "STANDARD COMPLIANT: Surface radiative properties align well with standard IEEE 738 utility planning assumptions."
        }

        return EmissivityResult(
            emissivity = eps,
            solarAbsorptivity = alpha,
            radiationHeatLossWPerM = qr,
            solarHeatGainWPerM = qs,
            netRadiativeCoolingWPerM = qr - qs,
            ampacityDrakeAmps = ampacity,
            ampacityVariancePercentFromDefault = variancePct,
            engineeringAssessment = assessment
        )
    }
}
