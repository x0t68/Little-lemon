pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("jvm") version "1.9.22"
        id("org.jetbrains.kotlin.plugin.compose") version "1.5.8"
        id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Little Lemon"
include(":app")
