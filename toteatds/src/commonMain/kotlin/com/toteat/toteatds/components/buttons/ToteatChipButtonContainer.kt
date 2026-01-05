package com.toteat.toteatds.components.buttons
import com.toteat.toteatds.utils.setTestTag

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
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.chip_container_description
import org.jetbrains.compose.resources.stringResource
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
fun ToteatChipButtonContainer(
    items: List<String>,
    selectedItem: String?,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    testTag: String = "",
    itemModifier: (itemText: String) -> Modifier = { Modifier }
) {
    val containerDescription = stringResource(Res.string.chip_container_description)

    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .semantics {
                contentDescription = containerDescription
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            val modifierForItem = itemModifier(item)
            ToteatChipButton(
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
private fun ToteatChipButtonContainerPreview() {
    ToteatTheme {
        var selectedItem by remember { mutableStateOf<String?>("Salon") }

        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ToteatChipButtonContainer(
                items = listOf("Salon", "Bar", "Terraza", "Vip", "Los Tres Chiflados"),
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )

            ToteatChipButtonContainer(
                items = listOf("Salon", "Bar", "Terraza", "Vip"),
                selectedItem = "Bar",
                onItemSelected = {},
                enabled = false
            )
        }
    }
}