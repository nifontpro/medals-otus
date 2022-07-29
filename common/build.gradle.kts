plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(Modules.transport))
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
}