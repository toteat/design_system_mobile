package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.PrimaryNormal
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.chip_not_selected
import designsystemmobile.toteatds.generated.resources.chip_selected
import designsystemmobile.toteatds.generated.resources.diner_add
import designsystemmobile.toteatds.generated.resources.diner_button_description
import designsystemmobile.toteatds.generated.resources.diner_view_more
import designsystemmobile.toteatds.generated.resources.icon_eye_open
import designsystemmobile.toteatds.generated.resources.plus_stroke_icon
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

private val DinerCircleSize = 56.dp
private val DinerBorderWidth = 1.5.dp
private val DinerIconSize = 24.dp
private val DinerLabelWidth = 72.dp

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
        verticalArrangement = Arrangement.spacedBy(8.dp),
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
            .padding(4.dp)
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
                    style = MaterialTheme.typography.titleMedium,
                    color = contentColor
                )

                ToteatDinerButtonType.Add -> Icon(
                    imageVector = vectorResource(Res.drawable.plus_stroke_icon),
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(DinerIconSize)
                )

                ToteatDinerButtonType.ViewMore -> Icon(
                    imageVector = vectorResource(Res.drawable.icon_eye_open),
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(DinerIconSize)
                )
            }
        }

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLargeRegular,
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
                type = ToteatDinerButtonType.Diner("Camila"),
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
