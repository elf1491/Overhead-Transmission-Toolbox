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
        // LINE DESIGN & CLEARANCES
        GlossaryTerm(
            id = "cat_01",
            category = "Line Design",
            term = "Catenary Curve",
            standardRef = "IEEE Std 738 / ASCE 74",
            definition = "The hyperbolic cosine curve y = C · (cosh(x/C) - 1) assumed by a flexible overhead cable hanging freely under its distributed weight between two supports.",
            practicalEngineeringContext = "Used in transmission line engineering to calculate mid-span sag, clearance to ground, conductor length, and maximum tension under wind and ice loadings."
        ),
        GlossaryTerm(
            id = "cat_02",
            category = "Line Design",
            term = "Ruling Span (Equivalent Span)",
            standardRef = "NESC Section 25 / CIGRE SC22",
            definition = "A hypothetical single level span length whose tension responds to changes in temperature, ice, and wind in the same manner as the average span in a long dead-end section with suspension insulators.",
            practicalEngineeringContext = "Calculated as S_r = sqrt(sum(S_i³) / sum(S_i)). Suspension insulators swing freely to equalize horizontal tension across spans to the ruling span tension."
        ),
        GlossaryTerm(
            id = "cat_03",
            category = "Line Design",
            term = "NESC Rule 232 (Vertical Ground Clearances)",
            standardRef = "NESC C2-2023 Table 232-1",
            definition = "Regulatory minimum vertical clearances of wires, conductors, and cables above ground, roadway, railroad track, and water surfaces.",
            practicalEngineeringContext = "Prescribes base clearance up to 22 kV plus a mandatory voltage adder of 0.4 inches per kV in excess of 22 kV, with a 3% per 1000 ft altitude adder above 3300 ft."
        ),
        GlossaryTerm(
            id = "cat_04",
            category = "Line Design",
            term = "NESC Rule 234 (Clearance to Buildings & Bridges)",
            standardRef = "NESC C2-2023 Table 234-1",
            definition = "Regulatory minimum horizontal and vertical clearances between energized transmission conductors and adjacent structures, buildings, signs, and bridges.",
            practicalEngineeringContext = "Evaluated at rest and under blowout sway (typically 6 psf wind at 60°F) to ensure sufficient insulation buffer and prevent flashovers."
        ),
        GlossaryTerm(
            id = "cat_05",
            category = "Line Design",
            term = "Blowout Angle",
            standardRef = "NESC Rule 234 / ASCE 74",
            definition = "The angular displacement of a conductor from the vertical plane under transverse wind pressure: arctan(W_wind / W_vertical).",
            practicalEngineeringContext = "Dictates right-of-way width requirements and minimum clearance to adjacent trees, buildings, and transmission towers to prevent flashover during extreme wind events."
        ),
        GlossaryTerm(
            id = "cat_06",
            category = "Line Design",
            term = "NESC Loading Districts (Rule 250B)",
            standardRef = "NESC C2-2023 Table 250-1",
            definition = "Three geographic climatic zones in North America (Heavy, Medium, Light) defining regulatory radial ice thickness, transverse wind pressure, ambient temperature, and constant K added to resultant conductor loading.",
            practicalEngineeringContext = "Heavy loading requires 0.50 in radial ice, 4 psf wind at 0°F, plus 0.30 lb/ft constant. Mandatory minimum for structural safety in northern and northeastern US."
        ),
        GlossaryTerm(
            id = "cat_07",
            category = "Line Design",
            term = "NESC Extreme Wind (Rule 250C)",
            standardRef = "NESC C2-2023 Rule 250C / ASCE 74",
            definition = "Special load case applying high-velocity gust wind (up to 140+ mph) without ice to transmission structures whose height exceeds 60 ft above ground or water.",
            practicalEngineeringContext = "Prevents catastrophic structural failure from extreme hurricane and derecho winds; governs tall lattice towers and wide river crossing structures."
        ),
        GlossaryTerm(
            id = "cat_08",
            category = "Line Design",
            term = "NESC Extreme Ice with Concurrent Wind (Rule 250D)",
            standardRef = "NESC C2-2023 Rule 250D",
            definition = "Load case applying site-specific 50-year mean recurrence radial glaze ice (up to 1.0–1.5 inches) combined with concurrent 30–50 mph wind without the K constant.",
            practicalEngineeringContext = "Primary defense against catastrophic freezing rain and ice storm cascade failures across North America."
        ),
        GlossaryTerm(
            id = "cat_09",
            category = "Line Design",
            term = "NESC Grade of Construction (Grade B vs Grade C)",
            standardRef = "NESC Section 24 & Rule 261",
            definition = "Regulatory classification system specifying safety factors and strength requirements for overhead line supports.",
            practicalEngineeringContext = "Grade B is the highest standard, mandatory for major highway, railroad, and transmission line crossings. Grade C is permitted for minor distribution lines."
        ),
        GlossaryTerm(
            id = "cat_10",
            category = "Line Design",
            term = "Conductor Creep & Inelastic Strain",
            standardRef = "Aluminum Association / IEEE 738",
            definition = "The irreversible metallurgical plastic elongation of a stranded aluminum conductor occurring under sustained mechanical tension over decades of service.",
            practicalEngineeringContext = "Creates permanent sag increase. Line engineers account for 10-year creep when calculating 50-year ground clearance margins."
        ),
        GlossaryTerm(
            id = "cat_11",
            category = "Line Design",
            term = "Conductor Galloping",
            standardRef = "CIGRE TB 322 / IEEE 563",
            definition = "High-amplitude, low-frequency (0.1 to 1.0 Hz) wind-induced standing-wave oscillations of overhead lines, typically triggered by asymmetric ice accretions creating aerodynamic lift.",
            practicalEngineeringContext = "Can cause phase-to-phase contact or structural crossarm failures. Mitigated using interphase spacers and aerodynamic drag dampers."
        ),
        GlossaryTerm(
            id = "cat_12",
            category = "Line Design",
            term = "Emissivity & Solar Absorptivity",
            standardRef = "IEEE Std 738 / CIGRE TB 601",
            definition = "Dimensionless surface thermal coefficients defining radiative heat rejection (emissivity ε) and solar irradiance absorption (absorptivity α) of bare overhead conductors.",
            practicalEngineeringContext = "New shiny aluminum has low emissivity (ε≈0.23), radiating less heat and running hotter than aged dark aluminum (ε≈0.80–0.90). Crucial for ampacity ratings under FERC Order 881."
        ),
        GlossaryTerm(
            id = "cat_13",
            category = "Line Design",
            term = "Ambient-Adjusted Ratings (AAR) & FERC Order 881",
            standardRef = "FERC Order 881 / IEEE 738",
            definition = "Federal regulation requiring transmission providers to implement transmission line ratings that adjust dynamically based on forecasted ambient temperatures in 1-hour increments.",
            practicalEngineeringContext = "Replaces static summer/winter seasonal ratings with temperature-dependent ampacity, unlocking 10%–30% extra transmission capacity during cooler hours."
        ),
        GlossaryTerm(
            id = "cat_14",
            category = "Line Design",
            term = "Dynamic Line Rating (DLR)",
            standardRef = "CIGRE TB 601 / EPRI Guidelines",
            definition = "Real-time calculation of conductor thermal capacity based on concurrent measurements of ambient temperature, wind speed, wind angle, solar radiation, and conductor tension/sag.",
            practicalEngineeringContext = "Utilizes line-mounted sensors and weather stations to dramatically increase transmission transfer limits above conservative static planning assumptions."
        ),

        // CORROSION
        GlossaryTerm(
            id = "cor_01",
            category = "Corrosion",
            term = "Atmospheric Corrosivity Category (C1-CX)",
            standardRef = "ISO 9223 / ASTM G92",
            definition = "International standardized classification quantifying the aggressiveness of atmospheric environments based on sulfur dioxide (SO2), airborne salinity (Cl-), temperature, and time of wetness.",
            practicalEngineeringContext = "Determines hot-dip galvanizing zinc loss rate (from 0.1 µm/yr in C1 up to >8.4 µm/yr in C5/CX), driving life-cycle repaint or replacement intervals for transmission towers."
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
            definition = "Empirical power-law model P = k · t^n estimating the maximum underground corrosion penetration depth P of carbon steel as a function of exposure time t and soil properties.",
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
        GlossaryTerm(
            id = "cor_06",
            category = "Corrosion",
            term = "Soil-Line Necking",
            standardRef = "ASCE Manual 141",
            definition = "Accelerated localized corrosion concentrated within the top 6 to 24 inches of soil where fluctuating oxygen concentration and moisture create intense differential aeration cells.",
            practicalEngineeringContext = "Responsible for abrupt failure of direct-embedded steel poles and guy anchor rods. Requires mandatory excavation and ultrasonic thickness (UT) inspection."
        ),

        // INSPECTION, REGULATORY & SAFETY
        GlossaryTerm(
            id = "ins_01",
            category = "Inspection and Assessment",
            term = "Minimum Approach Distance (MAD)",
            standardRef = "OSHA 29 CFR 1910.269 / IEEE 516",
            definition = "The minimum air distance that a qualified electrical worker must maintain from energized parts without approved live-line tools or protective insulating equipment.",
            practicalEngineeringContext = "Calculated as electrical component D_E plus ergonomic inadvertent movement adder D_M (typically 2 ft / 0.61 m), adjusted for per-unit transient overvoltages (T) and altitude."
        ),
        GlossaryTerm(
            id = "ins_02",
            category = "Inspection and Assessment",
            term = "NERC FAC-003 (Vegetation Management)",
            standardRef = "NERC Standard FAC-003-4",
            definition = "Mandatory North American electric reliability standard requiring transmission owners to prevent tree-caused outages on lines operating at 200 kV and above.",
            practicalEngineeringContext = "Enforces Minimum Vegetation Clearance Distances (MVCD). Violations carry severe federal financial penalties up to $1 million per day per violation."
        ),
        GlossaryTerm(
            id = "ins_03",
            category = "Inspection and Assessment",
            term = "NERC FAC-008 (Facility Ratings & Most Limiting Element)",
            standardRef = "NERC Standard FAC-008-5",
            definition = "Mandatory reliability standard requiring transmission owners to document the facility rating of each circuit based on its single most limiting series element.",
            practicalEngineeringContext = "Identifies whether the line thermal limit is constrained by the conductor, wave trap, current transformer (CT), disconnect switch, or protective relay setting."
        ),
        GlossaryTerm(
            id = "ins_04",
            category = "Inspection and Assessment",
            term = "Transmission Defect Priority Index (DSI)",
            standardRef = "EPRI Transmission Inspection Guidelines",
            definition = "A structured risk-matrix ranking system categorizing physical tower, conductor, and insulator defects from P1 (immediate hazard <24h) to P5 (monitor during standard patrol).",
            practicalEngineeringContext = "Ensures critical public safety hazards (e.g. sheared tower leg bolts, burnt conductor strands, or washed out foundations) trigger emergency clearances."
        ),
        GlossaryTerm(
            id = "ins_05",
            category = "Inspection and Assessment",
            term = "Wood Pole Sounding & Boring",
            standardRef = "ANSI O5.1 / ASCE Manual 91",
            definition = "Non-destructive acoustic hammer tapping combined with incremental resistance core drilling to detect internal decay pockets and quantify residual sound wood shell thickness.",
            practicalEngineeringContext = "NESC Rule 261 mandates that when a wood transmission pole loses more than one-third of its original moment capacity (remaining strength <67%), it must be reinforced (C-Truss) or replaced."
        ),
        GlossaryTerm(
            id = "ins_06",
            category = "Inspection and Assessment",
            term = "Slenderness Ratio (KL/r)",
            standardRef = "ASCE 10 / AISC 360",
            definition = "The ratio of the effective unbraced buckling length KL of a structural compression member to the minimum radius of gyration r of its cross-section.",
            practicalEngineeringContext = "ASCE 10 specifies maximum KL/r limits for latticed steel transmission towers (typically <= 200 for leg members). Bent or missing diagonal members drastically increase KL/r and trigger premature buckling."
        ),
        GlossaryTerm(
            id = "ins_07",
            category = "Inspection and Assessment",
            term = "Conductor Kneepoint Temperature",
            standardRef = "CIGRE TB 244 / IEEE 738",
            definition = "The thermal transition point in bi-metal conductors (e.g., ACSR) where aluminum thermal expansion exceeds steel, transferring all mechanical tension entirely to the central steel core.",
            practicalEngineeringContext = "Above the knee-point, sag rate decreases markedly because steel has a lower thermal expansion coefficient than aluminum. Crucial for thermal uprating studies."
        ),
        GlossaryTerm(
            id = "ins_08",
            category = "Inspection and Assessment",
            term = "Equipotential Zone (EPZ Grounding)",
            standardRef = "OSHA 1910.269(n) / IEEE 1048",
            definition = "A protective temporary bonding arrangement that minimizes differences in potential between work equipment, conductors, structures, and worker hands and feet.",
            practicalEngineeringContext = "Mandated by OSHA during de-energized line maintenance to protect lineworkers from induced voltages or accidental re-energization."
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
            term = "Ground Potential Rise (GPR)",
            standardRef = "IEEE Std 80 / IEEE 367",
            definition = "The maximum electrical potential with respect to remote earth that a transmission tower or grounding grid can attain during a phase-to-ground fault: GPR = I_fault · R_ground.",
            practicalEngineeringContext = "Drives dangerous touch and step voltages and can transfer lethal voltages onto co-located communication cables or utility pipelines."
        ),
        GlossaryTerm(
            id = "lgt_04",
            category = "Lightning and Grounding",
            term = "Tolerable Touch & Step Voltages",
            standardRef = "IEEE Std 80-2013",
            definition = "The maximum potential difference that a human body can safely withstand without ventricular fibrillation when touching a grounded metallic object (touch) or standing between feet 1 meter apart (step).",
            practicalEngineeringContext = "Calculated using body weight (50 kg or 70 kg) and surface layer resistivity (e.g. crushed rock surfacing) to guarantee public and crew safety."
        ),
        GlossaryTerm(
            id = "lgt_05",
            category = "Lightning and Grounding",
            term = "Ground Flash Density (GFD / Ng)",
            standardRef = "IEEE Std 1410 / CIGRE 63",
            definition = "The average number of cloud-to-ground lightning flashes per square kilometer per year in a given geographic region.",
            practicalEngineeringContext = "Direct input to line lightning trip-rate calculations. In North America, ranges from <1 flash/km²/yr in the Pacific Northwest to >12 flashes/km²/yr in Florida and the Gulf Coast."
        ),
        GlossaryTerm(
            id = "lgt_06",
            category = "Lightning and Grounding",
            term = "Wenner 4-Point Soil Method",
            standardRef = "IEEE Std 81",
            definition = "A specialized geophysical test method using four collinear equally-spaced ground pins driven into the soil to measure apparent resistivity rho = 2 · pi · a · R as a function of pin spacing a.",
            practicalEngineeringContext = "Provides depth-stratified soil resistivity profiles (two-layer soil models) essential for accurate substation and transmission tower grounding design."
        ),
        GlossaryTerm(
            id = "lgt_07",
            category = "Lightning and Grounding",
            term = "Critical Flashover Voltage (CFO)",
            standardRef = "IEEE Std 4 / IEC 60060",
            definition = "The crest value of standard 1.2/50 µs lightning impulse voltage that has a 50% probability of causing electrical breakdown across an insulator string or air gap.",
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
            term = "HTLS (High-Temperature Low-Sag Conductors)",
            standardRef = "CIGRE TB 498 / IEEE 1591",
            definition = "Advanced transmission conductors designed to operate continuously at 150°C–250°C while maintaining thermal sag comparable to conventional ACSR operating at 75°C.",
            practicalEngineeringContext = "Includes ACSS, ACCC (carbon composite core), and ACCR (aluminum matrix composite), providing up to 2x capacity without tower replacement."
        ),
        GlossaryTerm(
            id = "cnd_04",
            category = "Conductor and Hardware",
            term = "Aeolian Vibration & Stockbridge Damper",
            standardRef = "IEEE Std 664 / CIGRE SC22",
            definition = "High-frequency (3 to 40 Hz), low-amplitude wind-induced vortex-shedding vibrations causing fatigue failure of conductor strands at suspension clamps. Dampened using Stockbridge resonant weights.",
            practicalEngineeringContext = "Stockbridge dampers are positioned at specific nodal distances (0.8 · half-loop length) from suspension clamps to maximize vibrational energy dissipation."
        ),
        GlossaryTerm(
            id = "cnd_05",
            category = "Conductor and Hardware",
            term = "Insulator Creepage Distance",
            standardRef = "IEC 60815 / IEEE 1313",
            definition = "The shortest path along the external surface contours of an insulator ceramic or polymer shed between live line hardware and grounded metal fittings.",
            practicalEngineeringContext = "Mandates minimum leakage distance (e.g., 25-31 mm per kV line-to-line) to prevent dry band arcing and pollution flashovers in coastal or industrial environments."
        ),
        GlossaryTerm(
            id = "cnd_06",
            category = "Conductor and Hardware",
            term = "Corona Ring (Grading Ring)",
            standardRef = "IEEE Std 1827 / EPRI",
            definition = "A smooth toroidal metallic ring installed at the energized end (and ground end above 345 kV) of an insulator string to distribute the steep non-linear electric field gradient.",
            practicalEngineeringContext = "Prevents localized air ionization (corona discharge), radio frequency interference (RFI), and polymer housing erosion on EHV/UHV lines."
        ),
        GlossaryTerm(
            id = "cnd_07",
            category = "Conductor and Hardware",
            term = "OPGW (Optical Ground Wire)",
            standardRef = "IEEE Std 1138",
            definition = "An overhead shield wire combining traditional lightning protection and fault current carrying capability with optical fiber bundles embedded inside hermetically sealed metal tubes.",
            practicalEngineeringContext = "Serves simultaneously as the line top shield wire and the high-speed optical telecommunication backbone for grid SCADA and relay protection."
        ),

        // RIGHT-OF-WAY & ENVIRONMENT
        GlossaryTerm(
            id = "row_01",
            category = "Right-of-Way and Environment",
            term = "Electric and Magnetic Fields (EMF)",
            standardRef = "IEEE Std 644 / ICNIRP Guidelines",
            definition = "Low-frequency (50/60 Hz) non-ionizing physical fields produced by line voltage (electric field, kV/m) and line current (magnetic flux density, mG / µT).",
            practicalEngineeringContext = "Engineers calculate lateral ground profiles to verify compliance with public exposure standards (ICNIRP 5 kV/m electric, 200 µT / 2000 mG magnetic) at right-of-way boundaries."
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
        ),
        GlossaryTerm(
            id = "row_04",
            category = "Right-of-Way and Environment",
            term = "MVCD (Minimum Vegetation Clearance Distance)",
            standardRef = "NERC FAC-003-4 Table 2 / Gallet Equation",
            definition = "The minimum calculated air-gap distance that must be maintained at all times between energized transmission conductors and vegetation to prevent flashover.",
            practicalEngineeringContext = "Derived from switching surge transient overvoltage factors and air density altitude correction factors. Mandatory compliance threshold under federal audits."
        ),
        GlossaryTerm(
            id = "row_05",
            category = "Right-of-Way and Environment",
            term = "FAA Part 77 Obstruction Evaluation (100:1 / 50:1)",
            standardRef = "FAA 14 CFR Part 77 / AC 70/7460-1M",
            definition = "Federal aviation regulations defining imaginary obstruction slopes radiating outward from airport runways. Structures exceeding 200 ft AGL or penetrating runway slope surfaces trigger mandatory FAA Form 7460-1 notice.",
            practicalEngineeringContext = "Dictates whether transmission towers or catenary river crossings require aviation orange/white paint banding, top obstruction beacons, or catenary marker balls."
        ),
        GlossaryTerm(
            id = "row_06",
            category = "Right-of-Way and Environment",
            term = "Catenary Spherical Aviation Marker Balls",
            standardRef = "FAA AC 70/7460-1M Chapter 12",
            definition = "High-visibility 36-inch diameter fiberglass or aluminum spherical marker balls installed on overhead transmission shield wires across navigable waters, canyons, and highway crossings.",
            practicalEngineeringContext = "Installed on the highest wire (OHGW/OPGW) at intervals of 200 feet, alternating between aviation orange, white, and aviation yellow to prevent low-flying aircraft collisions."
        ),
        GlossaryTerm(
            id = "row_07",
            category = "Right-of-Way and Environment",
            term = "Avian Protection Plan (APLIC Standards)",
            standardRef = "APLIC Guidelines / USFWS Bald & Golden Eagle Act",
            definition = "Engineering and environmental standards designed to minimize bird electrocutions and collisions with overhead transmission lines.",
            practicalEngineeringContext = "Requires 60-inch minimum horizontal separation between phase conductors and grounded structures to accommodate eagle wingspans, along with spiral flight diverters on shield wires."
        ),
        GlossaryTerm(
            id = "row_08",
            category = "Right-of-Way and Environment",
            term = "Radio & Television Interference (RI / TVI)",
            standardRef = "IEEE Std 430 / CISPR 18",
            definition = "High-frequency electromagnetic noise generated by corona discharges at conductor surface irregularities, water droplets, and hardware sharp points, interfering with AM radio and broadcast signals.",
            practicalEngineeringContext = "Regulated at right-of-way boundaries (typically <= 40–45 dBµV/m at 0.5 MHz). Controlled by bundling conductors and installing grading rings on insulator strings."
        ),

        // ADDITIONAL LINE DESIGN & REGULATORY
        GlossaryTerm(
            id = "cat_15",
            category = "Line Design",
            term = "Conductor Annealing & Loss of Tensile Strength",
            standardRef = "IEEE Std 738 Clause 5 / Harvey & Foote",
            definition = "Permanent metallurgical recrystallization and softening of hard-drawn 1350-H19 aluminum strands under sustained elevated temperatures (>93°C to 100°C), resulting in cumulative loss of breaking strength.",
            practicalEngineeringContext = "Sets the upper thermal limit for ACSR emergency operation (typically 100°C for <24 hours, limiting cumulative loss of strength to <10% over the conductor life)."
        ),
        GlossaryTerm(
            id = "cat_16",
            category = "Line Design",
            term = "NESC Rule 261 (Structural Strength Overload Factors)",
            standardRef = "NESC C2-2023 Table 261-1A",
            definition = "Regulatory strength factors applied to nominal loads for Grade B and Grade C transmission construction, specifying overload multipliers for vertical, transverse wind, and longitudinal wire tension loads.",
            practicalEngineeringContext = "Grade B construction requires a 2.50 overload factor on transverse wind loads and 1.50 on vertical ice/wire loads for wood and reinforced concrete poles."
        ),
        GlossaryTerm(
            id = "cat_17",
            category = "Line Design",
            term = "ASCE Manual 74 (Transmission Line Structural Loading)",
            standardRef = "ASCE Manual of Practice 74",
            definition = "Authoritative civil engineering guide specifying wind, ice, and combined loading criteria, spatial gust response factors, and longitudinal broken conductor cascade mitigation.",
            practicalEngineeringContext = "Standard reference for utility structural engineers calculating design loads for steel lattice towers, tubular steel poles, and guyed masts across North America."
        ),
        GlossaryTerm(
            id = "cat_18",
            category = "Line Design",
            term = "FERC Order 1920 (Long-Term Transmission Planning)",
            standardRef = "FERC Order 1920 (18 CFR Part 35)",
            definition = "Landmark federal rule requiring regional transmission operators to conduct 20-year long-term proactive transmission planning considering changing resource mixes and extreme weather reliability.",
            practicalEngineeringContext = "Mandates evaluation of grid-enhancing technologies (GETs) including dynamic line ratings (DLR), advanced conductors, and power flow controllers."
        ),
        GlossaryTerm(
            id = "cat_19",
            category = "Line Design",
            term = "Conductor Splay & Birdcaging",
            standardRef = "IEEE Std 524 / EPRI Guide",
            definition = "Deformation where outer aluminum strands loosen, expand radially, and separate from inner strands or steel core, resembling a birdcage, caused by excessive stringing tension release or sudden mechanical shock.",
            practicalEngineeringContext = "Permanently degrades conductor structural integrity and creates severe local electric field concentration, triggering intense corona and acoustic noise."
        ),

        // ADDITIONAL CORROSION TERMS
        GlossaryTerm(
            id = "cor_07",
            category = "Corrosion",
            term = "Microbiologically Influenced Corrosion (MIC)",
            standardRef = "NACE TM0106 / ASTM G161",
            definition = "Electrochemical deterioration initiated or accelerated by metabolic activity of microorganisms, primarily anaerobic sulfate-reducing bacteria (SRB) in dense clay or waterlogged soils.",
            practicalEngineeringContext = "Attacks deep transmission steel foundation piles and guy anchors, producing deep localized hemispherical pitting beneath dense black iron sulfide scale."
        ),
        GlossaryTerm(
            id = "cor_08",
            category = "Corrosion",
            term = "Sacrificial Anode Cathodic Protection (SACP)",
            standardRef = "NACE SP0169 / ASTM B843",
            definition = "Galvanic corrosion mitigation system employing highly electronegative metals (magnesium alloy or zinc) connected via copper leads directly to buried transmission anchor rods or grillage footings.",
            practicalEngineeringContext = "Standard utility remediation practice for transmission structures exhibiting anchor rod thickness loss in corrosive soils with resistivity < 2,000 ohm-cm."
        ),
        GlossaryTerm(
            id = "cor_09",
            category = "Corrosion",
            term = "ASTM A123 (Hot-Dip Galvanized Zinc Specification)",
            standardRef = "ASTM A123 / ASTM A153",
            definition = "Standard specification for zinc coatings on fabricated iron and steel products, specifying minimum coating thickness grades (e.g. Grade 100 = 100 µm / 3.9 mils zinc).",
            practicalEngineeringContext = "Defines the metallurgical alloy layers (Gamma, Delta, Zeta, Eta) providing both barrier and sacrificial protection to structural transmission lattice members."
        ),
        GlossaryTerm(
            id = "cor_10",
            category = "Corrosion",
            term = "Stray DC Current Electrolysis",
            standardRef = "NACE SP0169 / IEEE 1695",
            definition = "Severe rapid metal loss occurring where direct electric currents from external sources (e.g., DC transit systems, neighboring impressed current CP, or HVDC ground return electrodes) exit metallic structures into earth.",
            practicalEngineeringContext = "One ampere of DC current discharging from steel into soil removes approximately 20 pounds of steel per year (Faraday's law), capable of severing guy anchor rods in months."
        ),

        // ADDITIONAL INSPECTION & ASSESSMENT
        GlossaryTerm(
            id = "ins_09",
            category = "Inspection and Assessment",
            term = "NERC PRC-023 (Transmission Relay Loadability)",
            standardRef = "NERC Standard PRC-023-4",
            definition = "Mandatory reliability standard preventing transmission line protective relays from tripping during non-fault emergency overload conditions or extreme line sag events.",
            practicalEngineeringContext = "Ensures phase distance (21) relays do not mistake heavy post-contingency power transfers for line faults, which was the primary trigger of the 2003 Northeast Blackout."
        ),
        GlossaryTerm(
            id = "ins_10",
            category = "Inspection and Assessment",
            term = "NERC CIP-014 (Physical Security of Transmission Facilities)",
            standardRef = "NERC Standard CIP-014-3",
            definition = "Mandatory federal reliability standard requiring transmission owners to perform risk assessments on transmission substations and associated primary line corridors whose destruction could cause instability.",
            practicalEngineeringContext = "Governs perimeter ballistic fencing, intrusion detection cameras, and physical hardening against physical attacks and sabotage."
        ),
        GlossaryTerm(
            id = "ins_11",
            category = "Inspection and Assessment",
            term = "Airborne LiDAR Transmission Inspection",
            standardRef = "IEEE Std 1591 / EPRI Guidelines",
            definition = "Helicopter or drone-mounted Light Detection and Ranging scanning emitting hundreds of thousands of laser pulses per second to create high-density 3D digital point clouds of transmission corridors.",
            practicalEngineeringContext = "Used in conjunction with PLS-CADD software to calibrate conductor catenary profiles, verify actual operating sag, and pinpoint vegetation encroachments with sub-inch precision."
        ),
        GlossaryTerm(
            id = "ins_12",
            category = "Inspection and Assessment",
            term = "Solar-Blind Ultraviolet (UV) Corona Inspection",
            standardRef = "EPRI Corona Inspection Guide",
            definition = "Specialized optical camera operating in the solar-blind UV-C band (240–280 nm) that detects faint photon emissions from electrical corona discharges in broad daylight.",
            practicalEngineeringContext = "Pinpoints surface tracking on composite polymer insulators, broken ceramic bells, loose cotter keys, and sharp burrs on conductor hardware prior to dielectric flashover."
        ),
        GlossaryTerm(
            id = "ins_13",
            category = "Inspection and Assessment",
            term = "Infrared Thermography (IR Inspection)",
            standardRef = "ASTM E1934 / IEEE 738",
            definition = "Thermal imaging technique detecting localized elevated temperatures caused by abnormally high electrical contact resistance in energized compression splices, dead-ends, and jumpers.",
            practicalEngineeringContext = "Identifies thermal hot spots (delta T > 10°C–30°C over conductor ambient) before splice failure causes a dropped conductor outage."
        ),
        GlossaryTerm(
            id = "ins_14",
            category = "Inspection and Assessment",
            term = "Wood Pole Fiber Stress at Groundline",
            standardRef = "ANSI O5.1 / NESC Rule 261A2",
            definition = "The designated ultimate bending fiber stress of wood species used for transmission poles (Southern Yellow Pine = 8,000 psi, Douglas Fir = 8,000 psi, Western Red Cedar = 6,000 psi).",
            practicalEngineeringContext = "Fundamental baseline parameter used in calculating allowable resisting moment M_r = (pi/32) · f_b · d^3 and evaluating decay shell loss."
        ),

        // ADDITIONAL LIGHTNING & GROUNDING
        GlossaryTerm(
            id = "lgt_08",
            category = "Lightning and Grounding",
            term = "Continuous Buried Counterpoise Wire",
            standardRef = "IEEE Std 80 / IEEE Std 1243",
            definition = "Bare metallic wire (typically copper-clad steel or galvanized steel) buried 18 to 36 inches deep in the transmission right-of-way, connecting adjacent tower grounding systems together.",
            practicalEngineeringContext = "Dramatically reduces tower footing surge impedance in rocky, high-resistivity soil by acting as a distributed transmission line for lightning wavefronts."
        ),
        GlossaryTerm(
            id = "lgt_09",
            category = "Lightning and Grounding",
            term = "Soil Ionization & Critical Breakdown Gradient (E0)",
            standardRef = "IEEE Std 80 / CIGRE WG 33.01",
            definition = "The physical breakdown of soil air voids occurring when the electric field around a grounding electrode exceeds the critical ionization gradient (E0 ≈ 300 to 400 kV/m) under large lightning stroke currents.",
            practicalEngineeringContext = "Effectively increases the apparent radius of ground rods and counterpoise wires during lightning surges, lowering the transient impulse impedance below low-frequency resistance."
        ),
        GlossaryTerm(
            id = "lgt_10",
            category = "Lightning and Grounding",
            term = "Transmission Line Surge Arresters (TLSA / NGLA)",
            standardRef = "IEEE Std C62.11 / IEC 60099-4",
            definition = "Non-gapped metal-oxide varistor (MOV) surge arresters mounted directly in parallel with transmission insulator strings across tower crossarms.",
            practicalEngineeringContext = "Extinguishes overvoltages during lightning strikes to shield wires or towers, completely eliminating backflashover outages on lines with poor grounding terrain."
        ),
        GlossaryTerm(
            id = "lgt_11",
            category = "Lightning and Grounding",
            term = "Dalziel Human Body Tolerance Equation",
            standardRef = "IEEE Std 80-2013 / Dalziel (1960)",
            definition = "Empirical physiological formula I_b = k / sqrt(t_s) determining the maximum non-fibrillating shock current tolerable by 99.5% of human beings as a function of shock duration t_s.",
            practicalEngineeringContext = "Forms the absolute mathematical foundation for tolerable step and touch voltage safety criteria on all high-voltage utility transmission and substation assets."
        ),

        // ADDITIONAL CONDUCTOR & HARDWARE
        GlossaryTerm(
            id = "cnd_08",
            category = "Conductor and Hardware",
            term = "ACCC (Aluminum Conductor Composite Core)",
            standardRef = "ASTM B987 / CIGRE TB 498",
            definition = "Advanced HTLS transmission conductor featuring a high-strength carbon and glass fiber composite core enveloped by trapezoidal-shaped fully annealed (1350-O) aluminum wires.",
            practicalEngineeringContext = "Operates up to 180°C continuous with virtually zero thermal sag due to near-zero thermal expansion coefficient of carbon composite, enabling doubling of circuit capacity."
        ),
        GlossaryTerm(
            id = "cnd_09",
            category = "Conductor and Hardware",
            term = "Preformed Armor Rods",
            standardRef = "IEEE Std 524 / PLP Standard",
            definition = "High-strength aluminum alloy helical rods applied by hand over conductors at suspension clamps, extending several feet in each direction from the clamp mouth.",
            practicalEngineeringContext = "Reduces localized dynamic bending stresses from Aeolian vibration, cushions clamp clamping pressure, and protects conductor from flashover arc burns."
        ),
        GlossaryTerm(
            id = "cnd_10",
            category = "Conductor and Hardware",
            term = "Suspension Clamp Magnetic Heating & Hysteresis",
            standardRef = "EPRI Conductor Hardware Guide",
            definition = "Parasitic power loss and dangerous heat generation caused by alternating magnetic flux in ferrous ductile iron suspension clamps surrounding AC transmission conductors.",
            practicalEngineeringContext = "On lines carrying >800 to 1,000 amperes, ferrous clamps can heat to >150°C, damaging conductor strands. Requires non-magnetic cast aluminum alloy suspension clamps."
        ),
        GlossaryTerm(
            id = "cnd_11",
            category = "Conductor and Hardware",
            term = "Interphase Spacers (IPS)",
            standardRef = "CIGRE TB 322 / IEEE 563",
            definition = "Rigid or semi-rigid composite insulating cross-struts installed directly between phase conductors at designated fractions of the span (e.g. 1/3 and 2/3 points).",
            practicalEngineeringContext = "Prevents mid-span phase-to-phase contact, flashovers, and conductor burning during asymmetric ice shedding and wind-induced galloping."
        ),
        GlossaryTerm(
            id = "cnd_12",
            category = "Conductor and Hardware",
            term = "Non-Ceramic Composite Insulator (NCI)",
            standardRef = "IEC 61109 / IEEE Std 1024",
            definition = "Transmission insulator constructed from an axial high-strength fiberglass epoxy rod covered with an extruded or molded high-temperature vulcanized (HTV) silicone rubber housing with weather sheds.",
            practicalEngineeringContext = "Provides superior pollution flashover resistance due to silicone hydrophobicity transfer, light weight (10% of porcelain), and vandalism resistance against gunshots."
        ),

        // BOLSTERED STANDARDS & REGULATORY CRITERIA
        GlossaryTerm(
            id = "reg_std_01",
            category = "Line Design",
            standardRef = "ASCE Manual 74 / IEEE Std 738",
            term = "Spatial Wind Gust Coherence (Turbulence Scale)",
            definition = "The spatial correlation of wind velocity gusts across long transmission spans, dictating that peak localized gusts do not act simultaneously across an entire wire length.",
            practicalEngineeringContext = "ASCE 74 applies span reduction factors (C_w) reducing effective transverse design wind load on ruling spans exceeding 600-1,000 ft."
        ),
        GlossaryTerm(
            id = "reg_std_02",
            category = "Line Design",
            standardRef = "CIGRE TB 498 / IEEE Std 1591",
            term = "Thermal Knee Point (Knee-Point Temperature)",
            definition = "The temperature at which the outer aluminum strands of an overhead conductor become completely relaxed (zero tension), transferring 100% of the mechanical tension to the central core.",
            practicalEngineeringContext = "Above the knee point, conductor thermal sag increases at only the low thermal expansion rate of the core (e.g. Invar or carbon composite), flattening the sag curve."
        ),
        GlossaryTerm(
            id = "reg_std_03",
            category = "Corrosion",
            standardRef = "NACE SP0106 / ISO 18086",
            term = "AC Corrosion Density (i_AC)",
            definition = "Alternating current leakage density discharging from a holiday (coating defect) on buried steel into electrolyte: i_AC = (8 · V_AC) / (π · ρ · d).",
            practicalEngineeringContext = "When i_AC exceeds 30-100 A/m², rapid localized electrochemical dissolution occurs even when standard -850 mV DC cathodic protection criteria are satisfied."
        ),
        GlossaryTerm(
            id = "reg_std_04",
            category = "Inspection and Assessment",
            standardRef = "IEEE Std 1829 / EPRI 1019881",
            term = "Daytime Solar-Blind UV Sensitivity Threshold",
            definition = "The minimum optical discharge detectable by a solar-blind bi-spectral UV corona camera in daylight, typically calibrated at ~1 picocoulomb (pC) at 10 meters distance.",
            practicalEngineeringContext = "Permits early airborne detection of incipient insulator tracking, loose cotter keys, and split washers before catastrophic mechanical drop occurs."
        ),
        GlossaryTerm(
            id = "reg_std_05",
            category = "Lightning and Grounding",
            standardRef = "IEEE Std 1410 / CIGRE WG C4.401",
            term = "Shielding Failure Flashover Rate (SFFOR)",
            definition = "The expected annual rate of lightning strokes that bypass overhead shield wires (striking phase conductors directly) and exceed conductor impulse CFO.",
            practicalEngineeringContext = "Minimized by optimizing shield wire shielding angle (typically negative to <20 degrees) via Armstrong-Whitehead electrogeometric rolling sphere models."
        ),
        GlossaryTerm(
            id = "reg_std_06",
            category = "Right-of-Way and Environment",
            standardRef = "IEEE Std 644 / ICNIRP Guidelines",
            term = "ROW Boundary Magnetic Field Threshold",
            definition = "Public and occupational magnetic flux density limits at edge of right-of-way under peak continuous load current.",
            practicalEngineeringContext = "ICNIRP public guidance is 2,000 mG (200 µT); typical state utility commissions enforce 150-200 mG maximum at edge of transmission ROW."
        ),
        GlossaryTerm(
            id = "reg_std_07",
            category = "Right-of-Way and Environment",
            standardRef = "FAA AC 70/7460-1M / 14 CFR Part 77",
            term = "Catenary Aerial Marker Spheres (Warning Balls)",
            definition = "Aviation warning spheres (minimum 36 inches / 91 cm diameter) installed on overhead transmission conductors or shield wires traversing waterways and highways.",
            practicalEngineeringContext = "Must alternate in aviation orange, white, and yellow colors with maximum spacing of 200 feet (61 m) along the highest catenary wire."
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
                    it.standardRef.lowercase().contains(q) ||
                    it.practicalEngineeringContext.lowercase().contains(q)
        }
    }
}
