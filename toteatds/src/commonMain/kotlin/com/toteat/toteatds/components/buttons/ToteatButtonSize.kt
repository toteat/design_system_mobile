package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.layout.layout
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Tamaño de [ToteatPrimaryButton], [ToteatSecondaryButton] y [ToteatTertiaryButton].
 *
 * [Default] es el aspecto que los botones tenían antes de existir este parámetro (48 dp de
 * alto, padding de Material, Bold 14 y los colores de siempre), así que las pantallas que no
 * lo pasan no cambian. [XS], [S], [M] y [L] siguen los componentes `Button/Primary-*`,
 * `Button/Secondary-*` y `Button/Terciary-*` de la librería "Design System Toteat" en Figma:
 * alto, padding, separación entre ícono y texto, tipografía y colores de cada estado
 * (hover, pressed = "selected" en Figma, disabled).
 *
 * @property height Alto fijo del botón.
 * @property iconSize Tamaño de ícono que Figma usa en este tamaño. Los botones no lo imponen
 * (los íconos son slots), así que conviene aplicarlo al ícono que se pasa:
 * `leadingIcon = { Icon(..., modifier = Modifier.size(ToteatButtonSize.S.iconSize)) }`.
 * [Dp.Unspecified] en [Default], que no tiene un tamaño de ícono definido.
 */
enum class ToteatButtonSize(val height: Dp, val iconSize: Dp) {
    /** 48 dp, padding de Material, Bold 14. El aspecto previo a los tamaños de Figma. */
    Default(height = 48.dp, iconSize = Dp.Unspecified),

    /** Figma XS: 32 dp, Bold 12, ícono de 16 dp. */
    XS(height = 32.dp, iconSize = 16.dp),

    /** Figma S: 45 dp, Bold 16 (h16-B), ícono de 18 dp. */
    S(height = 45.dp, iconSize = 18.dp),

    /** Figma M: 60 dp, Bold 20 (Titles/20B), ícono de 24 dp. */
    M(height = 60.dp, iconSize = 24.dp),

    /** Figma L: 80 dp, SemiBold 34 (Titles/Display SB), ícono de 30 dp. */
    L(height = 80.dp, iconSize = 30.dp)
}

internal enum class ToteatButtonKind { Primary, Secondary, Tertiary }

/** Estado visual de un botón. Pressed corresponde a la variante "selected" de Figma. */
internal enum class ToteatButtonState { Enabled, Hovered, Pressed, Disabled }

internal data class ToteatButtonColors(
    val container: Color,
    val content: Color,
    val border: Color? = null
)

/**
 * Padding por defecto de cada tamaño. En [ToteatButtonSize.Default] es exactamente
 * [ButtonDefaults.ContentPadding], el valor que el parámetro `contentPadding` tenía antes.
 *
 * XS usa 7 dp de padding vertical en lugar de los 8 de Figma: con 32 dp de alto fijo, 8 + 18
 * (alto de línea de Bold 12 al 150 %) + 8 suma 34 y el texto quedaría recortado. Figma lo
 * permite porque deja que el contenido se desborde.
 */
internal fun ToteatButtonSize.contentPadding(kind: ToteatButtonKind): PaddingValues = when (this) {
    ToteatButtonSize.Default -> ButtonDefaults.ContentPadding
    ToteatButtonSize.XS -> PaddingValues(horizontal = 32.dp, vertical = 7.dp)
    ToteatButtonSize.S, ToteatButtonSize.M ->
        if (kind == ToteatButtonKind.Primary) {
            PaddingValues(horizontal = 32.dp, vertical = 4.dp)
        } else {
            PaddingValues(horizontal = 32.dp, vertical = 8.dp)
        }
    ToteatButtonSize.L -> PaddingValues(horizontal = 68.dp, vertical = 16.dp)
}

/** Separación entre los íconos y el texto. */
internal fun ToteatButtonSize.contentGap(kind: ToteatButtonKind): Dp = when (this) {
    ToteatButtonSize.Default -> 8.dp
    ToteatButtonSize.XS -> 4.dp
    ToteatButtonSize.S -> if (kind == ToteatButtonKind.Primary) 8.dp else 4.dp
    ToteatButtonSize.M -> 4.dp
    ToteatButtonSize.L -> 8.dp
}

@Composable
@ReadOnlyComposable
internal fun ToteatButtonSize.textStyle(): TextStyle = when (this) {
    ToteatButtonSize.Default -> MaterialTheme.typography.bodyLarge
    // Body-base/B12 B y Heading/h16-B tienen alto de línea 150 %.
    ToteatButtonSize.XS -> MaterialTheme.typography.bodyMedium.copy(lineHeight = 18.sp)
    ToteatButtonSize.S -> MaterialTheme.typography.headlineMedium.copy(lineHeight = 24.sp)
    // Titles/20B y Titles/Display SB usan alto de línea automático.
    ToteatButtonSize.M -> MaterialTheme.typography.titleMedium
    ToteatButtonSize.L -> MaterialTheme.typography.displayLarge
}

/** Borde de la variante "selected" (pressed) de Primary y Secondary. */
internal val ToteatButtonSize.pressedBorderWidth: Dp
    get() = if (this == ToteatButtonSize.L) 2.dp else 1.dp

/** Borde del botón terciario. En los tamaños de Figma se dibuja por fuera (OUTSIDE). */
internal val ToteatButtonSize.tertiaryBorderWidth: Dp
    get() = if (this == ToteatButtonSize.XS) 1.dp else 1.5.dp

@Composable
internal fun toteatButtonState(
    enabled: Boolean,
    interactionSource: InteractionSource,
    previewState: ToteatButtonState?
): ToteatButtonState {
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    return when {
        !enabled -> ToteatButtonState.Disabled
        previewState != null -> previewState
        isPressed -> ToteatButtonState.Pressed
        isHovered -> ToteatButtonState.Hovered
        else -> ToteatButtonState.Enabled
    }
}

/**
 * Dibuja un borde de [width] por fuera del botón, como el trazo OUTSIDE de Figma: el botón
 * conserva su alto en el layout y el borde se ve alrededor. Usa un óvalo (pill) porque los
 * botones tienen radio completo.
 *
 * El `layout` que sigue al dibujo mide el contenido y reporta el tamaño ya ajustado a las
 * restricciones. Sin él, el dibujo tomaría el tamaño del nodo interno del botón de Material, que
 * `minimumInteractiveComponentSize` agranda a 48 dp en XS y S, y el borde quedaría más alto que
 * el botón.
 */
internal fun Modifier.outsidePillBorder(width: Dp, color: Color): Modifier = drawBehind {
    val strokePx = width.toPx()
    val half = strokePx / 2
    drawRoundRect(
        color = color,
        topLeft = Offset(-half, -half),
        size = Size(size.width + strokePx, size.height + strokePx),
        cornerRadius = CornerRadius((size.height + strokePx) / 2),
        style = Stroke(width = strokePx)
    )
}.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    // placeable.width/height are coerced to the constraints; place() centers a larger child.
    layout(placeable.width, placeable.height) { placeable.place(0, 0) }
}

@Composable
internal fun ToteatButtonContent(
    text: String?,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    textStyle: TextStyle,
    gap: Dp
) {
    if (leadingIcon != null) {
        leadingIcon()
        if (text != null) Spacer(Modifier.width(gap))
    }
    if (text != null) {
        Text(
            text = text,
            style = textStyle,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
    if (trailingIcon != null) {
        Spacer(Modifier.width(gap))
        trailingIcon()
    }
}
