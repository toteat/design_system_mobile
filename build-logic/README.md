# build-logic (Gradle Convention Plugins)

Documentación del módulo de build-logic usado para centralizar configuraciones de Gradle y reutilizarlas a través de plugins de convención.

## ¿Por qué usar build-logic?

En proyectos multiplataforma y con múltiples módulos, mantener la configuración de Gradle duplicada genera errores y fricción. El módulo build-logic:
- Elimina duplicación de configuración entre módulos.
- Asegura coherencia (mismas versiones, mismos flags de compilación, mismos targets KMP, etc.).
- Facilita escalar el monorepo: se agrega un módulo y solo se aplica un plugin.
- Reduce el tiempo de puesta en marcha para nuevos módulos y colaboradores.

## ¿Qué soluciona?

Este build-logic empaqueta y publica internamente plugins de convención con las decisiones técnicas del repositorio:

- Android (Application/Library)
  - compileSdk/minSdk/targetSdk desde Version Catalog.
  - Java 11 + Kotlin JVM target 11, desugaring habilitado.
  - Empaquetado: exclusión de licencias META-INF.
  - Compose configurado con BOM y tooling de preview.
- Compose Multiplatform (CMP)
  - Configura el target Android y los targets iOS (iosX64, iosArm64, iosSimulatorArm64).
  - Define binarios framework para iOS con nombre consistente.
- Kotlin Multiplatform (KMP)
  - Habilita flags de compilación comunes y expect/actual.
  - JvmTarget en Android.
- Namespaces, resourcePrefix y nombres de frameworks
  - A partir de la ruta del proyecto: utilidades de PathUtil generan valores coherentes.

## ¿Cómo está integrado en el proyecto?

En `settings.gradle.kts` se incluye el build-logic como un build anidado, lo que permite resolver los plugins localmente sin publicarlos a un repositorio externo:

```kotlin
pluginManagement {
    includeBuild("build-logic")
}
```

Además, se usan alias de plugins desde `gradle/libs.versions.toml` para aplicar los plugins de convención por nombre corto.

## Plugins disponibles y cuándo usarlos

IDs publicados por build-logic (ver `build-logic/convention/build.gradle.kts`):

- com.toteat.convention.android.application
  - Configura una app Android (AGP) con defaults y Kotlin para Android.
- com.toteat.convention.android.application.compose
  - Extiende la app Android con configuración de Compose (BOM, tooling, buildFeatures.compose=true).
- com.toteat.convention.cmp.application
  - Configura una aplicación Compose Multiplatform (Android + iOS), aplicando:
    - com.toteat.convention.android.application.compose
    - org.jetbrains.kotlin.multiplatform
    - org.jetbrains.compose y kotlin plugin compose
    - Targets Android e iOS listos para usar.
- com.toteat.convention.kmp.library
  - Configura una librería Kotlin Multiplatform estándar (Android+iOS) con flags comunes.
- com.toteat.convention.cmp.library
  - Configura una librería Compose Multiplatform (KMP + Compose) con dependencias de UI básicas (UI, Foundation, Material3, Material Icons) y tooling de debug.

## ¿Cómo aplicarlos en un módulo?

Se recomienda usar los alias desde el Version Catalog (`gradle/libs.versions.toml`). Este repositorio ya declara:

- convention-android-application
- convention-android-application-compose
- convention-cmp-application
- convention-kmp-library
- convention-cmp-library

Ejemplos:

1) Aplicación Android con Compose (solo Android)

```kotlin
// build.gradle.kts del módulo app Android
plugins {
    alias(libs.plugins.convention.android.application)
    alias(libs.plugins.convention.android.application.compose)
}
```

2) Aplicación Compose Multiplatform (Android + iOS)

```kotlin
// build.gradle.kts de un módulo CMP
plugins {
    alias(libs.plugins.convention.cmp.application)
}
```

3) Librería Kotlin Multiplatform base

```kotlin
plugins {
    alias(libs.plugins.convention.kmp.library)
}
```

4) Librería Compose Multiplatform (UI compartida)

```kotlin
plugins {
    alias(libs.plugins.convention.cmp.library)
}
```

Notas:
- Los alias pueden variar según la sintaxis de tu Version Catalog. En este repo, los IDs están definidos en `gradle/libs.versions.toml` bajo la sección `[plugins]` con los nombres `convention-...`.
- Si prefieres, puedes aplicar los IDs directamente (`id("com.toteat.convention.cmp.library")`).

## Detalles técnicos relevantes

- Kotlin/Android
  - compileSdk/minSdk/targetSdk se obtienen de `libs.versions.toml` (claves projectCompileSdkVersion, projectMinSdkVersion, projectTargetSdkVersion).
  - JavaVersion 11, Kotlin JVM target 11; desugaring habilitado.
  - Flags de compilación Kotlin comunes: opt-in de coroutines experimentales.
- Compose
  - Habilita `buildFeatures.compose = true`.
  - Usa BOM de AndroidX Compose para alinear versiones.
  - Agrega tooling de preview y ui-tooling en debug.
- KMP / iOS
  - Targets iOS: iosX64, iosArm64, iosSimulatorArm64.
  - En `CmpApplicationConventionPlugin` se generan frameworks iOS estáticos con baseName "ComposeApp".
  - En `KotlinMultiplatform.configureKotlinMultiplatform()` se soporta generación de baseName por ruta si se usa esa variante.
- Utilidades de nombres (PathUtil)
  - pathToPackageName(): genera namespace Android desde el path del módulo (prefijo `com.toteat`).
  - pathToResourcePrefix(): genera prefijos de recursos coherentes.
  - pathToFrameworkName(): construye nombres de framework iOS basados en el path.
- Acceso al Version Catalog en plugins
  - `Project.libs` (ProjectExt.kt) expone el catálogo `libs` dentro del build-logic.

## Mantenimiento

1) Actualizar versiones
- Editar `gradle/libs.versions.toml` para subir versiones de Kotlin, AGP, Compose, etc.
- Preferir actualizar primero en ramas dedicadas y probar localmente.

2) Agregar o modificar un plugin de convención
- Código fuente en `build-logic/convention/src/main/kotlin/...`.
- Registrar/editar en `build-logic/convention/build.gradle.kts` dentro del bloque `gradlePlugin { plugins { ... } }`.
- Mantener IDs con el prefijo `com.toteat.convention.`.

3) Validación
- Tarea `:build-logic:convention:validatePlugins` (ya configurada con validación estricta) y `:build-logic:convention:build`.
- Sincronizar Gradle y compilar algunos módulos consumidores (por ejemplo `:composeApp` y `:toteatds`).

4) Compatibilidad
- Revisa que las versiones de Kotlin y AGP sean compatibles con Compose Multiplatform.
- Si cambias JVM target o JavaVersion, asegúrate de alinear toolchains y desugaring.

5) Lineamientos
- Mantener los nombres y responsabilidades de cada plugin pequeños y claros: Android app, CMP app, KMP lib, CMP lib.
- Evitar dependencias duras a librerías de features específicas dentro de plugins genéricos.
- Documentar en este README cualquier cambio de comportamiento por defecto.

## Problemas comunes y soluciones

- No se encuentra el plugin `com.toteat.convention.*` al sincronizar:
  - Verifica que `includeBuild("build-logic")` esté en `settings.gradle.kts` dentro de `pluginManagement`.
  - Ejecuta una sincronización/limpieza de Gradle.

- Conflicto de versiones de Compose:
  - Revisa que el BOM (`androidx-compose-bom`) esté siendo aplicado y que no forces versiones directas en módulos consumidores.

- Error por target JVM incompatible:
  - Chequea que tu JDK sea 17+ (el build-logic compila a 17 para sí mismo y configura JvmTarget 11 para Android). Ajusta toolchains si es necesario.

## Referencias rápidas

- Código fuente de plugins: `build-logic/convention/src/main/kotlin/`
- Registro de plugins: `build-logic/convention/build.gradle.kts`
- Catálogo de versiones: `gradle/libs.versions.toml`
- Integración: `settings.gradle.kts` (pluginManagement.includeBuild)

Si necesitas extender o modificar el comportamiento, abre una PR actualizando este README con el cambio propuesto.