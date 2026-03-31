package com.toteat.toteatds.components.bottombar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.GreenNormal
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.SecondaryNormal
import com.toteat.toteatds.theme.TertiaryMuted
import com.toteat.toteatds.theme.TertiarySurface
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.bodyMediumRegular
import com.toteat.toteatds.theme.helperRegular
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.amount_bottom_bar_description
import designsystemmobile.toteatds.generated.resources.amount_paid
import designsystemmobile.toteatds.generated.resources.amount_to_pay
import designsystemmobile.toteatds.generated.resources.confirm
import designsystemmobile.toteatds.generated.resources.credit_card_icon
import designsystemmobile.toteatds.generated.resources.pan_fire_icon
import designsystemmobile.toteatds.generated.resources.pay
import designsystemmobile.toteatds.generated.resources.payment_detail
import designsystemmobile.toteatds.generated.resources.print_prebill
import designsystemmobile.toteatds.generated.resources.print_vector
import designsystemmobile.toteatds.generated.resources.subtotal
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val AmountBottomBarTopShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
private val AmountBottomBarPrintShape = RoundedCornerShape(14.dp)
private val AmountBottomBarActionShape = RoundedCornerShape(50)
private val AmountBottomBarTopHeight = 70.dp
private val AmountBottomBarBottomHeight = 77.dp
private val AmountBottomBarActionHeight = 45.dp
private val AmountBottomBarAmountWidth = 74.dp

@Composable
fun AmountBottomBar(
    subtotalAmount: String,
    amountToPay: String,
    paidAmount: String? = null,
    onPrintPreBillClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onPayClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    confirmEnabled: Boolean = enabled,
    payEnabled: Boolean = enabled,
    fullPaid: Boolean = false,
    onPaymentDetailClick: (() -> Unit)? = null,
    initialPaidAmountExpanded: Boolean = false,
    testTag: String = ""
) {
    val barDescription = stringResource(Res.string.amount_bottom_bar_description)
    val subtotalText = stringResource(Res.string.subtotal)
    val amountPaidText = stringResource(Res.string.amount_paid)
    val amountToPayText = stringResource(Res.string.amount_to_pay)
    val printPrebillText = stringResource(Res.string.print_prebill)
    val confirmText = stringResource(Res.string.confirm)
    val payText = stringResource(Res.string.pay)
    val paymentDetailText = stringResource(Res.string.payment_detail)
    var showPaidAmount by remember(paidAmount, initialPaidAmountExpanded) {
        mutableStateOf(initialPaidAmountExpanded)
    }

    val accessibilityDescription = remember(
        subtotalAmount,
        paidAmount,
        showPaidAmount,
        amountToPay,
        subtotalText,
        amountPaidText,
        amountToPayText
    ) {
        buildString {
            append("$subtotalText $subtotalAmount")
            if (!paidAmount.isNullOrBlank() && showPaidAmount) {
                append(", $amountPaidText $paidAmount")
            }
            append(", $amountToPayText $amountToPay")
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
        color = NeutralGray,
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .semantics { contentDescription = "$barDescription, $accessibilityDescription" },
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Surface(
                shape = AmountBottomBarTopShape,
                color = TertiarySurface,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AmountBottomBarTopHeight)
                    .then(
                        if (testTag.isNotEmpty()) {
                            Modifier.setTestTag("${testTag}_summary")
                        } else {
                            Modifier
                        }
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        onClick = onPrintPreBillClick,
                        enabled = enabled,
                        shape = AmountBottomBarPrintShape,
                        color = NeutralGray,
                        border = BorderStroke(1.dp, NeutralGray400),
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp,
                        modifier = Modifier
                            .width(40.dp)
                            .height(28.dp)
                            .semantics {
                                role = Role.Button
                                contentDescription = printPrebillText
                            }
                            .then(
                                if (testTag.isNotEmpty()) {
                                    Modifier.setTestTag("${testTag}_print_prebill")
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.print_vector),
                                contentDescription = null,
                                tint = NeutralGray500,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                AmountRow(
                                    label = subtotalText,
                                    amount = subtotalAmount,
                                    amountStyle = MaterialTheme.typography.bodySmall,
                                    amountColor = TertiaryMuted,
                                    labelStyle = MaterialTheme.typography.helperRegular,
                                    labelColor = TertiaryMuted,
                                    testTag = if (testTag.isNotEmpty()) "${testTag}_subtotal" else ""
                                )

                                if (!paidAmount.isNullOrBlank() && showPaidAmount) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    AmountRow(
                                        label = amountPaidText,
                                        amount = paidAmount,
                                        amountStyle = MaterialTheme.typography.bodySmall,
                                        amountColor = TertiaryMuted,
                                        labelStyle = MaterialTheme.typography.helperRegular,
                                        labelColor = TertiaryMuted,
                                        testTag = if (testTag.isNotEmpty()) "${testTag}_paid" else ""
                                    )
                                }

                                AmountRow(
                                    label = amountToPayText,
                                    amount = amountToPay,
                                    amountStyle = MaterialTheme.typography.bodyMedium,
                                    amountColor = MaterialTheme.colorScheme.secondary,
                                    labelStyle = MaterialTheme.typography.bodyMedium,
                                    labelColor = MaterialTheme.colorScheme.secondary,
                                    testTag = if (testTag.isNotEmpty()) "${testTag}_to_pay" else ""
                                )
                            }

                            if (!paidAmount.isNullOrBlank()) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .then(
                                            if (enabled) {
                                                Modifier.noRippleClick { showPaidAmount = !showPaidAmount }
                                            } else {
                                                Modifier
                                            }
                                        )
                                        .semantics {
                                            role = Role.Button
                                            contentDescription = amountPaidText
                                        }
                                        .then(
                                            if (testTag.isNotEmpty()) {
                                                Modifier.setTestTag("${testTag}_toggle_paid")
                                            } else {
                                                Modifier
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (showPaidAmount) {
                                            Icons.Default.KeyboardArrowDown
                                        } else {
                                            Icons.Default.KeyboardArrowUp
                                        },
                                        contentDescription = null,
                                        tint = TertiaryMuted,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AmountBottomBarBottomHeight)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AmountActionButton(
                    onClick = onConfirmClick,
                    enabled = confirmEnabled,
                    text = confirmText,
                    containerColor = SecondaryNormal,
                    contentColor = NeutralGray,
                    leadingIcon = {
                        Icon(
                            imageVector = vectorResource(Res.drawable.pan_fire_icon),
                            contentDescription = null,
                            tint = NeutralGray,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .then(
                            if (testTag.isNotEmpty()) {
                                Modifier.setTestTag("${testTag}_confirm")
                            } else {
                                Modifier
                            }
                        )
                )
                if (fullPaid) {
                    AmountActionButton(
                        onClick = { onPaymentDetailClick?.invoke() },
                        enabled = enabled,
                        text = paymentDetailText,
                        containerColor = GreenNormal,
                        contentColor = NeutralGray,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = NeutralGray,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .then(
                                if (testTag.isNotEmpty()) {
                                    Modifier.setTestTag("${testTag}_payment_detail")
                                } else {
                                    Modifier
                                }
                            )
                    )
                } else {
                    AmountActionButton(
                        onClick = onPayClick,
                        enabled = payEnabled,
                        text = payText,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = NeutralGray,
                        leadingIcon = {
                            Icon(
                                imageVector = vectorResource(Res.drawable.credit_card_icon),
                                contentDescription = null,
                                tint = NeutralGray,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .then(
                                if (testTag.isNotEmpty()) {
                                    Modifier.setTestTag("${testTag}_pay")
                                } else {
                                    Modifier
                                }
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun AmountActionButton(
    onClick: () -> Unit,
    enabled: Boolean,
    text: String,
    containerColor: Color,
    contentColor: Color,
    leadingIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        enabled = enabled,
        shape = AmountBottomBarActionShape,
        color = if (enabled) containerColor else NeutralGray300,
        contentColor = contentColor,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = modifier
            .height(AmountBottomBarActionHeight)
            .semantics {
                role = Role.Button
                contentDescription = text
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            leadingIcon()
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Deprecated(
    message = "Use AmountBottomBar instead",
    replaceWith = ReplaceWith(
        "AmountBottomBar(subtotalAmount, amountToPay, paidAmount, onPrintPreBillClick, onConfirmClick, onPayClick, modifier, enabled, confirmEnabled, payEnabled, fullPaid, onPaymentDetailClick, initialPaidAmountExpanded, testTag)"
    )
)
@Composable
fun AmountBotombar(
    subtotalAmount: String,
    amountToPay: String,
    paidAmount: String? = null,
    onPrintPreBillClick: () -> Unit,
    onConfirmClick: () -> Unit,
    onPayClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    confirmEnabled: Boolean = enabled,
    payEnabled: Boolean = enabled,
    fullPaid: Boolean = false,
    onPaymentDetailClick: (() -> Unit)? = null,
    initialPaidAmountExpanded: Boolean = false,
    testTag: String = ""
) = AmountBottomBar(
    subtotalAmount = subtotalAmount,
    amountToPay = amountToPay,
    paidAmount = paidAmount,
    onPrintPreBillClick = onPrintPreBillClick,
    onConfirmClick = onConfirmClick,
    onPayClick = onPayClick,
    modifier = modifier,
    enabled = enabled,
    confirmEnabled = confirmEnabled,
    payEnabled = payEnabled,
    fullPaid = fullPaid,
    onPaymentDetailClick = onPaymentDetailClick,
    initialPaidAmountExpanded = initialPaidAmountExpanded,
    testTag = testTag
)

@Composable
private fun AmountRow(
    label: String,
    amount: String,
    amountStyle: androidx.compose.ui.text.TextStyle,
    amountColor: androidx.compose.ui.graphics.Color,
    labelStyle: androidx.compose.ui.text.TextStyle,
    labelColor: Color,
    testTag: String = ""
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier)
    ) {
        Text(
            text = label,
            style = labelStyle,
            color = labelColor,
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier.width(AmountBottomBarAmountWidth),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = amount,
                style = amountStyle,
                color = amountColor,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AmountBottomBarPreviewWithPaidAmount() {
    ToteatTheme {
        Scaffold(
            bottomBar = {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    paidAmount = "$ 0",
                    amountToPay = "$32.780",
                    onPrintPreBillClick = {},
                    onConfirmClick = {},
                    onPayClick = {}
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AmountBottomBarPreviewFullPaid() {
    ToteatTheme {
        Scaffold(
            bottomBar = {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    paidAmount = "$ 32.780",
                    amountToPay = "$ 0",
                    fullPaid = true,
                    onPaymentDetailClick = {},
                    initialPaidAmountExpanded = true,
                    onPrintPreBillClick = {},
                    onConfirmClick = {},
                    onPayClick = {}
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AmountBottomBarPreviewWithoutPaidAmount() {
    ToteatTheme {
        Scaffold(
            bottomBar = {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    paidAmount = "$ 0",
                    amountToPay = "$32.780",
                    initialPaidAmountExpanded = true,
                    onPrintPreBillClick = {},
                    onConfirmClick = {},
                    onPayClick = {}
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }
}
