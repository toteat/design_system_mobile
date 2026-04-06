package com.toteat.toteatds.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.toteat.toteatds.components.icons.DifferentAmountPaymentsIcon
import com.toteat.toteatds.components.icons.SplitPaymentIcon
import com.toteat.toteatds.theme.BlueLight
import com.toteat.toteatds.theme.DarkBlue
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.coming_soon
import designsystemmobile.toteatds.generated.resources.square_button_description
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToteatSquareButton(
    title: String,
    subTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    comingSoon: Boolean = false,
    testTag: String = "",
    iconSize: Dp = 48.dp,
    icon: @Composable () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val targetBorderColor =
        if (pressed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val borderColor by animateColorAsState(targetValue = targetBorderColor, label = "squareBorder")

    val description = stringResource(Res.string.square_button_description, title, subTitle)
    val comingSoonText = stringResource(Res.string.coming_soon)

    // Cache static modifiers
    val spacerModifier = remember { Modifier.height(8.dp) }

    OutlinedCard(
        onClick = onClick,
        enabled = true,
        modifier = modifier
            .heightIn(min = if (comingSoon) 128.dp else 0.dp, max = 128.dp)
            .width(150.dp)
            .semantics {
                contentDescription = if (comingSoon) "$description, $comingSoonText" else description
                role = Role.Button
            }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        interactionSource = interactionSource,
        colors = CardDefaults
            .cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, if (comingSoon) NeutralGray300 else borderColor)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = if (comingSoon) 36.dp else 14.dp,
                        bottom = if (comingSoon) 6.dp else 14.dp,
                        start = 14.dp,
                        end = 14.dp
                    )
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier.size(iconSize),
                    contentAlignment = Alignment.Center
                ) {
                    icon()
                }
                Spacer(modifier = if (comingSoon) Modifier.height(2.dp) else spacerModifier)
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
                    textAlign = TextAlign.Start,
                    softWrap = true,
                    maxLines = Int.MAX_VALUE
                )
            }

            if (comingSoon) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = comingSoonText,
                        color = DarkBlue,
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .background(
                                color = BlueLight,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .then(
                                if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_coming_soon") else Modifier
                            )
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun ToteatSquareButtonPreview() {
    ToteatTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToteatSquareButton(
                title = "Dividir pago",
                subTitle = "En partes iguales",
                icon = {
                    SplitPaymentIcon(
                        modifier = Modifier.size(48.dp)
                    )
                },
                onClick = {}
            )
            ToteatSquareButton(
                title = "Pagos por",
                subTitle = "montos diferentes",
                icon = {
                    DifferentAmountPaymentsIcon(
                        modifier = Modifier.size(48.dp)
                    )
                },
                onClick = {}
            )
            ToteatSquareButton(
                title = "Pago por",
                subTitle = "monto especifico",
                comingSoon = true,
                iconSize = 36.dp,
                icon = {
                    DifferentAmountPaymentsIcon(
                        modifier = Modifier.size(36.dp)
                    )
                },
                onClick = {}
            )
        }
    }
}