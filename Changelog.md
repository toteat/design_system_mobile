# Changelog

## [Unreleased]

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
