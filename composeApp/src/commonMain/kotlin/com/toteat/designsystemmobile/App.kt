package com.toteat.designsystemmobile

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
import androidx.compose.material.icons.filled.ChevronRight
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.bottombar.AmountBottomBar
import com.toteat.toteatds.components.bottombar.FloatingTotalBar
import com.toteat.toteatds.components.bottombar.ToteatBottomBar
import com.toteat.toteatds.components.bottombar.ToteatBottomBarButtonType
import com.toteat.toteatds.components.cards.ToteatCategoryCard
import com.toteat.toteatds.components.cards.ToteatProductRow
import com.toteat.toteatds.components.cards.ToteatSubcategoryButton
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
import com.toteat.toteatds.components.list.ProductCard
import com.toteat.toteatds.components.list.ProductCardGroup
import com.toteat.toteatds.components.list.ProductCardItem
import com.toteat.toteatds.components.list.ProductCardStatus
import com.toteat.toteatds.components.messageview.WelcomeMessage
import com.toteat.toteatds.components.segmentbuttons.SegmentedTabs
import com.toteat.toteatds.components.segmentbuttons.TabSelectorBadge
import com.toteat.toteatds.components.segmentbuttons.TabSelectorBadgeItem
import com.toteat.toteatds.components.segmentbuttons.TabSelectorBadgeVariant
import com.toteat.toteatds.components.tags.StatusTag
import com.toteat.toteatds.components.tags.StatusTagVariant
import com.toteat.toteatds.components.textfields.ToteatPasswordTextField
import com.toteat.toteatds.components.textfields.ToteatPhoneNumberField
import com.toteat.toteatds.components.textfields.ToteatSearchBar
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
                    ComponentShowcaseItem(title = "Floating Total Bar"),
                    ComponentShowcaseItem(title = "Amount Bottom Bar"),
                    ComponentShowcaseItem(title = "TopBars"),
                    ComponentShowcaseItem(title = "Dropdowns"),
                    ComponentShowcaseItem(title = "Inputs"),
                    ComponentShowcaseItem(title = "Segmented Tabs"),
                    ComponentShowcaseItem(title = "Tab Selector Badge"),
                    ComponentShowcaseItem(title = "MessageView"),
                    ComponentShowcaseItem(title = "Brand"),
                    ComponentShowcaseItem(title = "Toast"),
                    ComponentShowcaseItem(title = "Switch container"),
                    ComponentShowcaseItem(title = "Chip container"),
                    ComponentShowcaseItem(title = "Category Cards"),
                    ComponentShowcaseItem(title = "Product Rows"),
                    ComponentShowcaseItem(title = "Subcategory Buttons"),
                    ComponentShowcaseItem(title = "Order detail"),
                    ComponentShowcaseItem(title = "Product card"),
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
                "Floating Total Bar" -> FloatingTotalBarShowcase()
                "Amount Bottom Bar" -> AmountBottomBarShowcase()
                "TopBars" -> TopBarShowcase()
                "Inputs" -> InputShowcase()
                "Dropdowns" -> DropdownShowcase()
                "Segmented Tabs" -> SegmentedTabsShowcase()
                "Tab Selector Badge" -> TabSelectorBadgeShowcase()
                "MessageView" -> MyShowroomScreen()
                "Brand" -> BrandShowcase()
                "Toast" -> ToastShowcase()
                "Switch container" -> SwitchButtonShowcase()
                "Chip container" -> ChipButtonShowcase()
                "Category Cards" -> CategoryCardShowcase()
                "Product Rows" -> ProductRowShowcase()
                "Subcategory Buttons" -> SubcategoryButtonShowcase()
                "Order detail" -> OrderDetailShowcase()
                "Product card" -> ProductCardShowcase()
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
fun FloatingTotalBarShowcase() {
    var productCount by remember { mutableIntStateOf(2) }
    val unitPrice = 14645
    val total = productCount * unitPrice
    val formattedTotal = remember(total) { formatAmountWithThousands(total) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Bottom bar flotante", style = MaterialTheme.typography.titleMedium)
        Text("Productos en pedido: $productCount", style = MaterialTheme.typography.bodyMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToteatSecondaryButton(
                onClick = { productCount += 1 },
                text = "Agregar"
            )
            ToteatSecondaryButton(
                onClick = { if (productCount > 0) productCount -= 1 },
                text = "Quitar",
                enabled = productCount > 0
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.medium
                )
                .background(color = Color.White, shape = MaterialTheme.shapes.medium)
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.White,
                bottomBar = {
                    FloatingTotalBar(
                        totalAmount = formattedTotal,
                        label = if (productCount > 0) "Ver pedido mesa" else null,
                        onClick = {}
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        "Ejemplo en Scaffold(bottomBar).",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "Total actualizado: $formattedTotal",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun AmountBottomBarShowcase() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Amount Bottom Bar", style = MaterialTheme.typography.titleMedium)

        Text("Estado colapsado", style = MaterialTheme.typography.bodyMedium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.medium
                )
                .background(color = Color.White, shape = MaterialTheme.shapes.medium)
        ) {
            AmountBottomBar(
                subtotalAmount = "$ 32.780",
                paidAmount = "$ 0",
                amountToPay = "$32.780",
                onPrintPreBillClick = {},
                onConfirmClick = {},
                onPayClick = {}
            )
        }

        Text("Estado expandido", style = MaterialTheme.typography.bodyMedium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.medium
                )
                .background(color = Color.White, shape = MaterialTheme.shapes.medium)
        ) {
            AmountBottomBar(
                subtotalAmount = "$ 32.780",
                paidAmount = "$ 0",
                amountToPay = "$32.780",
                initialPaidAmountExpanded = true,
                onPrintPreBillClick = {},
                onConfirmClick = {},
                onPayClick = {}
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

        Text("SearchBar", style = MaterialTheme.typography.titleMedium)
        var searchValue by remember { mutableStateOf("") }
        ToteatSearchBar(
            value = searchValue,
            onValueChange = { searchValue = it },
            placeholder = "Buscar productos..."
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

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
fun TabSelectorBadgeShowcase() {
    val itemsWithBadge = persistentListOf(
        TabSelectorBadgeItem("Carta"),
        TabSelectorBadgeItem("Pedido", badgeCount = 8)
    )
    val baseItems = persistentListOf(
        TabSelectorBadgeItem("Carta"),
        TabSelectorBadgeItem("Pedido")
    )
    var selectedTabIndex by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Base + badge", style = MaterialTheme.typography.titleMedium)
        TabSelectorBadge(
            items = itemsWithBadge,
            selectedIndex = selectedTabIndex,
            onTabSelect = { selectedTabIndex = it }
        )

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Variantes", style = MaterialTheme.typography.titleMedium)
        TabSelectorBadge(
            items = baseItems,
            selectedIndex = 0,
            onTabSelect = {},
            variant = TabSelectorBadgeVariant.DARK
        )
        TabSelectorBadge(
            items = baseItems,
            selectedIndex = 0,
            onTabSelect = {},
            variant = TabSelectorBadgeVariant.CREAM
        )
        TabSelectorBadge(
            items = baseItems,
            selectedIndex = 0,
            onTabSelect = {},
            variant = TabSelectorBadgeVariant.GRAY
        )
        TabSelectorBadge(
            items = baseItems,
            selectedIndex = 0,
            onTabSelect = {},
            variant = TabSelectorBadgeVariant.PRIMARY
        )
    }
}

@Preview
@Composable
private fun TabSelectorBadgeShowcasePreview() {
    ToteatTheme {
        TabSelectorBadgeShowcase()
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
fun ProductCardShowcase() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Single", style = MaterialTheme.typography.titleMedium)
        ProductCard(
            name = "Hamburguesa BBQ",
            description = "Unitario: $7.000",
            price = "$14.000",
            quantity = 2,
            status = ProductCardStatus.PENDING,
            showDeleteButton = true,
            onDeleteClick = {}
        )

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Grouped", style = MaterialTheme.typography.titleMedium)
        ProductCardGroup(
            maxCollapsedItems = 3,
            items = persistentListOf(
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: $5.290",
                    price = "$5.290",
                    quantity = 1,
                    status = ProductCardStatus.PENDING,
                    onClick = {}
                ),
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: $5.290",
                    price = "$5.290",
                    quantity = 1,
                    status = ProductCardStatus.PENDING,
                    onClick = {}
                ),
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: $5.290",
                    price = "$5.290",
                    quantity = 1,
                    status = ProductCardStatus.PENDING,
                    onClick = {}
                ),
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: $5.290",
                    price = "$5.290",
                    quantity = 1,
                    status = ProductCardStatus.PENDING,
                    onClick = {}
                ),
                ProductCardItem(
                    name = "Mojito tradicional",
                    description = "Unitario: $5.290",
                    price = "$5.290",
                    quantity = 1,
                    status = ProductCardStatus.PENDING,
                    onClick = {}
                )
            ))
    }
}

@Composable
fun CategoryCardShowcase() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Default", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToteatCategoryCard(
                name = "Entradas",
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            ToteatCategoryCard(
                name = "Bebidas",
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToteatCategoryCard(
                name = "Platos de fondo",
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            ToteatCategoryCard(
                name = "Café y postres",
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Text("Disabled", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToteatCategoryCard(
                name = "Entradas",
                onClick = {},
                enabled = false,
                modifier = Modifier.weight(1f)
            )
            ToteatCategoryCard(
                name = "Bebidas",
                onClick = {},
                enabled = false,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ProductRowShowcase() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        var quantity1 by remember { mutableIntStateOf(0) }
        var quantity2 by remember { mutableIntStateOf(1) }
        var quantity3 by remember { mutableIntStateOf(3) }

        Text("quantity = 0 (solo +)", style = MaterialTheme.typography.titleMedium)
        ToteatProductRow(
            name = "Copa de vino",
            price = "$ 3.690",
            description = "Vino tinto reserva",
            quantity = quantity1,
            onIncrement = { quantity1++ },
            onDecrement = { if (quantity1 > 0) quantity1-- }
        )

        Text("quantity = 1 (basura + 1 + plus)", style = MaterialTheme.typography.titleMedium)
        ToteatProductRow(
            name = "Mojito tradicional",
            price = "$ 5.290",
            description = "Descripción del componente (opcional)",
            quantity = quantity2,
            onIncrement = { quantity2++ },
            onDecrement = { if (quantity2 > 0) quantity2-- }
        )

        Text("quantity > 1 (minus + N + plus)", style = MaterialTheme.typography.titleMedium)
        ToteatProductRow(
            name = "Capuccino espresso doble con leche de almendras",
            price = "$ 4.500",
            quantity = quantity3,
            onIncrement = { quantity3++ },
            onDecrement = { if (quantity3 > 0) quantity3-- }
        )

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Text("Disabled", style = MaterialTheme.typography.titleMedium)
        ToteatProductRow(
            name = "Capuccino",
            price = "$ 3.690",
            description = "Descripción del componente (opcional)",
            quantity = 2,
            onIncrement = {},
            onDecrement = {},
            enabled = false
        )
    }
}

@Composable
fun SubcategoryButtonShowcase() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Default", style = MaterialTheme.typography.titleMedium)
        ToteatSubcategoryButton(
            name = "Cafés y bebidas calientes",
            onClick = {},
            trailingIcon = {
                Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(20.dp))
            }
        )

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Text("Disabled", style = MaterialTheme.typography.titleMedium)
        ToteatSubcategoryButton(
            name = "Cafés y bebidas calientes",
            onClick = {},
            enabled = false,
            trailingIcon = {
                Icon(Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(20.dp))
            }
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

fun formatAmountWithThousands(amount: Int): String {
    val safeAmount = if (amount < 0) 0 else amount
    val grouped = safeAmount.toString()
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
    return "\$ $grouped"
}
