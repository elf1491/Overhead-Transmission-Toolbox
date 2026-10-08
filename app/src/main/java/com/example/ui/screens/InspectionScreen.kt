package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.model.*
import com.example.ui.components.*
import kotlin.math.*

@Composable
fun InspectionScreen(
    onNavigateBack: () -> Unit,
    activeToolId: String = "defect_matrix",
    modifier: Modifier = Modifier
) {
    var selectedTool by remember { mutableStateOf(activeToolId) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back to toolbox")
            }
            Text(
                text = "Inspection & Assessment",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        ScrollableTabRow(
            selectedTabIndex = when (selectedTool) {
                "defect_matrix" -> 0
                "wood_pole" -> 1
                "lattice_buckling" -> 2
                "osha_mad" -> 3
                else -> 0
            },
            edgePadding = 0.dp
        ) {
            Tab(
                selected = selectedTool == "defect_matrix",
                onClick = { selectedTool = "defect_matrix" },
                text = { Text("Defect Priority Matrix") },
                icon = { Icon(Icons.Default.AssignmentLate, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "wood_pole",
                onClick = { selectedTool = "wood_pole" },
                text = { Text("Wood Pole Shell") },
                icon = { Icon(Icons.Default.Park, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "lattice_buckling",
                onClick = { selectedTool = "lattice_buckling" },
                text = { Text("Lattice Member Buckling") },
                icon = { Icon(Icons.Default.Architecture, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "osha_mad",
                onClick = { selectedTool = "osha_mad" },
                text = { Text("OSHA MAD Live-Line") },
                icon = { Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTool) {
            "defect_matrix" -> DefectMatrixCalculator()
            "wood_pole" -> WoodPoleStrengthCalculator()
            "lattice_buckling" -> LatticeBucklingCalculator()
            "osha_mad" -> OshaMadCalculator()
        }
    }
}

@Composable
fun DefectMatrixCalculator() {
    var structureType by remember { mutableStateOf("Steel Lattice Tower") }
    var foundationDefect by remember { mutableStateOf(0) } // 0 to 3
    var memberBent by remember { mutableStateOf(false) }
    var corrosionGrade by remember { mutableStateOf(1) } // 0 to 4
    var insulatorBrokenCount by remember { mutableStateOf(0) }
    var looseHardwareCount by remember { mutableStateOf(0) }
    var woodDecayPresent by remember { mutableStateOf(false) }

    val result = remember(
        structureType,
        foundationDefect,
        memberBent,
        corrosionGrade,
        insulatorBrokenCount,
        looseHardwareCount,
        woodDecayPresent
    ) {
        InspectionEngine.evaluateDefects(
            structureType = structureType,
            foundationDefectLevel = foundationDefect,
            structuralMemberBent = memberBent,
            corrosionGrade = corrosionGrade,
            insulatorDamageCount = insulatorBrokenCount,
            hardwareLooseCount = looseHardwareCount,
            woodDecayOrPoleShellLoss = woodDecayPresent
        )
    }

    Text(
        text = "Transmission Defect Priority & Severity Matrix",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Calculates EPRI / NESC Transmission Defect Severity Index (P1-P5 action queue)",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    // Form inputs
    Text("Structure Type:", style = MaterialTheme.typography.labelMedium)
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
        listOf("Steel Lattice", "Tubular Steel", "Wood Pole").forEach { type ->
            FilterChip(
                selected = structureType.startsWith(type),
                onClick = { structureType = type },
                label = { Text(type) }
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text("Foundation Condition (0 = Sound, 3 = Severe Spalling/Uplift):", style = MaterialTheme.typography.labelMedium)
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        (0..3).forEach { lvl ->
            FilterChip(
                selected = foundationDefect == lvl,
                onClick = { foundationDefect = lvl },
                label = { Text("Level $lvl") }
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Structural Leg / Crossarm Bent or Buckled:", style = MaterialTheme.typography.labelMedium)
        Switch(checked = memberBent, onCheckedChange = { memberBent = it })
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text("Steel Corrosion Grade (0 = Bright Zinc, 4 = Flaking Laminar Rust):", style = MaterialTheme.typography.labelMedium)
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        (0..4).forEach { gr ->
            FilterChip(
                selected = corrosionGrade == gr,
                onClick = { corrosionGrade = gr },
                label = { Text("Grade $gr") }
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Text("Damaged / Shattered Insulator Bells in String:", style = MaterialTheme.typography.labelMedium)
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        listOf(0, 1, 2, 3).forEach { count ->
            FilterChip(
                selected = insulatorBrokenCount == count,
                onClick = { insulatorBrokenCount = count },
                label = { Text("$count bells") }
            )
        }
    }

    if (structureType.contains("Wood")) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Internal Heartwood Rot / Woodpecker Hole:", style = MaterialTheme.typography.labelMedium)
            Switch(checked = woodDecayPresent, onCheckedChange = { woodDecayPresent = it })
        }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Results Box
    MetricResultBox(
        label = "Defect Action Priority Tier",
        value = "${result.priority.code} — ${result.priority.label}",
        statusText = "Action Timeline: ${result.priority.timeframe}",
        isPassed = result.priority == DefectUrgency.P5 || result.priority == DefectUrgency.P4,
        standardRef = "EPRI Assessment Protocol"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Estimated Structural Safety Factor",
        value = String.format("%.2f", result.safetyFactorEstimate),
        statusText = if (result.nescViolationPotential) "ALERT: Potential NESC Rule 260 / Rule 261 non-compliance" else "Compliant with baseline structural margins",
        isPassed = !result.nescViolationPotential,
        standardRef = "NESC Grade B Factor (min 1.50-2.50)"
    )

    Spacer(modifier = Modifier.height(12.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "Required Corrective Work Orders:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            result.recommendedActions.forEach { action ->
                Row(modifier = Modifier.padding(vertical = 3.dp)) {
                    Text("• ", color = MaterialTheme.colorScheme.primary, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Text(action, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun WoodPoleStrengthCalculator() {
    var circStr by remember { mutableStateOf("42.0") } // inches (Class 2 pole groundline)
    var shellStr by remember { mutableStateOf("3.5") } // inches sound shell
    var decayDepthStr by remember { mutableStateOf("0.5") } // external decay to shave

    val circ = circStr.toDoubleOrNull() ?: 42.0
    val shell = shellStr.toDoubleOrNull() ?: 3.5
    val decay = decayDepthStr.toDoubleOrNull() ?: 0.5

    val result = remember(circ, shell, decay) {
        InspectionEngine.calculateWoodPoleCapacity(circ, shell, decay)
    }

    Text(
        text = "Wood Pole Sounding & Shell Remaining Strength",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "ASCE Manual 91 / ANSI O5.1 hollow cylinder section modulus analysis for groundline heartwood decay.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Original Groundline Circumference",
        value = circStr,
        onValueChange = { circStr = it },
        unit = "in",
        presetOptions = listOf("Class 1 (45\")" to "45.0", "Class 2 (42\")" to "42.0", "Class 3 (38\")" to "38.0")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Measured Sound Wood Shell Thickness",
        value = shellStr,
        onValueChange = { shellStr = it },
        unit = "in",
        presetOptions = listOf("2.0\" (Severely Hollow)" to "2.0", "3.5\" (Decayed Core)" to "3.5", "5.5\" (Sound Shell)" to "5.5")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "External Surface Decay Depth",
        value = decayDepthStr,
        onValueChange = { decayDepthStr = it },
        unit = "in",
        presetOptions = listOf("None (0\")" to "0.0", "0.5\" (Shaved)" to "0.5", "1.0\" (Severe)" to "1.0")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Remaining Moment Bending Capacity",
        value = String.format("%.1f", result.remainingMomentCapacityPercent),
        unit = "%",
        statusText = result.structuralVerdict,
        isPassed = !result.requiresTagging,
        standardRef = "NESC Rule 261-A-2 (67% Threshold)"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Residual Section Modulus (Z_eff)",
        value = String.format("%.1f", result.remainingSectionModulusIn3),
        unit = "in³",
        statusText = "Original Z was ${String.format("%.1f", result.originalSectionModulusIn3)} in³ (Effective Outer Diam: ${String.format("%.1f", result.effectiveDiameterInches)}\")",
        isPassed = !result.requiresTagging,
        standardRef = "Z = π·(D⁴ - d⁴) / (32·D)"
    )
}

@Composable
fun LatticeBucklingCalculator() {
    var sectionName by remember { mutableStateOf("L 3 x 3 x 1/4") }
    var areaStr by remember { mutableStateOf("1.44") }
    var rInchesStr by remember { mutableStateOf("0.59") } // r_z min
    var lengthStr by remember { mutableStateOf("60.0") } // 5 ft unbraced length
    var yieldKsiStr by remember { mutableStateOf("36.0") }

    val area = areaStr.toDoubleOrNull() ?: 1.44
    val r = rInchesStr.toDoubleOrNull() ?: 0.59
    val length = lengthStr.toDoubleOrNull() ?: 60.0
    val yieldKsi = yieldKsiStr.toDoubleOrNull() ?: 36.0

    val result = remember(sectionName, area, r, length, yieldKsi) {
        InspectionEngine.calculateLatticeBuckling(
            sectionName = sectionName,
            areaSqIn = area,
            radiusOfGyrationInches = r,
            unbracedLengthInches = length,
            yieldStrengthKsi = yieldKsi
        )
    }

    Text(
        text = "Lattice Steel Member Buckling Capacity",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "ASCE 10 Design of Latticed Steel Transmission Structures column buckling assessment.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text("Standard Lattice Section Preset:", style = MaterialTheme.typography.labelMedium)
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        SuggestionChip(
            onClick = {
                sectionName = "L 3 x 3 x 1/4"
                areaStr = "1.44"
                rInchesStr = "0.59"
            },
            label = { Text("L 3x3x1/4") }
        )
        SuggestionChip(
            onClick = {
                sectionName = "L 4 x 4 x 3/8"
                areaStr = "2.86"
                rInchesStr = "0.78"
            },
            label = { Text("L 4x4x3/8") }
        )
        SuggestionChip(
            onClick = {
                sectionName = "L 5 x 5 x 1/2"
                areaStr = "4.75"
                rInchesStr = "0.98"
            },
            label = { Text("L 5x5x1/2") }
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Unbraced Length (L)",
        value = lengthStr,
        onValueChange = { lengthStr = it },
        unit = "in",
        presetOptions = listOf("48\" (4 ft)" to "48.0", "60\" (5 ft)" to "60.0", "84\" (7 ft)" to "84.0")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Steel Yield Strength (F_y)",
        value = yieldKsiStr,
        onValueChange = { yieldKsiStr = it },
        unit = "ksi",
        presetOptions = listOf("36 ksi (ASTM A36)" to "36.0", "50 ksi (ASTM A572-50)" to "50.0")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Slenderness Ratio (KL / r)",
        value = String.format("%.1f", result.slendernessRatioKlR),
        statusText = if (result.asce10LimitPassed) "Passes ASCE 10 slenderness limit (KL/r <= 200)" else "FAIL: Exceeds ASCE 10 maximum compression limit (KL/r > 200)",
        isPassed = result.asce10LimitPassed,
        standardRef = "ASCE 10-15 Section 3.7"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Allowable Compressive Axial Load",
        value = String.format("%.1f", result.allowableCompressiveLoadKips),
        unit = "kips",
        statusText = "Governing Mode: ${result.bucklingMode} (Critical Stress: ${String.format("%.1f", result.criticalStressFcrKsi)} ksi)",
        isPassed = result.asce10LimitPassed,
        standardRef = "P_all = F_cr · Area"
    )
}

@Composable
fun OshaMadCalculator() {
    var voltageStr by remember { mutableStateOf("230") } // kV
    var altStr by remember { mutableStateOf("1000") } // ft
    var customTStr by remember { mutableStateOf("") } // optional custom T

    val v = voltageStr.toDoubleOrNull() ?: 230.0
    val alt = altStr.toDoubleOrNull() ?: 1000.0
    val customT = customTStr.toDoubleOrNull()

    val result = remember(v, alt, customT) {
        RegulatoryClearanceEngine.calculateOshaMad(
            phaseToPhaseVoltageKv = v,
            transientOvervoltageT = customT,
            altitudeFt = alt
        )
    }

    Text(
        text = "OSHA 1910.269 Minimum Approach Distance (MAD)",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Calculates mandatory live-line worker and tool approach clearances per OSHA 29 CFR 1910.269(l)(3) & IEEE Std 516-2021.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Nominal System Voltage (Phase-to-Phase)",
        value = voltageStr,
        onValueChange = { voltageStr = it },
        unit = "kV",
        presetOptions = listOf("69 kV" to "69", "115 kV" to "115", "138 kV" to "138", "230 kV" to "230", "345 kV" to "345", "500 kV" to "500")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Worksite Elevation Above Sea Level",
        value = altStr,
        onValueChange = { altStr = it },
        unit = "ft",
        presetOptions = listOf("Sea Level (0 ft)" to "0", "3,000 ft" to "3000", "5,000 ft (Mile High)" to "5000", "8,000 ft (Mountain Pass)" to "8000")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Per-Unit Transient Overvoltage (T)",
        value = customTStr,
        onValueChange = { customTStr = it },
        unit = "pu",
        presetOptions = listOf("Default (${String.format("%.1f", result.perUnitTransientT)} pu)" to "", "2.0 pu" to "2.0", "2.4 pu" to "2.4", "3.0 pu" to "3.0")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Phase-to-Ground Minimum Approach Distance",
        value = String.format("%.2f", result.phaseToGroundMadFt),
        unit = "ft (${String.format("%.2f", result.phaseToGroundMadMeters)} m)",
        statusText = "Live-line tool & lineworker safety buffer. Transient T = ${String.format("%.2f", result.perUnitTransientT)} pu | Alt Factor A = ${String.format("%.2f", result.altitudeCorrectionFactorA)}",
        isPassed = true,
        standardRef = "OSHA 1910.269 App B Table R-6"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Phase-to-Phase Minimum Approach Distance",
        value = String.format("%.2f", result.phaseToPhaseMadFt),
        unit = "ft (${String.format("%.2f", result.phaseToPhaseMadMeters)} m)",
        statusText = "Between conductors of different phases during energized maneuvering.",
        isPassed = true,
        standardRef = "IEEE Std 516-2021"
    )

    Spacer(modifier = Modifier.height(12.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "Regulatory Crew Safety Requirements:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "• Qualified lineworkers must maintain MAD at all times unless insulated with rated live-line sticks (ASTM F711) or performing bare-hand work from an equipotential aerial platform.\n" +
                        "• At altitudes above 2,950 ft (900 m), thinner air reduces dielectric breakdown strength, requiring the altitude correction multiplier shown above.\n" +
                        "• Ensure automated reclosing is disabled (Hot Line Order / Hold Off) prior to energized work.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
