rootProject.name = "medals"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val kotestVersion: String by settings
        val openapiVersion: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        id("io.kotest.multiplatform") version kotestVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false

    }
}

include("common")
include("transport-main-openapi")
include("mappers")
include("ktor-app")
include("comment-stubs")
include("core")
include("cor")
include("bussines")
include("konform")
include("ok-marketplace-lib-validation")
include("kafka")
include("repo-inmemory")
