package com.toteat.toteatds.components.toast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.BlueLight
import com.toteat.toteatds.theme.BlueNormal
import com.toteat.toteatds.theme.GreenLight
import com.toteat.toteatds.theme.GreenNormal
import com.toteat.toteatds.theme.YellowLight
import com.toteat.toteatds.theme.YellowNormal
import com.toteat.toteatds.theme.bodyMediumRegular
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_error
import designsystemmobile.toteatds.generated.resources.icon_info
import designsystemmobile.toteatds.generated.resources.icon_success
import designsystemmobile.toteatds.generated.resources.icon_warning
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview


sealed class ToteatToastMessageType(
    val backgroundColor: Color,
    val borderBackground: Color,
    val iconRes: DrawableResource,
    val iconTint: Color
) {
    object Error : ToteatToastMessageType(Color(0xFFFBD7DD), Color(0xFFE12845), Res.drawable.icon_error
        , Color.White)
    object Success : ToteatToastMessageType(GreenLight,
        GreenNormal, Res.drawable.icon_success, Color.White)
    object Warning : ToteatToastMessageType(YellowLight,
        YellowNormal, Res.drawable.icon_warning, Color.White)
    object Info : ToteatToastMessageType(BlueLight, BlueNormal, Res.drawable.icon_info, Color.White)
}
@OptIn(ExperimentalResourceApi::class)
@Composable
fun ToteatToastMessage(
    title: String,
    message: String,
    type: ToteatToastMessageType,
    modifier: Modifier = Modifier,
    onDismiss: (() -> Unit)? = null,
    durationMs: Long = 1000L,
) {
    var isVisible by remember { mutableStateOf(true) }


    LaunchedEffect(durationMs, isVisible) {
        if (durationMs > 0 && isVisible) {
            kotlinx.coroutines.delay(durationMs)
            isVisible = false
            onDismiss?.invoke()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)

    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = androidx.compose.animation.slideInVertically(initialOffsetY = { it }),
            exit  = androidx.compose.animation.slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(type.backgroundColor, RoundedCornerShape(8.dp))
                    .border(1.dp, type.borderBackground, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = vectorResource(type.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(21.dp),
                    tint = type.iconTint
                )

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMediumRegular,
                        color = Color.Gray
                    )
                }

                if (onDismiss != null) {
                    IconButton(
                        onClick = {
                            isVisible = false
                            onDismiss()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar mensaje",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}



@Preview()
@Composable
fun ToteatToastMessagePreview() {
    MaterialTheme { Column (
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ToteatToastMessage(
            title = "Error",
            message = "This is an error message.",
            type = ToteatToastMessageType.Error,
            onDismiss = {}
        )
        ToteatToastMessage(
            title = "Success",
            message = "This is an error message.",
            type = ToteatToastMessageType.Success,
            onDismiss = {}
        )
        ToteatToastMessage(
            title = "Warning",
            message = "This is a warning message.",
            type = ToteatToastMessageType.Warning,
            onDismiss = {}
        )
        ToteatToastMessage(
            title = "Info",
            message = "This is an info message.",
            type = ToteatToastMessageType.Info,
            onDismiss = {}
        )
    } }
}