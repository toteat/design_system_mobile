package com.toteat.toteatds.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.tags.StatusTagVariant
import com.toteat.toteatds.components.topbar.ToteatTopBar
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.tagBold
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_comment
import designsystemmobile.toteatds.generated.resources.icon_comment_badge_description
import designsystemmobile.toteatds.generated.resources.icon_comment_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val BadgeShape = RoundedCornerShape(50)
private val BadgeHorizontalPadding = 6.dp
private val BadgeVerticalPadding = 1.dp

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
 * @param badgeText Optional short label (e.g. "Nuevo") drawn uppercase over the top-end corner of the
 * button. It does not change the size the button takes in its parent nor its touch area, does not
 * receive taps, and is appended to the button's accessible description. `null` hides it.
 * @param testTag Optional test tag for UI testing. Derived tags: `_badge`.
 */
@Composable
fun ToteatCommentIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = CircularIconButtonSize,
    iconSize: Dp = CircularIconButtonIconSize,
    badgeText: String? = null,
    testTag: String = ""
) {
    val contentDescription = if (badgeText != null) {
        stringResource(Res.string.icon_comment_badge_description, badgeText)
    } else {
        stringResource(Res.string.icon_comment_description)
    }

    Box(modifier = modifier, propagateMinConstraints = true) {
        ToteatCircularIconButton(
            onClick = onClick,
            imageVector = vectorResource(Res.drawable.icon_comment),
            contentDescription = contentDescription,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.secondary,
            enabled = enabled,
            size = size,
            iconSize = iconSize,
            testTag = testTag
        )

        if (badgeText != null) {
            CommentIconButtonBadge(
                text = badgeText,
                modifier = Modifier.align(Alignment.TopEnd),
                testTag = if (testTag.isNotEmpty()) "${testTag}_badge" else ""
            )
        }
    }
}

/**
 * Pill label drawn over the top-end corner of [ToteatCommentIconButton].
 *
 * It reports a 0x0 size to its parent and draws its content ending at that point, so a wide label
 * never grows the button. It is decorative for accessibility (the button already announces the text)
 * and has no pointer input, so taps on it fall through to the button underneath.
 */
@Composable
private fun CommentIconButtonBadge(
    text: String,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.tagBold,
        color = StatusTagVariant.Promotion.textColor,
        maxLines = 1,
        modifier = modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
                layout(0, 0) {
                    placeable.place(-placeable.width, 0)
                }
            }
            .clearAndSetSemantics { }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .background(StatusTagVariant.Promotion.backgroundColor, BadgeShape)
            .padding(horizontal = BadgeHorizontalPadding, vertical = BadgeVerticalPadding)
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

            ToteatCommentIconButton(
                onClick = {},
                badgeText = "Nuevo"
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
                ToteatCommentIconButton(onClick = {}, badgeText = "Nuevo")
            }
        )
    }
}
