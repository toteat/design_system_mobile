package com.toteat.toteatds.components.dropdown

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyMediumRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.dropdown_collapsed
import designsystemmobile.toteatds.generated.resources.dropdown_description
import designsystemmobile.toteatds.generated.resources.dropdown_expanded
import designsystemmobile.toteatds.generated.resources.dropdown_option_description
import designsystemmobile.toteatds.generated.resources.dropdown_option_selected
import designsystemmobile.toteatds.generated.resources.dropdown_select_placeholder
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToteatDropDown(
    label: String,
    options: ImmutableList<String>,
    selectedOption: String,
    onOptionSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(Res.string.dropdown_select_placeholder),
    enabled: Boolean = true,
    testTag: String = ""
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val density = LocalDensity.current
    
    val dropdownDescriptionText = stringResource(Res.string.dropdown_description, label)
    val expandedText = stringResource(Res.string.dropdown_expanded)
    val collapsedText = stringResource(Res.string.dropdown_collapsed)

    Column(modifier = modifier.setTestTag(testTag)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMediumRegular.copy(fontSize = 12.sp),
            modifier = Modifier.padding(start = 12.dp),
            color = if (enabled) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.extended.neutral400
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = enabled) { expanded = !expanded }
                .semantics {
                    role = Role.DropdownList
                    contentDescription = dropdownDescriptionText
                    stateDescription = if (expanded) expandedText else collapsedText
                }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { textFieldSize = Size(it.width.toFloat(), it.height.toFloat()) }
                    .defaultMinSize(minHeight = 50.dp),
                placeholder = @Composable { Text(placeholder, fontSize = 14.sp) },
                trailingIcon = @Composable { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                readOnly = true,
                enabled = false,
                shape = MaterialTheme.shapes.medium,
                textStyle = MaterialTheme.typography.bodyMediumRegular.copy(fontSize = 14.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = if (enabled) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.extended.neutral400,
                    disabledBorderColor = if (enabled) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.extended.neutral400,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.extended.neutral400,
                    disabledTrailingIconColor = if (enabled) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.extended.neutral400
                )
            )
        }

        if (expanded) {
            val textFieldHeightInDp = with(density) { textFieldSize.height.toDp() }
            val textFieldWidthInDp = with(density) { textFieldSize.width.toDp() }
            val selectedText = stringResource(Res.string.dropdown_option_selected)

            Popup(
                alignment = Alignment.TopStart,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                Card(
                    modifier = Modifier
                        .width(textFieldWidthInDp)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            val topOffset = with(density) { (textFieldHeightInDp + 8.dp).toPx() }.toInt()
                            layout(placeable.width, placeable.height) {
                                placeable.place(0, topOffset)
                            }
                        },
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        options.forEachIndexed { index, selectionOption ->
                            val isSelected = selectionOption == selectedOption
                            val optionDescription = stringResource(
                                Res.string.dropdown_option_description,
                                selectionOption,
                                index + 1,
                                options.size
                            )
                            
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .defaultMinSize(minHeight = 50.dp)
                                    .clickable {
                                        onOptionSelect(selectionOption)
                                        expanded = false
                                    }
                                    .background(
                                        color = if (isSelected) MaterialTheme.colorScheme.tertiaryContainer else Color.Transparent
                                    )
                                    .padding(horizontal = 16.dp)
                                    .semantics {
                                        role = Role.Button
                                        contentDescription = optionDescription
                                        if (isSelected) {
                                            stateDescription = selectedText
                                        }
                                    },
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    text = selectionOption,
                                    style = MaterialTheme.typography.bodyMediumRegular.copy(fontSize = 14.sp),
                                    color = if (isSelected) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.secondary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ToteatDropDownPreview() {
    ToteatTheme {
        var selectedOption by remember { mutableStateOf("") }
        val options = persistentListOf("Mesero 1", "Mesero 2", "Mesero 3")

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            ToteatDropDown(
                label = "Meseros turno",
                options = options,
                selectedOption = selectedOption,
                onOptionSelect = { selectedOption = it },
                placeholder = "Seleccionar mesero"
            )
        }
    }
}

@Preview
@Composable
private fun ToteatDropDownWithSelectionPreview() {
    ToteatTheme {
        var selectedOption by remember { mutableStateOf("Mesero 2") }
        val options = persistentListOf("Mesero 1", "Mesero 2", "Mesero 3")

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            ToteatDropDown(
                label = "Meseros turno",
                options = options,
                selectedOption = selectedOption,
                onOptionSelect = { selectedOption = it },
                placeholder = "Seleccionar mesero"
            )
        }
    }
}

@Preview
@Composable
private fun ToteatDropDownDisabledPreview() {
    ToteatTheme {
        var selectedOption by remember { mutableStateOf("Mesero 2") }
        val options = persistentListOf("Mesero 1", "Mesero 2", "Mesero 3")

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            ToteatDropDown(
                label = "Meseros turno",
                options = options,
                selectedOption = selectedOption,
                onOptionSelect = { selectedOption = it },
                placeholder = "Seleccionar mesero",
                enabled = false
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ToteatDropDownExpandedPreview() {
    ToteatTheme {
        var expanded by remember { mutableStateOf(true) }
        var selectedOption by remember { mutableStateOf("Mesero 2") }
        var textFieldSize by remember { mutableStateOf(Size.Zero) }
        val density = LocalDensity.current
        val options = persistentListOf("Mesero 1", "Mesero 2", "Mesero 3")

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Text(
                text = "Meseros turno",
                style = MaterialTheme.typography.bodyMediumRegular.copy(fontSize = 12.sp),
                modifier = Modifier.padding(start = 12.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedOption,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .onSizeChanged { textFieldSize = Size(it.width.toFloat(), it.height.toFloat()) }
                        .defaultMinSize(minHeight = 50.dp),
                    placeholder = @Composable { Text("Seleccionar mesero", fontSize = 14.sp) },
                    trailingIcon = @Composable { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    readOnly = true,
                    enabled = false,
                    shape = MaterialTheme.shapes.medium,
                    textStyle = MaterialTheme.typography.bodyMediumRegular.copy(fontSize = 14.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.secondary,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.extended.neutral400,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            if (expanded && textFieldSize.width > 0) {
                val textFieldHeightInDp = with(density) { textFieldSize.height.toDp() }
                val textFieldWidthInDp = with(density) { textFieldSize.width.toDp() }

                Box(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Card(
                        modifier = Modifier.width(textFieldWidthInDp),
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            options.forEach { selectionOption ->
                                val isSelected = selectionOption == selectedOption
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .defaultMinSize(minHeight = 50.dp)
                                        .clickable {
                                            selectedOption = selectionOption
                                            expanded = false
                                        }
                                        .background(
                                            color = if (isSelected) MaterialTheme.colorScheme.tertiaryContainer else Color.Transparent
                                        )
                                        .padding(horizontal = 16.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text(
                                        text = selectionOption,
                                        style = MaterialTheme.typography.bodyMediumRegular.copy(fontSize = 14.sp),
                                        color = if (isSelected) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.secondary,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
