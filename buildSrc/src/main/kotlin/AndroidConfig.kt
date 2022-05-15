import org.gradle.api.JavaVersion

object AndroidConfig {
    const val applicationId = "com.githunt.android"
    const val compileSdk = 31
    const val minSdk = 26
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "1.0"
    val javaVersion = JavaVersion.VERSION_11
    const val jvmTarget = "11"
}