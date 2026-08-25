package com.toteat.toteatds.components.messageview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.BlueNormal
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyMediumRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.headingMediumRegular
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.comment_bubble_sent_description
import designsystemmobile.toteatds.generated.resources.icon_double_check
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val BubbleShape = RoundedCornerShape(16.dp)
private val BubbleHorizontalPadding = 16.dp
private val BubbleVerticalPadding = 12.dp
private val BubbleContentSpacing = 8.dp
private val BubbleInfoSpacing = 4.dp
private val SentIconSize = 18.dp

/**
 * Sent comment bubble used in comment / messaging screens (e.g. "Comunicación cocina").
 *
 * Renders the message over the bubble surface and, aligned to the end, the optional [info] line with
 * the delivery reference (printer, station, hour...) plus the double check when the message was
 * already delivered. [variant] picks between the two states of the design: a white card with a soft
 * shadow and a regular-weight message ([ToteatCommentBubbleVariant.Default]) and a flat cream
 * container with the message in bold ([ToteatCommentBubbleVariant.Highlighted]), so the host can
 * highlight the comments that still need attention. The bubble keeps no state: what it shows is
 * what the host gives it.
 *
 * @param message The comment sent by the user.
 * @param modifier Modifier applied to the root container.
 * @param info Delivery reference rendered under the message (e.g. "Impresora bar vip - 18:00").
 * Pass `null` to hide the line.
 * @param isSent Whether the double check is shown next to [info].
 * @param variant Visual state of the bubble.
 * @param containerColor Overrides the [variant] background. Pass `null` to keep it.
 * @param testTag Optional test tag for UI testing. Derived tags: `_message`, `_info`, `_sent`.
 */
@Composable
fun ToteatCommentBubble(
    message: String,
    modifier: Modifier = Modifier,
    info: String? = null,
    isSent: Boolean = false,
    variant: ToteatCommentBubbleVariant = ToteatCommentBubbleVariant.Default,
    containerColor: Color? = null,
    testTag: String = ""
) {
    val sentDescription = stringResource(Res.string.comment_bubble_sent_description)
    val messageStyle = when (variant) {
        ToteatCommentBubbleVariant.Default -> MaterialTheme.typography.headingMediumRegular
        ToteatCommentBubbleVariant.Highlighted -> MaterialTheme.typography.headlineMedium
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (variant.elevation > 0.dp) {
                    Modifier.shadow(elevation = variant.elevation, shape = BubbleShape)
                } else {
                    Modifier
                }
            )
            .clip(BubbleShape)
            .background(containerColor ?: variant.containerColor)
            .semantics(mergeDescendants = true) { }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .padding(
                horizontal = BubbleHorizontalPadding,
                vertical = BubbleVerticalPadding
            ),
        verticalArrangement = Arrangement.spacedBy(BubbleContentSpacing)
    ) {
        Text(
            text = message,
            style = messageStyle,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_message") else Modifier
                )
        )

        if (info != null || isSent) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(BubbleInfoSpacing, Alignment.End)
            ) {
                if (info != null) {
                    Text(
                        text = info,
                        style = MaterialTheme.typography.bodyMediumRegular,
                        color = MaterialTheme.colorScheme.extended.neutral400,
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .then(
                                if (testTag.isNotEmpty()) {
                                    Modifier.setTestTag("${testTag}_info")
                                } else {
                                    Modifier
                                }
                            )
                    )
                }

                if (isSent) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.icon_double_check),
                        contentDescription = sentDescription,
                        tint = BlueNormal,
                        modifier = Modifier
                            .size(SentIconSize)
                            .then(
                                if (testTag.isNotEmpty()) {
                                    Modifier.setTestTag("${testTag}_sent")
                                } else {
                                    Modifier
                                }
                            )
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun ToteatCommentBubbleStatesPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ToteatCommentBubble(
                message = "Cenicero para la mesa",
                info = "Impresora bar vip - 18:00",
                isSent = true
            )
            ToteatCommentBubble(
                message = "Sin picante",
                info = "Impresora bar vip - 18:20",
                isSent = true,
                variant = ToteatCommentBubbleVariant.Highlighted
            )
        }
    }
}

@Composable
@Preview
private fun ToteatCommentBubblePendingPreview() {
    ToteatTheme {
        ToteatCommentBubble(
            message = "Sin cebolla",
            info = "Enviando...",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
@Preview
private fun ToteatCommentBubbleOnlyMessagePreview() {
    ToteatTheme {
        ToteatCommentBubble(
            message = "Mesa lista para el postre",
            variant = ToteatCommentBubbleVariant.Highlighted,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
@Preview
private fun ToteatCommentBubbleLongContentPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ToteatCommentBubble(
                message = "La mesa 12 pidió el lomo término medio y sin salsa, " +
                    "además de una guarnición extra de papas fritas",
                info = "Impresora cocina caliente - 18:04",
                isSent = true
            )
            ToteatCommentBubble(
                message = "Sin sal",
                info = "Impresora de la barra del segundo piso vip - 18:07",
                isSent = true,
                variant = ToteatCommentBubbleVariant.Highlighted
            )
        }
    }
}
