plugins {
    id("com.android.library")
    id("kotlin-android")
    `android-config-plugin`
    jacoco
}

@Suppress("UnstableApiUsage")
android {
    buildFeatures.compose = true
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    implementation(Dependencies.AndroidX.activityCompose)
    implementation(Dependencies.AndroidX.lifecycleViewModel)
    implementation(Dependencies.AndroidX.composeMaterial)
    debugImplementation(Dependencies.AndroidX.composeUiTooling)
    implementation(Dependencies.AndroidX.composeUiPreview)
    implementation(Dependencies.AndroidX.browser)
    api(Dependencies.AndroidX.coil)
    api(Dependencies.AndroidX.palette)
    api(project(":data:api"))
    implementation(project(":common"))
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("androidx.test.ext:junit-ktx:1.1.3")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("androidx.test:rules:1.4.0")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2")
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("app.cash.turbine:turbine:0.8.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
}