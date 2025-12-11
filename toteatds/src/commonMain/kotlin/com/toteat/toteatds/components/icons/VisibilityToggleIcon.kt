package com.toteat.toteatds.components.icons

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_eye_close
import designsystemmobile.toteatds.generated.resources.icon_eye_open
import org.jetbrains.compose.resources.vectorResource

@Composable
fun VisibilityToggleIcon(
    isVisible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    val icon = if (isVisible) {
        vectorResource(Res.drawable.icon_eye_open)
    } else {
        vectorResource(Res.drawable.icon_eye_close)
    }
    
    val contentDescription = if (isVisible) {
        "Ocultar contraseña"
    } else {
        "Mostrar contraseña"
    }

    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}