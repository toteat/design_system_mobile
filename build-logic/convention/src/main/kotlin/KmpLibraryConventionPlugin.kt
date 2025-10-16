import com.android.build.gradle.LibraryExtension
import com.toteat.designsystem.convention.configureKotlinAndroid
import com.toteat.designsystem.convention.configureKotlinMultiplatform
import com.toteat.designsystem.convention.libs
import com.toteat.designsystem.convention.pathToResourcePrefix
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
            }

            configureKotlinMultiplatform()

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                resourcePrefix = this@with.pathToResourcePrefix()

                // Required to make debug build of app run in iOS simulator
                experimentalProperties["android.experimental.kmp.enableAndroidResources"] = "true"
            }

            dependencies {
                "commonTestImplementation"(libs.findLibrary("kotlin-test").get())
            }
        }
    }
}
