 package com.toteat.toteatds.components.display

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray100
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.amount_display_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val DisplayShape = RoundedCornerShape(14.dp)
private val DisplayHeight = 56.dp

/**
 * Read-only display for formatted amounts, designed to pair with [ToteatNumericKeypad].
 *
 * @param value The formatted text to display (e.g. "$5.200").
 * @param modifier Modifier applied to the root container.
 * @param placeholder Text shown when [value] is empty.
 * @param enabled Visual enabled state.
 * @param testTag Optional test tag for UI testing.
 */
@Composable
fun ToteatAmountDisplay(
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    testTag: String = ""
) {
    val displayDescription = stringResource(Res.string.amount_display_description, value.ifEmpty { placeholder })

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(DisplayHeight)
            .clip(DisplayShape)
            .background(if (enabled) NeutralGray100 else NeutralGray)
            .border(1.dp, NeutralGray200, DisplayShape)
            .padding(horizontal = 16.dp)
            .semantics { contentDescription = displayDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.ifEmpty { placeholder },
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            ),
            color = when {
                !enabled -> NeutralGray200
                value.isEmpty() -> NeutralGray400
                else -> NeutralGray500
            },
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
@Preview
private fun ToteatAmountDisplayPreview() {
    ToteatTheme {
        ToteatAmountDisplay(value = "\$5.200")
    }
}

@Composable
@Preview
private fun ToteatAmountDisplayEmptyPreview() {
    ToteatTheme {
        ToteatAmountDisplay(value = "", placeholder = "\$0")
    }
}

@Composable
@Preview
private fun ToteatAmountDisplayDisabledPreview() {
    ToteatTheme {
        ToteatAmountDisplay(value = "\$5.200", enabled = false)
    }
}
