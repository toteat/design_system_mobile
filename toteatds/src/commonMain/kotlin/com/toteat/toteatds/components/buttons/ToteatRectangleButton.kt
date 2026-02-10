package com.toteat.toteatds.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.icons.TotalPaymentsIcon
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.rectangle_button_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatRectangleButton(
    title: String,
    subTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "",
    icon: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val targetBorderColor =
        if (pressed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val borderColor by animateColorAsState(targetValue = targetBorderColor, label = "rectBorder")
    val description = stringResource(Res.string.rectangle_button_description, title, subTitle)

    OutlinedCard(
        onClick = onClick,
        modifier = modifier
            .heightIn(max = 128.dp)
            .fillMaxWidth()
            .semantics {
                contentDescription = description
                role = Role.Button
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        interactionSource = interactionSource,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 14.dp, horizontal = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            icon()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.fillMaxWidth()
                    .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_title") else Modifier),
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                softWrap = true
            )
            Text(
                modifier = Modifier.fillMaxWidth()
                    .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_subtitle") else Modifier),
                text = subTitle,
                style = MaterialTheme.typography.bodyLargeRegular,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                softWrap = true,
                maxLines = Int.MAX_VALUE
            )
        }
    }
}

@Composable
@Preview
private fun ToteatRectangleButtonPreview() {
    ToteatTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToteatRectangleButton(
                title = "Pago total",
                subTitle = "de la cuenta",
                icon = {
                    TotalPaymentsIcon(
                        modifier = Modifier.size(48.dp)
                    )
                },
                onClick = {}
            )
        }
    }
}