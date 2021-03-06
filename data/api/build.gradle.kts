plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    `android-config-plugin`
    kotlin("plugin.serialization")
}

dependencies {
    implementation(Dependencies.Squareup.okHttp)
    implementation(Dependencies.Squareup.okHttpLogging)
    api(Dependencies.Squareup.retrofit)
    implementation(Dependencies.Squareup.retrofitConverter)
    implementation(Dependencies.JetBrains.serializationJson)
    api(Dependencies.JetBrains.dateTime)
}
