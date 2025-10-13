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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.extended

@Composable
fun ToteatTextFieldLayout(
    modifier: Modifier = Modifier,
    title: String? = null,
    isSuccess: Boolean = false,
    isError: Boolean = false,
    isWarning: Boolean = false,
    helperText: String? = null,
    enabled: Boolean = true,
    onFocusChanged: (Boolean) -> Unit = {},
    textField: @Composable (Modifier, MutableInteractionSource) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    LaunchedEffect(isFocused) {
        onFocusChanged(isFocused)
    }

    val textFieldStyleModifier = Modifier
        .fillMaxWidth()
        .background(
            color = when {
                isFocused -> MaterialTheme.colorScheme.background
                enabled -> MaterialTheme.colorScheme.background
                else -> MaterialTheme.colorScheme.outline // o un fill de disabled si tienes uno
            },
            shape = RoundedCornerShape(8.dp)
        )
        .border(
            width = 1.dp,
            color = when {
                isSuccess -> MaterialTheme.colorScheme.extended.isSuccess
                isError -> MaterialTheme.colorScheme.error
                isFocused -> MaterialTheme.colorScheme.onTertiary
                isWarning -> MaterialTheme.colorScheme.extended.isWarning
                else -> MaterialTheme.colorScheme.outline
            },
            shape = RoundedCornerShape(8.dp)
        )
        .padding(12.dp)
    Column(
        modifier = modifier
    ) {
        title?.let {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        textField(textFieldStyleModifier, interactionSource)
        helperText?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = helperText,
                color = MaterialTheme.colorScheme.extended.neutral400,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End
            )
        }
    }
}
