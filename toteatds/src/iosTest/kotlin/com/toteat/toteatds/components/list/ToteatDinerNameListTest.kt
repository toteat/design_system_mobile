package com.toteat.toteatds.components.list

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.ToteatTheme
import kotlinx.collections.immutable.persistentListOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// setTestTag prefixes every tag with "toteat:id/".
private const val Row1 = "toteat:id/list_row_1"
private const val Row2 = "toteat:id/list_row_2"

@OptIn(ExperimentalTestApi::class)
class ToteatDinerNameListTest {

    private fun SemanticsNodeInteraction.textStyle(): TextStyle {
        val results = mutableListOf<TextLayoutResult>()
        fetchSemanticsNode().config[SemanticsActions.GetTextLayoutResult].action!!.invoke(results)
        return results.first().layoutInput.style
    }

    private fun androidx.compose.ui.test.ComposeUiTest.showList() {
        setContent {
            ToteatTheme {
                ToteatDinerNameList(
                    names = persistentListOf("", "Trini"),
                    onNameChange = { _, _ -> },
                    testTag = "list",
                    modifier = Modifier.width(360.dp)
                )
            }
        }
    }

    @Test
    fun rowsAre52dpTallAnd8dpApart() = runComposeUiTest {
        showList()

        onNodeWithTag(Row1).assertHeightIsEqualTo(52.dp)
        onNodeWithTag(Row2).assertHeightIsEqualTo(52.dp)
        val gap = onNodeWithTag(Row2).getBoundsInRoot().top - onNodeWithTag(Row1).getBoundsInRoot().bottom
        assertEquals(8f, gap.value, 0.5f)
    }

    @Test
    fun pencilKeepsA48dpTouchAreaFlushWithTheRowEdge() = runComposeUiTest {
        showList()

        val row = onNodeWithTag(Row1).getBoundsInRoot()
        val edit = onNodeWithTag("${Row1}_edit").getBoundsInRoot()
        assertTrue((edit.right - edit.left).value >= 48f, "touch width ${edit.right - edit.left}")
        assertTrue((edit.bottom - edit.top).value >= 48f, "touch height ${edit.bottom - edit.top}")
        // The icon sits 12 dp inside this area, so it ends 12 dp from the row's edge.
        assertEquals(row.right.value, edit.right.value, 0.5f)
    }

    @Test
    fun placeholderAndNameUseBold11WithFigmaLineHeight() = runComposeUiTest {
        showList()

        listOf("Comensal 1", "Trini").forEach { text ->
            val style = onNodeWithText(text, useUnmergedTree = true).textStyle()
            assertEquals(11.sp, style.fontSize, "$text font size")
            assertEquals(FontWeight.Bold, style.fontWeight, "$text font weight")
            assertEquals(19.8f, style.lineHeight.value, 0.01f, "$text line height")
        }
    }

    @Test
    fun tappingTheRowStillOpensEditingAtTheSameHeight() = runComposeUiTest {
        showList()

        onNodeWithTag(Row2).performClick()

        onNodeWithTag("${Row2}_input").assertExists()
        onNodeWithTag(Row2).assertHeightIsEqualTo(52.dp)
    }
}
