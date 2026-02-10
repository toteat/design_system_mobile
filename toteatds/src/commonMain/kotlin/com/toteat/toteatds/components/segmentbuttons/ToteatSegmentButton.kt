package com.toteat.toteatds.components.segmentbuttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.segmented_tab_item
import designsystemmobile.toteatds.generated.resources.segmented_tab_not_selected
import designsystemmobile.toteatds.generated.resources.segmented_tab_selected
import designsystemmobile.toteatds.generated.resources.segmented_tabs_description
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A segmented tab component that displays a selection of options.
 *
 * @param items The list of strings to display as tab titles.
 * @param selectedIndex The index of the currently selected tab.
 * @param onTabSelect A callback invoked when a new tab is selected.
 * @param modifier The modifier to be applied to the component.
 * @param testTag The test tag for UI testing.
 */
@Composable
fun SegmentedTabs(
    items: ImmutableList<String>,
    selectedIndex: Int,
    onTabSelect: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    val cornerRadius = 8.dp
    val borderColor = MaterialTheme.colorScheme.extended.neutral400
    
    val tabsDescription = stringResource(Res.string.segmented_tabs_description)
    val selectedText = stringResource(Res.string.segmented_tab_selected)
    val notSelectedText = stringResource(Res.string.segmented_tab_not_selected)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .border(
                border = BorderStroke(1.dp, borderColor),
                shape = RoundedCornerShape(cornerRadius)
            )
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .semantics {
                contentDescription = tabsDescription
            }
    ) {
        items.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex
            val tabDescription = stringResource(Res.string.segmented_tab_item, title, index + 1, items.size)

            val backgroundColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background
            val textColor = if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.secondary

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(backgroundColor)
                    .clickable(onClick = { onTabSelect(index) })
                    .semantics {
                        role = Role.Tab
                        contentDescription = tabDescription
                        stateDescription = if (isSelected) selectedText else notSelectedText
                        selected = isSelected
                    }
                    .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_tab_${index}") else Modifier),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            if (index < items.size - 1) {
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(borderColor)
                )
            }
        }
    }
}

@Preview
@Composable
private fun SegmentedTabsPreview() {
    ToteatTheme {
        var selectedIndex by remember { mutableIntStateOf(0) }
        val tabs = persistentListOf("Resumen cuenta", "Información mesa")
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SegmentedTabs(
                items = tabs,
                selectedIndex = selectedIndex,
                onTabSelect = { selectedIndex = it }
            )
            
            Text(
                text = "Tab seleccionada: ${tabs[selectedIndex]}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview
@Composable
private fun SegmentedTabsThreeItemsPreview() {
    ToteatTheme {
        var selectedIndex by remember { mutableIntStateOf(1) }
        val tabs = persistentListOf("Opción 1", "Opción 2", "Opción 3")
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            SegmentedTabs(
                items = tabs,
                selectedIndex = selectedIndex,
                onTabSelect = { selectedIndex = it }
            )
        }
    }
}
