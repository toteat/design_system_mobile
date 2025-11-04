package com.toteat.toteatds.components.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
    object Tables : ToteatBottomBarButtonType( Res.drawable.icon_table, "Mis mesas")
    object AllTables : ToteatBottomBarButtonType(
        Res.drawable.icon_room,"Todas las mesas")
    object ViewMore : ToteatBottomBarButtonType( Res.drawable.icon_stroke_minus_plus,"Ver más")
}
@Composable
fun ToteatBottomBar(
    onMyTablesClick: () -> Unit,
    onAllTablesClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarItem(
                type = ToteatBottomBarButtonType.Tables,
                onClick = onMyTablesClick
            )
            BottomBarItem(
                type = ToteatBottomBarButtonType.AllTables,
                onClick = onAllTablesClick
            )
            BottomBarItem(
                type = ToteatBottomBarButtonType.ViewMore,
                onClick = onMoreClick
            )
        }
    }
}

@Composable
fun BottomBarItem(


    type: ToteatBottomBarButtonType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = vectorResource(type.iconRes), contentDescription = type.text, Modifier.size(20.dp,20.dp))
        type.text?.let { Text(text = it,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black) }
    }
}

@Preview
@Composable
fun ToteatBottomBarPreview() {
    ToteatTheme {
        ToteatBottomBar({}, {}, {})
    }
}