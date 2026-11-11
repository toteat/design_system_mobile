package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.utils.setTestTag

private val ButtonShape = RoundedCornerShape(50)

/**
 * @param size Tamaño del botón. [ToteatButtonSize.Default] (por defecto) mantiene el aspecto de
 * siempre: 48 dp, padding de Material y Bold 14. [ToteatButtonSize.XS], [ToteatButtonSize.S],
 * [ToteatButtonSize.M] y [ToteatButtonSize.L] aplican el alto, padding, tipografía y colores
 * de `Button/Secondary-*` en Figma.
 * @param contentPadding Espaciado interno entre el borde del botón y su contenido. Por defecto
 * es el padding de [size]: [ButtonDefaults.ContentPadding] en [ToteatButtonSize.Default] y el de
 * Figma en los demás tamaños. Un valor explícito reemplaza al de [size]; el alto, la tipografía y
 * los colores siguen siendo los de [size]. Conviene reducirlo cuando el botón tiene poco ancho
 * (por ejemplo, dos botones con `weight(1f)` en pantallas de ~360 dp) para que el texto no se
 * corte.
 */
@Composable
fun ToteatSecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String? = null,
    contentDescription: String? = null,
    testTag: String = "",
    size: ToteatButtonSize = ToteatButtonSize.Default,
    contentPadding: PaddingValues = size.contentPadding(ToteatButtonKind.Secondary),
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    SecondaryButtonImpl(
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
internal fun SecondaryButtonImpl(
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
    val colors = secondaryButtonColors(size, state)

    Button(
        onClick = onClick,
        modifier = modifier
            .height(size.height)
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
            containerColor = colors.container,
            contentColor = colors.content,
            disabledContainerColor = colors.container,
            disabledContentColor = colors.content
        ),
        border = colors.border?.let { BorderStroke(size.pressedBorderWidth, it) },
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        ToteatButtonContent(
            text = text,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = size.textStyle(),
            gap = size.contentGap(ToteatButtonKind.Secondary)
        )
    }
}

private fun secondaryButtonColors(size: ToteatButtonSize, state: ToteatButtonState): ToteatButtonColors =
    if (size == ToteatButtonSize.Default) {
        // Colores previos a los tamaños de Figma; Default no reacciona al hover.
        when (state) {
            ToteatButtonState.Disabled -> ToteatButtonColors(NeutralGray300, NeutralGray500)
            ToteatButtonState.Pressed -> ToteatButtonColors(NeutralGray500, NeutralGray)
            ToteatButtonState.Hovered, ToteatButtonState.Enabled -> ToteatButtonColors(SecondaryNormal, NeutralGray)
        }
    } else {
        // Figma Button/Secondary-*: default, hover, selected y disabled.
        when (state) {
            ToteatButtonState.Disabled -> ToteatButtonColors(NeutralGray200, NeutralGray400)
            ToteatButtonState.Pressed -> ToteatButtonColors(SecondaryNormal, NeutralGray, border = NeutralGray300)
            ToteatButtonState.Hovered -> ToteatButtonColors(NeutralGray400, NeutralGray)
            ToteatButtonState.Enabled -> ToteatButtonColors(SecondaryNormal, NeutralGray)
        }
    }
