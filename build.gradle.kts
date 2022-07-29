plugins {
    kotlin("jvm") apply false
}

group = "ru.otus"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

/*
tasks.test {
    useJUnitPlatform()
}
*/
/*

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}*/
