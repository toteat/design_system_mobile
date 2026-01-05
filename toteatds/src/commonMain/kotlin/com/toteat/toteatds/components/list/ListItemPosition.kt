package com.toteat.toteatds.components.list

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ListItemPosition {
    Single,
    First,
    Middle,
    Last
}

fun ListItemPosition.getShape(cornerRadius: Dp): Shape {
    return when (this) {
        ListItemPosition.Single -> RoundedCornerShape(cornerRadius)
        ListItemPosition.First -> RoundedCornerShape(
            topStart = cornerRadius,
            topEnd = cornerRadius,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        )
        ListItemPosition.Middle -> RoundedCornerShape(0)
        ListItemPosition.Last -> RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomStart = cornerRadius,
            bottomEnd = cornerRadius
        )
    }
}
