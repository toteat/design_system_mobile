package com.toteat.toteatds.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.manrope_bold
import designsystemmobile.toteatds.generated.resources.manrope_light
import designsystemmobile.toteatds.generated.resources.manrope_regular
import designsystemmobile.toteatds.generated.resources.manrope_semi_bold
import org.jetbrains.compose.resources.Font

// Cache the FontFamily to avoid recreating it on every composition
val Manrope: FontFamily
    @Composable get() {
        // Font() must be called in @Composable context, not inside remember lambda
        val regular = Font(Res.font.manrope_regular, FontWeight.Normal)
        val light = Font(Res.font.manrope_light, FontWeight.Light)
        val semiBold = Font(Res.font.manrope_semi_bold, FontWeight.SemiBold)
        val bold = Font(Res.font.manrope_bold, FontWeight.Bold)

        // Remember the FontFamily wrapper to avoid recreation
        return remember {
            FontFamily(regular, light, semiBold, bold)
        }
    }

// Cache Typography instance to avoid recreation
val Typography: Typography
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            Typography(
                displayLarge = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 34.sp,
                ),
                titleLarge = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                ),
                titleMedium = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                ),
                headlineLarge = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                headlineMedium = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                bodyLarge = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                bodyMedium = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            )
        }
    }

// Cache all extension TextStyles to avoid allocating on every access
private object CachedTextStyles {
    private var fontFamily: FontFamily? = null
    private val styles = mutableMapOf<String, TextStyle>()

    fun getOrCreate(key: String, fontFamily: FontFamily, factory: (FontFamily) -> TextStyle): TextStyle {
        // Update font family reference if changed
        if (this.fontFamily !== fontFamily) {
            this.fontFamily = fontFamily
            styles.clear()
        }
        return styles.getOrPut(key) { factory(fontFamily) }
    }
}

val Typography.tagBold: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("tagBold", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }
    }

val Typography.tagSemiBold: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("tagSemiBold", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp
                )
            }
        }
    }

val Typography.tagRegular: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("tagRegular", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp
                )
            }
        }
    }

val Typography.tagLight: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("tagLight", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Light,
                    fontSize = 10.sp
                )
            }
        }
    }

val Typography.bodyLargeRegular: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("bodyLargeRegular", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }
    }

val Typography.bodyMediumRegular: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("bodyMediumRegular", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            }
        }
    }

val Typography.headingLargeRegular: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("headingLargeRegular", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                )
            }
        }
    }

val Typography.headingMediumRegular: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("headingMediumRegular", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
        }
    }

val Typography.displayLargeLight: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("displayLargeLight", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Light,
                    fontSize = 34.sp
                )
            }
        }
    }

val Typography.titleLargeRegular: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("titleLargeRegular", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp
                )
            }
        }
    }

val Typography.titleMediumRegular: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("titleMediumRegular", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp
                )
            }
        }
    }

val Typography.helperBold: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("helperBold", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            }
        }
    }

val Typography.helperRegular: TextStyle
    @Composable get() {
        val fontFamily = Manrope
        return remember(fontFamily) {
            CachedTextStyles.getOrCreate("helperRegular", fontFamily) { ff ->
                TextStyle(
                    fontFamily = ff,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp
                )
            }
        }
    }
