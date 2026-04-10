package com.toteat.toteatds.components.keypad

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray100
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_delete_arrow
import designsystemmobile.toteatds.generated.resources.numeric_keypad_action_description
import designsystemmobile.toteatds.generated.resources.numeric_keypad_delete_description
import designsystemmobile.toteatds.generated.resources.numeric_keypad_description
import designsystemmobile.toteatds.generated.resources.numeric_keypad_number_description
import designsystemmobile.toteatds.generated.resources.plus_stroke_icon
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val KeypadShape = RoundedCornerShape(14.dp)
private val KeypadCellMinHeight = 64.dp

/**
 * Numeric keypad component with a 4×3 grid layout.
 *
 * Rows 1-3 contain digits 1-9, and the bottom row contains
 * a delete (backspace) button, digit 0, and a custom action button (+).
 *
 * @param onNumberClick Called when a digit (0-9) is pressed.
 * @param onDeleteClick Called when the backspace/delete button is pressed.
 * @param onActionClick Called when the action (+) button is pressed.
 * @param modifier Modifier applied to the root container.
 * @param actionIcon Optional composable icon for the action button. Defaults to a "+" text.
 * @param enabled Whether the keypad is interactive.
 * @param testTag Optional test tag for UI testing.
 */
@Composable
fun ToteatNumericKeypad(
    onNumberClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    actionIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    testTag: String = ""
) {
    val keypadDescription = stringResource(Res.string.numeric_keypad_description)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(KeypadShape)
            .background(NeutralGray)
            .border(1.dp, NeutralGray200, KeypadShape)
            .semantics { contentDescription = keypadDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        // Row 1: 1, 2, 3
        KeypadRow(
            numbers = intArrayOf(1, 2, 3),
            onNumberClick = onNumberClick,
            enabled = enabled,
            testTag = testTag
        )
        HorizontalDivider(color = NeutralGray100, thickness = 1.dp)

        // Row 2: 4, 5, 6
        KeypadRow(
            numbers = intArrayOf(4, 5, 6),
            onNumberClick = onNumberClick,
            enabled = enabled,
            testTag = testTag
        )
        HorizontalDivider(color = NeutralGray100, thickness = 1.dp)

        // Row 3: 7, 8, 9
        KeypadRow(
            numbers = intArrayOf(7, 8, 9),
            onNumberClick = onNumberClick,
            enabled = enabled,
            testTag = testTag
        )
        HorizontalDivider(color = NeutralGray100, thickness = 1.dp)

        // Row 4: delete, 0, action
        BottomRow(
            onNumberClick = onNumberClick,
            onDeleteClick = onDeleteClick,
            onActionClick = onActionClick,
            actionIcon = actionIcon,
            enabled = enabled,
            testTag = testTag
        )
    }
}

@Composable
private fun KeypadRow(
    numbers: IntArray,
    onNumberClick: (Int) -> Unit,
    enabled: Boolean,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        numbers.forEachIndexed { index, number ->
            NumberCell(
                number = number,
                onClick = { onNumberClick(number) },
                enabled = enabled,
                testTag = if (testTag.isNotEmpty()) "${testTag}_key_$number" else ""
            )
            if (index < numbers.size - 1) {
                VerticalDivider(color = NeutralGray100, thickness = 1.dp)
            }
        }
    }
}

@Composable
private fun BottomRow(
    onNumberClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onActionClick: () -> Unit,
    actionIcon: @Composable (() -> Unit)?,
    enabled: Boolean,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        DeleteCell(
            onClick = onDeleteClick,
            enabled = enabled,
            testTag = if (testTag.isNotEmpty()) "${testTag}_key_delete" else ""
        )
        VerticalDivider(color = NeutralGray100, thickness = 1.dp)

        NumberCell(
            number = 0,
            onClick = { onNumberClick(0) },
            enabled = enabled,
            testTag = if (testTag.isNotEmpty()) "${testTag}_key_0" else ""
        )
        VerticalDivider(color = NeutralGray100, thickness = 1.dp)

        ActionCell(
            onClick = onActionClick,
            icon = actionIcon,
            enabled = enabled,
            testTag = if (testTag.isNotEmpty()) "${testTag}_key_action" else ""
        )
    }
}

@Composable
private fun RowScope.NumberCell(
    number: Int,
    onClick: () -> Unit,
    enabled: Boolean,
    testTag: String
) {
    val numberDescription = stringResource(Res.string.numeric_keypad_number_description, number)

    Box(
        modifier = Modifier
            .weight(1f)
            .heightIn(min = KeypadCellMinHeight)
            .background(NeutralGray)
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .semantics {
                contentDescription = numberDescription
                role = Role.Button
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp
            ),
            color = if (enabled) NeutralGray500 else NeutralGray200
        )
    }
}

@Composable
private fun RowScope.DeleteCell(
    onClick: () -> Unit,
    enabled: Boolean,
    testTag: String
) {
    val deleteDescription = stringResource(Res.string.numeric_keypad_delete_description)

    Box(
        modifier = Modifier
            .weight(1f)
            .heightIn(min = KeypadCellMinHeight)
            .background(NeutralGray)
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .semantics {
                contentDescription = deleteDescription
                role = Role.Button
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.icon_delete_arrow),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (enabled) NeutralGray500 else NeutralGray200
        )
    }
}

@Composable
private fun RowScope.ActionCell(
    onClick: () -> Unit,
    icon: @Composable (() -> Unit)?,
    enabled: Boolean,
    testTag: String
) {
    val actionDescription = stringResource(Res.string.numeric_keypad_action_description)

    Box(
        modifier = Modifier
            .weight(1f)
            .heightIn(min = KeypadCellMinHeight)
            .background(NeutralGray)
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .semantics {
                contentDescription = actionDescription
                role = Role.Button
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.plus_stroke_icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (enabled) NeutralGray500 else NeutralGray200
        )
    }
}

@Composable
@Preview
private fun ToteatNumericKeypadPreview() {
    ToteatTheme {
        ToteatNumericKeypad(
            onNumberClick = {},
            onDeleteClick = {},
            onActionClick = {}
        )
    }
}

@Composable
@Preview
private fun ToteatNumericKeypadDisabledPreview() {
    ToteatTheme {
        ToteatNumericKeypad(
            onNumberClick = {},
            onDeleteClick = {},
            onActionClick = {},
            enabled = false
        )
    }
}
