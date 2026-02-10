package com.toteat.toteatds.components.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_split_payment
import designsystemmobile.toteatds.generated.resources.split_payment_icon
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SplitPaymentIcon(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    testTag: String = ""
) {
    val contentDescription = stringResource(Res.string.icon_split_payment)
    
    Icon(
        imageVector = vectorResource(Res.drawable.split_payment_icon),
        contentDescription = contentDescription,
        modifier = modifier
            .size(size)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    )
}

@Composable
@Preview
private fun DividePaymentIconIconPreview() {
    ToteatTheme {
        SplitPaymentIcon()
    }
}