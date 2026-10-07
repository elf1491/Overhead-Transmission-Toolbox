package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.model.*
import com.example.ui.components.*

@Composable
fun ConductorHardwareScreen(
    onNavigateBack: () -> Unit,
    activeToolId: String = "conductor_db",
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
                text = "Conductor & Hardware",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        ScrollableTabRow(
            selectedTabIndex = when (selectedTool) {
                "conductor_db" -> 0
                "aeolian_vibration" -> 1
                "insulator_string" -> 2
                else -> 0
            },
            edgePadding = 0.dp
        ) {
            Tab(
                selected = selectedTool == "conductor_db",
                onClick = { selectedTool = "conductor_db" },
                text = { Text("Conductor Database") },
                icon = { Icon(Icons.Default.Dataset, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "aeolian_vibration",
                onClick = { selectedTool = "aeolian_vibration" },
                text = { Text("Aeolian Damper") },
                icon = { Icon(Icons.Default.GraphicEq, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "insulator_string",
                onClick = { selectedTool = "insulator_string" },
                text = { Text("Insulator Creepage") },
                icon = { Icon(Icons.Default.Layers, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTool) {
            "conductor_db" -> ConductorDatabaseView()
            "aeolian_vibration" -> AeolianVibrationCalculator()
            "insulator_string" -> InsulatorStringCalculator()
        }
    }
}

@Composable
fun ConductorDatabaseView() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedConductor by remember { mutableStateOf(STANDARD_CONDUCTORS[1]) } // Drake default

    Text(
        text = "Standard Transmission Conductor Reference",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "ASTM B232 / B856 physical and electrical parameters for ACSR, ACSS, and AAAC.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        placeholder = { Text("Search by code word or size (e.g. Drake, 795, ACSS)...") },
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Conductor Selector Chips
    val filtered = remember(searchQuery) {
        if (searchQuery.isBlank()) STANDARD_CONDUCTORS
        else STANDARD_CONDUCTORS.filter {
            it.codeWord.contains(searchQuery, ignoreCase = true) ||
                    it.sizeKcmil.toString().contains(searchQuery) ||
                    it.type.contains(searchQuery, ignoreCase = true)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        filtered.take(4).forEach { cond ->
            FilterChip(
                selected = selectedConductor.codeWord == cond.codeWord,
                onClick = { selectedConductor = cond },
                label = { Text(cond.codeWord) }
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Detailed Card for Selected Conductor
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "${selectedConductor.codeWord} (${selectedConductor.type})",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${selectedConductor.sizeKcmil} kcmil • Stranding ${selectedConductor.strandingAlSt}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "${selectedConductor.standardAmpacityAmps.toInt()} A",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            // Properties Grid
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Overall Diameter:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("${selectedConductor.diameterInches}\" (${String.format("%.1f", selectedConductor.diameterMm)} mm)", fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
                }
                Column {
                    Text("Linear Weight:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("${selectedConductor.weightLbPerFt} lb/ft (${String.format("%.2f", selectedConductor.weightKgPerM)} kg/m)", fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Rated Breaking Strength:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("${selectedConductor.ratedBreakingStrengthLbf.toInt()} lbf (${String.format("%.1f", selectedConductor.rbsKn)} kN)", fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
                }
                Column {
                    Text("AC Resistance @ 75°C:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("${selectedConductor.acResistance75cOhmPerMile} Ω/mi", fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun AeolianVibrationCalculator() {
    var diamStr by remember { mutableStateOf("28.14") } // mm (Drake)
    var weightStr by remember { mutableStateOf("1.628") } // kg/m
    var everydayTensionStr by remember { mutableStateOf("28.0") } // kN (~20% RTS)
    var rbsKnStr by remember { mutableStateOf("140.1") } // kN (Drake RTS)
    var windSpeedStr by remember { mutableStateOf("3.5") } // m/s

    val diam = diamStr.toDoubleOrNull() ?: 28.14
    val weight = weightStr.toDoubleOrNull() ?: 1.628
    val everydayT = everydayTensionStr.toDoubleOrNull() ?: 28.0
    val rbs = rbsKnStr.toDoubleOrNull() ?: 140.1
    val wind = windSpeedStr.toDoubleOrNull() ?: 3.5

    val result = remember(diam, weight, everydayT, rbs, wind) {
        ConductorHardwareEngine.calculateVibrationAndDamper(diam, weight, everydayT, rbs, wind)
    }

    Text(
        text = "Aeolian Vibration & Stockbridge Damper Positioning",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Vortex-shedding Strouhal frequency f = (St·V)/d and optimum nodal distance x = 0.8·(λ/2) from suspension clamp per IEEE Std 664 & CIGRE SC22.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Conductor Diameter",
        value = diamStr,
        onValueChange = { diamStr = it },
        unit = "mm"
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Everyday Horizontal Tension (T_0)",
        value = everydayTensionStr,
        onValueChange = { everydayTensionStr = it },
        unit = "kN",
        presetOptions = listOf("16% RTS (22.4 kN)" to "22.4", "20% RTS (28.0 kN)" to "28.0", "24% RTS (33.6 kN)" to "33.6")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Laminar Wind Velocity (Transverse)",
        value = windSpeedStr,
        onValueChange = { windSpeedStr = it },
        unit = "m/s",
        presetOptions = listOf("2.0 m/s (Light)" to "2.0", "3.5 m/s (Standard)" to "3.5", "6.0 m/s (High)" to "6.0")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Recommended Damper Distance from Clamp",
        value = String.format("%.1f", result.recommendedDamperDistanceInches),
        unit = "inches",
        statusText = "${String.format("%.2f", result.recommendedDamperDistanceMeters)} meters from mouth of suspension clamp. ${result.recommendation}",
        isPassed = result.cigreEverydayTensionLimitPassed,
        standardRef = "x = 0.8 · (V_wave / 2f)"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Vortex Shedding Strouhal Frequency",
        value = String.format("%.1f", result.strouhalFrequencyHz),
        unit = "Hz",
        statusText = "Half Loop Length λ/2 = ${String.format("%.2f", result.halfLoopLengthMeters)} meters",
        isPassed = true,
        standardRef = "Strouhal St = 0.185"
    )
}

@Composable
fun InsulatorStringCalculator() {
    var lineVoltageStr by remember { mutableStateOf("230") } // kV
    var selectedPollution by remember { mutableStateOf("Heavy (25 mm/kV)") }
    var meRatingStr by remember { mutableStateOf("30000") } // lbs
    var workingLoadStr by remember { mutableStateOf("8500") } // lbs

    val lineV = lineVoltageStr.toDoubleOrNull() ?: 230.0
    val meRating = meRatingStr.toDoubleOrNull() ?: 30000.0
    val workingLoad = workingLoadStr.toDoubleOrNull() ?: 8500.0

    val result = remember(lineV, selectedPollution, meRating, workingLoad) {
        ConductorHardwareEngine.calculateInsulatorString(lineV, selectedPollution, meRating, workingLoad)
    }

    Text(
        text = "Insulator String Mechanical Rating & Creepage Distance",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "IEC 60815 Site Pollution Severity (SPS) specific creepage and ANSI C29.2 M&E mechanical capacity.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Nominal System Voltage (Phase-to-Phase)",
        value = lineVoltageStr,
        onValueChange = { lineVoltageStr = it },
        unit = "kV",
        presetOptions = listOf("115 kV" to "115", "230 kV" to "230", "500 kV" to "500")
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text("Site Pollution Severity Level (IEC 60815):", style = MaterialTheme.typography.labelMedium)
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        listOf("Light (16 mm/kV)", "Medium (20 mm/kV)", "Heavy (25 mm/kV)").forEach { lvl ->
            FilterChip(
                selected = selectedPollution.startsWith(lvl.take(5)),
                onClick = { selectedPollution = lvl },
                label = { Text(lvl.take(12)) }
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    EngineeringInputField(
        label = "Insulator Mechanical & Electrical (M&E) Rating",
        value = meRatingStr,
        onValueChange = { meRatingStr = it },
        unit = "lbf",
        presetOptions = listOf("25,000 lb (Class 52-3)" to "25000", "30,000 lb (Class 52-5)" to "30000", "50,000 lb (Heavy Dead-End)" to "50000")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Max Applied Mechanical Working Load",
        value = workingLoadStr,
        onValueChange = { workingLoadStr = it },
        unit = "lbf"
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Required Standard 10\" x 5-3/4\" Bells",
        value = "${result.numberOfStandardDiscs} Bells",
        statusText = "Total Creepage: ${result.requiredTotalCreepageMm.toInt()} mm (Polymer String Length: ${String.format("%.2f", result.polymerStringLengthMeters)} m)",
        isPassed = true,
        standardRef = "IEC 60815 / ANSI C29.2"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Mechanical Safety Factor",
        value = String.format("%.2f", result.mechanicalSafetyFactor),
        unit = ": 1",
        statusText = result.mechanicalVerdict,
        isPassed = result.mechanicalSafetyFactor >= 2.5,
        standardRef = "Target M&E SF >= 2.50:1"
    )
}
