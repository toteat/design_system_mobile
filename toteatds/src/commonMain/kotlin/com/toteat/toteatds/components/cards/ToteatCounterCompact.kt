package com.toteat.toteatds.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.counter_decrement
import designsystemmobile.toteatds.generated.resources.counter_delete
import designsystemmobile.toteatds.generated.resources.counter_description
import designsystemmobile.toteatds.generated.resources.counter_increment
import org.jetbrains.compose.resources.stringResource

private val ContainerShape = RoundedCornerShape(999.dp)
private val ContainerPadding = 4.dp
private val ButtonSize = 28.dp
private val IconSize = 18.dp

@Composable
fun ToteatCounterCompact(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    testTag: String = ""
) {
    val counterDescription = stringResource(Res.string.counter_description, quantity)
    val incrementDescription = stringResource(Res.string.counter_increment)
    val decrementDescription = if (quantity <= 1) {
        stringResource(Res.string.counter_delete)
    } else {
        stringResource(Res.string.counter_decrement)
    }

    val extended = MaterialTheme.colorScheme.extended
    val containerBg = if (enabled) extended.counterContainer else extended.counterContainerDisabled
    val buttonBg = if (enabled) extended.counterButton else extended.counterButtonDisabled
    val iconTint = if (enabled) MaterialTheme.colorScheme.onSurface
        else MaterialTheme.colorScheme.outlineVariant
    val textColor = if (enabled) MaterialTheme.colorScheme.onSurface else extended.disabledContent

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
                // Decrement / Delete button
                CounterButton(
                    onClick = onDecrement,
                    enabled = enabled,
                    backgroundColor = buttonBg,
                    iconTint = iconTint,
                    semanticDescription = decrementDescription,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_decrement" else ""
                ) {
                    Icon(
                        imageVector = if (quantity == 1) Icons.Default.Delete else Icons.Default.Remove,
                        contentDescription = null,
                        modifier = Modifier.size(IconSize)
                    )
                }

                // Quantity text
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

            // Increment button
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
fun CounterButton(
    onClick: () -> Unit,
    enabled: Boolean,
    backgroundColor: Color,
    iconTint: Color,
    semanticDescription: String,
    testTag: String,
    content: @Composable () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .size(ButtonSize)
            .semantics {
                this.contentDescription = semanticDescription
                this.role = Role.Button
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        enabled = enabled,
        shape = CircleShape,
        color = backgroundColor
    ) {
        CompositionLocalProvider(LocalContentColor provides iconTint) {
            Box(contentAlignment = Alignment.Center) {
                content()
            }
        }
    }
}
