package com.toteat.toteatds.components.brand.logo

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.toteat_logo_color
import designsystemmobile.toteatds.generated.resources.toteat_logo_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatLogoOriginal(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    testTag: String = ""
) {
    val description = contentDescription ?: stringResource(Res.string.toteat_logo_description)

    Image(
        modifier = modifier
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        imageVector = vectorResource(Res.drawable.toteat_logo_color),
        contentDescription = description
    )
}

@Composable
@Preview
private fun ToteatLogoOriginalPreview() {
    ToteatLogoOriginal()
}
