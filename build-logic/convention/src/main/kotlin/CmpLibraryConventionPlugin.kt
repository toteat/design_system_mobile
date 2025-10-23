// Tus imports existentes
import com.toteat.designsystem.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

import org.gradle.api.publish.maven.MavenPublication // <-- IMPORTANTE
import org.gradle.kotlin.dsl.withType                 // <-- IMPORTANTE
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure

class CmpLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {


            with(pluginManager) {
                apply("com.toteat.convention.kmp.library")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.compose")

                apply("maven-publish")
            }

            dependencies {
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-ui").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-foundation").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material3").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material-icons-core").get())

                "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
            }

            val libVersion = property("libVersion").toString()
            val libGroup = property("libGroup").toString()


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
                            username = System.getenv("GITHUB_ACTOR")
                            password = System.getenv("GITHUB_TOKEN")
                        }
                    }
                }
            }
        }
    }
}