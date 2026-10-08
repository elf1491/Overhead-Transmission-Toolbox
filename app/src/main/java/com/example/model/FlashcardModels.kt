package com.example.model

/**
 * Represents a study/flashcard item derived from key transmission glossary terms and standards.
 */
data class FlashcardItem(
    val id: String,
    val term: String,
    val standardRef: String,
    val category: String,
    val shortDefinition: String, // Concise, high-impact definition tailored for flashcards
    val keyTakeawayOrFormula: String, // Formula, rule of thumb, or key regulatory citation
    val fullContext: String,
    val questionPrompt: String = "What is the standard engineering definition and regulatory application of $term?"
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val standardRef: String,
    val category: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class AssessmentStats(
    val totalAnswered: Int = 0,
    val totalCorrect: Int = 0,
    val streak: Int = 0,
    val masteredTermIds: Set<String> = emptySet(),
    val reviewTermIds: Set<String> = emptySet()
)

object FlashcardRepository {

    /**
     * Curated list of flashcard cards with concise summaries, regulatory citations, and formulas.
     */
    val cards: List<FlashcardItem> = listOf(
        FlashcardItem(
            id = "fc_01",
            term = "Catenary Sag",
            standardRef = "IEEE Std 738 / ASCE 74",
            category = "Line Design",
            shortDefinition = "Hyperbolic cosine curve assumed by a flexible overhead cable hanging under distributed weight.",
            keyTakeawayOrFormula = "Sag S = (w · L²) / (8 · H) where H is horizontal tension.",
            fullContext = "Governs line clearance above ground datum under max operating temperature or heavy ice/wind load cases."
        ),
        FlashcardItem(
            id = "fc_02",
            term = "Ruling Span (Equivalent Span)",
            standardRef = "NESC Section 25 / CIGRE SC22",
            category = "Line Design",
            shortDefinition = "Hypothetical uniform level span whose tension changes identically to the average span in a dead-end section.",
            keyTakeawayOrFormula = "S_r = √(∑S_i³ / ∑S_i). Equalizes horizontal tension via suspension insulator swing.",
            fullContext = "Simplifies line design and sag-tension calculations across varied span lengths between strain towers."
        ),
        FlashcardItem(
            id = "fc_03",
            term = "NESC Rule 232 Ground Clearance",
            standardRef = "NESC C2-2023 Table 232-1",
            category = "Line Design",
            shortDefinition = "Mandatory legal vertical clearance of energized phase conductors above ground, roadways, rails, and water.",
            keyTakeawayOrFormula = "Base clearance up to 22 kV + 0.4 in/kV over 22 kV + 3% per 1,000 ft altitude above 3,300 ft.",
            fullContext = "Ensures safe clearance under maximum operating conductor temperature or extreme ice without wind."
        ),
        FlashcardItem(
            id = "fc_04",
            term = "Blowout Angle & Sway",
            standardRef = "NESC Rule 234 / ASCE 74",
            category = "Line Design",
            shortDefinition = "Angular deflection of overhead conductors from vertical under transverse wind pressure.",
            keyTakeawayOrFormula = "θ = arctan(F_wind / W_vertical). Dictates Right-of-Way boundary clearance.",
            fullContext = "Evaluated at rest and under blowout sway (typically 6 psf wind) to prevent air flashover to trees or structures."
        ),
        FlashcardItem(
            id = "fc_05",
            term = "FERC Order 881 Ambient-Adjusted Ratings (AAR)",
            standardRef = "FERC Order 881 / 18 CFR § 35.28",
            category = "Line Design",
            shortDefinition = "Federal rule requiring transmission providers to implement hourly ambient-adjusted thermal line ratings.",
            keyTakeawayOrFormula = "Requires hourly AARs evaluated at ≤5°F (≤2.8°C) temperature bins for at least 10 days ahead.",
            fullContext = "Replaces conservative seasonal static ratings with weather-responsive capacities, unlocking transmission transfer."
        ),
        FlashcardItem(
            id = "fc_06",
            term = "Conductor Emissivity & Solar Absorptivity",
            standardRef = "IEEE Std 738 Clause 6 / CIGRE TB 601",
            category = "Line Design",
            shortDefinition = "Radiative heat emission (ε) and solar absorption (α) coefficients governing steady-state conductor ampacity.",
            keyTakeawayOrFormula = "New shiny aluminum: ε=0.23, α=0.23. Weathered dark conductor: ε=0.70 to 0.90, α=0.90.",
            fullContext = "Weathered conductors emit far more thermal radiation, significantly boosting summer thermal current carrying capacity."
        ),
        FlashcardItem(
            id = "fc_07",
            term = "ISO 9223 Corrosivity Category",
            standardRef = "ISO 9223 / ISO 9224",
            category = "Corrosion",
            shortDefinition = "International standard classifying atmospheric corrosivity from C1 (Very Low) to CX (Extreme Offshore).",
            keyTakeawayOrFormula = "Zinc loss ranges from <0.1 µm/yr (C1) up to >8.4 µm/yr (CX) in marine/industrial zones.",
            fullContext = "Directly predicts galvanization life on steel lattice towers, guy anchors, and hardware."
        ),
        FlashcardItem(
            id = "fc_08",
            term = "Zinc Galvanization Consumption",
            standardRef = "ASTM A123 / ASTM A153",
            category = "Corrosion",
            shortDefinition = "Sacrificial anodic protection of structural steel by hot-dip zinc coating.",
            keyTakeawayOrFormula = "Typical coating ~85 µm (~610 g/m²). Useful life = Coating Thickness ÷ ISO Corrosion Rate.",
            fullContext = "Once sacrificial zinc is consumed, base carbon steel rusts at 10x-50x faster rate, threatening structural collapse."
        ),
        FlashcardItem(
            id = "fc_09",
            term = "Cathodic Protection 850 mV Criterion",
            standardRef = "NACE SP0169 / ISO 15589",
            category = "Corrosion",
            shortDefinition = "Electrochemical threshold for full corrosion mitigation of buried steel structures.",
            keyTakeawayOrFormula = "-0.850 V (or -850 mV) DC relative to a saturated copper-copper sulfate (CSE) reference electrode.",
            fullContext = "Applied to transmission tower grillage footings, guy anchor rods, and buried counterpoise wires."
        ),
        FlashcardItem(
            id = "fc_10",
            term = "Solar-Blind UV Corona Camera",
            standardRef = "EPRI Assessment Guide / IEEE Std 1829",
            category = "Inspection and Assessment",
            shortDefinition = "Optical inspection instrument detecting UV-C photon emissions (240-280 nm) from high-voltage electrical corona.",
            keyTakeawayOrFormula = "Detects micro-arcs, broken insulator bells, loose hardware, and contamination in broad daylight.",
            fullContext = "Solar radiation in the 240-280 nm band is fully absorbed by atmospheric ozone, enabling high-contrast daytime imaging."
        ),
        FlashcardItem(
            id = "fc_11",
            term = "Airborne LiDAR Inspection",
            standardRef = "NERC FAC-003 / EPRI LiDAR Guide",
            category = "Inspection and Assessment",
            shortDefinition = "Helicopter or drone laser scanning yielding high-density 3D georeferenced point clouds of transmission corridors.",
            keyTakeawayOrFormula = "Calibrates PLS-CADD wire sag models to within 1-2 cm under actual operating temperatures.",
            fullContext = "Validates NERC vegetation encroachments, conductor blowouts, and terrain clearance clearances across thousands of miles."
        ),
        FlashcardItem(
            id = "fc_12",
            term = "OSHA 1910.269 Minimum Approach Distance (MAD)",
            standardRef = "29 CFR § 1910.269 Table R-6",
            category = "Inspection and Assessment",
            shortDefinition = "Legally mandated minimum separation distance unqualified or live-line electrical workers must maintain from conductors.",
            keyTakeawayOrFormula = "MAD = Electrical Component (air breakdown) + Ergonomic Inadvertent Movement Buffer (0.61 m / 2 ft).",
            fullContext = "Adjusted for altitude (>900 m) and max transient overvoltage (T) to protect live-line maintenance crews from flashover."
        ),
        FlashcardItem(
            id = "fc_13",
            term = "Lightning Backflashover",
            standardRef = "IEEE Std 1243 / CIGRE TB 63",
            category = "Lightning and Grounding",
            shortDefinition = "Insulation flashover from a grounded steel tower structure back across the insulator string to a phase conductor.",
            keyTakeawayOrFormula = "V_cross = I_stroke · R_footing + L · (di/dt) - e_coupling · V_phase. Triggered when V_cross > CFO.",
            fullContext = "Occurs when lightning strikes the shield wire or tower top and high footing resistance drives tower potential above string withstand."
        ),
        FlashcardItem(
            id = "fc_14",
            term = "Tower Footing Resistance Target",
            standardRef = "IEEE Std 80 / IEEE Std 1243",
            category = "Lightning and Grounding",
            shortDefinition = "Grounding resistance from tower steel foundation to deep remote earth.",
            keyTakeawayOrFormula = "Target < 10 Ω (preferred) or < 15 Ω to prevent lightning backflashovers on EHV transmission lines.",
            fullContext = "Mitigated by installing buried radial counterpoise copper wires, chemical ground rods, or bentonite slurry."
        ),
        FlashcardItem(
            id = "fc_15",
            term = "Critical Flashover Voltage (CFO / U50)",
            standardRef = "IEEE Std 1313.1 / IEC 60071-1",
            category = "Lightning and Grounding",
            shortDefinition = "Peak impulse crest voltage (1.2/50 µs) at which an insulator string or air gap has a 50% probability of flashover.",
            keyTakeawayOrFormula = "Rule of thumb for standard porcelain bells: ~75-80 kV lightning impulse withstand per 5-3/4\" x 10\" bell.",
            fullContext = "Fundamental metric for insulation coordination against lightning strokes and switching surges."
        ),
        FlashcardItem(
            id = "fc_16",
            term = "Dalziel Human Body Tolerance Equation",
            standardRef = "IEEE Std 80-2013 / Dalziel",
            category = "Lightning and Grounding",
            shortDefinition = "Empirical physiological formula defining non-fibrillating AC shock current threshold for 99.5% of humans.",
            keyTakeawayOrFormula = "I_b = k / √t_s, where k = 0.116 for 50 kg body weight and t_s is shock duration in seconds.",
            fullContext = "Determines tolerable Step and Touch voltages around energized transmission towers and substation switchyards."
        ),
        FlashcardItem(
            id = "fc_17",
            term = "Aeolian Vibration & Strouhal Frequency",
            standardRef = "IEEE Std 563 / CIGRE SC22 WG01",
            category = "Conductor and Hardware",
            shortDefinition = "High-frequency (3 to 120 Hz) low-amplitude vortex shedding standing wave vibration on taut overhead conductors.",
            keyTakeawayOrFormula = "f = (St · V) / D, where St ≈ 0.185 (Strouhal number), V is transverse wind velocity, D is diameter.",
            fullContext = "Causes fatigue strand breakage under suspension clamp mouths; prevented using Stockbridge resonant dampers."
        ),
        FlashcardItem(
            id = "fc_18",
            term = "Conductor Galloping",
            standardRef = "CIGRE TB 322 / EPRI Galloping Guide",
            category = "Conductor and Hardware",
            shortDefinition = "Low-frequency (0.1 to 1.0 Hz) high-amplitude aerodynamic instability caused by moderate wind on asymmetric ice-coated conductors.",
            keyTakeawayOrFormula = "Vertical oscillations can span entire sag height, creating Den Hartog torsional instability.",
            fullContext = "Causes destructive phase-to-phase mid-span faults and crossarm structural failures; mitigated by interphase spacers."
        ),
        FlashcardItem(
            id = "fc_19",
            term = "ACCC & HTLS Conductors",
            standardRef = "ASTM B987 / CIGRE TB 498",
            category = "Conductor and Hardware",
            shortDefinition = "High-Temperature Low-Sag conductors using carbon composite or Invar cores with annealed trapezoidal aluminum.",
            keyTakeawayOrFormula = "Continuous operation up to 180°C (vs 75-90°C for ACSR) with near-zero thermal sag knee point.",
            fullContext = "Enables doubling line ampacity on existing towers without costly structure replacements or clearance violations."
        ),
        FlashcardItem(
            id = "fc_20",
            term = "Non-Ceramic Composite Insulator (NCI)",
            standardRef = "IEC 61109 / IEEE Std 1024",
            category = "Conductor and Hardware",
            shortDefinition = "Insulator featuring a high-strength fiberglass rod housed in hydrophobic silicone rubber weather sheds.",
            keyTakeawayOrFormula = "Hydrophobic surface transfer prevents continuous moisture film, preventing pollution flashovers.",
            fullContext = "Weighs ~90% less than porcelain disc strings and resists gunshot vandalism and seismic stresses."
        ),
        FlashcardItem(
            id = "fc_21",
            term = "NERC FAC-003 Minimum Vegetation Clearance Distance (MVCD)",
            standardRef = "NERC FAC-003-4 Reliability Standard",
            category = "Right-of-Way and Environment",
            shortDefinition = "Federal reliability clearance between energized overhead line conductors and unpruned vegetation.",
            keyTakeawayOrFormula = "MVCD = Gallet Air Breakdown Distance (cd) + Overvoltage Switching Surge Factor + Wind Sway Allowance.",
            fullContext = "Zero-tolerance compliance standard; violations causing vegetation-induced flashovers incur fines up to $1M/day."
        ),
        FlashcardItem(
            id = "fc_22",
            term = "FAA 14 CFR Part 77 Obstruction Marking & Lighting",
            standardRef = "FAA AC 70/7460-1 / 14 CFR Part 77",
            category = "Right-of-Way and Environment",
            shortDefinition = "Federal aviation rules for structures exceeding 200 ft AGL or intersecting airport imaginary approach slopes.",
            keyTakeawayOrFormula = "Requires alternating aviation orange/white bands (7 equal bands) and red/white beacon lighting.",
            fullContext = "Requires spherical aerial marker balls (≥36\" dia) on top shield wires at 200 ft intervals for waterway/highway crossings."
        ),
        FlashcardItem(
            id = "fc_23",
            term = "Corona Audible Noise & Radio Interference",
            standardRef = "EPRI Red Book / IEEE Std 430",
            category = "Right-of-Way and Environment",
            shortDefinition = "Acoustic hiss/crackle and RF interference caused by high surface electric field gradient exceeding air breakdown (30 kV/cm).",
            keyTakeawayOrFormula = "EPRI Heavy Rain Noise: L_50 ≈ 52 to 58 dBA at ROW boundary for 500 kV AC lines.",
            fullContext = "Mitigated by bundling phase conductors (2, 3, 4+ subconductors) and installing corona grading rings at hardware fittings."
        ),
        FlashcardItem(
            id = "fc_24",
            term = "AC Interference on Collocated Pipelines",
            standardRef = "NACE SP0177 / IEEE Std 80",
            category = "Right-of-Way and Environment",
            shortDefinition = "Inductive and conductive voltage coupling from high-voltage AC lines into parallel buried steel pipelines.",
            keyTakeawayOrFormula = "Max touch voltage on above-ground appurtenances ≤ 15 V AC steady state (NACE SP0177).",
            fullContext = "Mitigated by zinc ribbon counterpoise, solid-state DC decouplers, and deep grounding beds."
        )
    )

    /**
     * Multiple choice questions for knowledge assessment.
     */
    val quizQuestions: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = "q_01",
            question = "Under NESC Rule 232, how is the ground clearance adder calculated for transmission line voltages exceeding 22 kV?",
            standardRef = "NESC C2-2023 Table 232-1",
            category = "Line Design",
            options = listOf(
                "0.4 inches per kV in excess of 22 kV",
                "1.0 inch per kV in excess of 22 kV",
                "0.2 inches per kV in excess of 50 kV",
                "A flat 3.0 feet for all transmission class lines"
            ),
            correctIndex = 0,
            explanation = "NESC Rule 232 mandates a base clearance up to 22 kV plus exactly 0.4 inches (0.033 ft) for every kV in excess of 22 kV, plus an altitude correction for sites above 3,300 ft."
        ),
        QuizQuestion(
            id = "q_02",
            question = "Which federal mandate requires hourly ambient-adjusted thermal line ratings (AAR) based on forecasted temperature?",
            standardRef = "18 CFR § 35.28",
            category = "Line Design",
            options = listOf(
                "FERC Order 881",
                "NERC CIP-014",
                "FERC Order 1000",
                "OSHA 1910.269"
            ),
            correctIndex = 0,
            explanation = "FERC Order 881 mandates that transmission providers use hourly ambient-adjusted ratings (AARs) evaluating ambient air temperatures in increments of ≤5°F."
        ),
        QuizQuestion(
            id = "q_03",
            question = "What is the primary factor that causes aged overhead conductors to have a higher thermal ampacity rating than brand new conductors in IEEE 738?",
            standardRef = "IEEE Std 738 Clause 6",
            category = "Line Design",
            options = listOf(
                "Emissivity (ε) increases from ~0.23 up to 0.70-0.90 through surface oxidation",
                "DC electrical resistance decreases as aluminum ages",
                "Conductor weight increases from soot deposit",
                "Conductor tensile strength increases over time"
            ),
            correctIndex = 0,
            explanation = "Weathering and oxidation darken the aluminum surface, raising thermal emissivity (ε) from ~0.23 up to 0.70-0.90, which dramatically accelerates radiative heat cooling (q_r ∝ ε)."
        ),
        QuizQuestion(
            id = "q_04",
            question = "According to NACE SP0169, what is the recognized polarized cathodic protection potential criterion for mitigating corrosion on buried steel?",
            standardRef = "NACE SP0169 / ISO 15589",
            category = "Corrosion",
            options = listOf(
                "-850 mV DC with respect to a saturated Cu/CuSO4 reference electrode",
                "-500 mV DC with respect to a silver chloride electrode",
                "+850 mV DC with respect to a calomel electrode",
                "0.00 mV neutral potential"
            ),
            correctIndex = 0,
            explanation = "A negative (cathodic) potential of at least -850 mV (-0.85 V) relative to a saturated copper-copper sulfate reference electrode satisfies full cathodic protection."
        ),
        QuizQuestion(
            id = "q_05",
            question = "Under IEEE Std 1243, why is maintaining tower footing resistance below 10-15 Ohms critical?",
            standardRef = "IEEE Std 1243 / IEEE Std 80",
            category = "Lightning and Grounding",
            options = listOf(
                "To prevent tower top potential from exceeding insulator CFO and causing backflashover",
                "To reduce load current flow through ground wires",
                "To prevent galvanic corrosion on steel lattice members",
                "To minimize corona audible noise at structure crossarms"
            ),
            correctIndex = 0,
            explanation = "When lightning strikes the shield wire or tower, high footing resistance produces a massive voltage drop (V = I · R). If crossarm potential exceeds string CFO, lightning flashes back onto the phase conductor."
        ),
        QuizQuestion(
            id = "q_06",
            question = "What aerodynamic phenomenon causes high-frequency (3-120 Hz) micro-vibrations in overhead conductors that can lead to strand fatigue under suspension clamps?",
            standardRef = "IEEE Std 563",
            category = "Conductor and Hardware",
            options = listOf(
                "Aeolian vibration caused by Karman vortex shedding",
                "Subconductor wake flutter in bundled phases",
                "Full-span conductor galloping from asymmetric ice",
                "Magnetostriction of the steel core"
            ),
            correctIndex = 0,
            explanation = "Aeolian vibration is caused by alternating Karman vortices shedding in light, steady winds (1 to 7 m/s) at Strouhal frequency f = (0.185 · V) / D, damped using Stockbridge dampers."
        ),
        QuizQuestion(
            id = "q_07",
            question = "What is the primary operational advantage of HTLS ACCC (Aluminum Conductor Composite Core) over traditional ACSR?",
            standardRef = "ASTM B987 / CIGRE TB 498",
            category = "Conductor and Hardware",
            options = listOf(
                "Operates at up to 180°C continuous with near-zero thermal elongation sag",
                "Weighs 50% more to resist blowout wind sway",
                "Eliminates the need for any vibration dampers",
                "Eliminates all corona discharge up to 765 kV"
            ),
            correctIndex = 0,
            explanation = "ACCC conductors utilize a carbon-glass composite core with an exceptionally low thermal expansion coefficient, enabling operation up to 180°C (doubling ampacity) without violating ground clearances."
        ),
        QuizQuestion(
            id = "q_08",
            question = "Under FAA Advisory Circular AC 70/7460-1, at what structure height above ground level (AGL) does aviation marking and lighting become mandatory?",
            standardRef = "14 CFR Part 77 / FAA AC 70/7460-1",
            category = "Right-of-Way and Environment",
            options = listOf(
                "200 feet (61 meters) AGL",
                "100 feet (30.5 meters) AGL",
                "350 feet (107 meters) AGL",
                "500 feet (152 meters) AGL"
            ),
            correctIndex = 0,
            explanation = "Any structure that exceeds 200 feet AGL (or breaches airport imaginary approach surfaces) requires FAA notification and compliant aviation obstruction marking and lighting."
        ),
        QuizQuestion(
            id = "q_09",
            question = "In OSHA 1910.269, what are the two distinct components that sum together to establish the Minimum Approach Distance (MAD)?",
            standardRef = "29 CFR § 1910.269",
            category = "Inspection and Assessment",
            options = listOf(
                "Minimum Electrical Component (air breakdown) + Inadvertent Movement Buffer (2 ft / 0.61 m)",
                "Physical arm reach + Step potential buffer",
                "Conductor sag depth + Blowout angle buffer",
                "Arc flash boundary + Flash protection boundary"
            ),
            correctIndex = 0,
            explanation = "MAD equals the electrical component (dielectric air breakdown distance based on maximum transient overvoltage) plus an inadvertent movement buffer of 2 feet (0.61 meters)."
        ),
        QuizQuestion(
            id = "q_10",
            question = "Under NACE SP0177, what is the maximum recommended steady-state AC touch voltage permitted on above-ground pipeline appurtenances collocated on a power line ROW?",
            standardRef = "NACE SP0177 / IEEE Std 80",
            category = "Right-of-Way and Environment",
            options = listOf(
                "15 Volts AC",
                "50 Volts AC",
                "120 Volts AC",
                "5 Volts AC"
            ),
            correctIndex = 0,
            explanation = "NACE SP0177 stipulates that steady-state AC touch voltages on metallic pipeline structures accessible to personnel must not exceed 15 V AC to prevent electrical shock."
        )
    )

    fun cardsForCategory(category: String): List<FlashcardItem> {
        if (category.equals("All", ignoreCase = true)) return cards
        return cards.filter { it.category.equals(category, ignoreCase = true) }
    }

    fun questionsForCategory(category: String): List<QuizQuestion> {
        if (category.equals("All", ignoreCase = true)) return quizQuestions
        return quizQuestions.filter { it.category.equals(category, ignoreCase = true) }
    }
}
