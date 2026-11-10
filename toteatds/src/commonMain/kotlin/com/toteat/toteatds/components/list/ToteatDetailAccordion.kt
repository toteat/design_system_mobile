package com.toteat.toteatds.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.icons.ChevronDownIcon
import com.toteat.toteatds.components.icons.SuccessIcon
import com.toteat.toteatds.components.icons.WarningIcon
import com.toteat.toteatds.theme.GreenNormal
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyMediumRegular
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.detail_accordion_collapsed
import designsystemmobile.toteatds.generated.resources.detail_accordion_expanded
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/** Estado de un [ToteatDetailAccordion]. */
enum class ToteatDetailStatus {
    /** Completado: se muestra activo, con check verde, y se puede expandir. */
    Success,

    /** Pendiente: se muestra en gris, con ícono de advertencia, y no se expande. */
    Pending
}

/**
 * Fila de detalle de un [ToteatDetailAccordion]. Los textos llegan ya formateados; si se
 * quiere viñeta, se incluye en [label] (por ejemplo, "• Propina").
 */
@Immutable
data class ToteatDetailRow(
    val label: String,
    val value: String
)

/**
 * Acordeón de detalle: un encabezado con título, ícono de estado y chevron, y debajo las
 * filas de detalle cuando está expandido. Solo un acordeón [ToteatDetailStatus.Success]
 * responde al toque y muestra sus filas. El chevron apunta siempre hacia abajo.
 *
 * Test tags derivados: `<testTag>_header` y `<testTag>_row_<i>`.
 */
@Composable
fun ToteatDetailAccordion(
    title: String,
    status: ToteatDetailStatus,
    rows: ImmutableList<ToteatDetailRow>,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    testTag: String = "",
    onToggle: () -> Unit
) {
    val isSuccess = status == ToteatDetailStatus.Success
    val stateLabel = stringResource(
        if (expanded) Res.string.detail_accordion_expanded else Res.string.detail_accordion_collapsed
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 42.dp)
                .clickable(enabled = isSuccess, onClick = onToggle)
                .then(if (isSuccess) Modifier.semantics { stateDescription = stateLabel } else Modifier)
                .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_header") else Modifier)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSuccess) NeutralGray500 else NeutralGray400
            )
            if (isSuccess) {
                SuccessIcon(size = 16.dp, tint = GreenNormal)
            } else {
                WarningIcon(size = 16.dp, tint = NeutralGray300)
            }
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                ChevronDownIcon(size = 16.dp, tint = if (isSuccess) SecondaryNormal else NeutralGray200)
            }
        }
        if (expanded && isSuccess) {
            rows.forEachIndexed { index, row ->
                DetailRow(
                    row = row,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_row_$index" else ""
                )
            }
        }
    }
}

@Composable
private fun DetailRow(row: ToteatDetailRow, testTag: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = row.label,
            style = MaterialTheme.typography.bodyMediumRegular,
            color = NeutralGray500,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = row.value,
            style = MaterialTheme.typography.bodyMediumRegular,
            color = NeutralGray500
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun ToteatDetailAccordionPreview() {
    ToteatTheme {
        Column {
            ToteatDetailAccordion(
                title = "Detalle de pago 01",
                status = ToteatDetailStatus.Success,
                rows = persistentListOf(
                    ToteatDetailRow(label = "• Monto pagado", value = "$ 3.935"),
                    ToteatDetailRow(label = "• Propina", value = "10%")
                ),
                expanded = true,
                onToggle = {}
            )
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
