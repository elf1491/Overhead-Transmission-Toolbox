package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.model.GlossaryRepository
import com.example.ui.components.GlossaryCard

@Composable
fun GlossaryScreen(
    onNavigateBack: () -> Unit,
    initialCategory: String? = null,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(initialCategory ?: "All") }

    val categories = listOf(
        "All",
        "Line Design",
        "Corrosion",
        "Inspection and Assessment",
        "Lightning and Grounding",
        "Conductor and Hardware",
        "Right-of-Way and Environment"
    )

    val terms = remember(searchQuery, selectedCategory) {
        val baseList = if (selectedCategory == "All") GlossaryRepository.allTerms
        else GlossaryRepository.termsForCategory(selectedCategory)

        if (searchQuery.isBlank()) baseList
        else baseList.filter {
            it.term.contains(searchQuery, ignoreCase = true) ||
                    it.definition.contains(searchQuery, ignoreCase = true) ||
                    it.standardRef.contains(searchQuery, ignoreCase = true) ||
                    it.practicalEngineeringContext.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back to toolbox")
            }
            Text(
                text = "Engineering Glossary",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search transmission terms, formulas, standards...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = if (searchQuery.isNotEmpty()) {
                {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear search")
                    }
                }
            } else null,
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Category Filter Chips
        ScrollableTabRow(
            selectedTabIndex = categories.indexOf(selectedCategory).coerceAtLeast(0),
            edgePadding = 0.dp
        ) {
            categories.forEach { cat ->
                Tab(
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = cat },
                    text = { Text(if (cat == "All") "All Categories" else cat) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "${terms.size} Standards Definitions (IEEE, NESC, ASCE, CIGRE, ISO)",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(terms, key = { it.id }) { term ->
                GlossaryCard(term = term)
            }
        }
    }
}
