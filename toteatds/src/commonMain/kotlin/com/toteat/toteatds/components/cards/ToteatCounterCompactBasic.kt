package com.toteat.toteatds.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.CounterButtonColor
import com.toteat.toteatds.theme.CounterButtonDisabledColor
import com.toteat.toteatds.theme.CounterContainerColor
import com.toteat.toteatds.theme.CounterContainerDisabledColor
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.counter_decrement
import designsystemmobile.toteatds.generated.resources.counter_description
import designsystemmobile.toteatds.generated.resources.counter_increment
import com.toteat.toteatds.theme.ToteatTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val ContainerShape = RoundedCornerShape(999.dp)
private val ContainerPadding = 4.dp
private val IconSize = 18.dp

@Composable
fun ToteatCounterCompactBasic(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    testTag: String = ""
) {
    val counterDescription = stringResource(Res.string.counter_description, quantity)
    val incrementDescription = stringResource(Res.string.counter_increment)
    val decrementDescription = stringResource(Res.string.counter_decrement)

    val containerBg = if (enabled) CounterContainerColor else CounterContainerDisabledColor
    val buttonBg = if (enabled) CounterButtonColor else CounterButtonDisabledColor
    val iconTint = if (enabled) NeutralGray500 else NeutralGray200
    val textColor = if (enabled) NeutralGray500 else NeutralGray300

    Surface(
        modifier = modifier
            .semantics {
                this.contentDescription = counterDescription
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        shape = ContainerShape,
        color = containerBg
    ) {
        Row(
            modifier = Modifier.padding(ContainerPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (quantity > 0) {
                CounterButton(
                    onClick = onDecrement,
                    enabled = enabled,
                    backgroundColor = buttonBg,
                    iconTint = iconTint,
                    semanticDescription = decrementDescription,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_decrement" else ""
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = null,
                        modifier = Modifier.size(IconSize)
                    )
                }

                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .widthIn(min = 24.dp)
                        .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_quantity") else Modifier)
                )
            }

            CounterButton(
                onClick = onIncrement,
                enabled = enabled,
                backgroundColor = buttonBg,
                iconTint = iconTint,
                semanticDescription = incrementDescription,
                testTag = if (testTag.isNotEmpty()) "${testTag}_increment" else ""
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(IconSize)
                )
            }
        }
    }
}

@Composable
@Preview
private fun ToteatCounterCompactBasicPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ToteatCounterCompactBasic(
                quantity = 0,
                onIncrement = {},
                onDecrement = {}
            )
            ToteatCounterCompactBasic(
                quantity = 1,
                onIncrement = {},
                onDecrement = {}
            )
            ToteatCounterCompactBasic(
                quantity = 5,
                onIncrement = {},
                onDecrement = {}
            )
            ToteatCounterCompactBasic(
                quantity = 2,
                onIncrement = {},
                onDecrement = {},
                enabled = false
            )
        }
    }
}
