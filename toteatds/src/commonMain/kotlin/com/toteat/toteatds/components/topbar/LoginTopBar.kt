package com.toteat.toteatds.components.topbar

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.brand.iso.ToteatIsoCreamOrange
import com.toteat.toteatds.components.icons.CustomerServiceIcon
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.login_topbar_semantic_label
import designsystemmobile.toteatds.generated.resources.menu_open_description
import designsystemmobile.toteatds.generated.resources.toteat_logo_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoginTopBar(
    onMenuIconClick: () -> Unit,
    onCustomerServiceButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    val menuDescription = stringResource(Res.string.menu_open_description)
    val logoDescription = stringResource(Res.string.toteat_logo_description)

    ToteatTopBar(
        modifier = modifier,
        semanticLabel = stringResource(Res.string.login_topbar_semantic_label),
        leftComponent = {
            IconButton(
                onClick = onMenuIconClick,
                modifier = Modifier.semantics {
                    contentDescription = menuDescription
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        centerComponent = {
            ToteatIsoCreamOrange(
                modifier = Modifier
                    .size(26.dp)
                    .semantics {
                        contentDescription = logoDescription
                    }
            )
        },
        rightComponent = {
            CustomerServiceIcon(onIconClick = onCustomerServiceButtonClick)
        },
        testTag = testTag
    )
}

@Composable
@Preview
private fun LoginTopBarPreview() {
    ToteatTheme {
        LoginTopBar(
            onMenuIconClick = {},
            onCustomerServiceButtonClick = {}
        )
    }
}
