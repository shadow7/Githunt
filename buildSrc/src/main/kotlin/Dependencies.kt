@file:Suppress("unused")

object Dependencies {
    object Classpath {
        const val composeGradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:${Versions.composeGradlePlugin}"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        const val androidGradleTools = "com.android.tools.build:gradle:${Versions.gradleToolsVersion}"
        const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinVersion}"
    }

    object JetBrains {
        object Kotlin {
            const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"
            const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
            const val testCommon = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlinVersion}"
            const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlinVersion}"
            const val testJs = "org.jetbrains.kotlin:kotlin-test-js:${Versions.kotlinVersion}"
            const val testAnnotationsCommon =
                "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlinVersion}"
        }
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.4.0"
        const val activityCompose = "androidx.activity:activity-compose:1.4.0"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val browser = "androidx.browser:browser:1.4.0"
        const val junit = "junit:junit:4.13.2"
        const val composeUi = "androidx.compose.ui:ui:${Versions.composeCompilerVersion}"
        const val composeUiPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeCompilerVersion}"
        const val composeMaterial = "androidx.compose.material:material:${Versions.composeCompilerVersion}"
    }

    object Google {
        const val material = "com.google.android.material:material:1.6.0"
    }

    object Squareup {
        private const val sqlDelightVersion = "1.5.0"
        const val gradlePlugin = "com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion"
        const val androidDriver = "com.squareup.sqldelight:android-driver:$sqlDelightVersion"

        private const val retrofitVersion = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"

        private const val okhttpVersion = "4.9.3"
        const val okHttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
        const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
    }
}
