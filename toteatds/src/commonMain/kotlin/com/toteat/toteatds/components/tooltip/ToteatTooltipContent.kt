package com.toteat.toteatds.components.tooltip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.helperBold
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.tooltip_close_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val TagShape = RoundedCornerShape(4.dp)
private const val TagBackgroundAlpha = 0.12f

/**
 * Standard content of a [ToteatTooltipBox] bubble: an optional highlight tag (e.g. "Nuevo") next to
 * the title, a message below and a close button on the trailing edge.
 *
 * @param title Bold title of the tooltip.
 * @param message Supporting text.
 * @param onClose Invoked when the close button is tapped.
 * @param modifier Modifier applied to the root row.
 * @param tagText Optional short label shown before the title, rendered uppercase. `null` hides it.
 * @param testTag Optional test tag. Derived tags: `_tag`, `_title`, `_message`, `_close`.
 */
@Composable
fun ToteatTooltipContent(
    title: String,
    message: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    tagText: String? = null,
    testTag: String = ""
) {
    val closeDescription = stringResource(Res.string.tooltip_close_description)

    Row(modifier = modifier, verticalAlignment = Alignment.Top) {
        Column(
            modifier = Modifier.weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tagText?.let {
                    ToteatTooltipTag(
                        text = it,
                        testTag = if (testTag.isNotEmpty()) "${testTag}_tag" else ""
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .semantics { heading() }
                        .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_title") else Modifier)
                )
            }
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLargeRegular,
                color = MaterialTheme.colorScheme.extended.neutral500,
                modifier = Modifier
                    .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_message") else Modifier)
            )
        }

        Spacer(Modifier.width(12.dp))

        IconButton(
            onClick = onClose,
            modifier = Modifier
                .size(24.dp)
                .semantics { contentDescription = closeDescription }
                .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_close") else Modifier)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun ToteatTooltipTag(
    text: String,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.helperBold,
        letterSpacing = 0.5.sp,
        color = MaterialTheme.colorScheme.primary,
        maxLines = 1,
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary.copy(alpha = TagBackgroundAlpha), TagShape)
            .padding(horizontal = 6.dp, vertical = 2.dp)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    )
}

@Composable
@Preview
private fun ToteatTooltipContentPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ToteatTooltipContent(
                title = "Chat con cocina",
                message = "Envía comentarios a la impresora que necesites.",
                tagText = "Nuevo",
                onClose = {}
            )
            ToteatTooltipContent(
                title = "Sin tag",
                message = "El tag es opcional.",
                onClose = {}
            )
        }
    }
}
