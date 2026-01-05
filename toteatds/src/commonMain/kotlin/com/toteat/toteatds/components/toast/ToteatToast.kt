package com.toteat.toteatds.components.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.BlueLight
import com.toteat.toteatds.theme.BlueNormal
import com.toteat.toteatds.theme.GreenLight
import com.toteat.toteatds.theme.GreenNormal
import com.toteat.toteatds.theme.RedLight
import com.toteat.toteatds.theme.RedNormal
import com.toteat.toteatds.theme.YellowLight
import com.toteat.toteatds.theme.YellowNormal
import com.toteat.toteatds.theme.bodyMediumRegular
import com.toteat.toteatds.theme.extended
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.icon_error
import designsystemmobile.toteatds.generated.resources.icon_info
import designsystemmobile.toteatds.generated.resources.icon_success
import designsystemmobile.toteatds.generated.resources.icon_warning
import designsystemmobile.toteatds.generated.resources.toast_close_message
import designsystemmobile.toteatds.generated.resources.toast_error_description
import designsystemmobile.toteatds.generated.resources.toast_info_description
import designsystemmobile.toteatds.generated.resources.toast_success_description
import designsystemmobile.toteatds.generated.resources.toast_type_error
import designsystemmobile.toteatds.generated.resources.toast_type_info
import designsystemmobile.toteatds.generated.resources.toast_type_success
import designsystemmobile.toteatds.generated.resources.toast_type_warning
import designsystemmobile.toteatds.generated.resources.toast_warning_description
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.toteat.toteatds.theme.ToteatTheme


sealed class ToteatToastMessageType(
    val backgroundColor: Color,
    val borderBackground: Color,
    val iconRes: DrawableResource,
    val iconTint: Color,
    val typeNameRes: StringResource,
    val descriptionRes: StringResource
) {
    object Error : ToteatToastMessageType(
        backgroundColor = RedLight,
        borderBackground = RedNormal,
        iconRes = Res.drawable.icon_error,
        iconTint = RedNormal,
        typeNameRes = Res.string.toast_type_error,
        descriptionRes = Res.string.toast_error_description
    )

    object Success : ToteatToastMessageType(
        backgroundColor = GreenLight,
        borderBackground = GreenNormal,
        iconRes = Res.drawable.icon_success,
        iconTint = GreenNormal,
        typeNameRes = Res.string.toast_type_success,
        descriptionRes = Res.string.toast_success_description
    )

    object Warning : ToteatToastMessageType(
        backgroundColor = YellowLight,
        borderBackground = YellowNormal,
        iconRes = Res.drawable.icon_warning,
        iconTint = YellowNormal,
        typeNameRes = Res.string.toast_type_warning,
        descriptionRes = Res.string.toast_warning_description
    )

    object Info : ToteatToastMessageType(
        backgroundColor = BlueLight,
        borderBackground = BlueNormal,
        iconRes = Res.drawable.icon_info,
        iconTint = BlueNormal,
        typeNameRes = Res.string.toast_type_info,
        descriptionRes = Res.string.toast_info_description
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ToteatToastMessage(
    title: String,
    message: String,
    type: ToteatToastMessageType,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    testTag: String = ""
) {
    // Localized strings for accessibility
    val toastDescription = stringResource(type.descriptionRes, title, message)
    val closeButtonDescription = stringResource(Res.string.toast_close_message)
    val toastTypeName = stringResource(type.typeNameRes)
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                type.backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .border(1.dp, type.borderBackground, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .setTestTag(testTag)
            .semantics {
                role = Role.Button
                contentDescription = toastDescription
                liveRegion = LiveRegionMode.Polite
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = vectorResource(type.iconRes),
            contentDescription = toastTypeName,
            modifier = Modifier.size(21.dp),
            tint = type.iconTint
        )

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMediumRegular,
                color = MaterialTheme.colorScheme.extended.neutral500
            )
        }

        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .size(24.dp)
                .semantics {
                    contentDescription = closeButtonDescription
                }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
private fun ToteatToastMessageErrorPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            ToteatToastMessage(
                title = "Error",
                message = "No se pudo completar la operación",
                type = ToteatToastMessageType.Error,
                onDismiss = {}
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
private fun ToteatToastMessageSuccessPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            ToteatToastMessage(
                title = "Éxito",
                message = "La operación se completó correctamente",
                type = ToteatToastMessageType.Success,
                onDismiss = {}
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
private fun ToteatToastMessageWarningPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            ToteatToastMessage(
                title = "Advertencia",
                message = "Algunos datos podrían no guardarse",
                type = ToteatToastMessageType.Warning,
                onDismiss = {}
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
private fun ToteatToastMessageInfoPreview() {
    ToteatTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            ToteatToastMessage(
                title = "Información",
                message = "Hay una nueva actualización disponible",
                type = ToteatToastMessageType.Info,
                onDismiss = {}
            )
        }
    }
}
