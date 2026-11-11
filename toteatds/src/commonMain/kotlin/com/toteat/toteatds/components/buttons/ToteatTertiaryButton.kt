package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.PrimaryNormal
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import org.jetbrains.compose.ui.tooling.preview.Preview

private val ButtonShape = RoundedCornerShape(50)

/**
 * @param size Tamaño del botón. [ToteatButtonSize.Default] (por defecto) mantiene el aspecto de
 * siempre: 48 dp, padding de Material, Bold 14 y borde de 1.5 dp por dentro.
 * [ToteatButtonSize.XS], [ToteatButtonSize.S], [ToteatButtonSize.M] y [ToteatButtonSize.L]
 * aplican el alto, padding, tipografía y colores de `Button/Terciary-*` en Figma, con fondo
 * blanco y el borde dibujado por fuera como en Figma (OUTSIDE): el botón ocupa [ToteatButtonSize.height]
 * en el layout y el borde se ve alrededor, así que un contenedor que recorte justo en el borde
 * del botón lo taparía.
 * @param contentPadding Espaciado interno entre el borde del botón y su contenido. Por defecto
 * es el padding de [size]: [ButtonDefaults.ContentPadding] en [ToteatButtonSize.Default] y el de
 * Figma en los demás tamaños. Un valor explícito reemplaza al de [size]; el alto, la tipografía y
 * los colores siguen siendo los de [size]. Conviene reducirlo cuando el botón tiene poco ancho
 * (por ejemplo, dos botones con `weight(1f)` en pantallas de ~360 dp) para que el texto no se
 * corte.
 */
@Composable
fun ToteatTertiaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String? = null,
    contentDescription: String? = null,
    testTag: String = "",
    size: ToteatButtonSize = ToteatButtonSize.Default,
    contentPadding: PaddingValues = size.contentPadding(ToteatButtonKind.Tertiary),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TertiaryButtonImpl(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        text = text,
        contentDescription = contentDescription,
        testTag = testTag,
        size = size,
        contentPadding = contentPadding,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        previewState = null
    )
}

/** [previewState] fuerza un estado visual (hover, pressed) para las previews. */
@Composable
internal fun TertiaryButtonImpl(
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    text: String?,
    contentDescription: String?,
    testTag: String,
    size: ToteatButtonSize,
    contentPadding: PaddingValues,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    previewState: ToteatButtonState?
) {
    val interactionSource = remember { MutableInteractionSource() }
    val state = toteatButtonState(enabled, interactionSource, previewState)
    val isDefaultSize = size == ToteatButtonSize.Default
    val colors = tertiaryButtonColors(size, state)
    val borderColor = colors.border ?: colors.content

    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(size.height)
            .then(
                if (isDefaultSize) Modifier else Modifier.outsidePillBorder(size.tertiaryBorderWidth, borderColor)
            )
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
        colors = if (isDefaultSize) {
            ButtonDefaults.outlinedButtonColors(
                contentColor = colors.content,
                disabledContentColor = NeutralGray400
            )
        } else {
            ButtonDefaults.outlinedButtonColors(
                containerColor = colors.container,
                contentColor = colors.content,
                disabledContainerColor = colors.container,
                disabledContentColor = colors.content
            )
        },
        // Default dibuja el borde por dentro, como siempre; los tamaños de Figma, por fuera.
        border = if (isDefaultSize) BorderStroke(size.tertiaryBorderWidth, borderColor) else null,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        ToteatButtonContent(
            text = text,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = size.textStyle(),
            gap = size.contentGap(ToteatButtonKind.Tertiary)
        )
    }
}

private fun tertiaryButtonColors(size: ToteatButtonSize, state: ToteatButtonState): ToteatButtonColors =
    if (size == ToteatButtonSize.Default) {
        // Colores previos a los tamaños de Figma: el borde usa el color del contenido.
        // El fondo lo pone outlinedButtonColors (transparente), así que container no se usa.
        val content = when (state) {
            ToteatButtonState.Disabled -> NeutralGray400
            ToteatButtonState.Pressed, ToteatButtonState.Hovered -> PrimaryNormal
            ToteatButtonState.Enabled -> SecondaryNormal
        }
        ToteatButtonColors(container = Color.Transparent, content = content)
    } else {
        // Figma Button/Terciary-*: fondo blanco; en hover solo cambia el borde.
        // Terciary-L disabled tiene el borde en #1B1B1B en Figma, distinto de XS/S/M (Gray400);
        // se usa Gray400 en todos los tamaños.
        when (state) {
            ToteatButtonState.Disabled -> ToteatButtonColors(NeutralGray, NeutralGray400, border = NeutralGray400)
            ToteatButtonState.Pressed -> ToteatButtonColors(NeutralGray, PrimaryNormal, border = PrimaryNormal)
            ToteatButtonState.Hovered -> ToteatButtonColors(NeutralGray, SecondaryNormal, border = PrimaryNormal)
            ToteatButtonState.Enabled -> ToteatButtonColors(NeutralGray, SecondaryNormal, border = SecondaryNormal)
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
