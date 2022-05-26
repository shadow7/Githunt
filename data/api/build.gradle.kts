import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(Dependencies.Squareup.okHttp)
    implementation(Dependencies.Squareup.okHttpLogging)
    implementation(Dependencies.Squareup.retrofit)
    implementation(Dependencies.Squareup.retrofitConverter)
    implementation(Dependencies.JetBrains.serializationJson)
    implementation(Dependencies.JetBrains.dateTime)
}
