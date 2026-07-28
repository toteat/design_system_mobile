package com.toteat.toteatds.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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

private val DefaultArrowBackSize = 44.dp

/**
 * Circular back button.
 *
 * @param size Diameter of the circular container. The icon scales with it ([iconSize]).
 * @param iconSize Size of the arrow. Defaults to half the container [size].
 */
@Composable
fun ArrowBackIconButton(
    onNavigateBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = DefaultArrowBackSize,
    iconSize: Dp = size / 2,
    testTag: String = ""
) {
    val arrowBackDescription = stringResource(Res.string.icon_arrow_back)

    IconButton(
        onClick = onNavigateBackClick,
        enabled = enabled,
        modifier = modifier
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .size(size)
            .clip(CircleShape)
            .background(
                color = if (enabled) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                }
            )
            .semantics {
                role = Role.Button
                contentDescription = arrowBackDescription
            },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            contentColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.38f)
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Preview
@Composable
private fun ArrowBackIconButtonPreview() {
    ToteatTheme {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
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
                size = 36.dp,
                iconSize = 22.dp
            )
        }
    }
}
