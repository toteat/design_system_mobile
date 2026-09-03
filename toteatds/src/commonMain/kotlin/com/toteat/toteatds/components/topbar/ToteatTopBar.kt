package com.toteat.toteatds.components.topbar
import com.toteat.toteatds.utils.setTestTag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.topbar_semantic_label
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * @param centerFillsRemainingWidth When `false` (default) the three slots split the width 1 / 3 / 1,
 * the historical reparto. When `true` the side slots measure to their content and the center takes
 * whatever is left, for bars whose side content is narrower than a fifth of the width and whose
 * center would otherwise be clipped.
 */
@Composable
fun ToteatTopBar(
    modifier: Modifier = Modifier,
    semanticLabel: String = stringResource(Res.string.topbar_semantic_label),
    testTag: String = "",
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    leftComponent: (@Composable RowScope.() -> Unit)? = null,
    rightComponent: (@Composable RowScope.() -> Unit)? = null,
    centerFillsRemainingWidth: Boolean = false,
    centerComponent: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = containerColor)
            .padding(horizontal = 12.dp)
            .semantics {
                contentDescription = semanticLabel
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val sideModifier = if (centerFillsRemainingWidth) Modifier else Modifier.weight(1f)
        val centerModifier = if (centerFillsRemainingWidth) {
            Modifier.weight(1f)
        } else {
            Modifier.weight(3f)
        }

        Box(
            modifier = sideModifier,
            contentAlignment = Alignment.CenterStart
        ) {
            leftComponent?.invoke(this@Row)
        }
        Box(
            modifier = centerModifier,
            contentAlignment = Alignment.Center
        ) {
            centerComponent.invoke(this@Row)
        }

        Box(
            modifier = sideModifier,
            contentAlignment = Alignment.CenterEnd
        ) {
            rightComponent?.invoke(this@Row)
        }
    }
}

@Preview
@Composable
private fun ToteatTopBarPreview() {
    ToteatTheme {
        Column {
            ToteatTopBar(
                leftComponent = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                },
                centerComponent = {
                    Text(
                        text = "Título Central",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                },
                rightComponent = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            )

            ToteatTopBar(
                centerComponent = {
                    Text(
                        text = "Solo Centro",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            )

            ToteatTopBar(
                leftComponent = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                },
                centerComponent = {
                    Text(
                        text = "Título muy largo que debería adaptarse al espacio disponible",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSecondary,
                        maxLines = 1
                    )
                }
            )
        }
    }
}
