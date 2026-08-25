package com.toteat.toteatds.components.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_comment
import designsystemmobile.toteatds.generated.resources.icon_comment_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val DefaultCommentIconSize = 20.dp

/**
 * Outlined chat-bubble icon used by the comment / messaging entry points.
 *
 * @param modifier Modifier applied to the icon.
 * @param size Size of the icon.
 * @param tint Icon color. Defaults to the content color of the surrounding container.
 * @param contentDescription Accessible description. Pass `null` when the icon is decorative and the
 * container already carries the description (e.g. inside [ToteatCommentIconButton]).
 * @param testTag Optional test tag for UI testing.
 */
@Composable
fun CommentIcon(
    modifier: Modifier = Modifier,
    size: Dp = DefaultCommentIconSize,
    tint: Color = LocalContentColor.current,
    contentDescription: String? = stringResource(Res.string.icon_comment_description),
    testTag: String = ""
) {
    Icon(
        imageVector = vectorResource(Res.drawable.icon_comment),
        contentDescription = contentDescription,
        modifier = modifier
            .size(size)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        tint = tint
    )
}

@Composable
@Preview
private fun CommentIconPreview() {
    ToteatTheme {
        CommentIcon()
    }
}
