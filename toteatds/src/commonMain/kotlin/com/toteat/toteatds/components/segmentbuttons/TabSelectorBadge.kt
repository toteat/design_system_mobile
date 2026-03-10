package com.toteat.toteatds.components.segmentbuttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.segmented_tab_item
import designsystemmobile.toteatds.generated.resources.segmented_tab_not_selected
import designsystemmobile.toteatds.generated.resources.segmented_tab_selected
import designsystemmobile.toteatds.generated.resources.tab_selector_badge_count
import designsystemmobile.toteatds.generated.resources.tab_selector_badge_description
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Immutable
data class TabSelectorBadgeItem(
    val title: String,
    val badgeCount: Int? = null
)

enum class TabSelectorBadgeVariant {
    DARK,
    CREAM,
    GRAY,
    PRIMARY
}

@Immutable
private data class TabSelectorBadgeColors(
    val selectedContainer: androidx.compose.ui.graphics.Color,
    val selectedContent: androidx.compose.ui.graphics.Color
)

@Composable
private fun tabSelectorBadgeColors(variant: TabSelectorBadgeVariant): TabSelectorBadgeColors {
    return when (variant) {
        TabSelectorBadgeVariant.DARK -> TabSelectorBadgeColors(
            selectedContainer = MaterialTheme.colorScheme.secondary,
            selectedContent = MaterialTheme.colorScheme.onSecondary
        )
        TabSelectorBadgeVariant.CREAM -> TabSelectorBadgeColors(
            selectedContainer = MaterialTheme.colorScheme.tertiary,
            selectedContent = MaterialTheme.colorScheme.onTertiary
        )
        TabSelectorBadgeVariant.GRAY -> TabSelectorBadgeColors(
            selectedContainer = NeutralGray200,
            selectedContent = MaterialTheme.colorScheme.secondary
        )
        TabSelectorBadgeVariant.PRIMARY -> TabSelectorBadgeColors(
            selectedContainer = MaterialTheme.colorScheme.primary,
            selectedContent = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun TabSelectorBadge(
    items: ImmutableList<TabSelectorBadgeItem>,
    selectedIndex: Int,
    onTabSelect: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    variant: TabSelectorBadgeVariant = TabSelectorBadgeVariant.DARK,
    enabled: Boolean = true,
    testTag: String = ""
) {
    if (items.isEmpty()) return

    val safeSelectedIndex = selectedIndex.coerceIn(0, items.lastIndex)
    val colors = tabSelectorBadgeColors(variant)
    val tabsDescription = stringResource(Res.string.tab_selector_badge_description)
    val selectedText = stringResource(Res.string.segmented_tab_selected)
    val notSelectedText = stringResource(Res.string.segmented_tab_not_selected)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
            .border(width = 1.dp, color = NeutralGray200, shape = CircleShape)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .semantics { contentDescription = tabsDescription }
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == safeSelectedIndex
            val tabDescription = stringResource(
                Res.string.segmented_tab_item,
                item.title,
                index + 1,
                items.size
            )
            val badgeDescription = item.badgeCount
                ?.takeIf { it > 0 }
                ?.let { pluralStringResource(Res.plurals.tab_selector_badge_count, it, it) }
                .orEmpty()
            val fullDescription = if (badgeDescription.isEmpty()) {
                tabDescription
            } else {
                "$tabDescription, $badgeDescription"
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) colors.selectedContainer else MaterialTheme.colorScheme.background
                    )
                    .clickable(enabled = enabled, onClick = { onTabSelect(index) })
                    .semantics {
                        role = Role.Tab
                        contentDescription = fullDescription
                        stateDescription = if (isSelected) selectedText else notSelectedText
                        selected = isSelected
                    }
                    .then(
                        if (testTag.isNotEmpty()) {
                            Modifier.setTestTag("${testTag}_tab_${index}")
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) colors.selectedContent else MaterialTheme.colorScheme.extended.neutral500
                    )

                    if (item.badgeCount != null && item.badgeCount > 0) {
                        BadgeCount(
                            count = item.badgeCount,
                            testTag = if (testTag.isNotEmpty()) "${testTag}_tab_${index}_badge" else ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BadgeCount(
    count: Int,
    testTag: String = ""
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .defaultMinSize(minWidth = 28.dp, minHeight = 28.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun TabSelectorBadgePreview() {
    ToteatTheme {
        var selectedIndex by remember { mutableIntStateOf(1) }
        val items = persistentListOf(
            TabSelectorBadgeItem("Carta"),
            TabSelectorBadgeItem("Pedido", badgeCount = 4)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TabSelectorBadge(
                items = items,
                selectedIndex = selectedIndex,
                onTabSelect = { selectedIndex = it }
            )

            TabSelectorBadge(
                items = items,
                selectedIndex = 0,
                onTabSelect = {},
                variant = TabSelectorBadgeVariant.CREAM
            )

            TabSelectorBadge(
                items = items,
                selectedIndex = 0,
                onTabSelect = {},
                variant = TabSelectorBadgeVariant.GRAY
            )

            TabSelectorBadge(
                items = items,
                selectedIndex = 0,
                onTabSelect = {},
                variant = TabSelectorBadgeVariant.PRIMARY
            )
        }
    }

}
