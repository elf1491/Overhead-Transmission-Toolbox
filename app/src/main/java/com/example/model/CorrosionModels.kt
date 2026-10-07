package com.example.model

import kotlin.math.*

enum class IsoCorrosivityCategory(
    val code: String,
    val description: String,
    val typicalZincLossUmPerYr: Double, // um/yr
    val environmentExample: String
) {
    C1("C1 - Very Low", "Dry, clean indoor/substation", 0.1, "Enclosed control rooms, arid deserts"),
    C2("C2 - Low", "Atmosphere with low pollution", 0.5, "Rural areas, inland low humidity"),
    C3("C3 - Medium", "Moderate SO2 and humidity", 1.5, "Urban & light industrial areas, inland plains"),
    C4("C4 - High", "High SO2, moderate salinity", 3.0, "Heavy industrial areas, coastal 3-10 km"),
    C5("C5 - Very High", "High salinity and aggressive air", 6.0, "Marine shoreline <3 km, heavy chemical industrial"),
    CX("CX - Extreme", "Extreme marine or industrial offshore", 15.0, "Offshore crossings, severe salt spray splash zones")
}

data class ZincLifeResult(
    val coatingThicknessUm: Double,
    val coatingWeightOzPerSqFt: Double,
    val estimatedYearsToSteelExposure: Double,
    val estimatedYearsToStructuralDefect: Double,
    val maintenanceRecommendation: String,
    val corrosivityCategory: IsoCorrosivityCategory
)

data class SoilAnchorResult(
    val initialRadiusInches: Double,
    val projectedLossMilsAtDesignLife: Double,
    val remainingDiameterInches: Double,
    val remainingAreaPercent: Double,
    val originalTensileCapacityLbs: Double,
    val remainingTensileCapacityLbs: Double,
    val corrosionRiskRating: String // Low, Moderate, High, Severe
)

data class AcCorrosionResult(
    val acCurrentDensityAmpsPerM2: Double,
    val riskClassification: String,
    val requiresMitigation: Boolean,
    val recommendedAction: String
)

data class SacrificialAnodeResult(
    val requiredCurrentAmps: Double,
    val totalAnodeMassKg: Double,
    val numberOfAnodes: Int,
    val anodeType: String
)

object CorrosionEngine {

    /**
     * Atmospheric Galvanizing Life calculation
     */
    fun calculateGalvanizingLife(
        coatingThicknessUm: Double = 85.0, // ASTM A123 typical 85 um ~ 2.0 oz/ft2
        corrosivityCategory: IsoCorrosivityCategory = IsoCorrosivityCategory.C3
    ): ZincLifeResult {
        val lossRate = corrosivityCategory.typicalZincLossUmPerYr
        val yearsToSteel = coatingThicknessUm / lossRate
        // Once zinc is gone, bare carbon steel corrodes 10x-20x faster (~30-100 um/yr)
        val yearsToStructuralIssue = yearsToSteel + 15.0
        val ozPerSqFt = (coatingThicknessUm / 1000.0) * 0.03937 * 144.0 * (450.0 / 1728.0) * 16.0 / 10.0 // approx conversion

        val recommendation = when {
            yearsToSteel > 40 -> "Acceptable. Long-term coating integrity verified. Standard 10-year visual patrol."
            yearsToSteel > 20 -> "Moderate lifespan. Schedule non-destructive dry film thickness (DFT) checks at year 15."
            yearsToSteel > 10 -> "Action Required: Consider epoxy/polyurethane barrier overcoat or high-performance zinc spray."
            else -> "High Risk: Rapid atmospheric depletion. Recommend metallizing (thermal spray zinc/aluminum) or duplex coating."
        }

        return ZincLifeResult(
            coatingThicknessUm = coatingThicknessUm,
            coatingWeightOzPerSqFt = coatingThicknessUm * 0.024,
            estimatedYearsToSteelExposure = yearsToSteel,
            estimatedYearsToStructuralDefect = yearsToStructuralIssue,
            maintenanceRecommendation = recommendation,
            corrosivityCategory = corrosivityCategory
        )
    }

    /**
     * Romanoff Soil Corrosion Model for Buried Steel Anchors & Grillage Foundations
     * Penetration P = k * t^n
     */
    fun calculateAnchorCorrosion(
        rodDiameterInches: Double = 1.0, // standard guy anchor rod 0.75" to 1.25"
        soilResistivityOhmM: Double = 50.0,
        soilPh: Double = 6.5,
        exposureYears: Double = 40.0,
        isGalvanized: Boolean = true
    ): SoilAnchorResult {
        // Soil corrosivity factor k based on resistivity and pH
        val baseK = when {
            soilResistivityOhmM < 10.0 -> 8.0 // Severe
            soilResistivityOhmM < 25.0 -> 5.5 // High
            soilResistivityOhmM < 50.0 -> 3.5 // Moderate
            soilResistivityOhmM < 100.0 -> 2.2 // Low
            else -> 1.2 // Very low
        }
        val phMultiplier = if (soilPh < 5.0 || soilPh > 8.5) 1.5 else 1.0
        val effectiveK = baseK * phMultiplier

        val effectiveYears = if (isGalvanized) max(0.0, exposureYears - 15.0) else exposureYears
        val exponent = 0.55 // Romanoff exponent for aerated to poorly drained soils
        // Penetration in mils (1 mil = 0.001 inch)
        val penetrationMils = effectiveK * effectiveYears.pow(exponent) * 12.0
        val penetrationInches = penetrationMils / 1000.0

        val initialRadius = rodDiameterInches / 2.0
        val remainingRadius = max(0.05, initialRadius - penetrationInches)
        val remainingDiameter = remainingRadius * 2.0

        val initialArea = PI * initialRadius.pow(2)
        val remainingArea = PI * remainingRadius.pow(2)
        val remainingAreaPct = (remainingArea / initialArea) * 100.0

        val yieldStressPsi = 60000.0 // Grade 60 steel rod
        val initialCapacity = initialArea * yieldStressPsi
        val remainingCapacity = remainingArea * yieldStressPsi

        val riskRating = when {
            soilResistivityOhmM < 15.0 || soilPh < 5.0 -> "Severe (Cathodic protection or anchor replacement recommended)"
            soilResistivityOhmM < 30.0 -> "High (Targeted soil-line excavation & testing required)"
            soilResistivityOhmM < 60.0 -> "Moderate (Routine acoustic or test pit inspection)"
            else -> "Low (Stable soil environment)"
        }

        return SoilAnchorResult(
            initialRadiusInches = initialRadius,
            projectedLossMilsAtDesignLife = penetrationMils,
            remainingDiameterInches = remainingDiameter,
            remainingAreaPercent = remainingAreaPct,
            originalTensileCapacityLbs = initialCapacity,
            remainingTensileCapacityLbs = remainingCapacity,
            corrosionRiskRating = riskRating
        )
    }

    /**
     * AC Induced Corrosion on Colocated Utilities (ISO 18086 / NACE SP0106)
     * Current density: J_ac = (8 * V_ac) / (pi * rho * d)
     */
    fun calculateAcInterference(
        inducedAcVoltageVolts: Double = 18.0, // V_ac induced from transmission lines
        soilResistivityOhmM: Double = 40.0, // ohm-meter
        holidayDiameterMm: Double = 11.28 // standard 1 cm^2 circular coating defect (d = 11.28 mm)
    ): AcCorrosionResult {
        val dMeters = holidayDiameterMm / 1000.0
        // J_ac in A/m^2
        val jAc = (8.0 * inducedAcVoltageVolts) / (PI * soilResistivityOhmM * dMeters)

        val (classification, needsMitigation, action) = when {
            jAc < 30.0 -> Triple(
                "Low Risk (<30 A/m²)",
                false,
                "Acceptable. AC corrosion unlikely to occur under standard cathodic protection."
            )
            jAc in 30.0..100.0 -> Triple(
                "Medium Risk (30-100 A/m²)",
                true,
                "Mitigation Recommended: Evaluate DC polarization potential and install AC decoupling mitigation."
            )
            else -> Triple(
                "High / Severe Risk (>100 A/m²)",
                true,
                "Urgent Mitigation Required: Rapid localized metal perforation risk. Install solid-state decouplers and zinc grounding ribbons."
            )
        }

        return AcCorrosionResult(
            acCurrentDensityAmpsPerM2 = jAc,
            riskClassification = classification,
            requiresMitigation = needsMitigation,
            recommendedAction = action
        )
    }

    /**
     * Sacrificial Anode Cathodic Protection Sizing (Zinc or Magnesium for guy anchors/grillages)
     */
    fun calculateCathodicProtection(
        surfaceAreaSqFt: Double = 150.0, // e.g. grillage foundation + anchor rods
        currentDensityMaPerSqFt: Double = 2.0, // mA/sq ft
        designLifeYears: Double = 30.0,
        anodeType: String = "Magnesium (H-1)" // or Zinc
    ): SacrificialAnodeResult {
        val totalCurrentAmps = (surfaceAreaSqFt * currentDensityMaPerSqFt) / 1000.0
        val ampHoursPerYear = totalCurrentAmps * 8760.0

        // Magnesium capacity ~ 1100 A-h/kg, efficiency ~ 50% => 550 A-h/kg net
        // Zinc capacity ~ 820 A-h/kg, efficiency ~ 90% => 738 A-h/kg net
        val capacityAhPerKg = if (anodeType.contains("Zinc", ignoreCase = true)) 738.0 else 550.0
        val totalKgNeeded = (ampHoursPerYear * designLifeYears) / capacityAhPerKg
        val standardAnodeWeightKg = if (anodeType.contains("Zinc", ignoreCase = true)) 14.5 else 7.7
        val numAnodes = ceil(totalKgNeeded / standardAnodeWeightKg).toInt()

        return SacrificialAnodeResult(
            requiredCurrentAmps = totalCurrentAmps,
            totalAnodeMassKg = totalKgNeeded,
            numberOfAnodes = max(1, numAnodes),
            anodeType = anodeType
        )
    }
}
