package com.toteat.toteatds.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_send
import designsystemmobile.toteatds.generated.resources.icon_send_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Circular send button used by message / comment inputs.
 *
 * The circle uses the brand secondary color (black) with a white paper-plane icon; when disabled it
 * falls back to the neutral disabled surface keeping the white icon. Shares its geometry and
 * disabled handling with [ToteatPrintIconButton] through [ToteatCircularIconButton].
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
    size: Dp = CircularIconButtonSize,
    iconSize: Dp = CircularIconButtonIconSize,
    testTag: String = ""
) {
    ToteatCircularIconButton(
        onClick = onClick,
        imageVector = vectorResource(Res.drawable.icon_send),
        contentDescription = stringResource(Res.string.icon_send_description),
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        modifier = modifier,
        enabled = enabled,
        size = size,
        iconSize = iconSize,
        testTag = testTag
    )
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
