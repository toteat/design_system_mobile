package com.toteat.toteatds.components.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.different_payments_icon
import designsystemmobile.toteatds.generated.resources.icon_different_payment
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DifferentAmountPaymentsIcon(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    val contentDescription = stringResource(Res.string.icon_different_payment)
    
    Icon(
        imageVector = vectorResource(Res.drawable.different_payments_icon),
        contentDescription = contentDescription,
        modifier = modifier.size(size)
    )
}

@Composable
@Preview
private fun DifferentAmountPaymentsIconPreview() {
    ToteatTheme {
        DifferentAmountPaymentsIcon()
    }
}