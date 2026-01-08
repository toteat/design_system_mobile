package com.toteat.toteatds.components.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_order_list_ticket
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OrderListTicketIcon(
    modifier: Modifier = Modifier,
    size: Dp = 18.dp,
    tint: Color = NeutralGray,
    contentDescription: String? = null
) {
    val defaultContentDescription = stringResource(Res.string.icon_order_list_ticket)
    
    Icon(
        imageVector = vectorResource(Res.drawable.icon_order_list_ticket),
        contentDescription = contentDescription ?: defaultContentDescription,
        modifier = modifier.size(size),
        tint = tint
    )
}

@Composable
@Preview
private fun OrderListTicketIconPreview() {
    ToteatTheme {
        OrderListTicketIcon()
    }
}

@Composable
@Preview
private fun OrderListTicketIconPrimaryTintPreview() {
    ToteatTheme {
        OrderListTicketIcon(tint = MaterialTheme.colorScheme.primary)
    }
}
