package com.toteat.toteatds.components.tags

import androidx.compose.ui.graphics.Color
import com.toteat.toteatds.theme.NeutralGray
import com.toteat.toteatds.theme.NeutralGray200
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.SecondaryNormal
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.status_tag_cancelled
import designsystemmobile.toteatds.generated.resources.status_tag_confirmed
import designsystemmobile.toteatds.generated.resources.status_tag_ended
import designsystemmobile.toteatds.generated.resources.status_tag_pending
import org.jetbrains.compose.resources.StringResource

enum class StatusTagVariant(
    val backgroundColor: Color,
    val textColor: Color,
    val defaultTextRes: StringResource
) {
    Pending(
        backgroundColor = NeutralGray200,
        textColor = NeutralGray500,
        defaultTextRes = Res.string.status_tag_pending
    ),
    Confirmed(
        backgroundColor = NeutralGray400,
        textColor = NeutralGray,
        defaultTextRes = Res.string.status_tag_confirmed
    ),
    Ended(
        backgroundColor = NeutralGray500,
        textColor = NeutralGray,
        defaultTextRes = Res.string.status_tag_ended
    ),
    Cancelled(
        backgroundColor = SecondaryNormal,
        textColor = NeutralGray,
        defaultTextRes = Res.string.status_tag_cancelled
    )
}