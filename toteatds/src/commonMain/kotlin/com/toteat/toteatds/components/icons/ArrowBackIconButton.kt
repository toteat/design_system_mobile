package com.toteat.toteatds.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_arrow_back
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val DefaultArrowBackSize = CircularIconButtonSize
private val DefaultArrowBackIconSize = CircularIconButtonIconSize

/**
 * Circular back button.
 *
 * The circle is drawn by this composable instead of delegating to Material3's `IconButton`: that one
 * inflates its own container to the 48.dp minimum touch target, which would ignore a [size] smaller
 * than 48.dp and always paint a 48.dp circle. Here [minimumInteractiveComponentSize] reserves the
 * touch target *around* the circle, so the visual diameter is exactly [size].
 *
 * @param size Diameter of the circular container. Defaults to the shared 36.dp circle of the other
 * circular buttons ([ToteatSendIconButton], [ToteatCommentIconButton]...).
 * @param iconSize Size of the arrow. Defaults to the shared 22.dp icon.
 */
@Composable
fun ArrowBackIconButton(
    onNavigateBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = DefaultArrowBackSize,
    iconSize: Dp = DefaultArrowBackIconSize,
    testTag: String = ""
) {
    val arrowBackDescription = stringResource(Res.string.icon_arrow_back)
    val containerColor = if (enabled) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    }
    val contentColor = if (enabled) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.38f)
    }

    Box(
        modifier = modifier
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .minimumInteractiveComponentSize()
            .size(size)
            .clip(CircleShape)
            .background(color = containerColor)
            .clickable(
                enabled = enabled,
                onClick = onNavigateBackClick,
                role = Role.Button
            )
            .semantics {
                role = Role.Button
                contentDescription = arrowBackDescription
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = contentColor
        )
    }
}

@Preview
@Composable
private fun ArrowBackIconButtonPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ArrowBackIconButton(
                onNavigateBackClick = {}
            )

            ArrowBackIconButton(
                onNavigateBackClick = {},
                enabled = false
            )

            ArrowBackIconButton(
                onNavigateBackClick = {},
                size = 48.dp,
                iconSize = 24.dp
            )
        }
    }
}
