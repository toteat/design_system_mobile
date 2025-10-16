package com.toteat.toteatds.components.brand.iso

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.toteat_iso_logo_cream_orange
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatIsoCreamOrange() {
    Image(
        imageVector = vectorResource(Res.drawable.toteat_iso_logo_cream_orange),
        contentDescription = null
    )
}

@Composable
@Preview
fun ToteatIsoCreamOrangePreview() {
    ToteatIsoCreamOrange()
}
