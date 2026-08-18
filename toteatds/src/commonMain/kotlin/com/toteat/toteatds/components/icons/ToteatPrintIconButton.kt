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
import designsystemmobile.toteatds.generated.resources.icon_print
import designsystemmobile.toteatds.generated.resources.print_vector
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Circular print button used by comment / messaging screens.
 *
 * The circle uses the brand primary color (orange) with a white printer icon; when disabled it falls
 * back to the neutral disabled surface keeping the white icon. Shares its geometry and disabled
 * handling with [ToteatSendIconButton] through [ToteatCircularIconButton], and complements the
 * non-clickable [PrintIconButton] icon.
 *
 * @param onClick Invoked when the button is tapped.
 * @param modifier Modifier applied to the button.
 * @param enabled Whether the action is available.
 * @param size Diameter of the circular container.
 * @param iconSize Size of the printer icon.
 * @param testTag Optional test tag for UI testing.
 */
@Composable
fun ToteatPrintIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = CircularIconButtonSize,
    iconSize: Dp = CircularIconButtonIconSize,
    testTag: String = ""
) {
    ToteatCircularIconButton(
        onClick = onClick,
        imageVector = vectorResource(Res.drawable.print_vector),
        contentDescription = stringResource(Res.string.icon_print),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier,
        enabled = enabled,
        size = size,
        iconSize = iconSize,
        testTag = testTag
    )
}

@Composable
@Preview
private fun ToteatPrintIconButtonPreview() {
    ToteatTheme {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ToteatPrintIconButton(onClick = {})

            ToteatPrintIconButton(
                onClick = {},
                enabled = false
            )
        }
    }
}
