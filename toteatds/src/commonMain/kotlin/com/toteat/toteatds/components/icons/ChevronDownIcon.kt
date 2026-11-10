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
import designsystemmobile.toteatds.generated.resources.icon_chevron_down
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Chevron de trazo fino que apunta hacia abajo. Es decorativo: no tiene descripción
 * accesible, así que el elemento que lo contiene debe describir su propia acción.
 */
@Composable
fun ChevronDownIcon(
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    tint: Color = LocalContentColor.current,
    testTag: String = ""
) {
    Icon(
        imageVector = vectorResource(Res.drawable.icon_chevron_down),
        contentDescription = null,
        modifier = modifier
            .size(size)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        tint = tint
    )
}

@Composable
@Preview
private fun ChevronDownIconPreview() {
    ToteatTheme {
        ChevronDownIcon()
    }
}
