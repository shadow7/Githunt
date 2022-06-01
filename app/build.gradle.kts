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
    implementation(Dependencies.Google.material)
    implementation(Dependencies.AndroidX.navhost)
    implementation(Dependencies.Google.navigationAnimation)
    implementation(project(":common"))
    implementation(project(":ui:search"))
    implementation(project(":ui:ownerDetails"))
}