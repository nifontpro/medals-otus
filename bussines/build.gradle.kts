plugins {
    kotlin("jvm")
}

val coroutinesVersion: String by project

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.stubs))
    implementation(project(Modules.CoR))
    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    testImplementation(kotlin("test-junit"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}