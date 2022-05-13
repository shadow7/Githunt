buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(Dependencies.Classpath.kotlinGradlePlugin)
        classpath(Dependencies.Classpath.androidGradleTools)
        classpath(Dependencies.Classpath.kotlinSerialization)
    }
}
