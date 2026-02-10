package com.toteat.toteatds.components.topbar

import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.restaurant_description
import designsystemmobile.toteatds.generated.resources.topbar_semantic_label
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CenterContentTopBar(
    modifier: Modifier = Modifier,
    semanticLabel: String = "",
    testTag: String = "",
    content: @Composable () -> Unit
) {
    val resolvedLabel = semanticLabel.ifEmpty {
        stringResource(Res.string.topbar_semantic_label)
    }

    ToteatTopBar(
        modifier = modifier,
        semanticLabel = resolvedLabel,
        centerComponent = {
            content()
        },
        testTag = testTag
    )
}

@Composable
fun RestaurantNameTopBarItem(
    restaurantName: String,
    modifier: Modifier = Modifier
) {
    val description = stringResource(Res.string.restaurant_description, restaurantName)

    Row(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .semantics(mergeDescendants = true) {
                contentDescription = description
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = restaurantName,
            modifier = Modifier
                .weight(1f, fill = false)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    spacing = MarqueeSpacing(8.dp)
                ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProcessNameTopBarItem(
    processName: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = processName,
        modifier = modifier
            .basicMarquee(
                iterations = Int.MAX_VALUE,
                spacing = MarqueeSpacing(8.dp)
            )
            .semantics {
                heading()
                contentDescription = processName
            },
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSecondary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center
    )
}


@Composable
@Preview
private fun CenterContentTopBarRestaurantPreview() {
    ToteatTheme {
        CenterContentTopBar(
            content = {
                RestaurantNameTopBarItem(
                    restaurantName = "Kintaro ramen bar",
                )
            }
        )
    }
}

@Composable
@Preview
private fun CenterContentTopBarRestaurantLongNamePreview() {
    ToteatTheme {
        CenterContentTopBar(
            content = {
                RestaurantNameTopBarItem(
                    restaurantName = "Restaurante con nombre súper largo que debería mostrarse con marquee",
                )
            }
        )
    }
}

@Composable
@Preview
private fun CenterContentTopBarProcessPreview() {
    ToteatTheme {
        CenterContentTopBar(
            content = {
                ProcessNameTopBarItem(
                    processName = "Checkout",
                )
            }
        )
    }
}

@Composable
@Preview
private fun CenterContentTopBarProcessLongNamePreview() {
    ToteatTheme {
        CenterContentTopBar(
            content = {
                ProcessNameTopBarItem(
                    processName = "Proceso de confirmación y pago completo de la cuenta"
                )
            }
        )
    }
}
