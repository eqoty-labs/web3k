rootProject.name = "web3k"



pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("multiplatform") version "1.6.21"
        id("com.vanniktech.maven.publish") version "0.20.0"
    }
}

include(
    ":web3swift"
)
