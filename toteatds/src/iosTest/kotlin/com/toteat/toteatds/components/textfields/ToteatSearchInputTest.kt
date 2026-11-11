package com.toteat.toteatds.components.textfields

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.ToteatTheme
import kotlinx.collections.immutable.persistentListOf
import kotlin.test.Test
import kotlin.test.assertEquals

// setTestTag prefixes every tag with "toteat:id/".
private const val Field = "toteat:id/search"
private const val List = "toteat:id/search_suggestions"
private val Results = persistentListOf(
    ToteatSearchSuggestion("1", "Donut arcoiris"),
    ToteatSearchSuggestion("2", "Donut chocolate")
)

@OptIn(ExperimentalTestApi::class)
class ToteatSearchInputTest {

    @Test
    fun sameCallAsToteatSearchBarKeepsItsFieldTagAndSemantics() = runComposeUiTest {
        var typed = ""
        // Same call as ToteatSearchBar with only the name changed.
        setContent {
            ToteatTheme {
                ToteatSearchInput(
                    value = typed,
                    onValueChange = { typed = it },
                    placeholder = "Buscar producto",
                    testTag = "search"
                )
            }
        }

        onNodeWithTag(Field)
            .assertHeightIsEqualTo(50.dp)
            // Field + search icon, as in ToteatSearchBar.
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.ContentDescription, listOf("Buscador", "Buscar")))
            .performTextInput("Donut")
        assertEquals("Donut", typed)
        onNodeWithTag(List).assertDoesNotExist()
    }

    @Test
    fun suggestionsShow8dpBelowWith50dpRowsAndReportTheTappedOne() = runComposeUiTest {
        var tapped: ToteatSearchSuggestion? = null
        setContent {
            ToteatTheme {
                ToteatSearchInput(
                    value = "Donut",
                    onValueChange = {},
                    testTag = "search",
                    suggestions = Results,
                    onSuggestionClick = { tapped = it }
                )
            }
        }

        val gap = onNodeWithTag(List).getBoundsInRoot().top - onNodeWithTag(Field).getBoundsInRoot().bottom
        assertEquals(8f, gap.value, 0.5f)
        onNodeWithTag("${Field}_suggestion_2").assertHeightIsEqualTo(50.dp).performClick()
        assertEquals(Results[1], tapped)
    }

    @Test
    fun disabledHidesSuggestions() = runComposeUiTest {
        setContent {
            ToteatTheme {
                ToteatSearchInput(
                    value = "Donut",
                    onValueChange = {},
                    enabled = false,
                    testTag = "search",
                    suggestions = Results
                )
            }
        }

        onNodeWithTag(List).assertDoesNotExist()
    }
}
