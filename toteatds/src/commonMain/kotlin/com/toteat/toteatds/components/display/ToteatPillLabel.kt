package com.toteat.toteatds.components.display

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray100
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.credit_card_icon
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val PillShape = RoundedCornerShape(30)
private val PillHeight = 44.dp

/**
 * Non-interactive pill-shaped label with optional trailing icon.
 *
 * @param text The label text.
 * @param modifier Modifier applied to the root container.
 * @param trailingIcon Optional composable icon displayed after the text.
 * @param testTag Optional test tag for UI testing.
 */
@Composable
fun ToteatPillLabel(
    text: String,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    testTag: String = ""
) {
    Row(
        modifier = modifier
            .height(PillHeight)
            .clip(PillShape)
            .background(NeutralGray100)
            .border(1.dp, NeutralGray200, PillShape)
            .padding(horizontal = 20.dp)
            .semantics { contentDescription = text }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = NeutralGray500,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (trailingIcon != null) {
            Spacer(Modifier.width(8.dp))
            trailingIcon()
        }
    }
}

@Composable
@Preview
private fun ToteatPillLabelPreview() {
    ToteatTheme {
        ToteatPillLabel(
            text = "Pago por monto específico",
            trailingIcon = {
                Icon(
                    imageVector = vectorResource(Res.drawable.credit_card_icon),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = NeutralGray500
                )
            }
        )
    }
}

@Composable
@Preview
private fun ToteatPillLabelNoIconPreview() {
    ToteatTheme {
        ToteatPillLabel(text = "Título simple")
    }
}
