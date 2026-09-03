package com.toteat.toteatds.components.textfields
import com.toteat.toteatds.utils.setTestTag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.icons.StatusTrailingIcon
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.headingMediumRegular
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * @param maxLength Tope de caracteres. `null` (default) no topa nada. Rechaza en la
 * [InputTransformation], no recorta el state, así que tampoco filtra los cambios programáticos.
 * `<= 0` falla con `require`.
 */
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
    imeAction: ImeAction = ImeAction.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    onFocusChange: (Boolean) -> Unit = {},
    focusRequester: FocusRequester? = null,
    testTag: String = "",
    // New parameters go after the previously published ones so positional call sites keep working.
    maxLines: Int = Int.MAX_VALUE,
    minHeight: Dp = DefaultTextFieldMinHeight,
    shape: Shape = DefaultTextFieldShape,
    maxLength: Int? = null
) {
    require(maxLength == null || maxLength > 0) { "maxLength must be greater than zero, was $maxLength" }

    val inputTransformation = remember(maxLength) {
        maxLength?.let { InputTransformation.maxLength(it) }
    }

    ToteatTextFieldLayout(
        modifier = modifier,
        title = title,
        isSuccess = isSuccess,
        isError = isError,
        isWarning = isWarning,
        helperText = helperText,
        enabled = enabled,
        onFocusChange = onFocusChange,
        minHeight = minHeight,
        shape = shape,
        testTag = testTag
    ) { styleModifier, interactionSource ->
        BasicTextField(
            state = state,
            enabled = enabled,
            inputTransformation = inputTransformation,
            lineLimits = if (singleLine) {
                TextFieldLineLimits.SingleLine
            } else {
                TextFieldLineLimits.MultiLine(maxHeightInLines = maxLines)
            },
            textStyle = MaterialTheme.typography.headingMediumRegular,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
            onKeyboardAction = onKeyboardAction,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            interactionSource = interactionSource,
            modifier = focusRequester?.let { styleModifier.focusRequester(it) } ?: styleModifier,
            decorator = { innerBox ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = if (singleLine) Alignment.CenterStart else Alignment.TopStart
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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ToteatTextFieldMaxLengthPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tope alcanzado: 20 de 20
            ToteatTextField(
                state = rememberTextFieldState("Hamburguesa sin mayo"),
                modifier = Modifier.fillMaxWidth(),
                title = "Comentario (tope 20)",
                helperText = "No acepta más caracteres",
                maxLength = 20
            )

            // Bajo el tope
            ToteatTextField(
                state = rememberTextFieldState("Sin sal"),
                modifier = Modifier.fillMaxWidth(),
                title = "Comentario (tope 20)",
                helperText = "Acepta más caracteres",
                maxLength = 20
            )
        }
    }
}