package com.toteat.toteatds.components.brand.logo

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.toteat_logo_cream_and_orange
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatLogoCreamOrange() {
    Image(
        imageVector = vectorResource(Res.drawable.toteat_logo_cream_and_orange),
        contentDescription = null,
    )
}

@Composable
@Preview
fun ToteatLogoCreamOrangePreview() {
    ToteatLogoCreamOrange()
}