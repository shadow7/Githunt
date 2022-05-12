plugins {
    kotlin("android")
    id("com.android.application")
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(Dependencies.AndroidX.activityCompose)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.composeUi)
    implementation(Dependencies.AndroidX.composeMaterial)
    implementation(Dependencies.AndroidX.composeUiPreview)
    implementation(Dependencies.Google.material)
}

@Suppress("UnstableApiUsage")
android {
    compileSdk = AndroidConfig.compileSdk
    defaultConfig {
        applicationId = AndroidConfig.applicationId
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = AndroidConfig.javaVersion
        targetCompatibility = AndroidConfig.javaVersion
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = AndroidConfig.jvmTarget
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompilerVersion
    }
}