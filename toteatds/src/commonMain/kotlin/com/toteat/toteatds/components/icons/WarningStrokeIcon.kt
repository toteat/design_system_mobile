package com.toteat.toteatds.components.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_warning
import designsystemmobile.toteatds.generated.resources.icon_warning_stroke
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WarningStrokeIcon(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    tint: Color = LocalContentColor.current,
    testTag: String = ""
) {
    val contentDescription = stringResource(Res.string.icon_warning)

    Icon(
        imageVector = vectorResource(Res.drawable.icon_warning_stroke),
        contentDescription = contentDescription,
        modifier = modifier
            .size(size)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        tint = tint
    )
}

@Composable
@Preview
private fun WarningStrokeIconPreview() {
    ToteatTheme {
        WarningStrokeIcon()
    }
}
