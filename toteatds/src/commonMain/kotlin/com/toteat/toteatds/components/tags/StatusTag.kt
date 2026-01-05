package com.toteat.toteatds.components.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.status_tag_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StatusTag(
    variant: StatusTagVariant,
    modifier: Modifier = Modifier,
    text: String? = null,
    testTag: String = ""
) {
    val defaultText = stringResource(variant.defaultTextRes)
    val displayText = text ?: defaultText
    val statusDescription = stringResource(Res.string.status_tag_description, displayText)

    Box(
        modifier = modifier
            .heightIn(min = 22.dp)
            .wrapContentWidth()
            .widthIn(max = 200.dp)
            .background(
                color = variant.backgroundColor,
                shape = RoundedCornerShape(50)
            )
            .semantics {
                role = Role.Image
                contentDescription = statusDescription
            }
            .setTestTag(testTag)
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = displayText,
            color = variant.textColor,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun StatusTagVariantPreview() {
    ToteatTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            StatusTag(
                variant = StatusTagVariant.Pending,
                text = "Pendiente"
            )
            StatusTag(
                variant = StatusTagVariant.Confirmed,
                text = "Confirmado"
            )
            StatusTag(
                variant = StatusTagVariant.Ended,
                text = "Finalizado"
            )
            StatusTag(
                variant = StatusTagVariant.Cancelled,
                text = "Cancelado"
            )

            StatusTag(
                variant = StatusTagVariant.Pending,
                text = "OK"
            )

            StatusTag(
                variant = StatusTagVariant.Confirmed,
                text = "Texto muy largo que debería truncarse"
            )
        }
    }
}
