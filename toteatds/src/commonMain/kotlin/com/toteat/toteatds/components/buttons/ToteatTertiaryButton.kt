package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.PrimaryLight
import com.toteat.toteatds.utils.setTestTag

private val ButtonShape = RoundedCornerShape(50)
private val ButtonHeight = 48.dp
private val ButtonBorderWidth = 1.dp

@Composable
fun ToteatTertiaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String? = null,
    contentDescription: String? = null,
    testTag: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
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
        modifier = modifier
            .height(ButtonHeight)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .semantics {
                if (contentDescription != null) {
                    this.contentDescription = contentDescription
                } else if (text != null) {
                    this.contentDescription = text
                }
                role = Role.Button
            },
        enabled = enabled,
        shape = ButtonShape,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor,
            disabledContentColor = NeutralGray400
        ),
        border = BorderStroke(ButtonBorderWidth, borderColor),
        interactionSource = interactionSource
    ) {
        ButtonContent(text = text, leadingIcon = leadingIcon, trailingIcon = trailingIcon)
    }
}

@Composable
private fun ButtonContent(
    text: String?,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    if (leadingIcon != null) {
        leadingIcon()
        if (text != null) Spacer(Modifier.width(8.dp))
    }
    if (text != null) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
    if (trailingIcon != null) {
        Spacer(Modifier.width(8.dp))
        trailingIcon()
    }
}
