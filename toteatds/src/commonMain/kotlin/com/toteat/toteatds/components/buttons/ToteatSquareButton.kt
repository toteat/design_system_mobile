package com.toteat.toteatds.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.icons.DifferentAmountPaymentsIcon
import com.toteat.toteatds.components.icons.SplitPaymentIcon
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.square_button_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatSquareButton(
    title: String,
    subTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String? = null,
    icon: @Composable () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val targetBorderColor =
        if (pressed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val borderColor by animateColorAsState(targetValue = targetBorderColor, label = "squareBorder")
    val description = stringResource(Res.string.square_button_description, title, subTitle)

    OutlinedCard(
        onClick = onClick,
        modifier = modifier
            .heightIn(max = 128.dp)
            .width(150.dp)
            .semantics { contentDescription = description }
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        interactionSource = interactionSource,
        colors = CardDefaults
            .cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 14.dp, horizontal = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                softWrap = true
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = subTitle,
                style = MaterialTheme.typography.bodyLargeRegular,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Start,
                softWrap = true,
                maxLines = Int.MAX_VALUE
            )
        }
    }
}

@Composable
@Preview
private fun ToteatSquareButtonPreview() {
    ToteatTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToteatSquareButton(
                title = "Dividir pago",
                subTitle = "En partes iguales",
                icon = {
                    SplitPaymentIcon(
                        modifier = Modifier.size(48.dp)
                    )
                },
                onClick = {}
            )
            ToteatSquareButton(
                title = "Pagos por",
                subTitle = "montos diferentes",
                icon = {
                    DifferentAmountPaymentsIcon(
                        modifier = Modifier.size(48.dp)
                    )
                },
                onClick = {}
            )
        }
    }
}