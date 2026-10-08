package com.example.model

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: MessageSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val suggestedToolCategory: String? = null,
    val suggestedToolId: String? = null,
    val suggestedToolTitle: String? = null,
    val suggestedGlossaryCategory: String? = null,
    val suggestedGlossaryTerm: String? = null,
    val isStreamingOrLoading: Boolean = false
)

enum class MessageSender {
    USER,
    ASSISTANT
}

data class QuickPrompt(
    val title: String,
    val query: String
)

object TransmissionKnowledgeBase {

    val QUICK_PROMPTS = listOf(
        QuickPrompt("Emissivity & Rating", "How does conductor surface emissivity affect ampacity?"),
        QuickPrompt("NESC 232 Road Clearance", "What is the minimum ground clearance for a 138 kV line over roads?"),
        QuickPrompt("OSHA MAD Formula", "How is live-line Minimum Approach Distance calculated?"),
        QuickPrompt("Catenary Sag Calculation", "What is the formula for conductor catenary sag?"),
        QuickPrompt("Galvanized Zinc Life", "How long does zinc galvanizing last in industrial C4 environments?"),
        QuickPrompt("FERC 881 AAR", "What are the core requirements of FERC Order 881 for AAR?")
    )

    fun findSuggestedNavigation(query: String, answer: String): SuggestedNavigation? {
        val q = query.lowercase()
        val a = answer.lowercase()

        // Match tools
        return when {
            q.contains("emissiv") || q.contains("absorptiv") || a.contains("emissivity & solar absorptivity") ->
                SuggestedNavigation(
                    toolCategory = "line_design",
                    toolId = "emissivity",
                    toolTitle = "Emissivity & Solar Absorptivity Calculator",
                    glossaryCategory = "Conductor and Hardware",
                    glossaryTerm = "Conductor Emissivity (ε)"
                )
            q.contains("ampacity") || q.contains("ieee 738") || a.contains("ieee 738") ->
                SuggestedNavigation(
                    toolCategory = "line_design",
                    toolId = "ampacity",
                    toolTitle = "IEEE 738 Thermal Ampacity",
                    glossaryCategory = "Line Design",
                    glossaryTerm = "Thermal Ampacity"
                )
            q.contains("catenary") || q.contains("sag") || a.contains("catenary sag") ->
                SuggestedNavigation(
                    toolCategory = "line_design",
                    toolId = "catenary",
                    toolTitle = "Catenary Sag & Tension",
                    glossaryCategory = "Line Design",
                    glossaryTerm = "Catenary Sag"
                )
            q.contains("ruling span") || q.contains("equivalent span") ->
                SuggestedNavigation(
                    toolCategory = "line_design",
                    toolId = "ruling_span",
                    toolTitle = "Ruling Span (Equivalent Span)",
                    glossaryCategory = "Line Design",
                    glossaryTerm = "Ruling Span (Equivalent Span)"
                )
            q.contains("blowout") || q.contains("sway") || q.contains("wind deflection") ->
                SuggestedNavigation(
                    toolCategory = "line_design",
                    toolId = "blowout",
                    toolTitle = "Conductor Blowout & Sway",
                    glossaryCategory = "Line Design",
                    glossaryTerm = "Conductor Blowout"
                )
            q.contains("nesc") || q.contains("clearance") || q.contains("table 232") ->
                SuggestedNavigation(
                    toolCategory = "line_design",
                    toolId = "nesc_clearance",
                    toolTitle = "NESC 232 Ground Clearance",
                    glossaryCategory = "Line Design",
                    glossaryTerm = "NESC Table 232-1 Clearances"
                )
            q.contains("ferc") || q.contains("881") || q.contains("aar") ->
                SuggestedNavigation(
                    toolCategory = "line_design",
                    toolId = "ferc_aar",
                    toolTitle = "FERC 881 Ambient-Adjusted Rating",
                    glossaryCategory = "Line Design",
                    glossaryTerm = "FERC Order 881 (AAR)"
                )
            q.contains("zinc") || q.contains("galvaniz") || q.contains("iso 9223") || q.contains("coating life") ->
                SuggestedNavigation(
                    toolCategory = "corrosion",
                    toolId = "zinc_life",
                    toolTitle = "Galvanizing Coating Life",
                    glossaryCategory = "Corrosion",
                    glossaryTerm = "Atmospheric Corrosivity (ISO 9223)"
                )
            q.contains("soil anchor") || q.contains("romanoff") || q.contains("guy anchor") ->
                SuggestedNavigation(
                    toolCategory = "corrosion",
                    toolId = "soil_anchor",
                    toolTitle = "Soil Anchor & Foundation Loss",
                    glossaryCategory = "Corrosion",
                    glossaryTerm = "Romanoff Underground Pitting Equation"
                )
            q.contains("ac interfer") || q.contains("iso 18086") || q.contains("nace") ->
                SuggestedNavigation(
                    toolCategory = "corrosion",
                    toolId = "ac_corrosion",
                    toolTitle = "AC Interference & Corrosion Risk",
                    glossaryCategory = "Corrosion",
                    glossaryTerm = "AC Interference Current Density"
                )
            q.contains("cathodic") || q.contains("anode") || q.contains("cp anode") ->
                SuggestedNavigation(
                    toolCategory = "corrosion",
                    toolId = "cp_anode",
                    toolTitle = "Cathodic Protection Sizing",
                    glossaryCategory = "Corrosion",
                    glossaryTerm = "Sacrificial Galvanic Anode"
                )
            q.contains("defect") || q.contains("dsi") || q.contains("priority matrix") ->
                SuggestedNavigation(
                    toolCategory = "inspection",
                    toolId = "defect_matrix",
                    toolTitle = "Transmission Defect Priority (DSI)",
                    glossaryCategory = "Inspection & Assessment",
                    glossaryTerm = "Defect Severity Index (DSI)"
                )
            q.contains("wood pole") || q.contains("decay") || q.contains("shell thickness") ->
                SuggestedNavigation(
                    toolCategory = "inspection",
                    toolId = "wood_pole",
                    toolTitle = "Wood Pole Shell Remaining Strength",
                    glossaryCategory = "Inspection & Assessment",
                    glossaryTerm = "Wood Pole Shell Thickness"
                )
            q.contains("lattice") || q.contains("buckling") || q.contains("kl/r") || q.contains("slenderness") ->
                SuggestedNavigation(
                    toolCategory = "inspection",
                    toolId = "lattice_buckling",
                    toolTitle = "Lattice Member Buckling Capacity",
                    glossaryCategory = "Inspection & Assessment",
                    glossaryTerm = "Lattice Member Slenderness (KL/r)"
                )
            q.contains("mad") || q.contains("minimum approach") || q.contains("osha") ->
                SuggestedNavigation(
                    toolCategory = "inspection",
                    toolId = "osha_mad",
                    toolTitle = "OSHA Minimum Approach Distance (MAD)",
                    glossaryCategory = "Inspection & Assessment",
                    glossaryTerm = "Minimum Approach Distance (MAD)"
                )
            q.contains("footing") || q.contains("counterpoise") || q.contains("grounding") || q.contains("soil resistivity") ->
                SuggestedNavigation(
                    toolCategory = "lightning",
                    toolId = "footing_resistance",
                    toolTitle = "Footing Grounding & Counterpoise",
                    glossaryCategory = "Lightning & Grounding",
                    glossaryTerm = "Tower Footing Resistance"
                )
            q.contains("shielding angle") || q.contains("egm") || q.contains("strike") ->
                SuggestedNavigation(
                    toolCategory = "lightning",
                    toolId = "shielding_angle",
                    toolTitle = "Shielding Angle & Strike Protection",
                    glossaryCategory = "Lightning & Grounding",
                    glossaryTerm = "Shielding Angle & EGM"
                )
            q.contains("backflash") || q.contains("outage rate") ->
                SuggestedNavigation(
                    toolCategory = "lightning",
                    toolId = "backflashover",
                    toolTitle = "Critical Backflashover & Outage Rate",
                    glossaryCategory = "Lightning & Grounding",
                    glossaryTerm = "Backflashover"
                )
            q.contains("wenner") || q.contains("apparent resistivity") ->
                SuggestedNavigation(
                    toolCategory = "lightning",
                    toolId = "wenner_test",
                    toolTitle = "Wenner 4-Point Soil Profiler",
                    glossaryCategory = "Lightning & Grounding",
                    glossaryTerm = "Apparent Soil Resistivity (ρ)"
                )
            q.contains("acsr") || q.contains("conductor db") || q.contains("drake") || q.contains("hawk") ->
                SuggestedNavigation(
                    toolCategory = "conductor",
                    toolId = "conductor_db",
                    toolTitle = "Conductor Database & Specs",
                    glossaryCategory = "Conductor and Hardware",
                    glossaryTerm = "ACSR (Aluminum Conductor Steel-Reinforced)"
                )
            q.contains("aeolian") || q.contains("damper") || q.contains("stockbridge") || q.contains("vibration") ->
                SuggestedNavigation(
                    toolCategory = "conductor",
                    toolId = "aeolian_vibration",
                    toolTitle = "Aeolian Vibration & Damper Sizing",
                    glossaryCategory = "Conductor and Hardware",
                    glossaryTerm = "Aeolian Vibration"
                )
            q.contains("creepage") || q.contains("insulator") || q.contains("iec 60815") ->
                SuggestedNavigation(
                    toolCategory = "conductor",
                    toolId = "insulator_string",
                    toolTitle = "Insulator Creepage & Rating",
                    glossaryCategory = "Conductor and Hardware",
                    glossaryTerm = "Insulator Creepage Distance"
                )
            q.contains("emf") || q.contains("magnetic field") || q.contains("electric field") || q.contains("icnirp") ->
                SuggestedNavigation(
                    toolCategory = "row_env",
                    toolId = "emf_profile",
                    toolTitle = "EMF Ground Level Profiler",
                    glossaryCategory = "Right-of-Way & Environment",
                    glossaryTerm = "Electric and Magnetic Fields (EMF)"
                )
            q.contains("row width") || q.contains("corridor") || q.contains("right of way") ->
                SuggestedNavigation(
                    toolCategory = "row_env",
                    toolId = "row_width",
                    toolTitle = "ROW Corridor Minimum Width",
                    glossaryCategory = "Right-of-Way & Environment",
                    glossaryTerm = "Right-of-Way (ROW) Corridor"
                )
            q.contains("corona") || q.contains("audible noise") || q.contains("decibel") ->
                SuggestedNavigation(
                    toolCategory = "row_env",
                    toolId = "corona_noise",
                    toolTitle = "Corona Loss & Audible Noise",
                    glossaryCategory = "Right-of-Way & Environment",
                    glossaryTerm = "Corona Discharge & Audible Noise"
                )
            q.contains("mvcd") || q.contains("vegetation") || q.contains("fac-003") || q.contains("gallet") ->
                SuggestedNavigation(
                    toolCategory = "row_env",
                    toolId = "nerc_mvcd",
                    toolTitle = "NERC FAC-003-4 MVCD Clearance",
                    glossaryCategory = "Right-of-Way & Environment",
                    glossaryTerm = "NERC FAC-003 Minimum Vegetation Clearance Distance (MVCD)"
                )
            q.contains("faa") || q.contains("marker ball") || q.contains("paint banding") || q.contains("obstruction") ->
                SuggestedNavigation(
                    toolCategory = "row_env",
                    toolId = "faa_obstruction",
                    toolTitle = "FAA Tower Marking & Lighting",
                    glossaryCategory = "Right-of-Way & Environment",
                    glossaryTerm = "FAA AC 70/7460 Obstruction Lighting & Marking"
                )
            else -> null
        }
    }

    /**
     * Local offline fallback response when no Gemini API key is configured or offline.
     * Matches exact queries with EPRI, IEEE, NESC, and OSHA standards directly.
     */
    fun getLocalOfflineResponse(userQuery: String): ChatResponseResult {
        val q = userQuery.lowercase().trim()
        val nav = findSuggestedNavigation(userQuery, "")

        // Look for matching glossary term first
        val matchingGlossary = GlossaryRepository.searchTerms(userQuery).firstOrNull()

        val answerText = when {
            q.contains("emissiv") || q.contains("absorptiv") -> """
**Conductor Emissivity (ε) & Solar Absorptivity (α)**
Per **IEEE Std 738-2012 Clause 6**:
- **New Bright Conductor:** Emissivity ε ≈ 0.23 to 0.30, Absorptivity α ≈ 0.23 to 0.50.
- **Aged / Weathered Conductor (>1-2 years):** Emissivity ε ≈ 0.80 to 0.90, Absorptivity α ≈ 0.80 to 0.90.
- **Industrial / Heavy Marine Soot:** ε ≈ 0.90 to 0.95.

**Ampacity Impact:**
Higher emissivity increases radiant heat dissipation (q_r = 1.78 · 10⁻⁸ · ε · D · [(T_c+273)⁴ - (T_a+273)⁴]), which can increase the allowable steady-state current capacity by **10% to 22%** compared to new shiny conductors under moderate ambient temperatures!

Use the **Emissivity & Solar Absorptivity Calculator** in Line Design to test exact weatherings and ampacity changes.
            """.trimIndent()

            q.contains("nesc") && (q.contains("232") || q.contains("clearance") || q.contains("road")) -> """
**NESC Rule 232 Ground Clearance (Table 232-1)**
For high-voltage overhead lines:
- **Public Roads, Highways, & Streets:** Base clearance is **18.5 ft** for up to 22 kV to ground (230 kV phase-to-phase typically requires **21.5 ft to 22.0 ft** depending on crest surge and maximum conductor sag).
- **Railroads:** Base **26.5 ft** (approx. 8.1 m).
- **Cultivated Farmland / Commercial Traffic:** **18.5 ft** (5.6 m).
- **Pedestrian Spaces Only:** **14.5 ft** (4.4 m).

**Overvoltage Voltage Adder:** Add **0.4 in (1 cm)** for every 1 kV excess above 22 kV to ground.

Test exact voltages and terrains using the **NESC 232 Ground Clearance Calculator** in Line Design.
            """.trimIndent()

            q.contains("osha") || q.contains("mad") || q.contains("minimum approach") -> """
**OSHA Minimum Approach Distance (Live-Line Working)**
Under **OSHA 29 CFR 1910.269 Table R-6 / IEEE Std 516**:
MAD = D_electrical + D_ergonomic
- Where D_ergonomic = 2.0 ft (0.61 m) allowance for inadvertent worker movement.
- At **69 kV:** Ph-to-Ground MAD is **3.0 ft** (0.95 m) at sea level.
- At **138 kV:** Ph-to-Ground MAD is **3.6 ft** (1.09 m) at sea level.
- At **230 kV:** Ph-to-Ground MAD is **5.3 ft** (1.60 m) at sea level.
- At **500 kV:** Ph-to-Ground MAD is **11.3 ft** (3.44 m) at sea level.

**Altitude Derating:** Above 3,000 ft (900 m), air density decreases dielectric strength, requiring an OSHA elevation multiplication factor A (e.g., 1.15× at 5,000 ft elevation).

Launch the **OSHA MAD Calculator** in Inspection & Assessment to calculate altitude-corrected distances.
            """.trimIndent()

            q.contains("catenary") || (q.contains("sag") && !q.contains("blowout")) -> """
**Catenary Sag & Tension Formulation**
Per **IEEE Std 738 & ASCE Manual 74**:
Sag S = (w · L²) / (8 · H)
- **S:** Sag at mid-span (ft or m).
- **w:** Total resultant conductor weight including radial ice & wind (lb/ft or N/m).
- **L:** Horizontal span length between towers (ft or m).
- **H:** Horizontal component of cable tension (lb or N).

**Key Takeaway:** Conductor sag is directly proportional to the square of the span length (L²) and inversely proportional to horizontal tension (H). Maximum sag typically occurs at maximum operating thermal temperature (100°C - 120°C) or under Heavy NESC radial ice loading (0.50 in ice + 4 psf wind at 0°F).

Access the **Catenary Sag & Tension Calculator** in Line Design for full tension-%RTS analysis.
            """.trimIndent()

            q.contains("zinc") || q.contains("galvaniz") || q.contains("iso 9223") -> """
**Hot-Dip Galvanizing (Zinc) Coating Life**
Per **ISO 9223 & ASTM A123**:
- Typical tower zinc thickness: **85 µm to 100 µm** (≈ 610 g/m²).
- **Corrosivity Categories & Depletion Rates:**
  - **C1 (Very Low / Dry Desert):** < 0.1 µm/yr (Coating life > 100 yrs)
  - **C2 (Low / Rural Interior):** 0.1 - 0.7 µm/yr (Coating life 80 - 100 yrs)
  - **C3 (Medium / Urban & Coastal Inland):** 0.7 - 2.1 µm/yr (Coating life 40 - 70 yrs)
  - **C4 (High / Industrial & Coastal Marine):** 2.1 - 4.2 µm/yr (Coating life 20 - 40 yrs)
  - **C5 / CX (Very High / Offshore Surf & Chemical):** 4.2 - 25.0 µm/yr (Coating life < 15 yrs)

Calculate remaining life to first maintenance in the **Galvanizing Coating Life Calculator** under Corrosion.
            """.trimIndent()

            q.contains("ferc") || q.contains("881") || q.contains("aar") -> """
**FERC Order 881 (Ambient-Adjusted Ratings)**
Federal Energy Regulatory Commission Order 881 mandates:
1. **Hourly Ambient Adjustments (AAR):** Transmission providers must calculate hourly transmission line thermal ratings incorporating forecasted ambient air temperatures.
2. **Solar Radiation Modeling:** Ratings must account for daytime vs nighttime solar irradiation (q_s) or calculate conservative daylight absorption.
3. **Safety & Capacity Benefits:** In cold weather, line ratings can safely increase by **15% to 35%**, unlocking transmission headroom without costly rebuilds!

Evaluate temperature vs thermal capacity curves using the **FERC 881 AAR Calculator** in Line Design.
            """.trimIndent()

            matchingGlossary != null -> """
**${matchingGlossary.term}**
*Standard Reference:* ${matchingGlossary.standardRef} (${matchingGlossary.category})

**Engineering Definition:**
${matchingGlossary.definition}

**Practical Engineering Application:**
${matchingGlossary.practicalEngineeringContext}
            """.trimIndent()

            else -> """
I am your **Transmission Engineering Assistant**, specializing in EPRI, IEEE, NESC, OSHA, and NERC utility transmission line standards.

**You can ask me questions about:**
1. **Line Design:** Catenary sag, ruling span, IEEE 738 ampacity, conductor emissivity, and NESC 232 road clearances.
2. **Corrosion:** ISO 9223 zinc coating life, Romanoff soil anchor pitting, AC interference, and cathodic protection.
3. **Inspection:** Defect Severity Index (DSI), ASCE 10 lattice buckling, wood pole shell thickness, and OSHA MAD live-line distances.
4. **Lightning & Grounding:** Footing resistance, IEEE 80 soil ionization, shielding angle EGM, and Wenner 4-point soil profiles.
5. **Right-of-Way:** Ground EMF profiles, NERC FAC-003 MVCD vegetation clearance, and FAA Part 77 catenary marker balls.

*Tap one of the quick prompts or action buttons below to jump right to a calculator or glossary reference!*
            """.trimIndent()
        }

        return ChatResponseResult(
            answer = answerText,
            navigation = nav
        )
    }

    const val SYSTEM_PROMPT = """
You are the Transmission Engineering Assistant embedded within the Overhead Transmission Toolbox (OTT), adhering strictly to EPRI (Electric Power Research Institute), IEEE, NESC (National Electrical Safety Code - ANSI C2), OSHA 1910.269, NERC (FAC-003-4), and ASCE standards.

Your duty is to assist power delivery engineers, substation engineers, line designers, and field inspectors.
When answering:
1. Provide accurate, mathematically sound, standard-compliant transmission engineering answers directly in your response.
2. Cite the exact applicable standards (e.g., IEEE 738-2012, NESC Table 232-1, OSHA 1910.269, ISO 9223, ASCE Manual 74, NERC FAC-003).
3. If an OTT calculator or glossary term is relevant, mention it so the user can be directed to the tool (e.g., Catenary Sag & Tension, IEEE 738 Thermal Ampacity, Emissivity & Solar Absorptivity, Galvanizing Coating Life, OSHA MAD, NESC 232 Ground Clearance).
4. Keep explanations concise, professional, and rigorous with formulas where relevant.
"""
}

data class SuggestedNavigation(
    val toolCategory: String? = null,
    val toolId: String? = null,
    val toolTitle: String? = null,
    val glossaryCategory: String? = null,
    val glossaryTerm: String? = null
)

data class ChatResponseResult(
    val answer: String,
    val navigation: SuggestedNavigation?
)
