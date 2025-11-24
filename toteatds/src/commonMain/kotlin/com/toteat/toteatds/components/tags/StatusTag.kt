package com.toteat.toteatds.components.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.ToteatTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun StatusTag(
    variant: StatusTagVariant,
    modifier: Modifier = Modifier,
    text: String? = null
) {
    Box(
        modifier = modifier
            .background(
                color = variant.backgroundColor,
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 20.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = (text ?: variant.defaultText).uppercase(),
            color = variant.textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
@Preview(showBackground = true)
fun StatusTagVariantPreview() {
    ToteatTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
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
        }
    }
}
