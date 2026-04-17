package com.toteat.toteatds.components.cards

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
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
import com.toteat.toteatds.theme.NeutralGray100
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.PrimaryLight
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.subcategory_button_description
import org.jetbrains.compose.resources.stringResource

private val ButtonShape = RoundedCornerShape(16.dp)
private val ButtonPadding = 12.dp
private val ChevronSize = 20.dp

@Composable
fun ToteatSubcategoryButton(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    trailingIcon: @Composable () -> Unit,
    contentDescription: String? = null,
    testTag: String = ""
) {
    val description = contentDescription
        ?: stringResource(Res.string.subcategory_button_description, name)

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val containerColor = when {
        !enabled -> NeutralGray100
        isPressed -> NeutralGray100
        else -> NeutralGray
    }
    val iconTint = if (enabled) MaterialTheme.colorScheme.primary else NeutralGray300
    val textColor = when {
        !enabled -> NeutralGray300
        isPressed -> PrimaryLight
        else -> NeutralGray500
    }

    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                this.contentDescription = description
                this.role = Role.Button
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        enabled = enabled,
        shape = ButtonShape,
        color = containerColor,
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier.padding(ButtonPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: label
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
                    .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_name") else Modifier)
            )

            // Right: chevron
            CompositionLocalProvider(LocalContentColor provides iconTint) {
                Box(
                    modifier = Modifier
                        .size(ChevronSize)
                        .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_chevron") else Modifier),
                    contentAlignment = Alignment.Center
                ) {
                    trailingIcon()
                }
            }
        }
    }
}
