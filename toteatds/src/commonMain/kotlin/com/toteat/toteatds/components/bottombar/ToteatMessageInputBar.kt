package com.toteat.toteatds.components.bottombar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.icons.ToteatSendIconButton
import com.toteat.toteatds.components.textfields.ToteatTextField
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.message_input_placeholder
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

internal val MessageInputSpacing = 12.dp
internal val MessageInputMinHeight = 72.dp
internal val MessageInputShape = RoundedCornerShape(12.dp)

/** Lines the message field grows to before it starts scrolling its own content. */
const val MessageInputMaxLines = 4

/**
 * Message text field with a trailing circular send button.
 *
 * The field is multi-line and taller than the standard [ToteatTextField] so a kitchen note fits
 * without scrolling; text and placeholder are top-aligned. It grows up to [maxLines] and scrolls its
 * own content beyond that, so a long message never pushes the bar off-screen. The soft keyboard
 * shows a "send" action that triggers the same callback as the button. Sending an empty message is
 * the host's call — the button stays active while [enabled] is true, matching the design.
 *
 * @param state Text field state owned by the host.
 * @param onSendClick Invoked when the send button (or the keyboard send action) is used.
 * @param modifier Modifier applied to the root row.
 * @param placeholder Placeholder shown while the field is empty. Defaults to "Escribe un mensaje...".
 * @param enabled Whether the whole input is interactive.
 * @param maxLines Lines the field grows to before scrolling its content.
 * @param focusRequester Optional focus requester forwarded to the text field.
 * @param testTag Optional test tag. Derived tags: `_field`, `_send`.
 */
@Composable
fun ToteatMessageInputBar(
    state: TextFieldState,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    maxLines: Int = MessageInputMaxLines,
    focusRequester: FocusRequester? = null,
    testTag: String = ""
) {
    val resolvedPlaceholder = placeholder ?: stringResource(Res.string.message_input_placeholder)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MessageInputSpacing)
    ) {
        ToteatTextField(
            state = state,
            modifier = Modifier.weight(1f),
            placeHolder = resolvedPlaceholder,
            singleLine = false,
            maxLines = maxLines,
            enabled = enabled,
            minHeight = MessageInputMinHeight,
            shape = MessageInputShape,
            imeAction = ImeAction.Send,
            onKeyboardAction = { if (enabled) onSendClick() },
            focusRequester = focusRequester,
            testTag = if (testTag.isNotEmpty()) "${testTag}_field" else ""
        )

        ToteatSendIconButton(
            onClick = onSendClick,
            enabled = enabled,
            testTag = if (testTag.isNotEmpty()) "${testTag}_send" else ""
        )
    }
}

@Composable
@Preview
private fun ToteatMessageInputBarEmptyPreview() {
    ToteatTheme {
        ToteatMessageInputBar(
            state = rememberTextFieldState(),
            onSendClick = {},
            placeholder = "Mensaje para cocina......",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
@Preview
private fun ToteatMessageInputBarFilledPreview() {
    ToteatTheme {
        ToteatMessageInputBar(
            state = rememberTextFieldState("Sin cebolla, por favor"),
            onSendClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
@Preview
private fun ToteatMessageInputBarDisabledPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(MessageInputSpacing)
        ) {
            ToteatMessageInputBar(
                state = rememberTextFieldState("Mensaje enviándose"),
                onSendClick = {},
                enabled = false
            )
        }
    }
}
