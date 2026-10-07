package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.model.UnitSystem
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

sealed class AppDestination {
    object Home : AppDestination()
    data class LineDesign(val toolId: String = "catenary") : AppDestination()
    data class Corrosion(val toolId: String = "zinc_life") : AppDestination()
    data class Inspection(val toolId: String = "defect_matrix") : AppDestination()
    data class Lightning(val toolId: String = "footing_resistance") : AppDestination()
    data class Conductor(val toolId: String = "conductor_db") : AppDestination()
    data class RowEnv(val toolId: String = "emf_profile") : AppDestination()
    data class Glossary(val categoryFilter: String? = null) : AppDestination()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OttApp()
                }
            }
        }
    }
}

@Composable
fun OttApp() {
    var currentScreen by remember { mutableStateOf<AppDestination>(AppDestination.Home) }
    var selectedCategoryIndex by remember { mutableStateOf(0) }
    var unitSystem by remember { mutableStateOf(UnitSystem.IMPERIAL) }

    when (val screen = currentScreen) {
        is AppDestination.Home -> {
            HomeScreen(
                selectedCategoryIndex = selectedCategoryIndex,
                onCategorySelected = { selectedCategoryIndex = it },
                onLaunchTool = { category, toolId ->
                    currentScreen = when (category) {
                        "line_design" -> AppDestination.LineDesign(toolId)
                        "corrosion" -> AppDestination.Corrosion(toolId)
                        "inspection" -> AppDestination.Inspection(toolId)
                        "lightning" -> AppDestination.Lightning(toolId)
                        "conductor" -> AppDestination.Conductor(toolId)
                        "row_env" -> AppDestination.RowEnv(toolId)
                        else -> AppDestination.Home
                    }
                },
                onOpenGlossary = { categoryFilter ->
                    currentScreen = AppDestination.Glossary(categoryFilter)
                },
                unitSystem = unitSystem,
                onToggleUnitSystem = { unitSystem = unitSystem.toggle() }
            )
        }

        is AppDestination.LineDesign -> {
            BackHandler { currentScreen = AppDestination.Home }
            LineDesignScreen(
                activeToolId = screen.toolId,
                onNavigateBack = { currentScreen = AppDestination.Home }
            )
        }

        is AppDestination.Corrosion -> {
            BackHandler { currentScreen = AppDestination.Home }
            CorrosionScreen(
                activeToolId = screen.toolId,
                onNavigateBack = { currentScreen = AppDestination.Home }
            )
        }

        is AppDestination.Inspection -> {
            BackHandler { currentScreen = AppDestination.Home }
            InspectionScreen(
                activeToolId = screen.toolId,
                onNavigateBack = { currentScreen = AppDestination.Home }
            )
        }

        is AppDestination.Lightning -> {
            BackHandler { currentScreen = AppDestination.Home }
            LightningGroundingScreen(
                activeToolId = screen.toolId,
                onNavigateBack = { currentScreen = AppDestination.Home }
            )
        }

        is AppDestination.Conductor -> {
            BackHandler { currentScreen = AppDestination.Home }
            ConductorHardwareScreen(
                activeToolId = screen.toolId,
                onNavigateBack = { currentScreen = AppDestination.Home }
            )
        }

        is AppDestination.RowEnv -> {
            BackHandler { currentScreen = AppDestination.Home }
            RowEnvironmentScreen(
                activeToolId = screen.toolId,
                onNavigateBack = { currentScreen = AppDestination.Home }
            )
        }

        is AppDestination.Glossary -> {
            BackHandler { currentScreen = AppDestination.Home }
            GlossaryScreen(
                initialCategory = screen.categoryFilter,
                onNavigateBack = { currentScreen = AppDestination.Home }
            )
        }
    }
}
