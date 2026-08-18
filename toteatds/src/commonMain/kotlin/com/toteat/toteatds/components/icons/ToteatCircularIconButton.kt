package com.toteat.toteatds.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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

internal val CircularIconButtonSize = 48.dp
internal val CircularIconButtonIconSize = 22.dp

/**
 * Shared implementation of the circular action buttons used by comment / messaging screens
 * ([ToteatSendIconButton], [ToteatPrintIconButton]).
 *
 * Renders [imageVector] centered on a filled circle. While enabled the circle uses [containerColor]
 * with [contentColor] for the icon; when disabled both variants fall back to the neutral disabled
 * surface keeping a white icon, so a single contract governs the disabled look.
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

    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .size(size)
            .clip(CircleShape)
            .background(
                color = if (enabled) {
                    containerColor
                } else {
                    MaterialTheme.colorScheme.extended.disabledContent
                }
            )
            .semantics {
                role = Role.Button
                this.contentDescription = buttonDescription
            },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = contentColor,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.background
        )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.size(iconSize)
        )
    }
}
