package com.toteat.toteatds.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.components.tags.StatusTag
import com.toteat.toteatds.components.tags.StatusTagVariant
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyMediumRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.helperBold
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.diner_consumption_checkbox_description
import designsystemmobile.toteatds.generated.resources.diner_consumption_collapse
import designsystemmobile.toteatds.generated.resources.diner_consumption_description
import designsystemmobile.toteatds.generated.resources.diner_consumption_description_with_status
import designsystemmobile.toteatds.generated.resources.diner_consumption_expand
import designsystemmobile.toteatds.generated.resources.diner_consumption_list_description
import designsystemmobile.toteatds.generated.resources.diner_consumption_total
import designsystemmobile.toteatds.generated.resources.icon_chevron_down
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// Figma "comensal - genérico" (Toteat Go, 23838:22702).
private val HeaderHeight = 36.dp
private val ItemRowHeight = 36.dp
private val CardSpacing = 12.dp
// Figma icon/ checkbox is 14.dp (box inset 0.875.dp, 2.625.dp radius, ~1.dp border, check ~7.5.dp).
// It is drawn at 20.dp with the same proportions so it doesn't read as a tiny button. It sits 12.dp
// from the card's edge inside a 48.dp-wide touch area, so the name starts at 48.dp.
private val CheckboxSize = 20.dp
private val CheckboxInset = 1.25.dp
private val CheckboxShape = RoundedCornerShape(3.75.dp)
private val CheckboxBorderWidth = 1.5.dp
private val CheckIconSize = 13.dp
private val CheckboxTouchSize = 48.dp
// Figma icon/ chevron-strokes "4-down" (the DS icon_chevron_down), turned up when expanded.
private val ChevronSize = 16.dp
private val DividerThickness = 1.dp
private val HorizontalPadding = 12.dp
// Figma list bullets: the text starts 18.dp after the row padding; the bullet sits at the end of
// a 10.dp slot followed by the row's 8.dp spacing.
private val BulletSlotWidth = 10.dp
private val ContentSpacing = 8.dp

// "tab-light": offset (0, 1), blur radius 2, #D0D0D0 at 52 %. NeutralGray300 (#CFCFCF) is the
// palette's closest color.
private val CardShadowRadius = 2.dp
private val CardShadowOffset = DpOffset(0.dp, 1.dp)
private const val CardShadowAlpha = 0.52f
// The LazyColumn clips its content: keeps the first card's shadow (1.dp above) and the last one's
// (offset + blur, 3.dp below) visible.
private val ListShadowPadding = PaddingValues(top = 1.dp, bottom = 3.dp)

/** How a consumption row is painted. [Discount] rows are gray (neutral-gray400), as in Figma "comensal - genérico". */
enum class DinerConsumptionItemKind { Regular, Discount }

/**
 * @param quantity Already formatted quantity (e.g. "2"), shown before the name as "2x", also when it
 * is "1". `null` (default) shows nothing.
 *
 * An extra has no status of its own: it takes its product's tone (see [DinerConsumptionItem.statusLabel]).
 */
@Immutable
data class DinerConsumptionExtra(
    val name: String,
    val price: String,
    val quantity: String? = null
)

/**
 * @param quantity Already formatted quantity (e.g. "6"), shown before the name as "6x", also when it
 * is "1" ("1x"). `null` (default) shows nothing, e.g. for discounts.
 * @param extras Shown under the product, indented and in a smaller style, each with its price
 * aligned to the right.
 * @param statusLabel Short, already translated text (e.g. "Pagado") shown as a `Paid` [StatusTag]
 * before the price; the row keeps the gray tone. `null` (default) shows no tag and paints a
 * [DinerConsumptionItemKind.Regular] row in the darker pending tone. Its extras take the same tone
 * without a tag of their own. [DinerConsumptionItemKind.Discount] rows stay gray either way.
 */
@Immutable
data class DinerConsumptionItem(
    val name: String,
    val price: String,
    val kind: DinerConsumptionItemKind = DinerConsumptionItemKind.Regular,
    val quantity: String? = null,
    val extras: ImmutableList<DinerConsumptionExtra> = persistentListOf(),
    val statusLabel: String? = null
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
 * Expanding a card scrolls the list so its content is visible (see [ToteatDinerConsumptionCard]).
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
        verticalArrangement = Arrangement.spacedBy(CardSpacing),
        contentPadding = ListShadowPadding,
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
 *
 * When the user expands it inside a scrollable container (such as [ToteatDinerConsumptionList] or a
 * `verticalScroll` column), the container scrolls just enough to show the whole card once it has
 * its expanded size; if the card is taller than the viewport, its header is kept at the top. It
 * does not scroll when the content is already visible, when collapsing, or for [initiallyExpanded].
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
    val revealRequester = rememberExpansionRevealRequester()
    val statusLabel = consumption.statusLabel
    val cardDescription = if (statusLabel != null) {
        stringResource(Res.string.diner_consumption_description_with_status, consumption.name, statusLabel)
    } else {
        stringResource(Res.string.diner_consumption_description, consumption.name)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .revealOnExpand(revealRequester)
            // Before the clip so the shadow is drawn outside the card.
            .dropShadow(
                shape = MaterialTheme.shapes.medium,
                shadow = Shadow(
                    radius = CardShadowRadius,
                    color = NeutralGray300.copy(alpha = CardShadowAlpha),
                    offset = CardShadowOffset
                )
            )
            .clip(MaterialTheme.shapes.medium)
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
            onToggleExpanded = {
                isExpanded = !isExpanded
                revealRequester.revealOnNextResize = isExpanded
            },
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
                        statusLabel = item.statusLabel,
                        testTag = itemTestTag
                    )
                    if (item.extras.isNotEmpty()) {
                        DinerConsumptionExtras(
                            extras = item.extras,
                            color = consumptionRowColor(isDiscount, item.statusLabel),
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

/**
 * Asks the scrollable ancestors of an expanded card to show it once it has its expanded size.
 *
 * [BringIntoViewRequester.bringIntoView] skips the request when the bounds it reads are already
 * visible, so it must run after the card is remeasured, not when the tap is handled; [onSizeChanged]
 * runs right after that measurement. The second request keeps the header visible when the card is
 * taller than the viewport and the default spec would leave it where it was.
 */
@Stable
private class ExpansionRevealRequester(
    val bringIntoViewRequester: BringIntoViewRequester,
    private val scope: CoroutineScope,
    private val headerHeightPx: Float
) {
    var revealOnNextResize = false

    fun onResized(size: IntSize) {
        if (!revealOnNextResize) return
        revealOnNextResize = false
        scope.launch {
            bringIntoViewRequester.bringIntoView()
            bringIntoViewRequester.bringIntoView(Rect(0f, 0f, size.width.toFloat(), headerHeightPx))
        }
    }
}

@Composable
private fun rememberExpansionRevealRequester(): ExpansionRevealRequester {
    val scope = rememberCoroutineScope()
    val headerHeightPx = with(LocalDensity.current) { HeaderHeight.toPx() }
    return remember(scope, headerHeightPx) {
        ExpansionRevealRequester(BringIntoViewRequester(), scope, headerHeightPx)
    }
}

private fun Modifier.revealOnExpand(requester: ExpansionRevealRequester): Modifier = this
    .bringIntoViewRequester(requester.bringIntoViewRequester)
    .onSizeChanged(requester::onResized)

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
            .padding(end = HorizontalPadding)
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
            style = headerNameStyle(),
            color = MaterialTheme.colorScheme.extended.neutral500,
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
            imageVector = vectorResource(Res.drawable.icon_chevron_down),
            contentDescription = toggleDescription,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(ChevronSize)
                .rotate(if (isExpanded) 180f else 0f)
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

    // 48.dp-wide touch area from the card's edge with the 14.dp box 12.dp inside it, as in Figma.
    // sizeIn makes the toggleable node itself 48.dp wide: minimumInteractiveComponentSize alone
    // would center the box. The 36.dp header limits the height; the touch area still reaches 48.dp.
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .minimumInteractiveComponentSize()
            .sizeIn(minWidth = CheckboxTouchSize, minHeight = CheckboxTouchSize)
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            )
            .semantics { contentDescription = description }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .padding(start = HorizontalPadding)
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
                        // Figma "not-selected": neutral-gray400 outline.
                        else -> Modifier.border(CheckboxBorderWidth, MaterialTheme.colorScheme.extended.neutral400, CheckboxShape)
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
    quantity: String? = null,
    statusLabel: String? = null
) {
    val color = if (isTotal) MaterialTheme.colorScheme.primary else consumptionRowColor(isDiscount, statusLabel)
    // Figma Body-base/B12 B on every row, the total included.
    val style = rowTextStyle()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ContentSpacing),
        modifier = Modifier
            .fillMaxWidth()
            .height(ItemRowHeight)
            .background(MaterialTheme.colorScheme.background)
            .then(statusRowSemantics(statusLabel))
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .padding(horizontal = HorizontalPadding)
    ) {
        Bullet(style = style, color = color)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            if (quantity != null) {
                QuantityText(
                    quantity = quantity,
                    style = style,
                    color = color,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_quantity" else ""
                )
            }
            Text(
                text = name,
                style = style,
                color = color,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
        if (statusLabel != null) {
            StatusTag(
                variant = StatusTagVariant.Paid,
                text = statusLabel,
                testTag = if (testTag.isNotEmpty()) "${testTag}_status" else ""
            )
        }
        Text(
            text = price,
            style = style,
            color = color
        )
    }
}

/** List bullet at the end of its slot, so the text starts 18.dp after the row padding as in Figma. */
@Composable
private fun Bullet(style: TextStyle, color: Color, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.CenterEnd, modifier = modifier.width(BulletSlotWidth)) {
        Text(text = "•", style = style, color = color)
    }
}

/** Figma Body-base/B12 B: Manrope Bold 12, line height 1.5. */
@Composable
private fun rowTextStyle(): TextStyle {
    val base = MaterialTheme.typography.bodyMedium
    return remember(base) { base.copy(lineHeight = 18.sp) }
}

/** Figma Body-base/helper B: Manrope Bold 11, line height 1.8. */
@Composable
private fun headerNameStyle(): TextStyle {
    val base = MaterialTheme.typography.helperBold
    return remember(base) { base.copy(lineHeight = 19.8.sp) }
}

/**
 * Tone of a product row and its extras, as in Figma: neutral-gray400 for discounts and paid rows (with
 * a status label), secondary-normal while still pending.
 */
@Composable
private fun consumptionRowColor(isDiscount: Boolean, statusLabel: String?): Color =
    if (isDiscount || statusLabel != null) {
        MaterialTheme.colorScheme.extended.neutral400
    } else {
        MaterialTheme.colorScheme.secondary
    }

// A row with a status tag is read as one node, so the tag's description is read with its name and price.
private fun statusRowSemantics(statusLabel: String?): Modifier =
    if (statusLabel != null) Modifier.semantics(mergeDescendants = true) {} else Modifier

/** "6x" before a product or extra name, in the row's tone. Never cut: the name gives up the space. */
@Composable
private fun QuantityText(
    quantity: String,
    style: TextStyle,
    color: Color,
    testTag: String
) {
    Text(
        text = "${quantity}x",
        style = style,
        fontWeight = FontWeight.SemiBold,
        color = color,
        maxLines = 1,
        modifier = Modifier
            .padding(end = 4.dp)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    )
}

/**
 * Extras of a product, drawn under its row in the product's [color]. The name starts where the
 * product name does (an invisible bullet keeps the indent in step with the product row) and the price
 * lines up with the product price.
 */
@Composable
private fun DinerConsumptionExtras(
    extras: ImmutableList<DinerConsumptionExtra>,
    color: Color,
    testTag: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = HorizontalPadding, end = HorizontalPadding, bottom = 12.dp)
    ) {
        extras.forEachIndexed { index, extra ->
            key(extra.name, index) {
                val extraTestTag = if (testTag.isNotEmpty()) "${testTag}_extra_$index" else ""
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(ContentSpacing),
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(if (extraTestTag.isNotEmpty()) Modifier.setTestTag(extraTestTag) else Modifier)
                        .padding(vertical = 2.dp)
                ) {
                    Bullet(
                        style = rowTextStyle(),
                        color = color,
                        modifier = Modifier
                            .alpha(0f)
                            .clearAndSetSemantics {}
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        val quantity = extra.quantity
                        if (quantity != null) {
                            QuantityText(
                                quantity = quantity,
                                style = MaterialTheme.typography.bodyMediumRegular.copy(fontSize = 12.sp),
                                color = color,
                                testTag = if (extraTestTag.isNotEmpty()) "${extraTestTag}_quantity" else ""
                            )
                        }
                        Text(
                            text = extra.name,
                            style = MaterialTheme.typography.bodyMediumRegular,
                            fontSize = 12.sp,
                            color = color,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                    }
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
                            DinerConsumptionExtra(name = "Pebre", price = "$600", quantity = "2"),
                            DinerConsumptionExtra(name = "Salsa picante", price = "$0", quantity = "1")
                        )
                    ),
                    DinerConsumptionItem(name = "Mojito tradicional", price = "$7.000", quantity = "1")
                ),
                total = "$22.600"
            ),
            onCheckedChange = {},
            initiallyExpanded = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// Paid products keep the gray tone with a "Pagado" tag; what was added after the payment is darker.
@Preview(showBackground = true, backgroundColor = 0xFFFAFAFA)
@Composable
private fun ToteatDinerConsumptionCardPartiallyPaidPreview() {
    ToteatTheme {
        ToteatDinerConsumptionCard(
            consumption = DinerConsumption(
                name = "Pauli",
                items = persistentListOf(
                    DinerConsumptionItem(
                        name = "Empanada de pino",
                        price = "$15.600",
                        quantity = "6",
                        statusLabel = "Pagado",
                        extras = persistentListOf(
                            DinerConsumptionExtra(name = "Pebre", price = "$600", quantity = "2"),
                            DinerConsumptionExtra(name = "Salsa picante", price = "$0", quantity = "1")
                        )
                    ),
                    DinerConsumptionItem(
                        name = "Mojito tradicional",
                        price = "$7.000",
                        quantity = "1",
                        extras = persistentListOf(
                            DinerConsumptionExtra(name = "Extra menta", price = "$500")
                        )
                    )
                ),
                total = "$7.500"
            ),
            onCheckedChange = {},
            initiallyExpanded = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}
