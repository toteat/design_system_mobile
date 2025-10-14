package com.toteat.toteatds.components.textfields

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import com.toteat.toteatds.components.icons.StatusTrailingIcon
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.headingMediumRegular

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
    onToggleVisibilityCLick: () -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {}
) {
    ToteatTextFieldLayout(
        modifier = modifier,
        title = title,
        isSuccess = isSuccess,
        isWarning = isWarning,
        isError = isError,
        helperText = helperText,
        enabled = enabled,
        onFocusChanged = onFocusChanged
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