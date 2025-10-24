// CmpLibraryConventionPlugin.kt
// package com.tu.organizacion.buildlogic // ← si tu build-logic usa package, colócalo aquí

import com.toteat.designsystem.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

import com.android.build.gradle.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CmpLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {

        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.library")
            apply("org.jetbrains.kotlin.plugin.compose")
            apply("org.jetbrains.compose")
            apply("maven-publish")
        }

        dependencies {
            "commonMainImplementation"(libs.findLibrary("jetbrains-compose-ui").get())
            "commonMainImplementation"(libs.findLibrary("jetbrains-compose-foundation").get())
            "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material3").get())
            "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material-icons-core").get())
            // tooling para Android (opcional)
            "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
        }

        val libVersion = property("libVersion").toString() // ej: 0.2.0-SNAPSHOT
        val libGroup   = property("libGroup").toString()   // ej: com.toteat.designsystem

        extensions.configure<KotlinMultiplatformExtension> {
            applyDefaultHierarchyTemplate()

            androidTarget {
                publishLibraryVariants("release")
            }

            // Targets iOS
            iosArm64()
            iosX64()
            iosSimulatorArm64()


        }


        extensions.configure<LibraryExtension> {
            if (namespace == null) {
                namespace = "${libGroup}.toteatds"
            }
            compileSdk = 34
            defaultConfig { minSdk = 24 }

            publishing {
                singleVariant("release") {
                    withSourcesJar()
                }
            }
        }

        extensions.configure<PublishingExtension> {
            publications {
                withType<MavenPublication> {
                    groupId = libGroup
                    version = libVersion
                }
            }
            repositories {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/toteat/design_system_mobile")
                    credentials {

                        username = System.getenv("GITHUB_ACTOR") ?: findProperty("gpr.user")?.toString()
                        password = System.getenv("GITHUB_TOKEN") ?: findProperty("gpr.key")?.toString()
                    }
                }
            }
        }
    }
}
