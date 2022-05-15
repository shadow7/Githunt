plugins {
    id("com.android.application")
    kotlin("android")
    `android-config-plugin`
}

dependencies {
    implementation(Dependencies.AndroidX.activityCompose)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.composeUi)
    implementation(Dependencies.AndroidX.composeMaterial)
    implementation(Dependencies.Google.material)
    api(project(":uiSearch"))
}

@Suppress("UnstableApiUsage")
android {
    defaultConfig {
        applicationId = AndroidConfig.applicationId
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures.compose = true

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompilerVersion
    }
}