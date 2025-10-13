package com.toteat.toteatds.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.icons.StatusTrailingIcon
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.headingMediumRegular

@Composable
fun ToteatPhoneNumberField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    title: String? = null,
    helperText: String? = null,
    isSuccess: Boolean = false,
    isError: Boolean = false,
    isWarning: Boolean = false,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Phone,
    selectedDialCode: String,
    dialCodeOptions: List<String>,
    onDialCodeChange: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    ToteatTextFieldLayout(
        modifier = modifier,
        title = title,
        isSuccess = isSuccess,
        isError = isError,
        isWarning = isWarning,
        helperText = helperText,
        enabled = enabled,
        onFocusChanged = onFocusChanged
    ) { styleModifier, interactionSource ->
        BasicTextField(
            state = state,
            enabled = enabled,
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = MaterialTheme.typography.headingMediumRegular,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = styleModifier,
            decorator = { innerBox ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .clickable(enabled = enabled) { expanded = !expanded }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = selectedDialCode,
                                    color = MaterialTheme.colorScheme.secondary,
                                    style = MaterialTheme.typography.headingMediumRegular
                                )
                                Spacer(Modifier.width(2.dp))
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                                ) {
                                    dialCodeOptions.forEach { code ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = code,
                                                    style = MaterialTheme.typography.bodyLarge,
                                                    color = MaterialTheme.colorScheme.secondary
                                                )
                                            },
                                            onClick = {
                                                onDialCodeChange(code)
                                                expanded = false
                                            },
                                            colors = MenuDefaults.itemColors(
                                                textColor = MaterialTheme.colorScheme.secondary,
                                                leadingIconColor = MaterialTheme.colorScheme.secondary,
                                                trailingIconColor = MaterialTheme.colorScheme.secondary,
                                                disabledTextColor = MaterialTheme.colorScheme.secondary.copy(
                                                    alpha = 0.38f
                                                )
                                            )
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            if (state.text.isEmpty() && placeHolder != null) {
                                Text(
                                    text = placeHolder,
                                    color = MaterialTheme.colorScheme.extended.neutral400,
                                    style = MaterialTheme.typography.headingMediumRegular
                                )
                            }
                            innerBox()
                        }
                        StatusTrailingIcon(
                            isSuccess = isSuccess,
                            isError = isError,
                            isWarning = isWarning
                        )
                    }
                }
            }
        )
    }
}