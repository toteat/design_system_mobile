package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.PrimaryNormal
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.tagSemiBold
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.chip_not_selected
import designsystemmobile.toteatds.generated.resources.chip_selected
import designsystemmobile.toteatds.generated.resources.diner_add
import designsystemmobile.toteatds.generated.resources.diner_button_description
import designsystemmobile.toteatds.generated.resources.diner_view_more
import designsystemmobile.toteatds.generated.resources.icon_eye_open
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Content variants for [ToteatDinerButton]:
 * - [Diner]: shows the first letter of the diner's name inside the circle.
 * - [Add]: shows a plus icon, used to add a new diner.
 * - [ViewMore]: shows an eye icon, used to see the full diner list.
 */
sealed interface ToteatDinerButtonType {
    data class Diner(val name: String) : ToteatDinerButtonType
    data object Add : ToteatDinerButtonType
    data object ViewMore : ToteatDinerButtonType
}

// Figma "card-option" (Toteat Go, 23838:17777): 40 dp circle with a 1 dp border, Bold 12
// initial, SemiBold 10 name 2 dp below. Figma's name is 40 dp wide; 56 dp keeps ~9 visible
// characters (as the previous 64 dp at Regular 12 did) while bringing the circles closer.
private val DinerCircleSize = 40.dp
private val DinerBorderWidth = 1.dp
private val DinerViewMoreIconSize = 16.dp
private val DinerAddGlyphOffset = (-4).dp
private val DinerLabelWidth = 56.dp
private val DinerLabelSpacing = 2.dp

// The name fills the full width, so the rounded corners of the pressed area would cut its
// first and last letters. Extra space at the bottom keeps the name above those corners.
private val DinerPressedAreaBottomPadding = 6.dp

/**
 * Circular button used to assign an order item to a diner.
 *
 * Renders a circle (initial, plus icon or eye icon depending on [type])
 * with a label underneath. When [isSelected] is true the circle border,
 * content and label switch to the primary brand color.
 */
@Composable
fun ToteatDinerButton(
    type: ToteatDinerButtonType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    enabled: Boolean = true,
    testTag: String = ""
) {
    val label = when (type) {
        is ToteatDinerButtonType.Diner -> type.name
        ToteatDinerButtonType.Add -> stringResource(Res.string.diner_add)
        ToteatDinerButtonType.ViewMore -> stringResource(Res.string.diner_view_more)
    }

    val contentColor = when {
        !enabled -> NeutralGray300
        isSelected -> PrimaryNormal
        else -> SecondaryNormal
    }

    val selectedText = stringResource(Res.string.chip_selected)
    val notSelectedText = stringResource(Res.string.chip_not_selected)
    val dinerDescription = when (type) {
        is ToteatDinerButtonType.Diner -> stringResource(Res.string.diner_button_description, type.name)
        else -> label
    }
    val accessibilityDescription = remember(dinerDescription, isSelected, selectedText, notSelectedText) {
        if (isSelected) "$dinerDescription, $selectedText" else "$dinerDescription, $notSelectedText"
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(DinerLabelSpacing),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                role = Role.Button
            )
            .semantics {
                role = Role.Button
                selected = isSelected
                contentDescription = accessibilityDescription
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .padding(bottom = DinerPressedAreaBottomPadding)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(DinerCircleSize)
                .border(
                    width = DinerBorderWidth,
                    color = contentColor,
                    shape = CircleShape
                )
        ) {
            when (type) {
                is ToteatDinerButtonType.Diner -> Text(
                    text = type.name.take(1).uppercase(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor
                )

                // Figma draws the "+" as text (Titles/Display SB, SemiBold 34), not as an icon.
                // The line height is trimmed so the text box fits the 40 dp circle; Manrope draws
                // "+" 4 dp below the middle of that box, so it is moved up to sit centered.
                // It carries no semantics, like the decorative icons.
                ToteatDinerButtonType.Add -> Text(
                    text = "+",
                    style = MaterialTheme.typography.displayLarge.copy(
                        lineHeight = MaterialTheme.typography.displayLarge.fontSize,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.Both
                        )
                    ),
                    color = contentColor,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Visible,
                    modifier = Modifier
                        .offset(y = DinerAddGlyphOffset)
                        .clearAndSetSemantics {}
                )

                ToteatDinerButtonType.ViewMore -> Icon(
                    imageVector = vectorResource(Res.drawable.icon_eye_open),
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(DinerViewMoreIconSize)
                )
            }
        }

        Text(
            text = label,
            style = MaterialTheme.typography.tagSemiBold,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(DinerLabelWidth)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ToteatDinerButtonPreview() {
    ToteatTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ToteatDinerButton(
                type = ToteatDinerButtonType.Add,
                onClick = {}
            )
            ToteatDinerButton(
                type = ToteatDinerButtonType.Diner("Trini"),
                isSelected = true,
                onClick = {}
            )
            ToteatDinerButton(
                type = ToteatDinerButtonType.Diner("Pauli"),
                onClick = {}
            )
            ToteatDinerButton(
                type = ToteatDinerButtonType.Diner("Comensal 1"),
                enabled = false,
                onClick = {}
            )
            ToteatDinerButton(
                type = ToteatDinerButtonType.ViewMore,
                onClick = {}
            )
        }
    }
}
