import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.toteat.convention.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.toteat.convention.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidComposeApplication") {
            id = "com.toteat.convention.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("cmpApplication") {
            id = "com.toteat.convention.cmp.application"
            implementationClass = "CmpApplicationConventionPlugin"
        }

        register("kmpLibrary") {
            id = "com.toteat.convention.kmp.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }

        register("cmpLibrary") {
            id = "com.toteat.convention.cmp.library"
            implementationClass = "CmpLibraryConventionPlugin"
        }
    }
}

