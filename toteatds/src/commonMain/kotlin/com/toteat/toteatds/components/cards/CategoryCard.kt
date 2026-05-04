package com.toteat.toteatds.components.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.category_card_description
import org.jetbrains.compose.resources.stringResource

private val CardHeight = 72.dp
private val CardWidth = 160.dp
private val VerticalPadding = 12.dp
private val HorizontalPadding = 8.dp

@Composable
fun ToteatCategoryCard(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String? = null,
    testTag: String = ""
) {
    val description = contentDescription ?: stringResource(Res.string.category_card_description, name)

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val containerColor = when {
        !enabled -> MaterialTheme.colorScheme.surfaceVariant
        isPressed -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.background
    }
    val textColor = if (enabled) MaterialTheme.colorScheme.onBackground
        else MaterialTheme.colorScheme.extended.disabledContent

    Surface(
        onClick = onClick,
        modifier = modifier
            .width(CardWidth)
            .height(CardHeight)
            .semantics {
                this.contentDescription = description
                this.role = Role.Button
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        enabled = enabled,
        shape = MaterialTheme.shapes.large,
        color = containerColor,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        interactionSource = interactionSource
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = VerticalPadding, horizontal = HorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_name") else Modifier
            )
        }
    }
}
