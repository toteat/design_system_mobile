package com.toteat.toteatds.components.topbar

import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CenterContentTopBar(
    content: @Composable () -> Unit,
) {
    ToteatTopBar(
        centerComponent = {
            content()
        }
    )
}

@Composable
fun RestaurantNameTopBarItem(
    restaurantName: String,
    counter: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = restaurantName,
            modifier = Modifier.basicMarquee(
                iterations = Int.MAX_VALUE,
                spacing = MarqueeSpacing(8.dp)
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.extended.neutral400)
                .then(
                    if (counter.length <= 1) {
                        Modifier.requiredSize(22.dp)
                    } else {
                        Modifier.height(22.dp).padding(horizontal = 6.dp)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = counter,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun ProcessNameTopBarItem(
    processName: String,
) {
    Text(
        text = processName,
        modifier = Modifier.basicMarquee(
            iterations = Int.MAX_VALUE,
            spacing = MarqueeSpacing(8.dp)
        ),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSecondary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
@Preview
fun CenterContentTopBarPreview() {
    ToteatTheme {
        CenterContentTopBar(
            content = {
                RestaurantNameTopBarItem(
                    restaurantName = "Kintaro ramen bar",
                    counter = "2"
                )
            }
        )
    }
}

@Composable
@Preview
fun CenterContentTopBar2Preview() {
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
