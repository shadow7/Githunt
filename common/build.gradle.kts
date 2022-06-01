plugins {
    id("com.android.library")
    id("kotlin-android")
    `android-config-plugin`
}

@Suppress("UnstableApiUsage")
android {
    buildFeatures.compose = true
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    api(Dependencies.AndroidX.activityCompose)
    api(Dependencies.AndroidX.lifecycleViewModel)
    api(Dependencies.AndroidX.composeMaterial)
    debugApi(Dependencies.AndroidX.composeUiTooling)
    api(Dependencies.AndroidX.composeUiPreview)
    api(Dependencies.AndroidX.browser)
    api(Dependencies.AndroidX.coil)
    api(Dependencies.AndroidX.palette)
    api(project(":data:api"))
}