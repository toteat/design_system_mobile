package com.toteat.toteatds.components.modalbuttonsheet

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.theme.NeutralGray300
import com.toteat.toteatds.theme.NeutralGray400
import com.toteat.toteatds.theme.NeutralGray500
import com.toteat.toteatds.theme.ToteatTheme
import com.toteat.toteatds.utils.setTestTag
import designsystemmobile.toteatds.generated.resources.Res
import designsystemmobile.toteatds.generated.resources.action_bottom_sheet_description
import designsystemmobile.toteatds.generated.resources.action_item_description
import designsystemmobile.toteatds.generated.resources.action_type_profile
import designsystemmobile.toteatds.generated.resources.action_type_settings
import designsystemmobile.toteatds.generated.resources.action_type_switch_user
import designsystemmobile.toteatds.generated.resources.change_icon
import designsystemmobile.toteatds.generated.resources.chip_selected
import designsystemmobile.toteatds.generated.resources.group_bottom_view
import designsystemmobile.toteatds.generated.resources.state_disabled
import designsystemmobile.toteatds.generated.resources.user_icon_bottom_sheet
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Tipos de acciones predefinidas para el ActionBottomSheet.
 * Cada tipo incluye su icono (recurso drawable) y label fijos.
 */
sealed class ToteatActionType(
    val id: String,
    val iconRes: DrawableResource,
    val labelRes: StringResource
) {
    data object Profile : ToteatActionType(
        id = "profile",
        iconRes = Res.drawable.user_icon_bottom_sheet,
        labelRes = Res.string.action_type_profile
    )

    data object Settings : ToteatActionType(
        id = "settings",
        iconRes = Res.drawable.group_bottom_view,
        labelRes = Res.string.action_type_settings
    )

    data object SwitchUser : ToteatActionType(
        id = "switch_user",
        iconRes = Res.drawable.change_icon,
        labelRes = Res.string.action_type_switch_user
    )
}

/**
 * Configuración de una acción en el bottom sheet.
 *
 * @param type Tipo de acción predefinido (Profile, Settings, SwitchUser)
 * @param enabled Si la acción está habilitada
 * @param isSelected Si la acción está seleccionada (muestra el icono en color primario)
 * @param onClick Callback cuando se hace clic en la acción
 */
data class ToteatActionConfig(
    val type: ToteatActionType,
    val enabled: Boolean = true,
    val isSelected: Boolean = false,
    val onClick: () -> Unit
)

private val SheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
private val TileShape = RoundedCornerShape(16.dp)
private val TileSize = 84.dp
private val IconSize = 30.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToteatActionBottomSheet(
    actions: List<ToteatActionConfig>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    testTag: String = ""
) {
    val sheetDescription = stringResource(Res.string.action_bottom_sheet_description)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = SheetShape,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.background,
        dragHandle = { DragHandle() },
        modifier = modifier
            .semantics { contentDescription = sheetDescription }
            .then(if (testTag.isNotEmpty()) Modifier.setTestTag(testTag) else Modifier),
    ) {
        ActionMenuContent(
            actions = actions,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
private fun DragHandle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(NeutralGray300)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ActionMenuContent(
    actions: List<ToteatActionConfig>,
    onDismissRequest: () -> Unit,
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .navigationBarsPadding(),
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        actions.forEach { actionConfig ->
            ActionMenuItem(
                actionConfig = actionConfig,
                onItemClick = {
                    actionConfig.onClick()
                    onDismissRequest()
                }
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun ActionMenuItem(
    actionConfig: ToteatActionConfig,
    onItemClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val label = stringResource(actionConfig.type.labelRes)
    val actionDescription = stringResource(Res.string.action_item_description, label)
    val disabledText = stringResource(Res.string.state_disabled)
    val selectedText = stringResource(Res.string.chip_selected)

    val iconTintTarget = when {
        !actionConfig.enabled -> NeutralGray400
        actionConfig.isSelected -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    val iconTint by animateColorAsState(iconTintTarget, label = "actionIconTint")

    val scale by animateFloatAsState(
        targetValue = if (pressed && actionConfig.enabled) 0.98f else 1.0f,
        label = "tileScale"
    )

    val elevation = 2.dp

    Column(
        modifier = Modifier
            .width(TileSize),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            onClick = onItemClick,
            enabled = actionConfig.enabled,
            interactionSource = interactionSource,
            shape = TileShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = elevation,
                pressedElevation = elevation,
                disabledElevation = 0.dp
            ),
            modifier = Modifier
                .size(TileSize)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .semantics {
                    contentDescription = actionDescription
                    role = Role.Button
                    stateDescription = when {
                        !actionConfig.enabled -> disabledText
                        actionConfig.isSelected -> selectedText
                        else -> ""
                    }
                }
                .setTestTag("action_${actionConfig.type.id}")
                .alpha(if (actionConfig.enabled) 1f else 0.6f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TileSize),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = vectorResource(actionConfig.type.iconRes),
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(IconSize)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = NeutralGray500,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "BottomSheet - Light", showBackground = true)
@Composable
private fun ToteatActionBottomSheetPreview_Light() {
    ToteatTheme {
        ToteatActionBottomSheet(
            actions = previewActions(),
            onDismissRequest = {},
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            testTag = "preview_sheet"
        )
    }
}

@Composable
private fun previewActions(): List<ToteatActionConfig> {
    return listOf(
        ToteatActionConfig(
            type = ToteatActionType.Profile,
            isSelected = true,
            onClick = {}
        ),
        ToteatActionConfig(
            type = ToteatActionType.Settings,
            onClick = {}
        ),
        ToteatActionConfig(
            type = ToteatActionType.SwitchUser,
            onClick = {}
        )
    )
}
