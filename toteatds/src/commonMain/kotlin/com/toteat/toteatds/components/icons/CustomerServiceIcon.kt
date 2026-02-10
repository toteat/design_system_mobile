package com.toteat.toteatds.components.icons

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.customer_service_icon
import designsystemmobile.toteatds.generated.resources.icon_customer_service
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun CustomerServiceIcon(
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    val description = stringResource(Res.string.icon_customer_service)

    IconButton(
        onClick = onIconClick,
        modifier = modifier
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .semantics {
                role = Role.Button
                contentDescription = description
            }
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.customer_service_icon),
            contentDescription = null
        )
    }
}
