package com.toteat.toteatds.components.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
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
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray100
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.TertiaryLight
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_search_stroke
import designsystemmobile.toteatds.generated.resources.search_bar_description
import designsystemmobile.toteatds.generated.resources.search_bar_icon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/** Color de [ToteatSearchInput], como la propiedad "Color" de "Input/ search" en Figma. */
enum class ToteatSearchInputColor {
    /** Fondo gris claro (neutral100): el aspecto de [ToteatSearchBar]. */
    Gray,

    /** Fondo blanco, para usar sobre fondos grises. */
    White,

    /** Fondo gris oscuro (neutral500) con resultados oscuros. */
    Dark
}

/**
 * Resultado que [ToteatSearchInput] muestra debajo del campo.
 *
 * @param key Identificador único del resultado (por ejemplo, el id del producto). Se usa como
 * clave de la lista y en el test tag de la fila.
 * @param label Texto de la fila.
 */
@Immutable
data class ToteatSearchSuggestion(val key: String, val label: String)

// Figma "Input/ search" (Design System Toteat, 636:4483).
private val FieldHeight = 50.dp
private val FieldHorizontalPadding = 12.dp
private val FieldContentSpacing = 8.dp
private val FieldShape = RoundedCornerShape(8.dp)
private val SearchIconSize = 24.dp
private val BorderWidth = 1.dp
private val ListSpacing = 8.dp
private val ListShape = RoundedCornerShape(6.dp)
private val SuggestionHeight = 50.dp
private val SuggestionPadding = 10.dp
private val SuggestionContentSpacing = 8.dp
private val SuggestionImageSize = 40.dp
private val SuggestionListMaxHeight = SuggestionHeight * 5
private const val DisabledContainerAlpha = 0.5f

// "blur-shadow": offset (0, 3), blur radius 10, #1A1A1A at 8 %. SecondaryNormal (#1B1B1B) is the
// palette's closest color.
private val ListShadowRadius = 10.dp
private val ListShadowOffset = DpOffset(0.dp, 3.dp)
private const val ListShadowAlpha = 0.08f

/**
 * Buscador con el diseño "Input/ search" de Figma. Es una versión ampliada de
 * [ToteatSearchBar], que sigue disponible: tiene sus mismos parámetros, en el mismo orden, con el
 * mismo comportamiento, semántica y test tag, así que para pasar de uno a otro basta con cambiar el
 * nombre de la función. Agrega:
 *
 * - [color]: gris (por defecto, como [ToteatSearchBar]), blanco u oscuro.
 * - Resultados: si [suggestions] no está vacía (y el campo está habilitado), se muestran debajo del
 *   campo, a 8 dp, en una tarjeta con sombra; al tocar uno se llama a [onSuggestionClick]. La
 *   pantalla decide cuándo mostrarlos o cerrarlos pasando la lista o una lista vacía. Muestra hasta
 *   5 filas y luego hace scroll.
 *
 * Con foco, o con resultados abiertos, el campo usa el estado "focused" de Figma: borde en gris y
 * blanco, y texto y lupa más oscuros (más claros en el oscuro).
 *
 * @param iconTint Color de la lupa cuando el campo está habilitado. Si es `null`, usa el color de
 * Figma para [color] y el estado. Deshabilitado, la lupa siempre usa neutral400.
 * @param suggestionImage Imagen opcional de cada resultado. Se muestra recortada en un círculo de
 * 40 dp con borde neutral100 y fondo neutral200 mientras carga; el DS no carga imágenes, así que la
 * app pasa su propio composable (por ejemplo, un `AsyncImage` con `Modifier.fillMaxSize()`). Si es
 * `null`, las filas muestran solo el texto.
 */
@Composable
fun ToteatSearchInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    iconTint: Color? = null,
    onSearch: () -> Unit = {},
    testTag: String = "",
    color: ToteatSearchInputColor = ToteatSearchInputColor.Gray,
    suggestions: ImmutableList<ToteatSearchSuggestion> = persistentListOf(),
    onSuggestionClick: (ToteatSearchSuggestion) -> Unit = {},
    suggestionImage: (@Composable (ToteatSearchSuggestion) -> Unit)? = null
) {
    SearchInputImpl(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        enabled = enabled,
        iconTint = iconTint,
        onSearch = onSearch,
        testTag = testTag,
        color = color,
        suggestions = suggestions,
        onSuggestionClick = onSuggestionClick,
        suggestionImage = suggestionImage,
        previewFocused = false,
        previewHighlightedIndex = null
    )
}

/**
 * [previewFocused] shows the focused look and [previewHighlightedIndex] highlights a row, for
 * previews: neither can be triggered without input.
 */
@Composable
internal fun SearchInputImpl(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    placeholder: String,
    enabled: Boolean,
    iconTint: Color?,
    onSearch: () -> Unit,
    testTag: String,
    color: ToteatSearchInputColor,
    suggestions: ImmutableList<ToteatSearchSuggestion>,
    onSuggestionClick: (ToteatSearchSuggestion) -> Unit,
    suggestionImage: (@Composable (ToteatSearchSuggestion) -> Unit)?,
    previewFocused: Boolean,
    previewHighlightedIndex: Int?
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val description = stringResource(Res.string.search_bar_description)
    val searchIconDescription = stringResource(Res.string.search_bar_icon)
    val textStyle = searchInputTextStyle()

    val isOpen = enabled && suggestions.isNotEmpty()
    val isActive = enabled && (isFocused || isOpen || previewFocused)
    val colors = searchInputColors(color, enabled, isActive)

    Column(
        verticalArrangement = Arrangement.spacedBy(ListSpacing),
        modifier = modifier
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = true,
            textStyle = textStyle.copy(color = colors.text),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .height(FieldHeight)
                .background(colors.container, FieldShape)
                .border(BorderWidth, colors.border ?: Color.Transparent, FieldShape)
                .semantics { contentDescription = description }
                .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(FieldContentSpacing),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = FieldHorizontalPadding)
                ) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty() && placeholder.isNotEmpty()) {
                            Text(
                                text = placeholder,
                                style = textStyle,
                                color = NeutralGray400,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    }
                    Icon(
                        imageVector = vectorResource(Res.drawable.icon_search_stroke),
                        contentDescription = searchIconDescription,
                        tint = if (enabled && iconTint != null) iconTint else colors.icon,
                        modifier = Modifier.size(SearchIconSize)
                    )
                }
            }
        )

        if (isOpen) {
            SuggestionList(
                suggestions = suggestions,
                color = color,
                textStyle = textStyle,
                onSuggestionClick = onSuggestionClick,
                suggestionImage = suggestionImage,
                testTag = testTag,
                previewHighlightedIndex = previewHighlightedIndex
            )
        }
    }
}

@Composable
private fun SuggestionList(
    suggestions: ImmutableList<ToteatSearchSuggestion>,
    color: ToteatSearchInputColor,
    textStyle: TextStyle,
    onSuggestionClick: (ToteatSearchSuggestion) -> Unit,
    suggestionImage: (@Composable (ToteatSearchSuggestion) -> Unit)?,
    testTag: String,
    previewHighlightedIndex: Int?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow(
                shape = ListShape,
                shadow = Shadow(
                    radius = ListShadowRadius,
                    color = SecondaryNormal.copy(alpha = ListShadowAlpha),
                    offset = ListShadowOffset
                )
            )
            .clip(ListShape)
            .background(NeutralGray)
            .border(BorderWidth, NeutralGray300, ListShape)
            .heightIn(max = SuggestionListMaxHeight)
            .verticalScroll(rememberScrollState())
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_suggestions") else Modifier)
    ) {
        suggestions.forEachIndexed { index, suggestion ->
            key(suggestion.key) {
                SuggestionRow(
                    suggestion = suggestion,
                    color = color,
                    textStyle = textStyle,
                    forceHighlight = index == previewHighlightedIndex,
                    onClick = { onSuggestionClick(suggestion) },
                    image = suggestionImage,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_suggestion_${suggestion.key}" else ""
                )
            }
        }
    }
}

@Composable
private fun SuggestionRow(
    suggestion: ToteatSearchSuggestion,
    color: ToteatSearchInputColor,
    textStyle: TextStyle,
    forceHighlight: Boolean,
    onClick: () -> Unit,
    image: (@Composable (ToteatSearchSuggestion) -> Unit)?,
    testTag: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    // Figma "Dropdown/ search-states": the highlighted row (hover or pressed) changes background.
    val highlighted = forceHighlight || isPressed || isHovered
    val isDark = color == ToteatSearchInputColor.Dark
    val background = when {
        isDark && highlighted -> NeutralGray400
        isDark -> NeutralGray500
        highlighted -> TertiaryLight
        else -> NeutralGray
    }
    val textColor = when {
        isDark && highlighted -> NeutralGray
        isDark -> NeutralGray100
        else -> SecondaryNormal
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SuggestionContentSpacing),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = SuggestionHeight)
            .background(background)
            .hoverable(interactionSource)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .semantics(mergeDescendants = true) { role = Role.Button }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .padding(SuggestionPadding)
    ) {
        if (image != null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(SuggestionImageSize)
                    .clip(CircleShape)
                    .background(NeutralGray200)
                    .border(BorderWidth, NeutralGray100, CircleShape)
            ) {
                image(suggestion)
            }
        }
        Text(
            text = suggestion.label,
            style = textStyle,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

private data class SearchInputColors(
    val container: Color,
    val border: Color?,
    val text: Color,
    val icon: Color
)

/** Figma "Input/ search": Color × Status (default, focused; open uses the focused field). */
private fun searchInputColors(color: ToteatSearchInputColor, enabled: Boolean, active: Boolean): SearchInputColors {
    val container = when (color) {
        ToteatSearchInputColor.Gray -> NeutralGray100
        ToteatSearchInputColor.White -> NeutralGray
        ToteatSearchInputColor.Dark -> NeutralGray500
    }
    // Figma has no disabled variant: like ToteatSearchBar, the background fades and text and
    // icon turn neutral400.
    if (!enabled) {
        return SearchInputColors(container.copy(alpha = DisabledContainerAlpha), null, NeutralGray400, NeutralGray400)
    }
    return when (color) {
        ToteatSearchInputColor.Gray -> SearchInputColors(
            container = container,
            border = if (active) NeutralGray300 else null,
            text = SecondaryNormal,
            icon = if (active) NeutralGray400 else NeutralGray300
        )
        ToteatSearchInputColor.White -> SearchInputColors(
            container = container,
            border = if (active) NeutralGray100 else null,
            text = SecondaryNormal,
            icon = if (active) NeutralGray400 else NeutralGray300
        )
        ToteatSearchInputColor.Dark -> SearchInputColors(
            container = container,
            border = null,
            text = NeutralGray200,
            icon = if (active) NeutralGray else NeutralGray400
        )
    }
}

/** Figma Body-base/B14 R: Manrope Regular 14, line height 1.2. */
@Composable
private fun searchInputTextStyle(): TextStyle {
    val base = MaterialTheme.typography.bodyLargeRegular
    return remember(base) { base.copy(lineHeight = 16.8.sp) }
}

private val PreviewSuggestions = persistentListOf(
    ToteatSearchSuggestion("1", "Donut arcoiris"),
    ToteatSearchSuggestion("2", "Donut chocolate"),
    ToteatSearchSuggestion("3", "Donut manjar")
)

@Composable
private fun PreviewSearchInput(
    color: ToteatSearchInputColor,
    value: String = "",
    focused: Boolean = false,
    open: Boolean = false
) {
    SearchInputImpl(
        value = value,
        onValueChange = {},
        modifier = Modifier,
        placeholder = "Buscar producto",
        enabled = true,
        iconTint = null,
        onSearch = {},
        testTag = "",
        color = color,
        suggestions = if (open) PreviewSuggestions else persistentListOf(),
        onSuggestionClick = {},
        suggestionImage = { Box(Modifier.size(SuggestionImageSize).background(NeutralGray300)) },
        previewFocused = focused,
        previewHighlightedIndex = if (open) 0 else null
    )
}

/** Las 9 variantes de Figma: por color, default, focused y open (primera fila resaltada). */
@Preview(showBackground = true, backgroundColor = 0xFFDDDDDD, widthDp = 380)
@Composable
private fun ToteatSearchInputVariantsPreview() {
    ToteatTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ToteatSearchInputColor.entries.forEach { color ->
                PreviewSearchInput(color)
                PreviewSearchInput(color, value = "Donut", focused = true)
                PreviewSearchInput(color, value = "Donut", open = true)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 380)
@Composable
private fun ToteatSearchInputDisabledPreview() {
    ToteatTheme {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ToteatSearchInputColor.entries.forEach { color ->
                ToteatSearchInput(
                    value = "",
                    onValueChange = {},
                    placeholder = "Buscar producto",
                    enabled = false,
                    color = color
                )
            }
        }
    }
}

/** Interactivo: escribir muestra resultados; al tocar uno se completa el campo y se cierran. */
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 380)
@Composable
private fun ToteatSearchInputInteractivePreview() {
    ToteatTheme {
        var value by remember { mutableStateOf("") }
        var picked by remember { mutableStateOf<String?>(null) }
        val results = remember(value, picked) {
            if (value.isBlank() || value == picked) persistentListOf()
            else persistentListOf(*PreviewSuggestions.filter { it.label.contains(value, ignoreCase = true) }.toTypedArray())
        }
        ToteatSearchInput(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.padding(16.dp),
            placeholder = "Buscar producto",
            suggestions = results,
            onSuggestionClick = {
                picked = it.label
                value = it.label
            }
        )
    }
}
