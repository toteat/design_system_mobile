package com.toteat.toteatds.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
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

val Manrope
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.manrope_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.manrope_light,
            weight = FontWeight.Light
        ),
        Font(
            resource = Res.font.manrope_semi_bold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resource = Res.font.manrope_bold,
            weight = FontWeight.Bold
        ),
    )

val Typography
    @Composable get() = Typography(
        displayLarge = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.SemiBold,
            fontSize = 34.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    )

val Typography.tagBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp
    )

val Typography.tagSemiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp
    )

val Typography.tagRegular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    )

val Typography.tagLight: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Light,
        fontSize = 10.sp
    )

val Typography.bodyLargeRegular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )

val Typography.bodyMediumRegular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )

val Typography.headingLargeRegular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    )

val Typography.headingMediumRegular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

val Typography.displayLargeLight: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Light,
        fontSize = 34.sp
    )

val Typography.titleLargeRegular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    )

val Typography.titleMediumRegular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    )

val Typography.helperBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp
    )

val Typography.helperRegular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    )