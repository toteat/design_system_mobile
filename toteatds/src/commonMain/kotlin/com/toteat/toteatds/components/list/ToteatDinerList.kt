package com.toteat.toteatds.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.diner_list_description
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Alphabetically grouped diner list.
 *
 * Sorts [diners] and groups them by the first letter of each name. Every
 * group renders a centered letter header over a light gray background,
 * followed by one selectable bulleted row per diner. Backed by a
 * [LazyColumn], so long lists scroll vertically; when embedded inside
 * another vertical scrollable, give it a bounded height.
 */
@Composable
fun ToteatDinerList(
    diners: ImmutableList<String>,
    onDinerClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = ""
) {
    val listDescription = stringResource(Res.string.diner_list_description)

    val groups = remember(diners) {
        diners
            .sortedWith(String.CASE_INSENSITIVE_ORDER)
            .groupBy { it.first().uppercaseChar() }
            .toList()
    }

    LazyColumn(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(Color.White)
            .semantics { contentDescription = listDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        groups.forEach { (letter, names) ->
            item(key = "header_$letter") {
                DinerListHeader(
                    letter = letter,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_header_$letter" else ""
                )
            }

            itemsIndexed(
                items = names,
                // Index-based key: diner names may repeat
                key = { index, _ -> "item_${letter}_$index" }
            ) { _, name ->
                val onClick = remember(name) {
                    { onDinerClick(name) }
                }

                DinerListRow(
                    name = name,
                    onClick = onClick,
                    testTag = if (testTag.isNotEmpty()) "${testTag}_item_$name" else ""
                )
            }
        }
    }
}

@Composable
private fun DinerListHeader(
    letter: Char,
    testTag: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.extended.neutral100)
            .padding(vertical = 4.dp)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        Text(
            text = letter.toString(),
            style = MaterialTheme.typography.headlineMedium,
            color = SecondaryNormal
        )
    }
}

@Composable
private fun DinerListRow(
    name: String,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(
                onClick = onClick,
                role = Role.Button
            )
            .semantics { role = Role.Button }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyLargeRegular,
            color = MaterialTheme.colorScheme.extended.neutral500
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLargeRegular,
            color = MaterialTheme.colorScheme.extended.neutral500
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ToteatDinerListPreview() {
    ToteatTheme {
        ToteatDinerList(
            diners = persistentListOf(
                "Andrés", "Antonia", "Baltazar", "Berna", "Bruno",
                "Camila", "Carlos", "Daniela", "Dorotea"
            ),
            onDinerClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
