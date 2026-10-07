package com.example.model

data class GlossaryTerm(
    val id: String,
    val category: String,
    val term: String,
    val standardRef: String,
    val definition: String,
    val practicalEngineeringContext: String
)

object GlossaryRepository {
    val allTerms: List<GlossaryTerm> = listOf(
        // LINE DESIGN
        GlossaryTerm(
            id = "cat_01",
            category = "Line Design",
            term = "Catenary Curve",
            standardRef = "IEEE Std 738 / ASCE 74",
            definition = "The mathematical hyperbolic cosine curve y = C * (cosh(x/C) - 1) assumed by a flexible cord or cable hanging freely under its own uniform distributed weight between two supports.",
            practicalEngineeringContext = "Used in transmission line engineering to calculate mid-span sag, clearance to ground, conductor length, and maximum tension under wind and ice loadings."
        ),
        GlossaryTerm(
            id = "cat_02",
            category = "Line Design",
            term = "Ruling Span (Equivalent Span)",
            standardRef = "NESC Section 25 / CIGRE SC22",
            definition = "A hypothetical single level span length whose tension responds to changes in temperature, ice, and wind in the same manner as the average span in a long dead-end section with suspension insulators.",
            practicalEngineeringContext = "Calculated as S_r = sqrt(sum(S_i^3) / sum(S_i)). Suspension insulators are free to swing, equalizing horizontal tension across adjacent spans to the ruling span tension."
        ),
        GlossaryTerm(
            id = "cat_03",
            category = "Line Design",
            term = "Blowout Angle",
            standardRef = "NESC Rule 234 / ASCE 74",
            definition = "The angular displacement of a conductor from the vertical plane under transverse wind pressure, arctan(W_wind / W_vertical).",
            practicalEngineeringContext = "Dictates right-of-way width requirements and minimum clearance to adjacent trees, buildings, and transmission towers to prevent flashover during extreme wind events."
        ),
        GlossaryTerm(
            id = "cat_04",
            category = "Line Design",
            term = "Conductor Creep",
            standardRef = "Aluminum Association / IEEE 738",
            definition = "The irreversible non-elastic metallurgical elongation of a stranded aluminum conductor occurring under sustained mechanical tension over years of service.",
            practicalEngineeringContext = "Creates permanent sag increase. Line engineers account for 10-year creep when calculating 50-year ground clearance margins."
        ),
        GlossaryTerm(
            id = "cat_05",
            category = "Line Design",
            term = "Conductor Galloping",
            standardRef = "CIGRE TB 322 / IEEE 563",
            definition = "High-amplitude, low-frequency (0.1 to 1.0 Hz) wind-induced standing-wave oscillations of overhead lines, typically triggered by asymmetric ice accretions creating aerodynamic lift.",
            practicalEngineeringContext = "Can cause phase-to-phase contact or structural crossarm failures. Mitigated using interphase spacers and aerodynamic drag dampers."
        ),
        GlossaryTerm(
            id = "cat_06",
            category = "Line Design",
            term = "NESC Loading Districts",
            standardRef = "NESC C2-2023 Table 250-1",
            definition = "Three geographic climatic zones in North America (Heavy, Medium, Light) defining regulatory ice radial thickness, transverse wind pressure, ambient temperature, and constant K added to resultant conductor loading.",
            practicalEngineeringContext = "Heavy loading requires 0.50 in radial ice, 4 psf wind at 0°F, plus 0.30 lb/ft constant. Mandatory minimum for structural safety in northeastern and northern US."
        ),

        // CORROSION
        GlossaryTerm(
            id = "cor_01",
            category = "Corrosion",
            term = "Atmospheric Corrosivity Category (C1-CX)",
            standardRef = "ISO 9223 / ASTM G92",
            definition = "International standardized classification system quantifying the aggressiveness of atmospheric environments based on sulfur dioxide (SO2), airborne salinity (Cl-), temperature, and time of wetness.",
            practicalEngineeringContext = "Determines hot-dip galvanizing zinc loss rate (from 0.1 um/yr in C1 up to >8.4 um/yr in C5/CX), driving life-cycle repaint or replacement intervals for transmission towers."
        ),
        GlossaryTerm(
            id = "cor_02",
            category = "Corrosion",
            term = "Galvanic Corrosion",
            standardRef = "ASTM G82 / NACE SP0169",
            definition = "Accelerated electrochemical degradation occurring when two dissimilar metals with different electrode potentials are in direct electrical contact within a common electrolyte.",
            practicalEngineeringContext = "In transmission lines, dangerous at aluminum-to-copper hardware connections or steel-to-aluminum core interfaces in ACSR conductors without protective grease barriers."
        ),
        GlossaryTerm(
            id = "cor_03",
            category = "Corrosion",
            term = "Romanoff Soil Corrosion Model",
            standardRef = "NIST Circular 579",
            definition = "Empirical power-law model P = k * t^n estimating the maximum underground corrosion penetration depth P of carbon steel as a function of exposure time t and soil properties.",
            practicalEngineeringContext = "Used by transmission asset managers to predict remaining steel guy anchor rod thickness and foundation grillage degradation in low resistivity soils."
        ),
        GlossaryTerm(
            id = "cor_04",
            category = "Corrosion",
            term = "AC Induced Corrosion",
            standardRef = "ISO 18086 / NACE SP0106",
            definition = "Rapid localized electrochemical metal loss driven by alternating current discharge from buried metallic structures (e.g., pipelines or anchor rods) into the soil, induced by electromagnetic coupling from adjacent high-voltage lines.",
            practicalEngineeringContext = "Current densities exceeding 100 A/m² create severe pitting risks. Requires solid-state decouplers and sacrificial zinc mitigation ribbons."
        ),
        GlossaryTerm(
            id = "cor_05",
            category = "Corrosion",
            term = "Cathodic Protection (CP)",
            standardRef = "NACE SP0169 / IEEE 1695",
            definition = "A technique used to control corrosion of a metal surface by making it the cathode of an electrochemical cell, using either sacrificial galvanic anodes (magnesium/zinc) or impressed direct current (ICCP).",
            practicalEngineeringContext = "Applied to transmission tower grillage footings, steel direct-embedded monopoles, and buried guy anchor assemblies in aggressive soils."
        ),

        // INSPECTION & ASSESSMENT
        GlossaryTerm(
            id = "ins_01",
            category = "Inspection and Assessment",
            term = "Transmission Defect Priority Index (DSI)",
            standardRef = "EPRI Transmission Inspection Guidelines",
            definition = "A structured risk-matrix ranking system categorizing physical tower, conductor, and insulator defects from P1 (immediate hazard <24h) to P5 (monitor during standard patrol).",
            practicalEngineeringContext = "Ensures critical public safety hazards (e.g. sheared tower leg bolts, burnt conductor strands, or washed out foundations) trigger emergency clearances."
        ),
        GlossaryTerm(
            id = "ins_02",
            category = "Inspection and Assessment",
            term = "Wood Pole Sounding & Boring",
            standardRef = "ANSI O5.1 / ASCE Manual 91",
            definition = "Non-destructive acoustic hammer tapping combined with incremental resistance core drilling to detect internal decay pockets and quantify residual sound wood shell thickness.",
            practicalEngineeringContext = "NESC Rule 261 mandates that when a wood transmission pole loses more than one-third of its original moment capacity (remaining strength <67%), it must be reinforced (C-Truss) or replaced."
        ),
        GlossaryTerm(
            id = "ins_03",
            category = "Inspection and Assessment",
            term = "Slenderness Ratio (KL/r)",
            standardRef = "ASCE 10 / AISC 360",
            definition = "The ratio of the effective unbraced buckling length KL of a structural compression member to the minimum radius of gyration r of its cross-section.",
            practicalEngineeringContext = "ASCE 10 specifies maximum KL/r limits for latticed steel transmission towers (typically <= 200 for leg members). Bent or missing diagonal members drastically increase KL/r and trigger premature buckling."
        ),
        GlossaryTerm(
            id = "ins_04",
            category = "Inspection and Assessment",
            term = "Conductor Kneepoint Temperature",
            standardRef = "CIGRE TB 244 / IEEE 738",
            definition = "The thermal transition point in bi-metal conductors (e.g., ACSR) where aluminum thermal expansion exceeds steel, transferring all mechanical tension entirely to the central steel core.",
            practicalEngineeringContext = "Above the knee-point, sag rate decreases markedly because steel has a lower thermal expansion coefficient than aluminum. Crucial for thermal uprating studies."
        ),

        // LIGHTNING & GROUNDING
        GlossaryTerm(
            id = "lgt_01",
            category = "Lightning and Grounding",
            term = "Backflashover (BFOR)",
            standardRef = "IEEE Std 1243 / CIGRE SC33",
            definition = "A dielectric flashover across an insulator string occurring when lightning strikes the shield wire or tower top, raising the tower structure voltage above the phase conductor potential due to high tower footing resistance.",
            practicalEngineeringContext = "The dominant cause of lightning outages on high-voltage lines. Controlled by reducing tower footing resistance below 10-15 ohms and adding radial counterpoise."
        ),
        GlossaryTerm(
            id = "lgt_02",
            category = "Lightning and Grounding",
            term = "Shielding Failure (SFFOR)",
            standardRef = "IEEE Std 1243 / IEEE 1410",
            definition = "A direct lightning strike terminating directly on a phase conductor instead of the overhead shield wire (OHGW/OPGW), occurring when the lightning leader penetrates the protective cone of protection.",
            practicalEngineeringContext = "Evaluated using the Electrogeometric Model (EGM). Mitigated by maintaining a small shielding angle (typically <= 30° on single-circuit towers and negative angles on tall river crossings)."
        ),
        GlossaryTerm(
            id = "lgt_03",
            category = "Lightning and Grounding",
            term = "Ground Flash Density (GFD / Ng)",
            standardRef = "IEEE Std 1410 / CIGRE 63",
            definition = "The average number of cloud-to-ground lightning flashes per square kilometer per year in a given geographic region.",
            practicalEngineeringContext = "Direct input to line lightning trip-rate calculations. In North America, ranges from <1 flash/km²/yr in the Pacific Northwest to >12 flashes/km²/yr in Florida and the Gulf Coast."
        ),
        GlossaryTerm(
            id = "lgt_04",
            category = "Lightning and Grounding",
            term = "Wenner 4-Point Soil Method",
            standardRef = "IEEE Std 81",
            definition = "A specialized geophysical test method using four collinear equally-spaced ground pins driven into the soil to measure apparent resistivity rho = 2 * pi * a * R as a function of pin spacing a.",
            practicalEngineeringContext = "Provides depth-stratified soil resistivity profiles (two-layer soil models) essential for accurate substation and transmission tower grounding design."
        ),
        GlossaryTerm(
            id = "lgt_05",
            category = "Lightning and Grounding",
            term = "Critical Flashover Voltage (CFO)",
            standardRef = "IEEE Std 4 / IEC 60060",
            definition = "The crest value of standard 1.2/50 us lightning impulse voltage that has a 50% probability of causing electrical breakdown across an insulator string or air gap.",
            practicalEngineeringContext = "Defines the basic impulse insulation level (BIL) of the line. A 230 kV suspension string with 14 standard porcelain bells has a dry lightning CFO of approximately 1200-1300 kV."
        ),

        // CONDUCTOR & HARDWARE
        GlossaryTerm(
            id = "cnd_01",
            category = "Conductor and Hardware",
            term = "ACSR (Aluminum Conductor Steel Reinforced)",
            standardRef = "ASTM B232 / CSA C49",
            definition = "A concentric-lay-stranded conductor composed of one or more layers of hard-drawn 1350-H19 aluminum wires surrounding a central high-strength galvanized steel core.",
            practicalEngineeringContext = "The historical industry standard for high-voltage transmission, named after birds (e.g. Drake, Hawk, Cardinal) due to its high strength-to-weight ratio."
        ),
        GlossaryTerm(
            id = "cnd_02",
            category = "Conductor and Hardware",
            term = "ACSS (Aluminum Conductor Steel Supported)",
            standardRef = "ASTM B856",
            definition = "A stranded transmission conductor with annealed (O-temper) aluminum wires over a high-strength mischmetal or aluminum-clad steel core, rated for continuous operation up to 200°C–250°C.",
            practicalEngineeringContext = "High-temperature low-sag (HTLS) workhorse for reconductoring existing transmission corridors without exceeding structure tower loading limits."
        ),
        GlossaryTerm(
            id = "cnd_03",
            category = "Conductor and Hardware",
            term = "Aeolian Vibration & Stockbridge Damper",
            standardRef = "IEEE Std 664 / CIGRE SC22",
            definition = "High-frequency (3 to 40 Hz), low-amplitude wind-induced vortex-shedding vibrations causing fatigue failure of conductor strands at suspension clamps. Dampened using Stockbridge resonant weights.",
            practicalEngineeringContext = "Stockbridge dampers are positioned at specific nodal distances (0.8 * half-loop length) from suspension clamps to maximize vibrational energy dissipation."
        ),
        GlossaryTerm(
            id = "cnd_04",
            category = "Conductor and Hardware",
            term = "Insulator Creepage Distance",
            standardRef = "IEC 60815 / IEEE 1313",
            definition = "The shortest path along the external surface contours of an insulator ceramic or polymer shed between live line hardware and grounded metal fittings.",
            practicalEngineeringContext = "Mandates minimum leakage distance (e.g., 25-31 mm per kV line-to-line) to prevent dry band arcing and pollution flashovers in coastal or industrial environments."
        ),
        GlossaryTerm(
            id = "cnd_05",
            category = "Conductor and Hardware",
            term = "Corona Ring (Grading Ring)",
            standardRef = "IEEE Std 1827 / EPRI",
            definition = "A smooth toroidal metallic ring installed at the energized end (and ground end above 345 kV) of an insulator string to distribute the steep non-linear electric field gradient.",
            practicalEngineeringContext = "Prevents localized air ionization (corona discharge), radio frequency interference (RFI), and polymer housing erosion on EHV/UHV lines."
        ),

        // RIGHT-OF-WAY & ENVIRONMENT
        GlossaryTerm(
            id = "row_01",
            category = "Right-of-Way and Environment",
            term = "Electric and Magnetic Fields (EMF)",
            standardRef = "IEEE Std 644 / ICNIRP Guidelines",
            definition = "Low-frequency (50/60 Hz) non-ionizing physical fields produced by line voltage (electric field, kV/m) and line current (magnetic flux density, mG / uT).",
            practicalEngineeringContext = "Engineers calculate lateral ground profiles to verify compliance with public exposure standards (ICNIRP 5 kV/m electric, 200 uT / 2000 mG magnetic) at right-of-way boundaries."
        ),
        GlossaryTerm(
            id = "row_02",
            category = "Right-of-Way and Environment",
            term = "Audible Noise (Wet Conductor Corona)",
            standardRef = "EPRI Reference Book / IEEE 656",
            definition = "Acoustic buzzing, frying, and crackling sounds generated by electrical discharges into moist air at water droplets adhering to conductor surfaces under high electric gradient.",
            practicalEngineeringContext = "Predominant at 345 kV and above during rain or heavy fog. Mitigated by using bundle conductors (2, 3, or 4 subconductors per phase) to reduce surface voltage gradient."
        ),
        GlossaryTerm(
            id = "row_03",
            category = "Right-of-Way and Environment",
            term = "Danger Tree / Clearance Zone",
            standardRef = "NERC FAC-003-4 / NESC Rule 218",
            definition = "Any tree located inside or adjacent to the transmission right-of-way that could contact a phase conductor under max blowout, max operating sag, or fall-in trajectory.",
            practicalEngineeringContext = "Federal mandatory reliability compliance standard (FAC-003) enforcing zero vegetation-related line outages on bulk electric systems (200 kV+)."
        )
    )

    fun termsForCategory(category: String): List<GlossaryTerm> {
        return allTerms.filter { it.category.equals(category, ignoreCase = true) }
    }

    fun searchTerms(query: String): List<GlossaryTerm> {
        if (query.isBlank()) return allTerms
        val q = query.trim().lowercase()
        return allTerms.filter {
            it.term.lowercase().contains(q) ||
                    it.category.lowercase().contains(q) ||
                    it.definition.lowercase().contains(q) ||
                    it.standardRef.lowercase().contains(q)
        }
    }
}
