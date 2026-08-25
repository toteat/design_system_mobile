package com.toteat.toteatds.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag

internal val CircularIconButtonSize = 36.dp
internal val CircularIconButtonIconSize = 22.dp

/**
 * Shared implementation of the circular action buttons used by comment / messaging screens
 * ([ToteatSendIconButton], [ToteatPrintIconButton], [ToteatCommentIconButton]).
 *
 * Renders [imageVector] centered on a filled circle. While enabled the circle uses [containerColor]
 * with [contentColor] for the icon; when disabled both variants fall back to the neutral disabled
 * surface keeping a white icon, so a single contract governs the disabled look.
 *
 * The circle is drawn by this composable instead of delegating to Material3's `IconButton`: that one
 * inflates its own container to the 48.dp minimum touch target, which would ignore a [size] smaller
 * than 48.dp and always paint a 48.dp circle. Here [minimumInteractiveComponentSize] reserves the
 * touch target *around* the circle, so the visual diameter is exactly [size].
 *
 * @param onClick Invoked when the button is tapped.
 * @param imageVector Icon drawn inside the circle.
 * @param contentDescription Accessible description announced for the button.
 * @param containerColor Circle color while [enabled].
 * @param contentColor Icon color while [enabled].
 * @param modifier Modifier applied to the button.
 * @param enabled Whether the action is available.
 * @param size Diameter of the circular container.
 * @param iconSize Size of the icon.
 * @param testTag Optional test tag for UI testing.
 */
@Composable
internal fun ToteatCircularIconButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = CircularIconButtonSize,
    iconSize: Dp = CircularIconButtonIconSize,
    testTag: String = ""
) {
    val buttonDescription = contentDescription
    val resolvedContainerColor = if (enabled) {
        containerColor
    } else {
        MaterialTheme.colorScheme.extended.disabledContent
    }
    val resolvedContentColor = if (enabled) {
        contentColor
    } else {
        MaterialTheme.colorScheme.background
    }

    Box(
        modifier = modifier
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .minimumInteractiveComponentSize()
            .size(size)
            .clip(CircleShape)
            .background(color = resolvedContainerColor)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                role = Role.Button
            )
            .semantics {
                role = Role.Button
                this.contentDescription = buttonDescription
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = resolvedContentColor
        )
    }
}
