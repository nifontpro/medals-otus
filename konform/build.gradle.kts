plugins {
    kotlin("jvm")
}

val coroutinesVersion: String by project

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    testImplementation("io.konform:konform:0.4.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
}