plugins {
    id("com.android.library")
    id("kotlin-android")
    `android-config-plugin`
}

@Suppress("UnstableApiUsage")
android {
    buildFeatures.compose = true
}

dependencies {
    implementation(Dependencies.AndroidX.activityCompose)
    implementation(Dependencies.AndroidX.lifecycleViewModel)
    implementation(Dependencies.AndroidX.composeMaterial)
    debugImplementation("androidx.compose.ui:ui-tooling:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.1")
    implementation(Dependencies.AndroidX.browser)
    implementation(Dependencies.AndroidX.coil)
    implementation(Dependencies.AndroidX.palette)
    implementation(project(":data:api"))
}
