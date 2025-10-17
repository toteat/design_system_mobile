package com.toteat.toteatds.components.topbar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.toteat.toteatds.components.icons.ArrowBackIconButton
import com.toteat.toteatds.theme.ToteatTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BackNavigationTopBar(
    onNavigateBackClick: () -> Unit
) {
    ToteatTopBar(
        leftComponent = {
            ArrowBackIconButton(onNavigateBackClick = { onNavigateBackClick() })
        },
        centerComponent = {
            Text(
                text = "Mesa B1",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
}

@Composable
@Preview
fun BackNavigationTopBarPreview() {
    ToteatTheme {
        BackNavigationTopBar(
            onNavigateBackClick = {}
        )
    }
}
