package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.diner_container_description
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.first
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Horizontally scrollable row of [ToteatDinerButton]s for diner assignment.
 *
 * Renders an optional leading "Agregar" button, one button per diner name and
 * an optional trailing "Ver más" button. When the content is wider than the
 * screen the row scrolls horizontally.
 *
 * [enabled] applies to every button. [isAddEnabled] applies only to "Agregar",
 * so it can be disabled (e.g. when the diner limit is reached) while the diner
 * and "Ver más" buttons stay interactive.
 *
 * Whenever [selectedDiner] changes (including its first value) or moves to another
 * position in [diners], the row scrolls with an animation just enough to show that
 * diner's button completely. If it is already fully visible, or [selectedDiner] is
 * null or not in [diners], the row doesn't move.
 */
@Composable
fun ToteatDinerButtonContainer(
    diners: ImmutableList<String>,
    selectedDiner: String?,
    onDinerSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    onAddClick: (() -> Unit)? = null,
    onViewMoreClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    isAddEnabled: Boolean = true,
    testTag: String = ""
) {
    val containerDescription = stringResource(Res.string.diner_container_description)
    val listState = rememberLazyListState()

    // "Agregar" goes before the diners, so it shifts their indexes by one.
    val dinerIndex = selectedDiner?.let { diners.indexOf(it) } ?: -1
    val selectedIndex = if (dinerIndex < 0) -1 else dinerIndex + if (onAddClick != null) 1 else 0

    LaunchedEffect(selectedDiner, selectedIndex) {
        if (selectedIndex >= 0) {
            listState.scrollToShowFully(selectedIndex)
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier
            .semantics {
                contentDescription = containerDescription
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (onAddClick != null) {
            item(key = "diner_add") {
                ToteatDinerButton(
                    type = ToteatDinerButtonType.Add,
                    onClick = onAddClick,
                    enabled = enabled && isAddEnabled,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_add" else ""
                )
            }
        }

        items(
            items = diners,
            key = { diner -> diner }
        ) { diner ->
            val onClick = remember(diner, enabled) {
                { onDinerSelect(diner) }
            }

            ToteatDinerButton(
                type = ToteatDinerButtonType.Diner(diner),
                isSelected = diner == selectedDiner,
                onClick = onClick,
                enabled = enabled,
                testTag = if (testTag.isNotEmpty()) "${testTag}_$diner" else ""
            )
        }

        if (onViewMoreClick != null) {
            item(key = "diner_view_more") {
                ToteatDinerButton(
                    type = ToteatDinerButtonType.ViewMore,
                    onClick = onViewMoreClick,
                    enabled = enabled,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_view_more" else ""
                )
            }
        }
    }
}

/**
 * Scrolls with an animation the minimum needed so the item at [index] is fully
 * visible: aligned to the start if it's before the viewport, to the end if it's
 * after. Does nothing if it's already fully visible.
 */
private suspend fun LazyListState.scrollToShowFully(index: Int) {
    // Wait for a layout that already includes the item (e.g. a diner just added).
    snapshotFlow { layoutInfo.totalItemsCount }.first { it > index }

    val info = layoutInfo
    val visible = info.visibleItemsInfo
    if (visible.isEmpty()) return
    val viewportStart = info.viewportStartOffset
    val viewportEnd = info.viewportEndOffset

    val item = visible.firstOrNull { it.index == index }
    when {
        item != null -> {
            val itemEnd = item.offset + item.size
            when {
                item.offset < viewportStart -> animateScrollBy((item.offset - viewportStart).toFloat())
                itemEnd > viewportEnd -> {
                    // Never push the item's start out of the viewport when it's wider than it.
                    val delta = minOf(itemEnd - viewportEnd, item.offset - viewportStart)
                    animateScrollBy(delta.toFloat())
                }
            }
        }

        index < visible.first().index -> animateScrollToItem(index)

        else -> {
            // Not laid out yet: bring it in aligned to the end, estimating its size from the
            // visible items (all diner buttons are the same width), then adjust if needed.
            val estimatedSize = visible.sumOf { it.size } / visible.size
            val viewportSize = viewportEnd - viewportStart
            animateScrollToItem(index, scrollOffset = -(viewportSize - estimatedSize).coerceAtLeast(0))
            layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }?.let { laidOut ->
                val overflow = laidOut.offset + laidOut.size - layoutInfo.viewportEndOffset
                if (overflow > 0) animateScrollBy(overflow.toFloat())
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ToteatDinerButtonContainerPreview() {
    ToteatTheme {
        var selectedDiner by remember { mutableStateOf<String?>("Trini") }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ToteatDinerButtonContainer(
                diners = persistentListOf("Trini", "Pauli", "Camila", "Angie", "Vale", "Sofi", "Cata"),
                selectedDiner = selectedDiner,
                onDinerSelect = { selectedDiner = it },
                onAddClick = {},
                onViewMoreClick = {}
            )

            ToteatDinerButtonContainer(
                diners = persistentListOf("Trini", "Pauli"),
                selectedDiner = "Pauli",
                onDinerSelect = {},
                onAddClick = {},
                onViewMoreClick = {},
                enabled = false
            )

            ToteatDinerButtonContainer(
                diners = persistentListOf("Trini", "Pauli", "Camila"),
                selectedDiner = selectedDiner,
                onDinerSelect = { selectedDiner = it },
                onAddClick = {},
                onViewMoreClick = {},
                isAddEnabled = false
            )
        }
    }
}
