package com.toteat.toteatds.components.buttons

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.toteat.toteatds.theme.ToteatTheme
import kotlin.test.Test
import kotlin.test.assertEquals

// setTestTag prefixes every tag with "toteat:id/".
private const val CardTag = "toteat:id/card"

@OptIn(ExperimentalTestApi::class)
class ToteatSquareButtonTest {

    @Test
    fun disabledCardWithOnDisabledClickCallsItInsteadOfOnClick() = runComposeUiTest {
        var clicks = 0
        var disabledClicks = 0
        setContent {
            ToteatTheme {
                ToteatSquareButton(
                    title = "Pago por",
                    subTitle = "comensal",
                    onClick = { clicks++ },
                    enabled = false,
                    onDisabledClick = { disabledClicks++ },
                    testTag = "card",
                    icon = {}
                )
            }
        }

        onNodeWithTag(CardTag)
            // Still actionable for screen readers, announced as unavailable
            .assertIsEnabled()
            .assertHasClickAction()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "No disponible"))
            .performClick()

        assertEquals(0, clicks)
        assertEquals(1, disabledClicks)
    }

    @Test
    fun disabledCardWithoutOnDisabledClickCallsNothing() = runComposeUiTest {
        var clicks = 0
        setContent {
            ToteatTheme {
                ToteatSquareButton(
                    title = "Pago por",
                    subTitle = "comensal",
                    onClick = { clicks++ },
                    enabled = false,
                    testTag = "card",
                    icon = {}
                )
            }
        }

        onNodeWithTag(CardTag).performClick()

        assertEquals(0, clicks)
    }

    @Test
    fun enabledCardIgnoresOnDisabledClick() = runComposeUiTest {
        var clicks = 0
        var disabledClicks = 0
        setContent {
            ToteatTheme {
                ToteatSquareButton(
                    title = "Pago por",
                    subTitle = "comensal",
                    onClick = { clicks++ },
                    onDisabledClick = { disabledClicks++ },
                    testTag = "card",
                    icon = {}
                )
            }
        }

        onNodeWithTag(CardTag).performClick()

        assertEquals(1, clicks)
        assertEquals(0, disabledClicks)
    }
}
