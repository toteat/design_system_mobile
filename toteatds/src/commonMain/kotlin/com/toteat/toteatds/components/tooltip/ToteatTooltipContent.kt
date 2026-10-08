package com.toteat.toteatds.components.tooltip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.tooltip_close_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Standard content of a [ToteatTooltipBox] bubble: a bold title, a message below and a close button
 * on the trailing edge.
 *
 * @param title Bold title of the tooltip.
 * @param message Supporting text.
 * @param onClose Invoked when the close button is tapped.
 * @param modifier Modifier applied to the root row.
 * @param testTag Optional test tag. Derived tags: `_title`, `_message`, `_close`.
 */
@Composable
fun ToteatTooltipContent(
    title: String,
    message: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    val closeDescription = stringResource(Res.string.tooltip_close_description)

    Row(modifier = modifier, verticalAlignment = Alignment.Top) {
        Column(
            modifier = Modifier.weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .semantics { heading() }
                    .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_title") else Modifier)
            )
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
                onClose = {}
            )
            ToteatTooltipContent(
                title = "Título más largo que ocupa dos líneas dentro del globo",
                message = "El título se corta en dos líneas como máximo.",
                onClose = {}
            )
        }
    }
}
