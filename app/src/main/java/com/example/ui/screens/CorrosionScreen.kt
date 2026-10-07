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
fun CorrosionScreen(
    onNavigateBack: () -> Unit,
    activeToolId: String = "zinc_life",
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
                text = "Corrosion Tools",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        ScrollableTabRow(
            selectedTabIndex = when (selectedTool) {
                "zinc_life" -> 0
                "soil_anchor" -> 1
                "ac_corrosion" -> 2
                "cp_anode" -> 3
                else -> 0
            },
            edgePadding = 0.dp
        ) {
            Tab(
                selected = selectedTool == "zinc_life",
                onClick = { selectedTool = "zinc_life" },
                text = { Text("Zinc Galvanizing") },
                icon = { Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "soil_anchor",
                onClick = { selectedTool = "soil_anchor" },
                text = { Text("Soil Anchor Loss") },
                icon = { Icon(Icons.Default.Landscape, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "ac_corrosion",
                onClick = { selectedTool = "ac_corrosion" },
                text = { Text("AC Interference") },
                icon = { Icon(Icons.Default.ElectricBolt, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTool == "cp_anode",
                onClick = { selectedTool = "cp_anode" },
                text = { Text("Cathodic Protection") },
                icon = { Icon(Icons.Default.BatteryChargingFull, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTool) {
            "zinc_life" -> ZincLifeCalculator()
            "soil_anchor" -> SoilAnchorCalculator()
            "ac_corrosion" -> AcCorrosionCalculator()
            "cp_anode" -> CathodicProtectionCalculator()
        }
    }
}

@Composable
fun ZincLifeCalculator() {
    var coatingThicknessStr by remember { mutableStateOf("85") } // 85 um ~ ASTM A123
    var selectedCategory by remember { mutableStateOf(IsoCorrosivityCategory.C3) }

    val thickness = coatingThicknessStr.toDoubleOrNull() ?: 85.0
    val result = remember(thickness, selectedCategory) {
        CorrosionEngine.calculateGalvanizingLife(thickness, selectedCategory)
    }

    Text(
        text = "Atmospheric Galvanizing Life Estimator",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Predicts hot-dip zinc depletion rate and service life per ISO 9223 / ASTM A123",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Galvanizing Coating Thickness",
        value = coatingThicknessStr,
        onValueChange = { coatingThicknessStr = it },
        unit = "µm",
        presetOptions = listOf("65 µm (Light)" to "65", "85 µm (ASTM A123 Std)" to "85", "125 µm (Heavy)" to "125")
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Select ISO 9223 Corrosivity Environment:",
        style = MaterialTheme.typography.labelMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
    )

    Spacer(modifier = Modifier.height(6.dp))

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        IsoCorrosivityCategory.values().forEach { cat ->
            FilterChip(
                selected = selectedCategory == cat,
                onClick = { selectedCategory = cat },
                label = {
                    Column {
                        Text("${cat.code} (${cat.typicalZincLossUmPerYr} µm/yr)")
                        Text(cat.environmentExample, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Years to 5% Rust / Steel Exposure",
        value = String.format("%.1f", result.estimatedYearsToSteelExposure),
        unit = "Years",
        statusText = result.maintenanceRecommendation,
        isPassed = result.estimatedYearsToSteelExposure >= 25.0,
        standardRef = "ISO 9223 / ASTM A123"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Equivalent Coating Weight",
        value = String.format("%.2f", result.coatingWeightOzPerSqFt),
        unit = "oz / ft²",
        standardRef = "ASTM A90 Weigh-Strip"
    )
}

@Composable
fun SoilAnchorCalculator() {
    var diameterStr by remember { mutableStateOf("1.0") } // 1.0" guy anchor rod
    var resistivityStr by remember { mutableStateOf("35") } // ohm-m
    var phStr by remember { mutableStateOf("6.2") }
    var yearsStr by remember { mutableStateOf("40") }

    val diam = diameterStr.toDoubleOrNull() ?: 1.0
    val res = resistivityStr.toDoubleOrNull() ?: 35.0
    val ph = phStr.toDoubleOrNull() ?: 6.2
    val yrs = yearsStr.toDoubleOrNull() ?: 40.0

    val result = remember(diam, res, ph, yrs) {
        CorrosionEngine.calculateAnchorCorrosion(diam, res, ph, yrs)
    }

    Text(
        text = "Soil Resistivity & Anchor Rod Degradation",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Romanoff underground pitting model for buried carbon steel guy anchors and grillage footings.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Initial Rod Diameter",
        value = diameterStr,
        onValueChange = { diameterStr = it },
        unit = "in",
        presetOptions = listOf("0.75\" Rod" to "0.75", "1.00\" Rod" to "1.00", "1.25\" Heavy" to "1.25")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Soil Resistivity",
        value = resistivityStr,
        onValueChange = { resistivityStr = it },
        unit = "Ω·m",
        presetOptions = listOf("15 Ω·m (Very Aggressive)" to "15", "35 Ω·m (Moderate)" to "35", "100 Ω·m (Mild)" to "100")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Soil pH Level",
        value = phStr,
        onValueChange = { phStr = it },
        presetOptions = listOf("4.5 (Acidic)" to "4.5", "6.5 (Neutral)" to "6.5", "8.5 (Alkaline)" to "8.5")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "In-Service Exposure Period",
        value = yearsStr,
        onValueChange = { yearsStr = it },
        unit = "years"
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Remaining Rod Diameter",
        value = String.format("%.2f", result.remainingDiameterInches),
        unit = "inches",
        statusText = "Loss: ${String.format("%.1f", result.projectedLossMilsAtDesignLife)} mils (${String.format("%.1f", 100.0 - result.remainingAreaPercent)}% area loss)",
        isPassed = result.remainingAreaPercent >= 65.0,
        standardRef = "Romanoff P = k·t^0.55"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Residual Tensile Capacity",
        value = String.format("%.0f", result.remainingTensileCapacityLbs),
        unit = "lbf",
        statusText = "Corrosion Risk Rating: ${result.corrosionRiskRating}",
        isPassed = result.remainingAreaPercent >= 65.0,
        standardRef = "Grade 60 Steel Anchor"
    )
}

@Composable
fun AcCorrosionCalculator() {
    var acVoltStr by remember { mutableStateOf("18.0") } // Vac
    var soilResStr by remember { mutableStateOf("40.0") } // ohm-m
    var holidayDiamStr by remember { mutableStateOf("11.28") } // mm (1 cm2 circular holiday)

    val acVolt = acVoltStr.toDoubleOrNull() ?: 18.0
    val soilRes = soilResStr.toDoubleOrNull() ?: 40.0
    val holidayDiam = holidayDiamStr.toDoubleOrNull() ?: 11.28

    val result = remember(acVolt, soilRes, holidayDiam) {
        CorrosionEngine.calculateAcInterference(acVolt, soilRes, holidayDiam)
    }

    Text(
        text = "AC Induced Interference & Corrosion Risk",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Evaluates AC current density discharge on co-located metallic pipelines and utilities per ISO 18086 / NACE SP0106",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Induced AC Voltage on Utility",
        value = acVoltStr,
        onValueChange = { acVoltStr = it },
        unit = "V_ac",
        presetOptions = listOf("8 V (Safe)" to "8", "18 V (Elevated)" to "18", "45 V (Severe)" to "45")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Local Soil Resistivity",
        value = soilResStr,
        onValueChange = { soilResStr = it },
        unit = "Ω·m"
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Coating Holiday Defect Diameter",
        value = holidayDiamStr,
        onValueChange = { holidayDiamStr = it },
        unit = "mm",
        presetOptions = listOf("5.6 mm (0.25 cm²)" to "5.64", "11.3 mm (1 cm² standard)" to "11.28")
    )

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "AC Current Density (J_ac)",
        value = String.format("%.1f", result.acCurrentDensityAmpsPerM2),
        unit = "A / m²",
        statusText = "${result.riskClassification} — ${result.recommendedAction}",
        isPassed = !result.requiresMitigation,
        standardRef = "J_ac = (8·V_ac) / (π·ρ·d)"
    )
}

@Composable
fun CathodicProtectionCalculator() {
    var areaStr by remember { mutableStateOf("150") } // sq ft
    var currentDensityStr by remember { mutableStateOf("2.0") } // mA/sq ft
    var designLifeStr by remember { mutableStateOf("30") } // years
    var selectedAnodeType by remember { mutableStateOf("Magnesium (H-1)") }

    val area = areaStr.toDoubleOrNull() ?: 150.0
    val cd = currentDensityStr.toDoubleOrNull() ?: 2.0
    val life = designLifeStr.toDoubleOrNull() ?: 30.0

    val result = remember(area, cd, life, selectedAnodeType) {
        CorrosionEngine.calculateCathodicProtection(area, cd, life, selectedAnodeType)
    }

    Text(
        text = "Cathodic Protection Sacrificial Anode Sizing",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = "Sizing galvanic magnesium or zinc sacrificial anodes for grillage foundations and guy anchor assemblies.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(14.dp))

    EngineeringInputField(
        label = "Buried Steel Surface Area",
        value = areaStr,
        onValueChange = { areaStr = it },
        unit = "sq ft",
        presetOptions = listOf("80 sq ft (Single Guy Anchor)" to "80", "150 sq ft (Grillage Footing)" to "150", "300 sq ft (4 Legs)" to "300")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Protective Current Density Requirement",
        value = currentDensityStr,
        onValueChange = { currentDensityStr = it },
        unit = "mA / sq ft",
        presetOptions = listOf("1.0 mA (Passive Soil)" to "1.0", "2.0 mA (Moderate Soil)" to "2.0", "4.0 mA (Hot Corrosive)" to "4.0")
    )

    Spacer(modifier = Modifier.height(8.dp))

    EngineeringInputField(
        label = "Design Protection Life",
        value = designLifeStr,
        onValueChange = { designLifeStr = it },
        unit = "years",
        presetOptions = listOf("20 Years" to "20", "30 Years" to "30", "50 Years" to "50")
    )

    Spacer(modifier = Modifier.height(12.dp))

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
            selected = selectedAnodeType == "Magnesium (H-1)",
            onClick = { selectedAnodeType = "Magnesium (H-1)" },
            label = { Text("Magnesium (High Potential)") }
        )
        FilterChip(
            selected = selectedAnodeType == "Zinc (ASTM B418)",
            onClick = { selectedAnodeType = "Zinc (ASTM B418)" },
            label = { Text("Zinc (Standard)") }
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    MetricResultBox(
        label = "Recommended Anodes Count",
        value = "${result.numberOfAnodes}",
        unit = "Units",
        statusText = "Total Mass Required: ${String.format("%.1f", result.totalAnodeMassKg)} kg (${String.format("%.0f", result.totalAnodeMassKg * 2.20462)} lbs) of ${result.anodeType}",
        isPassed = true,
        standardRef = "NACE SP0169 CP Guidelines"
    )

    Spacer(modifier = Modifier.height(8.dp))

    MetricResultBox(
        label = "Total Protective Current Required",
        value = String.format("%.3f", result.requiredCurrentAmps),
        unit = "Amperes",
        standardRef = "I = Area · Density"
    )
}
