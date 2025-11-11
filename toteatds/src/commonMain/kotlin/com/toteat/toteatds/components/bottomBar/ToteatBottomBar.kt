package com.toteat.toteatds.components.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
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
    object AllTables : ToteatBottomBarButtonType(
        Res.drawable.icon_room, "Todas las mesas"
    )
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
   Box(
        modifier = modifier
            .fillMaxWidth()
            .dropShadow(
                shape = RoundedCornerShape(3.dp),
                shadow = Shadow(
                    radius = 6.dp,
                    color = Color(0x40000000),
                    offset = DpOffset(x = 0.dp, -1.dp)
                ))
            .background(MaterialTheme.colorScheme.background)

        )


    {
        Row(
            modifier = Modifier
                .fillMaxWidth().height(64.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarItem(
                type = ToteatBottomBarButtonType.Tables,
                isSelected = selectedType == ToteatBottomBarButtonType.Tables,
                onClick = onMyTablesClick
            )
            BottomBarItem(
                type = ToteatBottomBarButtonType.AllTables,
                isSelected = selectedType == ToteatBottomBarButtonType.AllTables,
                onClick = onAllTablesClick
            )
            BottomBarItem(
                type = ToteatBottomBarButtonType.ViewMore,
                isSelected = selectedType == ToteatBottomBarButtonType.ViewMore,
                onClick = onMoreClick
            )
        }
    }
}

@Composable
fun BottomBarItem(
    type: ToteatBottomBarButtonType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.secondary
    }

    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = vectorResource(type.iconRes),
            contentDescription = type.text,
            modifier = Modifier.size(20.dp, 20.dp),
            tint = contentColor
        )
        type.text?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ToteatBottomBarPreview() {
    ToteatTheme {
        var selectedItem by remember {
            mutableStateOf<ToteatBottomBarButtonType>(ToteatBottomBarButtonType.AllTables)
        }

        ToteatBottomBar(
            selectedType = selectedItem,
            onMyTablesClick = { selectedItem = ToteatBottomBarButtonType.Tables },
            onAllTablesClick = { selectedItem = ToteatBottomBarButtonType.AllTables },
            onMoreClick = { selectedItem = ToteatBottomBarButtonType.ViewMore }
        )
    }
}