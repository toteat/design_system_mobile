package com.toteat.toteatds.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.tagRegular
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.product_row_description
import org.jetbrains.compose.resources.stringResource

private val RowShape = RoundedCornerShape(12.dp)
private val RowPadding = 12.dp
private val TextGap = 2.dp

@Composable
fun ToteatProductRow(
    name: String,
    price: String,
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    showControls: Boolean = true,
    description: String? = null,
    contentDescription: String? = null,
    testTag: String = ""
) {
    val semanticDescription = contentDescription
        ?: stringResource(Res.string.product_row_description, name, price)

    val disabledContent = MaterialTheme.colorScheme.extended.disabledContent
    val containerColor = if (enabled) MaterialTheme.colorScheme.background
        else MaterialTheme.colorScheme.surfaceVariant
    val titleColor = if (enabled) MaterialTheme.colorScheme.onBackground else disabledContent
    val descriptionColor = if (enabled) MaterialTheme.colorScheme.onBackground else disabledContent
    val priceColor = if (enabled) MaterialTheme.colorScheme.primary else disabledContent

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                this.contentDescription = semanticDescription
                this.role = Role.Button
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        shape = RowShape,
        color = containerColor
    ) {
        Row(
            modifier = Modifier.padding(RowPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: text content
            Column(
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(TextGap)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = titleColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_title") else Modifier
                )

                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.tagRegular,
                        color = descriptionColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_description") else Modifier
                    )
                }

                Text(
                    text = price,
                    style = MaterialTheme.typography.bodyMedium,
                    color = priceColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_price") else Modifier
                )
            }

            // Right: counter or badge
            if (showControls) {
                ToteatCounterCompact(
                    quantity = quantity,
                    onIncrement = onIncrement,
                    onDecrement = onDecrement,
                    enabled = enabled,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_counter" else ""
                )
            } else if (quantity > 0) {
                ProductRowBadge(
                    quantity = quantity,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_badge" else ""
                )
            }
        }
    }
}

private val BadgeSize = 36.dp

@Composable
private fun ProductRowBadge(
    quantity: Int,
    testTag: String = ""
) {
    Box(
        modifier = Modifier
            .size(BadgeSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.extended.counterContainer)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}
