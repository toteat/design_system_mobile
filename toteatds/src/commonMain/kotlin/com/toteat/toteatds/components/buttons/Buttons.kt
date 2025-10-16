package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.PrimaryLight
import com.toteat.toteatds.theme.SecondaryNormal


private val ButtonShape = RoundedCornerShape(50)
private val ButtonHeight = 45.dp
private val ButtonBorderWidth = 1.dp

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val containerColor = when {
        !enabled -> NeutralGray300
        isPressed -> PrimaryLight
        else -> MaterialTheme.colorScheme.primary
    }
    val contentColor = if (enabled) MaterialTheme.colorScheme.onPrimary else NeutralGray

    Button(
        onClick = onClick,
        modifier = modifier.height(ButtonHeight),
        enabled = enabled,
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = NeutralGray300,
            disabledContentColor = NeutralGray
        ),
        interactionSource = interactionSource
    ) {
        ButtonContent(text = text, leadingIcon = leadingIcon)
    }
}

@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val containerColor = when {
        !enabled -> NeutralGray300
        isPressed -> NeutralGray500
        else -> SecondaryNormal
    }
    val contentColor = if (enabled) NeutralGray else NeutralGray500

    Button(
        onClick = onClick,
        modifier = modifier.height(ButtonHeight),
        enabled = enabled,
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = NeutralGray300,
            disabledContentColor = NeutralGray500
        ),
        interactionSource = interactionSource
    ) {
        ButtonContent(text = text, leadingIcon = leadingIcon)
    }
}

@Composable
fun TertiaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val contentColor = when {
        !enabled -> NeutralGray400
        isPressed -> PrimaryLight
        else -> MaterialTheme.colorScheme.primary
    }
    val borderColor = if (enabled) contentColor else NeutralGray400

    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(ButtonHeight),
        enabled = enabled,
        shape = ButtonShape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
            disabledContentColor = NeutralGray400
        ),
        border = BorderStroke(ButtonBorderWidth, borderColor),
        interactionSource = interactionSource
    ) {
        ButtonContent(text = text, leadingIcon = leadingIcon)
    }
}

@Composable
private fun RowScope.ButtonContent(
    text: String?,
    leadingIcon: @Composable (() -> Unit)?
) {
    if (leadingIcon != null) {
        leadingIcon()
        if (text != null) Spacer(Modifier.width(8.dp))
    }
    if (text != null) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 16.sp
        ))
    }
}

