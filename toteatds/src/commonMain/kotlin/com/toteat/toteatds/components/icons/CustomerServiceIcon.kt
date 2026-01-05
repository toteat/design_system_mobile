package com.toteat.toteatds.components.icons
import com.toteat.toteatds.utils.setTestTag

import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.customer_service_icon
import org.jetbrains.compose.resources.vectorResource

@Composable
fun CustomerServiceIcon(
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit
) {
    IconButton(onClick = { onIconClick() }) {
        Image(
            modifier = modifier,
            imageVector = vectorResource(Res.drawable.customer_service_icon),
            contentDescription = "Servicio al cliente"
        )
    }
}