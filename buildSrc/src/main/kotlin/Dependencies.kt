object Dependencies {
    object Classpath {
        const val kotlinSerialization =
            "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinVersion}"
    }

    object JetBrains {
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"

        private const val coroutineVersion = "1.3.8"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"

        const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.3.2"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.4.0"

        const val activityCompose = "androidx.activity:activity-compose:1.4.0"
        const val composeUi = "androidx.compose.ui:ui:${Versions.composeCompilerVersion}"
        const val composeUiPreview =
            "androidx.compose.ui:ui-tooling-preview:${Versions.composeCompilerVersion}"
        const val composeMaterial =
            "androidx.compose.material:material:${Versions.composeCompilerVersion}"

        const val navhost = "androidx.navigation:navigation-compose:2.4.2"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
        const val composePaging =
            "androidx.paging:paging-compose:${Versions.composeCompilerVersion}"
        const val paging = "androidx.paging:paging-runtime-ktx:3.0.0"
        const val browser = "androidx.browser:browser:1.4.0"
        const val coil = "io.coil-kt:coil-compose:2.1.0"
        const val junit = "junit:junit:4.13.2"
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
        const val retrofitConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"

        private const val okhttpVersion = "4.9.3"
        const val okHttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
        const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
    }
}
