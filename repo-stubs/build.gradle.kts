plugins {
    kotlin("jvm")
}

val coroutinesVersion: String by project

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    implementation(project(Modules.common))
    implementation(project(Modules.stubs))

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
}