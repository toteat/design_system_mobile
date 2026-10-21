package com.toteat.toteatds.components.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.buttons.ToteatPrimaryButton
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.credit_card_icon
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Payment card icon, meant as the `leadingIcon` of the "Pagar" button.
 *
 * Tinted with [LocalContentColor] by default, so inside [ToteatPrimaryButton] it follows the
 * button's content color (white, or the disabled color). Decorative by default: the button text
 * already says what it does.
 */
@Composable
fun PaymentCardIcon(
    modifier: Modifier = Modifier,
    size: Dp = 18.dp,
    tint: Color = LocalContentColor.current,
    contentDescription: String? = null,
    testTag: String = ""
) {
    Icon(
        imageVector = vectorResource(Res.drawable.credit_card_icon),
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier
            .size(size)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    )
}

@Composable
@Preview
private fun PaymentCardIconPreview() {
    ToteatTheme {
        ToteatPrimaryButton(
            text = "Pagar",
            onClick = {},
            leadingIcon = { PaymentCardIcon() }
        )
    }
}
