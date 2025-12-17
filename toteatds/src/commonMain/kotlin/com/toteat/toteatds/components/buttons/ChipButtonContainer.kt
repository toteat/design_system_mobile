package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Contenedor horizontal scrollable de chips buttons.
 *
 * @param items Lista de textos para los chips
 * @param selectedItem Item actualmente seleccionado (null si ninguno está seleccionado)
 * @param onItemSelected Callback cuando se selecciona un item
 * @param modifier Modificador para el contenedor
 * @param enabled Si los chips están habilitados o no
 * @param itemModifier Modificador personalizado para cada item basado en su texto
 */
@Composable
fun ChipButtonContainer(
    items: List<String>,
    selectedItem: String?,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    itemModifier: (itemText: String) -> Modifier = { Modifier }
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .semantics {
                contentDescription = "Lista de opciones seleccionables"
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            val modifierForItem = itemModifier(item)
            ChipButton(
                text = item,
                isSelected = item == selectedItem,
                onClick = {
                    onItemSelected(item)
                },
                enabled = enabled,
                modifier = modifierForItem
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ChipButtonContainerPreview() {
    ToteatTheme {
        var selectedItem by remember { mutableStateOf<String?>("Salon") }

        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estado normal con selección
            ChipButtonContainer(
                items = listOf("Salon", "Bar", "Terraza", "Vip", "Los Tres Chiflados"),
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )

            // Estado deshabilitado
            ChipButtonContainer(
                items = listOf("Salon", "Bar", "Terraza", "Vip"),
                selectedItem = "Bar",
                onItemSelected = {},
                enabled = false
            )
        }
    }
}