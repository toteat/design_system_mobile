package com.toteat.toteatds.components.messageview

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.TertiarySurface

/**
 * Visual variants of [ToteatCommentBubble].
 *
 * The two states share the layout and only differ in container, elevation and message weight, so a
 * host can highlight the comments that still need attention (or the last one sent) against the ones
 * already dealt with.
 *
 * @property containerColor Bubble background.
 * @property elevation Shadow drawn under the bubble.
 */
enum class ToteatCommentBubbleVariant(
    val containerColor: Color,
    val elevation: Dp
) {
    /** White card with a soft shadow and the message in regular weight. */
    Default(
        containerColor = NeutralGray,
        elevation = 2.dp
    ),

    /** Cream, flat container with the message in bold. */
    Highlighted(
        containerColor = TertiarySurface,
        elevation = 0.dp
    )
}
