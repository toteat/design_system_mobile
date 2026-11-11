package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

private val PreviewStates = listOf(
    "Default" to ToteatButtonState.Enabled,
    "Hover" to ToteatButtonState.Hovered,
    "Pressed" to ToteatButtonState.Pressed,
    "Disabled" to ToteatButtonState.Disabled
)

/** Los tres botones de [size] en cada estado; el último de cada fila lleva ícono. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ButtonSizePreviewContent(size: ToteatButtonSize) {
    ToteatTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ToteatButtonKind.entries.forEach { kind ->
                Text("${kind.name} · ${size.name}")
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PreviewStates.forEach { (label, state) ->
                        PreviewButton(kind = kind, size = size, label = label, state = state)
                    }
                    PreviewButton(
                        kind = kind,
                        size = size,
                        label = "Ícono",
                        state = ToteatButtonState.Enabled,
                        leadingIcon = {
                            val iconSize = if (size == ToteatButtonSize.Default) 24.dp else size.iconSize
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(iconSize))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun PreviewButton(
    kind: ToteatButtonKind,
    size: ToteatButtonSize,
    label: String,
    state: ToteatButtonState,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val enabled = state != ToteatButtonState.Disabled
    val previewState = state.takeIf { enabled }
    val padding = size.contentPadding(kind)
    when (kind) {
        ToteatButtonKind.Primary -> PrimaryButtonImpl(
            onClick = {}, modifier = Modifier, enabled = enabled, text = label, contentDescription = null,
            testTag = "", size = size, contentPadding = padding, leadingIcon = leadingIcon, trailingIcon = null,
            previewState = previewState
        )
        ToteatButtonKind.Secondary -> SecondaryButtonImpl(
            onClick = {}, modifier = Modifier, enabled = enabled, text = label, contentDescription = null,
            testTag = "", size = size, contentPadding = padding, leadingIcon = leadingIcon, trailingIcon = null,
            previewState = previewState
        )
        ToteatButtonKind.Tertiary -> TertiaryButtonImpl(
            onClick = {}, modifier = Modifier, enabled = enabled, text = label, contentDescription = null,
            testTag = "", size = size, contentPadding = padding, leadingIcon = leadingIcon, trailingIcon = null,
            previewState = previewState
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 420)
@Composable
private fun ToteatButtonSizeDefaultPreview() = ButtonSizePreviewContent(ToteatButtonSize.Default)

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 420)
@Composable
private fun ToteatButtonSizeXSPreview() = ButtonSizePreviewContent(ToteatButtonSize.XS)

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 420)
@Composable
private fun ToteatButtonSizeSPreview() = ButtonSizePreviewContent(ToteatButtonSize.S)

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 420)
@Composable
private fun ToteatButtonSizeMPreview() = ButtonSizePreviewContent(ToteatButtonSize.M)

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 600)
@Composable
private fun ToteatButtonSizeLPreview() = ButtonSizePreviewContent(ToteatButtonSize.L)

/** El par "Cancelar" / "Agregar" del modal "Agregar nuevo comensal" (Toteat Go), en tamaño S. */
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 360)
@Composable
private fun ToteatButtonSizeSModalPreview() {
    ToteatTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ToteatTertiaryButton(onClick = {}, text = "Cancelar", size = ToteatButtonSize.S)
            ToteatPrimaryButton(onClick = {}, text = "Agregar", size = ToteatButtonSize.S)
        }
    }
}
