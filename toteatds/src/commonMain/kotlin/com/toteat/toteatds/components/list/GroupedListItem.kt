package com.toteat.toteatds.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GroupedListItem(
    label: @Composable () -> Unit,
    value: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    labelBackgroundColor: Color = MaterialTheme.colorScheme.extended.neutral100,
    valueBackgroundColor: Color = Color.White,
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(0.4f)
                .background(labelBackgroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            label()
        }

        Box(
            modifier = Modifier
                .weight(0.6f)
                .background(valueBackgroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp),
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
    items: List<@Composable () -> Unit>
) {
    val shape = ListItemPosition.Single.getShape(cornerRadius)

    Column(
        modifier = modifier
            .clip(shape)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = shape
            )
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
fun GroupedListItemSinglePreview() {
    ToteatTheme {
        GroupedList(
            modifier = Modifier.padding(16.dp),
            items = listOf(
                {
                    GroupedListItem(
                        label = {
                            Text(
                                text = "Mesa",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
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
                }
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun GroupedListPreview() {
    ToteatTheme {
        GroupedList(
            modifier = Modifier.padding(16.dp),
            items = listOf(
                {
                    GroupedListItem(
                        label = {
                            Text(
                                text = "Mesa",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
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
