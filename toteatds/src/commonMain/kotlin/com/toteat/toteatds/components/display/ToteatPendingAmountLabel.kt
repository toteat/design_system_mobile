package com.toteat.toteatds.components.display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.TertiaryMuted
import com.toteat.toteatds.theme.TertiarySurface
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.pending_amount_description
import designsystemmobile.toteatds.generated.resources.pending_amount_label
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val PendingAmountLabelShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
private val PendingAmountLabelHeight = 44.dp

/**
 * Non-interactive banner showing a pending amount, e.g. the remaining balance to pay.
 *
 * Sits on a tertiary cream surface with top-rounded corners so it can be stacked above
 * an action button or [com.toteat.toteatds.components.bottombar.AmountBottomBar].
 *
 * @param amount The formatted amount to display (e.g. "$ 32.780").
 * @param modifier Modifier applied to the root container.
 * @param label Leading label shown next to the amount. Defaults to "Monto pendiente a pagar :".
 * @param testTag Optional test tag for UI testing.
 */
@Composable
fun ToteatPendingAmountLabel(
    amount: String,
    modifier: Modifier = Modifier,
    label: String = stringResource(Res.string.pending_amount_label),
    testTag: String = ""
) {
    val accessibilityDescription = stringResource(Res.string.pending_amount_description, label, amount)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(PendingAmountLabelHeight)
            .background(TertiarySurface)
            .padding(horizontal = 16.dp)
            .semantics { contentDescription = accessibilityDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TertiaryMuted,
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = amount,
            modifier = Modifier.padding(start = 12.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = TertiaryMuted,
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
@Preview
private fun ToteatPendingAmountLabelPreview() {
    ToteatTheme {
        ToteatPendingAmountLabel(amount = "\$ 32.780")
    }
}

@Composable
@Preview
private fun ToteatPendingAmountLabelCustomLabelPreview() {
    ToteatTheme {
        ToteatPendingAmountLabel(
            label = "Saldo restante :",
            amount = "\$ 12.780"
        )
    }
}
