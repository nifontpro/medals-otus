plugins {
    kotlin("jvm")
}

val datetimeVersion: String by project

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
    testImplementation(kotlin("test-junit"))
}