plugins {
    kotlin("jvm")
}

val cache4kVersion: String by project
val coroutinesVersion: String by project
val kmpUUIDVersion: String by project

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(project(Modules.common))

    implementation("io.github.reactivecircus.cache4k:cache4k:$cache4kVersion")
    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
}