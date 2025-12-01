package com.toteat.toteatds.components.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.customer_service_icon
import designsystemmobile.toteatds.generated.resources.print_vector
import designsystemmobile.toteatds.generated.resources.split_payment_icon
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PrintIconButton(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    Image(
        modifier = modifier.size(size),
        imageVector = vectorResource(Res.drawable.print_vector),
        contentDescription = null
    )
}

@Composable
@Preview
fun PrintIconButtonPreview() {
    ToteatTheme {
        PrintIconButton(

        )
    }
}