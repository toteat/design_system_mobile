package com.toteat.toteatds.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray100
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.PrimaryNormal
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.theme.bodyMediumRegular
import com.toteat.toteatds.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

private val HorizontalPadding = 12.dp
private val PriceColumnWidth = 75.dp
private val HourColumnWidth = 75.dp
private val RowHeight = 55.dp

data class OrderItemExtra(
    val name: String,
    val description: String,
    val price: String
)

data class OrderItem(
    val name: String,
    val quantity: Int = 1,
    val unitPrice: String? = null,
    val totalPrice: String,
    val time: String,
    val extras: List<OrderItemExtra> = emptyList()
)

@Composable
fun GroupedOrderDetail(
    items: List<OrderItem>,
    modifier: Modifier = Modifier,
    maxCollapsedItems: Int = 4,
    itemModifier: (OrderItem) -> Modifier = { Modifier },
    verMasModifier: Modifier = Modifier
) {
    var showAll by remember { mutableStateOf(false) }
    val hasMore = items.size > maxCollapsedItems
    val visible = if (showAll || !hasMore) items else items.take(maxCollapsedItems)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(NeutralGray, RoundedCornerShape(14.dp))
            .border(1.dp, NeutralGray200, RoundedCornerShape(14.dp))
    ) {
        OrderDetailHeader()

        visible.forEach { item ->
            OrderDetailItem(
                item = item,
                rowModifier = itemModifier(item)
            )
        }

        if (hasMore) {
            VerMasRow(
                expanded = showAll,
                onClick = { showAll = !showAll },
                modifier = verMasModifier
            )
        }
    }
}

@Composable
private fun OrderDetailHeader() {
    val extended = MaterialTheme.colorScheme.extended
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(NeutralGray100, RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
            .padding(start = HorizontalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Consumo", style = MaterialTheme.typography.bodyLargeRegular, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = extended.neutral500)
            Text("Productos", style = MaterialTheme.typography.bodyMediumRegular, fontSize = 11.sp, color = extended.neutral500)
        }

        Box(Modifier.width(1.dp).fillMaxHeight().background(NeutralGray200))

        Column(Modifier.width(PriceColumnWidth), horizontalAlignment = Alignment.Start) {
            Text("Precio", style = MaterialTheme.typography.bodyLargeRegular, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = extended.neutral500, modifier = Modifier.padding(start = 12.dp))
            Text("total", style = MaterialTheme.typography.bodyMediumRegular, fontSize = 11.sp, color = extended.neutral500, modifier = Modifier.padding(start = 12.dp))
        }

        Box(Modifier.width(1.dp).fillMaxHeight().background(NeutralGray200))

        Column(Modifier.width(HourColumnWidth), horizontalAlignment = Alignment.Start) {
            Text("Hora", style = MaterialTheme.typography.bodyLargeRegular, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = extended.neutral500, modifier = Modifier.padding(start = 12.dp))
            Text("ingreso", style = MaterialTheme.typography.bodyMediumRegular, fontSize = 11.sp, color = extended.neutral500, modifier = Modifier.padding(start = 12.dp))
        }
    }
}

@Composable
private fun OrderDetailItem(item: OrderItem, rowModifier: Modifier = Modifier) {
    val extended = MaterialTheme.colorScheme.extended
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(NeutralGray)
            .clickable(enabled = item.extras.isNotEmpty()) { if (item.extras.isNotEmpty()) isExpanded = !isExpanded }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(RowHeight)
                .padding(start = HorizontalPadding)
                .then(rowModifier),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f).padding(vertical = 4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(item.name, style = MaterialTheme.typography.bodyLargeRegular, fontSize = 13.sp, color = extended.neutral500)
                        if (item.quantity > 1) {
                            Spacer(Modifier.width(4.dp))
                            Text("x${item.quantity}", style = MaterialTheme.typography.bodyLargeRegular, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = PrimaryNormal)
                        }
                    }
                    item.unitPrice?.let {
                        Spacer(Modifier.height(2.dp))
                        Text("Precio unitario: $it", style = MaterialTheme.typography.bodyMediumRegular, fontSize = 11.sp, color = extended.neutral400)
                    }
                }

                if (item.extras.isNotEmpty()) {
                    Spacer(Modifier.width(6.dp))
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = PrimaryNormal,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Box(Modifier.width(1.dp).fillMaxHeight().background(NeutralGray200))

            Box(Modifier.width(PriceColumnWidth).fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                Text(item.totalPrice, style = MaterialTheme.typography.bodyLargeRegular, fontSize = 13.sp, color = extended.neutral500, modifier = Modifier.padding(horizontal = 8.dp))
            }

            Box(Modifier.width(1.dp).fillMaxHeight().background(NeutralGray200))

            Box(Modifier.width(HourColumnWidth).fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                Text(item.time, style = MaterialTheme.typography.bodyMediumRegular, fontSize = 12.sp, color = extended.neutral400, modifier = Modifier.padding(horizontal = 8.dp))
            }
        }

        if (isExpanded && item.extras.isNotEmpty()) {
            item.extras.forEach { ExtraItemRow(extra = it) }
        }
    }
}

@Composable
private fun ExtraItemRow(extra: OrderItemExtra) {
    val extended = MaterialTheme.colorScheme.extended
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = HorizontalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Text("↳ ", style = MaterialTheme.typography.bodyMediumRegular, fontSize = 12.sp, color = PrimaryNormal)
            Text("${extra.name} ", style = MaterialTheme.typography.bodyMediumRegular, fontSize = 12.sp, color = PrimaryNormal)
            Text(extra.description, style = MaterialTheme.typography.bodyMediumRegular, fontSize = 12.sp, color = extended.neutral400)
        }

        Box(Modifier.width(1.dp).height(20.dp).background(NeutralGray200))

        Box(Modifier.width(PriceColumnWidth).height(20.dp), contentAlignment = Alignment.CenterStart) {
            Text(extra.price, style = MaterialTheme.typography.bodyMediumRegular, fontSize = 12.sp, color = extended.neutral500, modifier = Modifier.padding(horizontal = 8.dp))
        }

        Box(Modifier.width(1.dp).height(20.dp).background(NeutralGray200))

        Box(Modifier.width(HourColumnWidth).height(20.dp))
    }
}

@Composable
private fun VerMasRow(expanded: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val extended = MaterialTheme.colorScheme.extended
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .background(NeutralGray100, RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp))
            .clickable { onClick() }
            .padding(horizontal = 15.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(if (expanded) "Ver menos" else "Ver más", style = MaterialTheme.typography.bodyMediumRegular, fontSize = 12.sp, color = extended.neutral400)
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = extended.neutral400,
            modifier = Modifier.size(24.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GroupedOrderDetailPreview() {
    ToteatTheme {
        val items = listOf(
            OrderItem(
                name = "Copa de vino",
                quantity = 2,
                unitPrice = "$4.000",
                totalPrice = "$8.000",
                time = "21:12 hrs"
            ),
            OrderItem(
                name = "Mocktail mojito",
                quantity = 1,
                unitPrice = "$6.000",
                totalPrice = "$6.000",
                time = "21:12 hrs"
            ),
            OrderItem(
                name = "Lasagna vegetariana",
                quantity = 1,
                unitPrice = "$7.000",
                totalPrice = "$7.000",
                time = "21:12 hrs"
            ),
            OrderItem(
                name = "Bowl mediterraneo",
                quantity = 1,
                unitPrice = "$6.000",
                totalPrice = "$6.000",
                time = "21:12 hrs"
            ),
            OrderItem(
                name = "Risotto funghi",
                quantity = 2,
                unitPrice = "$8.000",
                totalPrice = "$24.000",
                time = "21:12 hrs",
                extras = listOf(
                    OrderItemExtra(
                        name = "Extra plato 1:",
                        description = "Queso",
                        price = "$2.000"
                    ),
                    OrderItemExtra(
                        name = "Extra plato 2:",
                        description = "Filete",
                        price = "$6.000"
                    )
                )
            ),
            OrderItem(
                name = "Bowl mediterraneo",
                quantity = 1,
                unitPrice = "$6.000",
                totalPrice = "$6.000",
                time = "21:12 hrs"
            )
        )

        GroupedOrderDetail(
            items = items,

        )
    }
}

