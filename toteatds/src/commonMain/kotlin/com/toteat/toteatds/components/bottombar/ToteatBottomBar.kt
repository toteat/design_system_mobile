package com.toteat.toteatds.components.bottombar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.bottom_bar_all_tables
import designsystemmobile.toteatds.generated.resources.bottom_bar_main_description
import designsystemmobile.toteatds.generated.resources.bottom_bar_my_tables
import designsystemmobile.toteatds.generated.resources.bottom_bar_view_more
import designsystemmobile.toteatds.generated.resources.chip_not_selected
import designsystemmobile.toteatds.generated.resources.chip_selected
import designsystemmobile.toteatds.generated.resources.icon_room
import designsystemmobile.toteatds.generated.resources.icon_stroke_minus_plus
import designsystemmobile.toteatds.generated.resources.icon_table
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed class ToteatBottomBarButtonType(
    val iconRes: DrawableResource,
    val textRes: StringResource
) {
    object Tables :
        ToteatBottomBarButtonType(Res.drawable.icon_table, Res.string.bottom_bar_my_tables)

    object AllTables :
        ToteatBottomBarButtonType(Res.drawable.icon_room, Res.string.bottom_bar_all_tables)

    object ViewMore : ToteatBottomBarButtonType(
        Res.drawable.icon_stroke_minus_plus,
        Res.string.bottom_bar_view_more
    )
}

@Composable
fun ToteatBottomBar(
    selectedType: ToteatBottomBarButtonType,
    onMyTablesClick: () -> Unit,
    onAllTablesClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    // Cache string resources (these don't change)
    val tablesText = stringResource(ToteatBottomBarButtonType.Tables.textRes)
    val allTablesText = stringResource(ToteatBottomBarButtonType.AllTables.textRes)
    val viewMoreText = stringResource(ToteatBottomBarButtonType.ViewMore.textRes)
    val selectedText = stringResource(Res.string.chip_selected)
    val notSelectedText = stringResource(Res.string.chip_not_selected)
    val mainDescription = stringResource(Res.string.bottom_bar_main_description)

    // Cache icons to avoid repeated vectorResource calls
    val tablesIcon = vectorResource(ToteatBottomBarButtonType.Tables.iconRes)
    val allTablesIcon = vectorResource(ToteatBottomBarButtonType.AllTables.iconRes)
    val viewMoreIcon = vectorResource(ToteatBottomBarButtonType.ViewMore.iconRes)

    // Get color scheme (already cached by MaterialTheme)
    val colorScheme = MaterialTheme.colorScheme

    // Create NavigationBarItemColors (called in @Composable context)
    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = colorScheme.primary,
        selectedTextColor = colorScheme.primary,
        unselectedIconColor = colorScheme.onSurfaceVariant,
        unselectedTextColor = colorScheme.onSurfaceVariant,
        indicatorColor = Color.Transparent
    )

    // Cache accessibility descriptions to avoid string concatenation on every recomposition
    val tablesDescription = remember(tablesText, selectedType, selectedText, notSelectedText) {
        "$tablesText, ${if (selectedType == ToteatBottomBarButtonType.Tables) selectedText else notSelectedText}"
    }

    val allTablesDescription = remember(allTablesText, selectedType, selectedText, notSelectedText) {
        "$allTablesText, ${if (selectedType == ToteatBottomBarButtonType.AllTables) selectedText else notSelectedText}"
    }

    val viewMoreDescription = remember(viewMoreText, selectedType, selectedText, notSelectedText) {
        "$viewMoreText, ${if (selectedType == ToteatBottomBarButtonType.ViewMore) selectedText else notSelectedText}"
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        color = Color.White,
        tonalElevation = 0.dp
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = mainDescription
                },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tonalElevation = 0.dp,
            windowInsets = WindowInsets(0.dp)
        ) {
            NavigationBarItem(
                selected = selectedType == ToteatBottomBarButtonType.Tables,
                onClick = onMyTablesClick,
                icon = {
                    Icon(
                        imageVector = tablesIcon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = tablesText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = itemColors,
                modifier = Modifier.semantics {
                    contentDescription = tablesDescription
                }
            )

            NavigationBarItem(
                selected = selectedType == ToteatBottomBarButtonType.AllTables,
                onClick = onAllTablesClick,
                icon = {
                    Icon(
                        imageVector = allTablesIcon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = allTablesText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = itemColors,
                modifier = Modifier.semantics {
                    contentDescription = allTablesDescription
                }
            )

            NavigationBarItem(
                selected = selectedType == ToteatBottomBarButtonType.ViewMore,
                onClick = onMoreClick,
                icon = {
                    Icon(
                        imageVector = viewMoreIcon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = viewMoreText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = itemColors,
                modifier = Modifier.semantics {
                    contentDescription = viewMoreDescription
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ToteatBottomBarPreview() {
    ToteatTheme {
        val selectedState = androidx.compose.runtime.remember {
            androidx.compose.runtime.mutableStateOf<ToteatBottomBarButtonType>(
                ToteatBottomBarButtonType.Tables
            )
        }

        ToteatBottomBar(
            selectedType = selectedState.value,
            onMyTablesClick = { selectedState.value = ToteatBottomBarButtonType.Tables },
            onAllTablesClick = { selectedState.value = ToteatBottomBarButtonType.AllTables },
            onMoreClick = { selectedState.value = ToteatBottomBarButtonType.ViewMore }
        )
    }
}
