package com.toteat.toteatds.components.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.different_payments_icon
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DifferentAmountPaymentsIcon(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    Image(
        modifier = modifier.size(size),
        imageVector = vectorResource(Res.drawable.different_payments_icon),
        contentDescription = null
    )
}

@Composable
@Preview
fun DifferentAmountPaymentsIconPreview() {
    ToteatTheme {
        DifferentAmountPaymentsIcon()
    }
}