package com.toteat.toteatds.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.list.ToteatDetailAccordion
import com.toteat.toteatds.components.list.ToteatDetailRow
import com.toteat.toteatds.components.list.ToteatDetailStatus
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray100
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

private val DetailCardShape = RoundedCornerShape(12.dp)

/**
 * Tarjeta blanca con borde y sombra suave que agrupa acordeones de detalle
 * ([ToteatDetailAccordion]). Separa cada acordeón con [ToteatDetailDivider].
 */
@Composable
fun ToteatDetailCard(
    modifier: Modifier = Modifier,
    testTag: String = "",
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = DetailCardShape)
            .clip(DetailCardShape)
            .background(NeutralGray)
            .border(width = 1.dp, color = NeutralGray100, shape = DetailCardShape)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        content = content
    )
}

/** Línea divisoria entre los acordeones de una [ToteatDetailCard]. */
@Composable
fun ToteatDetailDivider(
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    HorizontalDivider(
        modifier = modifier.then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        color = NeutralGray100
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFAFAFA)
private fun ToteatDetailCardPreview() {
    var expanded by remember { mutableStateOf(true) }
    ToteatTheme {
        ToteatDetailCard(modifier = Modifier.padding(16.dp)) {
            ToteatDetailAccordion(
                title = "Detalle de pago 01",
                status = ToteatDetailStatus.Success,
                rows = persistentListOf(
                    ToteatDetailRow(label = "• Monto pagado", value = "$ 3.935"),
                    ToteatDetailRow(label = "• Propina", value = "10%")
                ),
                expanded = expanded,
                onToggle = { expanded = !expanded }
            )
            ToteatDetailDivider()
            ToteatDetailAccordion(
                title = "Detalle de pago 02",
                status = ToteatDetailStatus.Pending,
                rows = persistentListOf(),
                expanded = false,
                onToggle = {}
            )
        }
    }
}
