package com.toteat.toteatds.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ArrowBackIconButton(
    onNavigateBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    IconButton(
        onClick = onNavigateBackClick,
        enabled = enabled,
        modifier = modifier
            .minimumInteractiveComponentSize()
            .size(48.dp)
            .clip(CircleShape)
            .background(
                color = if (enabled) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                }
            )
            .semantics {
                role = Role.Button
                contentDescription = "Navegar hacia atrás"
            },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            contentColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.38f)
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
private fun ArrowBackIconButtonPreview() {
    ToteatTheme {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            ArrowBackIconButton(
                onNavigateBackClick = {}
            )

            ArrowBackIconButton(
                onNavigateBackClick = {},
                enabled = false
            )
        }
    }
}
