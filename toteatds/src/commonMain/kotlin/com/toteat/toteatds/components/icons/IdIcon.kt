package com.toteat.toteatds.components.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_id
import designsystemmobile.toteatds.generated.resources.id_icon
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IdIcon(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    tint: Color = NeutralGray,
    contentDescription: String? = null,
    testTag: String = ""
) {
    val defaultContentDescription = stringResource(Res.string.icon_id)

    Icon(
        imageVector = vectorResource(Res.drawable.id_icon),
        contentDescription = contentDescription ?: defaultContentDescription,
        modifier = modifier
            .size(size)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        tint = tint
    )
}

@Composable
@Preview
private fun IdIconPreview() {
    ToteatTheme {
        IdIcon()
    }
}

@Composable
@Preview
private fun IdIconPrimaryTintPreview() {
    ToteatTheme {
        IdIcon(tint = MaterialTheme.colorScheme.primary)
    }
}
