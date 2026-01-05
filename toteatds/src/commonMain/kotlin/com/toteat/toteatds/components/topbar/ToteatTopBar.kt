package com.toteat.toteatds.components.topbar

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.topbar_semantic_label
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatTopBar(
    modifier: Modifier = Modifier,
    semanticLabel: String = stringResource(Res.string.topbar_semantic_label),
    testTag: String? = null,
    leftComponent: (@Composable RowScope.() -> Unit)? = null,
    rightComponent: (@Composable RowScope.() -> Unit)? = null,
    centerComponent: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(horizontal = 12.dp)
            .semantics {
                contentDescription = semanticLabel
            }
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            leftComponent?.invoke(this@Row)
        }
        Box(
            modifier = Modifier.weight(3f),
            contentAlignment = Alignment.Center
        ) {
            centerComponent.invoke(this@Row)
        }

        Box(
            modifier = Modifier.weight(1f),
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
            // Con todos los componentes
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

            // Solo con componente central
            ToteatTopBar(
                centerComponent = {
                    Text(
                        text = "Solo Centro",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            )

            // Con texto largo
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
