package com.toteat.toteatds.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

actual fun Modifier.setTestTag(tag: String): Modifier = this
    .testTag("toteat:id/$tag")