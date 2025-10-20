package com.toteat.toteatds.components.topbar

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.brand.iso.ToteatIsoCreamOrange
import com.toteat.toteatds.components.icons.CustomerServiceIcon
import com.toteat.toteatds.theme.ToteatTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoginTopBar(
    onMenuIconClick: () -> Unit,
    onCustomerServiceButtonClick: () -> Unit
) {
    ToteatTopBar(
        leftComponent = {
            IconButton(onClick = { onMenuIconClick() }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        centerComponent = {
            ToteatIsoCreamOrange(modifier = Modifier.size(26.dp).weight(1f))

        },
        rightComponent = {
            CustomerServiceIcon(onIconClick = { onCustomerServiceButtonClick() })
        }
    )
}

@Composable
@Preview
fun LoginTopBarPreview() {
    ToteatTheme {
        LoginTopBar(
            onMenuIconClick = {},
            onCustomerServiceButtonClick = {}
        )
    }
}