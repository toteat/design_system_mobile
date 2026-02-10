package com.toteat.toteatds.components.textfields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.icons.StatusTrailingIcon
import com.toteat.toteatds.components.icons.VisibilityToggleIcon
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.headingMediumRegular
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatPasswordTextField(
    state: TextFieldState,
    isPasswordVisible: Boolean,
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    title: String? = null,
    helperText: String? = null,
    isSuccess: Boolean = false,
    isWarning: Boolean = false,
    isError: Boolean = false,
    enabled: Boolean = true,
    onToggleVisibilityClick: () -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
    testTag: String = ""
) {
    ToteatTextFieldLayout(
        modifier = modifier,
        title = title,
        isSuccess = isSuccess,
        isWarning = isWarning,
        isError = isError,
        helperText = helperText,
        enabled = enabled,
        onFocusChange = onFocusChange,
        testTag = testTag
    ) { styleModifier, interactionSource ->
        BasicSecureTextField(
            state = state,
            modifier = styleModifier,
            enabled = enabled,
            textStyle = MaterialTheme.typography.headingMediumRegular,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            interactionSource = interactionSource,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorator = { innerBox ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
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

                    VisibilityToggleIcon(
                        isVisible = isPasswordVisible,
                        onClick = onToggleVisibilityClick,
                        tint = MaterialTheme.colorScheme.extended.neutral400
                    )
                    if (isSuccess || isError || isWarning) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    StatusTrailingIcon(
                        isSuccess = isSuccess,
                        isError = isError,
                        isWarning = isWarning
                    )
                }
            },
            textObfuscationMode = if (isPasswordVisible) {
                TextObfuscationMode.Visible
            } else TextObfuscationMode.Hidden
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ToteatPasswordTextFieldPreview() {
    ToteatTheme {
        var isVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estado normal
            ToteatPasswordTextField(
                state = rememberTextFieldState(),
                isPasswordVisible = isVisible,
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Ingresa tu contraseña",
                title = "Contraseña",
                helperText = "Mínimo 8 caracteres",
                onToggleVisibilityClick = { isVisible = !isVisible }
            )

            // Con error
            ToteatPasswordTextField(
                state = rememberTextFieldState("123"),
                isPasswordVisible = false,
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Ingresa tu contraseña",
                title = "Contraseña",
                helperText = "Contraseña muy corta",
                isError = true,
                onToggleVisibilityClick = {}
            )

            // Con éxito
            ToteatPasswordTextField(
                state = rememberTextFieldState("MiPassword123!"),
                isPasswordVisible = false,
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Ingresa tu contraseña",
                title = "Contraseña",
                helperText = "Contraseña válida",
                isSuccess = true,
                onToggleVisibilityClick = {}
            )

            // Deshabilitado
            ToteatPasswordTextField(
                state = rememberTextFieldState("password"),
                isPasswordVisible = false,
                modifier = Modifier.fillMaxWidth(),
                placeHolder = "Ingresa tu contraseña",
                title = "Contraseña (deshabilitada)",
                helperText = "Campo bloqueado",
                enabled = false,
                onToggleVisibilityClick = {}
            )
        }
    }
}
