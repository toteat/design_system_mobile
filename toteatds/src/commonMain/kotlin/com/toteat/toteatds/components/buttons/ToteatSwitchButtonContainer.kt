package com.toteat.toteatds.components.buttons
import com.toteat.toteatds.utils.setTestTag

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.toteat.toteatds.theme.ToteatTheme
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.switch_description
import designsystemmobile.toteatds.generated.resources.switch_off
import designsystemmobile.toteatds.generated.resources.switch_on
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.toteat.toteatds.components.icons.ChangeIconComponent

private val ContainerShape = RoundedCornerShape(16.dp)

@Immutable
private data class SwitchColors(
    val trackOn: Color,
    val trackOff: Color,
    val thumbOn: Color,
    val thumbOff: Color
)


@Composable
fun ToteatSwitchButtonContainer(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    testTag: String = ""
) {
    val contentAlpha = if (enabled) 1f else 0.38f
    val switchState =
        if (checked) stringResource(Res.string.switch_on) else stringResource(Res.string.switch_off)
    val description = stringResource(Res.string.switch_description, title, subtitle, switchState)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = ContainerShape,
        color = MaterialTheme.colorScheme.background,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .clickable(enabled = enabled) { onCheckedChange(!checked) }
                .semantics { contentDescription = description }
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
                .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = contentAlpha),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    ChangeIconComponent(
                        size = 12.dp,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            CustomSwitch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    checkedTrackColor: Color,
    checkedThumbColor: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    width: Dp = 52.dp,
    height: Dp = 32.dp,
    thumbSize: Dp = 24.dp,
    uncheckedTrackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    uncheckedThumbColor: Color = MaterialTheme.colorScheme.outline
) {
    val interactionSource = remember { MutableInteractionSource() }
    val thumbPadding = (height - thumbSize) / 2
    val contentAlpha = if (enabled) 1f else 0.38f

    // Cache colors to avoid .copy() allocations during animation
    val colors = remember(checkedTrackColor, uncheckedTrackColor, checkedThumbColor, uncheckedThumbColor, contentAlpha) {
        SwitchColors(
            trackOn = checkedTrackColor.copy(alpha = contentAlpha),
            trackOff = uncheckedTrackColor.copy(alpha = contentAlpha),
            thumbOn = checkedThumbColor.copy(alpha = contentAlpha),
            thumbOff = uncheckedThumbColor.copy(alpha = contentAlpha)
        )
    }

    // Single animation drives all visual changes (reduces 4 animations to 1)
    val progress by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "SwitchProgress"
    )

    // Pre-calculate positions
    val thumbStartX = thumbPadding
    val thumbEndX = width - thumbSize - thumbPadding

    Canvas(
        modifier = modifier
            .size(width = width, height = height)
            .semantics { role = Role.Switch; selected = checked }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled
            ) { onCheckedChange(!checked) }
    ) {
        val cornerRadius = CornerRadius(height.toPx() / 2)

        // Interpolate colors based on single progress value
        val trackColor = lerp(colors.trackOff, colors.trackOn, progress)
        val thumbColor = lerp(colors.thumbOff, colors.thumbOn, progress)

        drawRoundRect(
            color = trackColor,
            cornerRadius = cornerRadius
        )

        // Interpolate thumb position
        val thumbX = lerp(thumbStartX.toPx(), thumbEndX.toPx(), progress)

        drawCircle(
            color = thumbColor,
            radius = thumbSize.toPx() / 2,
            center = Offset(
                x = thumbX + (thumbSize.toPx() / 2),
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
            ToteatSwitchButtonContainer(
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
            ToteatSwitchButtonContainer(
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
                ToteatSwitchButtonContainer(
                    title = "Terminal compartido",
                    subtitle = "Esta opción está deshabilitada",
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    enabled = false
                )
                Spacer(Modifier.height(16.dp))
                ToteatSwitchButtonContainer(
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
