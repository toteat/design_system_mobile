package com.toteat.toteatds.components.bottombar

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import kotlin.test.Test
import kotlin.test.assertEquals

private const val ConfirmTag = "toteat:id/bar_confirm"
private const val PayTag = "toteat:id/bar_pay"
private const val PaymentDetailTag = "toteat:id/bar_payment_detail"
private const val SubtotalTag = "toteat:id/bar_subtotal"
private const val PaidTag = "toteat:id/bar_paid"
private const val ToggleTag = "toteat:id/bar_toggle_paid"

@OptIn(ExperimentalTestApi::class)
class AmountBottomBarTest {

    @Test
    fun defaultBarShowsConfirmAndPayButtons() = runComposeUiTest {
        setContent {
            ToteatTheme {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    amountToPay = "$ 32.780",
                    onPrintPreBillClick = {},
                    onConfirmClick = {},
                    onPayClick = {},
                    testTag = "bar"
                )
            }
        }

        onNodeWithTag(ConfirmTag).assertExists()
        onNodeWithTag(PayTag).assertExists()
        onNodeWithTag(PaymentDetailTag).assertDoesNotExist()
    }

    @Test
    fun withoutPayButtonAnUnpaidOrderShowsOnlyConfirm() = runComposeUiTest {
        var confirms = 0
        setContent {
            ToteatTheme {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    amountToPay = "$ 32.780",
                    onPrintPreBillClick = {},
                    onConfirmClick = { confirms++ },
                    onPayClick = {},
                    showPayButton = false,
                    testTag = "bar"
                )
            }
        }

        onNodeWithTag(PayTag).assertDoesNotExist()
        onNodeWithTag(PaymentDetailTag).assertDoesNotExist()
        onNodeWithTag(ConfirmTag).assertExists().performClick()

        assertEquals(1, confirms)
    }

    @Test
    fun withoutPayButtonAPaidOrderShowsConfirmAndPaymentDetail() = runComposeUiTest {
        var confirms = 0
        var details = 0
        setContent {
            ToteatTheme {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    amountToPay = "$ 0",
                    onPrintPreBillClick = {},
                    onConfirmClick = { confirms++ },
                    onPayClick = {},
                    fullPaid = true,
                    onPaymentDetailClick = { details++ },
                    showPayButton = false,
                    testTag = "bar"
                )
            }
        }

        onNodeWithTag(PayTag).assertDoesNotExist()
        onNodeWithTag(ConfirmTag).assertExists().performClick()
        onNodeWithTag(PaymentDetailTag).assertExists().performClick()

        assertEquals(1, confirms)
        assertEquals(1, details)
    }

    @Test
    fun aPaidOrderShowsConfirmAndPaymentDetailEvenWithPayButtonEnabled() = runComposeUiTest {
        setContent {
            ToteatTheme {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    amountToPay = "$ 0",
                    onPrintPreBillClick = {},
                    onConfirmClick = {},
                    onPayClick = {},
                    fullPaid = true,
                    onPaymentDetailClick = {},
                    testTag = "bar"
                )
            }
        }

        onNodeWithTag(PayTag).assertDoesNotExist()
        onNodeWithTag(ConfirmTag).assertExists()
        onNodeWithTag(PaymentDetailTag).assertExists()
    }

    @Test
    fun tappingTheAmountsTogglesTheExpandedDetail() = runComposeUiTest {
        setContent {
            ToteatTheme {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    paidAmount = "$ 0",
                    amountToPay = "$ 32.780",
                    onPrintPreBillClick = {},
                    onConfirmClick = {},
                    onPayClick = {},
                    testTag = "bar"
                )
            }
        }

        onNodeWithTag(PaidTag).assertDoesNotExist()

        onNodeWithTag(SubtotalTag).performClick()
        onNodeWithTag(PaidTag).assertExists()

        onNodeWithTag(SubtotalTag).performClick()
        onNodeWithTag(PaidTag).assertDoesNotExist()
    }

    @Test
    fun theToggleIsAnAccessibleButtonThatAnnouncesItsState() = runComposeUiTest {
        setContent {
            ToteatTheme {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    paidAmount = "$ 0",
                    amountToPay = "$ 32.780",
                    onPrintPreBillClick = {},
                    onConfirmClick = {},
                    onPayClick = {},
                    testTag = "bar"
                )
            }
        }

        onNodeWithTag(ToggleTag)
            .assertHasClickAction()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Contraído"))
            .performClick()

        onNodeWithTag(ToggleTag)
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Expandido"))
    }

    @Test
    fun theToggleMeetsTheMinimumTouchTarget() = runComposeUiTest {
        setContent {
            ToteatTheme {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    paidAmount = "$ 0",
                    amountToPay = "$ 32.780",
                    onPrintPreBillClick = {},
                    onConfirmClick = {},
                    onPayClick = {},
                    testTag = "bar"
                )
            }
        }

        onNodeWithTag(ToggleTag)
            .assertHeightIsAtLeast(48.dp)
            .assertWidthIsAtLeast(48.dp)
    }

    @Test
    fun withoutExtraRowsThereIsNoToggle() = runComposeUiTest {
        setContent {
            ToteatTheme {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    amountToPay = "$ 32.780",
                    onPrintPreBillClick = {},
                    onConfirmClick = {},
                    onPayClick = {},
                    testTag = "bar"
                )
            }
        }

        onNodeWithTag(ToggleTag).assertDoesNotExist()
    }

    @Test
    fun aDisabledBarIgnoresTapsOnTheAmounts() = runComposeUiTest {
        setContent {
            ToteatTheme {
                AmountBottomBar(
                    subtotalAmount = "$ 32.780",
                    paidAmount = "$ 0",
                    amountToPay = "$ 32.780",
                    onPrintPreBillClick = {},
                    onConfirmClick = {},
                    onPayClick = {},
                    enabled = false,
                    testTag = "bar"
                )
            }
        }

        onNodeWithTag(SubtotalTag).performClick()

        onNodeWithTag(PaidTag).assertDoesNotExist()
    }
}
