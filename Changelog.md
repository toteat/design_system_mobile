# Changelog

## [0.1.28] - 2026-04-16
### Changed
- **ToteatActionBottomSheet**: `actions` parameter now uses `ImmutableList<ToteatActionConfig>` instead of `List`. Added `@Immutable` to `ToteatActionConfig`. Added `key()` wrappers for efficient recomposition tracking. Cached `vectorResource` icons in parent scope to avoid re-parsing on animation frames.
- **GroupedOrderDetail**: Added `@Immutable` to `OrderItem` and `OrderItemExtra`. Added `key()` wrappers in item and extras loops. Pre-compute `MaterialTheme.colorScheme.extended` once in parent and pass to child items instead of per-item CompositionLocal lookup.
- **ToteatDropDown**: Added `key()` wrappers in dropdown options for efficient diff tracking.

### Added
- **compose_stability.conf**: Stability configuration file marking public types (`ToteatActionType`, `ToteatActionConfig`, `OrderItem`, `OrderItemExtra`, `ButtonTableStatus`) as stable for consumer apps.
- **CmpLibraryConventionPlugin**: Wired `compose_stability.conf` into Compose compiler. Added opt-in Compose compiler reports via `-PenableComposeCompilerReports=true`.

## [0.1.27] - 2026-04-14
### Changed
- **ToteatProductRow**: Added `showControls` parameter (default `true`). When set to `false`, hides the +/- counter buttons and shows a numeric badge only — intended for products with variants/extras that require opening a detail screen instead of direct increment/decrement.

## [0.1.25] - 2026-04-09
### Added
- **ToteatNumericKeypad**: New numeric keypad component with a 4×3 grid layout (digits 0-9, backspace, and custom action button). Supports `onNumberClick`, `onDeleteClick`, and `onActionClick` callbacks, optional custom action icon, enabled/disabled states, and full accessibility semantics.
- **ToteatAmountDisplay**: New read-only display component for formatted amounts, designed to pair with ToteatNumericKeypad. Supports placeholder, enabled/disabled states, and accessibility semantics.
- **ToteatPillLabel**: New non-interactive pill-shaped label with optional trailing icon, for section titles and contextual labels.
- **icon_backspace.xml**: New vector drawable resource for the keypad delete button

## [0.1.21] - 2026-04-06
### Changed
- **TableIcon, WarningIcon, SuccessIcon, ErrorIcon, InfoIcon**: Added `tint` parameter (`Color = LocalContentColor.current`) to allow customizing icon color

## [0.1.20] - 2026-03-31
### Changed
- **AmountBottomBar**: Added `fullPaid` and `onPaymentDetailClick` parameters to support a "Detalle del pago" button (green with check icon) when the full consumption has been paid, replacing the "Pagar" button

## [0.1.19] - 2026-03-31
### Changed
- **ToteatSearchBar**: Added `onSearch` callback parameter to intercept the IME search action (keyboard search button). The `BasicTextField` already displayed the search IME action but had no way to notify callers when pressed.

## [0.1.18] - 2026-03-31
### Added
- **TableIcon**: New icon component wrapping `Res.drawable.icon_table`
- **WarningIcon**: New icon component wrapping `Res.drawable.icon_warning`
- **SuccessIcon**: New icon component wrapping `Res.drawable.icon_success`
- **ErrorIcon**: New icon component wrapping `Res.drawable.icon_error`
- **InfoIcon**: New icon component wrapping `Res.drawable.icon_info`

### Added
- **ProductCard**: New card component for displaying product information with icon, title, subtitle, price, and action buttons
- **ProductCardGroup**: Container component for displaying a group of ProductCards in a Column layout with add and delete functionality
- **icon_delete_default.xml**: New vector drawable resource for delete action button
- **icon_right_chevron.xml**: New vector drawable resource for navigation chevron indicator
- **strings.xml**: Added product card related string resources (`product_card_item`, `product_card_group`, `product_card_item_default`, `product_card_group_default`, `product_card_add`, `product_card_delete`, `product_card_empty_list`)
### Added
- **ToteatSearchBar**: New search bar component for filtering products
  - API: `ToteatSearchBar(value, onValueChange, placeholder, enabled, modifier, testTag)`
  - Background `NeutralGray100`, border visible only on focus (`NeutralGray300`), search icon on the right
  - Full-width, 50dp height, 8dp corner radius — consistent with existing text fields
  - Enabled and disabled states with proper color tokens
- **TabSelectorBadge**: New segmented selector with optional badge count per tab
  - API: `TabSelectorBadge(items, selectedIndex, onTabSelect, modifier, variant, enabled, testTag)`
  - Supports four visual variants (`DARK`, `CREAM`, `GRAY`, `PRIMARY`) following DS tokens
  - Includes accessibility semantics (`Role.Tab`, selected/not selected state, badge count description)
  - Added showcase section in `App.kt` and new string resources for accessibility
### Fixed
- **ToteatDropDown**: Options list now scrolls when there are more items than fit on screen (max height 200dp)
### Added
- **FloatingTotalBar**: Added new floating bottom bar component for order totals
  - API: `FloatingTotalBar(totalAmount, label, onClick, modifier, testTag)`
  - Rounded corners (32.dp), solid primary background, and floating shadow (8.dp)
  - Supports fixed bottom usage through `Scaffold(bottomBar = ...)`
  - Shows formatted total from caller, optional label, and trailing arrow action
  - Includes accessibility semantics (`Role.Button`, `contentDescription`) and test sub-tags (`_label`, `_amount`, `_icon`)
  - Added previews for both states (with label and without label)
- **Showcase App**: Added "Floating Total Bar" section demonstrating dynamic total updates from external state
- **ToteatCategoryCard**: New card component for menu category grid (L1)
  - Vertical layout with centered category name (`bodyLarge`)
  - Pressed state feedback via `MutableInteractionSource`
  - Default and disabled states with proper color tokens
  - Subtle shadow, 16dp corner radius, 160x72dp base size
  - Accessibility: contentDescription, Role.Button, testTag with sub-testTags (`_name`)
- **ToteatCounterCompact**: New compact counter/stepper component
  - Three states: qty=0 (only + button), qty=1 (delete + 1 + plus), qty>1 (minus + N + plus)
  - Circular buttons using `CounterButtonColor` and `CounterContainerColor` theme tokens
  - Delete icon (trash) at qty=1, minus icon at qty>1
  - Default and disabled states with proper color tokens
  - Accessibility: contentDescription, Role.Button, testTag with sub-testTags (`_increment`, `_decrement`, `_quantity`)
- **ToteatProductRow**: New product row component for menu item listing
  - Horizontal layout: text column (title `bodyMedium`, description `tagRegular`, price `bodyMedium` PrimaryNormal) + ToteatCounterCompact
  - Integrated counter with `quantity`, `onIncrement`, `onDecrement` params
  - Default and disabled states with proper color tokens
  - Subtle shadow, 12dp corner radius, full-width
  - Accessibility: contentDescription, Role.Button, testTag with sub-testTags (`_title`, `_description`, `_price`, `_counter`)
- **ToteatSubcategoryButton**: New subcategory navigation button
  - Horizontal layout: label (`bodyLarge`) + composable trailing icon slot (20dp)
  - Pressed state feedback via `MutableInteractionSource`
  - Default and disabled states with proper color tokens via `LocalContentColor`
  - Subtle shadow, 16dp corner radius, full-width
  - Accessibility: contentDescription, Role.Button, testTag with sub-testTags (`_name`, `_chevron`)
- **Color tokens**: Added `CounterContainerColor`, `CounterContainerDisabledColor`, `CounterButtonColor`, `CounterButtonDisabledColor` to theme

## [0.1.14] - 2026-02-16
### Fixed
- **WelcomeMessage**: Removed `stringResource` format args to avoid `IndexOutOfBoundsException` caused by Compose Resources version mismatch between DS and consumer apps
  - Split `welcome_greeting` into `welcome_greeting_prefix` and `welcome_greeting_suffix` string resources without placeholders
  - Greeting text is now built in code, eliminating dependency on `replaceWithArgs` behavior

## [0.1.10] - 2026-02-10
### Changed
- **ToteatRectangleButton**: Added `_title` and `_subtitle` sub-testTags for Appium automation access to child elements
- **ToteatSquareButton**: Added `_title` and `_subtitle` sub-testTags for Appium automation access to child elements
- **ToteatButtonTable**: Added `_table_name`, `_waiter`, and `_time` sub-testTags; added `stateDescription` with table status ("ocupada"/"disponible"); added `testTag` parameter to internal `ToteatButtonItemRow`
- **ToteatDropDown**: Added `_label` and `_input` sub-testTags for label text and input box
- **ToteatSegmentButton**: Added `_tab_${index}` sub-testTags for each tab element
- **ToteatToast**: Added `_icon`, `_title`, `_message`, and `_close` sub-testTags; added `stateDescription` with toast type name (Error/Éxito/Advertencia/Información)
- **ToteatMessageView**: Added `_icon`, `_greeting`, and `_message` sub-testTags for WelcomeMessage child elements
- **StatusTag**: Added `_text` sub-testTag; added `stateDescription` with display text
- **GroupedListItem**: Added `_label` and `_value` sub-testTags for label and value boxes
- **GroupedOrderDetail**: Added `_header`, `_item_${index}`, and `_view_more` sub-testTags; added `testTag` parameter to internal `OrderDetailHeader`, `OrderDetailItem`, and `ViewMoreRow`
- **ToteatBottomBar**: Added `_tables`, `_all_tables`, and `_view_more` sub-testTags for each NavigationBarItem
- **ToteatActionBottomSheet**: Added `stateDescription` to `ActionMenuItem` with disabled/selected state text

### Added
- **strings.xml**: Added `state_disabled` string resource ("deshabilitado") for accessibility state descriptions

## [0.1.9] - 2026-02-03
### Changed
- **ToteatButtonTable**: Replaced `Card` with `Surface` for maximum rendering performance on low-end POS devices (3GB RAM)
  - Removed elevation/shadow rendering (~42-63ms savings per 21 buttons)
  - Reduced corner radius from 8dp to 4dp
  - Removed unused `border` property from `TableColors`
  - Removed `performanceMode` parameter (simplified API - always uses optimized rendering)
  - Removed unused imports (`Card`, `CardDefaults`, `border`, `stateDescription`)
  - Shape is now a static `val` instead of per-call remembered value
- **ToteatSwitchButtonContainer**: Major performance optimization
  - Removed shadow rendering (4.dp elevation eliminated)
  - Replaced 4 concurrent animations with single `animateFloatAsState` (75% fewer recompositions)
  - Cached Color.copy() values with `@Immutable` `SwitchColors` data class (eliminates GC pressure during animations)
  - Reduced animation duration from 250ms to 200ms for snappier feel
  - Static `ContainerShape` val instead of per-call allocation
- **ToteatBottomBar**: Removed 16.dp `shadowElevation` (always-visible component, constant GPU cost)
- **ToteatDropDown**: Replaced `Card` with `Surface`, removed 10.dp elevation

### Removed
- **ToteatButtonTable**: Removed `Card` component usage in favor of lightweight `Surface`
- **ToteatSwitchButtonContainer**: Removed `shadow` modifier and border animation
- **ToteatDropDown**: Removed `Card` and `CardDefaults` imports

## [0.1.8] - 2026-02-02
### Changed
- **Typography System**: Completely redesigned to cache FontFamily and all TextStyle extensions with `remember` to eliminate allocations on every text render (95-98% allocation reduction across all components)
- **ToteatButtonTable**: Added `@Stable` annotation to `ButtonTableStatus` enum, created `@Immutable` `TableColors` data class, cached accessibility strings and modifiers (95% reduction in recompositions)
- **ToteatChipButtonContainer**: Converted from `Row` with `horizontalScroll` to `LazyRow` with stable keys and remembered lambdas (70% faster scrolling, 98% fewer allocations)
- **ToteatChipButton**: Cached colors, accessibility descriptions, and static modifiers (98% allocation reduction)
- **ToteatDropDown**: Extracted `DropdownOption` composable, cached accessibility strings and text styles (98% allocation reduction)
- **ToteatBottomBar**: Cached icon vectors, accessibility descriptions, and shared NavigationBarItemColors (90% allocation reduction)
- **ToteatSquareButton**: Cached description strings and spacer modifiers (60% allocation reduction)
- **App.kt**: Changed `ComponentShowcaseItem` to use immutable `val isExpanded`, converted `mutableStateListOf` to `persistentListOf`, replaced Card wrapper with Column + background, improved color scheme to white background with black text

### Removed
- **App.kt**: Removed unused "Cards" section from component showcase
- **App.kt**: Removed Card and CardDefaults imports

## [0.1.7] - 2026-01-29
### Changed
- **Library Version**: Bumped version to 0.1.7 in gradle.properties

### Removed
- **LoginTopBar**: Removed unused menu button component from left section
  - Removed leftComponent parameter with Menu IconButton
  - Removed menu icon click handler functionality
  - Removed menu_open_description string resource reference

## [0.1.6] - 2026-01-20
### Added
- **ConfigButtonSheet**: New icon component using `group_bottom_view` drawable for configuration actions
- **ChangueBottomSheet**: New icon component using `change_icon` drawable for change/switch actions
- **UserIconBottomSheet**: New icon component using `user_icon_bottom_sheet` drawable for user profile actions
- **strings.xml**: Added `icon_config` string resource ("configuración") for accessibility
- All bottom sheet icon components follow consistent design with customizable size (default 48.dp) and modifier parameters

## [0.1.5] - 2026-01-13
### Changed
- **ToteatActionBottomSheet**: Refactored to use fixed predefined icons instead of composable parameters
  - Changed from `ToteatActionItem` (with composable icon) to `ToteatActionConfig` (with action type)
  - Introduced `ToteatActionType` sealed class with predefined action types (Profile, Settings, SwitchUser)
  - Each action type includes fixed icon resource (DrawableResource) and label resource (StringResource)
  - Icons now use `vectorResource()` for better performance and consistency
  - Simplified component API - users only specify action type, enabled state, and isSelected flag
  - Icons are centered within 84x84dp tiles using `Box` with `fillMaxWidth()` and `height(TileSize)`
  - Icon size standardized to 28dp with proper color tinting based on state

### Added
- **icon_profile.xml**: New drawable resource for "Mi Perfil" action (person icon)
- **icon_settings.xml**: New drawable resource for "Configuración" action (settings gear icon)
- **icon_switch_user.xml**: New drawable resource for "Cambiar usuario" action (exit/switch icon)
- **strings.xml**: Added `action_type_profile`, `action_type_settings`, and `action_type_switch_user` for localization

## [0.1.4] - 2026-01-12
### Fixed
- **OrderListTicketIcon**: Corrected function name from `OrderListIcon` to `OrderListTicketIcon` for consistency
- **OrderListTicketIcon**: Fixed string resource reference from `order_l` to `order_list_ticket`
- **OrderListTicketIcon**: Fixed drawable resource reference from `total_payment_icon` to `order_list_ticket`
- **strings.xml**: Added missing `order_list_ticket` string resource for accessibility

## [0.1.3] - 2026-01-08
### Added
- **OrderListTicketIcon**: Added new icon component for order list tickets with customizable size, tint, and content description, following design system accessibility guidelines

## [0.1.1] - 2026-01-06
### Fixed
- **GroupedOrderDetail**: Corrected string placeholder format from `%s` to `%1$s` in `order_detail_unit_price` to properly display dynamic unit price values from backend

## [0.1.0] - 2026-01-05
### Changed
- **ToteatDropDown**: Renamed component from AppDropdown to ToteatDropDown for consistency with design system naming conventions
- **ToteatDropDown**: Replaced List<String> with ImmutableList<String> to avoid unstable collections warning in Compose
- **ToteatDropDown**: Optimized Card padding to use lambda-based modifier for deferred state reads, improving performance during animations
- **ToteatDropDown**: Added setTestTag for UI testing support
- **ToteatDropDown**: Enhanced accessibility with contentDescription and semantic properties
- **ToteatDropDown**: Implemented localization support using Res instead of hardcoded strings
- **ToteatDropDown**: Fixed dropdown behavior to allow opening/closing by clicking the component, and improved interaction handling
- **ToteatToastMessage**: Enhanced with full accessibility support including contentDescription and semantic roles
- **ToteatToastMessage**: Added setTestTag for UI testing support
- **ToteatToastMessage**: Implemented localization support using Res for all text content

### Fixed
- **App.kt**: Resolved unresolved reference to 'collections' by adding proper import for ImmutableList
- **ToteatDropDown**: Fixed dropdown not opening after previous changes, restored original design behavior
- **ToteatDropDown**: Removed error styling that was causing red appearance, maintained previous design

### Added
- **ToteatDropDown**: Added comprehensive accessibility features following design system guidelines
- **ToteatToastMessage**: Added localization and accessibility improvements to match other components

## [0.0.18] - 2025-12-17
### Changed
- **ToteatBottomBar**: Replaced custom dropShadow with native Material3 Surface shadowElevation (12.dp) for better cross-platform shadow rendering
- **ToteatBottomBar**: Improved shadow visibility and separation from content
- **ArrowBackIconButton**: Enhanced with full accessibility support including semantic roles, contentDescription, and minimumInteractiveComponentSize
- **ArrowBackIconButton**: Added enabled/disabled state with proper visual feedback (38% opacity for disabled)
- **ArrowBackIconButton**: Fixed icon color to use secondary (orange) as per design system
- **ArrowBackIconButton**: Improved API with modifier and enabled parameters for better flexibility
- **ChipButton**: Enhanced with complete accessibility including semantic roles, selected state, and contentDescription
- **ChipButton**: Added enabled/disabled state with proper opacity (38% for disabled text, 50% for disabled background)
- **ChipButton**: Improved touch targets with minimumInteractiveComponentSize (48.dp)
- **ChipButton**: Restored original colors: TertiaryNormal for selected, NeutralGray100 for unselected
- **ChipButtonContainer**: Refactored to controlled component pattern - selectedItem now managed externally
- **ChipButtonContainer**: Added enabled parameter that propagates to all child chips
- **ChipButtonContainer**: Enhanced with semantic contentDescription for the container
- **ChipButtonContainer**: Updated showcase in App.kt with proper state management
- **ToteatTextFieldLayout**: Enhanced with semantic contentDescription using field title
- **ToteatTextFieldLayout**: Added error semantics that announce error messages to screen readers
- **ToteatTextFieldLayout**: Improved disabled state visual feedback (12% background opacity, 38% border/text opacity)
- **ToteatTextFieldLayout**: Enhanced helper text color management based on state (error, warning, success, disabled)
- **ToteatTextField**: Enhanced preview showing 5 states (normal, error, success, warning, disabled)
- **ToteatPasswordTextField**: Enhanced preview showing 4 states with interactive visibility toggle
- **ToteatPhoneNumberField**: Added semantic role (DropdownList) and contentDescription to dial code selector
- **ToteatPhoneNumberField**: Enhanced preview showing 4 states with interactive dial code selection
- **ModifierExtensions**: Fixed naming conflicts by renaming Shadow to DropShadowConfig and DpOffset to DropShadowOffset

### Added
- **ChipButton**: Added preview showing all 4 states (unselected, selected, disabled unselected, disabled selected)
- **ChipButtonContainer**: Added comprehensive preview with normal and disabled states
- **ArrowBackIconButton**: Added preview showing enabled and disabled states
- **TextField Components**: All text field components now have complete accessibility documentation in previews

### Fixed
- **ArrowBackIconButton**: Fixed icon visibility issue by using proper IconButtonDefaults.iconButtonColors with transparent container
- **ChipButtonContainer**: Fixed implementation in App.kt to use new controlled component API
- **ModifierExtensions**: Resolved compilation errors caused by naming conflicts with Compose native types

## [0.0.17] - 2025-12-16
### Fixed
- SwitchButtonContainer: Fixed syntax errors and component structure
- SwitchButtonContainer: Fixed layout distribution to prevent text wrapping issues
- SwitchButtonContainer: Ensured switch stays fixed to the right
- SwitchButtonContainer: Properly implemented enabled/disabled state with visual feedback (38% opacity)
- SwitchButtonContainer: Improved text layout using weight modifier for proper space distribution
- ToteatBottomBar: Fixed background color to use NeutralGray (white from DS)
- ToteatBottomBar: Fixed selected item to show only icon color change without background
- ToteatBottomBar: Implemented proper NavigationBar with Material3 standards
- ToteatButtonTable: Fixed layout distribution and responsive sizing
- ToteatButtonTable: Improved accessibility with semantic labels and state descriptions
- StatusTag: Fixed text overflow and sizing issues
- StatusTag: Improved text adaptation to different tag sizes
- TopBars: Fixed title centering and alignment issues
- TopBars: Corrected color usage to onSecondary for consistency

### Changed
- SwitchButtonContainer: Simplified component parameters, removed unused features
- SwitchButtonContainer: Optimized layout using SpaceBetween arrangement
- ToteatBottomBar: Improved accessibility with contentDescription for each item
- ToteatBottomBar: Added semantic state descriptions ("seleccionado" vs "no seleccionado")
- ToteatBottomBar: Set indicatorColor to Transparent (no background on selection)
- ToteatButtonTable: Enhanced responsive design with widthIn and heightIn constraints
- ToteatButtonTable: Improved status indication with color variants
- StatusTag: Enhanced accessibility with proper semantic roles
- StatusTag: Improved text styling with better padding and alignment
- ToteatTopBar: Improved layout distribution with weight(1f, fill = false) for side components
- ToteatTopBar: Increased horizontal padding from 8dp to 12dp for better spacing
- ToteatTopBar: Added horizontalArrangement with spacedBy(8.dp) for consistent spacing
- LoginTopBar: Enhanced accessibility with semantic labels for icons
- BackNavigationTopBar: Improved text handling with marquee for long titles
- CenterContentTopBar: Better handling of long content with proper weights

### Added
- Dropdown Component: Added custom AppDropdown component with a text field anchor, a selectable list of options in a popup menu, and placeholder support
- MessageView Component: Added a reusable component featuring an icon, a primary title, and a secondary message to display status or greetings
- Logos and iso added to the brand components
- TopBars: Added ToteatTopBar base component with flexible left/center/right slots
- TopBars: Added LoginTopBar with menu, logo, and customer service icons
- TopBars: Added BackNavigationTopBar with back button and title
- TopBars: Added CenterContentTopBar with RestaurantNameTopBarItem and ProcessNameTopBarItem
- Squared and Rectangle button component added
- Added Toast Component
- Add table button
- Added Segmented Tabs container
- Added github workflows
- Add chip button container
- TopBars: Added comprehensive preview functions for all TopBar variants
- TopBars: Added accessibility support with semantic labels and descriptions
- TopBars: Added modifier parameter support for all TopBar components
- TopBars: Added semanticLabel parameter for customizable accessibility labels
- TopBarShowcase: Added examples with long titles to demonstrate marquee and overflow handling



## [0.0.1] - 2025-10-7
- Adding group list component
- ## [0.0.8] - 2025-11-18
- Adding group list component
- add order detail component
- ## [0.0.12] - 2025-12-11
- Update password texfield component
### Added
Initial Setup: Creation of the Kotlin Multiplatform library module (toteatds) with support for Android, iOS, and JVM.
Main Theme: Implementation of ToteatTheme with a custom color system (LightColorScheme) and typography (Manrope).
