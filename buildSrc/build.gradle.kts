plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
}