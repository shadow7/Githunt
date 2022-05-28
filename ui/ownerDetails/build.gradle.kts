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
    implementation(Dependencies.AndroidX.browser)
    implementation(Dependencies.AndroidX.coil)
    implementation(project(":data:api"))
}
