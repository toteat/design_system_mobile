# Toteat Design System - AI Agent Instructions

## Project Overview
This is **Toteat's mobile Design System** (`toteatds`) implemented with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform (CMP)**. It provides reusable UI components, tokens (colors, typography, spacing), and theming for Android and iOS platforms.

**Key modules:**
- `toteatds/` - The design system library (published or consumed as a module dependency)
- `composeApp/` - Demo/showcase app that previews and validates components
- `build-logic/` - Gradle convention plugins for consistent build configuration

## Architecture

### Build System: Convention Plugins
All modules use **custom Gradle convention plugins** from `build-logic/convention/` instead of repeating configuration:

**Available plugins (from [gradle/libs.versions.toml](gradle/libs.versions.toml)):**
- `com.toteat.convention.android.application` - Android app with defaults
- `com.toteat.convention.android.application.compose` - Android app + Compose setup
- `com.toteat.convention.cmp.application` - CMP app with Android + iOS targets
- `com.toteat.convention.kmp.library` - KMP library base
- `com.toteat.convention.cmp.library` - CMP library with Android + iOS targets

**What they handle:** compileSdk/minSdk/targetSdk, Java 11 + Kotlin JVM target, desugaring, Compose BOM, iOS targets (iosX64, iosArm64, iosSimulatorArm64), namespace generation via [PathUtil.kt](build-logic/convention/src/main/kotlin/com/toteat/designsystem/convention/PathUtil.kt).

**Key insight:** Namespace and resource prefixes are auto-generated from module path using `pathToPackageName()` and `pathToResourcePrefix()`. Don't hardcode these values.

### Multiplatform Structure
- **Common code:** `src/commonMain/kotlin/` - Shared Compose UI, components, theme
- **Platform-specific:** `src/androidMain/`, `src/iosMain/` for platform implementations
- **Pattern:** Components are 100% shared in `commonMain` using Compose Multiplatform

### Theme System
- **Entry point:** [ToteatTheme.kt](toteatds/src/commonMain/kotlin/com/toteat/toteatds/theme/ToteatTheme.kt) - Wraps all UI with Material3 theme + custom extended colors
- **Colors:** [Color.kt](toteatds/src/commonMain/kotlin/com/toteat/toteatds/theme/Color.kt) defines brand colors (PrimaryNormal, SecondaryNormal, TertiaryNormal), neutral grays, and feedback colors (GreenNormal, RedNormal, YellowNormal, BlueNormal)
- **Extended colors:** Access via `MaterialTheme.extendedColors` (LocalExtendedColors composition local)
- **Typography:** Uses Manrope font family (see Type.kt)

## Component Patterns

### Component Organization
Components are organized by category in [toteatds/src/commonMain/kotlin/com/toteat/toteatds/components/](toteatds/src/commonMain/kotlin/com/toteat/toteatds/components/):
- `buttons/` - PrimaryButton, SecondaryButton, TertiaryButton, ToteatButtonTable, chip/switch containers
- `textfields/` - ToteatTextField, ToteatPasswordTextField, ToteatPhoneNumberField
- `topbar/` - LoginTopBar, BackNavigationTopBar, CenterContentTopBar
- `bottomBar/` - ToteatBottomBar with navigation types
- `brand/` - logo and iso variants (Original, BlackAndCream, CreamOrange)
- `tags/` - StatusTag with color variants
- `toast/` - ToteatToastMessage for notifications
- `list/` - OrderItem, GroupedOrderDetail
- `DropDowns/`, `SegmentButtons/`, `messageview/`, `icons/`

### Button Pattern (Example)
From [Buttons.kt](toteatds/src/commonMain/kotlin/com/toteat/toteatds/components/buttons/Buttons.kt):
- **Standard shape:** `RoundedCornerShape(50)` for pill-shaped buttons
- **Height:** Fixed 45.dp for consistency
- **States:** Use `MutableInteractionSource` to handle pressed state and adjust colors
- **Disabled state:** Containers use `NeutralGray300`, content uses `NeutralGray`
- **Structure:** All buttons follow pattern of optional text + optional leadingIcon

### Demo/Showcase Pattern
[App.kt](composeApp/src/commonMain/kotlin/com/toteat/designsystemmobile/App.kt) demonstrates ALL components in expandable sections:
- Use `ComponentShowcaseItem` for collapsible component categories
- Each section shows component variations (enabled/disabled, different states)
- Always update App.kt when adding new components

## Development Workflows

### Running the Project
```bash
# Android
./gradlew :composeApp:assembleDebug

# iOS (macOS only)
# Open iosApp/iosApp.xcodeproj in Xcode and run

# Desktop
./gradlew :composeApp:runDebug
```

### Building the Library
```bash
./gradlew :toteatds:build
```

### Release Process
Use [release.sh](release.sh) script for automated versioning:
```bash
./release.sh <version> "[release message]"
# Example: ./release.sh 1.1.0 "Add new dropdown component"
```

**What the script does:**
1. Checks for uncommitted changes (prompts to commit if found)
2. Verifies tag doesn't already exist (can force recreate if needed)
3. Creates annotated git tag locally with version message
4. Shows summary: tag name, message, current branch, last commit
5. Optionally pushes changes and tag to remote origin
6. Provides GitHub release URL for manual release notes

**Before releasing:**
- Update [Changelog.md](Changelog.md) - move items from `[Unreleased]` to new version section
- Test components in showcase app on both Android and iOS
- Verify version numbers in [gradle/libs.versions.toml](gradle/libs.versions.toml) match release

### Version Management
Update these in [gradle/libs.versions.toml](gradle/libs.versions.toml):
- `projectVersionName` and `projectVersionCode` for app versioning
- `android-compileSdk`, `android-minSdk`, `android-targetSdk` for SDK versions
- All dependency versions centralized here

## Code Conventions

### Naming
- **Components:** PascalCase, descriptive names (e.g., `ToteatPasswordTextField`, `BackNavigationTopBar`)
- **Colors:** Use semantic names from theme (e.g., `PrimaryNormal`, `NeutralGray300`)
- **Packages:** lowercase, organized by feature/component type

### Component API Design
- Always provide `modifier: Modifier = Modifier` parameter
- Use `enabled: Boolean = true` for interactive components
- Use Material3 composition locals for theming (`MaterialTheme.colorScheme`, `MaterialTheme.typography`)
- Optional content uses nullable lambda parameters (e.g., `leadingIcon: @Composable (() -> Unit)? = null`)

### Hot Reload
Project uses `composeHotReload` plugin (beta). Changes to composables auto-refresh during development.

## Testing
- Test files in `src/commonTest/` using `kotlin-test`
- Component validation happens through the showcase app (`composeApp`)

## Critical Files
- [gradle/libs.versions.toml](gradle/libs.versions.toml) - Single source of truth for all dependencies and versions
- [settings.gradle.kts](settings.gradle.kts) - Declares modules and includes build-logic
- [Changelog.md](Changelog.md) - Track all component additions/fixes
- [build-logic/README.md](build-logic/README.md) - Detailed docs on convention plugins (Spanish)

## Adding New Components
1. Create component file in `toteatds/src/commonMain/kotlin/com/toteat/toteatds/components/[category]/`
2. Follow existing button/textfield patterns for API design
3. Use theme colors and typography from `MaterialTheme`
4. Add showcase in [App.kt](composeApp/src/commonMain/kotlin/com/toteat/designsystemmobile/App.kt)
5. Update [Changelog.md](Changelog.md) under `[Unreleased]` section
6. Test on both Android and iOS

## Common Pitfalls
- **Don't hardcode namespaces** in build files - convention plugins auto-generate them
- **Don't use Android-specific APIs** in commonMain - keep it platform-agnostic
- **Always use theme colors** instead of hardcoded Color values in components
- **Include 3-5 lines of context** when using replace tools for file edits
- **Compose for iOS requires framework export** - handled automatically by CmpLibraryConventionPlugin

## Accessibility Guidelines

### Compose Multiplatform Accessibility
Follow Material Design 3 and Compose accessibility best practices:

**Semantic Properties:**
- Use `Modifier.semantics { }` to provide accessible labels and descriptions
- Set `contentDescription` for icons and images without text
- Use `role = Role.Button` for clickable elements that aren't standard buttons
- Mark decorative elements with `contentDescription = null` to skip them

**Touch Targets:**
- Minimum touch target: 48.dp x 48.dp (Material Design guideline)
- Buttons already use 45.dp height - ensure adequate horizontal padding
- Use `Modifier.minimumInteractiveComponentSize()` for small clickable items

**Color Contrast:**
- Text on PrimaryNormal (FF4235): ensure WCAG AA compliance (4.5:1 ratio)
- Disabled states use NeutralGray on NeutralGray300 - verify sufficient contrast
- Test feedback colors (GreenNormal, RedNormal, YellowNormal) against backgrounds

**State Indication:**
- Don't rely solely on color for state (e.g., error, success)
- Combine color with icons, text labels, or shape changes
- Example: StatusTag uses both color AND icon/text to convey status

**Keyboard & Focus:**
- Compose Desktop/Android support keyboard navigation automatically
- iOS VoiceOver works through accessibility semantics
- Test with screen readers on target platforms

**References:**
- [Material Design Accessibility](https://m3.material.io/foundations/accessible-design/overview)
- [Compose Accessibility Guide](https://developer.android.com/jetpack/compose/accessibility)
- [WCAG 2.1 Guidelines](https://www.w3.org/WAI/WCAG21/quickref/)

## Official Documentation & References

### Kotlin Multiplatform
- [KMP Official Docs](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Kotlin/Native iOS Integration](https://kotlinlang.org/docs/native-ios-integration.html)

### Compose & Material Design
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material 3 Components](https://developer.android.com/jetpack/androidx/releases/compose-material3)
- [Material Design System](https://m3.material.io/)
- [Compose Theming](https://developer.android.com/jetpack/compose/designsystems/material3)

### Build & Tooling
- [Gradle Version Catalogs](https://docs.gradle.org/current/userguide/platforms.html)
- [Gradle Convention Plugins](https://docs.gradle.org/current/samples/sample_convention_plugins.html)
- [Android Gradle Plugin](https://developer.android.com/build)

### Design System Patterns
- [Design System Architecture](https://www.designsystems.com/)
- [Component API Design](https://developer.android.com/jetpack/compose/architecture#udf)
