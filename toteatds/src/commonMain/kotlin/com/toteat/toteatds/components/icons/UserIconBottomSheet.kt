package com.toteat.toteatds.components.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_user
import designsystemmobile.toteatds.generated.resources.user_icon_bottom_sheet
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UserIconButtonSheet(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    val contentDescription = stringResource(Res.string.icon_user)

    Icon(
        imageVector = vectorResource(Res.drawable.user_icon_bottom_sheet),
        contentDescription = contentDescription,
        modifier = modifier.size(size)
    )
}

@Composable
@Preview
private fun UserIconButtonSheetPreview() {
    ToteatTheme {
        UserIconButtonSheet(

        )
    }
}