package com.toteat.designsystemmobile

import com.toteat.toteatds.components.PrimaryButton
import com.toteat.toteatds.components.SecondaryButton
import com.toteat.toteatds.components.TertiaryButton
import com.toteat.toteatds.theme.ToteatTheme


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

// Data class para representar cada sección de componentes en nuestra lista
data class ComponentShowcaseItem(
    val title: String,
    var isExpanded: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

    ToteatTheme() {
        val componentList = remember {
            mutableStateListOf(
                ComponentShowcaseItem(title = "Buttons", isExpanded = true), // Inicia expandido
                ComponentShowcaseItem(title = "Inputs"),
                ComponentShowcaseItem(title = "Cards")
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Design System Showcase") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },

            ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(componentList) { index, item ->
                    ComponentShowcaseSection(
                        item = item,

                        onClick = {
                            // Al hacer clic, creamos una copia del item con el estado 'isExpanded' invertido
                            componentList[index] = item.copy(isExpanded = !item.isExpanded)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ComponentShowcaseSection(
    item: ComponentShowcaseItem,
    onClick: () -> Unit
) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background), border = BorderStroke(1.dp,MaterialTheme.colorScheme.outline), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column {
            // Cabecera de la sección (ej. "Buttons")
            SectionHeader(
                title = item.title,
                isExpanded = item.isExpanded,
                onClick = onClick
            )

            // Contenido expandible
            AnimatedVisibility(visible = item.isExpanded) {
                // Aquí decidimos qué componentes mostrar según el título de la sección
                when (item.title) {
                    "Buttons" -> ButtonShowcase()
                    else -> Text(
                        text = "Componentes próximamente...",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, isExpanded: Boolean, onClick: () -> Unit) {
    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Expand section",
            modifier = Modifier.rotate(rotationAngle)
        )
    }
}

@Composable
fun ButtonShowcase() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // --- Primary Buttons ---
        Text("Primary", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryButton(onClick = {}, text = "Default")
            PrimaryButton(onClick = {}, text = "Disabled", enabled = false)
        }
        Divider()

        // --- Secondary Buttons ---
        Text("Secondary", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SecondaryButton(onClick = {}, text = "Default", leadingIcon = { Icon(Icons.Default.Add, null) })
            SecondaryButton(onClick = {}, text = "Disabled", enabled = false, leadingIcon = { Icon(Icons.Default.Add, null) })
        }
        Divider()

        // --- Tertiary Buttons ---
        Text("Tertiary", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TertiaryButton(onClick = {}, text = "Default", leadingIcon = { Icon(Icons.Default.RocketLaunch, null) })
            TertiaryButton(onClick = {}, text = "Disabled", enabled = false, leadingIcon = { Icon(Icons.Default.RocketLaunch, null) })
        }
    }
}