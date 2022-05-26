
plugins {
    id("com.android.application")
    kotlin("android")
    `android-config-plugin`
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

dependencies {
    implementation(Dependencies.AndroidX.activityCompose)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.composeUi)
    implementation(Dependencies.AndroidX.composeMaterial)
    implementation(Dependencies.AndroidX.navhost)
    implementation(Dependencies.Google.material)
    implementation("com.google.accompanist:accompanist-navigation-animation:0.24.9-beta")
    api(project(":ui:search"))
    api(project(":ui:ownerDetails"))
}