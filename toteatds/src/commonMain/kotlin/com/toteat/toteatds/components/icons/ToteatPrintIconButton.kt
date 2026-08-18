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
import designsystemmobile.toteatds.generated.resources.icon_print
import designsystemmobile.toteatds.generated.resources.print_vector
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val DefaultPrintButtonSize = 48.dp
private val DefaultPrintIconSize = 22.dp

/**
 * Circular print button used by comment / messaging screens.
 *
 * The circle uses the brand primary color (orange) with a white printer icon; when disabled it falls
 * back to the neutral disabled surface keeping the white icon. Complements [ToteatSendIconButton],
 * which shares the same geometry with the secondary (black) container.
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
    size: Dp = DefaultPrintButtonSize,
    iconSize: Dp = DefaultPrintIconSize,
    testTag: String = ""
) {
    val printDescription = stringResource(Res.string.icon_print)

    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .size(size)
            .clip(CircleShape)
            .background(
                color = if (enabled) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.extended.disabledContent
                }
            )
            .semantics {
                role = Role.Button
                contentDescription = printDescription
            },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.background
        )
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.print_vector),
            contentDescription = null,
            modifier = Modifier.size(iconSize)
        )
    }
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
