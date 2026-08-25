package com.toteat.toteatds.components.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.buttons.ToteatChipButtonContainer
import com.toteat.toteatds.components.icons.CircularIconButtonSize
import com.toteat.toteatds.components.icons.ToteatPrintIconButton
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.comment_bottom_bar_description
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val SuggestionChipHeight = 18.dp
private val HorizontalPadding = 16.dp
private val VerticalPadding = 8.dp

/**
 * Height of the suggestions row. The circular print action is the tallest thing in it, and its
 * 48.dp minimum touch target must not stretch the row: pinning the height keeps the reserved area
 * from showing up as blank space between the chips and the message field.
 */
private val ActionRowHeight = CircularIconButtonSize

/** Gap the design asks for between the suggestion pill and the message field. */
private val SuggestionToInputSpacing = 10.dp

/**
 * The chips are centered inside [ActionRowHeight], so half of the difference against the row height
 * already sits below the pill; only the remainder is real spacing.
 */
private val ContentSpacing =
    SuggestionToInputSpacing - (ActionRowHeight - SuggestionChipHeight) / 2

/**
 * Bottom bar for comment / messaging screens (e.g. "Comunicación cocina").
 *
 * Two rows: quick-suggestion chips with an optional circular print action on the right, and the
 * multi-line message field with the circular send button on the right. Tapping a suggestion reports
 * it through [onSuggestionClick] so the host decides whether to append it to the message or send it
 * directly — the component keeps no state of its own beyond what it is given.
 *
 * @param state Text field state owned by the host.
 * @param onSendClick Invoked when the send button (or the keyboard send action) is used.
 * @param modifier Modifier applied to the root container.
 * @param suggestions Quick suggestions rendered as chips. Pass an empty list to hide the row.
 * @param onSuggestionClick Invoked with the tapped suggestion.
 * @param onPrintClick Invoked when the print button is tapped. Pass `null` to hide the button.
 * @param placeholder Placeholder shown while the field is empty.
 * @param enabled Whether the bar is interactive.
 * @param maxLines Lines the message field grows to before scrolling its content, so a long note
 * never pushes the bar off-screen.
 * @param focusRequester Optional focus requester forwarded to the text field.
 * @param testTag Optional test tag. Derived tags: `_suggestions`, `_print`, `_input`, `_input_field`,
 * `_input_send`.
 */
@Composable
fun ToteatCommentBottomBar(
    state: TextFieldState,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    suggestions: ImmutableList<String> = persistentListOf(),
    onSuggestionClick: (String) -> Unit = {},
    onPrintClick: (() -> Unit)? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    maxLines: Int = MessageInputMaxLines,
    focusRequester: FocusRequester? = null,
    testTag: String = ""
) {
    val barDescription = stringResource(Res.string.comment_bottom_bar_description)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .semantics { contentDescription = barDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.extended.neutral100
        )

        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = HorizontalPadding, vertical = VerticalPadding),
            verticalArrangement = Arrangement.spacedBy(ContentSpacing)
        ) {
            if (suggestions.isNotEmpty() || onPrintClick != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ActionRowHeight),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MessageInputSpacing)
                ) {
                    if (suggestions.isNotEmpty()) {
                        ToteatChipButtonContainer(
                            items = suggestions,
                            selectedItem = null,
                            onItemSelect = onSuggestionClick,
                            modifier = Modifier.weight(1f),
                            enabled = enabled,
                            containerColor = MaterialTheme.colorScheme.extended.tertiarySurface,
                            itemHeight = SuggestionChipHeight,
                            testTag = if (testTag.isNotEmpty()) "${testTag}_suggestions" else ""
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    onPrintClick?.let { onPrint ->
                        ToteatPrintIconButton(
                            onClick = onPrint,
                            enabled = enabled,
                            testTag = if (testTag.isNotEmpty()) "${testTag}_print" else ""
                        )
                    }
                }
            }

            ToteatMessageInputBar(
                state = state,
                onSendClick = onSendClick,
                placeholder = placeholder,
                enabled = enabled,
                maxLines = maxLines,
                focusRequester = focusRequester,
                testTag = if (testTag.isNotEmpty()) "${testTag}_input" else ""
            )
        }
    }
}

@Composable
@Preview
private fun ToteatCommentBottomBarPreview() {
    ToteatTheme {
        ToteatCommentBottomBar(
            state = rememberTextFieldState(),
            onSendClick = {},
            suggestions = persistentListOf("Sin cebolla", "Sin sal", "Sin picante", "Término medio"),
            onPrintClick = {},
            placeholder = "Mensaje para cocina......"
        )
    }
}

@Composable
@Preview
private fun ToteatCommentBottomBarFilledPreview() {
    ToteatTheme {
        ToteatCommentBottomBar(
            state = rememberTextFieldState("Sin cebolla"),
            onSendClick = {},
            suggestions = persistentListOf("Sin cebolla", "Sin sal", "Sin picante", "Término medio"),
            onPrintClick = {},
            placeholder = "Mensaje para cocina......"
        )
    }
}

@Composable
@Preview
private fun ToteatCommentBottomBarNoSuggestionsPreview() {
    ToteatTheme {
        ToteatCommentBottomBar(
            state = rememberTextFieldState(),
            onSendClick = {},
            placeholder = "Mensaje para cocina......"
        )
    }
}
