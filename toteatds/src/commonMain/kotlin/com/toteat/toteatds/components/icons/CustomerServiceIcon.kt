package com.toteat.toteatds.components.icons

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.customer_service_icon
import designsystemmobile.toteatds.generated.resources.icon_customer_service
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun CustomerServiceIcon(
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentDescription = stringResource(Res.string.icon_customer_service)
    
    IconButton(
        onClick = onIconClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.customer_service_icon),
            contentDescription = contentDescription
        )
    }
}