package com.toteat.toteatds.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.diner_name_edit_description
import designsystemmobile.toteatds.generated.resources.diner_name_list_description
import designsystemmobile.toteatds.generated.resources.diner_name_placeholder
import designsystemmobile.toteatds.generated.resources.icon_edit_pencil
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val DinerNameRowHeight = 48.dp
private val DinerNameEditIconSize = 20.dp
private const val NoEditingIndex = -1

/**
 * Editable list used to identify diners by name.
 *
 * Renders one card per entry in [names]. An empty name shows the
 * "Comensal N" placeholder; tapping the trailing pencil turns the row
 * into a text field so the name can be typed. The new value is reported
 * through [onNameChange] when the keyboard action confirms or the field
 * loses focus. Backed by a [LazyColumn], so long lists scroll vertically;
 * when embedded inside another vertical scrollable, give it a bounded
 * height.
 */
@Composable
fun ToteatDinerNameList(
    names: ImmutableList<String>,
    onNameChange: (index: Int, name: String) -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    val listDescription = stringResource(Res.string.diner_name_list_description)
    var editingIndex by remember { mutableIntStateOf(NoEditingIndex) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .semantics { contentDescription = listDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        itemsIndexed(
            items = names,
            key = { index, _ -> "diner_name_$index" }
        ) { index, name ->
            DinerNameRow(
                name = name,
                placeholder = stringResource(Res.string.diner_name_placeholder, index + 1),
                editDescription = stringResource(Res.string.diner_name_edit_description, index + 1),
                isEditing = editingIndex == index,
                onEditClick = { editingIndex = index },
                onCommit = { newName ->
                    onNameChange(index, newName)
                    editingIndex = NoEditingIndex
                },
                testTag = if (testTag.isNotEmpty()) "${testTag}_row_${index + 1}" else ""
            )
        }
    }
}

@Composable
private fun DinerNameRow(
    name: String,
    placeholder: String,
    editDescription: String,
    isEditing: Boolean,
    onEditClick: () -> Unit,
    onCommit: (String) -> Unit,
    testTag: String
) {
    val fieldState = if (isEditing) remember { TextFieldState(name) } else null

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(DinerNameRowHeight)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .padding(start = 20.dp, end = 4.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.weight(1f)
        ) {
            if (fieldState != null) {
                DinerNameTextField(
                    fieldState = fieldState,
                    placeholder = placeholder,
                    onCommit = onCommit,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_input" else ""
                )
            } else {
                Text(
                    text = name.ifBlank { placeholder },
                    style = MaterialTheme.typography.bodyLargeRegular,
                    color = if (name.isBlank()) {
                        MaterialTheme.colorScheme.extended.neutral400
                    } else {
                        MaterialTheme.colorScheme.extended.neutral500
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .clip(CircleShape)
                .clickable(
                    onClick = {
                        if (fieldState != null) {
                            onCommit(fieldState.text.toString().trim())
                        } else {
                            onEditClick()
                        }
                    },
                    role = Role.Button
                )
                .semantics {
                    role = Role.Button
                    contentDescription = editDescription
                }
                .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_edit") else Modifier)
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.icon_edit_pencil),
                contentDescription = null,
                tint = SecondaryNormal,
                modifier = Modifier.size(DinerNameEditIconSize)
            )
        }
    }
}

@Composable
private fun DinerNameTextField(
    fieldState: TextFieldState,
    placeholder: String,
    onCommit: (String) -> Unit,
    testTag: String
) {
    val focusRequester = remember { FocusRequester() }
    var hadFocus by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        state = fieldState,
        lineLimits = TextFieldLineLimits.SingleLine,
        textStyle = MaterialTheme.typography.bodyLargeRegular.copy(
            color = MaterialTheme.colorScheme.extended.neutral500
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        onKeyboardAction = KeyboardActionHandler {
            onCommit(fieldState.text.toString().trim())
        },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { state ->
                if (state.isFocused) {
                    hadFocus = true
                } else if (hadFocus) {
                    onCommit(fieldState.text.toString().trim())
                }
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        decorator = { innerTextField ->
            Box(contentAlignment = Alignment.CenterStart) {
                if (fieldState.text.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyLargeRegular,
                        color = MaterialTheme.colorScheme.extended.neutral400
                    )
                }
                innerTextField()
            }
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFEEEEEE)
@Composable
private fun ToteatDinerNameListPreview() {
    ToteatTheme {
        ToteatDinerNameList(
            names = persistentListOf("Trini", "", ""),
            onNameChange = { _, _ -> },
            modifier = Modifier.padding(16.dp)
        )
    }
}
