package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// setTestTag prefixes every tag with "toteat:id/".
private const val Container = "toteat:id/diners"
private const val Add = "toteat:id/diners_add"
private fun dinerTag(name: String) = "toteat:id/diners_$name"

private val Diners = persistentListOf("Trini", "Pauli", "Camila", "Angie", "Vale", "Sofi", "Cata")

// 56 dp buttons 4 dp apart: "Agregar", Trini and Pauli fit fully (0–176 dp), Camila is cut
// and the rest start off screen.
private val NarrowWidth = 200.dp

@OptIn(ExperimentalTestApi::class)
class ToteatDinerButtonContainerTest {

    private fun ComposeUiTest.assertFullyVisible(diner: String) {
        val container = onNodeWithTag(Container).getUnclippedBoundsInRoot()
        val button = onNodeWithTag(dinerTag(diner)).getUnclippedBoundsInRoot()
        assertTrue(
            button.left >= container.left && button.right <= container.right,
            "$diner should be fully visible: button $button, container $container"
        )
    }

    @Test
    fun scrollsToShowASelectedDinerThatIsOffScreen() = runComposeUiTest {
        var selected by mutableStateOf<String?>("Trini")
        setContent {
            ToteatTheme {
                ToteatDinerButtonContainer(
                    diners = Diners,
                    selectedDiner = selected,
                    onDinerSelect = {},
                    modifier = Modifier.width(NarrowWidth),
                    onAddClick = {},
                    onViewMoreClick = {},
                    testTag = "diners"
                )
            }
        }
        onNodeWithTag(dinerTag("Sofi")).assertDoesNotExist()

        selected = "Sofi"
        waitForIdle()

        assertFullyVisible("Sofi")
    }

    @Test
    fun scrollsBackToASelectedDinerBeforeTheViewport() = runComposeUiTest {
        var selected by mutableStateOf<String?>("Cata")
        setContent {
            ToteatTheme {
                ToteatDinerButtonContainer(
                    diners = Diners,
                    selectedDiner = selected,
                    onDinerSelect = {},
                    modifier = Modifier.width(NarrowWidth),
                    onAddClick = {},
                    testTag = "diners"
                )
            }
        }
        // The initial value is shown too.
        waitForIdle()
        assertFullyVisible("Cata")

        selected = "Pauli"
        waitForIdle()

        assertFullyVisible("Pauli")
    }

    @Test
    fun showsADinerAddedAtTheEndAndSelected() = runComposeUiTest {
        var diners: ImmutableList<String> by mutableStateOf(Diners)
        var selected by mutableStateOf<String?>("Trini")
        setContent {
            ToteatTheme {
                ToteatDinerButtonContainer(
                    diners = diners,
                    selectedDiner = selected,
                    onDinerSelect = {},
                    modifier = Modifier.width(NarrowWidth),
                    onAddClick = {},
                    onViewMoreClick = {},
                    testTag = "diners"
                )
            }
        }

        diners = Diners.add("Nueva")
        selected = "Nueva"
        waitForIdle()

        assertFullyVisible("Nueva")
    }

    @Test
    fun doesNotMoveWhenTheSelectedDinerIsAlreadyVisible() = runComposeUiTest {
        var selected by mutableStateOf<String?>("Trini")
        setContent {
            ToteatTheme {
                ToteatDinerButtonContainer(
                    diners = Diners,
                    selectedDiner = selected,
                    onDinerSelect = {},
                    modifier = Modifier.width(NarrowWidth),
                    onAddClick = {},
                    onViewMoreClick = {},
                    testTag = "diners"
                )
            }
        }
        waitForIdle()
        val addBefore = onNodeWithTag(Add).getUnclippedBoundsInRoot()

        selected = "Pauli"
        waitForIdle()

        // "Agregar" is still the first visible item, in the same place.
        assertEquals(addBefore, onNodeWithTag(Add).getUnclippedBoundsInRoot())
        assertFullyVisible("Pauli")
    }

    @Test
    fun doesNotMoveWhenTheSelectedDinerIsNullOrUnknown() = runComposeUiTest {
        var selected by mutableStateOf<String?>(null)
        setContent {
            ToteatTheme {
                ToteatDinerButtonContainer(
                    diners = Diners,
                    selectedDiner = selected,
                    onDinerSelect = {},
                    modifier = Modifier.width(NarrowWidth),
                    onAddClick = {},
                    testTag = "diners"
                )
            }
        }
        waitForIdle()
        val addBefore = onNodeWithTag(Add).getUnclippedBoundsInRoot()

        selected = "Nadie"
        waitForIdle()

        assertEquals(addBefore, onNodeWithTag(Add).getUnclippedBoundsInRoot())
    }
}
