package com.toteat.toteatds.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.components.icons.ChevronDownIcon
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.TertiaryLight
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.theme.bodyMediumRegular
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

// Figma "Dropdown/ select-options" (Design System Toteat, Inputs, 2078:21836).
private val FieldHeight = 50.dp
private val FieldHorizontalPadding = 12.dp
private val FieldVerticalPadding = 8.dp
private val FieldContentSpacing = 8.dp
private val LabelSpacing = 4.dp
private val MenuSpacing = 8.dp
private val MenuMaxHeight = 200.dp
private val OptionHeight = 50.dp
private val OptionPadding = 10.dp
private val OptionSpacing = 8.dp
private val BorderWidth = 1.dp
private val ChevronSize = 16.dp

// "blur-shadow": offset (0, 3), blur radius 10, #1A1A1A at 8 %. SecondaryNormal (#1B1B1B) is the
// palette's closest color.
private val MenuShadowRadius = 10.dp
private val MenuShadowOffset = DpOffset(0.dp, 3.dp)
private const val MenuShadowAlpha = 0.08f

/**
 * Dropdown con el diseño "Dropdown/ select-options" de Figma: campo blanco de 50 dp con borde
 * gris de 1 dp, radio de 8 dp y chevron de trazo fino; al abrirse (o con el mouse encima) el borde
 * se oscurece y el chevron apunta hacia arriba. Las opciones se muestran debajo, en una tarjeta con
 * sombra, y la seleccionada tiene fondo [TertiaryLight].
 *
 * Tiene los mismos parámetros, en el mismo orden, que [ToteatDropDown], que sigue disponible con
 * su diseño anterior: para pasar de uno a otro basta con cambiar el nombre de la función. También
 * comparte su comportamiento (la lista se abre debajo del campo, dentro del layout, con un alto
 * máximo de 200 dp y scroll), su semántica y sus test tags (`testTag`, `${testTag}_label`,
 * `${testTag}_input`).
 *
 * @param placeholder Texto cuando [selectedOption] está vacío; se muestra en gris.
 * @param leadingIcon Ícono opcional a la izquierda del texto, de 16 dp en Figma. Es decorativo.
 */
@Composable
fun ToteatSelectDropDown(
    label: String,
    options: ImmutableList<String>,
    selectedOption: String,
    onOptionSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(Res.string.dropdown_select_placeholder),
    enabled: Boolean = true,
    testTag: String = "",
    leadingIcon: @Composable (() -> Unit)? = null
) {
    SelectDropDownImpl(
        label = label,
        options = options,
        selectedOption = selectedOption,
        onOptionSelect = onOptionSelect,
        modifier = modifier,
        placeholder = placeholder,
        enabled = enabled,
        testTag = testTag,
        leadingIcon = leadingIcon,
        initiallyExpanded = false
    )
}

/** [initiallyExpanded] opens the list on first composition, for previews. */
@Composable
internal fun SelectDropDownImpl(
    label: String,
    options: ImmutableList<String>,
    selectedOption: String,
    onOptionSelect: (String) -> Unit,
    modifier: Modifier,
    placeholder: String,
    enabled: Boolean,
    testTag: String,
    leadingIcon: @Composable (() -> Unit)?,
    initiallyExpanded: Boolean
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val expandedText = stringResource(Res.string.dropdown_expanded)
    val collapsedText = stringResource(Res.string.dropdown_collapsed)
    val dropdownDescription = stringResource(Res.string.dropdown_description, label)

    val labelStyle = selectLabelStyle()
    val textStyle = selectTextStyle()

    // Figma has no disabled variant: like ToteatDropDown, everything turns NeutralGray400.
    val labelColor = if (enabled) NeutralGray500 else NeutralGray400
    val contentColor = if (enabled) SecondaryNormal else NeutralGray400
    val borderColor = when {
        !enabled -> NeutralGray400
        expanded || isHovered -> NeutralGray500 // Figma "master-hover-selected"
        else -> NeutralGray300
    }
    val fieldShape = MaterialTheme.shapes.small

    Column(
        verticalArrangement = Arrangement.spacedBy(LabelSpacing),
        modifier = modifier.then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        Text(
            text = label,
            style = labelStyle,
            color = labelColor,
            modifier = Modifier
                .padding(horizontal = FieldHorizontalPadding)
                .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_label") else Modifier)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(FieldContentSpacing),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = FieldHeight)
                .clip(fieldShape)
                .background(NeutralGray)
                .border(BorderWidth, borderColor, fieldShape)
                .hoverable(interactionSource, enabled = enabled)
                .clickable(
                    enabled = enabled,
                    interactionSource = interactionSource,
                    indication = null
                ) { expanded = !expanded }
                .semantics {
                    role = Role.DropdownList
                    contentDescription = dropdownDescription
                    stateDescription = if (expanded) expandedText else collapsedText
                }
                .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_input") else Modifier)
                .padding(horizontal = FieldHorizontalPadding, vertical = FieldVerticalPadding)
        ) {
            if (leadingIcon != null) leadingIcon()
            Text(
                text = selectedOption.ifEmpty { placeholder },
                style = textStyle,
                color = if (selectedOption.isEmpty()) NeutralGray400 else contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            ChevronDownIcon(
                size = ChevronSize,
                tint = contentColor,
                modifier = Modifier.rotate(if (expanded) 180f else 0f)
            )
        }

        if (expanded) {
            val selectedText = stringResource(Res.string.dropdown_option_selected)
            val menuShape = MaterialTheme.shapes.small
            Box(modifier = Modifier.padding(top = MenuSpacing - LabelSpacing)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(OptionSpacing),
                    modifier = Modifier
                        .fillMaxWidth()
                        .dropShadow(
                            shape = menuShape,
                            shadow = Shadow(
                                radius = MenuShadowRadius,
                                color = SecondaryNormal.copy(alpha = MenuShadowAlpha),
                                offset = MenuShadowOffset
                            )
                        )
                        .clip(menuShape)
                        .background(NeutralGray)
                        .border(BorderWidth, NeutralGray300, menuShape)
                        .heightIn(max = MenuMaxHeight)
                        .verticalScroll(rememberScrollState())
                ) {
                    options.forEachIndexed { index, option ->
                        key(option) {
                            SelectDropDownOption(
                                option = option,
                                isSelected = option == selectedOption,
                                selectedText = selectedText,
                                index = index,
                                totalOptions = options.size,
                                textStyle = textStyle,
                                onClick = {
                                    onOptionSelect(option)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectDropDownOption(
    option: String,
    isSelected: Boolean,
    selectedText: String,
    index: Int,
    totalOptions: Int,
    textStyle: TextStyle,
    onClick: () -> Unit
) {
    val optionDescription = stringResource(Res.string.dropdown_option_description, option, index + 1, totalOptions)

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = OptionHeight)
            // Figma "Dropdown-master-states" active=true.
            .background(if (isSelected) TertiaryLight else Color.Transparent)
            .clickable(onClick = onClick)
            .semantics {
                role = Role.Button
                contentDescription = optionDescription
                if (isSelected) stateDescription = selectedText
            }
            .padding(OptionPadding)
    ) {
        Text(text = option, style = textStyle, color = SecondaryNormal)
    }
}

/** Figma Body-base/B12 R: Manrope Regular 12, line height 1.5. */
@Composable
private fun selectLabelStyle(): TextStyle {
    val base = MaterialTheme.typography.bodyMediumRegular
    return remember(base) { base.copy(lineHeight = 18.sp) }
}

/** Figma Body-base/B14 R: Manrope Regular 14, line height 1.2. */
@Composable
private fun selectTextStyle(): TextStyle {
    val base = MaterialTheme.typography.bodyLargeRegular
    return remember(base) { base.copy(lineHeight = 16.8.sp) }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 300)
@Composable
private fun ToteatSelectDropDownPreview() {
    ToteatTheme {
        var selected by remember { mutableStateOf("") }
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ToteatSelectDropDown(
                label = "Meseros turno",
                options = persistentListOf("Mesero 1", "Mesero 2", "Mesero 3"),
                selectedOption = selected,
                onOptionSelect = { selected = it },
                placeholder = "Seleccionar mesero"
            )
            ToteatSelectDropDown(
                label = "Meseros turno",
                options = persistentListOf("Mesero 1", "Mesero 2", "Mesero 3"),
                selectedOption = "Mesero 2",
                onOptionSelect = {}
            )
            ToteatSelectDropDown(
                label = "Meseros turno",
                options = persistentListOf("Mesero 1", "Mesero 2", "Mesero 3"),
                selectedOption = "Mesero 2",
                onOptionSelect = {},
                enabled = false
            )
        }
    }
}

/** Abierto, como la variante active=true de Figma, con la segunda opción seleccionada. */
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 300)
@Composable
private fun ToteatSelectDropDownExpandedPreview() {
    ToteatTheme {
        SelectDropDownImpl(
            label = "Label",
            options = persistentListOf("Opción 1", "Opción 2", "Opción 3"),
            selectedOption = "Opción 2",
            onOptionSelect = {},
            modifier = Modifier.padding(16.dp),
            placeholder = "Select options",
            enabled = true,
            testTag = "",
            leadingIcon = null,
            initiallyExpanded = true
        )
    }
}
