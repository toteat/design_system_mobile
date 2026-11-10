package com.toteat.toteatds.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.TertiaryLight
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyMediumRegular
import com.toteat.toteatds.utils.setTestTag
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

private val TotalsCardShape = RoundedCornerShape(8.dp)

/**
 * Fila de una [ToteatTotalsCard]. Los textos llegan ya formateados. Con [emphasized] la
 * fila se muestra más grande y oscura, por ejemplo para el monto pendiente.
 */
@Immutable
data class ToteatTotalsRow(
    val label: String,
    val value: String,
    val emphasized: Boolean = false
)

/**
 * Tarjeta color crema con filas de totales: etiqueta a la izquierda y monto a la derecha.
 *
 * Test tag derivado: `<testTag>_row_<i>`.
 */
@Composable
fun ToteatTotalsCard(
    rows: ImmutableList<ToteatTotalsRow>,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 1.dp, shape = TotalsCardShape)
            .background(color = TertiaryLight, shape = TotalsCardShape)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        rows.forEachIndexed { index, row ->
            TotalsRow(row = row, testTag = if (testTag.isNotEmpty()) "${testTag}_row_$index" else "")
        }
    }
}

@Composable
private fun TotalsRow(row: ToteatTotalsRow, testTag: String) {
    val style = if (row.emphasized) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMediumRegular
    val color = if (row.emphasized) SecondaryNormal else NeutralGray500
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = row.label, style = style, color = color, modifier = Modifier.weight(1f))
        Text(text = row.value, style = style, color = color, textAlign = TextAlign.End)
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFAFAFA)
private fun ToteatTotalsCardPreview() {
    ToteatTheme {
        ToteatTotalsCard(
            rows = persistentListOf(
                ToteatTotalsRow(label = "Subtotal:", value = "$ 31.400"),
                ToteatTotalsRow(label = "Descuento:", value = "$ 1.000"),
                ToteatTotalsRow(label = "Pagado:", value = "$ 15.700"),
                ToteatTotalsRow(label = "Pendiente a pagar:", value = "$ 14.700", emphasized = true)
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
