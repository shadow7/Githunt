buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
//        classpath(Dependencies.Classpath.composeGradlePlugin)
        classpath(Dependencies.Classpath.kotlinGradlePlugin)
        classpath(Dependencies.Classpath.androidGradleTools)
        classpath(Dependencies.Classpath.kotlinSerialization)
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=org.mylibrary.OptInAnnotation"
}