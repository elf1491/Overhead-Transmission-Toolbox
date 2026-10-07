package com.example.model

import kotlin.math.*

data class EmfPoint(
    val lateralDistanceFt: Double,
    val electricFieldKvPerM: Double,
    val magneticFieldMilliGauss: Double
)

data class EmfProfileResult(
    val centerLineElectricFieldKvM: Double,
    val maxElectricFieldKvM: Double,
    val edgeOfRowElectricFieldKvM: Double,
    val centerLineMagneticFieldMG: Double,
    val maxMagneticFieldMG: Double,
    val edgeOfRowMagneticFieldMG: Double,
    val profilePoints: List<EmfPoint>,
    val icnirpLimitPassed: Boolean, // Electric: 5 kV/m public, Magnetic: 2000 mG (200 uT)
    val evaluationNotes: String
)

data class RowWidthResult(
    val structureBaseWidthFt: Double,
    val maxConductorBlowoutFt: Double,
    val electricalClearanceBufferFt: Double,
    val vegetationDangerTreeBufferFt: Double,
    val totalRecommendedRowWidthFt: Double,
    val nescRuleCitation: String
)

data class CoronaNoiseResult(
    val surfaceGradientKvPerCm: Double,
    val wetRainAudibleNoisedBa: Double,
    val fairWeatherAudibleNoisedBa: Double,
    val coronaLossKwPerKm: Double,
    val epriStandardPassed: Boolean
)

object RowEnvironmentalEngine {

    /**
     * Electric and Magnetic Field Profile at 1 meter above ground (IEEE Std 644)
     */
    fun calculateEmfProfile(
        lineVoltageKv: Double = 230.0,
        lineCurrentAmps: Double = 800.0,
        conductorHeightAboveGroundFt: Double = 35.0,
        phaseSpacingFt: Double = 20.0,
        rowWidthFt: Double = 125.0
    ): EmfProfileResult {
        val halfRow = rowWidthFt / 2.0
        val points = mutableListOf<EmfPoint>()

        val heightM = conductorHeightAboveGroundFt * 0.3048
        val spacingM = phaseSpacingFt * 0.3048
        val vPhaseKv = lineVoltageKv / sqrt(3.0)

        // Generate points from -halfRow - 40ft to +halfRow + 40ft
        val rangeFt = halfRow + 50.0
        val steps = 30
        var maxE = 0.0
        var maxB = 0.0
        var centerE = 0.0
        var centerB = 0.0
        var edgeE = 0.0
        var edgeB = 0.0

        for (i in 0..steps) {
            val xFt = -rangeFt + (2.0 * rangeFt * i / steps)
            val xM = xFt * 0.3048

            // Simplified multi-phase field integration
            // Electric field (kV/m): quasi-static image charge approximation
            val rCenter = sqrt(xM.pow(2) + heightM.pow(2))
            val rLeft = sqrt((xM + spacingM).pow(2) + heightM.pow(2))
            val rRight = sqrt((xM - spacingM).pow(2) + heightM.pow(2))

            // Peak spatial E-field kV/m
            val eVal = (vPhaseKv / 5.0) * ( (1.0 / rLeft) + (1.2 / rCenter) + (1.0 / rRight) ) * 0.18
            // Magnetic field B in microTesla -> convert to milliGauss (1 uT = 10 mG)
            // B = (mu0 * I) / (2 * pi * r) => mu0 / 2pi = 2e-7
            val bTesla = 2.0e-7 * lineCurrentAmps * ( (1.0 / rLeft) + (1.0 / rCenter) + (1.0 / rRight) )
            val bMicroTesla = bTesla * 1.0e6
            val bMilliGauss = bMicroTesla * 10.0

            if (abs(xFt) < 3.0) {
                centerE = eVal
                centerB = bMilliGauss
            }
            if (abs(abs(xFt) - halfRow) < 5.0) {
                edgeE = eVal
                edgeB = bMilliGauss
            }
            if (eVal > maxE) maxE = eVal
            if (bMilliGauss > maxB) maxB = bMilliGauss

            points.add(EmfPoint(xFt, eVal, bMilliGauss))
        }

        val icnirpPassed = maxE <= 5.0 && edgeB <= 2000.0 // ICNIRP public exposure: 5.0 kV/m, 2000 mG
        val notes = if (edgeE <= 1.0 && edgeB <= 50.0) {
            "Well within all international exposure limits. Edge of ROW field is benign (<1.0 kV/m and <50 mG)."
        } else {
            "Within ICNIRP standard (5 kV/m public limit), but edge-of-ROW EMF warrants notification in residential adjacency."
        }

        return EmfProfileResult(
            centerLineElectricFieldKvM = centerE,
            maxElectricFieldKvM = maxE,
            edgeOfRowElectricFieldKvM = edgeE,
            centerLineMagneticFieldMG = centerB,
            maxMagneticFieldMG = maxB,
            edgeOfRowMagneticFieldMG = edgeB,
            profilePoints = points,
            icnirpLimitPassed = icnirpPassed,
            evaluationNotes = notes
        )
    }

    /**
     * Right-of-Way Corridor Width (NESC Rule 234)
     * Total Width = Structure Base + 2 * (Conductor Blowout + Electrical Clearance + Safety Buffer)
     */
    fun calculateRowWidth(
        lineVoltageKv: Double = 230.0,
        structureFootprintWidthFt: Double = 25.0,
        spanLengthFt: Double = 800.0,
        conductorSagFt: Double = 22.0,
        windPressurePsf: Double = 6.0 // 6 psf standard NESC blowout wind
    ): RowWidthResult {
        // Conductor blowout swing ~ sag * sin(theta) where theta ~ 25 deg under 6 psf
        val blowoutFt = conductorSagFt * sin(Math.toRadians(25.0))
        // NESC Rule 234 electrical clearance for 230 kV: base 7.5 ft + (kV - 22) * 0.4 / 12
        val electricalClearanceFt = 7.5 + ((lineVoltageKv - 22.0) * 0.4 / 12.0)
        val vegetationBufferFt = 10.0 // danger tree fall-in / operational buffer

        val sideRequirement = blowoutFt + electricalClearanceFt + vegetationBufferFt
        val totalWidth = structureFootprintWidthFt + (2.0 * sideRequirement)

        return RowWidthResult(
            structureBaseWidthFt = structureFootprintWidthFt,
            maxConductorBlowoutFt = blowoutFt,
            electricalClearanceBufferFt = electricalClearanceFt,
            vegetationDangerTreeBufferFt = vegetationBufferFt,
            totalRecommendedRowWidthFt = ceil(totalWidth),
            nescRuleCitation = "NESC Table 234-1 & IEEE Std 1428 Corridor Guidelines"
        )
    }

    /**
     * Corona Loss & Audible Noise (EPRI Transmission Line Reference Book - 200 kV and Above)
     */
    fun calculateCoronaNoise(
        lineVoltageKv: Double = 500.0,
        subconductorsPerBundle: Int = 3,
        subconductorDiameterCm: Double = 2.81, // ~1.1 inch
        bundleSpacingCm: Double = 45.7 // 18 inches
    ): CoronaNoiseResult {
        // Approximate maximum conductor surface gradient (kV_rms / cm)
        val vPhase = lineVoltageKv / sqrt(3.0)
        val rEq = subconductorDiameterCm / 2.0
        val eGradient = (vPhase / (rEq * ln(1000.0 / rEq))) * 0.75 // kV/cm

        // EPRI wet-conductor rain audible noise formula (dBA at ROW edge ~ 15m)
        val wetNoise = 52.0 + (12.0 * log10(max(1.0, subconductorsPerBundle.toDouble()))) + (2.5 * (eGradient - 16.0))
        val fairNoise = max(20.0, wetNoise - 25.0)

        // Corona losses in heavy rain (kW/km)
        val coronaLoss = max(5.0, 15.0 + 3.2 * (eGradient - 14.0).pow(1.8))

        return CoronaNoiseResult(
            surfaceGradientKvPerCm = eGradient,
            wetRainAudibleNoisedBa = wetNoise,
            fairWeatherAudibleNoisedBa = fairNoise,
            coronaLossKwPerKm = coronaLoss,
            epriStandardPassed = wetNoise <= 58.0 // standard target <= 53-58 dBA at ROW boundary
        )
    }
}
