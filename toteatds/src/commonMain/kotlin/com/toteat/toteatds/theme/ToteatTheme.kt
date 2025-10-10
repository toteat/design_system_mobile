package com.toteat.toteatds.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun ToteatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) LightColorScheme else LightColorScheme
    val extendedScheme = if (darkTheme) LightExtendedColors else LightExtendedColors

    CompositionLocalProvider(LocalExtendedColors.provides(extendedScheme)) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}