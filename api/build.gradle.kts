plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(Dependencies.Squareup.okHttp)
    implementation(Dependencies.Squareup.okHttpLogging)
    implementation(Dependencies.Squareup.retrofit)
    implementation(Dependencies.JetBrains.serializationJson)
}