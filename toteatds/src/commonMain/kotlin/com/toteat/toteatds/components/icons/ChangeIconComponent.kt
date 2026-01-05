package com.toteat.toteatds.components.icons
import com.toteat.toteatds.utils.setTestTag

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_change
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChangeIconComponent(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    tint: Color = MaterialTheme.colorScheme.extended.neutral500
) {
    Image(
        modifier = modifier.size(size),
        imageVector = vectorResource(Res.drawable.icon_change),
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint)
    )
}

@Composable
@Preview
private fun ChangeIconComponentPreview() {
    ToteatTheme {
        ChangeIconComponent()
    }
}

@Composable
@Preview
private fun ChangeIconComponentPrimaryTintPreview() {
    ToteatTheme {
        ChangeIconComponent(tint = MaterialTheme.colorScheme.primary)
    }
}
