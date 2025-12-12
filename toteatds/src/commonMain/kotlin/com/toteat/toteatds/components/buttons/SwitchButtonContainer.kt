package com.toteat.toteatds.components.buttons
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun SwitchButtonContainer(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val contentAlpha = if (enabled) 1f else 0.38f

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            ),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .clickable(enabled = enabled) { onCheckedChange(!checked) }
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Columna de textos con weight para ocupar el espacio disponible
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = contentAlpha),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Switch fijo a la derecha
            CustomSwitch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedBorderColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    width: Dp = 52.dp,
    height: Dp = 32.dp,
    thumbSize: Dp = 24.dp,
    borderWidth: Dp = 0.dp,
    checkedTrackColor: Color,
    checkedThumbColor: Color,
    uncheckedTrackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    uncheckedThumbColor: Color = MaterialTheme.colorScheme.outline,
    checkedBorderColor: Color,
    uncheckedBorderColor: Color = MaterialTheme.colorScheme.outline
) {
    val interactionSource = remember { MutableInteractionSource() }
    val thumbPadding = (height - thumbSize) / 2
    val contentAlpha = if (enabled) 1f else 0.38f

    val thumbPosition by animateDpAsState(
        targetValue = if (checked) width - thumbSize - thumbPadding else thumbPadding,
        animationSpec = tween(durationMillis = 250), label = "ThumbPosition"
    )

    val trackColor by animateColorAsState(
        targetValue = if (checked) checkedTrackColor.copy(alpha = contentAlpha) else uncheckedTrackColor.copy(alpha = contentAlpha),
        animationSpec = tween(durationMillis = 250), label = "TrackColor"
    )

    val thumbColor by animateColorAsState(
        targetValue = if (checked) checkedThumbColor.copy(alpha = contentAlpha) else uncheckedThumbColor.copy(alpha = contentAlpha),
        animationSpec = tween(durationMillis = 250), label = "ThumbColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (checked) checkedBorderColor.copy(alpha = contentAlpha) else uncheckedBorderColor.copy(alpha = contentAlpha),
        animationSpec = tween(durationMillis = 250), label = "BorderColor"
    )

    Canvas(
        modifier = modifier
            .size(width = width, height = height)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled
            ) { onCheckedChange(!checked) }
    ) {

        drawRoundRect(
            color = trackColor,
            cornerRadius = CornerRadius(height.toPx() / 2)
        )


        if (borderWidth > 0.dp) {
            drawRoundRect(
                color = borderColor,
                cornerRadius = CornerRadius(height.toPx() / 2),
                style = Stroke(width = borderWidth.toPx())
            )
        }


        drawCircle(
            color = thumbColor,
            radius = thumbSize.toPx() / 2,
            center = Offset(
                x = thumbPosition.toPx() + (thumbSize.toPx() / 2),
                y = size.height / 2
            )
        )
    }
}


@Preview
@Composable
private fun SwitchButtonContainerPreview() {
    var isChecked by remember { mutableStateOf(false) }
    ToteatTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SwitchButtonContainer(
                title = "Terminal compartido",
                subtitle = "Esta opción es para POS compartidas",
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SwitchButtonContainerCheckedPreview() {
    var isChecked by remember { mutableStateOf(true) }
    ToteatTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SwitchButtonContainer(
                title = "Terminal compartido",
                subtitle = "Esta opción es para POS compartidas",
                checked = isChecked,
                onCheckedChange = { isChecked = it },
                modifier = Modifier.padding(50.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SwitchButtonContainerDisabledPreview() {
    var isChecked by remember { mutableStateOf(true) }
    ToteatTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.padding(16.dp)) {
                SwitchButtonContainer(
                    title = "Terminal compartido",
                    subtitle = "Esta opción está deshabilitada",
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    enabled = false
                )
                Spacer(Modifier.height(16.dp))
                SwitchButtonContainer(
                    title = "Terminal compartido",
                    subtitle = "Esta opción está deshabilitada",
                    checked = false,
                    onCheckedChange = { },
                    enabled = false
                )
            }
        }
    }
}

