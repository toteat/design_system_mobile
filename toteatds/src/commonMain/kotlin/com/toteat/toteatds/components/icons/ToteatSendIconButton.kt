package com.toteat.toteatds.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_send
import designsystemmobile.toteatds.generated.resources.icon_send_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val DefaultSendButtonSize = 48.dp
private val DefaultSendIconSize = 22.dp

/**
 * Circular send button used by message / comment inputs.
 *
 * The circle uses the brand secondary color (black) with a white paper-plane icon; when disabled it
 * falls back to the neutral disabled surface keeping the white icon.
 *
 * @param onClick Invoked when the button is tapped.
 * @param modifier Modifier applied to the button.
 * @param enabled Whether the action is available.
 * @param size Diameter of the circular container.
 * @param iconSize Size of the send icon.
 * @param testTag Optional test tag for UI testing.
 */
@Composable
fun ToteatSendIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = DefaultSendButtonSize,
    iconSize: Dp = DefaultSendIconSize,
    testTag: String = ""
) {
    val sendDescription = stringResource(Res.string.icon_send_description)

    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .size(size)
            .clip(CircleShape)
            .background(
                color = if (enabled) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.extended.disabledContent
                }
            )
            .semantics {
                role = Role.Button
                contentDescription = sendDescription
            },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.background
        )
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.icon_send),
            contentDescription = null,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Composable
@Preview
private fun ToteatSendIconButtonPreview() {
    ToteatTheme {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ToteatSendIconButton(onClick = {})

            ToteatSendIconButton(
                onClick = {},
                enabled = false
            )
        }
    }
}
