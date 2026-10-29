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
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.theme.extended
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
            .assertTextEquals("6x")
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
    fun quantitiesAreShownBeforeTheNameIncludingOneUnitAndExtras() = runComposeUiTest {
        var pending = Color.Unspecified
        setContent {
            ToteatTheme {
                pending = MaterialTheme.colorScheme.secondary
                ToteatDinerConsumptionCard(
                    consumption = DinerConsumption(
                        name = "Pauli",
                        items = persistentListOf(
                            DinerConsumptionItem(
                                name = "Empanada de pino",
                                price = "$15.600",
                                quantity = "6",
                                extras = persistentListOf(
                                    DinerConsumptionExtra(name = "Pebre", price = "$600", quantity = "2"),
                                    DinerConsumptionExtra(name = "Salsa picante", price = "$0")
                                )
                            ),
                            DinerConsumptionItem(name = "Mojito tradicional", price = "$7.000", quantity = "1")
                        ),
                        total = "$23.200"
                    ),
                    onCheckedChange = {},
                    initiallyExpanded = true,
                    testTag = "diner"
                )
            }
        }

        fun left(node: SemanticsNodeInteraction) = node.fetchSemanticsNode().boundsInRoot.left

        val productQuantity = onNodeWithTag(tag("diner_item_0_quantity")).assertTextEquals("6x")
        assertTrue(left(productQuantity) < left(onNodeWithText("Empanada de pino")))

        onNodeWithTag(tag("diner_item_1_quantity")).assertTextEquals("1x")

        val extraQuantity = onNodeWithTag(tag("diner_item_0_extra_0_quantity"), useUnmergedTree = true)
            .assertTextEquals("2x")
        assertTrue(left(extraQuantity) < left(onNodeWithText("Pebre")))
        // Extra quantities line up with the product's.
        assertEquals(left(productQuantity), left(extraQuantity))

        // In the row's tone like the name (pending here), not primary.
        assertEquals(pending, textColor(productQuantity))
        assertEquals(pending, textColor(extraQuantity))

        onNodeWithTag(tag("diner_item_0_extra_1_quantity"), useUnmergedTree = true).assertDoesNotExist()
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
    fun paidProductsShowAStatusTagAndTheirExtrasFollowTheProductTone() = runComposeUiTest {
        var gray = Color.Unspecified
        var pending = Color.Unspecified
        setContent {
            ToteatTheme {
                gray = MaterialTheme.colorScheme.extended.neutral400
                pending = MaterialTheme.colorScheme.secondary
                ToteatDinerConsumptionCard(
                    consumption = DinerConsumption(
                        name = "Pauli",
                        items = persistentListOf(
                            DinerConsumptionItem(
                                name = "Empanada de pino",
                                price = "$15.600",
                                quantity = "6",
                                statusLabel = "Pagado",
                                extras = persistentListOf(
                                    DinerConsumptionExtra(name = "Pebre", price = "$600")
                                )
                            ),
                            DinerConsumptionItem(
                                name = "Mojito tradicional",
                                price = "$7.000",
                                extras = persistentListOf(
                                    DinerConsumptionExtra(name = "Extra menta", price = "$500")
                                )
                            ),
                            DinerConsumptionItem(
                                name = "Descuento",
                                price = "-$200",
                                kind = DinerConsumptionItemKind.Discount
                            )
                        ),
                        total = "$7.300"
                    ),
                    onCheckedChange = {},
                    initiallyExpanded = true,
                    testTag = "diner"
                )
            }
        }

        // Rows with a tag merge their children, so the tag lives in the unmerged tree.
        onNodeWithTag(tag("diner_item_0_status"), useUnmergedTree = true)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Estado: Pagado")
        onNodeWithTag(tag("diner_item_1_status"), useUnmergedTree = true).assertDoesNotExist()
        // Extras never show a tag: only their product does.
        onNodeWithTag(tag("diner_item_0_extra_0_status"), useUnmergedTree = true).assertDoesNotExist()
        onAllNodesWithContentDescription("Estado: Pagado", useUnmergedTree = true).assertCountEquals(1)

        // The paid row is read as one node that includes the tag.
        onNodeWithTag(tag("diner_item_0"))
            .assertContentDescriptionContains("Estado: Pagado")
            .assert(hasText("Empanada de pino") and hasText("$15.600"))

        fun color(text: String) = textColor(onNodeWithText(text, useUnmergedTree = true))
        assertEquals(gray, color("Empanada de pino"))
        assertEquals(gray, color("$15.600"))
        assertEquals(gray, textColor(onNodeWithTag(tag("diner_item_0_quantity"), useUnmergedTree = true)))
        // Extras take their product's tone.
        assertEquals(gray, color("Pebre"))
        assertEquals(gray, color("$600"))
        assertEquals(pending, color("Mojito tradicional"))
        assertEquals(pending, color("$7.000"))
        assertEquals(pending, color("Extra menta"))
        assertEquals(pending, color("$500"))
        // Discounts use the same neutral-gray400 as paid rows (Figma), not the lighter disabled gray.
        assertEquals(gray, color("Descuento"))
        assertEquals(gray, color("-$200"))
        assertNotEquals(gray, pending)
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
