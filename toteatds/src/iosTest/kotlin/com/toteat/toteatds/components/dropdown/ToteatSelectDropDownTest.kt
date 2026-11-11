package com.toteat.toteatds.components.dropdown

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import kotlinx.collections.immutable.persistentListOf
import kotlin.test.Test
import kotlin.test.assertEquals

// setTestTag prefixes every tag with "toteat:id/".
private const val Input = "toteat:id/meseros_input"
private const val Label = "toteat:id/meseros_label"
private val Options = persistentListOf("Mesero 1", "Mesero 2", "Mesero 3")

@OptIn(ExperimentalTestApi::class)
class ToteatSelectDropDownTest {

    @Test
    fun keepsTheTagsAndSemanticsOfToteatDropDownWithA50dpField() = runComposeUiTest {
        // Same call as ToteatDropDown with only the name changed.
        setContent {
            ToteatTheme {
                ToteatSelectDropDown(
                    label = "Meseros turno",
                    options = Options,
                    selectedOption = "",
                    onOptionSelect = {},
                    testTag = "meseros"
                )
            }
        }

        // 16 dp, like the label box in Figma.
        onNodeWithTag(Label).assertHeightIsEqualTo(16.dp)
        onNodeWithTag(Input)
            .assertHeightIsEqualTo(50.dp)
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, androidx.compose.ui.semantics.Role.DropdownList))
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "colapsado"))
    }

    @Test
    fun selectingAnOptionReportsItAndClosesTheList() = runComposeUiTest {
        var selected = ""
        setContent {
            ToteatTheme {
                ToteatSelectDropDown(
                    label = "Meseros turno",
                    options = Options,
                    selectedOption = selected,
                    onOptionSelect = { selected = it },
                    testTag = "meseros"
                )
            }
        }

        onNodeWithTag(Input).performClick()
        onNodeWithTag(Input).assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "expandido"))
        onNodeWithText("Mesero 2").assertHeightIsEqualTo(50.dp).performClick()

        assertEquals("Mesero 2", selected)
        onNodeWithTag(Input).assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "colapsado"))
        onNodeWithText("Mesero 2").assertDoesNotExist()
    }

    @Test
    fun disabledDoesNotOpen() = runComposeUiTest {
        setContent {
            ToteatTheme {
                ToteatSelectDropDown(
                    label = "Meseros turno",
                    options = Options,
                    selectedOption = "Mesero 2",
                    onOptionSelect = {},
                    enabled = false,
                    testTag = "meseros"
                )
            }
        }

        onNodeWithTag(Input).performClick()
        onNodeWithText("Mesero 1").assertDoesNotExist()
    }
}
