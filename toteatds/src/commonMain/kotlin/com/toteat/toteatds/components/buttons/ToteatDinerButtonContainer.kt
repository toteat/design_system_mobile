package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Horizontally scrollable row of [ToteatDinerButton]s for diner assignment.
 *
 * Renders an optional leading "Agregar" button, one button per diner name and
 * an optional trailing "Ver más" button. When the content is wider than the
 * screen the row scrolls horizontally.
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
    testTag: String = ""
) {
    val containerDescription = stringResource(Res.string.diner_container_description)

    LazyRow(
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
                    enabled = enabled,
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
        }
    }
}
