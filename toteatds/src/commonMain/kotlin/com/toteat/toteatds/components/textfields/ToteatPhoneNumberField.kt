package com.toteat.toteatds.components.textfields
import com.toteat.toteatds.utils.setTestTag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.icons.StatusTrailingIcon
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.headingMediumRegular
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatPhoneNumberField(
    state: TextFieldState,
    selectedDialCode: String,
    dialCodeOptions: List<String>,
    onDialCodeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    title: String? = null,
    helperText: String? = null,
    isSuccess: Boolean = false,
    isError: Boolean = false,
    isWarning: Boolean = false,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Phone,
    onFocusChange: (Boolean) -> Unit = {},
    testTag: String = ""
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
        onFocusChange = onFocusChange,
        testTag = testTag
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
                                    .semantics {
                                        role = Role.DropdownList
                                        contentDescription = "Código de país: $selectedDialCode"
                                    }
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ToteatPhoneNumberFieldPreview() {
    ToteatTheme {
        var selectedDialCode by remember { mutableStateOf("+56") }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estado normal
            ToteatPhoneNumberField(
                state = rememberTextFieldState(),
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "912345678",
                title = "Teléfono",
                helperText = "Ingresa tu número sin el código de país",
                selectedDialCode = selectedDialCode,
                dialCodeOptions = listOf("+56", "+1", "+34", "+54", "+52"),
                onDialCodeChange = { selectedDialCode = it }
            )

            // Con error
            ToteatPhoneNumberField(
                state = rememberTextFieldState("123"),
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "912345678",
                title = "Teléfono",
                helperText = "Número muy corto",
                isError = true,
                selectedDialCode = "+56",
                dialCodeOptions = listOf("+56", "+1", "+34"),
                onDialCodeChange = {}
            )

            // Con éxito
            ToteatPhoneNumberField(
                state = rememberTextFieldState("912345678"),
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "912345678",
                title = "Teléfono",
                helperText = "Número válido",
                isSuccess = true,
                selectedDialCode = "+56",
                dialCodeOptions = listOf("+56", "+1", "+34"),
                onDialCodeChange = {}
            )

            // Deshabilitado
            ToteatPhoneNumberField(
                state = rememberTextFieldState("987654321"),
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "912345678",
                title = "Teléfono (deshabilitado)",
                helperText = "Campo bloqueado",
                enabled = false,
                selectedDialCode = "+56",
                dialCodeOptions = listOf("+56", "+1", "+34"),
                onDialCodeChange = {}
            )
        }
    }
}
