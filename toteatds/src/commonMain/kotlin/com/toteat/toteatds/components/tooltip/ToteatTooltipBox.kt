package com.toteat.toteatds.components.tooltip

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.roundToIntRect
import androidx.compose.ui.unit.toRect
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.toteat.toteatds.components.icons.ToteatCommentIconButton
import com.toteat.toteatds.components.topbar.ToteatTopBar
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import org.jetbrains.compose.ui.tooling.preview.Preview

/** Default maximum width of the tooltip bubble. The bubble wraps its content up to this width. */
val ToteatTooltipDefaultMaxWidth = 280.dp

private val TooltipScreenMargin = 16.dp
private val TooltipAnchorGap = 6.dp
private val TooltipCaretWidth = 16.dp
private val TooltipCaretHeight = 8.dp
private val TooltipCornerRadius = 16.dp
private val TooltipShape = RoundedCornerShape(TooltipCornerRadius)
private val TooltipElevation = 4.dp
private val TooltipHorizontalPadding = 16.dp
private val TooltipVerticalPadding = 14.dp
private val HighlightRingWidth = 3.dp
private val HighlightHaloWidth = 5.dp
private const val ScrimAlpha = 0.4f
private const val HaloAlpha = 0.35f
private const val AppearDurationMillis = 200

/**
 * Anchors a tooltip bubble ("coach mark") to any component.
 *
 * [content] is rendered untouched; while [visible] is `true` a full-window overlay is shown on top of
 * the screen with:
 * - an optional dimming scrim with a cut-out over the anchor, so the anchor stays in full color,
 * - an optional ring in the primary brand color around the anchor, following [highlightShape],
 * - a bubble with a caret pointing at the anchor that hosts [tooltip].
 *
 * The bubble is placed below the anchor when it fits and above otherwise, centered on the anchor and
 * clamped to the screen edges; the caret always points at the anchor center.
 *
 * Any tap outside the bubble, and the system back gesture, call [onDismissRequest]. The host owns
 * the visibility state (and, for "show once" coach marks, its persistence).
 *
 * @param visible Whether the tooltip is shown.
 * @param onDismissRequest Invoked when the user taps outside the bubble or presses back.
 * @param tooltip Content of the bubble. The bubble already provides background, padding and caret;
 * use [ToteatTooltipContent] for the standard title + message + close layout.
 * @param modifier Modifier applied to the anchor container.
 * @param highlightShape Shape of the anchor, used for the scrim cut-out and the ring
 * (e.g. [CircleShape] for circular icon buttons, a pill shape for buttons).
 * @param showScrim Whether the rest of the screen is dimmed.
 * @param highlightAnchor Whether the primary-color ring is drawn around the anchor.
 * @param onHighlightClick Invoked when the user taps the highlighted anchor while the tooltip is
 * shown (e.g. to open the feature directly). The tooltip is not dismissed automatically. With `null`
 * a tap on the anchor dismisses like any other tap outside the bubble.
 * @param maxWidth Maximum width of the bubble. It is also capped by the window width minus margins.
 * @param testTag Optional test tag for UI testing, applied to the bubble.
 * @param content The anchor.
 */
@Composable
fun ToteatTooltipBox(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    tooltip: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    highlightShape: Shape = CircleShape,
    showScrim: Boolean = true,
    highlightAnchor: Boolean = true,
    onHighlightClick: (() -> Unit)? = null,
    maxWidth: Dp = ToteatTooltipDefaultMaxWidth,
    testTag: String = "",
    content: @Composable () -> Unit
) {
    // Both positions are taken on screen: the popup window does not necessarily start at the
    // window origin (on Android it is laid out below the status bar), so window coordinates of the
    // anchor cannot be reused inside the popup.
    var anchorOnScreen by remember { mutableStateOf<Rect?>(null) }
    var overlayOriginOnScreen by remember { mutableStateOf<Offset?>(null) }

    Box(
        modifier = modifier.onGloballyPositioned { coordinates ->
            anchorOnScreen = Rect(coordinates.positionOnScreen(), coordinates.size.toSize())
        },
        propagateMinConstraints = true
    ) {
        content()

        if (visible) {
            Popup(
                popupPositionProvider = WindowOriginPositionProvider,
                onDismissRequest = onDismissRequest,
                properties = PopupProperties(focusable = true)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .onGloballyPositioned { coordinates ->
                            overlayOriginOnScreen = coordinates.positionOnScreen()
                        }
                ) {
                    val anchor = anchorOnScreen
                    val origin = overlayOriginOnScreen
                    if (anchor != null && origin != null) {
                        TooltipOverlay(
                            anchorBounds = anchor.translate(-origin).roundToIntRect(),
                            highlightShape = highlightShape,
                            showScrim = showScrim,
                            highlightAnchor = highlightAnchor,
                            maxWidth = maxWidth,
                            onDismissRequest = onDismissRequest,
                            onHighlightClick = onHighlightClick,
                            testTag = testTag,
                            tooltip = tooltip
                        )
                    }
                }
            }
        }
    }
}

/**
 * Convenience overload of [ToteatTooltipBox] with the standard [ToteatTooltipContent]: title,
 * message and a close button that calls [onDismissRequest].
 *
 * @param testTag Optional test tag. Derived tags: `_title`, `_message`, `_close`.
 */
@Composable
fun ToteatTooltipBox(
    visible: Boolean,
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    highlightShape: Shape = CircleShape,
    showScrim: Boolean = true,
    highlightAnchor: Boolean = true,
    onHighlightClick: (() -> Unit)? = null,
    maxWidth: Dp = ToteatTooltipDefaultMaxWidth,
    testTag: String = "",
    content: @Composable () -> Unit
) {
    ToteatTooltipBox(
        visible = visible,
        onDismissRequest = onDismissRequest,
        tooltip = {
            ToteatTooltipContent(
                title = title,
                message = message,
                onClose = onDismissRequest,
                testTag = testTag
            )
        },
        modifier = modifier,
        highlightShape = highlightShape,
        showScrim = showScrim,
        highlightAnchor = highlightAnchor,
        onHighlightClick = onHighlightClick,
        maxWidth = maxWidth,
        testTag = testTag,
        content = content
    )
}

/** Places the popup at the window origin so the overlay can cover the whole window. */
private object WindowOriginPositionProvider : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset = IntOffset.Zero
}

/** Caret geometry, written during measure and read during draw so it only invalidates drawing. */
private class TooltipCaretState {
    var tipX by mutableFloatStateOf(0f)
    var baseY by mutableFloatStateOf(0f)
    var pointsUp by mutableStateOf(true)
}

@Composable
private fun TooltipOverlay(
    anchorBounds: IntRect,
    highlightShape: Shape,
    showScrim: Boolean,
    highlightAnchor: Boolean,
    maxWidth: Dp,
    onDismissRequest: () -> Unit,
    onHighlightClick: (() -> Unit)?,
    testTag: String,
    tooltip: @Composable () -> Unit
) {
    val scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = ScrimAlpha)
    val ringColor = MaterialTheme.colorScheme.primary
    val bubbleColor = MaterialTheme.colorScheme.background
    val currentOnDismissRequest by rememberUpdatedState(onDismissRequest)
    val currentOnHighlightClick by rememberUpdatedState(onHighlightClick)
    val caret = remember { TooltipCaretState() }
    val appear = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        appear.animateTo(1f, tween(AppearDurationMillis))
    }

    Layout(
        content = {
            Box(
                modifier = Modifier
                    .shadow(TooltipElevation, TooltipShape)
                    .background(bubbleColor, TooltipShape)
                    // Swallow taps on the bubble so they do not reach the dismiss handler below.
                    .pointerInput(Unit) { detectTapGestures { } }
                    .semantics { liveRegion = LiveRegionMode.Polite }
                    .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
                    .padding(horizontal = TooltipHorizontalPadding, vertical = TooltipVerticalPadding)
            ) {
                tooltip()
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { alpha = appear.value }
            .pointerInput(anchorBounds) {
                detectTapGestures { offset ->
                    val onHighlight = currentOnHighlightClick
                    if (onHighlight != null && anchorBounds.toRect().contains(offset)) {
                        onHighlight()
                    } else {
                        currentOnDismissRequest()
                    }
                }
            }
            .drawWithContent {
                val anchorRect = anchorBounds.toRect()
                val ringWidth = HighlightRingWidth.toPx()
                if (showScrim) {
                    val hole = if (highlightAnchor) anchorRect.inflate(ringWidth) else anchorRect
                    clipPath(shapePath(highlightShape, hole), ClipOp.Difference) {
                        drawRect(scrimColor)
                    }
                }
                if (highlightAnchor) {
                    val haloWidth = HighlightHaloWidth.toPx()
                    drawPath(
                        path = shapePath(highlightShape, anchorRect.inflate(ringWidth + haloWidth / 2)),
                        color = ringColor.copy(alpha = HaloAlpha),
                        style = Stroke(width = haloWidth)
                    )
                    drawPath(
                        path = shapePath(highlightShape, anchorRect.inflate(ringWidth / 2)),
                        color = ringColor,
                        style = Stroke(width = ringWidth)
                    )
                }

                drawContent()

                drawCaret(caret, bubbleColor)
            }
    ) { measurables, constraints ->
        val windowWidth = constraints.maxWidth
        val windowHeight = constraints.maxHeight
        val margin = TooltipScreenMargin.roundToPx()
        val gap = TooltipAnchorGap.roundToPx()
        val caretHeight = TooltipCaretHeight.roundToPx()
        val caretHalfWidth = TooltipCaretWidth.roundToPx() / 2
        val cornerRadius = TooltipCornerRadius.roundToPx()

        val bubbleMaxWidth = minOf(maxWidth.roundToPx(), windowWidth - 2 * margin).coerceAtLeast(0)
        val bubble = measurables.first().measure(
            Constraints(maxWidth = bubbleMaxWidth, maxHeight = windowHeight)
        )

        val spaceBelow = windowHeight - anchorBounds.bottom - gap - caretHeight - margin
        val spaceAbove = anchorBounds.top - gap - caretHeight - margin
        val placeBelow = bubble.height <= spaceBelow || spaceBelow >= spaceAbove

        val anchorCenterX = anchorBounds.center.x
        val bubbleX = (anchorCenterX - bubble.width / 2)
            .coerceIn(margin, (windowWidth - margin - bubble.width).coerceAtLeast(margin))
        val bubbleY = if (placeBelow) {
            anchorBounds.bottom + gap + caretHeight
        } else {
            anchorBounds.top - gap - caretHeight - bubble.height
        }

        val caretMinX = bubbleX + cornerRadius + caretHalfWidth
        val caretMaxX = bubbleX + bubble.width - cornerRadius - caretHalfWidth
        caret.tipX = if (caretMinX <= caretMaxX) {
            anchorCenterX.coerceIn(caretMinX, caretMaxX).toFloat()
        } else {
            (bubbleX + bubble.width / 2).toFloat()
        }
        caret.baseY = (if (placeBelow) bubbleY else bubbleY + bubble.height).toFloat()
        caret.pointsUp = placeBelow

        layout(windowWidth, windowHeight) {
            bubble.place(bubbleX, bubbleY)
        }
    }
}

private fun DrawScope.drawCaret(caret: TooltipCaretState, color: Color) {
    val halfWidth = TooltipCaretWidth.toPx() / 2
    val height = TooltipCaretHeight.toPx()
    // Overlap the bubble by 1px so no seam shows between caret and bubble.
    val overlap = 1f
    val direction = if (caret.pointsUp) -1f else 1f
    val base = caret.baseY - direction * overlap
    val path = Path().apply {
        moveTo(caret.tipX - halfWidth, base)
        lineTo(caret.tipX, caret.baseY + direction * height)
        lineTo(caret.tipX + halfWidth, base)
        close()
    }
    drawPath(path, color)
}

private fun DrawScope.shapePath(shape: Shape, rect: Rect): Path {
    val outline = shape.createOutline(rect.size, layoutDirection, this)
    return Path().apply {
        addOutline(outline)
        translate(Offset(rect.left, rect.top))
    }
}

@Composable
@Preview
private fun ToteatTooltipBoxPreview() {
    ToteatTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            ToteatTopBar(
                centerComponent = {
                    Text(
                        text = "Mesa S7",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                },
                rightComponent = {
                    ToteatTooltipBox(
                        visible = true,
                        title = "Chat con cocina",
                        message = "Envía comentarios a la impresora que necesites.",
                        onDismissRequest = {}
                    ) {
                        ToteatCommentIconButton(onClick = {}, badgeText = "Nuevo")
                    }
                }
            )
        }
    }
}
