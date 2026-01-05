package com.toteat.toteatds.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.toteat.toteatds.theme.extended
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_status_error
import designsystemmobile.toteatds.generated.resources.icon_status_success
import designsystemmobile.toteatds.generated.resources.icon_status_warning
import org.jetbrains.compose.resources.stringResource

@Composable
fun StatusTrailingIcon(
    isSuccess: Boolean,
    isError: Boolean,
    isWarning: Boolean,
    modifier: Modifier = Modifier
) {
    val successText = stringResource(Res.string.icon_status_success)
    val errorText = stringResource(Res.string.icon_status_error)
    val warningText = stringResource(Res.string.icon_status_warning)
    
    val (icon, tint, description) = when {
        isSuccess -> Triple(
            Icons.Filled.CheckCircle,
            MaterialTheme.colorScheme.extended.isSuccess,
            successText
        )
        isError -> Triple(
            Icons.Filled.Close,
            MaterialTheme.colorScheme.error,
            errorText
        )
        isWarning -> Triple(
            Icons.Filled.Warning,
            MaterialTheme.colorScheme.extended.isWarning,
            warningText
        )
        else -> Triple(null, null, null)
    }

    if (icon != null && tint != null && description != null) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = tint,
            modifier = modifier
        )
    }
}