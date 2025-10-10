package com.toteat.toteatds.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }

val ColorScheme.extended: ExtendedColors
    @ReadOnlyComposable
    @Composable
    get() = LocalExtendedColors.current

@Immutable
data class ExtendedColors(
    //TextField states
    val isSuccess: Color,
    val isWarning: Color,
    val neutral400: Color,
)

val LightExtendedColors = ExtendedColors(
    isSuccess = GreenNormal,
    isWarning = WarningDark,
    neutral400 = NeutralGray400,
)

val LightColorScheme = lightColorScheme(
    primary = PrimaryNormal,
    onPrimary = NeutralGray,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = NeutralGray,
    secondary = SecondaryNormal,
    onSecondary = NeutralGray,
    secondaryContainer = SecondaryLight,
    onSecondaryContainer = NeutralGray500,
    tertiary = TertiaryNormal,
    onTertiary = NeutralGray500,
    tertiaryContainer = TertiaryLight,
    background = NeutralGray,
    surface = TertiaryLight,
    onSurface = NeutralGray,
    error = RedNormal,
    onError = NeutralGray,
    outline = NeutralGray300,
)