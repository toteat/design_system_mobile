package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.theme.helperBold
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ButtonTableStatus {
    AVAILABLE, OCCUPIED
}

@Composable
fun ToteatButtonTable(
    tableName: String,
    waiterName: String,
    occupationTime: String,
    tableStatus: ButtonTableStatus,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isOccupied = tableStatus == ButtonTableStatus.OCCUPIED
    val containerColor = if (isOccupied) {
        MaterialTheme.colorScheme.extended.neutral500
    } else {
        MaterialTheme.colorScheme.extended.neutral100
    }

    val contentColor: Color = if (isOccupied) NeutralGray else NeutralGray400

    Card(
        modifier = modifier.size(width = 106.dp, height = 80.dp),
        onClick = onClick,
        enabled = isOccupied,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = tableName,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                maxLines = 1,
                color = contentColor,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            ToteatButtonItemRow(
                icon = Icons.Default.AccountCircle,
                title = waiterName,
                tint = contentColor
            )
            ToteatButtonItemRow(
                icon = Icons.Default.Schedule,
                title = occupationTime,
                tint = contentColor
            )
        }
    }
}

@Composable
private fun ToteatButtonItemRow(
    icon: ImageVector,
    title: String,
    tint: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = title,
            color = tint,
            style = MaterialTheme.typography.helperBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
@Preview
fun ToteatButtonTablePreview() {
    ToteatTheme {
        Column {
            ToteatButtonTable(
                tableName = "Mesa S7",
                waiterName = "Jaime",
                occupationTime = "16:02 hrs",
                tableStatus = ButtonTableStatus.OCCUPIED,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            ToteatButtonTable(
                tableName = "Mesa S10",
                waiterName = "Disponible",
                occupationTime = "-",
                tableStatus = ButtonTableStatus.AVAILABLE,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(16.dp))
            ToteatButtonTable(
                tableName = "Mesa S10",
                waiterName = "No Disponible",
                occupationTime = "-",
                tableStatus = ButtonTableStatus.AVAILABLE,
                onClick = {}
            )
        }
    }
}
