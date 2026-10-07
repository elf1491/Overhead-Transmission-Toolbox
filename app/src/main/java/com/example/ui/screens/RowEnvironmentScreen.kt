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
fun RowEnvironmentScreen(
    onNavigateBack: () -> Unit,
    activeToolId: String = "emf_profile",
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
                text = "Right-of-Way & Environment",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        ScrollableTabRow(
            selectedTabIndex = when (selectedTool) {
                "emf_profile" -> 0
                "row_width" -> 1
                "corona_noise" -> 2
                else -> 0
            },
            edgePadding = 0.dp
        ) {
            Tab(
                selected = selectedTool == "emf_profile",
                onClick = { selectedTool = "emf_profile" },
                text = { Text("EMF Profiler") },
                icon = { Icon(Icons.Default.Waves, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "row_width",
                onClick = { selectedTool = "row_width" },
                text = { Text("ROW Corridor Width") },
                icon = { Icon(Icons.Default.Straighten, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "corona_noise",
                onClick = { selectedTool = "corona_noise" },
                text = { Text("Corona & Noise") },
                icon = { Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTool) {
            "emf_profile" -> EmfProfileCalculator()
            "row_width" -> RowWidthCalculator()
            "corona_noise" -> CoronaNoiseCalculator()
        }
    }
}

@Composable
fun EmfProfileCalculator() {
    var voltageStr by remember { mutableStateOf("230") } // kV
    var currentStr by remember { mutableStateOf("800") } // Amps
    var clearanceStr by remember { mutableStateOf("35") } // ft
    var spacingStr by remember { mutableStateOf("20") } // ft
    var rowWidthStr by remember { mutableStateOf("125") } // ft

    val v = voltageStr.toDoubleOrNull() ?: 230.0
    val i = currentStr.toDoubleOrNull() ?: 800.0
    val cl = clearanceStr.toDoubleOrNull() ?: 35.0
    val sp = spacingStr.toDoubleOrNull() ?: 20.0
    val rowW = rowWidthStr.toDoubleOrNull() ?: 125.0

    val result = remember(v, i, cl, sp, rowW) {
        RowEnvironmentalEngine.calculateEmfProfile(v, i, cl, sp, rowW)
    }

    Text(
        text = "Electric & Magnetic Field (EMF) Ground Profiler",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Evaluates lateral Electric Field (kV/m) and Magnetic Flux Density (mG) 1 meter above ground per IEEE Std 644 and ICNIRP public guidelines.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Operating Line Voltage",
        value = voltageStr,
        onValueChange = { voltageStr = it },
        unit = "kV",
        presetOptions = listOf("115 kV" to "115", "230 kV" to "230", "500 kV" to "500")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Balanced Line Current",
        value = currentStr,
        onValueChange = { currentStr = it },
        unit = "Amperes",
        presetOptions = listOf("400 A" to "400", "800 A" to "800", "1500 A" to "1500")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Conductor Minimum Clearance Above Ground",
        value = clearanceStr,
        onValueChange = { clearanceStr = it },
        unit = "ft"
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Total Right-of-Way Width",
        value = rowWidthStr,
        onValueChange = { rowWidthStr = it },
        unit = "ft",
        presetOptions = listOf("100 ft" to "100", "125 ft" to "125", "200 ft" to "200")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Peak Electric Field (E-Field)",
        value = String.format("%.2f", result.maxElectricFieldKvM),
        unit = "kV / m",
        statusText = "Edge of ROW: ${String.format("%.2f", result.edgeOfRowElectricFieldKvM)} kV/m (ICNIRP Public Threshold: 5.0 kV/m)",
        isPassed = result.icnirpLimitPassed,
        standardRef = "IEEE Std 644 / ICNIRP"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Peak Magnetic Flux Density (B-Field)",
        value = String.format("%.1f", result.maxMagneticFieldMG),
        unit = "milliGauss (mG)",
        statusText = "Edge of ROW: ${String.format("%.1f", result.edgeOfRowMagneticFieldMG)} mG (${String.format("%.2f", result.edgeOfRowMagneticFieldMG / 10.0)} µT). ${result.evaluationNotes}",
        isPassed = result.icnirpLimitPassed,
        standardRef = "ICNIRP 2000 mG (200 µT)"
    )
}

@Composable
fun RowWidthCalculator() {
    var voltageStr by remember { mutableStateOf("230") } // kV
    var baseWidthStr by remember { mutableStateOf("25") } // ft
    var sagStr by remember { mutableStateOf("22") } // ft
    var windPsfStr by remember { mutableStateOf("6") } // psf

    val v = voltageStr.toDoubleOrNull() ?: 230.0
    val base = baseWidthStr.toDoubleOrNull() ?: 25.0
    val sag = sagStr.toDoubleOrNull() ?: 22.0
    val wind = windPsfStr.toDoubleOrNull() ?: 6.0

    val result = remember(v, base, sag, wind) {
        RowEnvironmentalEngine.calculateRowWidth(v, base, 800.0, sag, wind)
    }

    Text(
        text = "Right-of-Way Minimum Corridor Width",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Calculates minimum legal corridor width per NESC Rule 234 Table 234-1 under conductor blowout sway.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Operating Line Voltage",
        value = voltageStr,
        onValueChange = { voltageStr = it },
        unit = "kV"
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Tower Footprint / Crossarm Width",
        value = baseWidthStr,
        onValueChange = { baseWidthStr = it },
        unit = "ft"
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Maximum Mid-Span Conductor Sag",
        value = sagStr,
        onValueChange = { sagStr = it },
        unit = "ft"
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Total Minimum Recommended ROW Width",
        value = "${result.totalRecommendedRowWidthFt.toInt()}",
        unit = "ft",
        statusText = "Comprises: Structure ${result.structureBaseWidthFt.toInt()}ft + 2×(Blowout ${String.format("%.1f", result.maxConductorBlowoutFt)}ft + Electrical Clearance ${String.format("%.1f", result.electricalClearanceBufferFt)}ft + Danger Tree Buffer ${result.vegetationDangerTreeBufferFt.toInt()}ft)",
        isPassed = true,
        standardRef = result.nescRuleCitation
    )
}

@Composable
fun CoronaNoiseCalculator() {
    var voltageStr by remember { mutableStateOf("500") } // kV
    var bundleCountStr by remember { mutableStateOf("3") }
    var diameterCmStr by remember { mutableStateOf("2.81") } // cm

    val v = voltageStr.toDoubleOrNull() ?: 500.0
    val bundle = bundleCountStr.toIntOrNull() ?: 3
    val diam = diameterCmStr.toDoubleOrNull() ?: 2.81

    val result = remember(v, bundle, diam) {
        RowEnvironmentalEngine.calculateCoronaNoise(v, bundle, diam)
    }

    Text(
        text = "Corona Loss & Audible Noise (EPRI)",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Estimates wet-conductor rain crackle noise and active corona power loss per EPRI Transmission Line Reference Book.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "System Voltage",
        value = voltageStr,
        onValueChange = { voltageStr = it },
        unit = "kV",
        presetOptions = listOf("230 kV" to "230", "345 kV" to "345", "500 kV" to "500")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Subconductors per Phase Bundle",
        value = bundleCountStr,
        onValueChange = { bundleCountStr = it },
        unit = "conductors",
        presetOptions = listOf("1 (Single)" to "1", "2 (Twin Bundle)" to "2", "3 (Tri-Bundle)" to "3", "4 (Quad)" to "4")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Wet Conductor Rain Audible Noise",
        value = String.format("%.1f", result.wetRainAudibleNoisedBa),
        unit = "dBA @ ROW",
        statusText = if (result.epriStandardPassed) "Complies with EPRI residential corridor guideline (<= 53-58 dBA)" else "Exceeds standard target; consider larger subconductor diameter or bundled configuration",
        isPassed = result.epriStandardPassed,
        standardRef = "EPRI Reference Book Chapter 6"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Corona Power Loss (Heavy Rain)",
        value = String.format("%.1f", result.coronaLossKwPerKm),
        unit = "kW / km",
        statusText = "Surface Voltage Gradient: ${String.format("%.1f", result.surfaceGradientKvPerCm)} kV_rms/cm",
        isPassed = true,
        standardRef = "EPRI Formula"
    )
}
