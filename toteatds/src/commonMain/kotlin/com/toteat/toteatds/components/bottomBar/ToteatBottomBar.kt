package com.toteat.toteatds.components.bottomBar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_room
import designsystemmobile.toteatds.generated.resources.icon_stroke_minus_plus
import designsystemmobile.toteatds.generated.resources.icon_table
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed class ToteatBottomBarButtonType(
    val iconRes: DrawableResource,
    val text: String? = null
) {
    object Tables : ToteatBottomBarButtonType(Res.drawable.icon_table, "Mis mesas")
    object AllTables : ToteatBottomBarButtonType(Res.drawable.icon_room, "Todas las mesas")
    object ViewMore : ToteatBottomBarButtonType(Res.drawable.icon_stroke_minus_plus, "Ver más")
}

@Composable
fun ToteatBottomBar(
    selectedType: ToteatBottomBarButtonType,
    onMyTablesClick: () -> Unit,
    onAllTablesClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.semantics {
            contentDescription = "Barra de navegación principal"
        },
        containerColor = NeutralGray,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 3.dp,
        windowInsets = WindowInsets(0.dp)
    ) {
        NavigationBarItem(
            selected = selectedType == ToteatBottomBarButtonType.Tables,
            onClick = onMyTablesClick,
            icon = {
                Icon(
                    imageVector = vectorResource(ToteatBottomBarButtonType.Tables.iconRes),
                    contentDescription = null
                )
            },
            label = {
                ToteatBottomBarButtonType.Tables.text?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                indicatorColor = Color.Transparent
            ),
            modifier = Modifier.semantics {
                contentDescription = "${ToteatBottomBarButtonType.Tables.text}, ${
                    if (selectedType == ToteatBottomBarButtonType.Tables) "seleccionado" else "no seleccionado"
                }"
            }
        )

        NavigationBarItem(
            selected = selectedType == ToteatBottomBarButtonType.AllTables,
            onClick = onAllTablesClick,
            icon = {
                Icon(
                    imageVector = vectorResource(ToteatBottomBarButtonType.AllTables.iconRes),
                    contentDescription = null
                )
            },
            label = {
                ToteatBottomBarButtonType.AllTables.text?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                indicatorColor = Color.Transparent
            ),
            modifier = Modifier.semantics {
                contentDescription = "${ToteatBottomBarButtonType.AllTables.text}, ${
                    if (selectedType == ToteatBottomBarButtonType.AllTables) "seleccionado" else "no seleccionado"
                }"
            }
        )

        NavigationBarItem(
            selected = selectedType == ToteatBottomBarButtonType.ViewMore,
            onClick = onMoreClick,
            icon = {
                Icon(
                    imageVector = vectorResource(ToteatBottomBarButtonType.ViewMore.iconRes),
                    contentDescription = null
                )
            },
            label = {
                ToteatBottomBarButtonType.ViewMore.text?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                indicatorColor = Color.Transparent
            ),
            modifier = Modifier.semantics {
                contentDescription = "${ToteatBottomBarButtonType.ViewMore.text}, ${
                    if (selectedType == ToteatBottomBarButtonType.ViewMore) "seleccionado" else "no seleccionado"
                }"
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ToteatBottomBarPreview() {
    ToteatTheme {
        val selectedState = androidx.compose.runtime.remember {
            androidx.compose.runtime.mutableStateOf<ToteatBottomBarButtonType>(ToteatBottomBarButtonType.Tables)
        }

        ToteatBottomBar(
            selectedType = selectedState.value,
            onMyTablesClick = { selectedState.value = ToteatBottomBarButtonType.Tables },
            onAllTablesClick = { selectedState.value = ToteatBottomBarButtonType.AllTables },
            onMoreClick = { selectedState.value = ToteatBottomBarButtonType.ViewMore }
        )
    }
}
