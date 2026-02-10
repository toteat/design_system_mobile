package com.toteat.designsystemmobile

import com.toteat.toteatds.components.messageview.WelcomeMessage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.segmentbuttons.SegmentedTabs
import com.toteat.toteatds.components.bottombar.ToteatBottomBar
import com.toteat.toteatds.components.bottombar.ToteatBottomBarButtonType
import com.toteat.toteatds.components.brand.iso.ToteatIsoBlackAndCream
import com.toteat.toteatds.components.brand.iso.ToteatIsoCreamOrange
import com.toteat.toteatds.components.brand.iso.ToteatIsoOriginal
import com.toteat.toteatds.components.brand.logo.ToteatLogoBlackAndCream
import com.toteat.toteatds.components.brand.logo.ToteatLogoCreamOrange
import com.toteat.toteatds.components.brand.logo.ToteatLogoOriginal
import com.toteat.toteatds.components.buttons.ButtonTableStatus
import com.toteat.toteatds.components.buttons.ToteatButtonTable
import com.toteat.toteatds.components.buttons.ToteatChipButtonContainer
import com.toteat.toteatds.components.buttons.ToteatPrimaryButton
import com.toteat.toteatds.components.buttons.ToteatPrintButton
import com.toteat.toteatds.components.buttons.ToteatRectangleButton
import com.toteat.toteatds.components.buttons.ToteatSecondaryButton
import com.toteat.toteatds.components.buttons.ToteatSquareButton
import com.toteat.toteatds.components.buttons.ToteatSwitchButtonContainer
import com.toteat.toteatds.components.buttons.ToteatTertiaryButton
import com.toteat.toteatds.components.dropdown.ToteatDropDown
import com.toteat.toteatds.components.icons.DifferentAmountPaymentsIcon
import com.toteat.toteatds.components.icons.PrintIconButton
import com.toteat.toteatds.components.icons.SplitPaymentIcon
import com.toteat.toteatds.components.icons.TotalPaymentsIcon
import com.toteat.toteatds.components.list.GroupedOrderDetail
import com.toteat.toteatds.components.list.OrderItem
import com.toteat.toteatds.components.list.OrderItemExtra
import com.toteat.toteatds.components.tags.StatusTag
import com.toteat.toteatds.components.tags.StatusTagVariant
import com.toteat.toteatds.components.textfields.ToteatPasswordTextField
import com.toteat.toteatds.components.textfields.ToteatPhoneNumberField
import com.toteat.toteatds.components.textfields.ToteatTextField
import com.toteat.toteatds.components.toast.ToteatToastMessage
import com.toteat.toteatds.components.toast.ToteatToastMessageType
import com.toteat.toteatds.components.topbar.BackNavigationTopBar
import com.toteat.toteatds.components.topbar.CenterContentTopBar
import com.toteat.toteatds.components.topbar.LoginTopBar
import com.toteat.toteatds.components.topbar.ProcessNameTopBarItem
import com.toteat.toteatds.components.topbar.RestaurantNameTopBarItem
import com.toteat.toteatds.theme.ToteatTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

data class ComponentShowcaseItem(
    val title: String,
    val isExpanded: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    ToteatTheme {
        var selectedItem by remember {
            mutableStateOf<ToteatBottomBarButtonType>(ToteatBottomBarButtonType.AllTables)
        }

        // Use immutable list with state instead of mutableStateListOf
        var componentList by remember {
            mutableStateOf(
                persistentListOf(
                    ComponentShowcaseItem(title = "Buttons"),
                    ComponentShowcaseItem(title = "TopBars"),
                    ComponentShowcaseItem(title = "Dropdowns"),
                    ComponentShowcaseItem(title = "Inputs"),
                    ComponentShowcaseItem(title = "Segmented Tabs"),
                    ComponentShowcaseItem(title = "MessageView"),
                    ComponentShowcaseItem(title = "Brand"),
                    ComponentShowcaseItem(title = "Toast"),
                    ComponentShowcaseItem(title = "Switch container"),
                    ComponentShowcaseItem(title = "Chip container"),
                    ComponentShowcaseItem(title = "Order detail"),
                    ComponentShowcaseItem(title = "Tags")
                )
            )
        }
        var toastMessage by remember { mutableStateOf<String?>(null) }

        if (toastMessage != null) {
            LaunchedEffect(toastMessage) {
                delay(2000)
                toastMessage = null
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    CenterContentTopBar(
                        content = {
                            ProcessNameTopBarItem("Design System Showcase")
                        }
                    )
                },
                bottomBar = {
                    ToteatBottomBar(
                        selectedType = selectedItem,
                        onMyTablesClick = {
                            selectedItem = ToteatBottomBarButtonType.Tables
                            toastMessage = "Has presionado Mis mesas"
                        },
                        onAllTablesClick = {
                            selectedItem = ToteatBottomBarButtonType.AllTables
                            toastMessage = "Has presionado Todas las mesas"
                        },
                        onMoreClick = {
                            selectedItem = ToteatBottomBarButtonType.ViewMore
                            toastMessage = "Has presionado Ver más"
                        }
                    )
                }

            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(
                        items = componentList,
                        key = { _, item -> item.title }  // Use title as stable key
                    ) { index, item ->
                        ComponentShowcaseSection(
                            item = item,
                            onClick = {
                                // Update immutable list by replacing the item at index
                                componentList = componentList.set(index, item.copy(isExpanded = !item.isExpanded))
                            }
                        )
                    }
                }
            }
            toastMessage?.let {
                ToteatToastMessage(
                    title = "Acción",
                    message = it,
                    type = ToteatToastMessageType.Info,
                    onDismiss = { toastMessage = null },
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 100.dp)
                )
            }
        }
    }
}

@Composable
fun ComponentShowcaseSection(
    item: ComponentShowcaseItem,
    onClick: () -> Unit
) {
    val shape = MaterialTheme.shapes.medium

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = shape
            )
            .background(
                color = Color.White,  // Fondo blanco para mejor contraste
                shape = shape
            )
    ) {
        SectionHeader(
            title = item.title,
            isExpanded = item.isExpanded,
            onClick = onClick
        )

        AnimatedVisibility(visible = item.isExpanded) {
            HorizontalDivider()
            when (item.title) {
                "Buttons" -> ButtonShowcase()
                "TopBars" -> TopBarShowcase()
                "Inputs" -> InputShowcase()
                "Dropdowns" -> DropdownShowcase()
                "Segmented Tabs" -> SegmentedTabsShowcase()
                "MessageView" -> MyShowroomScreen()
                "Brand" -> BrandShowcase()
                "Toast" -> ToastShowcase()
                "Switch container" -> SwitchButtonShowcase()
                "Chip container" -> ChipButtonShowcase()
                "Order detail" -> OrderDetailShowcase()
                "Tags" -> StatusTagShowcase()
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, isExpanded: Boolean, onClick: () -> Unit) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "sectionHeaderRotation"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black  // Texto negro para contraste con fondo blanco
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = if (isExpanded) "Collapse section" else "Expand section",
            modifier = Modifier.rotate(rotationAngle),
            tint = Color.Black.copy(alpha = 0.6f)  // Icono gris oscuro
        )
    }
}

@Composable
fun ButtonShowcase() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Primary", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToteatPrimaryButton(onClick = {}, text = "Default")
            ToteatPrimaryButton(onClick = {}, text = "Disabled", enabled = false)
        }
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Secondary", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToteatSecondaryButton(
                onClick = {},
                text = "Default",
                leadingIcon = { Icon(Icons.Default.Add, null, modifier = Modifier.size(32.dp)) })
            ToteatSecondaryButton(
                onClick = {},
                text = "Disabled",
                enabled = false,
                leadingIcon = { Icon(Icons.Default.Add, null, modifier = Modifier.size(32.dp)) })
        }
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Tertiary", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToteatTertiaryButton(
                onClick = {},
                text = "Default",
                leadingIcon = {
                    Icon(
                        Icons.Default.RocketLaunch,
                        null,
                        modifier = Modifier.size(32.dp)
                    )
                })
            ToteatTertiaryButton(
                onClick = {},
                text = "Disabled",
                enabled = false,
                leadingIcon = {
                    Icon(
                        Icons.Default.RocketLaunch,
                        null,
                        modifier = Modifier.size(32.dp)
                    )
                })
        }

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToteatPrintButton(
                onClick = {},
                text = "Default",
                leadingIcon = { PrintIconButton(size = 32.dp) },
                modifier = Modifier.fillMaxWidth()
            )

        }
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Payment Buttons", style = MaterialTheme.typography.titleMedium)

        ToteatRectangleButton(
            title = "Pago total",
            subTitle = "de la cuenta",
            icon = { TotalPaymentsIcon(size = 32.dp) },
            onClick = {}
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ToteatSquareButton(
                title = "Dividir pago",
                subTitle = "En partes iguales",
                icon = { SplitPaymentIcon(size = 32.dp) },
                onClick = {}
            )
            Spacer(modifier = Modifier.width(24.dp))
            ToteatSquareButton(
                title = "Pagos por",
                subTitle = "montos diferentes",
                icon = { DifferentAmountPaymentsIcon(size = 32.dp) },
                onClick = {}
            )
        }
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Tables", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToteatButtonTable(
                tableName = "Mesa S7",
                waiterName = "Jaime",
                occupationTime = "16:02 hrs",
                tableStatus = ButtonTableStatus.OCCUPIED,
                onClick = {}
            )
            ToteatButtonTable(
                tableName = "Mesa S10",
                waiterName = "Disponible",
                occupationTime = "-",
                tableStatus = ButtonTableStatus.AVAILABLE,
                onClick = {}
            )
        }
    }
}

@Composable
fun ChipButtonShowcase() {
    var selectedItem by remember { mutableStateOf<String?>("Salon") }

    Column(
        modifier = Modifier.padding(vertical = 24.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Estado normal", style = MaterialTheme.typography.titleSmall)
        ToteatChipButtonContainer(
            items = persistentListOf("Salon", "Bar", "Terraza", "Vip"),
            selectedItem = selectedItem,
            onItemSelect = { selectedItem = it }
        )

        Text("Estado deshabilitado", style = MaterialTheme.typography.titleSmall)
        ToteatChipButtonContainer(
            items = persistentListOf("Salon", "Bar", "Terraza", "Vip"),
            selectedItem = "Bar",
            onItemSelect = {},
            enabled = false
        )
    }
}


@Composable
fun InputShowcase() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("TextField", style = MaterialTheme.typography.titleMedium)
        val textState = rememberTextFieldState()
        ToteatTextField(
            state = textState,
            title = "Email",
            placeHolder = "user@toteat.com",
            helperText = "Ingrese su correo",
            keyboardType = KeyboardType.Email,
            isSuccess = true
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Password", style = MaterialTheme.typography.titleMedium)
        val passwordState = rememberTextFieldState()
        val isPasswordVisible = remember { mutableStateOf(false) }
        ToteatPasswordTextField(
            state = passwordState,
            isPasswordVisible = isPasswordVisible.value,
            title = "Contraseña",
            placeHolder = "••••••••",
            helperText = "Mínimo 8 caracteres",
            isError = true,
            onToggleVisibilityClick = { isPasswordVisible.value = !isPasswordVisible.value }
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Phone Number", style = MaterialTheme.typography.titleMedium)
        val phoneState = rememberTextFieldState()
        val dialCodes = persistentListOf("+56", "+54", "+51", "+1")
        val selectedDialCode = remember { mutableStateOf(dialCodes.first()) }
        ToteatPhoneNumberField(
            state = phoneState,
            title = "Teléfono",
            placeHolder = "9 1234 5678",
            helperText = "Teléfono",
            isWarning = true,
            selectedDialCode = selectedDialCode.value,
            dialCodeOptions = dialCodes,
            onDialCodeChange = { selectedDialCode.value = it }
        )
    }
}

@Composable
fun DropdownShowcase() {
    var selectedOption by remember { mutableStateOf("") }
    val options = persistentListOf("Mesero 1", "Mesero 2", "Mesero 3")

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ToteatDropDown(
            label = "Meseros turno",
            options = options,
            selectedOption = selectedOption,
            onOptionSelect = { selectedOption = it }
        )
    }
}

@Composable
fun SwitchButtonShowcase() {
    var isChecked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.padding(16.dp)
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToteatSwitchButtonContainer(
            title = "Terminal compartido",
            subtitle = "Esta opción es para POS compartidas",
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ToastShowcase() {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
    }
}

@Composable
fun BrandShowcase() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Logos", style = MaterialTheme.typography.titleMedium)
        Text("Original", style = MaterialTheme.typography.bodyMedium)
        ToteatLogoOriginal()
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Text("Black and Cream", style = MaterialTheme.typography.bodyMedium)
        ToteatLogoBlackAndCream()
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Text("Cream and Orange", style = MaterialTheme.typography.bodyMedium)
        ToteatLogoCreamOrange()
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Isotipos", style = MaterialTheme.typography.titleMedium)
        Text("Original", style = MaterialTheme.typography.bodyMedium)
        ToteatIsoOriginal()
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Text("Black and Cream", style = MaterialTheme.typography.bodyMedium)
        ToteatIsoBlackAndCream()
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Text("Cream and Orange", style = MaterialTheme.typography.bodyMedium)
        ToteatIsoCreamOrange()
    }
}

@Composable
fun TopBarShowcase() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Back Navigation", style = MaterialTheme.typography.titleMedium)
        BackNavigationTopBar(
            title = "Mesa B1",
            onNavigateBackClick = {},
            testTag = "back_navigation_topbar"
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Back Navigation - Título largo", style = MaterialTheme.typography.titleMedium)
        BackNavigationTopBar(
            title = "Mesa principal con nombre muy largo que debe truncarse",
            onNavigateBackClick = {},
            testTag = "back_navigation_topbar_long_title"
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Login", style = MaterialTheme.typography.titleMedium)
        LoginTopBar(
            onMenuIconClick = {},
            onCustomerServiceButtonClick = {},
            testTag = "login_topbar"
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Center content: Restaurant", style = MaterialTheme.typography.titleMedium)
        CenterContentTopBar(
            content = {
                RestaurantNameTopBarItem(
                    restaurantName = "Kintaro ramen bar",
                )
            },
            testTag = "center_content_restaurant_topbar"
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text(
            "Center content: Restaurant - Nombre largo",
            style = MaterialTheme.typography.titleMedium
        )
        CenterContentTopBar(
            content = {
                RestaurantNameTopBarItem(
                    restaurantName = "Restaurante con nombre súper largo que debería mostrarse con marquee",
                )
            },
            testTag = "center_content_restaurant_long_topbar"
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Center content: Process", style = MaterialTheme.typography.titleMedium)
        CenterContentTopBar(
            content = {
                ProcessNameTopBarItem(
                    processName = "Checkout"
                )
            },
            testTag = "center_content_process_topbar"
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Center content: Process - Nombre largo", style = MaterialTheme.typography.titleMedium)
        CenterContentTopBar(
            content = {
                ProcessNameTopBarItem(
                    processName = "Proceso de confirmación y pago completo de la cuenta"
                )
            },
            testTag = "center_content_process_long_topbar"
        )
    }
}

@Composable
fun MyShowroomScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        WelcomeMessage(

            imageVector = Icons.Default.WavingHand,
            userName = "Jhon Doe",
            message = "Sesión iniciada con éxito"
        )
    }
}


@Composable
fun SegmentedTabsShowcase() {
    val tabs = persistentListOf("Resumen cuenta", "Información mesa")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SegmentedTabs(
            items = tabs,
            selectedIndex = selectedTabIndex,
            onTabSelect = { index ->
                selectedTabIndex = index
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        when (selectedTabIndex) {
            0 -> Text("Mostrando el contenido de Resumen de Cuenta...")
            1 -> Text("Mostrando el contenido de Información de Mesa...")
        }
    }
}

@Composable
fun OrderDetailShowcase() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val items = persistentListOf(
            OrderItem(
                name = "Copa de vino",
                quantity = 2,
                unitPrice = "$4.000",
                totalPrice = "$8.000",
                time = "21:12 hrs"
            ),
            OrderItem(
                name = "Bowl mediterraneo con jalea real y pasas al ron",
                quantity = 1,
                unitPrice = "$6.000",
                totalPrice = "$6.000",
                time = "21:12 hrs"
            ),
            OrderItem(
                name = "Lasagna vegetariana",
                quantity = 1,
                unitPrice = "$7.000",
                totalPrice = "$7.000",
                time = "21:12 hrs"
            ),
            OrderItem(
                name = "Bowl mediterraneo",
                quantity = 1,
                unitPrice = "$6.000",
                totalPrice = "$6.000",
                time = "21:12 hrs"
            ),
            OrderItem(
                name = "Risotto funghi",
                quantity = 2,
                unitPrice = "$8.000",
                totalPrice = "$24.000",
                time = "21:12 hrs",
                extras = persistentListOf(
                    OrderItemExtra(
                        name = "Extra plato 1:",
                        description = "Queso",
                        price = "$2.000"
                    ),
                    OrderItemExtra(
                        name = "Extra plato 2:",
                        description = "Filete",
                        price = "$6.000"
                    )
                )
            ),
            OrderItem(
                name = "Bowl mediterraneo",
                quantity = 1,
                unitPrice = "$6.000",
                totalPrice = "$6.000",
                time = "21:12 hrs"
            )
        )

        GroupedOrderDetail(
            items = items,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun StatusTagShowcase() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Status Tags", style = MaterialTheme.typography.titleMedium)

        Text("Pending", style = MaterialTheme.typography.bodyMedium)
        StatusTag(variant = StatusTagVariant.Pending)
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Confirmed", style = MaterialTheme.typography.bodyMedium)
        StatusTag(variant = StatusTagVariant.Confirmed)
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Ended", style = MaterialTheme.typography.bodyMedium)
        StatusTag(variant = StatusTagVariant.Ended)
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Cancelled", style = MaterialTheme.typography.bodyMedium)
        StatusTag(variant = StatusTagVariant.Cancelled)
    }
}
