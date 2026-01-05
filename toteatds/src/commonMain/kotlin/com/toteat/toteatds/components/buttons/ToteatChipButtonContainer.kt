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
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.chip_container_description
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatChipButtonContainer(
    items: ImmutableList<String>,
    selectedItem: String?,
    onItemSelect: (String) -> Unit,
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
                    onItemSelect(item)
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
                items = persistentListOf("Salon", "Bar", "Terraza", "Vip", "Los Tres Chiflados"),
                selectedItem = selectedItem,
                onItemSelect = { selectedItem = it }
            )

            ToteatChipButtonContainer(
                items = persistentListOf("Salon", "Bar", "Terraza", "Vip"),
                selectedItem = "Bar",
                onItemSelect = {},
                enabled = false
            )
        }
    }
}