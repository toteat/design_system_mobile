package com.toteat.toteatds.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyMediumRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.search_bar_description
import designsystemmobile.toteatds.generated.resources.search_bar_icon
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    testTag: String = ""
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val description = stringResource(Res.string.search_bar_description)
    val searchIconDescription = stringResource(Res.string.search_bar_icon)

    val shape = RoundedCornerShape(8.dp)
    val baseStyle = MaterialTheme.typography.bodyMediumRegular
    val textStyle = remember(baseStyle) { baseStyle.copy(fontSize = 14.sp) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = true,
        textStyle = textStyle.copy(
            color = if (enabled) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.extended.neutral400
        ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(
                color = if (enabled) MaterialTheme.colorScheme.extended.neutral100
                        else MaterialTheme.colorScheme.extended.neutral100.copy(alpha = 0.5f),
                shape = shape
            )
            .border(
                width = 1.dp,
                color = if (isFocused && enabled) MaterialTheme.colorScheme.outline
                        else Color.Transparent,
                shape = shape
            )
            .semantics { contentDescription = description }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty() && placeholder.isNotEmpty()) {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colorScheme.extended.neutral400,
                            style = textStyle
                        )
                    }
                    innerTextField()
                }
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = searchIconDescription,
                    tint = if (enabled) MaterialTheme.colorScheme.secondary
                           else MaterialTheme.colorScheme.extended.neutral400,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}

@Preview
@Composable
private fun ToteatSearchBarEmptyPreview() {
    ToteatTheme {
        var value by remember { mutableStateOf("") }
        ToteatSearchBar(
            value = value,
            onValueChange = { value = it },
            placeholder = "Buscar productos..."
        )
    }
}

@Preview
@Composable
private fun ToteatSearchBarWithTextPreview() {
    ToteatTheme {
        var value by remember { mutableStateOf("Hamburguesa") }
        ToteatSearchBar(
            value = value,
            onValueChange = { value = it },
            placeholder = "Buscar productos..."
        )
    }
}

@Preview
@Composable
private fun ToteatSearchBarDisabledPreview() {
    ToteatTheme {
        ToteatSearchBar(
            value = "",
            onValueChange = {},
            placeholder = "Buscar productos...",
            enabled = false
        )
    }
}
