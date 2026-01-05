package com.toteat.toteatds.components.brand.logo
import com.toteat.toteatds.utils.setTestTag

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.toteat_logo_black_and_cream
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatLogoBlackAndCream(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
        imageVector = vectorResource(Res.drawable.toteat_logo_black_and_cream),
        contentDescription = null
    )
}

@Composable
@Preview
fun ToteatLogoBlackAndCreamPreview() {
    ToteatLogoBlackAndCream()
}