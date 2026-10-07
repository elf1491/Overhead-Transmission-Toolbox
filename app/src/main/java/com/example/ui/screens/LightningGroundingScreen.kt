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

@Composable
fun LightningGroundingScreen(
    onNavigateBack: () -> Unit,
    activeToolId: String = "footing_resistance",
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
                text = "Lightning & Grounding",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        ScrollableTabRow(
            selectedTabIndex = when (selectedTool) {
                "footing_resistance" -> 0
                "shielding_angle" -> 1
                "backflashover" -> 2
                "wenner_test" -> 3
                else -> 0
            },
            edgePadding = 0.dp
        ) {
            Tab(
                selected = selectedTool == "footing_resistance",
                onClick = { selectedTool = "footing_resistance" },
                text = { Text("Footing Grounding") },
                icon = { Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "shielding_angle",
                onClick = { selectedTool = "shielding_angle" },
                text = { Text("Shielding Angle") },
                icon = { Icon(Icons.Default.Umbrella, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "backflashover",
                onClick = { selectedTool = "backflashover" },
                text = { Text("Backflashover Rate") },
                icon = { Icon(Icons.Default.FlashOn, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "wenner_test",
                onClick = { selectedTool = "wenner_test" },
                text = { Text("Wenner 4-Pin Test") },
                icon = { Icon(Icons.Default.Sensors, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTool) {
            "footing_resistance" -> FootingResistanceCalculator()
            "shielding_angle" -> ShieldingAngleCalculator()
            "backflashover" -> BackflashoverCalculator()
            "wenner_test" -> WennerTestCalculator()
        }
    }
}

@Composable
fun FootingResistanceCalculator() {
    var soilResStr by remember { mutableStateOf("250") } // ohm-m
    var numRodsStr by remember { mutableStateOf("4") }
    var rodLenStr by remember { mutableStateOf("3.05") } // 10 ft rod ~ 3.05 m
    var cpLenStr by remember { mutableStateOf("30") } // counterpoise meters
    var lightningKaStr by remember { mutableStateOf("40") } // kA stroke

    val soilRes = soilResStr.toDoubleOrNull() ?: 250.0
    val numRods = numRodsStr.toIntOrNull() ?: 4
    val rodLen = rodLenStr.toDoubleOrNull() ?: 3.05
    val cpLen = cpLenStr.toDoubleOrNull() ?: 30.0
    val lightningKa = lightningKaStr.toDoubleOrNull() ?: 40.0

    val result = remember(soilRes, numRods, rodLen, cpLen, lightningKa) {
        LightningGroundingEngine.calculateFootingResistance(
            soilResistivityOhmM = soilRes,
            numberOfRods = numRods,
            rodLengthMeters = rodLen,
            counterpoiseLengthMeters = cpLen,
            lightningStrokeCurrentKa = lightningKa
        )
    }

    Text(
        text = "Tower Footing Resistance & Counterpoise Simulator",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Computes 60Hz power frequency and lightning impulse grounding impedance with soil ionization per IEEE Std 80 / IEEE 142.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(12.dp))

    // Interactive Grounding Diagram
    GroundingTowerCanvas(
        rodsCount = numRods,
        lowFreqOhms = result.lowFrequencyResistanceOhms,
        impulseOhms = result.impulseSurgeResistanceOhms
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Soil Resistivity",
        value = soilResStr,
        onValueChange = { soilResStr = it },
        unit = "Ω·m",
        presetOptions = listOf("80 Ω·m (Good)" to "80", "250 Ω·m (Average)" to "250", "800 Ω·m (Rocky)" to "800")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Number of Driven Ground Rods",
        value = numRodsStr,
        onValueChange = { numRodsStr = it },
        unit = "rods",
        presetOptions = listOf("2 Rods" to "2", "4 Rods (Per Leg)" to "4", "8 Rods" to "8")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Counterpoise Buried Trench Wire Length",
        value = cpLenStr,
        onValueChange = { cpLenStr = it },
        unit = "meters",
        presetOptions = listOf("None (0m)" to "0", "30 m (100 ft)" to "30", "75 m (250 ft)" to "75")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Lightning Stroke Peak Current",
        value = lightningKaStr,
        onValueChange = { lightningKaStr = it },
        unit = "kA",
        presetOptions = listOf("30 kA (Median)" to "30", "40 kA (Standard)" to "40", "100 kA (Severe)" to "100")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Combined 60Hz Footing Resistance (R_f)",
        value = String.format("%.1f", result.lowFrequencyResistanceOhms),
        unit = "Ω",
        statusText = result.recommendation,
        isPassed = result.targetMet10Ohm,
        standardRef = "IEEE Std 80 Dwight Formula"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Surge Impulse Grounding Resistance (R_i)",
        value = String.format("%.1f", result.impulseSurgeResistanceOhms),
        unit = "Ω",
        statusText = "Soil ionization reduces impedance by ${String.format("%.0f", (1.0 - result.impulseSurgeResistanceOhms / result.lowFrequencyResistanceOhms) * 100.0)}% during ${lightningKa.toInt()} kA strike.",
        isPassed = result.impulseSurgeResistanceOhms <= 15.0,
        standardRef = "CIGRE TB 63 Soil Ionization"
    )
}

@Composable
fun ShieldingAngleCalculator() {
    var shieldHeightStr by remember { mutableStateOf("35.0") } // meters
    var phaseHeightStr by remember { mutableStateOf("26.0") } // meters
    var separationStr by remember { mutableStateOf("4.5") } // meters
    var targetCurrentStr by remember { mutableStateOf("15.0") } // kA

    val shH = shieldHeightStr.toDoubleOrNull() ?: 35.0
    val phH = phaseHeightStr.toDoubleOrNull() ?: 26.0
    val sep = separationStr.toDoubleOrNull() ?: 4.5
    val current = targetCurrentStr.toDoubleOrNull() ?: 15.0

    val result = remember(shH, phH, sep, current) {
        LightningGroundingEngine.calculateShieldingAngle(shH, phH, sep, current)
    }

    Text(
        text = "Shielding Failure & Strike Angle Analysis",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Electrogeometric Model (EGM Armstrong-Whitehead) for overhead shield wire (OHGW/OPGW) protection cone.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Shield Wire Height (H_s)",
        value = shieldHeightStr,
        onValueChange = { shieldHeightStr = it },
        unit = "m"
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Top Phase Conductor Height (H_p)",
        value = phaseHeightStr,
        onValueChange = { phaseHeightStr = it },
        unit = "m"
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Horizontal Separation (X_s)",
        value = separationStr,
        onValueChange = { separationStr = it },
        unit = "m",
        presetOptions = listOf("2.5 m (Tight)" to "2.5", "4.5 m (Standard)" to "4.5", "6.5 m (Wide)" to "6.5")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Shielding Angle (θ)",
        value = String.format("%.1f", result.shieldAngleDegrees),
        unit = "degrees",
        statusText = result.recommendation,
        isPassed = result.shieldingEffective,
        standardRef = "θ = arctan(Xs / ΔH) <= 30°"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Expected Shielding Failure Flashover Rate (SFFOR)",
        value = String.format("%.2f", result.expectedSfforPer100kmYr),
        unit = "per 100 km-yr",
        statusText = "Striking Distance r_s = ${String.format("%.1f", result.strikingDistanceMeters)} m for ${current.toInt()} kA stroke",
        isPassed = result.expectedSfforPer100kmYr <= 0.1,
        standardRef = "IEEE Std 1243 EGM"
    )
}

@Composable
fun BackflashoverCalculator() {
    var cfoStr by remember { mutableStateOf("1200") } // kV
    var footingRStr by remember { mutableStateOf("15") } // ohms
    var gfdStr by remember { mutableStateOf("4.0") } // flashes/km2/yr

    val cfo = cfoStr.toDoubleOrNull() ?: 1200.0
    val footingR = footingRStr.toDoubleOrNull() ?: 15.0
    val gfd = gfdStr.toDoubleOrNull() ?: 4.0

    val result = remember(cfo, footingR, gfd) {
        LightningGroundingEngine.calculateBackflashover(
            insulatorCfoKv = cfo,
            footingResistanceOhms = footingR,
            groundFlashDensityFlPerKm2Yr = gfd
        )
    }

    Text(
        text = "Critical Backflashover Current & Trip Rate",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Evaluates insulator string crossarm withstand and predicted lightning outage rate per IEEE Std 1243.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Insulator String CFO (Critical Flashover)",
        value = cfoStr,
        onValueChange = { cfoStr = it },
        unit = "kV",
        presetOptions = listOf("750 kV (115kV line)" to "750", "1200 kV (230kV line)" to "1200", "2200 kV (500kV line)" to "2200")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Tower Footing Resistance",
        value = footingRStr,
        onValueChange = { footingRStr = it },
        unit = "Ω",
        presetOptions = listOf("8 Ω (Low)" to "8", "15 Ω (Moderate)" to "15", "35 Ω (High)" to "35")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Ground Flash Density (GFD / N_g)",
        value = gfdStr,
        onValueChange = { gfdStr = it },
        unit = "flashes/km²/yr",
        presetOptions = listOf("1.5 (Low - West)" to "1.5", "4.0 (Midwest)" to "4.0", "10.0 (High - Florida/Gulf)" to "10.0")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Critical Flashover Stroke Current (I_c)",
        value = String.format("%.1f", result.criticalCurrentKa),
        unit = "kA",
        statusText = result.riskEvaluation,
        isPassed = result.criticalCurrentKa >= 100.0,
        standardRef = "I_c = CFO / ((1-K)·R + Zt/6)"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Predicted Lightning Outage Rate (BFOR)",
        value = String.format("%.2f", result.totalTripRatePer100kmYr),
        unit = "outages / 100 km-yr",
        statusText = "Target benchmark is typically < 1.0 outages per 100 km-yr on bulk transmission corridors.",
        isPassed = result.totalTripRatePer100kmYr <= 1.0,
        standardRef = "IEEE Std 1243 Anderson-Eriksson"
    )
}

@Composable
fun WennerTestCalculator() {
    var spacingStr by remember { mutableStateOf("5.0") } // meters
    var rMeasuredStr by remember { mutableStateOf("4.2") } // ohms

    val spacing = spacingStr.toDoubleOrNull() ?: 5.0
    val rMeas = rMeasuredStr.toDoubleOrNull() ?: 4.2

    val result = remember(spacing, rMeas) {
        LightningGroundingEngine.calculateWennerResistivity(spacing, rMeas)
    }

    Text(
        text = "Wenner 4-Point Soil Resistivity Profiler",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Apparent soil resistivity rho = 2 * pi * a * R per IEEE Std 81 four-pin array.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Pin Spacing (a)",
        value = spacingStr,
        onValueChange = { spacingStr = it },
        unit = "meters",
        presetOptions = listOf("2.0 m (Shallow)" to "2.0", "5.0 m (Medium)" to "5.0", "15.0 m (Deep bedrock)" to "15.0")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Measured Resistance (R)",
        value = rMeasuredStr,
        onValueChange = { rMeasuredStr = it },
        unit = "Ω"
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Apparent Soil Resistivity (ρ)",
        value = String.format("%.1f", result.apparentResistivityOhmM),
        unit = "Ω·m",
        statusText = "Soil Stratification: ${result.soilClassDescription}",
        isPassed = result.apparentResistivityOhmM <= 300.0,
        standardRef = "ρ = 2·π·a·R"
    )
}
