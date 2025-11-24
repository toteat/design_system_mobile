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
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChipButtonContainer(
    items: List<String>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    itemModifier: (itemText: String) -> Modifier = { Modifier }
) {
    var selectedItem by remember { mutableStateOf(items.firstOrNull()) }
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            val modifierForItem = itemModifier(item)
            ChipButton(
                text = item,
                isSelected = item == selectedItem,
                onClick = {
                    selectedItem = item
                    onItemSelected(item)
                },
                modifier = modifierForItem
            )
        }
    }
}

@Preview
@Composable
fun ChipButtonContainerPreview() {
    ToteatTheme{
        ChipButtonContainer(
            items = listOf("Salon", "Bar", "Terraza", "Vip", "los tres"),
            onItemSelected = {}
        )
    }
}