package com.toteat.toteatds.components.textfields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.icons.StatusTrailingIcon
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.headingMediumRegular
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    title: String? = null,
    helperText: String? = null,
    isSuccess: Boolean = false,
    isError: Boolean = false,
    isWarning: Boolean = false,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    onFocusChanged: (Boolean) -> Unit = {}
) {
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
            lineLimits = if (singleLine) {
                TextFieldLineLimits.SingleLine
            } else TextFieldLineLimits.Default,
            textStyle = MaterialTheme.typography.headingMediumRegular,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            interactionSource = interactionSource,
            modifier = styleModifier,
            decorator = { innerBox ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
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
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ToteatTextFieldPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estado normal
            ToteatTextField(
                state = rememberTextFieldState(),
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Ingresa tu email",
                title = "Email",
                helperText = "Tu correo electrónico"
            )

            // Estado con error
            ToteatTextField(
                state = rememberTextFieldState("invalid@"),
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Ingresa tu email",
                title = "Email",
                helperText = "Email inválido",
                isError = true
            )

            // Estado con éxito
            ToteatTextField(
                state = rememberTextFieldState("usuario@toteat.cl"),
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Ingresa tu email",
                title = "Email",
                helperText = "Email válido",
                isSuccess = true
            )

            // Estado con advertencia
            ToteatTextField(
                state = rememberTextFieldState("test@test.com"),
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Ingresa tu email",
                title = "Email",
                helperText = "Usa tu email corporativo",
                isWarning = true
            )

            // Estado deshabilitado
            ToteatTextField(
                state = rememberTextFieldState("disabled@toteat.cl"),
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Ingresa tu email",
                title = "Email (deshabilitado)",
                helperText = "Campo deshabilitado",
                enabled = false
            )
        }
    }
}