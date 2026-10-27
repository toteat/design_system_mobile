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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.components.tags.StatusTag
import com.toteat.toteatds.components.tags.StatusTagVariant
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.theme.bodyMediumRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.diner_consumption_checkbox_description
import designsystemmobile.toteatds.generated.resources.diner_consumption_collapse
import designsystemmobile.toteatds.generated.resources.diner_consumption_description
import designsystemmobile.toteatds.generated.resources.diner_consumption_description_with_status
import designsystemmobile.toteatds.generated.resources.diner_consumption_expand
import designsystemmobile.toteatds.generated.resources.diner_consumption_list_description
import designsystemmobile.toteatds.generated.resources.diner_consumption_total
import designsystemmobile.toteatds.generated.resources.icon_right_chevron
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val HeaderHeight = 56.dp
private val ItemRowHeight = 48.dp
// Figma "comensal - genérico" icon/ checkbox: a 14.dp icon whose box is inset 0.875.dp (12.25.dp),
// with a 2.625.dp radius, a ~1.dp border and a check about 7.5.dp wide.
private val CheckboxSize = 14.dp
private val CheckboxInset = 0.875.dp
private val CheckboxShape = RoundedCornerShape(2.625.dp)
private val CheckboxBorderWidth = 1.dp
private val CheckIconSize = 10.dp
// Figma icon/ chevron-strokes: the DS right chevron turned to point down or up.
private val ChevronSize = 16.dp
private val DividerThickness = 2.dp
private val HorizontalPadding = 16.dp

/** How a consumption row is painted. [Discount] rows are dimmed, as in Figma "comensal - genérico". */
enum class DinerConsumptionItemKind { Regular, Discount }

@Immutable
data class DinerConsumptionExtra(
    val name: String,
    val price: String
)

/**
 * @param quantity Already formatted quantity (e.g. "6"), shown after the name as "x6". `null`
 * (default) shows nothing.
 * @param extras Shown under the product, indented and in a smaller gray style, each with its price
 * aligned to the right.
 */
@Immutable
data class DinerConsumptionItem(
    val name: String,
    val price: String,
    val kind: DinerConsumptionItemKind = DinerConsumptionItemKind.Regular,
    val quantity: String? = null,
    val extras: ImmutableList<DinerConsumptionExtra> = persistentListOf()
)

/**
 * @param statusLabel Short, already translated text shown in the header between the name and the
 * chevron (e.g. "Sin deuda"). `null` (default) shows nothing.
 * @param isSelectable With `false` the checkbox is shown disabled and ignores taps; the header
 * still expands and collapses the card so the consumption can be checked.
 */
@Immutable
data class DinerConsumption(
    val name: String,
    val items: ImmutableList<DinerConsumptionItem>,
    val total: String,
    val isChecked: Boolean = false,
    val statusLabel: String? = null,
    val isSelectable: Boolean = true
)

/**
 * Selectable, expandable per-diner consumption list.
 *
 * Renders one [ToteatDinerConsumptionCard] per entry in [diners]. Each card
 * shows a checkbox, the diner name and a chevron; tapping the header expands
 * the card to reveal the consumed items with their prices and a highlighted
 * total. Checkbox changes are reported through [onCheckedChange] with the
 * diner's index; diners with [DinerConsumption.isSelectable] `false` never report them. Backed by a [LazyColumn], so long lists scroll vertically;
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
    val statusLabel = consumption.statusLabel
    val cardDescription = if (statusLabel != null) {
        stringResource(Res.string.diner_consumption_description_with_status, consumption.name, statusLabel)
    } else {
        stringResource(Res.string.diner_consumption_description, consumption.name)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .semantics { contentDescription = cardDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        DinerConsumptionHeader(
            name = consumption.name,
            statusLabel = statusLabel,
            checked = consumption.isChecked,
            isSelectable = consumption.isSelectable,
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
                    val isDiscount = item.kind == DinerConsumptionItemKind.Discount
                    val itemTestTag = if (testTag.isNotEmpty()) "${testTag}_item_$index" else ""
                    DinerConsumptionRow(
                        name = item.name,
                        price = item.price,
                        isTotal = false,
                        isDiscount = isDiscount,
                        quantity = item.quantity,
                        testTag = itemTestTag
                    )
                    if (item.extras.isNotEmpty()) {
                        DinerConsumptionExtras(
                            extras = item.extras,
                            isDiscount = isDiscount,
                            testTag = itemTestTag
                        )
                    }
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
                isDiscount = false,
                testTag = if (testTag.isNotEmpty()) "${testTag}_total" else ""
            )
        }
    }
}

@Composable
private fun DinerConsumptionHeader(
    name: String,
    statusLabel: String?,
    checked: Boolean,
    isSelectable: Boolean,
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
            enabled = isSelectable,
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

        // Unweighted, so it is measured first at its full width and the name gives up the space.
        if (statusLabel != null) {
            StatusTag(
                variant = StatusTagVariant.Paid,
                text = statusLabel,
                testTag = if (testTag.isNotEmpty()) "${testTag}_status" else "",
                modifier = Modifier.padding(start = 8.dp, end = 4.dp)
            )
        }

        Icon(
            imageVector = vectorResource(Res.drawable.icon_right_chevron),
            contentDescription = toggleDescription,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(ChevronSize)
                .rotate(if (isExpanded) -90f else 90f)
        )
    }
}

@Composable
private fun DinerConsumptionCheckbox(
    checked: Boolean,
    enabled: Boolean,
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
                enabled = enabled,
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
                .padding(CheckboxInset)
                .clip(CheckboxShape)
                .then(
                    when {
                        !enabled -> Modifier
                            .background(MaterialTheme.colorScheme.outlineVariant)
                            .border(CheckboxBorderWidth, MaterialTheme.colorScheme.extended.disabledContent, CheckboxShape)
                        checked -> Modifier.background(MaterialTheme.colorScheme.secondary)
                        else -> Modifier.border(CheckboxBorderWidth, MaterialTheme.colorScheme.outline, CheckboxShape)
                    }
                )
        ) {
            // A diner that can't be selected never shows the check, whatever isChecked says.
            if (checked && enabled) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(CheckIconSize)
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
    isDiscount: Boolean,
    testTag: String,
    quantity: String? = null
) {
    val extended = MaterialTheme.colorScheme.extended
    val nameColor = when {
        isTotal -> MaterialTheme.colorScheme.primary
        isDiscount -> extended.disabledContent
        else -> extended.neutral400
    }
    val priceColor = when {
        isTotal -> MaterialTheme.colorScheme.primary
        isDiscount -> extended.disabledContent
        else -> extended.neutral400
    }
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            // fill = false keeps the quantity right after the name; the name gives up the space.
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLargeRegular,
                fontWeight = fontWeight,
                color = nameColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false)
            )
            if (quantity != null) {
                Text(
                    text = "x$quantity",
                    style = MaterialTheme.typography.bodyLargeRegular,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDiscount) extended.disabledContent else MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_quantity") else Modifier)
                )
            }
        }
        Text(
            text = price,
            style = MaterialTheme.typography.bodyLargeRegular,
            fontWeight = fontWeight,
            color = priceColor
        )
    }
}

/**
 * Extras of a product, drawn under its row. The name starts where the product name does (an
 * invisible bullet keeps the indent in step with the product row) and the price lines up with the
 * product price.
 */
@Composable
private fun DinerConsumptionExtras(
    extras: ImmutableList<DinerConsumptionExtra>,
    isDiscount: Boolean,
    testTag: String
) {
    val extended = MaterialTheme.colorScheme.extended
    val color = if (isDiscount) extended.disabledContent else extended.neutral400

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = HorizontalPadding, end = HorizontalPadding, bottom = 12.dp)
    ) {
        extras.forEachIndexed { index, extra ->
            key(extra.name, index) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_extra_$index") else Modifier)
                        .padding(vertical = 2.dp)
                ) {
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodyLargeRegular,
                        modifier = Modifier
                            .alpha(0f)
                            .clearAndSetSemantics {}
                    )
                    Text(
                        text = extra.name,
                        style = MaterialTheme.typography.bodyMediumRegular,
                        fontSize = 12.sp,
                        color = color,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = extra.price,
                        style = MaterialTheme.typography.bodyMediumRegular,
                        fontSize = 12.sp,
                        color = color
                    )
                }
            }
        }
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
                        DinerConsumptionItem(name = "Mojito tradicional", price = "$7.000"),
                        DinerConsumptionItem(
                            name = "Descuento 10%",
                            price = "-$1.600",
                            kind = DinerConsumptionItemKind.Discount
                        )
                    ),
                    total = "$14.400",
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
                    items = persistentListOf(
                        DinerConsumptionItem(name = "Limonada", price = "$3.500")
                    ),
                    total = "$0",
                    statusLabel = "Sin deuda",
                    isSelectable = false
                )
            ),
            onCheckedChange = { _, _ -> },
            modifier = Modifier.padding(16.dp)
        )
    }
}

// Mirrors Figma "comensal - genérico": selected diner, expanded, with a discount and the total in primary.
@Preview(showBackground = true, backgroundColor = 0xFFFAFAFA)
@Composable
private fun ToteatDinerConsumptionCardExpandedPreview() {
    ToteatTheme {
        ToteatDinerConsumptionCard(
            consumption = DinerConsumption(
                name = "Pauli",
                items = persistentListOf(
                    DinerConsumptionItem(name = "Hamburguesa BBQ", price = "$9.000"),
                    DinerConsumptionItem(name = "Bebida", price = "$1.000"),
                    DinerConsumptionItem(
                        name = "Descuento",
                        price = "-$200",
                        kind = DinerConsumptionItemKind.Discount
                    )
                ),
                total = "$9.800",
                isChecked = true
            ),
            onCheckedChange = {},
            initiallyExpanded = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFAFAFA)
@Composable
private fun ToteatDinerConsumptionCardWithExtrasPreview() {
    ToteatTheme {
        ToteatDinerConsumptionCard(
            consumption = DinerConsumption(
                name = "Pauli",
                items = persistentListOf(
                    DinerConsumptionItem(
                        name = "Empanada de pino",
                        price = "$15.600",
                        quantity = "6",
                        extras = persistentListOf(
                            DinerConsumptionExtra(name = "Pebre", price = "$600"),
                            DinerConsumptionExtra(name = "Salsa picante", price = "$0")
                        )
                    ),
                    DinerConsumptionItem(name = "Mojito tradicional", price = "$7.000")
                ),
                total = "$22.600"
            ),
            onCheckedChange = {},
            initiallyExpanded = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}
