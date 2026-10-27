package com.toteat.toteatds.components.list

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import kotlinx.collections.immutable.persistentListOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

// setTestTag prefixes every tag with "toteat:id/".
private fun tag(name: String) = "toteat:id/$name"

// Color the Text was laid out with, read from its text layout instead of from pixels.
private fun textColor(node: SemanticsNodeInteraction): Color {
    val results = mutableListOf<TextLayoutResult>()
    node.fetchSemanticsNode().config[SemanticsActions.GetTextLayoutResult].action?.invoke(results)
    return results.single().layoutInput.style.color
}

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
    fun totalRowIsPaintedWithPrimary() = runComposeUiTest {
        var primary = Color.Unspecified
        var secondary = Color.Unspecified
        setContent {
            ToteatTheme {
                primary = MaterialTheme.colorScheme.primary
                secondary = MaterialTheme.colorScheme.secondary
                ToteatDinerConsumptionCard(
                    consumption = DinerConsumption(
                        name = "Pauli",
                        items = persistentListOf(
                            DinerConsumptionItem(name = "Hamburguesa BBQ", price = "$9.000")
                        ),
                        total = "$8.100"
                    ),
                    onCheckedChange = {},
                    initiallyExpanded = true,
                    testTag = "diner"
                )
            }
        }

        val inTotalRow = hasAnyAncestor(hasTestTag(tag("diner_total")))
        assertEquals(primary, textColor(onNode(hasText("•") and inTotalRow)))
        assertEquals(primary, textColor(onNode(hasText("Total") and inTotalRow)))
        assertEquals(primary, textColor(onNode(hasText("$8.100") and inTotalRow)))
        // Regular rows keep their gray, not the total's primary.
        assertNotEquals(primary, textColor(onNodeWithText("Hamburguesa BBQ")))
        assertNotEquals(secondary, primary)
    }

    @Test
    fun checkboxKeepsA48dpTouchTarget() = runComposeUiTest {
        setContent {
            ToteatTheme {
                ToteatDinerConsumptionCard(
                    consumption = DinerConsumption(name = "Pauli", items = persistentListOf(), total = "$0"),
                    onCheckedChange = {},
                    testTag = "diner"
                )
            }
        }

        val checkbox = onNodeWithTag(tag("diner_checkbox"))
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Checkbox))
            .assertContentDescriptionEquals("Seleccionar a Pauli")

        // minimumInteractiveComponentSize widens the touch bounds, not the node's layout bounds.
        val touchBounds = checkbox.fetchSemanticsNode().touchBoundsInRoot
        val minTouchPx = with(density) { 48.dp.toPx() }
        assertTrue(touchBounds.width >= minTouchPx, "touch width ${touchBounds.width}px < $minTouchPx px")
        assertTrue(touchBounds.height >= minTouchPx, "touch height ${touchBounds.height}px < $minTouchPx px")
    }

    @Test
    fun chevronDescriptionFollowsTheExpandedState() = runComposeUiTest {
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
                    testTag = "diner"
                )
            }
        }

        onNodeWithContentDescription("Ver consumo", useUnmergedTree = true).assertIsDisplayed()
        onNodeWithTag(tag("diner_total")).assertDoesNotExist()

        onNodeWithTag(tag("diner_header")).performClick()

        onNodeWithContentDescription("Ocultar consumo", useUnmergedTree = true).assertIsDisplayed()
        onNodeWithTag(tag("diner_total")).assertIsDisplayed()
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
