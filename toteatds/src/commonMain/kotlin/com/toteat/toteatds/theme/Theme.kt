package com.toteat.toteatds.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val ToteatShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

val ToteatPillShape = RoundedCornerShape(50)

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
    val neutral100: Color,
    val neutral400: Color,
    val neutral500: Color,
    val disabledContent: Color,
    val counterContainer: Color,
    val counterContainerDisabled: Color,
    val counterButton: Color,
    val counterButtonDisabled: Color,
)

val LightExtendedColors = ExtendedColors(
    isSuccess = GreenNormal,
    isWarning = WarningDark,
    neutral100 = NeutralGray100,
    neutral400 = NeutralGray400,
    neutral500 = NeutralGray500,
    disabledContent = NeutralGray300,
    counterContainer = CounterContainerColor,
    counterContainerDisabled = CounterContainerDisabledColor,
    counterButton = CounterButtonColor,
    counterButtonDisabled = CounterButtonDisabledColor,
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
    onBackground = NeutralGray500,
    surface = TertiaryLight,
    onSurface = NeutralGray500,
    surfaceVariant = NeutralGray100,
    onSurfaceVariant = NeutralGray500,
    error = RedNormal,
    onError = NeutralGray,
    outline = NeutralGray300,
    outlineVariant = NeutralGray200,
)