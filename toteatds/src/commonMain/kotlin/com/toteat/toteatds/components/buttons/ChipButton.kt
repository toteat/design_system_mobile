package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray100
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.TertiaryNormal
import com.toteat.toteatds.theme.ToteatTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChipButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = when {
            !enabled -> NeutralGray.copy(alpha = 0.38f)
            else -> Color.Black
        },
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clip(CircleShape)
            .background(
                color = when {
                    !enabled -> NeutralGray300.copy(alpha = 0.5f)
                    isSelected -> TertiaryNormal
                    else -> NeutralGray100
                }
            )
            .clickable(
                enabled = enabled,
                onClick = onClick,
                role = Role.Button
            )
            .semantics {
                role = Role.Button
                selected = isSelected
                contentDescription = if (isSelected) {
                    "$text, seleccionado"
                } else {
                    "$text, no seleccionado"
                }
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ChipButtonPreview() {
    ToteatTheme {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ChipButton(
                text = "Salon",
                isSelected = false,
                onClick = {}
            )

            ChipButton(
                text = "Bar",
                isSelected = true,
                onClick = {}
            )

            ChipButton(
                text = "Terraza",
                isSelected = false,
                onClick = {},
                enabled = false
            )

            ChipButton(
                text = "VIP",
                isSelected = true,
                onClick = {},
                enabled = false
            )
        }
    }
}

