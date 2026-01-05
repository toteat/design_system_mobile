package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.SecondaryNormal

private val ButtonShape = RoundedCornerShape(50)
private val ButtonHeight = 48.dp

@Composable
fun ToteatSecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String? = null,
    contentDescription: String? = null,
    testTag: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
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
        modifier = modifier
            .height(ButtonHeight)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier)
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
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = NeutralGray300,
            disabledContentColor = NeutralGray500
        ),
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
