package com.toteat.toteatds.components.buttons
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyMediumRegular
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_change
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun SwitchButtonContainer(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = vectorResource(Res.drawable.icon_change),
) {
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
                .clickable { onCheckedChange(!checked) }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    icon?.let {
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            imageVector = it,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMediumRegular,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(Modifier.width(16.dp))
            CustomSwitch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedBorderColor = MaterialTheme.colorScheme.primary,
                borderWidth = 1.dp
            )
        }
    }
}

@Composable
private fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
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

    val thumbPosition by animateDpAsState(
        targetValue = if (checked) width - thumbSize - thumbPadding else thumbPadding,
        animationSpec = tween(durationMillis = 250), label = "ThumbPosition"
    )

    val trackColor by animateColorAsState(
        targetValue = if (checked) checkedTrackColor else uncheckedTrackColor,
        animationSpec = tween(durationMillis = 250), label = "TrackColor"
    )

    val thumbColor by animateColorAsState(
        targetValue = if (checked) checkedThumbColor else uncheckedThumbColor,
        animationSpec = tween(durationMillis = 250), label = "ThumbColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (checked) checkedBorderColor else uncheckedBorderColor,
        animationSpec = tween(durationMillis = 250), label = "BorderColor"
    )

    Canvas(
        modifier = modifier
            .size(width = width, height = height)
            .clickable(
                interactionSource = interactionSource,
                indication = null
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
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
