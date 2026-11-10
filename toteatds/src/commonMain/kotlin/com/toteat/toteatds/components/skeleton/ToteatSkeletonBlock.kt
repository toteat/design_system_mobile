package com.toteat.toteatds.components.skeleton

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import org.jetbrains.compose.ui.tooling.preview.Preview

// Mismo gris que el skeleton original de la app, para que se vea igual.
private val SkeletonColor = Color.LightGray

/**
 * Bloque gris con pulso para siluetas de carga. El ancho lo define [modifier]. El pulso se
 * dibuja en la fase de dibujo, así que no recompone en cada cuadro.
 */
@Composable
fun ToteatSkeletonBlock(
    height: Dp,
    cornerRadius: Dp,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    val transition = rememberInfiniteTransition(label = "skeleton_block")
    val alpha = transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(animation = tween(1000), repeatMode = RepeatMode.Reverse),
        label = "skeleton_block_alpha"
    )
    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .drawBehind { drawRect(SkeletonColor.copy(alpha = alpha.value)) }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFAFAFA)
private fun ToteatSkeletonBlockPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToteatSkeletonBlock(
                height = 17.dp,
                cornerRadius = 4.dp,
                modifier = Modifier.padding(start = 8.dp).width(120.dp)
            )
            ToteatSkeletonBlock(height = 84.dp, cornerRadius = 12.dp, modifier = Modifier.fillMaxWidth())
        }
    }
}
