package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*

data class CategoryDefinition(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val tools: List<ToolDefinition>
)

data class ToolDefinition(
    val id: String,
    val title: String,
    val tag: String, // CALCULATOR, SIMULATION, DATABASE
    val description: String,
    val icon: ImageVector,
    val screenCategory: String
)

val APP_CATEGORIES = listOf(
    CategoryDefinition(
        id = "line_design",
        title = "Line Design",
        subtitle = "Catenary sag, ruling span, blowout sway & ampacity",
        icon = Icons.Default.Timeline,
        tools = listOf(
            ToolDefinition(
                id = "catenary",
                title = "Catenary Sag & Tension",
                tag = "CALCULATOR",
                description = "Level span sag, clearance to ground datum, and tension % RTS under NESC loading.",
                icon = Icons.Default.Timeline,
                screenCategory = "line_design"
            ),
            ToolDefinition(
                id = "ruling_span",
                title = "Ruling Span (Equivalent Span)",
                tag = "CALCULATOR",
                description = "Equivalent dead-end section tension and sag distribution across suspension spans.",
                icon = Icons.Default.Calculate,
                screenCategory = "line_design"
            ),
            ToolDefinition(
                id = "blowout",
                title = "Conductor Blowout & Sway",
                tag = "SIMULATION",
                description = "Transverse wind deflection angle, blowout arc, and right-of-way buffer clearance.",
                icon = Icons.Default.Air,
                screenCategory = "line_design"
            ),
            ToolDefinition(
                id = "ampacity",
                title = "IEEE 738 Thermal Ampacity",
                tag = "CALCULATOR",
                description = "Steady-state current rating based on convective, radiative, and solar heat balance.",
                icon = Icons.Default.Bolt,
                screenCategory = "line_design"
            )
        )
    ),
    CategoryDefinition(
        id = "corrosion",
        title = "Corrosion",
        subtitle = "Atmospheric zinc loss, soil anchors & AC interference",
        icon = Icons.Default.Shield,
        tools = listOf(
            ToolDefinition(
                id = "zinc_life",
                title = "Galvanizing Coating Life",
                tag = "CALCULATOR",
                description = "Predicts hot-dip zinc depletion rate and years to first maintenance per ISO 9223 (C1-CX).",
                icon = Icons.Default.Shield,
                screenCategory = "corrosion"
            ),
            ToolDefinition(
                id = "soil_anchor",
                title = "Soil Anchor & Foundation Loss",
                tag = "SIMULATION",
                description = "Romanoff underground pitting model for buried guy anchor rods and grillage steel.",
                icon = Icons.Default.Landscape,
                screenCategory = "corrosion"
            ),
            ToolDefinition(
                id = "ac_corrosion",
                title = "AC Interference & Corrosion Risk",
                tag = "CALCULATOR",
                description = "Evaluates AC current density discharge and pitting criteria per ISO 18086 / NACE SP0106.",
                icon = Icons.Default.ElectricBolt,
                screenCategory = "corrosion"
            ),
            ToolDefinition(
                id = "cp_anode",
                title = "Cathodic Protection Sizing",
                tag = "CALCULATOR",
                description = "Sacrificial magnesium and zinc anode mass requirements for guy anchors and grillages.",
                icon = Icons.Default.BatteryChargingFull,
                screenCategory = "corrosion"
            )
        )
    ),
    CategoryDefinition(
        id = "inspection",
        title = "Inspection & Assessment",
        subtitle = "Defect priority matrix, pole sounding & member buckling",
        icon = Icons.Default.AssignmentLate,
        tools = listOf(
            ToolDefinition(
                id = "defect_matrix",
                title = "Transmission Defect Priority (DSI)",
                tag = "SIMULATION",
                description = "EPRI / NESC structural, foundation, hardware, and insulator defect scoring with P1-P5 urgency.",
                icon = Icons.Default.AssignmentLate,
                screenCategory = "inspection"
            ),
            ToolDefinition(
                id = "wood_pole",
                title = "Wood Pole Shell Remaining Strength",
                tag = "CALCULATOR",
                description = "ASCE Manual 91 / ANSI O5.1 hollow cylinder section modulus for groundline heartwood decay.",
                icon = Icons.Default.Park,
                screenCategory = "inspection"
            ),
            ToolDefinition(
                id = "lattice_buckling",
                title = "Lattice Member Buckling Capacity",
                tag = "CALCULATOR",
                description = "ASCE 10 column slenderness ratio KL/r and allowable compressive axial capacity in kips.",
                icon = Icons.Default.Architecture,
                screenCategory = "inspection"
            )
        )
    ),
    CategoryDefinition(
        id = "lightning",
        title = "Lightning & Grounding",
        subtitle = "Footing impedance, shielding failure & backflashover",
        icon = Icons.Default.FlashOn,
        tools = listOf(
            ToolDefinition(
                id = "footing_resistance",
                title = "Footing Grounding & Counterpoise",
                tag = "SIMULATION",
                description = "IEEE 80 / 142 low frequency and high-current impulse impedance with soil ionization.",
                icon = Icons.Default.Bolt,
                screenCategory = "lightning"
            ),
            ToolDefinition(
                id = "shielding_angle",
                title = "Shielding Angle & Strike Protection",
                tag = "CALCULATOR",
                description = "Electrogeometric model (EGM) protection cone and shielding failure flashover rate (SFFOR).",
                icon = Icons.Default.Umbrella,
                screenCategory = "lightning"
            ),
            ToolDefinition(
                id = "backflashover",
                title = "Critical Backflashover & Outage Rate",
                tag = "CALCULATOR",
                description = "Calculates critical stroke current Ic and annual lightning outage trips per 100 km-yr.",
                icon = Icons.Default.FlashOn,
                screenCategory = "lightning"
            ),
            ToolDefinition(
                id = "wenner_test",
                title = "Wenner 4-Point Soil Profiler",
                tag = "CALCULATOR",
                description = "IEEE Std 81 apparent soil resistivity calculation and soil stratification classification.",
                icon = Icons.Default.Sensors,
                screenCategory = "lightning"
            )
        )
    ),
    CategoryDefinition(
        id = "conductor",
        title = "Conductor & Hardware",
        subtitle = "ACSR database, Aeolian dampers & insulator creepage",
        icon = Icons.Default.Dataset,
        tools = listOf(
            ToolDefinition(
                id = "conductor_db",
                title = "Conductor Database & Specs",
                tag = "DATABASE",
                description = "Standard ACSR, ACSS, and AAAC sizes (Hawk, Drake, Cardinal, Bluejay, Curlew, etc.).",
                icon = Icons.Default.Dataset,
                screenCategory = "conductor"
            ),
            ToolDefinition(
                id = "aeolian_vibration",
                title = "Aeolian Vibration & Damper Sizing",
                tag = "CALCULATOR",
                description = "Strouhal frequency and recommended Stockbridge damper placement distance from clamp mouth.",
                icon = Icons.Default.GraphicEq,
                screenCategory = "conductor"
            ),
            ToolDefinition(
                id = "insulator_string",
                title = "Insulator Creepage & Rating",
                tag = "CALCULATOR",
                description = "IEC 60815 pollution severity creepage distance and ANSI C29.2 mechanical safety factor.",
                icon = Icons.Default.Layers,
                screenCategory = "conductor"
            )
        )
    ),
    CategoryDefinition(
        id = "row_env",
        title = "Right-of-Way & Environment",
        subtitle = "Ground EMF profiler, ROW corridor width & corona noise",
        icon = Icons.Default.Waves,
        tools = listOf(
            ToolDefinition(
                id = "emf_profile",
                title = "EMF Ground Level Profiler",
                tag = "SIMULATION",
                description = "Calculates lateral Electric (kV/m) and Magnetic (mG) profiles against ICNIRP public limits.",
                icon = Icons.Default.Waves,
                screenCategory = "row_env"
            ),
            ToolDefinition(
                id = "row_width",
                title = "ROW Corridor Minimum Width",
                tag = "CALCULATOR",
                description = "NESC Rule 234 minimum corridor width considering structure base, blowout, and clearance.",
                icon = Icons.Default.Straighten,
                screenCategory = "row_env"
            ),
            ToolDefinition(
                id = "corona_noise",
                title = "Corona Loss & Audible Noise",
                tag = "CALCULATOR",
                description = "EPRI wet-conductor rain noise (dBA) and active corona loss (kW/km) under bundle conductor.",
                icon = Icons.Default.VolumeUp,
                screenCategory = "row_env"
            )
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    selectedCategoryIndex: Int,
    onCategorySelected: (Int) -> Unit,
    onLaunchTool: (category: String, toolId: String) -> Unit,
    onOpenGlossary: (categoryName: String?) -> Unit,
    unitSystem: UnitSystem,
    onToggleUnitSystem: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    val currentCategory = APP_CATEGORIES[selectedCategoryIndex.coerceIn(0, APP_CATEGORIES.lastIndex)]

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 10.dp)
                        ) {
                            Text(
                                text = "OTT",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Overhead Transmission Toolbox",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "High-Voltage Engineering Suite",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    // Unit Toggle Button
                    FilterChip(
                        selected = unitSystem == UnitSystem.METRIC,
                        onClick = onToggleUnitSystem,
                        label = { Text(if (unitSystem == UnitSystem.IMPERIAL) "US" else "SI") },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .testTag("unit_toggle_btn")
                    )

                    // Glossary Action
                    IconButton(
                        onClick = { onOpenGlossary(null) },
                        modifier = Modifier.testTag("top_bar_glossary_btn")
                    ) {
                        Icon(Icons.Default.MenuBook, contentDescription = "Glossary")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search Input Row
            PaddingValues(horizontal = 16.dp, vertical = 6.dp).let {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search all 21 calculators & standards...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = if (searchQuery.isNotEmpty()) {
                        {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("home_search_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            if (searchQuery.isNotEmpty()) {
                // Global Search Results View
                val matchingTools = remember(searchQuery) {
                    APP_CATEGORIES.flatMap { it.tools }.filter {
                        it.title.contains(searchQuery, ignoreCase = true) ||
                                it.description.contains(searchQuery, ignoreCase = true) ||
                                it.tag.contains(searchQuery, ignoreCase = true)
                    }
                }

                val matchingTerms = remember(searchQuery) {
                    GlossaryRepository.searchTerms(searchQuery)
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            text = "Matching Calculators & Simulations (${matchingTools.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    items(matchingTools) { tool ->
                        ToolTileCard(
                            title = tool.title,
                            tag = tool.tag,
                            description = tool.description,
                            icon = tool.icon,
                            onClick = { onLaunchTool(tool.screenCategory, tool.id) }
                        )
                    }

                    item {
                        Text(
                            text = "Matching Glossary Terms (${matchingTerms.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                    }

                    items(matchingTerms) { term ->
                        GlossaryCard(term = term)
                    }
                }
            } else {
                // Category Tabs Row
                ScrollableTabRow(
                    selectedTabIndex = selectedCategoryIndex,
                    edgePadding = 16.dp,
                    modifier = Modifier.testTag("category_tab_row")
                ) {
                    APP_CATEGORIES.forEachIndexed { index, cat ->
                        Tab(
                            selected = selectedCategoryIndex == index,
                            onClick = { onCategorySelected(index) },
                            text = { Text(cat.title) },
                            icon = { Icon(cat.icon, contentDescription = null, modifier = Modifier.size(18.dp)) },
                            modifier = Modifier.testTag("tab_${cat.id}")
                        )
                    }
                }

                // Category Tools List with Header and Glossary Shortcut
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 14.dp)
                ) {
                    // Category Hero Banner
                    item {
                        CategoryHeaderCard(
                            title = currentCategory.title,
                            subtitle = currentCategory.subtitle,
                            icon = currentCategory.icon,
                            colorAccent = when (currentCategory.id) {
                                "line_design" -> EpriBlue
                                "corrosion" -> EpriTeal
                                "inspection" -> EpriAmber
                                "lightning" -> EpriBlueLight
                                "conductor" -> EpriTealLight
                                else -> EpriAmberLight
                            },
                            onGlossaryClick = { onOpenGlossary(currentCategory.title) }
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Available Engineering Tools",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${currentCategory.tools.size} tools",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Tool Tiles
                    items(currentCategory.tools) { tool ->
                        ToolTileCard(
                            title = tool.title,
                            tag = tool.tag,
                            description = tool.description,
                            icon = tool.icon,
                            colorAccent = when (tool.tag) {
                                "CALCULATOR" -> EpriBlue
                                "SIMULATION" -> EpriTeal
                                else -> EpriAmber
                            },
                            onClick = { onLaunchTool(tool.screenCategory, tool.id) }
                        )
                    }

                    // Category Glossary Preview Section
                    item {
                        val categoryTerms = GlossaryRepository.termsForCategory(currentCategory.title)
                        if (categoryTerms.isNotEmpty()) {
                            Column(modifier = Modifier.padding(top = 10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${currentCategory.title} Key Terms",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    TextButton(onClick = { onOpenGlossary(currentCategory.title) }) {
                                        Text("View All (${categoryTerms.size})")
                                    }
                                }

                                categoryTerms.take(2).forEach { term ->
                                    GlossaryCard(
                                        term = term,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
