package com.toteat.toteatds.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.toteat.toteatds.theme.extended

@Composable
fun StatusTrailingIcon(
    isSuccess: Boolean,
    isError: Boolean,
    isWarning: Boolean
) {
    val (icon, tint) = when {
        isSuccess -> Icons.Filled.CheckCircle to MaterialTheme.colorScheme.extended.isSuccess
        isError -> Icons.Filled.Close to MaterialTheme.colorScheme.error
        isWarning -> Icons.Filled.Warning to MaterialTheme.colorScheme.extended.isWarning
        else -> null to null
    }

    if (icon != null && tint != null) {
        Icon(imageVector = icon, contentDescription = null, tint = tint)
    }
}