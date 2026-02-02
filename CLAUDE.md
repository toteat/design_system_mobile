# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is **Toteat's mobile Design System** (`toteatds`) built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform (CMP)**. It provides reusable UI components, design tokens (colors, typography, spacing), and theming for Android and iOS platforms.

**Key modules:**
- `toteatds/` - The design system library (published as `com.toteat.designsystem:toteatds`)
- `composeApp/` - Demo/showcase app that previews and validates all components
- `build-logic/` - Gradle convention plugins for consistent build configuration

## Essential Commands

### Running the Project
```bash
# Android (build and install to device/emulator)
./gradlew :composeApp:installDebug

# Desktop (JVM preview)
./gradlew :composeApp:run

# iOS - Open iosApp/iosApp.xcodeproj in Xcode and run
```

### Building the Library
```bash
# Build the design system library
./gradlew :toteatds:assemble

# Build everything
./gradlew clean build

# List available tasks for a module
./gradlew :toteatds:tasks
```

### Testing
```bash
# Run all tests
./gradlew test

# Run tests for specific module
./gradlew :toteatds:test
```

## Architecture

### Build System: Convention Plugins

All modules use **custom Gradle convention plugins** from `build-logic/convention/` to eliminate configuration duplication. NEVER hardcode build configuration that should be in convention plugins.

**Available plugins** (defined in `gradle/libs.versions.toml`):
- `convention-cmp-library` - For the `toteatds` library (CMP library with Android + iOS targets)
- `convention-cmp-application` - For the `composeApp` showcase (CMP app with Android + iOS targets)
- `convention-kmp-library` - Base KMP library without Compose
- `convention-android-application` - Android-only application
- `convention-android-application-compose` - Android-only app with Compose

**What convention plugins handle:**
- SDK versions (compileSdk=36, minSdk=24, targetSdk=36)
- Java 11 bytecode target with JDK 17 for builds
- Android desugaring for newer Java APIs on older Android versions
- Compose BOM and Material3 dependencies
- iOS targets: iosX64, iosArm64, iosSimulatorArm64
- Auto-generated namespace and resource prefix from module path (via `PathUtil.kt`)

**CRITICAL:** Namespace and resource prefixes are auto-generated from module path using `pathToPackageName()` and `pathToResourcePrefix()`. Never hardcode these values in build files.

### Multiplatform Structure

- **Common code:** `src/commonMain/kotlin/` - All shared Compose UI, components, and theme logic
- **Platform-specific:** `src/androidMain/`, `src/iosMain/` for platform implementations
- **Pattern:** Components are 100% shared in `commonMain` using Compose Multiplatform - avoid platform-specific code unless absolutely necessary

### Theme System

**Entry point:** `toteatds/src/commonMain/kotlin/com/toteat/toteatds/theme/ToteatTheme.kt`
- Wraps all UI with Material3 theme + custom extended colors
- Access theme colors via `MaterialTheme.colorScheme` and `MaterialTheme.extendedColors`

**Color palette** (`Color.kt`):
- **Brand:** PrimaryNormal (0xFFFF4235), SecondaryNormal (0xFF1B1B1B), TertiaryNormal (0xFFE6D9CA)
- **Neutral:** NeutralGray500 through NeutralGray100, NeutralGray (white)
- **Feedback:** GreenNormal, RedNormal, YellowNormal, BlueNormal (with light and dark variants)

**Typography:** Uses Manrope font family (see `Type.kt`)

**Extended colors:** Access via `MaterialTheme.extendedColors` (LocalExtendedColors composition local)

## Component Organization

Components in `toteatds/src/commonMain/kotlin/com/toteat/toteatds/components/`:
- `buttons/` - PrimaryButton, SecondaryButton, TertiaryButton, ToteatButtonTable, chip/switch containers
- `textfields/` - ToteatTextField, ToteatPasswordTextField, ToteatPhoneNumberField
- `topbar/` - LoginTopBar, BackNavigationTopBar, CenterContentTopBar
- `bottombar/` - ToteatBottomBar with navigation types
- `brand/` - Logo and iso variants (Original, BlackAndCream, CreamOrange)
- `tags/` - StatusTag with color variants
- `toast/` - ToteatToastMessage for notifications
- `list/` - OrderItem, GroupedOrderDetail
- `icons/` - Icon components with accessibility support
- `dropdown/`, `segmentbuttons/`, `messageview/`, `modalbuttonsheet/`

### Component Design Patterns

**Standard button pattern** (from `Buttons.kt`):
- Shape: `RoundedCornerShape(50)` for pill-shaped buttons
- Height: Fixed 45.dp for consistency
- States: Use `MutableInteractionSource` to handle pressed state and adjust colors
- Disabled state: Container uses `NeutralGray300`, content uses `NeutralGray`
- Structure: Optional text + optional leadingIcon

**Component API conventions:**
- Always provide `modifier: Modifier = Modifier` parameter
- Use `enabled: Boolean = true` for interactive components
- Use Material3 composition locals for theming (`MaterialTheme.colorScheme`, `MaterialTheme.typography`)
- Optional content uses nullable lambda parameters (e.g., `leadingIcon: @Composable (() -> Unit)? = null`)
- ALWAYS use theme colors instead of hardcoded Color values

**Demo/showcase pattern** (`composeApp/src/commonMain/kotlin/com/toteat/designsystemmobile/App.kt`):
- ALL components must be demonstrated in App.kt
- Use `ComponentShowcaseItem` for collapsible component categories
- Show component variations (enabled/disabled, different states)
- ALWAYS update App.kt when adding new components

## Adding New Components

1. Create component file in `toteatds/src/commonMain/kotlin/com/toteat/toteatds/components/[category]/`
2. Follow existing patterns:
   - Use `@Composable` function with `modifier: Modifier = Modifier`
   - Use theme colors from `MaterialTheme.colorScheme` or `MaterialTheme.extendedColors`
   - Use theme typography from `MaterialTheme.typography`
   - Add proper accessibility semantics (contentDescription, role, etc.)
3. Add showcase in `composeApp/.../App.kt` showing all states
4. Update `Changelog.md` under `[Unreleased]` section
5. Test on both Android and iOS before committing

## Version Management and Releases

**Version numbers:** Update in `gradle.properties`:
- `libVersion` - Library version (e.g., "0.1.7")
- `libGroup` - Maven group ID ("com.toteat.designsystem")

**SDK versions:** Update in `gradle/libs.versions.toml`:
- `android-compileSdk`, `android-minSdk`, `android-targetSdk`
- All dependency versions are centralized here

**Release process:** Use the `release.sh` script:
```bash
./release.sh <version> "[release message]"
# Example: ./release.sh 0.1.8 "Add new modal components"
```

**What release.sh does:**
1. Checks for uncommitted changes (prompts to commit if found)
2. Verifies tag doesn't exist (can force recreate)
3. Creates annotated git tag with version message
4. Optionally pushes changes and tag to remote

**Before releasing:**
1. Update `Changelog.md` - move items from `[Unreleased]` to new version section
2. Update `libVersion` in `gradle.properties`
3. Test components in showcase app on both Android and iOS
4. Verify all tests pass

## Accessibility Guidelines

**Semantic properties:**
- Use `Modifier.semantics { }` for accessible labels and descriptions
- Set `contentDescription` for icons and images without text
- Use `role = Role.Button` for clickable elements that aren't standard buttons
- Mark decorative elements with `contentDescription = null`

**Touch targets:**
- Minimum touch target: 48.dp x 48.dp (Material Design guideline)
- Use `Modifier.minimumInteractiveComponentSize()` for small clickable items

**Color contrast:**
- Text on PrimaryNormal (0xFFFF4235) must meet WCAG AA (4.5:1 ratio)
- Disabled states (NeutralGray on NeutralGray300) must have sufficient contrast
- Don't rely solely on color for state - combine with icons, text, or shape changes

## Common Pitfalls

- **DON'T hardcode namespaces** in build files - convention plugins auto-generate them
- **DON'T use Android-specific APIs** in `commonMain` - keep it platform-agnostic
- **ALWAYS use theme colors** (`MaterialTheme.colorScheme`) instead of hardcoded `Color()` values
- **DON'T create Android-only features** without iOS equivalents in a CMP component
- **ALWAYS test on both platforms** before committing component changes
- **DON'T skip the showcase** - every component must be demonstrated in `App.kt`

## Hot Reload

Project uses `composeHotReload` plugin (beta version 1.0.0-beta07). Changes to composables auto-refresh during development without rebuilding.

## Prerequisites

- macOS, Windows, or Linux for Android/Desktop; macOS required for iOS
- Android Studio latest stable (Koala+ recommended) with Kotlin plugin
- JDK 17 configured (AGP 8.x requires JDK 17; project targets Java 11 bytecode)
- Android SDK 36 with emulator or physical device
- Xcode 15+ for iOS Simulator or device (macOS only)

## Troubleshooting

- **SDK not found:** Set `sdk.dir=/path/to/Android/sdk` in `local.properties`
- **JDK mismatch:** Set Gradle JDK to 17 in Android Studio (Settings > Build > Gradle > Gradle JDK)
- **iOS build issues:** Ensure Xcode 15+, correct simulator selected, and valid signing for device builds
- **Namespace errors:** Never hardcode namespace - let convention plugins auto-generate from module path
