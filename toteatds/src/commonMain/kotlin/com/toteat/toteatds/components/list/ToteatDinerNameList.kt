package com.toteat.toteatds.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.helperBold
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.diner_name_edit_description
import designsystemmobile.toteatds.generated.resources.diner_name_list_description
import designsystemmobile.toteatds.generated.resources.diner_name_placeholder
import designsystemmobile.toteatds.generated.resources.icon_edit_pen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// Figma "Identificar comensales" (Toteat Go, Abrir mesa, selector 23838:17199): 52 dp rows
// (16 + 20 dp of Bold 11 at 1.8 line height + 16) 8 dp apart, 12 dp side padding, a 16 dp pencil
// 12 dp from the right edge and the "tab-light" shadow.
private val DinerNameRowHeight = 52.dp
private val DinerNameRowSpacing = 8.dp
private val DinerNameRowHorizontalPadding = 12.dp
private val DinerNameEditIconSize = 16.dp
private val DinerNameEditTouchSize = 48.dp
private val DinerNameTextLineHeight = 19.8.sp

// "tab-light": offset (0, 1), blur radius 2, #D0D0D0 at 52 %. NeutralGray300 (#CFCFCF) is the
// palette's closest color.
private val DinerNameRowShadowRadius = 2.dp
private val DinerNameRowShadowOffset = DpOffset(0.dp, 1.dp)
private const val DinerNameRowShadowAlpha = 0.52f

// The LazyColumn clips its content: this keeps the shadow of the first row (1 dp above) and the
// last row (offset + blur, 3 dp below) visible.
private val DinerNameListShadowPadding = PaddingValues(top = 1.dp, bottom = 3.dp)
private const val NoEditingIndex = -1
private const val DefaultDinerNameMaxLength = 50

/**
 * Editable list used to identify diners by name.
 *
 * Renders one card per entry in [names]. An empty name shows the
 * "Comensal N" placeholder; tapping anywhere on the row (or its trailing
 * pencil) turns it into a text field so the name can be typed. The new value is reported
 * through [onNameChange] when the keyboard action confirms or the field
 * loses focus. Backed by a [LazyColumn], so long lists scroll vertically;
 * when embedded inside another vertical scrollable, give it a bounded
 * height.
 *
 * @param editingIndex Row being edited when the screen controls editing, `null` for none.
 * Ignored unless [onEditingIndexChange] is set.
 * @param onEditingIndexChange When `null` (default) the list keeps the editing row in its own
 * state, as before. When set, the list keeps no state: tapping a row calls it with the row index,
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
        verticalArrangement = Arrangement.spacedBy(DinerNameRowSpacing),
        contentPadding = DinerNameListShadowPadding,
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

    val rowShape = MaterialTheme.shapes.large
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(DinerNameRowHeight)
            // Before clip and background so the shadow is drawn outside the row.
            .dropShadow(
                shape = rowShape,
                shadow = Shadow(
                    radius = DinerNameRowShadowRadius,
                    color = NeutralGray300.copy(alpha = DinerNameRowShadowAlpha),
                    offset = DinerNameRowShadowOffset
                )
            )
            .clip(rowShape)
            .background(MaterialTheme.colorScheme.background)
            // While not editing, tapping anywhere on the card opens it, not only the pencil.
            // While editing, the card takes no taps so the text field keeps them.
            .then(
                if (fieldState == null) {
                    Modifier.clickable(
                        onClickLabel = editDescription,
                        role = Role.Button,
                        onClick = onEditClick
                    )
                } else {
                    Modifier
                }
            )
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            // No end padding: the pencil's 48 dp touch area reaches the row's edge and the
            // icon sits 12 dp inside it (see below).
            .padding(start = DinerNameRowHorizontalPadding)
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
                    style = dinerNameTextStyle(),
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

        // 48 dp touch area flush with the row's right edge; the 16 dp icon is aligned to its end
        // with 12 dp of padding, so it sits 12 dp from the edge as in Figma. The touch area is
        // not reduced: only the icon moves inside it (4 dp right of its center).
        // sizeIn makes the clickable node itself 48 dp: minimumInteractiveComponentSize alone
        // reserves 48 dp but centers a smaller child, which would put the icon 22 dp from the edge.
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .sizeIn(minWidth = DinerNameEditTouchSize, minHeight = DinerNameEditTouchSize)
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
                imageVector = vectorResource(Res.drawable.icon_edit_pen),
                contentDescription = null,
                tint = SecondaryNormal,
                modifier = Modifier
                    .padding(end = DinerNameRowHorizontalPadding)
                    .size(DinerNameEditIconSize)
            )
        }
    }
}

/**
 * Figma Body-base/helper B (Manrope Bold 11, line height 1.8) for the placeholder, the name and
 * the text field, so opening a row for editing doesn't change how it looks.
 */
@Composable
private fun dinerNameTextStyle(): TextStyle {
    val helperBold = MaterialTheme.typography.helperBold
    return remember(helperBold) { helperBold.copy(lineHeight = DinerNameTextLineHeight) }
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
        textStyle = dinerNameTextStyle().copy(
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
                        style = dinerNameTextStyle(),
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

/**
 * Narrow POS width (360 dp) on the Figma screen background, to check that the Bold 11 text reads
 * well: placeholder, a name, a long name that ellipsizes, and a row being edited.
 */
@Preview(showBackground = true, backgroundColor = 0xFFFAFAFA, widthDp = 360)
@Composable
private fun ToteatDinerNameListNarrowPreview() {
    ToteatTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            ToteatDinerNameList(
                names = persistentListOf("", "Trini", "Maximiliano Fernández de la Fuente"),
                onNameChange = { _, _ -> }
            )
            ToteatDinerNameList(
                names = persistentListOf("Pauli"),
                onNameChange = { _, _ -> },
                editingIndex = 0,
                onEditingIndexChange = {}
            )
        }
    }
}
