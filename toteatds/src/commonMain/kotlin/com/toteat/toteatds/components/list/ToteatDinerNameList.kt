package com.toteat.toteatds.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.maxLength
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
import androidx.compose.runtime.rememberUpdatedState
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
private const val DefaultDinerNameMaxLength = 50

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
 *
 * @param editingIndex Row being edited when the screen controls editing, `null` for none.
 * Ignored unless [onEditingIndexChange] is set.
 * @param onEditingIndexChange When `null` (default) the list keeps the editing row in its own
 * state, as before. When set, the list keeps no state: the pencil calls it with the row index,
 * and confirming a name calls [onNameChange] first and then this with `null`. Setting
 * [editingIndex] to a row opens it with the text of `names[i]` and focuses the field, so going
 * back from `null` to the same row re-opens it after its name was confirmed (e.g. after an alert
 * for a repeated name).
 * @param maxNameLength Character cap for the name field, 50 by default. `null` caps nothing.
 * Rejects typed and pasted text past the cap; a longer name already in [names] opens cut to it.
 * `<= 0` fails with `require`.
 */
@Composable
fun ToteatDinerNameList(
    names: ImmutableList<String>,
    onNameChange: (index: Int, name: String) -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "",
    // New parameters go after the previously published ones so positional call sites keep working.
    editingIndex: Int? = null,
    onEditingIndexChange: ((Int?) -> Unit)? = null,
    maxNameLength: Int? = DefaultDinerNameMaxLength
) {
    require(maxNameLength == null || maxNameLength > 0) {
        "maxNameLength must be greater than zero, was $maxNameLength"
    }

    val listDescription = stringResource(Res.string.diner_name_list_description)
    var internalEditingIndex by remember { mutableIntStateOf(NoEditingIndex) }
    val currentEditingIndex = if (onEditingIndexChange != null) {
        editingIndex ?: NoEditingIndex
    } else {
        internalEditingIndex
    }
    val latestEditingIndex by rememberUpdatedState(currentEditingIndex)
    val latestOnEditingIndexChange by rememberUpdatedState(onEditingIndexChange)
    val latestOnNameChange by rememberUpdatedState(onNameChange)

    fun setEditingIndex(index: Int) {
        val onChange = latestOnEditingIndexChange
        if (onChange != null) {
            onChange(index.takeIf { it != NoEditingIndex })
        } else {
            internalEditingIndex = index
        }
    }

    val inputTransformation = remember(maxNameLength) {
        maxNameLength?.let { InputTransformation.maxLength(it) }
    }

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
                isEditing = currentEditingIndex == index,
                maxNameLength = maxNameLength,
                inputTransformation = inputTransformation,
                onEditClick = { setEditingIndex(index) },
                onCommit = { newName ->
                    latestOnNameChange(index, newName)
                    // A row that loses focus because another row took over editing only reports
                    // its name; closing editing here would cancel the row that was just opened.
                    if (latestEditingIndex == index) setEditingIndex(NoEditingIndex)
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
    maxNameLength: Int?,
    inputTransformation: InputTransformation?,
    onEditClick: () -> Unit,
    onCommit: (String) -> Unit,
    testTag: String
) {
    // Leaving editing drops this state, so opening the row again starts from the current name.
    // A name longer than the cap opens cut to it: maxLength rejects every edit while over the cap.
    val fieldState = if (isEditing) {
        remember { TextFieldState(maxNameLength?.let { name.take(it) } ?: name) }
    } else {
        null
    }
    // The pencil, the keyboard action and the focus loss that may follow them report the name
    // only once per edit, so the screen does not get the same name (or alert) twice.
    var committed by remember(isEditing) { mutableStateOf(false) }
    fun commit(state: TextFieldState) {
        if (committed) return
        committed = true
        onCommit(state.text.toString().trim())
    }

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
                    inputTransformation = inputTransformation,
                    placeholder = placeholder,
                    onCommit = { commit(fieldState) },
                    onFocus = { committed = false },
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
                            commit(fieldState)
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
    inputTransformation: InputTransformation?,
    placeholder: String,
    onCommit: () -> Unit,
    onFocus: () -> Unit,
    testTag: String
) {
    val focusRequester = remember { FocusRequester() }
    var hadFocus by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        state = fieldState,
        inputTransformation = inputTransformation,
        lineLimits = TextFieldLineLimits.SingleLine,
        textStyle = MaterialTheme.typography.bodyLargeRegular.copy(
            color = MaterialTheme.colorScheme.extended.neutral500
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        onKeyboardAction = KeyboardActionHandler { onCommit() },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { state ->
                if (state.isFocused) {
                    hadFocus = true
                    onFocus()
                } else if (hadFocus) {
                    onCommit()
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

@Preview(showBackground = true, backgroundColor = 0xFFEEEEEE)
@Composable
private fun ToteatDinerNameListControlledPreview() {
    ToteatTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            // The screen holds the editing row: row 2 opens in edit mode with its current name.
            ToteatDinerNameList(
                names = persistentListOf("Trini", "Pauli", ""),
                onNameChange = { _, _ -> },
                editingIndex = 1,
                onEditingIndexChange = {}
            )
            // Names capped at 10 characters.
            ToteatDinerNameList(
                names = persistentListOf("Fernanda", ""),
                onNameChange = { _, _ -> },
                maxNameLength = 10
            )
        }
    }
}
