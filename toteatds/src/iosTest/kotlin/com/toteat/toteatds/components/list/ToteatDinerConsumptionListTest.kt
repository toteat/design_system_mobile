package com.toteat.toteatds.components.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.toteat.toteatds.theme.ToteatTheme
import kotlinx.collections.immutable.persistentListOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// setTestTag prefixes every tag with "toteat:id/".
private fun tag(name: String) = "toteat:id/$name"

@OptIn(ExperimentalTestApi::class)
class ToteatDinerConsumptionListTest {

    @Test
    fun disabledCheckboxDoesNotReportChanges() = runComposeUiTest {
        val changes = mutableListOf<Pair<Int, Boolean>>()
        setContent {
            ToteatTheme {
                ToteatDinerConsumptionList(
                    diners = persistentListOf(
                        DinerConsumption(
                            name = "Vale",
                            items = persistentListOf(),
                            total = "$0",
                            isSelectable = false
                        )
                    ),
                    onCheckedChange = { index, checked -> changes += index to checked },
                    testTag = "diners"
                )
            }
        }

        onNodeWithTag(tag("diners_card_0_checkbox"))
            .assertIsNotEnabled()
            .performClick()

        assertEquals(emptyList(), changes)
    }

    @Test
    fun selectableCheckboxReportsChanges() = runComposeUiTest {
        val changes = mutableListOf<Pair<Int, Boolean>>()
        setContent {
            ToteatTheme {
                ToteatDinerConsumptionList(
                    diners = persistentListOf(
                        DinerConsumption(name = "Pauli", items = persistentListOf(), total = "$0")
                    ),
                    onCheckedChange = { index, checked -> changes += index to checked },
                    testTag = "diners"
                )
            }
        }

        onNodeWithTag(tag("diners_card_0_checkbox"))
            .assertIsEnabled()
            .performClick()

        assertEquals(listOf(0 to true), changes)
    }

    @Test
    fun statusLabelIsShownAsPaidTagWithItsTestTag() = runComposeUiTest {
        setContent {
            ToteatTheme {
                ToteatDinerConsumptionCard(
                    consumption = DinerConsumption(
                        name = "Vale",
                        items = persistentListOf(),
                        total = "$0",
                        statusLabel = "Sin deuda",
                        isSelectable = false
                    ),
                    onCheckedChange = {},
                    testTag = "diner"
                )
            }
        }

        // The clickable header merges its children's semantics, so the label lives in the unmerged tree.
        onNodeWithTag(tag("diner_status"), useUnmergedTree = true)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Estado: Sin deuda")
        onNodeWithTag(tag("diner_status_text"), useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextEquals("Sin deuda")
    }

    @Test
    fun discountRowIsRendered() = runComposeUiTest {
        setContent {
            ToteatTheme {
                ToteatDinerConsumptionCard(
                    consumption = DinerConsumption(
                        name = "Pauli",
                        items = persistentListOf(
                            DinerConsumptionItem(name = "Hamburguesa BBQ", price = "$9.000"),
                            DinerConsumptionItem(
                                name = "Descuento 10%",
                                price = "-$900",
                                kind = DinerConsumptionItemKind.Discount
                            )
                        ),
                        total = "$8.100",
                        isChecked = true
                    ),
                    onCheckedChange = {},
                    initiallyExpanded = true,
                    testTag = "diner"
                )
            }
        }

        onNodeWithTag(tag("diner_item_1")).assertIsDisplayed()
        onNodeWithText("Descuento 10%").assertIsDisplayed()
        onNodeWithText("-$900").assertIsDisplayed()
    }

    @Test
    fun quantityAndExtrasAreShownUnderTheirProduct() = runComposeUiTest {
        setContent {
            ToteatTheme {
                ToteatDinerConsumptionCard(
                    consumption = DinerConsumption(
                        name = "Pauli",
                        items = persistentListOf(
                            DinerConsumptionItem(
                                name = "Empanada de pino",
                                price = "$15.600",
                                quantity = "6",
                                extras = persistentListOf(
                                    DinerConsumptionExtra(name = "Pebre", price = "$600"),
                                    DinerConsumptionExtra(name = "Salsa picante", price = "$0")
                                )
                            )
                        ),
                        total = "$16.200"
                    ),
                    onCheckedChange = {},
                    initiallyExpanded = true,
                    testTag = "diner"
                )
            }
        }

        onNodeWithTag(tag("diner_item_0_quantity"))
            .assertIsDisplayed()
            .assertTextEquals("x6")
        onNodeWithTag(tag("diner_item_0_extra_0")).assertIsDisplayed()
        onNodeWithTag(tag("diner_item_0_extra_1")).assertIsDisplayed()
        onNodeWithText("Pebre").assertIsDisplayed()
        onNodeWithText("$600").assertIsDisplayed()
        onNodeWithText("Salsa picante").assertIsDisplayed()
        onNodeWithText("$0").assertIsDisplayed()

        // Extras sit under their product row, not beside it.
        val productBottom = onNodeWithTag(tag("diner_item_0")).fetchSemanticsNode().boundsInRoot.bottom
        val extraTop = onNodeWithTag(tag("diner_item_0_extra_0")).fetchSemanticsNode().boundsInRoot.top
        assertTrue(extraTop >= productBottom)
    }

    @Test
    fun itemWithoutQuantityOrExtrasShowsNeither() = runComposeUiTest {
        setContent {
            ToteatTheme {
                ToteatDinerConsumptionCard(
                    consumption = DinerConsumption(
                        name = "Pauli",
                        items = persistentListOf(
                            DinerConsumptionItem(name = "Hamburguesa BBQ", price = "$9.000")
                        ),
                        total = "$9.000"
                    ),
                    onCheckedChange = {},
                    initiallyExpanded = true,
                    testTag = "diner"
                )
            }
        }

        onNodeWithTag(tag("diner_item_0")).assertIsDisplayed()
        onNodeWithTag(tag("diner_item_0_quantity")).assertDoesNotExist()
        onNodeWithTag(tag("diner_item_0_extra_0")).assertDoesNotExist()
    }
}
