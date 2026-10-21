package com.toteat.toteatds.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.toteat.toteatds.components.icons.DifferentAmountPaymentsIcon
import com.toteat.toteatds.components.icons.DinerPaymentIcon
import com.toteat.toteatds.components.icons.SplitPaymentIcon
import com.toteat.toteatds.components.icons.TotalPaymentsIcon
import com.toteat.toteatds.theme.BlueLight
import com.toteat.toteatds.theme.DarkBlue
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyLargeRegular
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.coming_soon
import designsystemmobile.toteatds.generated.resources.square_button_description
import designsystemmobile.toteatds.generated.resources.square_button_unavailable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * @param onDisabledClick Called when the card is tapped while [enabled] is `false`, so the app can
 * explain why the option is not available. The card keeps its disabled look, shows no pressed
 * border nor ripple, and announces "No disponible" while staying actionable for screen readers.
 * `null` (default) keeps a disabled card unresponsive. Ignored while [enabled] is `true`.
 */
@Composable
fun ToteatSquareButton(
    title: String,
    subTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    comingSoon: Boolean = false,
    enabled: Boolean = true,
    onDisabledClick: (() -> Unit)? = null,
    testTag: String = "",
    iconSize: Dp = 48.dp,
    icon: @Composable () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val targetBorderColor =
        if (pressed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val borderColor by animateColorAsState(targetValue = targetBorderColor, label = "squareBorder")
    val contentColor = if (enabled) MaterialTheme.colorScheme.secondary else NeutralGray400

    val description = stringResource(Res.string.square_button_description, title, subTitle)
    val comingSoonText = stringResource(Res.string.coming_soon)
    val unavailableText = stringResource(Res.string.square_button_unavailable)

    // Cache static modifiers
    val spacerModifier = remember { Modifier.height(8.dp) }

    val cardModifier = modifier
        .heightIn(min = 128.dp, max = 128.dp)
        .width(150.dp)
        .semantics {
            contentDescription = if (comingSoon) "$description, $comingSoonText" else description
            role = Role.Button
        }
        .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    val cardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    val border = BorderStroke(1.dp, if (comingSoon || !enabled) NeutralGray300 else borderColor)

    val content: @Composable ColumnScope.() -> Unit = {
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
                    modifier = Modifier.size(iconSize).alpha(if (enabled) 1f else 0.4f),
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
                    color = contentColor,
                    textAlign = TextAlign.Center,
                    softWrap = true
                )
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .then(if (testTag.isNotEmpty()) Modifier.setTestTag("${testTag}_subtitle") else Modifier),
                    text = subTitle,
                    style = MaterialTheme.typography.bodyLargeRegular,
                    color = contentColor,
                    textAlign = TextAlign.Center,
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

    if (!enabled && onDisabledClick != null) {
        // Same look as a disabled card, but tappable: no indication, so no ripple, and the card's
        // own interaction source is not used, so the border never turns primary.
        // No disabled() semantics: Android drops the click action of disabled nodes, so TalkBack
        // could never reach onDisabledClick. The state is announced through stateDescription instead.
        OutlinedCard(
            modifier = cardModifier
                .clickable(
                    interactionSource = null,
                    indication = null,
                    role = Role.Button,
                    onClick = onDisabledClick
                )
                .semantics { stateDescription = unavailableText },
            colors = CardDefaults.cardColors(
                containerColor = cardColors.disabledContainerColor,
                contentColor = cardColors.disabledContentColor
            ),
            border = border,
            content = content
        )
    } else {
        OutlinedCard(
            onClick = onClick,
            enabled = enabled,
            modifier = cardModifier,
            interactionSource = interactionSource,
            colors = cardColors,
            border = border,
            content = content
        )
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
                title = "Pago total",
                subTitle = "de la cuenta",
                icon = { TotalPaymentsIcon(size = 48.dp) },
                onClick = {}
            )
            ToteatSquareButton(
                title = "Pago por",
                subTitle = "comensal",
                icon = { DinerPaymentIcon(size = 48.dp) },
                onClick = {}
            )
            ToteatSquareButton(
                title = "Pago",
                subTitle = "dividido",
                icon = { SplitPaymentIcon(size = 48.dp) },
                onClick = {}
            )
            // Subtitle wrapping to two lines
            ToteatSquareButton(
                title = "Pago por",
                subTitle = "montos diferentes entre comensales",
                icon = { DifferentAmountPaymentsIcon(size = 48.dp) },
                onClick = {}
            )
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
            ToteatSquareButton(
                title = "Pago por",
                subTitle = "comensal",
                enabled = false,
                onDisabledClick = {},
                icon = { DinerPaymentIcon(size = 48.dp) },
                onClick = {}
            )
            ToteatSquareButton(
                title = "Pago por",
                subTitle = "monto especifico",
                enabled = false,
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