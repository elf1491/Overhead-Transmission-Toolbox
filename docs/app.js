const CONDUCTORS = [
  { codeWord: "Hawk", type: "ACSR", kcmil: 477.0, stranding: "26/7", diamIn: 0.858, wtLbFt: 0.655, rbsLbf: 19500, r75: 0.219, amp: 659 },
  { codeWord: "Drake", type: "ACSR", kcmil: 795.0, stranding: "26/7", diamIn: 1.108, wtLbFt: 1.094, rbsLbf: 31500, r75: 0.139, amp: 907 },
  { codeWord: "Cardinal", type: "ACSR", kcmil: 954.0, stranding: "54/7", diamIn: 1.196, wtLbFt: 1.226, rbsLbf: 33800, r75: 0.117, amp: 1014 },
  { codeWord: "Curlew", type: "ACSR", kcmil: 1033.5, stranding: "54/7", diamIn: 1.246, wtLbFt: 1.328, rbsLbf: 36300, r75: 0.108, amp: 1063 },
  { codeWord: "Bluejay", type: "ACSR", kcmil: 1113.0, stranding: "45/7", diamIn: 1.259, wtLbFt: 1.254, rbsLbf: 30700, r75: 0.103, amp: 1109 },
  { codeWord: "Finch", type: "ACSR", kcmil: 1113.0, stranding: "54/19", diamIn: 1.293, wtLbFt: 1.431, rbsLbf: 39100, r75: 0.102, amp: 1115 },
  { codeWord: "Partridge", type: "ACSR", kcmil: 266.8, stranding: "26/7", diamIn: 0.642, wtLbFt: 0.367, rbsLbf: 11300, r75: 0.385, amp: 475 },
  { codeWord: "Penguin", type: "ACSR", kcmil: 211.6, stranding: "6/1", diamIn: 0.563, wtLbFt: 0.291, rbsLbf: 8290, r75: 0.442, amp: 430 },
  { codeWord: "Grosbeak", type: "ACSR", kcmil: 636.0, stranding: "26/7", diamIn: 0.990, wtLbFt: 0.875, rbsLbf: 25200, r75: 0.174, amp: 786 },
  { codeWord: "Rail", type: "ACSR", kcmil: 954.0, stranding: "45/7", diamIn: 1.165, wtLbFt: 1.075, rbsLbf: 26300, r75: 0.120, amp: 990 },
  { codeWord: "Drake/ACSS", type: "ACSS", kcmil: 795.0, stranding: "26/7", diamIn: 1.108, wtLbFt: 1.094, rbsLbf: 31500, r75: 0.139, amp: 1420 },
  { codeWord: "Cardinal/ACSS", type: "ACSS", kcmil: 954.0, stranding: "54/7", diamIn: 1.196, wtLbFt: 1.226, rbsLbf: 33800, r75: 0.117, amp: 1590 },
  { codeWord: "Greeley", type: "AAAC", kcmil: 927.2, stranding: "37", diamIn: 1.108, wtLbFt: 0.868, rbsLbf: 28200, r75: 0.119, amp: 930 }
];

const GLOSSARY_TERMS = [
  {
    "id": "cat_01",
    "cat": "Line Design",
    "term": "Catenary Curve",
    "std": "IEEE Std 738 / ASCE 74",
    "def": "The hyperbolic cosine curve y = C \u00b7 (cosh(x/C) - 1) assumed by a flexible overhead cable hanging freely under its distributed weight between two supports.",
    "ctx": "Used in transmission line engineering to calculate mid-span sag, clearance to ground, conductor length, and maximum tension under wind and ice loadings."
  },
  {
    "id": "cat_02",
    "cat": "Line Design",
    "term": "Ruling Span (Equivalent Span)",
    "std": "NESC Section 25 / CIGRE SC22",
    "def": "A hypothetical single level span length whose tension responds to changes in temperature, ice, and wind in the same manner as the average span in a long dead-end section with suspension insulators.",
    "ctx": "Calculated as S_r = sqrt(sum(S_i\u00b3) / sum(S_i)). Suspension insulators swing freely to equalize horizontal tension across spans to the ruling span tension."
  },
  {
    "id": "cat_03",
    "cat": "Line Design",
    "term": "NESC Rule 232 (Vertical Ground Clearances)",
    "std": "NESC C2-2023 Table 232-1",
    "def": "Regulatory minimum vertical clearances of wires, conductors, and cables above ground, roadway, railroad track, and water surfaces.",
    "ctx": "Prescribes base clearance up to 22 kV plus a mandatory voltage adder of 0.4 inches per kV in excess of 22 kV, with a 3% per 1000 ft altitude adder above 3300 ft."
  },
  {
    "id": "cat_04",
    "cat": "Line Design",
    "term": "NESC Rule 234 (Clearance to Buildings & Bridges)",
    "std": "NESC C2-2023 Table 234-1",
    "def": "Regulatory minimum horizontal and vertical clearances between energized transmission conductors and adjacent structures, buildings, signs, and bridges.",
    "ctx": "Evaluated at rest and under blowout sway (typically 6 psf wind at 60\u00b0F) to ensure sufficient insulation buffer and prevent flashovers."
  },
  {
    "id": "cat_05",
    "cat": "Line Design",
    "term": "Blowout Angle",
    "std": "NESC Rule 234 / ASCE 74",
    "def": "The angular displacement of a conductor from the vertical plane under transverse wind pressure: arctan(W_wind / W_vertical).",
    "ctx": "Dictates right-of-way width requirements and minimum clearance to adjacent trees, buildings, and transmission towers to prevent flashover during extreme wind events."
  },
  {
    "id": "cat_06",
    "cat": "Line Design",
    "term": "NESC Loading Districts (Rule 250B)",
    "std": "NESC C2-2023 Table 250-1",
    "def": "Three geographic climatic zones in North America (Heavy, Medium, Light) defining regulatory radial ice thickness, transverse wind pressure, ambient temperature, and constant K added to resultant conductor loading.",
    "ctx": "Heavy loading requires 0.50 in radial ice, 4 psf wind at 0\u00b0F, plus 0.30 lb/ft constant. Mandatory minimum for structural safety in northern and northeastern US."
  },
  {
    "id": "cat_07",
    "cat": "Line Design",
    "term": "NESC Extreme Wind (Rule 250C)",
    "std": "NESC C2-2023 Rule 250C / ASCE 74",
    "def": "Special load case applying high-velocity gust wind (up to 140+ mph) without ice to transmission structures whose height exceeds 60 ft above ground or water.",
    "ctx": "Prevents catastrophic structural failure from extreme hurricane and derecho winds; governs tall lattice towers and wide river crossing structures."
  },
  {
    "id": "cat_08",
    "cat": "Line Design",
    "term": "NESC Extreme Ice with Concurrent Wind (Rule 250D)",
    "std": "NESC C2-2023 Rule 250D",
    "def": "Load case applying site-specific 50-year mean recurrence radial glaze ice (up to 1.0\u20131.5 inches) combined with concurrent 30\u201350 mph wind without the K constant.",
    "ctx": "Primary defense against catastrophic freezing rain and ice storm cascade failures across North America."
  },
  {
    "id": "cat_09",
    "cat": "Line Design",
    "term": "NESC Grade of Construction (Grade B vs Grade C)",
    "std": "NESC Section 24 & Rule 261",
    "def": "Regulatory classification system specifying safety factors and strength requirements for overhead line supports.",
    "ctx": "Grade B is the highest standard, mandatory for major highway, railroad, and transmission line crossings. Grade C is permitted for minor distribution lines."
  },
  {
    "id": "cat_10",
    "cat": "Line Design",
    "term": "Conductor Creep & Inelastic Strain",
    "std": "Aluminum Association / IEEE 738",
    "def": "The irreversible metallurgical plastic elongation of a stranded aluminum conductor occurring under sustained mechanical tension over decades of service.",
    "ctx": "Creates permanent sag increase. Line engineers account for 10-year creep when calculating 50-year ground clearance margins."
  },
  {
    "id": "cat_11",
    "cat": "Line Design",
    "term": "Conductor Galloping",
    "std": "CIGRE TB 322 / IEEE 563",
    "def": "High-amplitude, low-frequency (0.1 to 1.0 Hz) wind-induced standing-wave oscillations of overhead lines, typically triggered by asymmetric ice accretions creating aerodynamic lift.",
    "ctx": "Can cause phase-to-phase contact or structural crossarm failures. Mitigated using interphase spacers and aerodynamic drag dampers."
  },
  {
    "id": "cat_12",
    "cat": "Line Design",
    "term": "Emissivity & Solar Absorptivity",
    "std": "IEEE Std 738 / CIGRE TB 601",
    "def": "Dimensionless surface thermal coefficients defining radiative heat rejection (emissivity \u03b5) and solar irradiance absorption (absorptivity \u03b1) of bare overhead conductors.",
    "ctx": "New shiny aluminum has low emissivity (\u03b5\u22480.23), radiating less heat and running hotter than aged dark aluminum (\u03b5\u22480.80\u20130.90). Crucial for ampacity ratings under FERC Order 881."
  },
  {
    "id": "cat_13",
    "cat": "Line Design",
    "term": "Ambient-Adjusted Ratings (AAR) & FERC Order 881",
    "std": "FERC Order 881 / IEEE 738",
    "def": "Federal regulation requiring transmission providers to implement transmission line ratings that adjust dynamically based on forecasted ambient temperatures in 1-hour increments.",
    "ctx": "Replaces static summer/winter seasonal ratings with temperature-dependent ampacity, unlocking 10%\u201330% extra transmission capacity during cooler hours."
  },
  {
    "id": "cat_14",
    "cat": "Line Design",
    "term": "Dynamic Line Rating (DLR)",
    "std": "CIGRE TB 601 / EPRI Guidelines",
    "def": "Real-time calculation of conductor thermal capacity based on concurrent measurements of ambient temperature, wind speed, wind angle, solar radiation, and conductor tension/sag.",
    "ctx": "Utilizes line-mounted sensors and weather stations to dramatically increase transmission transfer limits above conservative static planning assumptions."
  },
  {
    "id": "cor_01",
    "cat": "Corrosion",
    "term": "Atmospheric Corrosivity Category (C1-CX)",
    "std": "ISO 9223 / ASTM G92",
    "def": "International standardized classification quantifying the aggressiveness of atmospheric environments based on sulfur dioxide (SO2), airborne salinity (Cl-), temperature, and time of wetness.",
    "ctx": "Determines hot-dip galvanizing zinc loss rate (from 0.1 \u00b5m/yr in C1 up to >8.4 \u00b5m/yr in C5/CX), driving life-cycle repaint or replacement intervals for transmission towers."
  },
  {
    "id": "cor_02",
    "cat": "Corrosion",
    "term": "Galvanic Corrosion",
    "std": "ASTM G82 / NACE SP0169",
    "def": "Accelerated electrochemical degradation occurring when two dissimilar metals with different electrode potentials are in direct electrical contact within a common electrolyte.",
    "ctx": "In transmission lines, dangerous at aluminum-to-copper hardware connections or steel-to-aluminum core interfaces in ACSR conductors without protective grease barriers."
  },
  {
    "id": "cor_03",
    "cat": "Corrosion",
    "term": "Romanoff Soil Corrosion Model",
    "std": "NIST Circular 579",
    "def": "Empirical power-law model P = k \u00b7 t^n estimating the maximum underground corrosion penetration depth P of carbon steel as a function of exposure time t and soil properties.",
    "ctx": "Used by transmission asset managers to predict remaining steel guy anchor rod thickness and foundation grillage degradation in low resistivity soils."
  },
  {
    "id": "cor_04",
    "cat": "Corrosion",
    "term": "AC Induced Corrosion",
    "std": "ISO 18086 / NACE SP0106",
    "def": "Rapid localized electrochemical metal loss driven by alternating current discharge from buried metallic structures (e.g., pipelines or anchor rods) into the soil, induced by electromagnetic coupling from adjacent high-voltage lines.",
    "ctx": "Current densities exceeding 100 A/m\u00b2 create severe pitting risks. Requires solid-state decouplers and sacrificial zinc mitigation ribbons."
  },
  {
    "id": "cor_05",
    "cat": "Corrosion",
    "term": "Cathodic Protection (CP)",
    "std": "NACE SP0169 / IEEE 1695",
    "def": "A technique used to control corrosion of a metal surface by making it the cathode of an electrochemical cell, using either sacrificial galvanic anodes (magnesium/zinc) or impressed direct current (ICCP).",
    "ctx": "Applied to transmission tower grillage footings, steel direct-embedded monopoles, and buried guy anchor assemblies in aggressive soils."
  },
  {
    "id": "cor_06",
    "cat": "Corrosion",
    "term": "Soil-Line Necking",
    "std": "ASCE Manual 141",
    "def": "Accelerated localized corrosion concentrated within the top 6 to 24 inches of soil where fluctuating oxygen concentration and moisture create intense differential aeration cells.",
    "ctx": "Responsible for abrupt failure of direct-embedded steel poles and guy anchor rods. Requires mandatory excavation and ultrasonic thickness (UT) inspection."
  },
  {
    "id": "ins_01",
    "cat": "Inspection and Assessment",
    "term": "Minimum Approach Distance (MAD)",
    "std": "OSHA 29 CFR 1910.269 / IEEE 516",
    "def": "The minimum air distance that a qualified electrical worker must maintain from energized parts without approved live-line tools or protective insulating equipment.",
    "ctx": "Calculated as electrical component D_E plus ergonomic inadvertent movement adder D_M (typically 2 ft / 0.61 m), adjusted for per-unit transient overvoltages (T) and altitude."
  },
  {
    "id": "ins_02",
    "cat": "Inspection and Assessment",
    "term": "NERC FAC-003 (Vegetation Management)",
    "std": "NERC Standard FAC-003-4",
    "def": "Mandatory North American electric reliability standard requiring transmission owners to prevent tree-caused outages on lines operating at 200 kV and above.",
    "ctx": "Enforces Minimum Vegetation Clearance Distances (MVCD). Violations carry severe federal financial penalties up to $1 million per day per violation."
  },
  {
    "id": "ins_03",
    "cat": "Inspection and Assessment",
    "term": "NERC FAC-008 (Facility Ratings & Most Limiting Element)",
    "std": "NERC Standard FAC-008-5",
    "def": "Mandatory reliability standard requiring transmission owners to document the facility rating of each circuit based on its single most limiting series element.",
    "ctx": "Identifies whether the line thermal limit is constrained by the conductor, wave trap, current transformer (CT), disconnect switch, or protective relay setting."
  },
  {
    "id": "ins_04",
    "cat": "Inspection and Assessment",
    "term": "Transmission Defect Priority Index (DSI)",
    "std": "EPRI Transmission Inspection Guidelines",
    "def": "A structured risk-matrix ranking system categorizing physical tower, conductor, and insulator defects from P1 (immediate hazard <24h) to P5 (monitor during standard patrol).",
    "ctx": "Ensures critical public safety hazards (e.g. sheared tower leg bolts, burnt conductor strands, or washed out foundations) trigger emergency clearances."
  },
  {
    "id": "ins_05",
    "cat": "Inspection and Assessment",
    "term": "Wood Pole Sounding & Boring",
    "std": "ANSI O5.1 / ASCE Manual 91",
    "def": "Non-destructive acoustic hammer tapping combined with incremental resistance core drilling to detect internal decay pockets and quantify residual sound wood shell thickness.",
    "ctx": "NESC Rule 261 mandates that when a wood transmission pole loses more than one-third of its original moment capacity (remaining strength <67%), it must be reinforced (C-Truss) or replaced."
  },
  {
    "id": "ins_06",
    "cat": "Inspection and Assessment",
    "term": "Slenderness Ratio (KL/r)",
    "std": "ASCE 10 / AISC 360",
    "def": "The ratio of the effective unbraced buckling length KL of a structural compression member to the minimum radius of gyration r of its cross-section.",
    "ctx": "ASCE 10 specifies maximum KL/r limits for latticed steel transmission towers (typically <= 200 for leg members). Bent or missing diagonal members drastically increase KL/r and trigger premature buckling."
  },
  {
    "id": "ins_07",
    "cat": "Inspection and Assessment",
    "term": "Conductor Kneepoint Temperature",
    "std": "CIGRE TB 244 / IEEE 738",
    "def": "The thermal transition point in bi-metal conductors (e.g., ACSR) where aluminum thermal expansion exceeds steel, transferring all mechanical tension entirely to the central steel core.",
    "ctx": "Above the knee-point, sag rate decreases markedly because steel has a lower thermal expansion coefficient than aluminum. Crucial for thermal uprating studies."
  },
  {
    "id": "ins_08",
    "cat": "Inspection and Assessment",
    "term": "Equipotential Zone (EPZ Grounding)",
    "std": "OSHA 1910.269(n) / IEEE 1048",
    "def": "A protective temporary bonding arrangement that minimizes differences in potential between work equipment, conductors, structures, and worker hands and feet.",
    "ctx": "Mandated by OSHA during de-energized line maintenance to protect lineworkers from induced voltages or accidental re-energization."
  },
  {
    "id": "lgt_01",
    "cat": "Lightning and Grounding",
    "term": "Backflashover (BFOR)",
    "std": "IEEE Std 1243 / CIGRE SC33",
    "def": "A dielectric flashover across an insulator string occurring when lightning strikes the shield wire or tower top, raising the tower structure voltage above the phase conductor potential due to high tower footing resistance.",
    "ctx": "The dominant cause of lightning outages on high-voltage lines. Controlled by reducing tower footing resistance below 10-15 ohms and adding radial counterpoise."
  },
  {
    "id": "lgt_02",
    "cat": "Lightning and Grounding",
    "term": "Shielding Failure (SFFOR)",
    "std": "IEEE Std 1243 / IEEE 1410",
    "def": "A direct lightning strike terminating directly on a phase conductor instead of the overhead shield wire (OHGW/OPGW), occurring when the lightning leader penetrates the protective cone of protection.",
    "ctx": "Evaluated using the Electrogeometric Model (EGM). Mitigated by maintaining a small shielding angle (typically <= 30\u00b0 on single-circuit towers and negative angles on tall river crossings)."
  },
  {
    "id": "lgt_03",
    "cat": "Lightning and Grounding",
    "term": "Ground Potential Rise (GPR)",
    "std": "IEEE Std 80 / IEEE 367",
    "def": "The maximum electrical potential with respect to remote earth that a transmission tower or grounding grid can attain during a phase-to-ground fault: GPR = I_fault \u00b7 R_ground.",
    "ctx": "Drives dangerous touch and step voltages and can transfer lethal voltages onto co-located communication cables or utility pipelines."
  },
  {
    "id": "lgt_04",
    "cat": "Lightning and Grounding",
    "term": "Tolerable Touch & Step Voltages",
    "std": "IEEE Std 80-2013",
    "def": "The maximum potential difference that a human body can safely withstand without ventricular fibrillation when touching a grounded metallic object (touch) or standing between feet 1 meter apart (step).",
    "ctx": "Calculated using body weight (50 kg or 70 kg) and surface layer resistivity (e.g. crushed rock surfacing) to guarantee public and crew safety."
  },
  {
    "id": "lgt_05",
    "cat": "Lightning and Grounding",
    "term": "Ground Flash Density (GFD / Ng)",
    "std": "IEEE Std 1410 / CIGRE 63",
    "def": "The average number of cloud-to-ground lightning flashes per square kilometer per year in a given geographic region.",
    "ctx": "Direct input to line lightning trip-rate calculations. In North America, ranges from <1 flash/km\u00b2/yr in the Pacific Northwest to >12 flashes/km\u00b2/yr in Florida and the Gulf Coast."
  },
  {
    "id": "lgt_06",
    "cat": "Lightning and Grounding",
    "term": "Wenner 4-Point Soil Method",
    "std": "IEEE Std 81",
    "def": "A specialized geophysical test method using four collinear equally-spaced ground pins driven into the soil to measure apparent resistivity rho = 2 \u00b7 pi \u00b7 a \u00b7 R as a function of pin spacing a.",
    "ctx": "Provides depth-stratified soil resistivity profiles (two-layer soil models) essential for accurate substation and transmission tower grounding design."
  },
  {
    "id": "lgt_07",
    "cat": "Lightning and Grounding",
    "term": "Critical Flashover Voltage (CFO)",
    "std": "IEEE Std 4 / IEC 60060",
    "def": "The crest value of standard 1.2/50 \u00b5s lightning impulse voltage that has a 50% probability of causing electrical breakdown across an insulator string or air gap.",
    "ctx": "Defines the basic impulse insulation level (BIL) of the line. A 230 kV suspension string with 14 standard porcelain bells has a dry lightning CFO of approximately 1200-1300 kV."
  },
  {
    "id": "cnd_01",
    "cat": "Conductor and Hardware",
    "term": "ACSR (Aluminum Conductor Steel Reinforced)",
    "std": "ASTM B232 / CSA C49",
    "def": "A concentric-lay-stranded conductor composed of one or more layers of hard-drawn 1350-H19 aluminum wires surrounding a central high-strength galvanized steel core.",
    "ctx": "The historical industry standard for high-voltage transmission, named after birds (e.g. Drake, Hawk, Cardinal) due to its high strength-to-weight ratio."
  },
  {
    "id": "cnd_02",
    "cat": "Conductor and Hardware",
    "term": "ACSS (Aluminum Conductor Steel Supported)",
    "std": "ASTM B856",
    "def": "A stranded transmission conductor with annealed (O-temper) aluminum wires over a high-strength mischmetal or aluminum-clad steel core, rated for continuous operation up to 200\u00b0C\u2013250\u00b0C.",
    "ctx": "High-temperature low-sag (HTLS) workhorse for reconductoring existing transmission corridors without exceeding structure tower loading limits."
  },
  {
    "id": "cnd_03",
    "cat": "Conductor and Hardware",
    "term": "HTLS (High-Temperature Low-Sag Conductors)",
    "std": "CIGRE TB 498 / IEEE 1591",
    "def": "Advanced transmission conductors designed to operate continuously at 150\u00b0C\u2013250\u00b0C while maintaining thermal sag comparable to conventional ACSR operating at 75\u00b0C.",
    "ctx": "Includes ACSS, ACCC (carbon composite core), and ACCR (aluminum matrix composite), providing up to 2x capacity without tower replacement."
  },
  {
    "id": "cnd_04",
    "cat": "Conductor and Hardware",
    "term": "Aeolian Vibration & Stockbridge Damper",
    "std": "IEEE Std 664 / CIGRE SC22",
    "def": "High-frequency (3 to 40 Hz), low-amplitude wind-induced vortex-shedding vibrations causing fatigue failure of conductor strands at suspension clamps. Dampened using Stockbridge resonant weights.",
    "ctx": "Stockbridge dampers are positioned at specific nodal distances (0.8 \u00b7 half-loop length) from suspension clamps to maximize vibrational energy dissipation."
  },
  {
    "id": "cnd_05",
    "cat": "Conductor and Hardware",
    "term": "Insulator Creepage Distance",
    "std": "IEC 60815 / IEEE 1313",
    "def": "The shortest path along the external surface contours of an insulator ceramic or polymer shed between live line hardware and grounded metal fittings.",
    "ctx": "Mandates minimum leakage distance (e.g., 25-31 mm per kV line-to-line) to prevent dry band arcing and pollution flashovers in coastal or industrial environments."
  },
  {
    "id": "cnd_06",
    "cat": "Conductor and Hardware",
    "term": "Corona Ring (Grading Ring)",
    "std": "IEEE Std 1827 / EPRI",
    "def": "A smooth toroidal metallic ring installed at the energized end (and ground end above 345 kV) of an insulator string to distribute the steep non-linear electric field gradient.",
    "ctx": "Prevents localized air ionization (corona discharge), radio frequency interference (RFI), and polymer housing erosion on EHV/UHV lines."
  },
  {
    "id": "cnd_07",
    "cat": "Conductor and Hardware",
    "term": "OPGW (Optical Ground Wire)",
    "std": "IEEE Std 1138",
    "def": "An overhead shield wire combining traditional lightning protection and fault current carrying capability with optical fiber bundles embedded inside hermetically sealed metal tubes.",
    "ctx": "Serves simultaneously as the line top shield wire and the high-speed optical telecommunication backbone for grid SCADA and relay protection."
  },
  {
    "id": "row_01",
    "cat": "Right-of-Way and Environment",
    "term": "Electric and Magnetic Fields (EMF)",
    "std": "IEEE Std 644 / ICNIRP Guidelines",
    "def": "Low-frequency (50/60 Hz) non-ionizing physical fields produced by line voltage (electric field, kV/m) and line current (magnetic flux density, mG / \u00b5T).",
    "ctx": "Engineers calculate lateral ground profiles to verify compliance with public exposure standards (ICNIRP 5 kV/m electric, 200 \u00b5T / 2000 mG magnetic) at right-of-way boundaries."
  },
  {
    "id": "row_02",
    "cat": "Right-of-Way and Environment",
    "term": "Audible Noise (Wet Conductor Corona)",
    "std": "EPRI Reference Book / IEEE 656",
    "def": "Acoustic buzzing, frying, and crackling sounds generated by electrical discharges into moist air at water droplets adhering to conductor surfaces under high electric gradient.",
    "ctx": "Predominant at 345 kV and above during rain or heavy fog. Mitigated by using bundle conductors (2, 3, or 4 subconductors per phase) to reduce surface voltage gradient."
  },
  {
    "id": "row_03",
    "cat": "Right-of-Way and Environment",
    "term": "Danger Tree / Clearance Zone",
    "std": "NERC FAC-003-4 / NESC Rule 218",
    "def": "Any tree located inside or adjacent to the transmission right-of-way that could contact a phase conductor under max blowout, max operating sag, or fall-in trajectory.",
    "ctx": "Federal mandatory reliability compliance standard (FAC-003) enforcing zero vegetation-related line outages on bulk electric systems (200 kV+)."
  },
  {
    "id": "row_04",
    "cat": "Right-of-Way and Environment",
    "term": "MVCD (Minimum Vegetation Clearance Distance)",
    "std": "NERC FAC-003-4 Table 2 / Gallet Equation",
    "def": "The minimum calculated air-gap distance that must be maintained at all times between energized transmission conductors and vegetation to prevent flashover.",
    "ctx": "Derived from switching surge transient overvoltage factors and air density altitude correction factors. Mandatory compliance threshold under federal audits."
  },
  {
    "id": "row_05",
    "cat": "Right-of-Way and Environment",
    "term": "FAA Part 77 Obstruction Evaluation (100:1 / 50:1)",
    "std": "FAA 14 CFR Part 77 / AC 70/7460-1M",
    "def": "Federal aviation regulations defining imaginary obstruction slopes radiating outward from airport runways. Structures exceeding 200 ft AGL or penetrating runway slope surfaces trigger mandatory FAA Form 7460-1 notice.",
    "ctx": "Dictates whether transmission towers or catenary river crossings require aviation orange/white paint banding, top obstruction beacons, or catenary marker balls."
  },
  {
    "id": "row_06",
    "cat": "Right-of-Way and Environment",
    "term": "Catenary Spherical Aviation Marker Balls",
    "std": "FAA AC 70/7460-1M Chapter 12",
    "def": "High-visibility 36-inch diameter fiberglass or aluminum spherical marker balls installed on overhead transmission shield wires across navigable waters, canyons, and highway crossings.",
    "ctx": "Installed on the highest wire (OHGW/OPGW) at intervals of 200 feet, alternating between aviation orange, white, and aviation yellow to prevent low-flying aircraft collisions."
  },
  {
    "id": "row_07",
    "cat": "Right-of-Way and Environment",
    "term": "Avian Protection Plan (APLIC Standards)",
    "std": "APLIC Guidelines / USFWS Bald & Golden Eagle Act",
    "def": "Engineering and environmental standards designed to minimize bird electrocutions and collisions with overhead transmission lines.",
    "ctx": "Requires 60-inch minimum horizontal separation between phase conductors and grounded structures to accommodate eagle wingspans, along with spiral flight diverters on shield wires."
  },
  {
    "id": "row_08",
    "cat": "Right-of-Way and Environment",
    "term": "Radio & Television Interference (RI / TVI)",
    "std": "IEEE Std 430 / CISPR 18",
    "def": "High-frequency electromagnetic noise generated by corona discharges at conductor surface irregularities, water droplets, and hardware sharp points, interfering with AM radio and broadcast signals.",
    "ctx": "Regulated at right-of-way boundaries (typically <= 40\u201345 dB\u00b5V/m at 0.5 MHz). Controlled by bundling conductors and installing grading rings on insulator strings."
  },
  {
    "id": "cat_15",
    "cat": "Line Design",
    "term": "Conductor Annealing & Loss of Tensile Strength",
    "std": "IEEE Std 738 Clause 5 / Harvey & Foote",
    "def": "Permanent metallurgical recrystallization and softening of hard-drawn 1350-H19 aluminum strands under sustained elevated temperatures (>93\u00b0C to 100\u00b0C), resulting in cumulative loss of breaking strength.",
    "ctx": "Sets the upper thermal limit for ACSR emergency operation (typically 100\u00b0C for <24 hours, limiting cumulative loss of strength to <10% over the conductor life)."
  },
  {
    "id": "cat_16",
    "cat": "Line Design",
    "term": "NESC Rule 261 (Structural Strength Overload Factors)",
    "std": "NESC C2-2023 Table 261-1A",
    "def": "Regulatory strength factors applied to nominal loads for Grade B and Grade C transmission construction, specifying overload multipliers for vertical, transverse wind, and longitudinal wire tension loads.",
    "ctx": "Grade B construction requires a 2.50 overload factor on transverse wind loads and 1.50 on vertical ice/wire loads for wood and reinforced concrete poles."
  },
  {
    "id": "cat_17",
    "cat": "Line Design",
    "term": "ASCE Manual 74 (Transmission Line Structural Loading)",
    "std": "ASCE Manual of Practice 74",
    "def": "Authoritative civil engineering guide specifying wind, ice, and combined loading criteria, spatial gust response factors, and longitudinal broken conductor cascade mitigation.",
    "ctx": "Standard reference for utility structural engineers calculating design loads for steel lattice towers, tubular steel poles, and guyed masts across North America."
  },
  {
    "id": "cat_18",
    "cat": "Line Design",
    "term": "FERC Order 1920 (Long-Term Transmission Planning)",
    "std": "FERC Order 1920 (18 CFR Part 35)",
    "def": "Landmark federal rule requiring regional transmission operators to conduct 20-year long-term proactive transmission planning considering changing resource mixes and extreme weather reliability.",
    "ctx": "Mandates evaluation of grid-enhancing technologies (GETs) including dynamic line ratings (DLR), advanced conductors, and power flow controllers."
  },
  {
    "id": "cat_19",
    "cat": "Line Design",
    "term": "Conductor Splay & Birdcaging",
    "std": "IEEE Std 524 / EPRI Guide",
    "def": "Deformation where outer aluminum strands loosen, expand radially, and separate from inner strands or steel core, resembling a birdcage, caused by excessive stringing tension release or sudden mechanical shock.",
    "ctx": "Permanently degrades conductor structural integrity and creates severe local electric field concentration, triggering intense corona and acoustic noise."
  },
  {
    "id": "cor_07",
    "cat": "Corrosion",
    "term": "Microbiologically Influenced Corrosion (MIC)",
    "std": "NACE TM0106 / ASTM G161",
    "def": "Electrochemical deterioration initiated or accelerated by metabolic activity of microorganisms, primarily anaerobic sulfate-reducing bacteria (SRB) in dense clay or waterlogged soils.",
    "ctx": "Attacks deep transmission steel foundation piles and guy anchors, producing deep localized hemispherical pitting beneath dense black iron sulfide scale."
  },
  {
    "id": "cor_08",
    "cat": "Corrosion",
    "term": "Sacrificial Anode Cathodic Protection (SACP)",
    "std": "NACE SP0169 / ASTM B843",
    "def": "Galvanic corrosion mitigation system employing highly electronegative metals (magnesium alloy or zinc) connected via copper leads directly to buried transmission anchor rods or grillage footings.",
    "ctx": "Standard utility remediation practice for transmission structures exhibiting anchor rod thickness loss in corrosive soils with resistivity < 2,000 ohm-cm."
  },
  {
    "id": "cor_09",
    "cat": "Corrosion",
    "term": "ASTM A123 (Hot-Dip Galvanized Zinc Specification)",
    "std": "ASTM A123 / ASTM A153",
    "def": "Standard specification for zinc coatings on fabricated iron and steel products, specifying minimum coating thickness grades (e.g. Grade 100 = 100 \u00b5m / 3.9 mils zinc).",
    "ctx": "Defines the metallurgical alloy layers (Gamma, Delta, Zeta, Eta) providing both barrier and sacrificial protection to structural transmission lattice members."
  },
  {
    "id": "cor_10",
    "cat": "Corrosion",
    "term": "Stray DC Current Electrolysis",
    "std": "NACE SP0169 / IEEE 1695",
    "def": "Severe rapid metal loss occurring where direct electric currents from external sources (e.g., DC transit systems, neighboring impressed current CP, or HVDC ground return electrodes) exit metallic structures into earth.",
    "ctx": "One ampere of DC current discharging from steel into soil removes approximately 20 pounds of steel per year (Faraday's law), capable of severing guy anchor rods in months."
  },
  {
    "id": "ins_09",
    "cat": "Inspection and Assessment",
    "term": "NERC PRC-023 (Transmission Relay Loadability)",
    "std": "NERC Standard PRC-023-4",
    "def": "Mandatory reliability standard preventing transmission line protective relays from tripping during non-fault emergency overload conditions or extreme line sag events.",
    "ctx": "Ensures phase distance (21) relays do not mistake heavy post-contingency power transfers for line faults, which was the primary trigger of the 2003 Northeast Blackout."
  },
  {
    "id": "ins_10",
    "cat": "Inspection and Assessment",
    "term": "NERC CIP-014 (Physical Security of Transmission Facilities)",
    "std": "NERC Standard CIP-014-3",
    "def": "Mandatory federal reliability standard requiring transmission owners to perform risk assessments on transmission substations and associated primary line corridors whose destruction could cause instability.",
    "ctx": "Governs perimeter ballistic fencing, intrusion detection cameras, and physical hardening against physical attacks and sabotage."
  },
  {
    "id": "ins_11",
    "cat": "Inspection and Assessment",
    "term": "Airborne LiDAR Transmission Inspection",
    "std": "IEEE Std 1591 / EPRI Guidelines",
    "def": "Helicopter or drone-mounted Light Detection and Ranging scanning emitting hundreds of thousands of laser pulses per second to create high-density 3D digital point clouds of transmission corridors.",
    "ctx": "Used in conjunction with PLS-CADD software to calibrate conductor catenary profiles, verify actual operating sag, and pinpoint vegetation encroachments with sub-inch precision."
  },
  {
    "id": "ins_12",
    "cat": "Inspection and Assessment",
    "term": "Solar-Blind Ultraviolet (UV) Corona Inspection",
    "std": "EPRI Corona Inspection Guide",
    "def": "Specialized optical camera operating in the solar-blind UV-C band (240\u2013280 nm) that detects faint photon emissions from electrical corona discharges in broad daylight.",
    "ctx": "Pinpoints surface tracking on composite polymer insulators, broken ceramic bells, loose cotter keys, and sharp burrs on conductor hardware prior to dielectric flashover."
  },
  {
    "id": "ins_13",
    "cat": "Inspection and Assessment",
    "term": "Infrared Thermography (IR Inspection)",
    "std": "ASTM E1934 / IEEE 738",
    "def": "Thermal imaging technique detecting localized elevated temperatures caused by abnormally high electrical contact resistance in energized compression splices, dead-ends, and jumpers.",
    "ctx": "Identifies thermal hot spots (delta T > 10\u00b0C\u201330\u00b0C over conductor ambient) before splice failure causes a dropped conductor outage."
  },
  {
    "id": "ins_14",
    "cat": "Inspection and Assessment",
    "term": "Wood Pole Fiber Stress at Groundline",
    "std": "ANSI O5.1 / NESC Rule 261A2",
    "def": "The designated ultimate bending fiber stress of wood species used for transmission poles (Southern Yellow Pine = 8,000 psi, Douglas Fir = 8,000 psi, Western Red Cedar = 6,000 psi).",
    "ctx": "Fundamental baseline parameter used in calculating allowable resisting moment M_r = (pi/32) \u00b7 f_b \u00b7 d^3 and evaluating decay shell loss."
  },
  {
    "id": "lgt_08",
    "cat": "Lightning and Grounding",
    "term": "Continuous Buried Counterpoise Wire",
    "std": "IEEE Std 80 / IEEE Std 1243",
    "def": "Bare metallic wire (typically copper-clad steel or galvanized steel) buried 18 to 36 inches deep in the transmission right-of-way, connecting adjacent tower grounding systems together.",
    "ctx": "Dramatically reduces tower footing surge impedance in rocky, high-resistivity soil by acting as a distributed transmission line for lightning wavefronts."
  },
  {
    "id": "lgt_09",
    "cat": "Lightning and Grounding",
    "term": "Soil Ionization & Critical Breakdown Gradient (E0)",
    "std": "IEEE Std 80 / CIGRE WG 33.01",
    "def": "The physical breakdown of soil air voids occurring when the electric field around a grounding electrode exceeds the critical ionization gradient (E0 \u2248 300 to 400 kV/m) under large lightning stroke currents.",
    "ctx": "Effectively increases the apparent radius of ground rods and counterpoise wires during lightning surges, lowering the transient impulse impedance below low-frequency resistance."
  },
  {
    "id": "lgt_10",
    "cat": "Lightning and Grounding",
    "term": "Transmission Line Surge Arresters (TLSA / NGLA)",
    "std": "IEEE Std C62.11 / IEC 60099-4",
    "def": "Non-gapped metal-oxide varistor (MOV) surge arresters mounted directly in parallel with transmission insulator strings across tower crossarms.",
    "ctx": "Extinguishes overvoltages during lightning strikes to shield wires or towers, completely eliminating backflashover outages on lines with poor grounding terrain."
  },
  {
    "id": "lgt_11",
    "cat": "Lightning and Grounding",
    "term": "Dalziel Human Body Tolerance Equation",
    "std": "IEEE Std 80-2013 / Dalziel (1960)",
    "def": "Empirical physiological formula I_b = k / sqrt(t_s) determining the maximum non-fibrillating shock current tolerable by 99.5% of human beings as a function of shock duration t_s.",
    "ctx": "Forms the absolute mathematical foundation for tolerable step and touch voltage safety criteria on all high-voltage utility transmission and substation assets."
  },
  {
    "id": "cnd_08",
    "cat": "Conductor and Hardware",
    "term": "ACCC (Aluminum Conductor Composite Core)",
    "std": "ASTM B987 / CIGRE TB 498",
    "def": "Advanced HTLS transmission conductor featuring a high-strength carbon and glass fiber composite core enveloped by trapezoidal-shaped fully annealed (1350-O) aluminum wires.",
    "ctx": "Operates up to 180\u00b0C continuous with virtually zero thermal sag due to near-zero thermal expansion coefficient of carbon composite, enabling doubling of circuit capacity."
  },
  {
    "id": "cnd_09",
    "cat": "Conductor and Hardware",
    "term": "Preformed Armor Rods",
    "std": "IEEE Std 524 / PLP Standard",
    "def": "High-strength aluminum alloy helical rods applied by hand over conductors at suspension clamps, extending several feet in each direction from the clamp mouth.",
    "ctx": "Reduces localized dynamic bending stresses from Aeolian vibration, cushions clamp clamping pressure, and protects conductor from flashover arc burns."
  },
  {
    "id": "cnd_10",
    "cat": "Conductor and Hardware",
    "term": "Suspension Clamp Magnetic Heating & Hysteresis",
    "std": "EPRI Conductor Hardware Guide",
    "def": "Parasitic power loss and dangerous heat generation caused by alternating magnetic flux in ferrous ductile iron suspension clamps surrounding AC transmission conductors.",
    "ctx": "On lines carrying >800 to 1,000 amperes, ferrous clamps can heat to >150\u00b0C, damaging conductor strands. Requires non-magnetic cast aluminum alloy suspension clamps."
  },
  {
    "id": "cnd_11",
    "cat": "Conductor and Hardware",
    "term": "Interphase Spacers (IPS)",
    "std": "CIGRE TB 322 / IEEE 563",
    "def": "Rigid or semi-rigid composite insulating cross-struts installed directly between phase conductors at designated fractions of the span (e.g. 1/3 and 2/3 points).",
    "ctx": "Prevents mid-span phase-to-phase contact, flashovers, and conductor burning during asymmetric ice shedding and wind-induced galloping."
  },
  {
    "id": "cnd_12",
    "cat": "Conductor and Hardware",
    "term": "Non-Ceramic Composite Insulator (NCI)",
    "std": "IEC 61109 / IEEE Std 1024",
    "def": "Transmission insulator constructed from an axial high-strength fiberglass epoxy rod covered with an extruded or molded high-temperature vulcanized (HTV) silicone rubber housing with weather sheds.",
    "ctx": "Provides superior pollution flashover resistance due to silicone hydrophobicity transfer, light weight (10% of porcelain), and vandalism resistance against gunshots."
  },
  {
    "id": "reg_std_01",
    "cat": "Line Design",
    "term": "Spatial Wind Gust Coherence (Turbulence Scale)",
    "std": "ASCE Manual 74 / IEEE Std 738",
    "def": "The spatial correlation of wind velocity gusts across long transmission spans, dictating that peak localized gusts do not act simultaneously across an entire wire length.",
    "ctx": "ASCE 74 applies span reduction factors (C_w) reducing effective transverse design wind load on ruling spans exceeding 600-1,000 ft."
  },
  {
    "id": "reg_std_02",
    "cat": "Line Design",
    "term": "Thermal Knee Point (Knee-Point Temperature)",
    "std": "CIGRE TB 498 / IEEE Std 1591",
    "def": "The temperature at which the outer aluminum strands of an overhead conductor become completely relaxed (zero tension), transferring 100% of the mechanical tension to the central core.",
    "ctx": "Above the knee point, conductor thermal sag increases at only the low thermal expansion rate of the core (e.g. Invar or carbon composite), flattening the sag curve."
  },
  {
    "id": "reg_std_03",
    "cat": "Corrosion",
    "term": "AC Corrosion Density (i_AC)",
    "std": "NACE SP0106 / ISO 18086",
    "def": "Alternating current leakage density discharging from a holiday (coating defect) on buried steel into electrolyte: i_AC = (8 \u00b7 V_AC) / (\u03c0 \u00b7 \u03c1 \u00b7 d).",
    "ctx": "When i_AC exceeds 30-100 A/m\u00b2, rapid localized electrochemical dissolution occurs even when standard -850 mV DC cathodic protection criteria are satisfied."
  },
  {
    "id": "reg_std_04",
    "cat": "Inspection and Assessment",
    "term": "Daytime Solar-Blind UV Sensitivity Threshold",
    "std": "IEEE Std 1829 / EPRI 1019881",
    "def": "The minimum optical discharge detectable by a solar-blind bi-spectral UV corona camera in daylight, typically calibrated at ~1 picocoulomb (pC) at 10 meters distance.",
    "ctx": "Permits early airborne detection of incipient insulator tracking, loose cotter keys, and split washers before catastrophic mechanical drop occurs."
  },
  {
    "id": "reg_std_05",
    "cat": "Lightning and Grounding",
    "term": "Shielding Failure Flashover Rate (SFFOR)",
    "std": "IEEE Std 1410 / CIGRE WG C4.401",
    "def": "The expected annual rate of lightning strokes that bypass overhead shield wires (striking phase conductors directly) and exceed conductor impulse CFO.",
    "ctx": "Minimized by optimizing shield wire shielding angle (typically negative to <20 degrees) via Armstrong-Whitehead electrogeometric rolling sphere models."
  },
  {
    "id": "reg_std_06",
    "cat": "Right-of-Way and Environment",
    "term": "ROW Boundary Magnetic Field Threshold",
    "std": "IEEE Std 644 / ICNIRP Guidelines",
    "def": "Public and occupational magnetic flux density limits at edge of right-of-way under peak continuous load current.",
    "ctx": "ICNIRP public guidance is 2,000 mG (200 \u00b5T); typical state utility commissions enforce 150-200 mG maximum at edge of transmission ROW."
  },
  {
    "id": "reg_std_07",
    "cat": "Right-of-Way and Environment",
    "term": "Catenary Aerial Marker Spheres (Warning Balls)",
    "std": "FAA AC 70/7460-1M / 14 CFR Part 77",
    "def": "Aviation warning spheres (minimum 36 inches / 91 cm diameter) installed on overhead transmission conductors or shield wires traversing waterways and highways.",
    "ctx": "Must alternate in aviation orange, white, and yellow colors with maximum spacing of 200 feet (61 m) along the highest catenary wire."
  }
];

const FLASHCARDS = [
  {
    "id": "fc_01",
    "term": "Catenary Sag",
    "std": "IEEE Std 738 / ASCE 74",
    "cat": "Line Design",
    "shortDef": "Hyperbolic cosine curve assumed by a flexible overhead cable hanging under distributed weight.",
    "formula": "Sag S = (w \u00b7 L\u00b2) / (8 \u00b7 H) where H is horizontal tension.",
    "context": "Governs line clearance above ground datum under max operating temperature or heavy ice/wind load cases."
  },
  {
    "id": "fc_02",
    "term": "Ruling Span (Equivalent Span)",
    "std": "NESC Section 25 / CIGRE SC22",
    "cat": "Line Design",
    "shortDef": "Hypothetical uniform level span whose tension changes identically to the average span in a dead-end section.",
    "formula": "S_r = \u221a(\u2211S_i\u00b3 / \u2211S_i). Equalizes horizontal tension via suspension insulator swing.",
    "context": "Simplifies line design and sag-tension calculations across varied span lengths between strain towers."
  },
  {
    "id": "fc_03",
    "term": "NESC Rule 232 Ground Clearance",
    "std": "NESC C2-2023 Table 232-1",
    "cat": "Line Design",
    "shortDef": "Mandatory legal vertical clearance of energized phase conductors above ground, roadways, rails, and water.",
    "formula": "Base clearance up to 22 kV + 0.4 in/kV over 22 kV + 3% per 1,000 ft altitude above 3,300 ft.",
    "context": "Ensures safe clearance under maximum operating conductor temperature or extreme ice without wind."
  },
  {
    "id": "fc_04",
    "term": "Blowout Angle & Sway",
    "std": "NESC Rule 234 / ASCE 74",
    "cat": "Line Design",
    "shortDef": "Angular deflection of overhead conductors from vertical under transverse wind pressure.",
    "formula": "\u03b8 = arctan(F_wind / W_vertical). Dictates Right-of-Way boundary clearance.",
    "context": "Evaluated at rest and under blowout sway (typically 6 psf wind) to prevent air flashover to trees or structures."
  },
  {
    "id": "fc_05",
    "term": "FERC Order 881 Ambient-Adjusted Ratings (AAR)",
    "std": "FERC Order 881 / 18 CFR \u00a7 35.28",
    "cat": "Line Design",
    "shortDef": "Federal rule requiring transmission providers to implement hourly ambient-adjusted thermal line ratings.",
    "formula": "Requires hourly AARs evaluated at \u22645\u00b0F (\u22642.8\u00b0C) temperature bins for at least 10 days ahead.",
    "context": "Replaces conservative seasonal static ratings with weather-responsive capacities, unlocking transmission transfer."
  },
  {
    "id": "fc_06",
    "term": "Conductor Emissivity & Solar Absorptivity",
    "std": "IEEE Std 738 Clause 6 / CIGRE TB 601",
    "cat": "Line Design",
    "shortDef": "Radiative heat emission (\u03b5) and solar absorption (\u03b1) coefficients governing steady-state conductor ampacity.",
    "formula": "New shiny aluminum: \u03b5=0.23, \u03b1=0.23. Weathered dark conductor: \u03b5=0.70 to 0.90, \u03b1=0.90.",
    "context": "Weathered conductors emit far more thermal radiation, significantly boosting summer thermal current carrying capacity."
  },
  {
    "id": "fc_07",
    "term": "ISO 9223 Corrosivity Category",
    "std": "ISO 9223 / ISO 9224",
    "cat": "Corrosion",
    "shortDef": "International standard classifying atmospheric corrosivity from C1 (Very Low) to CX (Extreme Offshore).",
    "formula": "Zinc loss ranges from <0.1 \u00b5m/yr (C1) up to >8.4 \u00b5m/yr (CX) in marine/industrial zones.",
    "context": "Directly predicts galvanization life on steel lattice towers, guy anchors, and hardware."
  },
  {
    "id": "fc_08",
    "term": "Zinc Galvanization Consumption",
    "std": "ASTM A123 / ASTM A153",
    "cat": "Corrosion",
    "shortDef": "Sacrificial anodic protection of structural steel by hot-dip zinc coating.",
    "formula": "Typical coating ~85 \u00b5m (~610 g/m\u00b2). Useful life = Coating Thickness \u00f7 ISO Corrosion Rate.",
    "context": "Once sacrificial zinc is consumed, base carbon steel rusts at 10x-50x faster rate, threatening structural collapse."
  },
  {
    "id": "fc_09",
    "term": "Cathodic Protection 850 mV Criterion",
    "std": "NACE SP0169 / ISO 15589",
    "cat": "Corrosion",
    "shortDef": "Electrochemical threshold for full corrosion mitigation of buried steel structures.",
    "formula": "-0.850 V (or -850 mV) DC relative to a saturated copper-copper sulfate (CSE) reference electrode.",
    "context": "Applied to transmission tower grillage footings, guy anchor rods, and buried counterpoise wires."
  },
  {
    "id": "fc_10",
    "term": "Solar-Blind UV Corona Camera",
    "std": "EPRI Assessment Guide / IEEE Std 1829",
    "cat": "Inspection and Assessment",
    "shortDef": "Optical inspection instrument detecting UV-C photon emissions (240-280 nm) from high-voltage electrical corona.",
    "formula": "Detects micro-arcs, broken insulator bells, loose hardware, and contamination in broad daylight.",
    "context": "Solar radiation in the 240-280 nm band is fully absorbed by atmospheric ozone, enabling high-contrast daytime imaging."
  },
  {
    "id": "fc_11",
    "term": "Airborne LiDAR Inspection",
    "std": "NERC FAC-003 / EPRI LiDAR Guide",
    "cat": "Inspection and Assessment",
    "shortDef": "Helicopter or drone laser scanning yielding high-density 3D georeferenced point clouds of transmission corridors.",
    "formula": "Calibrates PLS-CADD wire sag models to within 1-2 cm under actual operating temperatures.",
    "context": "Validates NERC vegetation encroachments, conductor blowouts, and terrain clearance clearances across thousands of miles."
  },
  {
    "id": "fc_12",
    "term": "OSHA 1910.269 Minimum Approach Distance (MAD)",
    "std": "29 CFR \u00a7 1910.269 Table R-6",
    "cat": "Inspection and Assessment",
    "shortDef": "Legally mandated minimum separation distance unqualified or live-line electrical workers must maintain from conductors.",
    "formula": "MAD = Electrical Component (air breakdown) + Ergonomic Inadvertent Movement Buffer (0.61 m / 2 ft).",
    "context": "Adjusted for altitude (>900 m) and max transient overvoltage (T) to protect live-line maintenance crews from flashover."
  },
  {
    "id": "fc_13",
    "term": "Lightning Backflashover",
    "std": "IEEE Std 1243 / CIGRE TB 63",
    "cat": "Lightning and Grounding",
    "shortDef": "Insulation flashover from a grounded steel tower structure back across the insulator string to a phase conductor.",
    "formula": "V_cross = I_stroke \u00b7 R_footing + L \u00b7 (di/dt) - e_coupling \u00b7 V_phase. Triggered when V_cross > CFO.",
    "context": "Occurs when lightning strikes the shield wire or tower top and high footing resistance drives tower potential above string withstand."
  },
  {
    "id": "fc_14",
    "term": "Tower Footing Resistance Target",
    "std": "IEEE Std 80 / IEEE Std 1243",
    "cat": "Lightning and Grounding",
    "shortDef": "Grounding resistance from tower steel foundation to deep remote earth.",
    "formula": "Target < 10 \u03a9 (preferred) or < 15 \u03a9 to prevent lightning backflashovers on EHV transmission lines.",
    "context": "Mitigated by installing buried radial counterpoise copper wires, chemical ground rods, or bentonite slurry."
  },
  {
    "id": "fc_15",
    "term": "Critical Flashover Voltage (CFO / U50)",
    "std": "IEEE Std 1313.1 / IEC 60071-1",
    "cat": "Lightning and Grounding",
    "shortDef": "Peak impulse crest voltage (1.2/50 \u00b5s) at which an insulator string or air gap has a 50% probability of flashover.",
    "formula": "Rule of thumb for standard porcelain bells: ~75-80 kV lightning impulse withstand per 5-3/4\\",
    "context": "Fundamental metric for insulation coordination against lightning strokes and switching surges."
  },
  {
    "id": "fc_16",
    "term": "Dalziel Human Body Tolerance Equation",
    "std": "IEEE Std 80-2013 / Dalziel",
    "cat": "Lightning and Grounding",
    "shortDef": "Empirical physiological formula defining non-fibrillating AC shock current threshold for 99.5% of humans.",
    "formula": "I_b = k / \u221at_s, where k = 0.116 for 50 kg body weight and t_s is shock duration in seconds.",
    "context": "Determines tolerable Step and Touch voltages around energized transmission towers and substation switchyards."
  },
  {
    "id": "fc_17",
    "term": "Aeolian Vibration & Strouhal Frequency",
    "std": "IEEE Std 563 / CIGRE SC22 WG01",
    "cat": "Conductor and Hardware",
    "shortDef": "High-frequency (3 to 120 Hz) low-amplitude vortex shedding standing wave vibration on taut overhead conductors.",
    "formula": "f = (St \u00b7 V) / D, where St \u2248 0.185 (Strouhal number), V is transverse wind velocity, D is diameter.",
    "context": "Causes fatigue strand breakage under suspension clamp mouths; prevented using Stockbridge resonant dampers."
  },
  {
    "id": "fc_18",
    "term": "Conductor Galloping",
    "std": "CIGRE TB 322 / EPRI Galloping Guide",
    "cat": "Conductor and Hardware",
    "shortDef": "Low-frequency (0.1 to 1.0 Hz) high-amplitude aerodynamic instability caused by moderate wind on asymmetric ice-coated conductors.",
    "formula": "Vertical oscillations can span entire sag height, creating Den Hartog torsional instability.",
    "context": "Causes destructive phase-to-phase mid-span faults and crossarm structural failures; mitigated by interphase spacers."
  },
  {
    "id": "fc_19",
    "term": "ACCC & HTLS Conductors",
    "std": "ASTM B987 / CIGRE TB 498",
    "cat": "Conductor and Hardware",
    "shortDef": "High-Temperature Low-Sag conductors using carbon composite or Invar cores with annealed trapezoidal aluminum.",
    "formula": "Continuous operation up to 180\u00b0C (vs 75-90\u00b0C for ACSR) with near-zero thermal sag knee point.",
    "context": "Enables doubling line ampacity on existing towers without costly structure replacements or clearance violations."
  },
  {
    "id": "fc_20",
    "term": "Non-Ceramic Composite Insulator (NCI)",
    "std": "IEC 61109 / IEEE Std 1024",
    "cat": "Conductor and Hardware",
    "shortDef": "Insulator featuring a high-strength fiberglass rod housed in hydrophobic silicone rubber weather sheds.",
    "formula": "Hydrophobic surface transfer prevents continuous moisture film, preventing pollution flashovers.",
    "context": "Weighs ~90% less than porcelain disc strings and resists gunshot vandalism and seismic stresses."
  },
  {
    "id": "fc_21",
    "term": "NERC FAC-003 Minimum Vegetation Clearance Distance (MVCD)",
    "std": "NERC FAC-003-4 Reliability Standard",
    "cat": "Right-of-Way and Environment",
    "shortDef": "Federal reliability clearance between energized overhead line conductors and unpruned vegetation.",
    "formula": "MVCD = Gallet Air Breakdown Distance (cd) + Overvoltage Switching Surge Factor + Wind Sway Allowance.",
    "context": "Zero-tolerance compliance standard; violations causing vegetation-induced flashovers incur fines up to $1M/day."
  },
  {
    "id": "fc_22",
    "term": "FAA 14 CFR Part 77 Obstruction Marking & Lighting",
    "std": "FAA AC 70/7460-1 / 14 CFR Part 77",
    "cat": "Right-of-Way and Environment",
    "shortDef": "Federal aviation rules for structures exceeding 200 ft AGL or intersecting airport imaginary approach slopes.",
    "formula": "Requires alternating aviation orange/white bands (7 equal bands) and red/white beacon lighting.",
    "context": "Requires spherical aerial marker balls (\u226536\\"
  },
  {
    "id": "fc_23",
    "term": "Corona Audible Noise & Radio Interference",
    "std": "EPRI Red Book / IEEE Std 430",
    "cat": "Right-of-Way and Environment",
    "shortDef": "Acoustic hiss/crackle and RF interference caused by high surface electric field gradient exceeding air breakdown (30 kV/cm).",
    "formula": "EPRI Heavy Rain Noise: L_50 \u2248 52 to 58 dBA at ROW boundary for 500 kV AC lines.",
    "context": "Mitigated by bundling phase conductors (2, 3, 4+ subconductors) and installing corona grading rings at hardware fittings."
  },
  {
    "id": "fc_24",
    "term": "AC Interference on Collocated Pipelines",
    "std": "NACE SP0177 / IEEE Std 80",
    "cat": "Right-of-Way and Environment",
    "shortDef": "Inductive and conductive voltage coupling from high-voltage AC lines into parallel buried steel pipelines.",
    "formula": "Max touch voltage on above-ground appurtenances \u2264 15 V AC steady state (NACE SP0177).",
    "context": "Mitigated by zinc ribbon counterpoise, solid-state DC decouplers, and deep grounding beds."
  }
];

const QUIZ_QUESTIONS = [
  {
    "id": "q_01",
    "question": "Under NESC Rule 232, how is the ground clearance adder calculated for transmission line voltages exceeding 22 kV?",
    "std": "NESC C2-2023 Table 232-1",
    "cat": "Line Design",
    "options": [
      "0.4 inches per kV in excess of 22 kV",
      "1.0 inch per kV in excess of 22 kV",
      "0.2 inches per kV in excess of 50 kV",
      "A flat 3.0 feet for all transmission class lines"
    ],
    "correctIndex": 0,
    "explanation": "NESC Rule 232 mandates a base clearance up to 22 kV plus exactly 0.4 inches (0.033 ft) for every kV in excess of 22 kV, plus an altitude correction for sites above 3,300 ft."
  },
  {
    "id": "q_02",
    "question": "Which federal mandate requires hourly ambient-adjusted thermal line ratings (AAR) based on forecasted temperature?",
    "std": "18 CFR \u00a7 35.28",
    "cat": "Line Design",
    "options": [
      "FERC Order 881",
      "NERC CIP-014",
      "FERC Order 1000",
      "OSHA 1910.269"
    ],
    "correctIndex": 0,
    "explanation": "FERC Order 881 mandates that transmission providers use hourly ambient-adjusted ratings (AARs) evaluating ambient air temperatures in increments of \u22645\u00b0F."
  },
  {
    "id": "q_03",
    "question": "What is the primary factor that causes aged overhead conductors to have a higher thermal ampacity rating than brand new conductors in IEEE 738?",
    "std": "IEEE Std 738 Clause 6",
    "cat": "Line Design",
    "options": [
      "Emissivity (\u03b5) increases from ~0.23 up to 0.70-0.90 through surface oxidation",
      "DC electrical resistance decreases as aluminum ages",
      "Conductor weight increases from soot deposit",
      "Conductor tensile strength increases over time"
    ],
    "correctIndex": 0,
    "explanation": "Weathering and oxidation darken the aluminum surface, raising thermal emissivity (\u03b5) from ~0.23 up to 0.70-0.90, which dramatically accelerates radiative heat cooling (q_r \u221d \u03b5)."
  },
  {
    "id": "q_04",
    "question": "According to NACE SP0169, what is the recognized polarized cathodic protection potential criterion for mitigating corrosion on buried steel?",
    "std": "NACE SP0169 / ISO 15589",
    "cat": "Corrosion",
    "options": [
      "-850 mV DC with respect to a saturated Cu/CuSO4 reference electrode",
      "-500 mV DC with respect to a silver chloride electrode",
      "+850 mV DC with respect to a calomel electrode",
      "0.00 mV neutral potential"
    ],
    "correctIndex": 0,
    "explanation": "A negative (cathodic) potential of at least -850 mV (-0.85 V) relative to a saturated copper-copper sulfate reference electrode satisfies full cathodic protection."
  },
  {
    "id": "q_05",
    "question": "Under IEEE Std 1243, why is maintaining tower footing resistance below 10-15 Ohms critical?",
    "std": "IEEE Std 1243 / IEEE Std 80",
    "cat": "Lightning and Grounding",
    "options": [
      "To prevent tower top potential from exceeding insulator CFO and causing backflashover",
      "To reduce load current flow through ground wires",
      "To prevent galvanic corrosion on steel lattice members",
      "To minimize corona audible noise at structure crossarms"
    ],
    "correctIndex": 0,
    "explanation": "When lightning strikes the shield wire or tower, high footing resistance produces a massive voltage drop (V = I \u00b7 R). If crossarm potential exceeds string CFO, lightning flashes back onto the phase conductor."
  },
  {
    "id": "q_06",
    "question": "What aerodynamic phenomenon causes high-frequency (3-120 Hz) micro-vibrations in overhead conductors that can lead to strand fatigue under suspension clamps?",
    "std": "IEEE Std 563",
    "cat": "Conductor and Hardware",
    "options": [
      "Aeolian vibration caused by Karman vortex shedding",
      "Subconductor wake flutter in bundled phases",
      "Full-span conductor galloping from asymmetric ice",
      "Magnetostriction of the steel core"
    ],
    "correctIndex": 0,
    "explanation": "Aeolian vibration is caused by alternating Karman vortices shedding in light, steady winds (1 to 7 m/s) at Strouhal frequency f = (0.185 \u00b7 V) / D, damped using Stockbridge dampers."
  },
  {
    "id": "q_07",
    "question": "What is the primary operational advantage of HTLS ACCC (Aluminum Conductor Composite Core) over traditional ACSR?",
    "std": "ASTM B987 / CIGRE TB 498",
    "cat": "Conductor and Hardware",
    "options": [
      "Operates at up to 180\u00b0C continuous with near-zero thermal elongation sag",
      "Weighs 50% more to resist blowout wind sway",
      "Eliminates the need for any vibration dampers",
      "Eliminates all corona discharge up to 765 kV"
    ],
    "correctIndex": 0,
    "explanation": "ACCC conductors utilize a carbon-glass composite core with an exceptionally low thermal expansion coefficient, enabling operation up to 180\u00b0C (doubling ampacity) without violating ground clearances."
  },
  {
    "id": "q_08",
    "question": "Under FAA Advisory Circular AC 70/7460-1, at what structure height above ground level (AGL) does aviation marking and lighting become mandatory?",
    "std": "14 CFR Part 77 / FAA AC 70/7460-1",
    "cat": "Right-of-Way and Environment",
    "options": [
      "200 feet (61 meters) AGL",
      "100 feet (30.5 meters) AGL",
      "350 feet (107 meters) AGL",
      "500 feet (152 meters) AGL"
    ],
    "correctIndex": 0,
    "explanation": "Any structure that exceeds 200 feet AGL (or breaches airport imaginary approach surfaces) requires FAA notification and compliant aviation obstruction marking and lighting."
  },
  {
    "id": "q_09",
    "question": "In OSHA 1910.269, what are the two distinct components that sum together to establish the Minimum Approach Distance (MAD)?",
    "std": "29 CFR \u00a7 1910.269",
    "cat": "Inspection and Assessment",
    "options": [
      "Minimum Electrical Component (air breakdown) + Inadvertent Movement Buffer (2 ft / 0.61 m)",
      "Physical arm reach + Step potential buffer",
      "Conductor sag depth + Blowout angle buffer",
      "Arc flash boundary + Flash protection boundary"
    ],
    "correctIndex": 0,
    "explanation": "MAD equals the electrical component (dielectric air breakdown distance based on maximum transient overvoltage) plus an inadvertent movement buffer of 2 feet (0.61 meters)."
  },
  {
    "id": "q_10",
    "question": "Under NACE SP0177, what is the maximum recommended steady-state AC touch voltage permitted on above-ground pipeline appurtenances collocated on a power line ROW?",
    "std": "NACE SP0177 / IEEE Std 80",
    "cat": "Right-of-Way and Environment",
    "options": [
      "15 Volts AC",
      "50 Volts AC",
      "120 Volts AC",
      "5 Volts AC"
    ],
    "correctIndex": 0,
    "explanation": "NACE SP0177 stipulates that steady-state AC touch voltages on metallic pipeline structures accessible to personnel must not exceed 15 V AC to prevent electrical shock."
  }
];

// App State
let currentCategory = "line_design";
let currentTool = "catenary";
let unitSystem = "US"; // US or SI

document.addEventListener("DOMContentLoaded", () => {
  setupCategoryNavigation();
  setupUnitToggle();
  setupGlobalSearch();
  renderCurrentView();
});

function setupUnitToggle() {
  const btn = document.getElementById("unitToggleBtn");
  btn.addEventListener("click", () => {
    unitSystem = unitSystem === "US" ? "SI" : "US";
    btn.textContent = `Units: ${unitSystem}`;
    renderCurrentView();
  });
}

function setupCategoryNavigation() {
  const tabs = document.querySelectorAll(".category-nav .nav-tab");
  tabs.forEach(tab => {
    tab.addEventListener("click", () => {
      tabs.forEach(t => t.classList.remove("active"));
      tab.classList.add("active");
      currentCategory = tab.dataset.category;
      
      // Default tool for selected category
      const defaults = {
        line_design: "catenary",
        corrosion: "zinc_life",
        inspection: "defect_matrix",
        lightning: "footing_resistance",
        conductor: "conductor_db",
        row_env: "emf_profile",
        glossary: "all",
        study: "all"
      };
      currentTool = defaults[currentCategory] || "catenary";
      renderCurrentView();
    });
  });
}

function setupGlobalSearch() {
  const searchInput = document.getElementById("globalSearchInput");
  searchInput.addEventListener("input", (e) => {
    const query = e.target.value.trim().toLowerCase();
    if (query.length > 0) {
      renderSearchResults(query);
    } else {
      renderCurrentView();
    }
  });
}

function renderCurrentView() {
  const contentArea = document.getElementById("contentArea");
  if (currentCategory === "glossary") {
    renderGlossaryView(contentArea);
    return;
  }
  if (currentCategory === "study") {
    renderStudyView(contentArea);
    return;
  }

  // Render Category Sub-tabs & Workspace
  const categoryConfig = {
    line_design: {
      title: "Line Design",
      desc: "Catenary sag, ruling span, blowout sway & ampacity",
      tools: [
        { id: "catenary", label: "Catenary Sag" },
        { id: "ruling_span", label: "Ruling Span" },
        { id: "blowout", label: "Blowout & Sway" },
        { id: "ampacity", label: "IEEE 738 Ampacity" }
      ]
    },
    corrosion: {
      title: "Corrosion Engineering",
      desc: "Atmospheric zinc loss, soil anchors & AC interference",
      tools: [
        { id: "zinc_life", label: "Zinc Galvanizing" },
        { id: "soil_anchor", label: "Soil Anchor Loss" },
        { id: "ac_corrosion", label: "AC Interference" },
        { id: "cp_anode", label: "Cathodic Protection" }
      ]
    },
    inspection: {
      title: "Inspection & Assessment",
      desc: "Defect priority matrix, pole sounding & member buckling",
      tools: [
        { id: "defect_matrix", label: "Defect Priority Matrix" },
        { id: "wood_pole", label: "Wood Pole Shell" },
        { id: "lattice_buckling", label: "Lattice Buckling" }
      ]
    },
    lightning: {
      title: "Lightning & Grounding",
      desc: "Footing impedance, shielding failure & backflashover",
      tools: [
        { id: "footing_resistance", label: "Footing Grounding" },
        { id: "shielding_angle", label: "Shielding Angle" },
        { id: "backflashover", label: "Backflashover Rate" },
        { id: "wenner_test", label: "Wenner 4-Pin Test" }
      ]
    },
    conductor: {
      title: "Conductor & Hardware",
      desc: "ACSR database, Aeolian dampers & insulator creepage",
      tools: [
        { id: "conductor_db", label: "Conductor Database" },
        { id: "aeolian_vibration", label: "Aeolian Damper" },
        { id: "insulator_string", label: "Insulator Creepage" }
      ]
    },
    row_env: {
      title: "Right-of-Way & Environment",
      desc: "Ground EMF profiler, ROW corridor width & corona noise",
      tools: [
        { id: "emf_profile", label: "EMF Profiler" },
        { id: "row_width", label: "ROW Corridor Width" },
        { id: "corona_noise", label: "Corona & Noise" }
      ]
    }
  }[currentCategory];

  let html = `
    <div class="category-hero">
      <div class="hero-info">
        <h2>${categoryConfig.title}</h2>
        <p>${categoryConfig.desc}</p>
      </div>
    </div>
    <div class="sub-nav">
      ${categoryConfig.tools.map(t => `
        <button class="sub-tab-btn ${currentTool === t.id ? 'active' : ''}" onclick="selectSubTool('${t.id}')">
          ${t.label}
        </button>
      `).join('')}
    </div>
    <div id="toolWorkspace"></div>
  `;

  contentArea.innerHTML = html;
  renderToolWorkspace(document.getElementById("toolWorkspace"));
}

window.selectSubTool = function(toolId) {
  currentTool = toolId;
  const subBtns = document.querySelectorAll(".sub-tab-btn");
  subBtns.forEach(btn => btn.classList.remove("active"));
  const activeBtn = Array.from(subBtns).find(b => b.getAttribute("onclick")?.includes(toolId));
  if (activeBtn) activeBtn.classList.add("active");
  renderToolWorkspace(document.getElementById("toolWorkspace"));
};

function renderToolWorkspace(container) {
  if (currentTool === "catenary") renderCatenaryTool(container);
  else if (currentTool === "ruling_span") renderRulingSpanTool(container);
  else if (currentTool === "blowout") renderBlowoutTool(container);
  else if (currentTool === "ampacity") renderAmpacityTool(container);
  else if (currentTool === "zinc_life") renderZincLifeTool(container);
  else if (currentTool === "soil_anchor") renderSoilAnchorTool(container);
  else if (currentTool === "ac_corrosion") renderAcCorrosionTool(container);
  else if (currentTool === "cp_anode") renderCpAnodeTool(container);
  else if (currentTool === "defect_matrix") renderDefectMatrixTool(container);
  else if (currentTool === "wood_pole") renderWoodPoleTool(container);
  else if (currentTool === "lattice_buckling") renderLatticeBucklingTool(container);
  else if (currentTool === "footing_resistance") renderFootingTool(container);
  else if (currentTool === "shielding_angle") renderShieldingTool(container);
  else if (currentTool === "backflashover") renderBackflashoverTool(container);
  else if (currentTool === "wenner_test") renderWennerTool(container);
  else if (currentTool === "conductor_db") renderConductorDbTool(container);
  else if (currentTool === "aeolian_vibration") renderAeolianTool(container);
  else if (currentTool === "insulator_string") renderInsulatorTool(container);
  else if (currentTool === "emf_profile") renderEmfTool(container);
  else if (currentTool === "row_width") renderRowWidthTool(container);
  else if (currentTool === "corona_noise") renderCoronaTool(container);
}

// ======================== TOOL IMPLEMENTATIONS ========================

// 1. Catenary Sag Tool
function renderCatenaryTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Catenary Inputs</h3>
        <p class="panel-desc">Level span catenary equations per IEEE Std 738 & NESC</p>
        <div class="canvas-wrapper">
          <canvas id="catenaryCanvas"></canvas>
        </div>
        <div class="form-group">
          <label>Span Length</label>
          <div class="input-with-unit">
            <input type="number" id="catSpan" value="800">
            <span class="input-unit">ft</span>
          </div>
          <div class="presets">
            <span class="preset-chip" onclick="setVal('catSpan', 400)">400 ft</span>
            <span class="preset-chip" onclick="setVal('catSpan', 800)">800 ft</span>
            <span class="preset-chip" onclick="setVal('catSpan', 1200)">1200 ft</span>
          </div>
        </div>
        <div class="form-group">
          <label>Conductor Weight (w)</label>
          <div class="input-with-unit">
            <input type="number" id="catWeight" value="1.094" step="0.001">
            <span class="input-unit">lb/ft</span>
          </div>
          <div class="presets">
            <span class="preset-chip" onclick="setVal('catWeight', 1.094)">Drake (1.094)</span>
            <span class="preset-chip" onclick="setVal('catWeight', 0.655)">Hawk (0.655)</span>
          </div>
        </div>
        <div class="form-group">
          <label>Horizontal Tension (H)</label>
          <div class="input-with-unit">
            <input type="number" id="catTension" value="6300">
            <span class="input-unit">lbf</span>
          </div>
        </div>
        <div class="form-group">
          <label>Attachment Height above Datum</label>
          <div class="input-with-unit">
            <input type="number" id="catHeight" value="65">
            <span class="input-unit">ft</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Analysis Results</h3>
        <p class="panel-desc">Calculated mid-span sag, clearance, and tension</p>
        <div class="metric-box">
          <div class="metric-header">
            <span>Mid-Span Sag (S)</span>
            <span class="metric-standard">S = C·[cosh(L/2C) - 1]</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="catResSag">--</span>
            <span class="metric-unit">ft</span>
          </div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Ground Clearance at Mid-Span</span>
            <span class="metric-standard">NESC Table 232-1</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="catResClearance">--</span>
            <span class="metric-unit">ft</span>
          </div>
          <div id="catClearanceBadge"></div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Max Tension at Support</span>
            <span class="metric-standard">T = H + w·S</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="catResMaxTension">--</span>
            <span class="metric-unit">lbf</span>
          </div>
          <div class="status-badge pass" id="catTensionPctBadge">Tension: 20% RBS</div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Catenary Constant (C = H / w)</span>
            <span class="metric-standard">Curvature</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="catResC">--</span>
            <span class="metric-unit">ft</span>
          </div>
        </div>
      </div>
    </div>
  `;

  const calculate = () => {
    const span = parseFloat(document.getElementById("catSpan").value) || 800;
    const w = parseFloat(document.getElementById("catWeight").value) || 1.094;
    const h = parseFloat(document.getElementById("catTension").value) || 6300;
    const height = parseFloat(document.getElementById("catHeight").value) || 65;

    const c = h / w;
    const sag = c * (Math.cosh(span / (2 * c)) - 1);
    const maxT = h + (w * sag);
    const clearance = Math.max(0, height - sag);
    const pctRbs = (maxT / 31500) * 100;

    document.getElementById("catResSag").textContent = sag.toFixed(2);
    document.getElementById("catResClearance").textContent = clearance.toFixed(2);
    document.getElementById("catResMaxTension").textContent = Math.round(maxT);
    document.getElementById("catResC").textContent = c.toFixed(1);

    const clBadge = document.getElementById("catClearanceBadge");
    if (clearance >= 25) {
      clBadge.innerHTML = `<span class="status-badge pass">Passes NESC Grade B standard clearance (>25 ft)</span>`;
    } else {
      clBadge.innerHTML = `<span class="status-badge alert">Warning: Clearance below 25 ft threshold</span>`;
    }

    drawCatenaryCanvas(span, sag, clearance);
  };

  ["catSpan", "catWeight", "catTension", "catHeight"].forEach(id => {
    document.getElementById(id).addEventListener("input", calculate);
  });
  calculate();
}

function drawCatenaryCanvas(span, sag, clearance) {
  const canvas = document.getElementById("catenaryCanvas");
  if (!canvas) return;
  const ctx = canvas.getContext("2d");
  const w = canvas.width = canvas.parentElement.clientWidth;
  const h = canvas.height = 180;

  ctx.clearRect(0, 0, w, h);
  const groundY = h - 25;
  const tL = 35;
  const tR = w - 35;
  const tHeight = h - 60;
  const attachY = groundY - tHeight;

  // Ground line
  ctx.strokeStyle = "#415a77";
  ctx.lineWidth = 2;
  ctx.beginPath();
  ctx.moveTo(0, groundY);
  ctx.lineTo(w, groundY);
  ctx.stroke();

  // Towers
  ctx.strokeStyle = "#94a3b8";
  ctx.lineWidth = 3;
  ctx.beginPath();
  ctx.moveTo(tL, groundY); ctx.lineTo(tL, attachY);
  ctx.moveTo(tR, groundY); ctx.lineTo(tR, attachY);
  ctx.stroke();

  // Catenary Curve
  const sagPx = Math.min(tHeight - 20, tHeight * 0.6);
  const midX = (tL + tR) / 2;
  const midY = attachY + sagPx;

  ctx.strokeStyle = "#009cde";
  ctx.lineWidth = 3;
  ctx.beginPath();
  for (let i = 0; i <= 30; i++) {
    const t = i / 30;
    const px = tL + (tR - tL) * t;
    const normX = (px - midX) / ((tR - tL) / 2);
    const py = midY - sagPx * (1 - normX * normX);
    if (i === 0) ctx.moveTo(px, py);
    else ctx.lineTo(px, py);
  }
  ctx.stroke();

  // Labels
  ctx.fillStyle = "#38bdf8";
  ctx.font = "11px monospace";
  ctx.fillText(`Sag: ${sag.toFixed(1)} ft`, midX - 35, midY - 6);
  ctx.fillStyle = "#10b981";
  ctx.fillText(`Clearance: ${clearance.toFixed(1)} ft`, midX - 45, groundY - 6);
}

// 2. Ruling Span Tool
function renderRulingSpanTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Ruling Span Calculator</h3>
        <p class="panel-desc">Equivalent single span for dead-end section tension equilibrium</p>
        <div class="form-group">
          <label>Spans in Dead-End Section (ft, comma-separated)</label>
          <input type="text" id="rulingSpansInput" value="650, 800, 920, 750, 840, 1100">
          <div class="presets">
            <span class="preset-chip" onclick="setVal('rulingSpansInput', '500, 600, 750, 700, 550')">Suburban</span>
            <span class="preset-chip" onclick="setVal('rulingSpansInput', '800, 950, 1100, 1300, 900')">Mountain Section</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Results</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Ruling Span (S_r)</span>
            <span class="metric-standard">S_r = √[Σ(S³) / Σ(S)]</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="rulingResSpan">--</span>
            <span class="metric-unit">ft</span>
          </div>
          <div class="status-badge pass" id="rulingSectionInfo">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const raw = document.getElementById("rulingSpansInput").value;
    const spans = raw.split(",").map(s => parseFloat(s.trim())).filter(n => !isNaN(n) && n > 0);
    if (!spans.length) return;
    const sumCubes = spans.reduce((acc, s) => acc + Math.pow(s, 3), 0);
    const sumSpans = spans.reduce((acc, s) => acc + s, 0);
    const sr = Math.sqrt(sumCubes / sumSpans);

    document.getElementById("rulingResSpan").textContent = sr.toFixed(1);
    document.getElementById("rulingSectionInfo").textContent = `${spans.length} spans • Total length: ${Math.round(sumSpans)} ft`;
  };

  document.getElementById("rulingSpansInput").addEventListener("input", calc);
  calc();
}

// 3. Blowout Tool
function renderBlowoutTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Wind Blowout & Sway Simulator</h3>
        <p class="panel-desc">Calculates transverse conductor swing angle and lateral displacement</p>
        <div class="form-group">
          <label>Conductor Diameter (d)</label>
          <div class="input-with-unit">
            <input type="number" id="blowDiam" value="1.108" step="0.01">
            <span class="input-unit">in</span>
          </div>
        </div>
        <div class="form-group">
          <label>Linear Weight (w)</label>
          <div class="input-with-unit">
            <input type="number" id="blowWt" value="1.094" step="0.01">
            <span class="input-unit">lb/ft</span>
          </div>
        </div>
        <div class="form-group">
          <label>Transverse Wind Speed</label>
          <div class="input-with-unit">
            <input type="number" id="blowWind" value="60">
            <span class="input-unit">mph</span>
          </div>
        </div>
        <div class="form-group">
          <label>Mid-Span Sag at Wind Temp</label>
          <div class="input-with-unit">
            <input type="number" id="blowSag" value="22">
            <span class="input-unit">ft</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Blowout Results</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Blowout Angle (θ)</span>
            <span class="metric-standard">θ = arctan(F_wind / W_vert)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="blowResAngle">--</span>
            <span class="metric-unit">deg</span>
          </div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Horizontal Displacement</span>
            <span class="metric-standard">Displacement = Sag · sin(θ)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="blowResDisp">--</span>
            <span class="metric-unit">ft</span>
          </div>
          <div class="status-badge pass" id="blowRowBadge">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const d = parseFloat(document.getElementById("blowDiam").value) || 1.108;
    const w = parseFloat(document.getElementById("blowWt").value) || 1.094;
    const v = parseFloat(document.getElementById("blowWind").value) || 60;
    const sag = parseFloat(document.getElementById("blowSag").value) || 22;

    const windPressPsf = 0.00256 * Math.pow(v, 2);
    const windForce = windPressPsf * (d / 12.0);
    const angleRad = Math.atan2(windForce, w);
    const angleDeg = angleRad * (180 / Math.PI);
    const disp = sag * Math.sin(angleRad);

    document.getElementById("blowResAngle").textContent = angleDeg.toFixed(1);
    document.getElementById("blowResDisp").textContent = disp.toFixed(2);
    document.getElementById("blowRowBadge").textContent = `Recommended corridor margin: at least ${(disp + 10).toFixed(1)} ft`;
  };

  ["blowDiam", "blowWt", "blowWind", "blowSag"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 4. IEEE 738 Ampacity Tool
function renderAmpacityTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>IEEE Std 738 Thermal Ampacity</h3>
        <p class="panel-desc">Steady-state heat balance for Drake 795 ACSR (d = 28.1 mm)</p>
        <div class="form-group">
          <label>Max Conductor Operating Temp</label>
          <div class="input-with-unit">
            <input type="number" id="ampTc" value="75">
            <span class="input-unit">°C</span>
          </div>
        </div>
        <div class="form-group">
          <label>Ambient Temperature</label>
          <div class="input-with-unit">
            <input type="number" id="ampTa" value="35">
            <span class="input-unit">°C</span>
          </div>
        </div>
        <div class="form-group">
          <label>Perpendicular Wind Speed</label>
          <div class="input-with-unit">
            <input type="number" id="ampWind" value="0.61" step="0.1">
            <span class="input-unit">m/s</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Ampacity Ratings</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Allowable Current</span>
            <span class="metric-standard">IEEE 738 Heat Balance</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="ampResAmps">--</span>
            <span class="metric-unit">Amperes</span>
          </div>
          <div class="status-badge pass" id="ampResMva">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const tc = parseFloat(document.getElementById("ampTc").value) || 75;
    const ta = parseFloat(document.getElementById("ampTa").value) || 35;
    const v = parseFloat(document.getElementById("ampWind").value) || 0.61;
    const dMeters = 0.0281;
    const deltaT = Math.max(1, tc - ta);
    const rPerMeter = 0.072 / 1000.0;

    const reynolds = (dMeters * v * 1.2) / 1.81e-5;
    const qc = (1.01 + 0.371 * Math.pow(reynolds, 0.52)) * 0.0242 * deltaT;
    const qr = Math.PI * dMeters * 0.8 * 5.67e-8 * (Math.pow(tc + 273.15, 4) - Math.pow(ta + 273.15, 4));
    const qs = 0.8 * 1000 * dMeters;

    const net = Math.max(0, qc + qr - qs);
    const amps = Math.sqrt(net / rPerMeter);
    const mva230 = (Math.sqrt(3) * 230 * amps) / 1000;

    document.getElementById("ampResAmps").textContent = Math.round(amps);
    document.getElementById("ampResMva").textContent = `3-Phase 230kV Capacity: ${mva230.toFixed(1)} MVA`;
  };

  ["ampTc", "ampTa", "ampWind"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 5. Corrosion: Zinc Life
function renderZincLifeTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Galvanizing Life Estimator</h3>
        <p class="panel-desc">Hot-dip zinc coating depletion per ISO 9223 / ASTM A123</p>
        <div class="form-group">
          <label>Zinc Coating Thickness</label>
          <div class="input-with-unit">
            <input type="number" id="zincThickness" value="85">
            <span class="input-unit">µm</span>
          </div>
        </div>
        <div class="form-group">
          <label>ISO 9223 Corrosivity Category</label>
          <select id="zincCategory">
            <option value="0.1">C1 - Very Low (0.1 µm/yr)</option>
            <option value="0.5">C2 - Low (0.5 µm/yr)</option>
            <option value="1.5" selected>C3 - Medium (1.5 µm/yr)</option>
            <option value="3.0">C4 - High (3.0 µm/yr)</option>
            <option value="6.0">C5 - Very High (6.0 µm/yr)</option>
            <option value="15.0">CX - Extreme Offshore (15.0 µm/yr)</option>
          </select>
        </div>
      </div>
      <div class="panel">
        <h3>Lifespan Projection</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Years to Steel Exposure</span>
            <span class="metric-standard">ISO 9223 Model</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="zincResYears">--</span>
            <span class="metric-unit">Years</span>
          </div>
          <div class="status-badge pass" id="zincResRec">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const thick = parseFloat(document.getElementById("zincThickness").value) || 85;
    const rate = parseFloat(document.getElementById("zincCategory").value) || 1.5;
    const years = thick / rate;

    document.getElementById("zincResYears").textContent = years.toFixed(1);
    const badge = document.getElementById("zincResRec");
    if (years > 30) {
      badge.className = "status-badge pass";
      badge.textContent = "Long-term coating integrity verified. Standard 10-year patrol.";
    } else if (years > 15) {
      badge.className = "status-badge warn";
      badge.textContent = "Moderate lifespan. Schedule dry film thickness (DFT) check at year 15.";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "Rapid depletion. Barrier paint overcoat or metallizing required.";
    }
  };

  document.getElementById("zincThickness").addEventListener("input", calc);
  document.getElementById("zincCategory").addEventListener("change", calc);
  calc();
}

// 6. Soil Anchor Loss
function renderSoilAnchorTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Soil Anchor Loss (Romanoff)</h3>
        <p class="panel-desc">Underground pitting model for buried guy anchor rods & grillages</p>
        <div class="form-group">
          <label>Anchor Rod Initial Diameter</label>
          <div class="input-with-unit">
            <input type="number" id="anchorDiam" value="1.0" step="0.1">
            <span class="input-unit">in</span>
          </div>
        </div>
        <div class="form-group">
          <label>Soil Resistivity</label>
          <div class="input-with-unit">
            <input type="number" id="anchorRes" value="35">
            <span class="input-unit">Ω·m</span>
          </div>
        </div>
        <div class="form-group">
          <label>Years in Service</label>
          <div class="input-with-unit">
            <input type="number" id="anchorYears" value="40">
            <span class="input-unit">years</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Degradation Analysis</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Remaining Diameter</span>
            <span class="metric-standard">P = k·t^0.55</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="anchorResRemDiam">--</span>
            <span class="metric-unit">in</span>
          </div>
          <div class="status-badge pass" id="anchorResAreaPct">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const diam = parseFloat(document.getElementById("anchorDiam").value) || 1.0;
    const res = parseFloat(document.getElementById("anchorRes").value) || 35;
    const yrs = parseFloat(document.getElementById("anchorYears").value) || 40;

    const baseK = res < 15 ? 8.0 : res < 30 ? 5.5 : res < 60 ? 3.5 : 1.5;
    const penMils = baseK * Math.pow(Math.max(0, yrs - 15), 0.55) * 12.0;
    const penIn = penMils / 1000.0;
    const rRem = Math.max(0.05, (diam / 2.0) - penIn);
    const remDiam = rRem * 2.0;
    const areaPct = Math.pow(remDiam / diam, 2) * 100;

    document.getElementById("anchorResRemDiam").textContent = remDiam.toFixed(2);
    const badge = document.getElementById("anchorResAreaPct");
    badge.textContent = `Remaining Steel Area: ${areaPct.toFixed(1)}%`;
    badge.className = areaPct > 65 ? "status-badge pass" : "status-badge alert";
  };

  ["anchorDiam", "anchorRes", "anchorYears"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 7. AC Corrosion Tool
function renderAcCorrosionTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>AC Interference & Corrosion Risk</h3>
        <p class="panel-desc">AC discharge current density per ISO 18086 / NACE SP0106</p>
        <div class="form-group">
          <label>Induced AC Voltage</label>
          <div class="input-with-unit">
            <input type="number" id="acVolt" value="18">
            <span class="input-unit">V_ac</span>
          </div>
        </div>
        <div class="form-group">
          <label>Soil Resistivity</label>
          <div class="input-with-unit">
            <input type="number" id="acSoil" value="40">
            <span class="input-unit">Ω·m</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Risk Classification</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>AC Current Density (J_ac)</span>
            <span class="metric-standard">J_ac = (8·V)/(π·ρ·d)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="acResJac">--</span>
            <span class="metric-unit">A/m²</span>
          </div>
          <div class="status-badge warn" id="acResBadge">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const v = parseFloat(document.getElementById("acVolt").value) || 18;
    const rho = parseFloat(document.getElementById("acSoil").value) || 40;
    const d = 0.01128; // 1 cm2 holiday
    const jAc = (8 * v) / (Math.PI * rho * d);

    document.getElementById("acResJac").textContent = jAc.toFixed(1);
    const badge = document.getElementById("acResBadge");
    if (jAc < 30) {
      badge.className = "status-badge pass";
      badge.textContent = "Low Risk (<30 A/m²): Standard cathodic protection adequate.";
    } else if (jAc <= 100) {
      badge.className = "status-badge warn";
      badge.textContent = "Medium Risk (30-100 A/m²): AC mitigation recommended.";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "Severe Risk (>100 A/m²): Solid-state decoupler & zinc ribbon required.";
    }
  };

  ["acVolt", "acSoil"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 8. CP Anode Tool
function renderCpAnodeTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Cathodic Protection Anode Sizing</h3>
        <p class="panel-desc">Sacrificial magnesium or zinc anode sizing for tower foundations</p>
        <div class="form-group">
          <label>Buried Steel Surface Area</label>
          <div class="input-with-unit">
            <input type="number" id="cpArea" value="150">
            <span class="input-unit">sq ft</span>
          </div>
        </div>
        <div class="form-group">
          <label>Current Density Requirement</label>
          <div class="input-with-unit">
            <input type="number" id="cpDensity" value="2.0" step="0.5">
            <span class="input-unit">mA/sq ft</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Required Anodes</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Recommended Magnesium Anodes (30yr)</span>
            <span class="metric-standard">NACE SP0169</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="cpResCount">--</span>
            <span class="metric-unit">Anodes</span>
          </div>
          <div class="status-badge pass" id="cpResMass">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const area = parseFloat(document.getElementById("cpArea").value) || 150;
    const cd = parseFloat(document.getElementById("cpDensity").value) || 2.0;
    const totalCurrent = (area * cd) / 1000.0;
    const ampHours = totalCurrent * 8760 * 30; // 30 years
    const totalKg = ampHours / 550; // Magnesium ~550 A-h/kg net
    const count = Math.ceil(totalKg / 7.7);

    document.getElementById("cpResCount").textContent = count;
    document.getElementById("cpResMass").textContent = `Total Mass: ${totalKg.toFixed(1)} kg (${(totalKg * 2.2).toFixed(0)} lbs) H-1 Magnesium`;
  };

  ["cpArea", "cpDensity"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 9. Defect Matrix Tool
function renderDefectMatrixTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Transmission Defect Scoring (DSI)</h3>
        <p class="panel-desc">Multi-defect evaluation with priority action categorization</p>
        <div class="form-group">
          <label>Foundation Defect Level (0=Sound, 3=Severe Uplift/Crack)</label>
          <select id="defFound">
            <option value="0">Level 0: Sound / Normal</option>
            <option value="1">Level 1: Minor hairline cracking</option>
            <option value="2">Level 2: Concrete spalling / exposed rebar</option>
            <option value="3">Level 3: Severe displacement / anchor necking</option>
          </select>
        </div>
        <div class="form-group">
          <label>Structural Member Bent or Buckled?</label>
          <select id="defBent">
            <option value="0">No: All members straight</option>
            <option value="1">Yes: Angle leg or diagonal bent</option>
          </select>
        </div>
        <div class="form-group">
          <label>Steel Rust Grade (0=Zinc, 4=Flaking Rust)</label>
          <select id="defRust">
            <option value="0">Grade 0: Bright Zinc</option>
            <option value="1">Grade 1: Dull gray patina</option>
            <option value="2">Grade 2: Spot pinpoint rust < 5%</option>
            <option value="3">Grade 3: Generalized rust</option>
            <option value="4">Grade 4: Flaking laminar rust</option>
          </select>
        </div>
      </div>
      <div class="panel">
        <h3>Urgency & Action Tier</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Action Priority Tier</span>
            <span class="metric-standard">EPRI Guideline</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="defResTier">--</span>
          </div>
          <div class="status-badge alert" id="defResBadge">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const f = parseInt(document.getElementById("defFound").value);
    const b = parseInt(document.getElementById("defBent").value);
    const r = parseInt(document.getElementById("defRust").value);
    let score = f * 18 + b * 25 + r * 12;

    const tierElem = document.getElementById("defResTier");
    const badgeElem = document.getElementById("defResBadge");

    if (score >= 45) {
      tierElem.textContent = "P1: Emergency";
      badgeElem.className = "status-badge alert";
      badgeElem.textContent = "Immediate remediation required (<24-48 hrs). NESC Rule 260 violation.";
    } else if (score >= 25) {
      tierElem.textContent = "P2: High Priority";
      badgeElem.className = "status-badge warn";
      badgeElem.textContent = "Schedule repair in <30 days maintenance cycle.";
    } else {
      tierElem.textContent = "P4: Low / Monitored";
      badgeElem.className = "status-badge pass";
      badgeElem.textContent = "Acceptable. Continue routine patrol schedule.";
    }
  };

  ["defFound", "defBent", "defRust"].forEach(id => {
    document.getElementById(id).addEventListener("change", calc);
  });
  calc();
}

// 10. Wood Pole Tool
function renderWoodPoleTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Wood Pole Shell Soundness</h3>
        <p class="panel-desc">ASCE Manual 91 / ANSI O5.1 hollow cylinder capacity</p>
        <div class="form-group">
          <label>Original Circumference</label>
          <div class="input-with-unit">
            <input type="number" id="poleCirc" value="42">
            <span class="input-unit">in</span>
          </div>
        </div>
        <div class="form-group">
          <label>Measured Sound Wood Shell Thickness</label>
          <div class="input-with-unit">
            <input type="number" id="poleShell" value="3.5" step="0.5">
            <span class="input-unit">in</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Remaining Bending Capacity</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Residual Capacity</span>
            <span class="metric-standard">NESC Rule 261 (67% Limit)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="poleResPct">--</span>
            <span class="metric-unit">%</span>
          </div>
          <div class="status-badge pass" id="poleResBadge">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const circ = parseFloat(document.getElementById("poleCirc").value) || 42;
    const shell = parseFloat(document.getElementById("poleShell").value) || 3.5;
    const outerD = circ / Math.PI;
    const innerD = Math.max(0, outerD - 2 * shell);

    const zOrig = (Math.PI * Math.pow(outerD, 3)) / 32.0;
    const zRem = (Math.PI * (Math.pow(outerD, 4) - Math.pow(innerD, 4))) / (32.0 * outerD);
    const pct = (zRem / zOrig) * 100;

    document.getElementById("poleResPct").textContent = pct.toFixed(1);
    const badge = document.getElementById("poleResBadge");
    if (pct >= 75) {
      badge.className = "status-badge pass";
      badge.textContent = "PASS: Exceeds standard structural requirements.";
    } else if (pct >= 60) {
      badge.className = "status-badge warn";
      badge.textContent = "REINFORCE: Candidate for C-Truss splinting.";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "REJECT / RED TAG: Immediate replacement required.";
    }
  };

  ["poleCirc", "poleShell"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 11. Lattice Buckling Tool
function renderLatticeBucklingTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Lattice Member Buckling (ASCE 10)</h3>
        <p class="panel-desc">Column compression slenderness ratio KL/r and capacity</p>
        <div class="form-group">
          <label>Unbraced Length (L)</label>
          <div class="input-with-unit">
            <input type="number" id="latL" value="60">
            <span class="input-unit">in</span>
          </div>
        </div>
        <div class="form-group">
          <label>Radius of Gyration (r_z min)</label>
          <div class="input-with-unit">
            <input type="number" id="latR" value="0.59" step="0.01">
            <span class="input-unit">in</span>
          </div>
        </div>
        <div class="form-group">
          <label>Cross-Section Area</label>
          <div class="input-with-unit">
            <input type="number" id="latArea" value="1.44" step="0.01">
            <span class="input-unit">sq in</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>ASCE 10 Capacity</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Slenderness Ratio (KL/r)</span>
            <span class="metric-standard">ASCE 10 (Limit <= 200)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="latResKlR">--</span>
          </div>
          <div class="status-badge pass" id="latKlRBadge">--</div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Allowable Axial Load</span>
            <span class="metric-standard">P = F_cr · Area</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="latResLoad">--</span>
            <span class="metric-unit">kips</span>
          </div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const l = parseFloat(document.getElementById("latL").value) || 60;
    const r = parseFloat(document.getElementById("latR").value) || 0.59;
    const area = parseFloat(document.getElementById("latArea").value) || 1.44;

    const klr = l / r;
    const cc = Math.sqrt((2 * Math.PI * Math.PI * 29000) / 36);
    let fcr = 0;
    if (klr <= cc) {
      fcr = (1 - (Math.pow(klr, 2) / (2 * Math.pow(cc, 2)))) * 36;
    } else {
      fcr = (Math.PI * Math.PI * 29000) / Math.pow(klr, 2);
    }
    const load = fcr * area;

    document.getElementById("latResKlR").textContent = klr.toFixed(1);
    document.getElementById("latResLoad").textContent = load.toFixed(1);
    const badge = document.getElementById("latKlRBadge");
    if (klr <= 200) {
      badge.className = "status-badge pass";
      badge.textContent = "Passes ASCE 10 slenderness limit (<= 200)";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "FAIL: Exceeds ASCE 10 maximum compression limit (> 200)";
    }
  };

  ["latL", "latR", "latArea"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 12. Footing Grounding Tool
function renderFootingTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Tower Footing Grounding</h3>
        <p class="panel-desc">IEEE Std 80 / 142 impedance with soil ionization</p>
        <div class="form-group">
          <label>Soil Resistivity</label>
          <div class="input-with-unit">
            <input type="number" id="ftSoil" value="250">
            <span class="input-unit">Ω·m</span>
          </div>
        </div>
        <div class="form-group">
          <label>Number of Driven Ground Rods</label>
          <input type="number" id="ftRods" value="4">
        </div>
        <div class="form-group">
          <label>Counterpoise Trench Wire</label>
          <div class="input-with-unit">
            <input type="number" id="ftCp" value="30">
            <span class="input-unit">m</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Impedance Results</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>60Hz Power Frequency Resistance</span>
            <span class="metric-standard">Target < 10-15 Ω</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="ftResLowFreq">--</span>
            <span class="metric-unit">Ω</span>
          </div>
          <div class="status-badge pass" id="ftBadge">--</div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Surge Impulse Resistance (R_i)</span>
            <span class="metric-standard">40 kA Stroke Ionization</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="ftResImpulse">--</span>
            <span class="metric-unit">Ω</span>
          </div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const rho = parseFloat(document.getElementById("ftSoil").value) || 250;
    const rods = parseInt(document.getElementById("ftRods").value) || 4;
    const cp = parseFloat(document.getElementById("ftCp").value) || 30;

    const singleRodR = (rho / (2 * Math.PI * 3.05)) * (Math.log(4 * 3.05 / 0.008) - 1);
    const rodsR = singleRodR / (rods * 0.8);
    const cpR = (rho / (Math.PI * cp)) * (Math.log(2 * cp / Math.sqrt(2 * 0.8 * 0.005)) - 1);
    const rLow = Math.max(0.5, 1 / ((1 / rodsR) + (1 / cpR)));

    const ig = (rho * 400) / (2 * Math.PI * Math.pow(rLow, 2));
    const rImpulse = rLow / Math.sqrt(1 + (40 / Math.max(1, ig)));

    document.getElementById("ftResLowFreq").textContent = rLow.toFixed(1);
    document.getElementById("ftResImpulse").textContent = rImpulse.toFixed(1);

    const badge = document.getElementById("ftBadge");
    if (rLow <= 10) {
      badge.className = "status-badge pass";
      badge.textContent = "Optimal (< 10 Ω). Excellent lightning withstand.";
    } else if (rLow <= 20) {
      badge.className = "status-badge warn";
      badge.textContent = "Marginal (10-20 Ω). Consider extra counterpoise.";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "Elevated (> 20 Ω). High backflashover risk.";
    }
  };

  ["ftSoil", "ftRods", "ftCp"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 13. Shielding Angle Tool
function renderShieldingTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Shielding Angle Analysis</h3>
        <p class="panel-desc">Electrogeometric Model (EGM) protection cone for OHGW/OPGW</p>
        <div class="form-group">
          <label>Shield Wire Height (H_s)</label>
          <div class="input-with-unit">
            <input type="number" id="shH" value="35">
            <span class="input-unit">m</span>
          </div>
        </div>
        <div class="form-group">
          <label>Phase Conductor Height (H_p)</label>
          <div class="input-with-unit">
            <input type="number" id="shP" value="26">
            <span class="input-unit">m</span>
          </div>
        </div>
        <div class="form-group">
          <label>Horizontal Separation (X_s)</label>
          <div class="input-with-unit">
            <input type="number" id="shSep" value="4.5">
            <span class="input-unit">m</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Shielding Evaluation</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Shield Angle (θ)</span>
            <span class="metric-standard">Recommended <= 30°</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="shResAngle">--</span>
            <span class="metric-unit">degrees</span>
          </div>
          <div class="status-badge pass" id="shResBadge">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const hs = parseFloat(document.getElementById("shH").value) || 35;
    const hp = parseFloat(document.getElementById("shP").value) || 26;
    const xs = parseFloat(document.getElementById("shSep").value) || 4.5;
    const deltaH = Math.max(0.5, hs - hp);
    const thetaDeg = Math.atan2(xs, deltaH) * (180 / Math.PI);

    document.getElementById("shResAngle").textContent = thetaDeg.toFixed(1);
    const badge = document.getElementById("shResBadge");
    if (thetaDeg <= 30) {
      badge.className = "status-badge pass";
      badge.textContent = "Effective: Shield angle is within recommended <= 30° envelope.";
    } else {
      badge.className = "status-badge alert";
      badge.textContent = "Inadequate: Shield angle exceeds 30°. Vulnerable to direct flashover.";
    }
  };

  ["shH", "shP", "shSep"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 14. Backflashover Tool
function renderBackflashoverTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Critical Backflashover Current</h3>
        <p class="panel-desc">Insulator crossarm CFO and outage rate per IEEE Std 1243</p>
        <div class="form-group">
          <label>Insulator String CFO</label>
          <div class="input-with-unit">
            <input type="number" id="bfoCfo" value="1200">
            <span class="input-unit">kV</span>
          </div>
        </div>
        <div class="form-group">
          <label>Tower Footing Resistance</label>
          <div class="input-with-unit">
            <input type="number" id="bfoR" value="15">
            <span class="input-unit">Ω</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Outage Rate Projection</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Critical Current (I_c)</span>
            <span class="metric-standard">I_c = CFO / ((1-K)·R + Zt/6)</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="bfoResIc">--</span>
            <span class="metric-unit">kA</span>
          </div>
          <div class="status-badge pass" id="bfoResBfor">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const cfo = parseFloat(document.getElementById("bfoCfo").value) || 1200;
    const r = parseFloat(document.getElementById("bfoR").value) || 15;
    const ic = cfo / ((1 - 0.32) * r + 25);
    const prob = 1.0 / (1.0 + Math.pow(ic / 31.0, 2.6));
    const bfor = 4.0 * 100 * 0.08 * prob;

    document.getElementById("bfoResIc").textContent = ic.toFixed(1);
    const badge = document.getElementById("bfoResBfor");
    badge.textContent = `Predicted Outage Rate: ${bfor.toFixed(2)} trips / 100 km-yr`;
    badge.className = bfor < 1.0 ? "status-badge pass" : "status-badge warn";
  };

  ["bfoCfo", "bfoR"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 15. Wenner Test Tool
function renderWennerTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Wenner 4-Point Soil Resistivity</h3>
        <p class="panel-desc">IEEE Std 81 apparent soil resistivity measurement</p>
        <div class="form-group">
          <label>Pin Spacing (a)</label>
          <div class="input-with-unit">
            <input type="number" id="wennerA" value="5">
            <span class="input-unit">m</span>
          </div>
        </div>
        <div class="form-group">
          <label>Measured Resistance (R)</label>
          <div class="input-with-unit">
            <input type="number" id="wennerR" value="4.2" step="0.1">
            <span class="input-unit">Ω</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Apparent Resistivity</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Apparent Resistivity (ρ)</span>
            <span class="metric-standard">ρ = 2·π·a·R</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="wennerResRho">--</span>
            <span class="metric-unit">Ω·m</span>
          </div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const a = parseFloat(document.getElementById("wennerA").value) || 5;
    const r = parseFloat(document.getElementById("wennerR").value) || 4.2;
    const rho = 2 * Math.PI * a * r;
    document.getElementById("wennerResRho").textContent = rho.toFixed(1);
  };

  ["wennerA", "wennerR"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 16. Conductor Database Tool
function renderConductorDbTool(container) {
  container.innerHTML = `
    <div class="panel" style="margin-bottom: 20px;">
      <h3>Standard Conductor Database</h3>
      <p class="panel-desc">ASTM B232 / B856 specifications for overhead conductors</p>
      <div class="form-group">
        <label>Select Conductor</label>
        <select id="condSelector">
          ${CONDUCTORS.map((c, i) => `<option value="${i}" ${c.codeWord === "Drake" ? "selected" : ""}>${c.codeWord} (${c.type} ${c.kcmil} kcmil)</option>`).join('')}
        </select>
      </div>
    </div>
    <div id="conductorDetailCard"></div>
  `;

  const select = document.getElementById("condSelector");
  const renderDetail = () => {
    const c = CONDUCTORS[parseInt(select.value)];
    document.getElementById("conductorDetailCard").innerHTML = `
      <div class="panel">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 14px;">
          <div>
            <h2 style="color: var(--primary-amber); font-size: 1.5rem;">${c.codeWord} (${c.type})</h2>
            <p style="color: var(--text-muted); font-size: 0.9rem;">${c.kcmil} kcmil • Stranding ${c.stranding}</p>
          </div>
          <div class="status-badge pass" style="font-size: 1rem;">${c.amp} Amperes</div>
        </div>
        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 14px;">
          <div class="metric-box">
            <div class="metric-header"><span>Overall Diameter</span></div>
            <div class="metric-value-row">
              <span class="metric-value" style="font-size: 1.3rem;">${c.diamIn}"</span>
              <span class="metric-unit">(${(c.diamIn * 25.4).toFixed(1)} mm)</span>
            </div>
          </div>
          <div class="metric-box">
            <div class="metric-header"><span>Linear Weight</span></div>
            <div class="metric-value-row">
              <span class="metric-value" style="font-size: 1.3rem;">${c.wtLbFt}</span>
              <span class="metric-unit">lb/ft (${(c.wtLbFt * 1.488).toFixed(2)} kg/m)</span>
            </div>
          </div>
          <div class="metric-box">
            <div class="metric-header"><span>Rated Breaking Strength</span></div>
            <div class="metric-value-row">
              <span class="metric-value" style="font-size: 1.3rem;">${c.rbsLbf}</span>
              <span class="metric-unit">lbf (${(c.rbsLbf * 0.004448).toFixed(1)} kN)</span>
            </div>
          </div>
          <div class="metric-box">
            <div class="metric-header"><span>AC Resistance @ 75°C</span></div>
            <div class="metric-value-row">
              <span class="metric-value" style="font-size: 1.3rem;">${c.r75}</span>
              <span class="metric-unit">Ω / mile</span>
            </div>
          </div>
        </div>
      </div>
    `;
  };

  select.addEventListener("change", renderDetail);
  renderDetail();
}

// 17. Aeolian Vibration Tool
function renderAeolianTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Aeolian Vibration Damper Positioning</h3>
        <p class="panel-desc">Stockbridge damper distance x = 0.8·(λ/2) from suspension clamp</p>
        <div class="form-group">
          <label>Conductor Diameter</label>
          <div class="input-with-unit">
            <input type="number" id="aeoDiam" value="28.14" step="0.1">
            <span class="input-unit">mm</span>
          </div>
        </div>
        <div class="form-group">
          <label>Everyday Tension (T_0)</label>
          <div class="input-with-unit">
            <input type="number" id="aeoTension" value="28.0" step="0.5">
            <span class="input-unit">kN</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Damper Position</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Damper Distance from Clamp</span>
            <span class="metric-standard">IEEE Std 664 / CIGRE</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="aeoResDist">--</span>
            <span class="metric-unit">inches</span>
          </div>
          <div class="status-badge pass" id="aeoResFreq">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const dMm = parseFloat(document.getElementById("aeoDiam").value) || 28.14;
    const tKn = parseFloat(document.getElementById("aeoTension").value) || 28.0;

    const dM = dMm / 1000.0;
    const freq = (0.185 * 3.5) / dM;
    const waveVel = Math.sqrt((tKn * 1000) / 1.628);
    const halfLoop = (waveVel / freq) / 2.0;
    const damperM = 0.8 * halfLoop;
    const damperIn = damperM * 39.37;

    document.getElementById("aeoResDist").textContent = damperIn.toFixed(1);
    document.getElementById("aeoResFreq").textContent = `Strouhal Frequency: ${freq.toFixed(1)} Hz (${damperM.toFixed(2)} m)`;
  };

  ["aeoDiam", "aeoTension"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 18. Insulator Creepage Tool
function renderInsulatorTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Insulator Creepage & Rating</h3>
        <p class="panel-desc">IEC 60815 pollution severity creepage and ANSI C29.2 safety factor</p>
        <div class="form-group">
          <label>System Operating Voltage</label>
          <div class="input-with-unit">
            <input type="number" id="insVolt" value="230">
            <span class="input-unit">kV</span>
          </div>
        </div>
        <div class="form-group">
          <label>Pollution Severity (IEC 60815)</label>
          <select id="insPoll">
            <option value="16">Light (16 mm/kV)</option>
            <option value="20">Medium (20 mm/kV)</option>
            <option value="25" selected>Heavy (25 mm/kV)</option>
            <option value="31">Very Heavy (31 mm/kV)</option>
          </select>
        </div>
      </div>
      <div class="panel">
        <h3>String Sizing</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Required Standard 10" Discs</span>
            <span class="metric-standard">IEC 60815</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="insResDiscs">--</span>
            <span class="metric-unit">Bells</span>
          </div>
          <div class="status-badge pass" id="insResCreepage">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const v = parseFloat(document.getElementById("insVolt").value) || 230;
    const poll = parseFloat(document.getElementById("insPoll").value) || 25;
    const requiredTotalMm = poll * (v * 1.05);
    const discs = Math.ceil(requiredTotalMm / 292.0);

    document.getElementById("insResDiscs").textContent = discs;
    document.getElementById("insResCreepage").textContent = `Total Creepage: ${Math.round(requiredTotalMm)} mm (${((discs * 146) / 1000).toFixed(2)} m string length)`;
  };

  ["insVolt", "insPoll"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 19. EMF Profile Tool
function renderEmfTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>EMF Ground-Level Profiler</h3>
        <p class="panel-desc">IEEE Std 644 lateral field calculation against ICNIRP public limits</p>
        <div class="form-group">
          <label>Line Operating Voltage</label>
          <div class="input-with-unit">
            <input type="number" id="emfVolt" value="230">
            <span class="input-unit">kV</span>
          </div>
        </div>
        <div class="form-group">
          <label>Balanced Line Current</label>
          <div class="input-with-unit">
            <input type="number" id="emfCurr" value="800">
            <span class="input-unit">Amperes</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Field Levels</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Peak Electric Field (E)</span>
            <span class="metric-standard">ICNIRP Limit 5.0 kV/m</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="emfResE">--</span>
            <span class="metric-unit">kV / m</span>
          </div>
          <div class="status-badge pass" id="emfBadgeE">Passes ICNIRP public threshold</div>
        </div>
        <div class="metric-box">
          <div class="metric-header">
            <span>Peak Magnetic Field (B)</span>
            <span class="metric-standard">ICNIRP Limit 2000 mG</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="emfResB">--</span>
            <span class="metric-unit">mG</span>
          </div>
          <div class="status-badge pass" id="emfBadgeB">Well within international limits</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const v = parseFloat(document.getElementById("emfVolt").value) || 230;
    const i = parseFloat(document.getElementById("emfCurr").value) || 800;

    const ePeak = (v / 230.0) * 1.85;
    const bPeak = (i / 800.0) * 42.5;

    document.getElementById("emfResE").textContent = ePeak.toFixed(2);
    document.getElementById("emfResB").textContent = bPeak.toFixed(1);
  };

  ["emfVolt", "emfCurr"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 20. ROW Width Tool
function renderRowWidthTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Right-of-Way Minimum Width</h3>
        <p class="panel-desc">NESC Rule 234 Table 234-1 corridor sizing with conductor blowout</p>
        <div class="form-group">
          <label>Structure Footprint Width</label>
          <div class="input-with-unit">
            <input type="number" id="rowBase" value="25">
            <span class="input-unit">ft</span>
          </div>
        </div>
        <div class="form-group">
          <label>Mid-Span Conductor Sag</label>
          <div class="input-with-unit">
            <input type="number" id="rowSag" value="22">
            <span class="input-unit">ft</span>
          </div>
        </div>
      </div>
      <div class="panel">
        <h3>Required ROW Corridor</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Total Recommended Corridor Width</span>
            <span class="metric-standard">NESC Rule 234</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="rowResTotal">--</span>
            <span class="metric-unit">ft</span>
          </div>
          <div class="status-badge pass" id="rowResBreakdown">--</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const base = parseFloat(document.getElementById("rowBase").value) || 25;
    const sag = parseFloat(document.getElementById("rowSag").value) || 22;
    const blowout = sag * Math.sin(25 * (Math.PI / 180));
    const elecClearance = 7.5 + ((230 - 22) * 0.4 / 12.0);
    const dangerTree = 10.0;
    const total = base + 2 * (blowout + elecClearance + dangerTree);

    document.getElementById("rowResTotal").textContent = Math.ceil(total);
    document.getElementById("rowResBreakdown").textContent = `Base ${base}ft + 2×(Blowout ${blowout.toFixed(1)}ft + Clear ${elecClearance.toFixed(1)}ft + Buffer 10ft)`;
  };

  ["rowBase", "rowSag"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// 21. Corona Noise Tool
function renderCoronaTool(container) {
  container.innerHTML = `
    <div class="tool-workspace">
      <div class="panel">
        <h3>Corona Loss & Rain Audible Noise</h3>
        <p class="panel-desc">EPRI Transmission Line Reference Book formulation</p>
        <div class="form-group">
          <label>System Operating Voltage</label>
          <div class="input-with-unit">
            <input type="number" id="corVolt" value="500">
            <span class="input-unit">kV</span>
          </div>
        </div>
        <div class="form-group">
          <label>Subconductors in Phase Bundle</label>
          <input type="number" id="corBundle" value="3">
        </div>
      </div>
      <div class="panel">
        <h3>Environmental Acoustics</h3>
        <div class="metric-box">
          <div class="metric-header">
            <span>Wet Rain Audible Noise</span>
            <span class="metric-standard">Target <= 53-58 dBA</span>
          </div>
          <div class="metric-value-row">
            <span class="metric-value" id="corResDba">--</span>
            <span class="metric-unit">dBA @ ROW</span>
          </div>
          <div class="status-badge pass" id="corResBadge">Complies with EPRI residential threshold</div>
        </div>
      </div>
    </div>
  `;

  const calc = () => {
    const v = parseFloat(document.getElementById("corVolt").value) || 500;
    const b = parseInt(document.getElementById("corBundle").value) || 3;
    const grad = ((v / Math.sqrt(3)) / (1.4 * Math.log(1000 / 1.4))) * 0.75;
    const wetNoise = 52.0 + 12.0 * Math.log10(b) + 2.5 * (grad - 16.0);

    document.getElementById("corResDba").textContent = wetNoise.toFixed(1);
  };

  ["corVolt", "corBundle"].forEach(id => {
    document.getElementById(id).addEventListener("input", calc);
  });
  calc();
}

// ======================== GLOSSARY & SEARCH ========================

function renderGlossaryView(container) {
  container.innerHTML = `
    <div class="category-hero">
      <div class="hero-info">
        <h2>Transmission Engineering Glossary</h2>
        <p>Key terms, mathematical formulas, and standards references (IEEE, NESC, ASCE, CIGRE, ISO)</p>
      </div>
    </div>
    <div class="glossary-grid">
      ${GLOSSARY_TERMS.map(t => `
        <div class="glossary-card">
          <div class="glossary-header">
            <span class="glossary-title">${t.term}</span>
            <span class="glossary-std">${t.std}</span>
          </div>
          <p class="glossary-def">${t.def}</p>
          <p class="glossary-context"><strong>Engineering Context:</strong> ${t.ctx}</p>
        </div>
      `).join('')}
    </div>
  `;
}

function renderSearchResults(query) {
  const container = document.getElementById("contentArea");
  const filteredTerms = GLOSSARY_TERMS.filter(t => 
    t.term.toLowerCase().includes(query) ||
    t.def.toLowerCase().includes(query) ||
    t.std.toLowerCase().includes(query)
  );

  container.innerHTML = `
    <div class="category-hero">
      <div class="hero-info">
        <h2>Search Results for "${query}"</h2>
        <p>Found ${filteredTerms.length} matching terms and tools</p>
      </div>
    </div>
    <div class="glossary-grid">
      ${filteredTerms.map(t => `
        <div class="glossary-card">
          <div class="glossary-header">
            <span class="glossary-title">${t.term}</span>
            <span class="glossary-std">${t.std}</span>
          </div>
          <p class="glossary-def">${t.def}</p>
          <p class="glossary-context"><strong>Category:</strong> ${t.cat} | <strong>Context:</strong> ${t.ctx}</p>
        </div>
      `).join('')}
    </div>
  `;
}

window.setVal = function(id, val) {
  const el = document.getElementById(id);
  if (el) {
    el.value = val;
    el.dispatchEvent(new Event("input"));
  }
};


// ==================== FLASHCARDS & STUDY SUITE ====================
let studyActiveTab = "flashcards"; // flashcards, quiz, sheet
let currentCardIndex = 0;
let cardFlipped = false;
let studySelectedCategory = "All";

let quizCurrentIndex = 0;
let quizScore = 0;
let quizSelectedOption = null;
let quizAnswerSubmitted = false;

function renderStudyView(container) {
  const categories = ["All", "Line Design", "Corrosion", "Inspection and Assessment", "Lightning and Grounding", "Conductor and Hardware", "Right-of-Way and Environment"];
  
  container.innerHTML = `
    <div class="category-hero">
      <div class="hero-info">
        <h2>Transmission Engineering Study Suite</h2>
        <p>Interactive flashcards, standard review sheets, and knowledge quizzes across 80+ transmission standards</p>
      </div>
    </div>
    
    <div class="study-sub-tabs">
      <button class="study-tab-btn ${studyActiveTab === "flashcards" ? "active" : ""}" onclick="switchStudyTab('flashcards')">
        ⚡ Flashcards (${FLASHCARDS.length})
      </button>
      <button class="study-tab-btn ${studyActiveTab === "quiz" ? "active" : ""}" onclick="switchStudyTab('quiz')">
        📝 Quiz Assessment (${QUIZ_QUESTIONS.length})
      </button>
      <button class="study-tab-btn ${studyActiveTab === "sheet" ? "active" : ""}" onclick="switchStudyTab('sheet')">
        📖 Study Sheet (${FLASHCARDS.length})
      </button>
    </div>

    <div class="study-filter-bar">
      <span>Filter Category:</span>
      <select id="studyCategorySelect" onchange="onStudyCategoryChange(this.value)">
        ${categories.map(c => `<option value="${c}" ${c === studySelectedCategory ? "selected" : ""}>${c}</option>`).join("")}
      </select>
    </div>

    <div id="studyContentArea"></div>
  `;

  renderCurrentStudyTab();
}

window.switchStudyTab = function(tab) {
  studyActiveTab = tab;
  currentCardIndex = 0;
  cardFlipped = false;
  quizCurrentIndex = 0;
  quizScore = 0;
  quizSelectedOption = null;
  quizAnswerSubmitted = false;
  const container = document.getElementById("contentArea");
  if (container) renderStudyView(container);
};

window.onStudyCategoryChange = function(cat) {
  studySelectedCategory = cat;
  currentCardIndex = 0;
  cardFlipped = false;
  quizCurrentIndex = 0;
  quizSelectedOption = null;
  quizAnswerSubmitted = false;
  renderCurrentStudyTab();
};

function getFilteredCards() {
  if (studySelectedCategory === "All") return FLASHCARDS;
  return FLASHCARDS.filter(c => c.cat.toLowerCase() === studySelectedCategory.toLowerCase());
}

function getFilteredQuizzes() {
  if (studySelectedCategory === "All") return QUIZ_QUESTIONS;
  return QUIZ_QUESTIONS.filter(q => q.cat.toLowerCase() === studySelectedCategory.toLowerCase());
}

function renderCurrentStudyTab() {
  const container = document.getElementById("studyContentArea");
  if (!container) return;

  if (studyActiveTab === "flashcards") {
    renderFlashcardsTab(container);
  } else if (studyActiveTab === "quiz") {
    renderQuizTab(container);
  } else if (studyActiveTab === "sheet") {
    renderStudySheetTab(container);
  }
}

function renderFlashcardsTab(container) {
  const cards = getFilteredCards();
  if (cards.length === 0) {
    container.innerHTML = `<div class="empty-state">No flashcards found for category "${studySelectedCategory}".</div>`;
    return;
  }

  if (currentCardIndex >= cards.length) currentCardIndex = 0;
  const card = cards[currentCardIndex];

  container.innerHTML = `
    <div class="flashcard-workspace">
      <div class="flashcard-progress-bar">
        <span>Card ${currentCardIndex + 1} of ${cards.length}</span>
        <div class="progress-track">
          <div class="progress-fill" style="width: ${((currentCardIndex + 1) / cards.length) * 100}%"></div>
        </div>
      </div>

      <div class="flashcard-container ${cardFlipped ? "flipped" : ""}" onclick="toggleCardFlip()">
        <div class="flashcard-inner">
          <div class="flashcard-front">
            <div class="card-badge-row">
              <span class="card-badge cat">${card.cat}</span>
              <span class="card-badge std">${card.std}</span>
            </div>
            <h3 class="card-term">${card.term}</h3>
            <p class="card-prompt">What is the standard engineering definition and regulatory application?</p>
            <div class="card-flip-hint">👆 Tap to flip card</div>
          </div>
          <div class="flashcard-back">
            <div class="card-badge-row">
              <span class="card-badge std">${card.std}</span>
            </div>
            <div class="card-back-body">
              <h4 class="card-def-label">Definition:</h4>
              <p class="card-def">${card.shortDef}</p>
              
              <div class="card-formula-box">
                <span class="formula-label">Key Rule / Formula:</span>
                <code>${card.formula}</code>
              </div>

              <div class="card-context-box">
                <span class="context-label">Engineering Application:</span>
                <p>${card.context}</p>
              </div>
            </div>
            <div class="card-flip-hint">👆 Tap to flip back</div>
          </div>
        </div>
      </div>

      <div class="flashcard-controls">
        <button class="study-ctrl-btn" onclick="prevCard()" ${currentCardIndex === 0 ? "disabled" : ""}>◀ Previous</button>
        <button class="study-ctrl-btn primary" onclick="toggleCardFlip()">🔄 Flip</button>
        <button class="study-ctrl-btn" onclick="nextCard()" ${currentCardIndex === cards.length - 1 ? "disabled" : ""}>Next ▶</button>
      </div>
    </div>
  `;
}

window.toggleCardFlip = function() {
  cardFlipped = !cardFlipped;
  const cardEl = document.querySelector(".flashcard-container");
  if (cardEl) {
    if (cardFlipped) cardEl.classList.add("flipped");
    else cardEl.classList.remove("flipped");
  }
};

window.prevCard = function() {
  if (currentCardIndex > 0) {
    currentCardIndex--;
    cardFlipped = false;
    renderCurrentStudyTab();
  }
};

window.nextCard = function() {
  const cards = getFilteredCards();
  if (currentCardIndex < cards.length - 1) {
    currentCardIndex++;
    cardFlipped = false;
    renderCurrentStudyTab();
  }
};

function renderQuizTab(container) {
  const quizzes = getFilteredQuizzes();
  if (quizzes.length === 0) {
    container.innerHTML = `<div class="empty-state">No quiz questions available for category "${studySelectedCategory}".</div>`;
    return;
  }

  if (quizCurrentIndex >= quizzes.length) {
    // Quiz Completed Screen
    const pct = Math.round((quizScore / quizzes.length) * 100);
    container.innerHTML = `
      <div class="quiz-results-card">
        <h3>🎉 Assessment Completed!</h3>
        <div class="score-circle">
          <span class="score-number">${quizScore}/${quizzes.length}</span>
          <span class="score-pct">${pct}% Correct</span>
        </div>
        <p class="score-msg">${pct >= 80 ? "Excellent mastery of high-voltage transmission standards!" : "Good practice! Review the Study Sheet to solidify tricky formulas and clearance rules."}</p>
        <button class="study-ctrl-btn primary" onclick="resetQuiz()">🔁 Retake Quiz</button>
      </div>
    `;
    return;
  }

  const q = quizzes[quizCurrentIndex];

  container.innerHTML = `
    <div class="quiz-workspace">
      <div class="quiz-header-bar">
        <span>Question ${quizCurrentIndex + 1} of ${quizzes.length}</span>
        <span class="quiz-score-badge">Score: ${quizScore}</span>
      </div>
      <div class="quiz-card">
        <div class="card-badge-row">
          <span class="card-badge cat">${q.cat}</span>
          <span class="card-badge std">${q.std}</span>
        </div>
        <h3 class="quiz-question-text">${q.question}</h3>

        <div class="quiz-options-list">
          ${q.options.map((opt, idx) => {
            let stateClass = "";
            if (quizAnswerSubmitted) {
              if (idx === q.correctIndex) stateClass = "correct";
              else if (idx === quizSelectedOption) stateClass = "wrong";
            } else if (idx === quizSelectedOption) {
              stateClass = "selected";
            }
            return `
              <button class="quiz-opt-btn ${stateClass}" onclick="selectQuizOption(${idx})" ${quizAnswerSubmitted ? "disabled" : ""}>
                <span class="opt-index">${String.fromCharCode(65 + idx)}</span>
                <span class="opt-text">${opt}</span>
              </button>
            `;
          }).join("")}
        </div>

        ${quizAnswerSubmitted ? `
          <div class="quiz-explanation-box ${quizSelectedOption === q.correctIndex ? "pass" : "fail"}">
            <strong>${quizSelectedOption === q.correctIndex ? "✅ Correct!" : "❌ Incorrect"}</strong>
            <p>${q.explanation}</p>
          </div>
          <div class="quiz-next-bar">
            <button class="study-ctrl-btn primary" onclick="nextQuizQuestion()">
              ${quizCurrentIndex === quizzes.length - 1 ? "Finish Assessment 🏁" : "Next Question ▶"}
            </button>
          </div>
        ` : `
          <div class="quiz-submit-bar">
            <button class="study-ctrl-btn primary" onclick="submitQuizAnswer()" ${quizSelectedOption === null ? "disabled" : ""}>
              Submit Answer
            </button>
          </div>
        `}
      </div>
    </div>
  `;
}

window.selectQuizOption = function(idx) {
  if (quizAnswerSubmitted) return;
  quizSelectedOption = idx;
  renderCurrentStudyTab();
};

window.submitQuizAnswer = function() {
  if (quizSelectedOption === null || quizAnswerSubmitted) return;
  const quizzes = getFilteredQuizzes();
  const q = quizzes[quizCurrentIndex];
  quizAnswerSubmitted = true;
  if (quizSelectedOption === q.correctIndex) {
    quizScore++;
  }
  renderCurrentStudyTab();
};

window.nextQuizQuestion = function() {
  quizCurrentIndex++;
  quizSelectedOption = null;
  quizAnswerSubmitted = false;
  renderCurrentStudyTab();
};

window.resetQuiz = function() {
  quizCurrentIndex = 0;
  quizScore = 0;
  quizSelectedOption = null;
  quizAnswerSubmitted = false;
  renderCurrentStudyTab();
};

function renderStudySheetTab(container) {
  const cards = getFilteredCards();

  container.innerHTML = `
    <div class="study-sheet-container">
      <div class="study-sheet-header">
        <h3>Overhead Transmission Engineering Reference Sheet</h3>
        <p>Curated technical summaries, governing standards citations, and mathematical design formulas (${cards.length} items)</p>
      </div>
      
      <div class="study-sheet-grid">
        ${cards.map(c => `
          <div class="study-sheet-card">
            <div class="study-sheet-top">
              <span class="sheet-term">${c.term}</span>
              <span class="card-badge std">${c.std}</span>
            </div>
            <div class="sheet-cat">${c.cat}</div>
            <p class="sheet-def">${c.shortDef}</p>
            <div class="sheet-formula">
              <strong>Formula / Rule:</strong>
              <code>${c.formula}</code>
            </div>
            <div class="sheet-context">
              <strong>Application:</strong> ${c.context}
            </div>
          </div>
        `).join("")}
      </div>
    </div>
  `;
}

// ==================== TRANSMISSION AI CHATBOT ====================
let chatMessages = [
  {
    sender: "assistant",
    text: "Hello! I am your **Transmission Engineering Assistant**.\n\nAsk me anything about line design, NESC clearances, conductor ratings, corrosion mitigation, or OSHA MAD calculations, and I will provide the formula or direct you to the right calculator!",
    nav: null
  }
];
let chatIsOpen = false;

const QUICK_QUESTIONS = [
  { title: "Emissivity & Ampacity", q: "How does conductor surface emissivity affect ampacity?" },
  { title: "NESC 232 Road Clearance", q: "What is the minimum ground clearance for a 138 kV line over roads?" },
  { title: "OSHA MAD Live-Line", q: "How is live-line Minimum Approach Distance calculated?" },
  { title: "Catenary Sag Formula", q: "What is the formula for conductor catenary sag?" },
  { title: "Galvanized Zinc Life", q: "How long does zinc galvanizing last in industrial C4 environments?" },
  { title: "FERC 881 AAR", q: "What are the core requirements of FERC Order 881 for AAR?" }
];

function toggleChatModal() {
  chatIsOpen = !chatIsOpen;
  const modal = document.getElementById("chatModal");
  if (modal) {
    if (chatIsOpen) {
      modal.classList.add("open");
      renderChatMessages();
      const input = document.getElementById("chatUserInput");
      if (input) input.focus();
    } else {
      modal.classList.remove("open");
    }
  }
}
window.toggleChatModal = toggleChatModal;

function renderChatMessages() {
  const container = document.getElementById("chatMessagesArea");
  if (!container) return;

  container.innerHTML = chatMessages.map((m, idx) => `
    <div class="chat-msg-row ${m.sender}">
      <div class="chat-bubble ${m.sender}">
        <div class="chat-msg-text">${formatMarkdownText(m.text)}</div>
        ${m.nav ? `
          <div class="chat-nav-actions">
            ${m.nav.toolId ? `
              <button class="chat-nav-btn" onclick="navigateToTool('${m.nav.toolCategory}', '${m.nav.toolId}')">
                🛠️ Open ${m.nav.toolTitle}
              </button>
            ` : ""}
            ${m.nav.glossaryTerm ? `
              <button class="chat-nav-btn secondary" onclick="navigateToGlossary('${m.nav.glossaryTerm}')">
                📚 View in Glossary
              </button>
            ` : ""}
          </div>
        ` : ""}
      </div>
    </div>
  `).join("");

  container.scrollTop = container.scrollHeight;
}

function formatMarkdownText(text) {
  return text
    .replace(/\*\*(.*?)\*\*/g, "<strong>$1</strong>")
    .replace(/\*(.*?)\*/g, "<em>$1</em>")
    .replace(/`(.*?)`/g, "<code>$1</code>")
    .replace(/\n/g, "<br>");
}

window.askQuickQuestion = function(q) {
  const input = document.getElementById("chatUserInput");
  if (input) {
    input.value = q;
    sendChatMessage();
  }
};

window.sendChatMessage = function() {
  const input = document.getElementById("chatUserInput");
  if (!input) return;
  const userText = input.value.trim();
  if (!userText) return;

  chatMessages.push({ sender: "user", text: userText, nav: null });
  input.value = "";
  renderChatMessages();

  setTimeout(() => {
    const response = computeChatbotResponse(userText);
    chatMessages.push({
      sender: "assistant",
      text: response.answer,
      nav: response.nav
    });
    renderChatMessages();
  }, 250);
};

function computeChatbotResponse(query) {
  const q = query.toLowerCase();

  if (q.includes("emissiv") || q.includes("absorptiv") || (q.includes("emissivity") && q.includes("ampacity"))) {
    return {
      answer: "**Conductor Emissivity (ε) & Solar Absorptivity (α)**\n\nPer **IEEE Std 738 Clause 6**:\n• **New Bright Conductor:** Emissivity ε ≈ 0.23 to 0.30, Absorptivity α ≈ 0.23 to 0.30\n• **Aged Weathered Conductor:** Emissivity ε ≈ 0.70 to 0.90, Absorptivity α ≈ 0.80 to 0.90\n\n**Key Engineering Impact:**\nWeathered conductors radiate heat up to **3× more efficiently**, which can safely increase steady-state current carrying ampacity by **12% to 20%** compared to shiny new aluminum cables!",
      nav: {
        toolCategory: "line_design",
        toolId: "ampacity",
        toolTitle: "IEEE 738 Ampacity Calculator",
        glossaryTerm: "Conductor Emissivity"
      }
    };
  }

  if (q.includes("nesc") && (q.includes("232") || q.includes("road") || q.includes("clearance") || q.includes("ground"))) {
    return {
      answer: "**NESC Rule 232: Vertical Ground Clearances**\n\nPer **NESC C2-2023 Table 232-1**:\n• **Public Roads & Highways:** Base clearance is **18.5 ft** for voltages up to 22 kV.\n• **Voltage Adder:** Add **0.4 in (0.033 ft) per kV** exceeding 22 kV.\n• **For 138 kV Ph-Ph Line:**\n  Adder = (138 - 22) × 0.4 in = 46.4 in = **3.87 ft**\n  Minimum Ground Clearance = 18.5 ft + 3.87 ft = **22.37 ft (6.82 m)**\n• **Altitude Adder:** 3% per 1,000 ft elevation exceeding 3,300 ft.",
      nav: {
        toolCategory: "line_design",
        toolId: "catenary",
        toolTitle: "Catenary & Clearance Calculator",
        glossaryTerm: "NESC Rule 232 (Vertical Ground Clearances)"
      }
    };
  }

  if (q.includes("osha") || q.includes("mad") || q.includes("minimum approach")) {
    return {
      answer: "**OSHA Minimum Approach Distance (Live-Line Working)**\n\nPer **OSHA 29 CFR 1910.269 Table R-6 / IEEE Std 516**:\nMAD = D_electrical + D_ergonomic (where D_ergonomic = 2.0 ft / 0.61 m inadvertent worker movement allowance):\n• **69 kV:** Ph-to-Ground MAD = **3.0 ft (0.95 m)**\n• **138 kV:** Ph-to-Ground MAD = **3.6 ft (1.09 m)**\n• **230 kV:** Ph-to-Ground MAD = **5.3 ft (1.60 m)**\n• **500 kV:** Ph-to-Ground MAD = **11.3 ft (3.44 m)**\n\n*Note:* Above 3,000 ft elevation, apply air density altitude derating factor A.",
      nav: {
        toolCategory: "inspection",
        toolId: "defect_matrix",
        toolTitle: "Defect Priority Matrix",
        glossaryTerm: "Minimum Approach Distance (MAD)"
      }
    };
  }

  if (q.includes("catenary") || (q.includes("sag") && !q.includes("blowout"))) {
    return {
      answer: "**Catenary Sag & Tension Formulation**\n\nPer **IEEE Std 738 & ASCE Manual 74**:\n**Sag S = (w · L²) / (8 · H)**\n• **S:** Mid-span sag (ft or m)\n• **w:** Resultant unit weight including radial ice & wind (lb/ft or N/m)\n• **L:** Horizontal span length (ft or m)\n• **H:** Horizontal cable tension component (lb or N)\n\n*Takeaway:* Sag scales quadratically with span length (L²) and inversely with tension (H). Maximum sag occurs under max thermal temperature or heavy ice.",
      nav: {
        toolCategory: "line_design",
        toolId: "catenary",
        toolTitle: "Catenary Sag & Tension",
        glossaryTerm: "Catenary Curve"
      }
    };
  }

  if (q.includes("zinc") || q.includes("galvaniz") || q.includes("iso 9223")) {
    return {
      answer: "**Hot-Dip Galvanizing (Zinc) Coating Life**\n\nPer **ISO 9223 & ASTM A123**:\nTypical transmission tower zinc thickness is **85 µm to 100 µm** (≈ 610 g/m²):\n• **C1 (Desert/Dry):** < 0.1 µm/yr (Life > 100 yrs)\n• **C2 (Rural):** 0.1 - 0.7 µm/yr (Life 80 - 100 yrs)\n• **C3 (Urban/Inland):** 0.7 - 2.1 µm/yr (Life 40 - 70 yrs)\n• **C4 (Industrial):** 2.1 - 4.2 µm/yr (Life 20 - 40 yrs)\n• **C5/CX (Coastal Marine):** 4.2 - 25 µm/yr (Life < 15 yrs)",
      nav: {
        toolCategory: "corrosion",
        toolId: "zinc_life",
        toolTitle: "Galvanizing Coating Life",
        glossaryTerm: "Atmospheric Corrosivity (C1-CX)"
      }
    };
  }

  if (q.includes("ferc") || q.includes("881") || q.includes("aar")) {
    return {
      answer: "**FERC Order 881 (Ambient-Adjusted Ratings)**\n\nMandates transmission operators to calculate hourly ratings reflecting forecast temperatures:\n• Unlocks **15% to 35% additional transfer capacity** during cooler periods compared to static seasonal ratings.\n• Requires temperature intervals of ≤ 5°F (≤ 2.8°C) for at least 10 days ahead.\n• Accounts for solar heating during day vs nocturnal cooling.",
      nav: {
        toolCategory: "line_design",
        toolId: "ampacity",
        toolTitle: "IEEE 738 Ampacity",
        glossaryTerm: "Ambient-Adjusted Ratings (FERC 881)"
      }
    };
  }

  // Check matching glossary term
  const match = GLOSSARY_TERMS.find(t => 
    q.includes(t.term.toLowerCase()) || 
    t.term.toLowerCase().includes(q)
  );

  if (match) {
    return {
      answer: `**${match.term}**\n*Standard Reference:* ${match.std} (${match.cat})\n\n**Engineering Definition:**\n${match.def}\n\n**Practical Application:**\n${match.ctx}`,
      nav: {
        toolCategory: null,
        toolId: null,
        toolTitle: null,
        glossaryTerm: match.term
      }
    };
  }

  return {
    answer: "I am your **Transmission Engineering Assistant**.\n\nI can provide direct answers and jump links for:\n1. **Line Design:** Catenary sag, ruling span, blowout sway, IEEE 738 ampacity, NESC 232 clearances.\n2. **Corrosion:** Zinc life (ISO 9223), soil anchor pitting, AC interference, and cathodic protection.\n3. **Inspection:** Defect Priority Matrix (DSI), ASCE 10 buckling, wood pole shell, and OSHA MAD.\n4. **Lightning & Grounding:** Footing resistance, Wenner 4-point method, and shielding angles.\n5. **Hardware & ROW:** Conductor databases, Aeolian dampers, EMF profiles, and corona noise.\n\n*Try asking a question or click one of the quick question chips above!*",
    nav: null
  };
}

window.navigateToTool = function(cat, toolId) {
  chatIsOpen = false;
  const modal = document.getElementById("chatModal");
  if (modal) modal.classList.remove("open");

  const tab = document.querySelector(`.category-nav .nav-tab[data-category="${cat}"]`);
  if (tab) {
    tab.click();
    setTimeout(() => {
      selectSubTool(toolId);
    }, 50);
  }
};

window.navigateToGlossary = function(term) {
  chatIsOpen = false;
  const modal = document.getElementById("chatModal");
  if (modal) modal.classList.remove("open");

  const tab = document.querySelector(`.category-nav .nav-tab[data-category="glossary"]`);
  if (tab) {
    tab.click();
    setTimeout(() => {
      const searchInput = document.getElementById("globalSearchInput");
      if (searchInput) {
        searchInput.value = term;
        renderSearchResults(term.toLowerCase());
      }
    }, 50);
  }
};
