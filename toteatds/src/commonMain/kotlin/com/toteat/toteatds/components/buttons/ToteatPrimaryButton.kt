package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.PrimaryLight
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import org.jetbrains.compose.ui.tooling.preview.Preview

private val ButtonShape = RoundedCornerShape(50)
private val ButtonHeight = 48.dp

/**
 * @param contentPadding Espaciado interno entre el borde del botón y su contenido.
 * Por defecto usa [ButtonDefaults.ContentPadding]. Conviene reducirlo cuando el botón tiene poco
 * ancho (por ejemplo, dos botones con `weight(1f)` en pantallas de ~360 dp) para que el
 * texto no se corte.
 */
@Composable
fun ToteatPrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String? = null,
    contentDescription: String? = null,
    testTag: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val containerColor = when {
        !enabled -> NeutralGray300
        isPressed -> PrimaryLight
        else -> MaterialTheme.colorScheme.primary
    }
    val contentColor = if (enabled) MaterialTheme.colorScheme.onPrimary else NeutralGray

    Button(
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
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = NeutralGray300,
            disabledContentColor = NeutralGray
        ),
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

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 360)
private fun ToteatButtonsReducedPaddingPreview() {
    val reducedPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ToteatTheme {
        Row(
            modifier = Modifier
                .width(360.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToteatTertiaryButton(
                onClick = {},
                text = "Volver a la mesa",
                modifier = Modifier.weight(1f),
                contentPadding = reducedPadding
            )
            ToteatPrimaryButton(
                onClick = {},
                text = "Cobrar pendiente",
                modifier = Modifier.weight(1f),
                contentPadding = reducedPadding
            )
        }
    }
}
