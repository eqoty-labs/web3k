rootProject.name = "web3k"



pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    val versions = java.util.Properties().apply {
        load(File("versions.properties").reader())
    }

    plugins {
        id("de.fayard.refreshVersions") version versions["version.refreshVersions"] as String
        kotlin("multiplatform") version versions["version.kotlin"] as String
        id("com.vanniktech.maven.publish") version versions["version.gradleMavenPublishPlugin"] as String
    }
}

plugins {
    id("de.fayard.refreshVersions")
}


refreshVersions { // Optional: configure the plugin
    // ...
}

include(
    ":web3swift"
)
