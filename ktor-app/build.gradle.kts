import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project

// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.0.1" with only ktor("netty")
fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    application
    id("io.ktor.plugin") version "2.1.0"
    id("com.bmuschko.docker-java-application")
    id("com.bmuschko.docker-remote-api")
    kotlin("plugin.serialization")
}

application {
    mainClass.set("ru.otus.ApplicationKt")
    /*
        val isDevelopment: Boolean = project.ext.has("development")
        applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")*/
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":transport-main-openapi"))
    implementation(project(":mappers"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
    implementation(ktor("netty")) // "io.ktor:ktor-ktor-server-netty:$ktorVersion"

    // jackson
    implementation(ktor("jackson", "serialization")) // io.ktor:ktor-serialization-jackson
    implementation(ktor("content-negotiation")) // io.ktor:ktor-server-content-negotiation
//    implementation(ktor("kotlinx-json", "serialization")) // io.ktor:ktor-serialization-kotlinx-json

    implementation(ktor("locations"))
    implementation(ktor("caching-headers"))
    implementation(ktor("call-logging"))
    implementation(ktor("auto-head-response"))
    implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("default-headers")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
    implementation(ktor("auto-head-response"))
    implementation(ktor("websockets"))

    implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
    implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"
    implementation(ktor("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    implementation(ktor("content-negotiation", prefix = "client-"))
    implementation(ktor("websockets", prefix = "client-"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass.get())
        baseImage.set("adoptopenjdk/openjdk17:alpine-jre")
        maintainer.set("(c) Otus")
        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
            )
        )
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
}