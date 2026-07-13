package com.toteat.toteatds.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.diner_consumption_checkbox_description
import designsystemmobile.toteatds.generated.resources.diner_consumption_collapse
import designsystemmobile.toteatds.generated.resources.diner_consumption_description
import designsystemmobile.toteatds.generated.resources.diner_consumption_expand
import designsystemmobile.toteatds.generated.resources.diner_consumption_list_description
import designsystemmobile.toteatds.generated.resources.diner_consumption_total
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val HeaderHeight = 56.dp
private val ItemRowHeight = 48.dp
private val CheckboxSize = 24.dp
private val CheckboxShape = RoundedCornerShape(6.dp)
private val DividerThickness = 2.dp
private val HorizontalPadding = 16.dp

@Immutable
data class DinerConsumptionItem(
    val name: String,
    val price: String
)

@Immutable
data class DinerConsumption(
    val name: String,
    val items: ImmutableList<DinerConsumptionItem>,
    val total: String,
    val isChecked: Boolean = false
)

/**
 * Selectable, expandable per-diner consumption list.
 *
 * Renders one [ToteatDinerConsumptionCard] per entry in [diners]. Each card
 * shows a checkbox, the diner name and a chevron; tapping the header expands
 * the card to reveal the consumed items with their prices and a highlighted
 * total. Checkbox changes are reported through [onCheckedChange] with the
 * diner's index. Backed by a [LazyColumn], so long lists scroll vertically;
 * when embedded inside another vertical scrollable, give it a bounded height.
 */
@Composable
fun ToteatDinerConsumptionList(
    diners: ImmutableList<DinerConsumption>,
    onCheckedChange: (index: Int, checked: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    val listDescription = stringResource(Res.string.diner_consumption_list_description)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .semantics { contentDescription = listDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        itemsIndexed(
            items = diners,
            // Index-based key: diner names may repeat
            key = { index, _ -> "diner_consumption_$index" }
        ) { index, diner ->
            ToteatDinerConsumptionCard(
                consumption = diner,
                onCheckedChange = { checked -> onCheckedChange(index, checked) },
                testTag = if (testTag.isNotEmpty()) "${testTag}_card_$index" else ""
            )
        }
    }
}

/**
 * Single expandable card with a diner's consumption.
 *
 * Collapsed it shows a checkbox, the diner name and a chevron over a light
 * gray pill-cornered surface. Tapping the header toggles the expanded state,
 * revealing one row per [DinerConsumption.items] entry plus a total row where
 * the amount is highlighted with the primary color.
 */
@Composable
fun ToteatDinerConsumptionCard(
    consumption: DinerConsumption,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    initiallyExpanded: Boolean = false,
    testTag: String = ""
) {
    var isExpanded by remember { mutableStateOf(initiallyExpanded) }
    val cardDescription = stringResource(Res.string.diner_consumption_description, consumption.name)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .semantics { contentDescription = cardDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        DinerConsumptionHeader(
            name = consumption.name,
            checked = consumption.isChecked,
            isExpanded = isExpanded,
            onCheckedChange = onCheckedChange,
            onToggleExpanded = { isExpanded = !isExpanded },
            testTag = testTag
        )

        if (isExpanded) {
            consumption.items.forEachIndexed { index, item ->
                key(item.name, index) {
                    if (index > 0) {
                        HorizontalDivider(
                            thickness = DividerThickness,
                            color = MaterialTheme.colorScheme.extended.neutral100
                        )
                    }
                    DinerConsumptionRow(
                        name = item.name,
                        price = item.price,
                        isTotal = false,
                        testTag = if (testTag.isNotEmpty()) "${testTag}_item_$index" else ""
                    )
                }
            }

            if (consumption.items.isNotEmpty()) {
                HorizontalDivider(
                    thickness = DividerThickness,
                    color = MaterialTheme.colorScheme.extended.neutral100
                )
            }

            DinerConsumptionRow(
                name = stringResource(Res.string.diner_consumption_total),
                price = consumption.total,
                isTotal = true,
                testTag = if (testTag.isNotEmpty()) "${testTag}_total" else ""
            )
        }
    }
}

@Composable
private fun DinerConsumptionHeader(
    name: String,
    checked: Boolean,
    isExpanded: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onToggleExpanded: () -> Unit,
    testTag: String
) {
    val toggleDescription = stringResource(
        if (isExpanded) Res.string.diner_consumption_collapse else Res.string.diner_consumption_expand
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(HeaderHeight)
            .background(MaterialTheme.colorScheme.extended.neutral100)
            .clickable(role = Role.Button, onClickLabel = toggleDescription) { onToggleExpanded() }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_header") else Modifier)
            .padding(start = 4.dp, end = HorizontalPadding)
    ) {
        DinerConsumptionCheckbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            dinerName = name,
            testTag = if (testTag.isNotEmpty()) "${testTag}_checkbox" else ""
        )

        Text(
            text = name,
            style = MaterialTheme.typography.bodyLargeRegular,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = toggleDescription,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun DinerConsumptionCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    dinerName: String,
    testTag: String
) {
    val description = stringResource(Res.string.diner_consumption_checkbox_description, dinerName)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .minimumInteractiveComponentSize()
            .toggleable(
                value = checked,
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            )
            .semantics { contentDescription = description }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(CheckboxSize)
                .clip(CheckboxShape)
                .then(
                    if (checked) Modifier.background(MaterialTheme.colorScheme.secondary)
                    else Modifier.border(1.5.dp, MaterialTheme.colorScheme.outline, CheckboxShape)
                )
        ) {
            if (checked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun DinerConsumptionRow(
    name: String,
    price: String,
    isTotal: Boolean,
    testTag: String
) {
    val extended = MaterialTheme.colorScheme.extended
    val nameColor = if (isTotal) MaterialTheme.colorScheme.secondary else extended.neutral400
    val priceColor = if (isTotal) MaterialTheme.colorScheme.primary else extended.neutral400
    val fontWeight = if (isTotal) FontWeight.Bold else null

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(ItemRowHeight)
            .background(MaterialTheme.colorScheme.background)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .padding(horizontal = HorizontalPadding)
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyLargeRegular,
            fontWeight = fontWeight,
            color = nameColor
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLargeRegular,
            fontWeight = fontWeight,
            color = nameColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = price,
            style = MaterialTheme.typography.bodyLargeRegular,
            fontWeight = fontWeight,
            color = priceColor
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAFAFA)
@Composable
private fun ToteatDinerConsumptionListPreview() {
    ToteatTheme {
        ToteatDinerConsumptionList(
            diners = persistentListOf(
                DinerConsumption(
                    name = "Pauli",
                    items = persistentListOf(
                        DinerConsumptionItem(name = "Hamburguesa BBQ", price = "$9.000"),
                        DinerConsumptionItem(name = "Mojito tradicional", price = "$7.000")
                    ),
                    total = "$16.000",
                    isChecked = true
                ),
                DinerConsumption(
                    name = "Trini",
                    items = persistentListOf(
                        DinerConsumptionItem(name = "Lasagna vegetariana", price = "$8.500")
                    ),
                    total = "$8.500"
                ),
                DinerConsumption(
                    name = "Vale",
                    items = persistentListOf(),
                    total = "$0"
                )
            ),
            onCheckedChange = { _, _ -> },
            modifier = Modifier.padding(16.dp)
        )
    }
}
