package com.toteat.toteatds.components.brand.iso
import com.toteat.toteatds.utils.setTestTag

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.toteat_iso_logo_cream_orange
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatIsoCreamOrange(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
        imageVector = vectorResource(Res.drawable.toteat_iso_logo_cream_orange),
        contentDescription = null
    )
}

@Composable
@Preview
private fun ToteatIsoCreamOrangePreview() {
    ToteatIsoCreamOrange()
}
