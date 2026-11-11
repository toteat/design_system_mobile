package com.toteat.toteatds.components.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.click
import androidx.compose.ui.test.getBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toteat.toteatds.theme.ToteatTheme
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

// setTestTag prefixes every tag with "toteat:id/".
private const val ButtonTag = "toteat:id/button"
private const val Label = "Agregar"

@OptIn(ExperimentalTestApi::class)
class ToteatButtonSizeTest {

    private data class Expected(
        val height: Dp,
        val fontSize: TextUnit,
        val lineHeight: TextUnit,
        val fontWeight: FontWeight = FontWeight.Bold
    )

    private val expected = mapOf(
        ToteatButtonSize.Default to Expected(48.dp, 14.sp, TextUnit.Unspecified),
        ToteatButtonSize.XS to Expected(32.dp, 12.sp, 18.sp),
        ToteatButtonSize.S to Expected(45.dp, 16.sp, 24.sp),
        ToteatButtonSize.M to Expected(60.dp, 20.sp, TextUnit.Unspecified),
        ToteatButtonSize.L to Expected(80.dp, 34.sp, TextUnit.Unspecified, FontWeight.SemiBold)
    )

    @Composable
    private fun TestButton(
        kind: ToteatButtonKind,
        size: ToteatButtonSize?,
        contentPadding: PaddingValues? = null,
        onClick: () -> Unit = {}
    ) {
        // size == null: the call doesn't pass size, as existing screens do.
        when (kind) {
            ToteatButtonKind.Primary -> when {
                size == null -> ToteatPrimaryButton(onClick = onClick, text = Label, testTag = "button")
                contentPadding == null ->
                    ToteatPrimaryButton(onClick = onClick, text = Label, testTag = "button", size = size)
                else -> ToteatPrimaryButton(
                    onClick = onClick, text = Label, testTag = "button", size = size, contentPadding = contentPadding
                )
            }
            ToteatButtonKind.Secondary -> when {
                size == null -> ToteatSecondaryButton(onClick = onClick, text = Label, testTag = "button")
                contentPadding == null ->
                    ToteatSecondaryButton(onClick = onClick, text = Label, testTag = "button", size = size)
                else -> ToteatSecondaryButton(
                    onClick = onClick, text = Label, testTag = "button", size = size, contentPadding = contentPadding
                )
            }
            ToteatButtonKind.Tertiary -> when {
                size == null -> ToteatTertiaryButton(onClick = onClick, text = Label, testTag = "button")
                contentPadding == null ->
                    ToteatTertiaryButton(onClick = onClick, text = Label, testTag = "button", size = size)
                else -> ToteatTertiaryButton(
                    onClick = onClick, text = Label, testTag = "button", size = size, contentPadding = contentPadding
                )
            }
        }
    }

    private fun SemanticsNodeInteraction.textStyle(): TextStyle {
        val results = mutableListOf<TextLayoutResult>()
        fetchSemanticsNode().config[SemanticsActions.GetTextLayoutResult].action!!.invoke(results)
        return results.first().layoutInput.style
    }

    @Test
    fun omittedSizeKeepsThePreviousLook() {
        ToteatButtonKind.entries.forEach { kind ->
            runComposeUiTest {
                setContent { ToteatTheme { TestButton(kind, size = null) } }

                onNodeWithTag(ButtonTag).assertHeightIsEqualTo(48.dp)
                val style = onNodeWithText(Label, useUnmergedTree = true).textStyle()
                assertEquals(14.sp, style.fontSize, "$kind font size")
                // ButtonDefaults.ContentPadding: 24 dp at the start.
                val inset = onNodeWithText(Label, useUnmergedTree = true).getBoundsInRoot().left -
                    onNodeWithTag(ButtonTag).getBoundsInRoot().left
                assertEquals(24f, inset.value, 0.5f, "$kind start padding")
            }
        }
    }

    @Test
    fun eachSizeHasTheFigmaHeightAndTypography() {
        ToteatButtonKind.entries.forEach { kind ->
            expected.forEach { (size, spec) ->
                runComposeUiTest {
                    setContent { ToteatTheme { Box(Modifier.padding(8.dp)) { TestButton(kind, size) } } }

                    onNodeWithTag(ButtonTag).assertHeightIsEqualTo(spec.height)
                    val style = onNodeWithText(Label, useUnmergedTree = true).textStyle()
                    assertEquals(spec.fontSize, style.fontSize, "$kind $size font size")
                    assertEquals(spec.fontWeight, style.fontWeight, "$kind $size font weight")
                    if (spec.lineHeight != TextUnit.Unspecified) {
                        assertEquals(spec.lineHeight, style.lineHeight, "$kind $size line height")
                    }
                }
            }
        }
    }

    @Test
    fun figmaSizesUseTheFigmaHorizontalPadding() {
        val expectedStart = mapOf(
            ToteatButtonSize.XS to 32f,
            ToteatButtonSize.S to 32f,
            ToteatButtonSize.M to 32f,
            ToteatButtonSize.L to 68f
        )
        ToteatButtonKind.entries.forEach { kind ->
            expectedStart.forEach { (size, start) ->
                runComposeUiTest {
                    setContent { ToteatTheme { Box(Modifier.padding(8.dp)) { TestButton(kind, size) } } }

                    val inset = onNodeWithText(Label, useUnmergedTree = true).getBoundsInRoot().left -
                        onNodeWithTag(ButtonTag).getBoundsInRoot().left
                    assertEquals(start, inset.value, 0.5f, "$kind $size start padding")
                }
            }
        }
    }

    @Test
    fun explicitContentPaddingWinsOverTheSizePadding() {
        ToteatButtonKind.entries.forEach { kind ->
            runComposeUiTest {
                setContent {
                    ToteatTheme {
                        Box(Modifier.padding(8.dp)) {
                            TestButton(kind, ToteatButtonSize.S, PaddingValues(horizontal = 12.dp, vertical = 8.dp))
                        }
                    }
                }

                // Padding from the caller, height from the size.
                onNodeWithTag(ButtonTag).assertHeightIsEqualTo(45.dp)
                val inset = onNodeWithText(Label, useUnmergedTree = true).getBoundsInRoot().left -
                    onNodeWithTag(ButtonTag).getBoundsInRoot().left
                assertEquals(12f, inset.value, 0.5f, "$kind start padding")
            }
        }
    }

    @Test
    fun smallSizesStillReceiveTapsJustOutsideTheirBounds() {
        // XS (32 dp) and S (45 dp) are under the 48 dp minimum touch target; Compose extends
        // the touch area of smaller pointer targets.
        listOf(ToteatButtonSize.XS, ToteatButtonSize.S).forEach { size ->
            runComposeUiTest {
                var clicks = 0
                setContent {
                    ToteatTheme {
                        Box(Modifier.padding(24.dp)) {
                            TestButton(ToteatButtonKind.Primary, size, onClick = { clicks++ })
                        }
                    }
                }

                val overshootPx = with(density) { 1.dp.toPx() }
                onNodeWithTag(ButtonTag).performTouchInput {
                    click(Offset(centerX, -overshootPx))
                }
                assertTrue(clicks == 1, "$size: tap 1 dp above the button should click it")
            }
        }
    }
}
