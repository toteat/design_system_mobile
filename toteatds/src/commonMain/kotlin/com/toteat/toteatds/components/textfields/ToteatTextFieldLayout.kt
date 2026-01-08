package com.toteat.toteatds.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag

@Composable
fun ToteatTextFieldLayout(
    modifier: Modifier = Modifier,
    title: String? = null,
    isSuccess: Boolean = false,
    isError: Boolean = false,
    isWarning: Boolean = false,
    helperText: String? = null,
    enabled: Boolean = true,
    onFocusChange: (Boolean) -> Unit = {},
    testTag: String = "",
    textField: @Composable (Modifier, MutableInteractionSource) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val currentOnFocusChange by rememberUpdatedState(onFocusChange)

    LaunchedEffect(isFocused) {
        currentOnFocusChange(isFocused)
    }

    val textFieldStyleModifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(
            color = when {
                !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
                else -> MaterialTheme.colorScheme.background
            },
            shape = RoundedCornerShape(8.dp)
        )
        .border(
            width = 1.dp,
            color = when {
                isFocused -> MaterialTheme.colorScheme.onTertiary
                !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.38f)
                else -> MaterialTheme.colorScheme.outline
            },
            shape = RoundedCornerShape(8.dp)
        )
        .padding(horizontal = 12.dp, vertical = 8.dp)
        .semantics {
            if (title != null) {
                contentDescription = title
            }
            if (isError && helperText != null) {
                error(helperText)
            }
        }
    Column(
        modifier = modifier
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        title?.let {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = if (enabled) {
                    MaterialTheme.colorScheme.onTertiary
                } else {
                    MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.38f)
                }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        textField(textFieldStyleModifier, interactionSource)
        helperText?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = helperText,
                color = when {
                    !enabled -> MaterialTheme.colorScheme.extended.neutral400.copy(alpha = 0.38f)
                    isError -> MaterialTheme.colorScheme.error
                    isWarning -> MaterialTheme.colorScheme.extended.isWarning
                    isSuccess -> MaterialTheme.colorScheme.extended.isSuccess
                    else -> MaterialTheme.colorScheme.extended.neutral400
                },
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )
        }
    }
}
