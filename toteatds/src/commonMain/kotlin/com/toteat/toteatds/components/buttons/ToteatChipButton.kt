package com.toteat.toteatds.components.buttons
import com.toteat.toteatds.utils.setTestTag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray100
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.TertiaryNormal
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.chip_not_selected
import designsystemmobile.toteatds.generated.resources.chip_selected
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Pill-shaped chip button.
 *
 * @param height Fixed height of the chip. When `null` the chip sizes itself from the text plus its
 * vertical padding and keeps the 48.dp minimum touch target; when set, the vertical padding and that
 * minimum are dropped so the chip measures exactly [height] with the text centered (e.g. the 22.dp
 * suggestions of [com.toteat.toteatds.components.bottombar.ToteatCommentBottomBar], where the
 * reserved touch target would show up as blank space around the pill).
 */
@Composable
fun ToteatChipButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    testTag: String = "",
    // New parameters go after the previously published ones so positional call sites keep working.
    containerColor: Color? = null,
    height: Dp? = null
) {
    val selectedText = stringResource(Res.string.chip_selected)
    val notSelectedText = stringResource(Res.string.chip_not_selected)

    // Cache colors to avoid recreating Color instances
    val textColor = remember(enabled) {
        if (!enabled) NeutralGray.copy(alpha = 0.38f) else Color.Black
    }

    val backgroundColor = remember(enabled, isSelected, containerColor) {
        when {
            !enabled -> NeutralGray300.copy(alpha = 0.5f)
            isSelected -> TertiaryNormal
            else -> containerColor ?: NeutralGray100
        }
    }

    // Cache accessibility description to avoid string concatenation on every recomposition
    val accessibilityDescription = remember(text, isSelected, selectedText, notSelectedText) {
        if (isSelected) "$text, $selectedText" else "$text, $notSelectedText"
    }

    // Cache static modifiers. A fixed [height] is an explicit layout request (e.g. the 22.dp
    // suggestions of ToteatCommentBottomBar), so the 48.dp minimum touch target is skipped there:
    // otherwise the row reserves 48.dp and the extra space reads as padding around the pill.
    val baseModifier = remember(height) {
        if (height == null) {
            Modifier
                .minimumInteractiveComponentSize()
                .clip(CircleShape)
        } else {
            Modifier.clip(CircleShape)
        }
    }

    val paddingModifier = remember(height) {
        Modifier.padding(
            horizontal = 16.dp,
            vertical = if (height != null) 0.dp else 8.dp
        )
    }

    Box(
        modifier = modifier
            .then(baseModifier)
            .background(color = backgroundColor)
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
            .then(if (height != null) Modifier.height(height) else Modifier)
            .then(paddingModifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ChipButtonPreview() {
    ToteatTheme {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToteatChipButton(
                text = "Salon",
                isSelected = false,
                onClick = {}
            )

            ToteatChipButton(
                text = "Bar",
                isSelected = true,
                onClick = {}
            )

            ToteatChipButton(
                text = "Terraza",
                isSelected = false,
                onClick = {},
                enabled = false
            )

            ToteatChipButton(
                text = "VIP",
                isSelected = true,
                onClick = {},
                enabled = false
            )
        }
    }
}
