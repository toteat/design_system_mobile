package com.toteat.toteatds.components.tags

import androidx.compose.ui.graphics.Color
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.SecondaryNormal

enum class StatusTagVariant(
    val backgroundColor: Color,
    val textColor: Color,
    val defaultText: String
) {
    Pending(
        backgroundColor = NeutralGray200,
        textColor = NeutralGray500,
        defaultText = "PENDIENTE"
    ),
    Confirmed(
        backgroundColor = NeutralGray400,
        textColor = NeutralGray,
        defaultText = "CONFIRMADO"
    ),
    Ended(
        backgroundColor = NeutralGray500,
        textColor = NeutralGray,
        defaultText = "FINALIZADO"
    ),
    Cancelled(
        backgroundColor = SecondaryNormal,
        textColor = NeutralGray,
        defaultText = "CANCELADO"
    )
}