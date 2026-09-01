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
import com.toteat.toteatds.theme.extended
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

/**
 * Compact quantity counter without the delete affordance: the decrement button always shows the
 * minus icon.
 *
 * @param allowZero Whether the counter keeps its full dial at `quantity == 0`. When `false` (the
 * default, and the behaviour of every call site published so far) a zero quantity collapses the
 * counter to just the "+" button. When `true` the row is always complete — decrement button, the
 * "0" and the increment button — with the decrement button disabled so the quantity can never go
 * below zero. Used by screens that need to show and confirm the zero state, such as the POS quantity
 * adjustment modal.
 */
@Composable
fun ToteatCounterCompactBasic(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    testTag: String = "",
    // New parameters go after the previously published ones so positional call sites keep working.
    allowZero: Boolean = false
) {
    val counterDescription = stringResource(Res.string.counter_description, quantity)
    val incrementDescription = stringResource(Res.string.counter_increment)
    val decrementDescription = stringResource(Res.string.counter_decrement)

    val extended = MaterialTheme.colorScheme.extended
    val containerBg = if (enabled) extended.counterContainer else extended.counterContainerDisabled
    val buttonBg = if (enabled) extended.counterButton else extended.counterButtonDisabled
    val iconTint = if (enabled) MaterialTheme.colorScheme.onSurface
        else MaterialTheme.colorScheme.outlineVariant
    val textColor = if (enabled) MaterialTheme.colorScheme.onSurface else extended.disabledContent

    // Only reachable with allowZero = true; otherwise the block below is not rendered at 0.
    val decrementEnabled = enabled && quantity > 0
    val decrementBg = if (decrementEnabled) buttonBg else extended.counterButtonDisabled
    val decrementIconTint = if (decrementEnabled) iconTint else MaterialTheme.colorScheme.outlineVariant

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
            if (quantity > 0 || allowZero) {
                CounterButton(
                    onClick = onDecrement,
                    enabled = decrementEnabled,
                    backgroundColor = decrementBg,
                    iconTint = decrementIconTint,
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

@Composable
@Preview
private fun ToteatCounterCompactBasicAllowZeroPreview() {
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
                onDecrement = {},
                allowZero = true
            )
            ToteatCounterCompactBasic(
                quantity = 1,
                onIncrement = {},
                onDecrement = {},
                allowZero = true
            )
            ToteatCounterCompactBasic(
                quantity = 2,
                onIncrement = {},
                onDecrement = {},
                allowZero = true
            )
        }
    }
}
