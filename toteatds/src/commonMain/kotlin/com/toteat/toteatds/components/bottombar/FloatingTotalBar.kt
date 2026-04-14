package com.toteat.toteatds.components.bottombar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import org.jetbrains.compose.ui.tooling.preview.Preview

private val FloatingTotalBarShape = androidx.compose.foundation.shape.RoundedCornerShape(32.dp)
private val FloatingTotalBarMinHeight = 56.dp

@Composable
fun FloatingTotalBar(
    totalAmount: String,
    label: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    testTag: String = ""
) {
    val accessibilityDescription = remember(label, totalAmount) {
        if (label.isNullOrBlank()) totalAmount else "$label, $totalAmount"
    }

    val containerColor = if (enabled) MaterialTheme.colorScheme.primary else NeutralGray300
    val contentColor = if (enabled) MaterialTheme.colorScheme.onPrimary else NeutralGray

    Surface(
        onClick = onClick,
        enabled = enabled,
        shape = FloatingTotalBarShape,
        color = containerColor,
        contentColor = contentColor,
        shadowElevation = 8.dp,
        tonalElevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = FloatingTotalBarMinHeight)
            .padding(horizontal = 16.dp)
            .semantics {
                role = Role.Button
                contentDescription = accessibilityDescription
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = FloatingTotalBarMinHeight)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!label.isNullOrBlank()) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    color = contentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .then(
                            if (testTag.isNotEmpty()) {
                                Modifier.setTestTag("${testTag}_label")
                            } else {
                                Modifier
                            }
                        )
                )
            } else {
                Box(modifier = Modifier.weight(1f))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = totalAmount,
                    style = MaterialTheme.typography.bodyLarge,
                    color = contentColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.then(
                        if (testTag.isNotEmpty()) {
                            Modifier.setTestTag("${testTag}_amount")
                        } else {
                            Modifier
                        }
                    )
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier
                        .size(10.dp)
                        .then(
                            if (testTag.isNotEmpty()) {
                                Modifier.setTestTag("${testTag}_icon")
                            } else {
                                Modifier
                            }
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun FloatingTotalBarPreviewWithLabel() {
    ToteatTheme {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                Box(
                    modifier = Modifier.fillMaxSize()
                )


                FloatingTotalBar(
                    totalAmount = "$ 29.290",
                    label = "Ver pedido mesa",
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .navigationBarsPadding()
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun FloatingTotalBarPreviewDisabled() {
    ToteatTheme {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                Box(
                    modifier = Modifier.fillMaxSize()
                )

                FloatingTotalBar(
                    totalAmount = "$ 29.290",
                    label = "Ver pedido mesa",
                    onClick = {},
                    enabled = false,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .navigationBarsPadding()
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun FloatingTotalBarPreviewWithoutLabel() {
    ToteatTheme {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                Box(
                    modifier = Modifier.fillMaxSize()
                )

                FloatingTotalBar(
                    totalAmount = "$ 29.290",
                    label = null,
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .navigationBarsPadding()
                )
            }
        }
    }
}
