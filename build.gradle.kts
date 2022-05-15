import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Classpath.kotlinSerialization)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = AndroidConfig.jvmTarget
        freeCompilerArgs = listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi"
        )
    }
}