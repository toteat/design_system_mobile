package com.toteat.toteatds.components.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.group_bottom_view
import designsystemmobile.toteatds.generated.resources.icon_config
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ConfigBottomSheet(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    testTag: String = ""
) {
    val contentDescription = stringResource(Res.string.icon_config)

    Icon(
        imageVector = vectorResource(Res.drawable.group_bottom_view),
        contentDescription = contentDescription,
        modifier = modifier
            .size(size)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    )
}

@Deprecated(
    message = "Use ConfigBottomSheet instead",
    replaceWith = ReplaceWith("ConfigBottomSheet(modifier, size, testTag)")
)
@Composable
fun ConfigBottonSheet(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    testTag: String = ""
) = ConfigBottomSheet(modifier, size, testTag)

@Composable
@Preview
private fun ConfigBottomSheetPreview() {
    ToteatTheme {
        ConfigBottomSheet()
    }
}
