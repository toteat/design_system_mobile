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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
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
import designsystemmobile.toteatds.generated.resources.icon_edit_pencil
import designsystemmobile.toteatds.generated.resources.icon_right_chevron
import designsystemmobile.toteatds.generated.resources.product_card_description
import designsystemmobile.toteatds.generated.resources.product_card_edit_description
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

private val ProductCardCornerRadius = 14.dp
private val ProductCardShape = RoundedCornerShape(ProductCardCornerRadius)
private val QuantityBadgeShape = RoundedCornerShape(8.dp)

enum class ProductCardStatus(val statusRes: StringResource) {
    DEFAULT(Res.string.product_card_status_default),
    CONFIRMED(Res.string.product_card_status_confirmed),
    PENDING(Res.string.product_card_status_pending)
}

/**
 * Qué significa el botón de acción de la fila, cuando `showDeleteButton = true`.
 *
 * Cada variante fija su ícono, su descripción de accesibilidad y su tamaño, de modo que la
 * iconografía siga siendo decisión del design system y el consumidor solo elija la intención.
 *
 * [DELETE] es el comportamiento histórico: el basurero de "Eliminar %1$s". [EDIT] es el lápiz de
 * "Ajustar cantidad de %1$s", para las pantallas donde el ícono ya no borra el producto sino que
 * abre el modal de ajuste de cantidad (POS, comanda por confirmar).
 *
 * Sobre [iconSize]: los dos drawables ocupan proporciones muy distintas de su viewport.
 * `icon_delete_default` es un 16x16 relleno cuyo trazo llega casi al borde, así que a 12.dp se ven
 * ~11.5.dp de tinta. `icon_edit_pencil` es un 24x24 de líneas cuyo dibujo cubre ~70% del viewport,
 * así que a 12.dp se verían ~8.4.dp y un trazo de 0.75.dp: más chico y más delgado que el basurero.
 * A 16.dp el lápiz recupera ~11.2.dp de tinta y un trazo de 1.dp, que es el que usa el resto de la
 * iconografía lineal del sistema, y ópticamente pesa lo mismo que el basurero de 12.dp.
 */
enum class ProductCardAction {
    DELETE,
    EDIT
}

private val ProductCardDeleteIconSize = 12.dp
private val ProductCardEditIconSize = 16.dp

data class ProductCardItem(
    val name: String,
    val description: String?,
    val price: String,
    val quantity: Int?,
    val status: ProductCardStatus,
    val showDeleteButton: Boolean = false,
    val onDeleteClick: (() -> Unit)? = null,
    val onClick: (() -> Unit)? = null,
    val action: ProductCardAction = ProductCardAction.DELETE
)

/**
 * @param action Qué representa el botón de acción que aparece con [showDeleteButton]. Por defecto
 * [ProductCardAction.DELETE], el basurero de siempre; [ProductCardAction.EDIT] dibuja el lápiz de
 * "ajustar cantidad".
 */
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
    testTag: String = "",
    // New parameters go after the previously published ones so positional call sites keep working.
    action: ProductCardAction = ProductCardAction.DELETE
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
            action = action,
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            testTag = testTag
        )
    }
}

/**
 * Una sola fila del grupo, para consumirla desde un `LazyColumn` cuando la lista es
 * larga y componerla completa resulta costoso. El resultado es visualmente idéntico a
 * [ProductCardGroup]: [position] define las esquinas redondeadas y el separador, y el
 * borde se dibuja extendido hacia afuera en las posiciones intermedias para que en las
 * uniones no se sumen dos trazos.
 *
 * El colapso ("ver todos") es responsabilidad del consumidor en este modo.
 */
@Composable
fun ProductCardGroupItem(
    item: ProductCardItem,
    position: ListItemPosition,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    val shape = position.getShape(ProductCardCornerRadius)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clipToBounds()
            .background(NeutralGray, shape)
            .groupItemBorder(position)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        ProductCardRow(
            name = item.name,
            description = item.description,
            price = item.price,
            quantity = item.quantity,
            status = item.status,
            showDeleteButton = item.showDeleteButton,
            onDeleteClick = item.onDeleteClick,
            action = item.action,
            onClick = item.onClick,
            modifier = Modifier.fillMaxWidth(),
            testTag = testTag
        )

        if (position == ListItemPosition.First || position == ListItemPosition.Middle) {
            HorizontalDivider(
                color = NeutralGray100,
                thickness = 1.dp
            )
        }
    }
}

/**
 * Dibuja el borde del grupo para una fila suelta. En las posiciones que continúan
 * (First, Middle, Last) el rectángulo se extiende más allá del alto visible, de modo
 * que el trazo horizontal de ese lado cae fuera del `clipToBounds` y no se duplica
 * contra el borde de la fila vecina.
 */
private fun Modifier.groupItemBorder(position: ListItemPosition): Modifier = drawBehind {
    val stroke = 1.dp.toPx()
    val radius = ProductCardCornerRadius.toPx()
    val overflowTop = if (position == ListItemPosition.Middle || position == ListItemPosition.Last) {
        radius
    } else {
        0f
    }
    val overflowBottom = if (position == ListItemPosition.Middle || position == ListItemPosition.First) {
        radius
    } else {
        0f
    }

    drawRoundRect(
        color = NeutralGray200,
        topLeft = Offset(x = stroke / 2f, y = -overflowTop + stroke / 2f),
        size = Size(
            width = size.width - stroke,
            height = size.height + overflowTop + overflowBottom - stroke
        ),
        cornerRadius = CornerRadius(radius),
        style = Stroke(width = stroke)
    )
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
                action = item.action,
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
    action: ProductCardAction,
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
                    action = action,
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
    action: ProductCardAction,
    testTag: String = ""
) {
    val actionDescription = when (action) {
        ProductCardAction.DELETE -> stringResource(Res.string.product_card_remove_description, name)
        ProductCardAction.EDIT -> stringResource(Res.string.product_card_edit_description, name)
    }
    val actionIcon = when (action) {
        ProductCardAction.DELETE -> Res.drawable.icon_delete_default
        ProductCardAction.EDIT -> Res.drawable.icon_edit_pencil
    }
    val actionSize = when (action) {
        ProductCardAction.DELETE -> ProductCardDeleteIconSize
        ProductCardAction.EDIT -> ProductCardEditIconSize
    }

    Icon(
        imageVector = vectorResource(actionIcon),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .size(actionSize)
            .then(
                if (onDeleteClick != null) {
                    Modifier
                        .clickable(onClick = onDeleteClick)
                        .semantics {
                            role = Role.Button
                            contentDescription = actionDescription
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
private fun ProductCardSingleEditActionPreview() {
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
                onDeleteClick = {},
                action = ProductCardAction.EDIT
            )
            ProductCard(
                name = "Mojito tradicional",
                description = "Unitario: \$ 5.290",
                price = "\$5.290",
                quantity = 1,
                status = ProductCardStatus.PENDING,
                showDeleteButton = true,
                onDeleteClick = {},
                action = ProductCardAction.DELETE
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
private fun ProductCardGroupedEditActionPreview() {
    ToteatTheme {
        ProductCardGroup(
            modifier = Modifier.padding(16.dp),
            items = persistentListOf(
                ProductCardItem(
                    name = "Hamburguesa BBQ",
                    description = "Unitario: \$ 7.000",
                    price = "\$14.000",
                    quantity = 2,
                    status = ProductCardStatus.PENDING,
                    showDeleteButton = true,
                    onDeleteClick = {},
                    action = ProductCardAction.EDIT
                ),
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: \$ 5.290",
                    price = "\$5.290",
                    quantity = 1,
                    status = ProductCardStatus.PENDING,
                    showDeleteButton = true,
                    onDeleteClick = {},
                    action = ProductCardAction.EDIT
                ),
                ProductCardItem(
                    name = "Papas fritas grandes",
                    description = "Unitario: \$ 3.500",
                    price = "\$3.500",
                    quantity = 1,
                    status = ProductCardStatus.CONFIRMED,
                    showDeleteButton = true,
                    onDeleteClick = {}
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
