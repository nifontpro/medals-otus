plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(Modules.transport))
    implementation(project(Modules.common))

    testImplementation(kotlin("test-junit"))
}