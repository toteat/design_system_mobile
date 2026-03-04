package com.toteat.toteatds.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.BlueLight
import com.toteat.toteatds.theme.BlueNormal
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray100
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyMediumRegular
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_delete_default
import designsystemmobile.toteatds.generated.resources.icon_right_chevron
import designsystemmobile.toteatds.generated.resources.product_card_description
import designsystemmobile.toteatds.generated.resources.product_card_group_description
import designsystemmobile.toteatds.generated.resources.product_card_quantity_description
import designsystemmobile.toteatds.generated.resources.product_card_remove_description
import designsystemmobile.toteatds.generated.resources.product_card_status_confirmed
import designsystemmobile.toteatds.generated.resources.product_card_status_default
import designsystemmobile.toteatds.generated.resources.product_card_status_pending
import designsystemmobile.toteatds.generated.resources.product_card_view_all_items
import designsystemmobile.toteatds.generated.resources.product_card_view_less_items
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.vectorResource

private val ProductCardShape = RoundedCornerShape(14.dp)
private val QuantityBadgeShape = RoundedCornerShape(8.dp)

enum class ProductCardStatus(val statusRes: StringResource) {
    DEFAULT(Res.string.product_card_status_default),
    CONFIRMED(Res.string.product_card_status_confirmed),
    PENDING(Res.string.product_card_status_pending)
}

data class ProductCardItem(
    val name: String,
    val description: String?,
    val price: String,
    val quantity: Int?,
    val status: ProductCardStatus,
    val showDeleteButton: Boolean = false,
    val onDeleteClick: (() -> Unit)? = null,
    val onClick: (() -> Unit)? = null
)

@Composable
fun ProductCard(
    name: String,
    description: String?,
    price: String,
    quantity: Int?,
    status: ProductCardStatus,
    showDeleteButton: Boolean = false,
    onDeleteClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(ProductCardShape)
            .background(NeutralGray)
            .border(1.dp, NeutralGray200, ProductCardShape)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        ProductCardRow(
            name = name,
            description = description,
            price = price,
            quantity = quantity,
            status = status,
            showDeleteButton = showDeleteButton,
            onDeleteClick = onDeleteClick,
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            testTag = testTag
        )
    }
}

@Composable
fun ProductCardGroup(
    items: ImmutableList<ProductCardItem>,
    modifier: Modifier = Modifier,
    maxCollapsedItems: Int = 4,
    footerText: String? = null,
    onFooterClick: (() -> Unit)? = null,
    testTag: String = ""
) {
    if (items.isEmpty()) return

    val collapsedLimit = maxCollapsedItems.coerceAtLeast(1)
    val hasMoreItems = items.size > collapsedLimit
    var isExpanded by remember(items, collapsedLimit) { mutableStateOf(false) }
    val visibleItems = if (isExpanded || !hasMoreItems) items else items.take(collapsedLimit)
    val groupDescription = stringResource(Res.string.product_card_group_description, items.size)
    val defaultFooterText = if (isExpanded) {
        stringResource(Res.string.product_card_view_less_items)
    } else {
        stringResource(Res.string.product_card_view_all_items)
    }
    val displayFooterText = footerText ?: defaultFooterText
    val shouldShowFooter = hasMoreItems || footerText != null
    val footerEnabled = hasMoreItems || onFooterClick != null

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(ProductCardShape)
            .background(NeutralGray)
            .border(1.dp, NeutralGray200, ProductCardShape)
            .semantics { contentDescription = groupDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        visibleItems.forEachIndexed { index, item ->
            ProductCardRow(
                name = item.name,
                description = item.description,
                price = item.price,
                quantity = item.quantity,
                status = item.status,
                showDeleteButton = item.showDeleteButton,
                onDeleteClick = item.onDeleteClick,
                onClick = item.onClick,
                modifier = Modifier.fillMaxWidth(),
                testTag = if (testTag.isNotEmpty()) "${testTag}_item_$index" else ""
            )

            if (index < visibleItems.size - 1) {
                HorizontalDivider(
                    color = NeutralGray100,
                    thickness = 1.dp
                )
            }
        }

        if (shouldShowFooter) {
            HorizontalDivider(
                color = NeutralGray100,
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 36.dp)
                    .then(
                        if (footerEnabled) {
                            Modifier
                                .clickable {
                                    if (hasMoreItems) {
                                        isExpanded = !isExpanded
                                    }
                                    onFooterClick?.invoke()
                                }
                                .semantics { role = Role.Button }
                        } else {
                            Modifier
                        }
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp)
                    .then(
                        if (testTag.isNotEmpty()) {
                            Modifier.setTestTag("${testTag}_footer")
                        } else {
                            Modifier
                        }
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = displayFooterText,
                    style = MaterialTheme.typography.bodyMediumRegular,
                    color = NeutralGray400
                )
                Spacer(Modifier.size(4.dp))
                Icon(
                    imageVector = vectorResource(Res.drawable.icon_right_chevron),
                    contentDescription = null,
                    tint = NeutralGray400,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

@Composable
private fun ProductCardRow(
    name: String,
    description: String?,
    price: String,
    quantity: Int?,
    status: ProductCardStatus,
    showDeleteButton: Boolean,
    onDeleteClick: (() -> Unit)?,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    val statusLabel = stringResource(status.statusRes)
    val cardDescription = stringResource(
        Res.string.product_card_description,
        name,
        price,
        statusLabel
    )

    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 62.dp)
            .then(
                if (onClick != null) {
                    Modifier
                        .clickable(onClick = onClick)
                        .semantics {
                            role = Role.Button
                            contentDescription = cardDescription
                        }
                } else {
                    Modifier.semantics { contentDescription = cardDescription }
                }
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (quantity != null) {
            QuantityBadge(
                quantity = quantity,
                status = status,
                testTag = if (testTag.isNotEmpty()) "${testTag}_quantity" else ""
            )
            Spacer(Modifier.size(10.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = NeutralGray500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.then(
                    if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_name") else Modifier
                )
            )

            description?.let {
                Spacer(Modifier.size(2.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMediumRegular,
                    color = NeutralGray400,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.then(
                        if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_description") else Modifier
                    )
                )
            }
        }

        Text(
            text = price,
            style = MaterialTheme.typography.bodyLarge,
            color = NeutralGray500,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.then(
                if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_price") else Modifier
            )
        )

        when {
            showDeleteButton -> {
                Spacer(Modifier.size(2.dp))
                RemoveProductButton(
                    name = name,
                    onDeleteClick = onDeleteClick,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_delete" else ""
                )
            }

            onClick != null -> {
                Spacer(Modifier.size(2.dp))
                Icon(
                    imageVector = vectorResource(Res.drawable.icon_right_chevron),
                    contentDescription = null,
                    tint = NeutralGray300,
                    modifier = Modifier
                        .size(12.dp)
                        .then(
                            if (testTag.isNotEmpty()) {
                                Modifier.setTestTag("${testTag}_arrow")
                            } else {
                                Modifier
                            }
                        )
                )
            }
        }
    }
}

@Composable
private fun QuantityBadge(
    quantity: Int,
    status: ProductCardStatus,
    testTag: String = ""
) {
    val quantityDescription = stringResource(Res.string.product_card_quantity_description, quantity)
    val (backgroundColor, textColor) = when (status) {
        ProductCardStatus.PENDING -> BlueLight to BlueNormal
        ProductCardStatus.CONFIRMED -> NeutralGray200 to NeutralGray400
        ProductCardStatus.DEFAULT -> NeutralGray200 to NeutralGray400
    }

    Box(
        modifier = Modifier
            .clip(QuantityBadgeShape)
            .background(backgroundColor)
            .padding(horizontal = 6.dp, vertical = 4.dp)
            .semantics { contentDescription = quantityDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "x$quantity",
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

@Composable
private fun RemoveProductButton(
    name: String,
    onDeleteClick: (() -> Unit)?,
    testTag: String = ""
) {
    val removeDescription = stringResource(Res.string.product_card_remove_description, name)

    Icon(
        imageVector = vectorResource(Res.drawable.icon_delete_default),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .size(12.dp)
            .then(
                if (onDeleteClick != null) {
                    Modifier
                        .clickable(onClick = onDeleteClick)
                        .semantics {
                            role = Role.Button
                            contentDescription = removeDescription
                        }
                } else {
                    Modifier
                }
            )
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    )
}

@Preview(showBackground = true)
@Composable
private fun ProductCardSinglePreview() {
    ToteatTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProductCard(
                name = "Hamburguesa BBQ",
                description = "Unitario: \$ 7.000",
                price = "\$14.000",
                quantity = 2,
                status = ProductCardStatus.PENDING,
                showDeleteButton = true,
                onDeleteClick = {}
            )
            ProductCard(
                name = "Mojito tradicional",
                description = "Unitario: \$ 5.290",
                price = "\$5.290",
                quantity = 1,
                status = ProductCardStatus.CONFIRMED
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardGroupedPreview() {
    ToteatTheme {
        ProductCardGroup(
            modifier = Modifier.padding(16.dp),
            items = persistentListOf(
                ProductCardItem(
                    name = "Hamburguesa BBQ",
                    description = "Unitario: \$ 7.000",
                    price = "\$14.000",
                    quantity = 2,
                    status = ProductCardStatus.CONFIRMED
                ),
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: \$ 5.290",
                    price = "\$5.290",
                    quantity = 1,
                    status = ProductCardStatus.CONFIRMED
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardGroupedWithFooterPreview() {
    ToteatTheme {
        ProductCardGroup(
            modifier = Modifier.padding(16.dp),
            items = persistentListOf(
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: \$ 5.290",
                    price = "\$5.290",
                    quantity = 1,
                    status = ProductCardStatus.PENDING,
                    onClick = {}
                ),
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: \$ 5.290",
                    price = "\$5.290",
                    quantity = 1,
                    status = ProductCardStatus.PENDING,
                    onClick = {}
                ),
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: \$ 5.290",
                    price = "\$5.290",
                    quantity = 1,
                    status = ProductCardStatus.PENDING,
                    onClick = {}
                ),
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: \$ 5.290",
                    price = "\$5.290",
                    quantity = 1,
                    status = ProductCardStatus.PENDING,
                    onClick = {}
                ),
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: \$ 5.290",
                    price = "\$5.290",
                    quantity = 1,
                    status = ProductCardStatus.PENDING,
                    onClick = {}
                )
            )
        )
    }
}
