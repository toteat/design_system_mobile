package com.toteat.toteatds.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.topbar.ToteatTopBar
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_comment
import designsystemmobile.toteatds.generated.resources.icon_comment_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Circular comment button used as the navigation-bar entry point to the conversation.
 *
 * Inverts the palette of the other circular actions: a white circle with the outlined chat bubble in
 * the brand secondary color, so it reads over the dark navigation bar. When disabled it falls back
 * to the neutral disabled surface, like [ToteatSendIconButton] and [ToteatPrintIconButton], with
 * which it shares the geometry through [ToteatCircularIconButton].
 *
 * @param onClick Invoked when the button is tapped.
 * @param modifier Modifier applied to the button.
 * @param enabled Whether the action is available.
 * @param size Diameter of the circular container.
 * @param iconSize Size of the chat-bubble icon.
 * @param testTag Optional test tag for UI testing.
 */
@Composable
fun ToteatCommentIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = CircularIconButtonSize,
    iconSize: Dp = CircularIconButtonIconSize,
    testTag: String = ""
) {
    ToteatCircularIconButton(
        onClick = onClick,
        imageVector = vectorResource(Res.drawable.icon_comment),
        contentDescription = stringResource(Res.string.icon_comment_description),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.secondary,
        modifier = modifier,
        enabled = enabled,
        size = size,
        iconSize = iconSize,
        testTag = testTag
    )
}

@Composable
@Preview
private fun ToteatCommentIconButtonPreview() {
    ToteatTheme {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ToteatCommentIconButton(onClick = {})

            ToteatCommentIconButton(
                onClick = {},
                enabled = false
            )
        }
    }
}

@Composable
@Preview
private fun ToteatCommentIconButtonInTopBarPreview() {
    ToteatTheme {
        ToteatTopBar(
            centerComponent = {
                Text(
                    text = "Mesa S7",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            rightComponent = {
                ToteatCommentIconButton(onClick = {})
            }
        )
    }
}
