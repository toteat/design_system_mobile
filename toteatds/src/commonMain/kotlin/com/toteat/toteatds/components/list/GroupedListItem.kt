package com.toteat.toteatds.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.grouped_list_description
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GroupedListItem(
    modifier: Modifier = Modifier,
    labelBackgroundColor: Color = MaterialTheme.colorScheme.extended.neutral100,
    valueBackgroundColor: Color = Color.White,
    testTag: String = "",
    contentDescription: String? = null,
    label: @Composable () -> Unit,
    value: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
                .background(labelBackgroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_label") else Modifier),
            contentAlignment = Alignment.TopStart
        ) {
            label()
        }

        Box(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
                .background(valueBackgroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_value") else Modifier),
            contentAlignment = Alignment.CenterStart
        ) {
            value()
        }
    }
}

@Composable
fun GroupedList(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    borderWidth: Dp = 1.dp,
    testTag: String = "",
    contentDescription: String? = null,
    items: ImmutableList<@Composable () -> Unit>
) {
    val shape = ListItemPosition.Single.getShape(cornerRadius)
    val defaultDescription = stringResource(Res.string.grouped_list_description)
    val description = contentDescription ?: defaultDescription

    Column(
        modifier = modifier
            .clip(shape)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = shape
            )
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
            .semantics { this.contentDescription = description }
    ) {
        items.forEachIndexed { index, item ->
            item()

            if (index < items.size - 1) {
                HorizontalDivider(
                    color = borderColor,
                    thickness = borderWidth
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun GroupedListItemSinglePreview() {
    ToteatTheme {
        GroupedListItem(
            label = {
                Column {
                    Text(
                        text = "Número",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "de orden",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                }
            },
            value = {
                Text(
                    text = "01234567890",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun GroupedListPreview() {
    ToteatTheme {
        GroupedList(
            modifier = Modifier.padding(16.dp),
            items = persistentListOf(
                {
                    GroupedListItem(
                        label = {
                            Column {
                                Text(
                                    text = "Número",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "de orden",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Light,
                                    color = Color.Black
                                )
                            }
                        },
                        value = {
                            Text(
                                text = "Mesa B1",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        }
                    )
                },
                {
                    GroupedListItem(
                        label = {
                            Text(
                                text = "Estado",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        },
                        value = {
                            Text(
                                text = "En preparación",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        }
                    )
                },
                {
                    GroupedListItem(
                        label = {
                            Text(
                                text = "Sector",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        },
                        value = {
                            Text(
                                text = "Salón",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        }
                    )
                },
                {
                    GroupedListItem(
                        label = {
                            Text(
                                text = "Garzón",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        },
                        value = {
                            Text(
                                text = "Javier Díaz",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        }
                    )
                }
            )
        )
    }
}
