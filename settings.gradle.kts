rootProject.name = "medals"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
    }
}

include("common")
include("transport-main-openapi")
