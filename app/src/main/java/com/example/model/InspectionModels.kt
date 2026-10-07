package com.example.model

import kotlin.math.*

enum class DefectUrgency(val code: String, val label: String, val timeframe: String, val badgeColorHex: Long) {
    P1("P1", "Immediate / Emergency", "Remediate < 24-48 hours", 0xFFEF476F),
    P2("P2", "High Priority", "Schedule repair < 30 days", 0xFFFF7A00),
    P3("P3", "Medium Priority", "Address in 6-12 months maintenance cycle", 0xFFFFD166),
    P4("P4", "Low / Monitored", "Monitor during next patrol cycle", 0xFF00ADB5),
    P5("P5", "Satisfactory", "No action required", 0xFF06D6A0)
}

data class DefectAssessmentResult(
    val priority: DefectUrgency,
    val score: Int, // 0 to 100
    val safetyFactorEstimate: Double,
    val nescViolationPotential: Boolean,
    val recommendedActions: List<String>
)

data class WoodPoleStrengthResult(
    val originalDiameterInches: Double,
    val effectiveDiameterInches: Double,
    val originalSectionModulusIn3: Double,
    val remainingSectionModulusIn3: Double,
    val remainingMomentCapacityPercent: Double,
    val structuralVerdict: String, // PASS, REINFORCE (C-Truss), REJECT (Red Tag)
    val requiresTagging: Boolean
)

data class LatticeBucklingResult(
    val slendernessRatioKlR: Double,
    val criticalStressFcrKsi: Double,
    val allowableCompressiveLoadKips: Double,
    val bucklingMode: String, // Elastic (Euler) vs Inelastic
    val asce10LimitPassed: Boolean
)

object InspectionEngine {

    /**
     * Transmission Defect Scoring Matrix
     */
    fun evaluateDefects(
        structureType: String, // Lattice, Tubular Steel, Wood, Concrete
        foundationDefectLevel: Int, // 0 (none) to 3 (severe spalling / exposed rebar / uplift)
        structuralMemberBent: Boolean,
        corrosionGrade: Int, // 0 (bright zinc) to 4 (flaking laminar rust with section loss)
        insulatorDamageCount: Int, // broken sheds / flashover
        hardwareLooseCount: Int, // missing cotter keys / loose bolts
        woodDecayOrPoleShellLoss: Boolean = false
    ): DefectAssessmentResult {
        var score = 0
        val actions = mutableListOf<String>()
        var nescViolation = false

        score += foundationDefectLevel * 18
        if (foundationDefectLevel >= 2) {
            actions.add("Foundation stabilization required; inspect for grillage corrosion or anchor bolt necking.")
            nescViolation = true
        }

        if (structuralMemberBent) {
            score += 25
            actions.add("Structural angle/pole deflection exceeds tolerance; perform ASCE 10 / NESC Rule 260 buckling re-rating.")
            nescViolation = true
        }

        score += corrosionGrade * 12
        if (corrosionGrade >= 3) {
            actions.add("Laminar rust observed; measure ultrasonic thickness (UT) and schedule abrasive blasting/epoxy painting.")
        }

        score += insulatorDamageCount * 14
        if (insulatorDamageCount >= 2) {
            actions.add("Insulator string integrity compromised; risk of phase-to-ground flashover under wet switching surge.")
            nescViolation = true
        }

        score += hardwareLooseCount * 8
        if (hardwareLooseCount >= 3) {
            actions.add("Cotter key / bolt hardware missing; schedule live-line hardware replacement.")
        }

        if (woodDecayOrPoleShellLoss) {
            score += 30
            actions.add("Critical shell thickness reduction detected; trigger ANSI O5.1 sounding & boring protocol.")
            nescViolation = true
        }

        val priority = when {
            score >= 65 -> DefectUrgency.P1
            score >= 45 -> DefectUrgency.P2
            score >= 25 -> DefectUrgency.P3
            score >= 10 -> DefectUrgency.P4
            else -> DefectUrgency.P5
        }

        val estimatedSf = max(0.8, 2.5 - (score / 50.0))

        return DefectAssessmentResult(
            priority = priority,
            score = score,
            safetyFactorEstimate = estimatedSf,
            nescViolationPotential = nescViolation,
            recommendedActions = actions.ifEmpty { listOf("No significant defects detected; maintain regular visual drone/ground patrol.") }
        )
    }

    /**
     * Wood Pole Sounding & Shell Remaining Strength (ASCE Manual 91 & NESC Rule 261)
     * Z_orig = pi * D^3 / 32
     * Z_remaining = pi * (D^4 - d_inner^4) / (32 * D)
     */
    fun calculateWoodPoleCapacity(
        originalCircumferenceInches: Double = 42.0, // Class 2 ~ 40-45"
        soundShellThicknessInches: Double = 3.5, // measured sound shell
        groundLineDecayDepthInches: Double = 0.5 // external decay depth to remove
    ): WoodPoleStrengthResult {
        val outerDiam = (originalCircumferenceInches / PI) - (2.0 * groundLineDecayDepthInches)
        val rOuter = outerDiam / 2.0
        val rInner = max(0.0, rOuter - soundShellThicknessInches)
        val innerDiam = rInner * 2.0

        val zOrig = (PI * outerDiam.pow(3)) / 32.0
        val zRem = (PI * (outerDiam.pow(4) - innerDiam.pow(4))) / (32.0 * outerDiam)
        val remainingCapacityPct = (zRem / zOrig) * 100.0

        val (verdict, needsTag) = when {
            remainingCapacityPct >= 75.0 -> Pair("PASS: Structural capacity exceeds standard NESC Grade B/C requirements.", false)
            remainingCapacityPct >= 60.0 -> Pair("REINFORCE: Pole meets criteria for C-Truss steel splinting or fiberglass reinforcement.", false)
            else -> Pair("REJECT / RED TAG: Remaining moment capacity below 60%. Schedule emergency replacement.", true)
        }

        return WoodPoleStrengthResult(
            originalDiameterInches = originalCircumferenceInches / PI,
            effectiveDiameterInches = outerDiam,
            originalSectionModulusIn3 = zOrig,
            remainingSectionModulusIn3 = zRem,
            remainingMomentCapacityPercent = remainingCapacityPct,
            structuralVerdict = verdict,
            requiresTagging = needsTag
        )
    }

    /**
     * ASCE 10 Lattice Steel Tower Member Compression Buckling
     * KL/r slenderness ratio, Fy = 36 ksi or 50 ksi
     */
    fun calculateLatticeBuckling(
        sectionName: String = "L 3 x 3 x 1/4",
        areaSqIn: Double = 1.44, // in^2
        radiusOfGyrationInches: Double = 0.59, // r_z min
        unbracedLengthInches: Double = 60.0, // 5 ft
        yieldStrengthKsi: Double = 36.0, // A36 or Grade 50
        kFactor: Double = 1.0 // end restraint factor
    ): LatticeBucklingResult {
        val klR = (kFactor * unbracedLengthInches) / radiusOfGyrationInches
        val modulusE = 29000.0 // steel ksi
        val cc = sqrt((2.0 * PI.pow(2) * modulusE) / yieldStrengthKsi)

        val fCr: Double
        val mode: String
        if (klR <= cc) {
            // Inelastic parabolic buckling
            fCr = (1.0 - (klR.pow(2) / (2.0 * cc.pow(2)))) * yieldStrengthKsi
            mode = "Inelastic Column Buckling"
        } else {
            // Elastic Euler buckling
            fCr = (PI.pow(2) * modulusE) / klR.pow(2)
            mode = "Elastic Euler Buckling"
        }

        val allowableLoad = fCr * areaSqIn
        val passesAsce10 = klR <= 200.0 // ASCE 10 compression member limit KL/r <= 200 (or 250 for redundant)

        return LatticeBucklingResult(
            slendernessRatioKlR = klR,
            criticalStressFcrKsi = fCr,
            allowableCompressiveLoadKips = allowableLoad,
            bucklingMode = mode,
            asce10LimitPassed = passesAsce10
        )
    }
}
