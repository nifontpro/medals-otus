plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
}

group = "ru.otus"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        maven { url = uri("https://jitpack.io") }

        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}