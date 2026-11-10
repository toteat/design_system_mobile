package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.PrimaryNormal
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import org.jetbrains.compose.ui.tooling.preview.Preview

private val ButtonShape = RoundedCornerShape(50)
private val ButtonHeight = 48.dp
private val ButtonBorderWidth = 1.5.dp

/**
 * @param contentPadding Espaciado interno entre el borde del botón y su contenido.
 * Por defecto usa [ButtonDefaults.ContentPadding]. Conviene reducirlo cuando el botón tiene poco
 * ancho (por ejemplo, dos botones con `weight(1f)` en pantallas de ~360 dp) para que el
 * texto no se corte.
 */
@Composable
fun ToteatTertiaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String? = null,
    contentDescription: String? = null,
    testTag: String = "",
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val contentColor = when {
        !enabled -> NeutralGray400
        isPressed -> PrimaryNormal
        isHovered -> PrimaryNormal
        else -> SecondaryNormal
    }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(ButtonHeight)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .semantics {
                if (contentDescription != null) {
                    this.contentDescription = contentDescription
                } else if (text != null) {
                    this.contentDescription = text
                }
                role = Role.Button
            },
        enabled = enabled,
        shape = ButtonShape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
            disabledContentColor = NeutralGray400
        ),
        border = BorderStroke(ButtonBorderWidth, contentColor),
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        ButtonContent(text = text, leadingIcon = leadingIcon, trailingIcon = trailingIcon)
    }
}

@Composable
private fun ButtonContent(
    text: String?,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    if (leadingIcon != null) {
        leadingIcon()
        if (text != null) Spacer(Modifier.width(8.dp))
    }
    if (text != null) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
    if (trailingIcon != null) {
        Spacer(Modifier.width(8.dp))
        trailingIcon()
    }
}

/**
 * Cubre en compilación las llamadas con una lambda fuera de los paréntesis: esa lambda
 * debe asignarse a `trailingIcon`, así que `trailingIcon` tiene que seguir siendo el
 * último parámetro de los botones.
 */
@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun ToteatButtonsTrailingLambdaPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToteatTertiaryButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Volver")
            }
            ToteatSecondaryButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Volver")
            }
            ToteatPrimaryButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Volver")
            }
        }
    }
}
