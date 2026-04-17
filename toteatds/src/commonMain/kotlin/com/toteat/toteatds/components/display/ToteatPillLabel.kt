package com.toteat.toteatds.components.display

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray100
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.credit_card_icon
import designsystemmobile.toteatds.generated.resources.icon_split_bill
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val PillShape = RoundedCornerShape(30)
private val PillHeight = 44.dp

/**
 * Non-interactive pill-shaped label with optional trailing icon.
 *
 * @param text The label text.
 * @param modifier Modifier applied to the root container.
 * @param trailingIcon Icon displayed at the end. Defaults to `icon_split_bill`. Pass `null` to hide.
 * @param testTag Optional test tag for UI testing.
 */
@Composable
fun ToteatPillLabel(
    text: String,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = { DefaultPillLabelIcon() },
    testTag: String = ""
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(PillHeight)
            .clip(PillShape)
            .background(NeutralGray100)
            .border(1.dp, NeutralGray200, PillShape)
            .padding(horizontal = 20.dp)
            .semantics { contentDescription = text }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            style = MaterialTheme.typography.bodyLarge,
            color = NeutralGray500,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (trailingIcon != null) {
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                trailingIcon()
            }
        }
    }
}

@Composable
private fun DefaultPillLabelIcon() {
    Icon(
        imageVector = vectorResource(Res.drawable.icon_split_bill),
        contentDescription = null,
        modifier = Modifier.size(18.dp),
        tint = NeutralGray500
    )
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
private fun ToteatPillLabelDefaultIconPreview() {
    ToteatTheme {
        ToteatPillLabel(text = "Dividir cuenta")
    }
}

@Composable
@Preview
private fun ToteatPillLabelNoIconPreview() {
    ToteatTheme {
        ToteatPillLabel(text = "Título simple", trailingIcon = null)
    }
}
