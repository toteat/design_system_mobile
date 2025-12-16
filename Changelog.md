# Changelog

## [Unreleased]

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
