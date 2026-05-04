package com.toteat.toteatds.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun ToteatTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalExtendedColors.provides(LightExtendedColors)) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = Typography,
            shapes = ToteatShapes,
            content = content
        )
    }
}