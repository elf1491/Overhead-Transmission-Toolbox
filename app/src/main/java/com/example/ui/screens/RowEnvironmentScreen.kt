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
                "nerc_mvcd" -> 3
                "faa_obstruction" -> 4
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
            Tab(
                selected = selectedTool == "nerc_mvcd",
                onClick = { selectedTool = "nerc_mvcd" },
                text = { Text("NERC FAC-003 MVCD") },
                icon = { Icon(Icons.Default.Park, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "faa_obstruction",
                onClick = { selectedTool = "faa_obstruction" },
                text = { Text("FAA Tower Marking") },
                icon = { Icon(Icons.Default.FlightTakeoff, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTool) {
            "emf_profile" -> EmfProfileCalculator()
            "row_width" -> RowWidthCalculator()
            "corona_noise" -> CoronaNoiseCalculator()
            "nerc_mvcd" -> NercMvcdCalculator()
            "faa_obstruction" -> FaaObstructionCalculator()
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

@Composable
fun NercMvcdCalculator() {
    var voltageStr by remember { mutableStateOf("230") } // kV
    var transientFactorStr by remember { mutableStateOf("2.4") }
    var altitudeStr by remember { mutableStateOf("1500") } // ft
    var windBufferStr by remember { mutableStateOf("3.0") } // ft
    var observedDistanceStr by remember { mutableStateOf("12.0") } // ft

    val kv = voltageStr.toDoubleOrNull() ?: 230.0
    val t = transientFactorStr.toDoubleOrNull() ?: 2.4
    val alt = altitudeStr.toDoubleOrNull() ?: 1500.0
    val windBuf = windBufferStr.toDoubleOrNull() ?: 3.0
    val obsDist = observedDistanceStr.toDoubleOrNull() ?: 12.0

    val result = remember(kv, t, alt, windBuf, obsDist) {
        NercMvcdEngine.calculateMvcd(
            lineVoltageKv = kv,
            transientFactorT = t,
            altitudeFt = alt,
            windSwayBufferFt = windBuf,
            observedVegetationClearanceFt = obsDist
        )
    }

    Text(
        text = "NERC FAC-003-4 Minimum Vegetation Clearance (MVCD)",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Calculates mandatory federal regulatory vegetation clearance to prevent conductor-to-tree flashover under switching surges per NERC FAC-003 Table 2 & Gallet equation.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Line Voltage (Phase-to-Phase)",
        value = voltageStr,
        onValueChange = { voltageStr = it },
        unit = "kV",
        presetOptions = listOf("69 kV" to "69", "115 kV" to "115", "230 kV" to "230", "345 kV" to "345", "500 kV" to "500")
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        EngineeringInputField(
            label = "Transient Overvoltage Factor (T)",
            value = transientFactorStr,
            onValueChange = { transientFactorStr = it },
            presetOptions = listOf("2.0" to "2.0", "2.4 (Std)" to "2.4", "3.0" to "3.0"),
            modifier = Modifier.weight(1f)
        )
        EngineeringInputField(
            label = "Elevation Above Sea Level",
            value = altitudeStr,
            onValueChange = { altitudeStr = it },
            unit = "ft",
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        EngineeringInputField(
            label = "Conductor Wind Sway Buffer",
            value = windBufferStr,
            onValueChange = { windBufferStr = it },
            unit = "ft",
            modifier = Modifier.weight(1f)
        )
        EngineeringInputField(
            label = "Field Observed Tree Clearance",
            value = observedDistanceStr,
            onValueChange = { observedDistanceStr = it },
            unit = "ft",
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    val isCompliant = obsDist >= result.totalMvcdRequiredFt

    MetricResultBox(
        label = "Mandatory Total MVCD Required",
        value = String.format("%.2f", result.totalMvcdRequiredFt),
        unit = "ft (${String.format("%.2f", result.totalMvcdRequiredMeters)} m)",
        statusText = result.complianceStatus,
        isPassed = isCompliant,
        standardRef = result.nercStandardCitation
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Gallet Electrical Flashover Distance",
        value = String.format("%.2f", result.electricalClearanceMvcdFt),
        unit = "ft (Air Gap)",
        statusText = "Altitude Factor A: ${String.format("%.2f", result.altitudeCorrectionFactorA)} | Wind Buffer: ${result.windSwayBufferFt} ft",
        isPassed = true,
        standardRef = "V_peak = 3400 / (1 + 8/D) · A"
    )

    Spacer(modifier = Modifier.height(12.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "NERC FAC-003 Regulatory Compliance Note:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Under NERC Standard FAC-003-4, any sustained vegetation contact or encroachment within the MVCD triggers a reportable Category 1 (grow-in) or Category 2 (fall-in) event with severe federal enforcement penalties. Routine lidar inspections must be maintained.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun FaaObstructionCalculator() {
    var structureHeightStr by remember { mutableStateOf("160") } // ft AGL
    var proximityStr by remember { mutableStateOf("15000") } // ft to runway
    var isInstrumentRunway by remember { mutableStateOf(true) }
    var isWaterCrossing by remember { mutableStateOf(false) }
    var spanLengthStr by remember { mutableStateOf("1200") } // ft

    val h = structureHeightStr.toDoubleOrNull() ?: 160.0
    val dist = proximityStr.toDoubleOrNull() ?: 15000.0
    val span = spanLengthStr.toDoubleOrNull() ?: 1200.0

    val result = remember(h, dist, isInstrumentRunway, isWaterCrossing, span) {
        FaaObstructionEngine.evaluateFaaObstruction(
            structureHeightAglFt = h,
            proximityToRunwayFt = dist,
            isInstrumentRunway = isInstrumentRunway,
            isWaterOrCanyonCrossing = isWaterCrossing,
            spanLengthFt = span
        )
    }

    Text(
        text = "FAA Part 77 Tower Marking & Lighting Evaluator",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Evaluates FAA Advisory Circular AC 70/7460-1M airspace hazards, aviation orange paint banding, catenary marker balls, and obstruction beacon requirements.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        EngineeringInputField(
            label = "Structure Height (AGL)",
            value = structureHeightStr,
            onValueChange = { structureHeightStr = it },
            unit = "ft",
            presetOptions = listOf("120 ft" to "120", "180 ft" to "180", "220 ft (>200)" to "220"),
            modifier = Modifier.weight(1f)
        )
        EngineeringInputField(
            label = "Distance to Nearest Runway",
            value = proximityStr,
            onValueChange = { proximityStr = it },
            unit = "ft",
            presetOptions = listOf("8,000 ft" to "8000", "15,000 ft" to "15000", "25,000 ft" to "25000"),
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Airport Runway Type:", style = MaterialTheme.typography.bodyMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
            Text(
                if (isInstrumentRunway) "Instrument Runway (>3,200 ft length): 100:1 imaginary slope" else "Visual / Short Runway (<= 3,200 ft): 50:1 imaginary slope",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = isInstrumentRunway,
            onCheckedChange = { isInstrumentRunway = it }
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Navigable Waterway / River Crossing:", style = MaterialTheme.typography.bodyMedium, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
            Text(
                "Mandatory 36-inch spherical marker balls on shield wire/OPGW",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = isWaterCrossing,
            onCheckedChange = { isWaterCrossing = it }
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "FAA Airspace Hazard Assessment",
        value = if (result.isObstructionTriggered) "FAA NOTICE REQUIRED" else "BELOW HAZARD THRESHOLD",
        unit = "",
        statusText = if (result.isObstructionTriggered) "Obstruction triggered: Structure (${h} ft) penetrates FAA Part 77 slope limit (${String.format("%.1f", result.slopeLimitHeightFt)} ft) or exceeds 200 ft AGL." else "Safe: Structure height is below slope surface (${String.format("%.1f", result.slopeLimitHeightFt)} ft) and under 200 ft AGL.",
        isPassed = !result.isObstructionTriggered,
        standardRef = result.faaCitation
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Aviation Marking & Paint Bands",
        value = if (result.requiresAviationMarkingPaint) "${result.paintBandsCount} Alternating Bands" else "Standard Galvanized",
        unit = if (result.requiresAviationMarkingPaint) "(${String.format("%.1f", result.bandHeightFt)} ft per band)" else "(No paint needed)",
        statusText = if (result.requiresAviationMarkingPaint) "Requires 7 alternating aviation orange and white equal-width bands per AC 70/7460-1M Chap 3." else "Standard non-painted lattice tower or weathered pole permissible.",
        isPassed = true,
        standardRef = "AC 70/7460-1M Chapter 3"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Catenary Marker Balls & Lighting",
        value = if (result.requiresCatenaryMarkerBalls) "36\" Marker Balls Required" else "No Catenary Balls",
        unit = if (result.requiresCatenaryMarkerBalls) "@ ${result.markerBallSpacingFt.toInt()} ft spacing" else "",
        statusText = "Lighting: ${result.lightingSpecification}",
        isPassed = true,
        standardRef = "AC 70/7460-1M Chapter 12"
    )
}
