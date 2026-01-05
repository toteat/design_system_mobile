package com.toteat.toteatds.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

actual fun Modifier.setTestTag(tag: String): Modifier = this
    .testTag("toteat:id/$tag")
    .semantics { testTagsAsResourceId = true }