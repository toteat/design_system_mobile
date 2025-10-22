package com.toteat.designsystemmobile

import WelcomeMessage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import com.toteat.toteatds.components.DropDowns.AppDropdown
import com.toteat.toteatds.components.SegmentButtons.SegmentedTabs
import com.toteat.toteatds.components.buttons.PrimaryButton
import com.toteat.toteatds.components.buttons.SecondaryButton
import com.toteat.toteatds.components.buttons.TertiaryButton
import com.toteat.toteatds.components.buttons.ToteatRectangleButton
import com.toteat.toteatds.components.buttons.ToteatSquareButton
import com.toteat.toteatds.components.icons.TotalPaymentsIcon
import com.toteat.toteatds.components.icons.SplitPaymentIcon
import com.toteat.toteatds.components.icons.DifferentAmountPaymentsIcon
import com.toteat.toteatds.components.brand.iso.ToteatIsoBlackAndCream
import com.toteat.toteatds.components.brand.iso.ToteatIsoCreamOrange
import com.toteat.toteatds.components.brand.iso.ToteatIsoOriginal
import com.toteat.toteatds.components.brand.logo.ToteatLogoBlackAndCream
import com.toteat.toteatds.components.brand.logo.ToteatLogoCreamOrange
import com.toteat.toteatds.components.brand.logo.ToteatLogoOriginal
import com.toteat.toteatds.components.buttons.ButtonTableStatus
import com.toteat.toteatds.components.buttons.PrimaryButton
import com.toteat.toteatds.components.buttons.SecondaryButton
import com.toteat.toteatds.components.buttons.TertiaryButton
import com.toteat.toteatds.components.buttons.ToteatButtonTable
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
import org.jetbrains.compose.ui.tooling.preview.Preview

data class ComponentShowcaseItem(
    val title: String,
    var isExpanded: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    ToteatTheme {
        val componentList = remember {
            mutableStateListOf(
                ComponentShowcaseItem(title = "Buttons"),
                ComponentShowcaseItem(title = "TopBars"),
                ComponentShowcaseItem(title = "Dropdowns"),
                ComponentShowcaseItem(title = "Inputs"),
                ComponentShowcaseItem(title = "Cards"),
                ComponentShowcaseItem(title = "Segmented Tabs"),
                ComponentShowcaseItem(title = "MessageView"),
                ComponentShowcaseItem(title = "Brand"),
                ComponentShowcaseItem(title = "Toast"),
            )
        }

        Scaffold(
            topBar = {
                CenterContentTopBar(
                    content = {
                        ProcessNameTopBarItem("Design System Showcase")
                    }
                )
            },

            ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(componentList) { index, item ->
                    ComponentShowcaseSection(
                        item = item,
                        onClick = {
                            componentList[index] = item.copy(isExpanded = !item.isExpanded)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ComponentShowcaseSection(
    item: ComponentShowcaseItem,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {
            SectionHeader(
                title = item.title,
                isExpanded = item.isExpanded,
                onClick = onClick
            )

            AnimatedVisibility(visible = item.isExpanded) {
                when (item.title) {
                    "Buttons" -> ButtonShowcase()
                    "TopBars" -> TopBarShowcase()
                    "Inputs" -> InputShowcase()
                    "Dropdowns" -> DropdownShowcase()
                    "Segmented Tabs" -> SegmentedTabsShowcase()
                    "MessageView" -> MyShowroomScreen()
                    "Brand" -> BrandShowcase()
                    "Toast" -> ToastShowcase()

                    else -> Text(
                        text = "Componentes próximamente...",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, isExpanded: Boolean, onClick: () -> Unit) {
    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Expand section",
            modifier = Modifier.rotate(rotationAngle)
        )
    }
}

@Composable
fun ButtonShowcase() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // --- Primary Buttons ---
        Text("Primary", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryButton(onClick = {}, text = "Default")
            PrimaryButton(onClick = {}, text = "Disabled", enabled = false)
        }
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Secondary", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SecondaryButton(onClick = {}, text = "Default", leadingIcon = { Icon(Icons.Default.Add, null) })
            SecondaryButton(onClick = {}, text = "Disabled", enabled = false, leadingIcon = { Icon(Icons.Default.Add, null) })
        }
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Tertiary", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TertiaryButton(onClick = {}, text = "Default", leadingIcon = { Icon(Icons.Default.RocketLaunch, null) })
            TertiaryButton(onClick = {}, text = "Disabled", enabled = false, leadingIcon = { Icon(Icons.Default.RocketLaunch, null) })
        }

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        // --- Botones de pago ---
        Text("Payment Buttons", style = MaterialTheme.typography.titleMedium)

        // Rectangle button
        ToteatRectangleButton(
            title = "Pago total",
            subTitle = "de la cuenta",
            icon = { TotalPaymentsIcon() },
            onClick = {}
        )

        // Two square buttons below
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ToteatSquareButton(
                title = "Dividir pago",
                subTitle = "En partes iguales",
                icon = { SplitPaymentIcon() },
                onClick = {}
            )
            Spacer(modifier = Modifier.width(24.dp))
            ToteatSquareButton(
                title = "Pagos por",
                subTitle = "montos diferentes",
                icon = { DifferentAmountPaymentsIcon() },
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
fun InputShowcase() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // --- Text Field ---
        Text("TextField", style = MaterialTheme.typography.titleMedium)
        val textState = rememberTextFieldState()
        ToteatTextField(
            state = textState,
            title = "Email",
            placeHolder = "user@toteat.com",
            helperText = "Ingrese su correo",
            isSuccess = true
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        // --- Password Field ---
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
            onToggleVisibilityCLick = { isPasswordVisible.value = !isPasswordVisible.value }
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        // --- Phone Number Field ---
        Text("Phone Number", style = MaterialTheme.typography.titleMedium)
        val phoneState = rememberTextFieldState()
        val dialCodes = listOf("+56", "+54", "+51", "+1")
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
    val options = listOf("Mesero 1", "Mesero 2", "Mesero 3")

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AppDropdown(
            label = "Meseros turno",
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it }
        )
    }
}

@Composable
fun ToastShowcase() {
    Column(modifier = Modifier.fillMaxSize()
        .background(Color.White)
        .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
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
        // Logos
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

        // Isotipos
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
        BackNavigationTopBar(onNavigateBackClick = {})
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Login", style = MaterialTheme.typography.titleMedium)
        LoginTopBar(onMenuIconClick = {}, onCustomerServiceButtonClick = {})
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Center content: Restaurant", style = MaterialTheme.typography.titleMedium)
        CenterContentTopBar {
            RestaurantNameTopBarItem(
                restaurantName = "Kintaro ramen bar",
                counter = "2"
            )
        }
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Center content: Process", style = MaterialTheme.typography.titleMedium)
        CenterContentTopBar {
            ProcessNameTopBarItem(
                processName = "Checkout"
            )
        }
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
    // 1. Define las opciones y el estado de la selección
    val tabs = listOf("Resumen cuenta", "Información mesa")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 2. Llama a tu componente
        SegmentedTabs(
            items = tabs,
            selectedIndex = selectedTabIndex,
            onTabSelected = { index ->
                // Cuando el usuario selecciona una pestaña, actualizamos el estado
                selectedTabIndex = index
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 3. (Opcional) Muestra un contenido diferente según la selección
        when (selectedTabIndex) {
            0 -> Text("Mostrando el contenido de Resumen de Cuenta...")
            1 -> Text("Mostrando el contenido de Información de Mesa...")
        }
    }
}

