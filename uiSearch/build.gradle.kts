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
//    implementation(Dependencies.AndroidX.composeUi)
    implementation(Dependencies.AndroidX.composeMaterial)
    implementation(project(":api"))
}
