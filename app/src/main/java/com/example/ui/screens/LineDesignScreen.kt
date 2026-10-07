package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import com.example.ui.theme.*

@Composable
fun LineDesignScreen(
    onNavigateBack: () -> Unit,
    activeToolId: String = "catenary",
    modifier: Modifier = Modifier
) {
    var selectedTool by remember { mutableStateOf(activeToolId) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back to toolbox")
            }
            Text(
                text = "Line Design Tools",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Tool Selector Tabs
        ScrollableTabRow(
            selectedTabIndex = when (selectedTool) {
                "catenary" -> 0
                "ruling_span" -> 1
                "blowout" -> 2
                "ampacity" -> 3
                else -> 0
            },
            edgePadding = 0.dp
        ) {
            Tab(
                selected = selectedTool == "catenary",
                onClick = { selectedTool = "catenary" },
                text = { Text("Catenary Sag") },
                icon = { Icon(Icons.Default.Timeline, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "ruling_span",
                onClick = { selectedTool = "ruling_span" },
                text = { Text("Ruling Span") },
                icon = { Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "blowout",
                onClick = { selectedTool = "blowout" },
                text = { Text("Blowout / Sway") },
                icon = { Icon(Icons.Default.Air, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "ampacity",
                onClick = { selectedTool = "ampacity" },
                text = { Text("IEEE 738 Ampacity") },
                icon = { Icon(Icons.Default.Bolt, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTool) {
            "catenary" -> CatenaryCalculator()
            "ruling_span" -> RulingSpanCalculator()
            "blowout" -> BlowoutCalculator()
            "ampacity" -> AmpacityCalculator()
        }
    }
}

@Composable
fun CatenaryCalculator() {
    var spanStr by remember { mutableStateOf("800") }
    var weightStr by remember { mutableStateOf("1.094") } // Drake ACSR lb/ft
    var tensionStr by remember { mutableStateOf("6300") } // ~20% of Drake RTS
    var heightStr by remember { mutableStateOf("65") } // ft
    var rbsStr by remember { mutableStateOf("31500") } // Drake RTS

    val span = spanStr.toDoubleOrNull() ?: 800.0
    val weight = weightStr.toDoubleOrNull() ?: 1.094
    val tension = tensionStr.toDoubleOrNull() ?: 6300.0
    val height = heightStr.toDoubleOrNull() ?: 65.0
    val rbs = rbsStr.toDoubleOrNull() ?: 31500.0

    val result = remember(span, weight, tension, height, rbs) {
        LineDesignEngine.calculateCatenary(span, weight, tension, height, rbs)
    }

    Text(
        text = "Catenary Sag & Tension Calculator",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Hyperbolic level span profile per IEEE 738 & NESC Section 25",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(12.dp))

    // Interactive Profile Diagram
    CatenaryProfileCanvas(
        spanLength = span,
        sag = result.sag,
        supportHeight = height,
        minClearance = result.minGroundClearance
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Inputs
    EngineeringInputField(
        label = "Span Length",
        value = spanStr,
        onValueChange = { spanStr = it },
        unit = "ft",
        presetOptions = listOf("400 ft" to "400", "800 ft" to "800", "1200 ft" to "1200")
    )

    Spacer(modifier = Modifier.height(10.dp))

    EngineeringInputField(
        label = "Conductor Weight per Unit Length",
        value = weightStr,
        onValueChange = { weightStr = it },
        unit = "lb/ft",
        presetOptions = listOf("Drake (1.094)" to "1.094", "Hawk (0.655)" to "0.655", "Cardinal (1.226)" to "1.226")
    )

    Spacer(modifier = Modifier.height(10.dp))

    EngineeringInputField(
        label = "Horizontal Tension (H)",
        value = tensionStr,
        onValueChange = { tensionStr = it },
        unit = "lbf",
        presetOptions = listOf("15% RTS" to "4725", "20% RTS" to "6300", "25% RTS" to "7875")
    )

    Spacer(modifier = Modifier.height(10.dp))

    EngineeringInputField(
        label = "Attachment Height above Datum",
        value = heightStr,
        onValueChange = { heightStr = it },
        unit = "ft"
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Results
    Text(
        text = "Catenary Analysis Results",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Mid-Span Maximum Sag (S)",
        value = String.format("%.2f", result.sag),
        unit = "ft",
        standardRef = "S = C·[cosh(L/2C) - 1]"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Ground Clearance at Mid-Span",
        value = String.format("%.2f", result.minGroundClearance),
        unit = "ft",
        statusText = if (result.minGroundClearance >= 25.0) "Passes NESC Grade B standard clearance (>25 ft)" else "Warning: Clearance below 25 ft threshold",
        isPassed = result.minGroundClearance >= 25.0,
        standardRef = "NESC Table 232-1"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Maximum Tension at Support",
        value = String.format("%.0f", result.maxTension),
        unit = "lbf",
        statusText = "Tension is ${String.format("%.1f", result.tensionPercentRts)}% of Rated Breaking Strength (RBS)",
        isPassed = result.tensionPercentRts <= 25.0,
        standardRef = "T_max = H + w·S"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Catenary Parameter (C = H / w)",
        value = String.format("%.1f", result.catenaryParameterC),
        unit = "ft",
        standardRef = "Curvature Constant"
    )
}

@Composable
fun RulingSpanCalculator() {
    var spanListStr by remember { mutableStateOf("650, 800, 920, 750, 840, 1100") }

    val spans = remember(spanListStr) {
        spanListStr.split(",")
            .mapNotNull { it.trim().toDoubleOrNull() }
            .filter { it > 0 }
    }

    val rulingSpan = remember(spans) {
        LineDesignEngine.calculateRulingSpan(spans)
    }

    Text(
        text = "Ruling Span (Equivalent Span) Calculator",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Calculates equivalent dead-end section span where suspension insulator swing equalizes tension.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    OutlinedTextField(
        value = spanListStr,
        onValueChange = { spanListStr = it },
        label = { Text("Span Lengths in Dead-End Section (ft, comma-separated)") },
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SuggestionChip(
            onClick = { spanListStr = "500, 600, 750, 700, 550" },
            label = { Text("Suburban Section") }
        )
        SuggestionChip(
            onClick = { spanListStr = "800, 950, 1100, 1300, 900" },
            label = { Text("Mountain Valley") }
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Calculated Ruling Span (S_r)",
        value = String.format("%.1f", rulingSpan),
        unit = "ft",
        statusText = "Represents ${spans.size} tension-equalized spans (Total section length: ${spans.sum().toInt()} ft)",
        isPassed = true,
        standardRef = "S_r = √[Σ(S_i³) / Σ(S_i)]"
    )

    Spacer(modifier = Modifier.height(12.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "Engineering Guidelines:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "• Sag in any individual span S_i is calculated as: Sag_i = Sag_ruling * (S_i / S_r)^2.\n" +
                        "• Long spans (>1.5 * S_r) or short spans (<0.5 * S_r) can cause severe insulator swing off vertical; consider dead-ending isolated atypical spans.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun BlowoutCalculator() {
    var diameterStr by remember { mutableStateOf("1.108") } // Drake inches
    var weightStr by remember { mutableStateOf("1.094") } // Drake lb/ft
    var windSpeedStr by remember { mutableStateOf("60") } // mph
    var sagStr by remember { mutableStateOf("22") } // ft
    var iceStr by remember { mutableStateOf("0.0") } // inches

    val diam = diameterStr.toDoubleOrNull() ?: 1.108
    val weight = weightStr.toDoubleOrNull() ?: 1.094
    val wind = windSpeedStr.toDoubleOrNull() ?: 60.0
    val sag = sagStr.toDoubleOrNull() ?: 22.0
    val ice = iceStr.toDoubleOrNull() ?: 0.0

    val result = remember(diam, weight, wind, sag, ice) {
        LineDesignEngine.calculateBlowout(diam, weight, wind, sag, ice)
    }

    Text(
        text = "Conductor Blowout & Sway Simulator",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Calculates transverse wind deflection angle and horizontal mid-span displacement.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(12.dp))

    BlowoutArcCanvas(
        blowoutAngleDeg = result.blowoutAngleDegrees,
        displacementFt = result.horizontalDisplacementFt,
        windForce = result.windForcePerFt
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Conductor Diameter",
        value = diameterStr,
        onValueChange = { diameterStr = it },
        unit = "in",
        presetOptions = listOf("Drake (1.108\")" to "1.108", "Hawk (0.858\")" to "0.858", "Cardinal (1.196\")" to "1.196")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Wind Speed",
        value = windSpeedStr,
        onValueChange = { windSpeedStr = it },
        unit = "mph",
        presetOptions = listOf("40 mph (4 psf)" to "39.5", "60 mph (9 psf)" to "59.3", "90 mph (Extreme)" to "90")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Mid-Span Sag at Wind Temperature",
        value = sagStr,
        onValueChange = { sagStr = it },
        unit = "ft"
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Radial Ice Thickness",
        value = iceStr,
        onValueChange = { iceStr = it },
        unit = "in",
        presetOptions = listOf("Bare (0\")" to "0.0", "Medium (0.25\")" to "0.25", "Heavy (0.50\")" to "0.50")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Conductor Blowout Angle (θ)",
        value = String.format("%.1f", result.blowoutAngleDegrees),
        unit = "deg",
        standardRef = "θ = arctan(F_wind / W_vert)"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Horizontal Conductor Displacement",
        value = String.format("%.2f", result.horizontalDisplacementFt),
        unit = "ft",
        statusText = "Requires minimum ROW width clearance margin of at least ${String.format("%.1f", result.horizontalDisplacementFt + 10.0)} ft from centerline.",
        isPassed = result.blowoutAngleDegrees < 45.0,
        standardRef = "Displacement = Sag · sin(θ)"
    )
}

@Composable
fun AmpacityCalculator() {
    var maxTempStr by remember { mutableStateOf("75") } // °C
    var ambientTempStr by remember { mutableStateOf("35") } // °C
    var windSpeedStr by remember { mutableStateOf("0.61") } // m/s (2 ft/s)
    var solarStr by remember { mutableStateOf("1000") } // W/m2

    val maxTemp = maxTempStr.toDoubleOrNull() ?: 75.0
    val ambTemp = ambientTempStr.toDoubleOrNull() ?: 35.0
    val wind = windSpeedStr.toDoubleOrNull() ?: 0.61
    val solar = solarStr.toDoubleOrNull() ?: 1000.0

    val result = remember(maxTemp, ambTemp, wind, solar) {
        LineDesignEngine.calculateAmpacityIeee738(
            conductorDiameterMm = 28.1, // Drake
            conductorResistance75cOhmPerKm = 0.072,
            maxAllowableTempC = maxTemp,
            ambientTempC = ambTemp,
            windSpeedMetersPerSec = wind,
            solarIrradianceWPerM2 = solar
        )
    }

    Text(
        text = "IEEE Std 738 Steady-State Thermal Ampacity",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Standard heat balance: q_convection + q_radiation = I²·R + q_solar for Drake 795 ACSR",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Max Conductor Operating Temperature",
        value = maxTempStr,
        onValueChange = { maxTempStr = it },
        unit = "°C",
        presetOptions = listOf("75°C (Standard ACSR)" to "75", "100°C (Emergency)" to "100", "200°C (ACSS)" to "200")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Ambient Temperature",
        value = ambientTempStr,
        onValueChange = { ambientTempStr = it },
        unit = "°C",
        presetOptions = listOf("25°C (Moderate)" to "25", "35°C (Summer Peak)" to "35", "40°C (Extreme Heat)" to "40")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Wind Speed (Perpendicular)",
        value = windSpeedStr,
        onValueChange = { windSpeedStr = it },
        unit = "m/s",
        presetOptions = listOf("0.61 m/s (2 ft/s standard)" to "0.61", "1.5 m/s (Light Breeze)" to "1.5", "0.2 m/s (Stagnant Air)" to "0.2")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Allowable Steady-State Ampacity",
        value = String.format("%.0f", result.ampacityAmperes),
        unit = "Amperes",
        statusText = "3-Phase 230kV Capacity: ${String.format("%.1f", result.mvaRating3Phase230kV)} MVA | 500kV: ${String.format("%.1f", result.mvaRating3Phase500kV)} MVA",
        isPassed = true,
        standardRef = "IEEE Std 738-2012"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Convective Heat Dissipation (q_c)",
        value = String.format("%.2f", result.convectionLossWPerM),
        unit = "W/m",
        standardRef = "Forced Convection"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Radiative Heat Dissipation (q_r)",
        value = String.format("%.2f", result.radiationLossWPerM),
        unit = "W/m",
        standardRef = "Stefan-Boltzmann qr = π·D·ε·σ·(Tc⁴ - Ta⁴)"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Solar Heat Absorption Gain (q_s)",
        value = String.format("%.2f", result.solarGainWPerM),
        unit = "W/m",
        standardRef = "qs = α·Qs·D"
    )
}
