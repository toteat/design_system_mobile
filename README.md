# Toteat Design System (toteatds)

This repository contains Toteat’s mobile Design System implemented with Kotlin Multiplatform and Compose Multiplatform. Its goal is to provide a reusable UI library of components, tokens (colors, typography, spacing), and theming utilities to be consumed by the main Toteat project and any other internal apps that need it.

- Library module: `:toteatds`
- Demo/Showcase: `:composeApp` (used to preview and validate Design System components)
- Platforms: Android and iOS (KMP)

## How it’s consumed by the main project

The Design System is used as a library that supplies components to Toteat’s main app. There are two ways to integrate it:

1) Module dependency (monorepo)
- If the main project lives in the same repository, add the module dependency directly:
```kotlin
dependencies {
    implementation(project(":toteatds"))
}
```

2) Published dependency (artifact repository)
- If it’s published to an internal/external repository (example):
```kotlin
dependencies {
    implementation("com.toteat:toteatds:<version>")
}
```

## Basic usage

- Global theme
```kotlin
ToteatTheme {
    // Your UI
}
```

- Components
```kotlin
PrimaryButton(text = "Continue", onClick = { /* ... */ })
ToteatTextField(value = state, onValueChange = { /* ... */ }, label = "Email")
```

See more examples in `composeApp/src/.../App.kt`, which showcases Buttons, Dropdowns, Inputs, Cards, and more.

## How to run the project

Prerequisites
- macOS, Windows, or Linux for Android/Desktop; macOS for iOS.
- Android Studio latest stable (Koala+ recommended) with Kotlin plugin.
- JDK 17 configured (AGP 8.x requires JDK 17; the project targets Java 11 bytecode).
- Android SDK 36 with an emulator or a physical device.
- Xcode 15+ for iOS Simulator or device (macOS only).

1) Clone and open
- Clone the repo and open it with Android Studio.
- Let Gradle sync finish. Ensure the IDE uses JDK 17.

2) Run on Android
- From Android Studio: select the "composeApp" run configuration, choose an emulator/device, and click Run.
- Or via terminal:
```
./gradlew :composeApp:installDebug
```
Then launch the app on the device/emulator.

3) Run on iOS (Simulator)
- Open `iosApp/iosApp.xcodeproj` in Xcode.
- Select the `iosApp` scheme and pick an iOS Simulator (e.g., iPhone 15).
- Press Run. If code signing warnings appear, set your Team in Signing & Capabilities for the `iosApp` target.

4) Run Desktop (JVM)
- From terminal:
```
./gradlew :composeApp:run
```
This runs the desktop target that previews the same UI as the mobile showcase.

5) Build the Design System library only
```
./gradlew :toteatds:assemble
```
Artifacts will be in `toteatds/build/`.

Common commands
- Clean and build everything: `./gradlew clean build`
- List tasks for a module: `./gradlew :composeApp:tasks`

Troubleshooting
- SDK not found: set `sdk.dir=/path/to/Android/sdk` in `local.properties`.
- JDK mismatch: set Gradle JDK to 17 in Android Studio (Settings > Build > Gradle > Gradle JDK).
- iOS build issues: ensure Xcode 15+, correct selected simulator, and valid signing for device builds.

## Main packages
- `com.toteat.toteatds.theme`: color/typography tokens and `ToteatTheme`.
- `com.toteat.toteatds.components`: UI components (Buttons, TextFields, Dropdowns, Cards, etc.).

## Maintenance and versioning
- Changes and new versions are tracked in `Changelog.md`.
- Aim to keep API compatibility across major versions whenever possible.

---