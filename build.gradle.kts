import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "1.6.21"
    `maven-publish`
}

group = "org.demo.crypto"
version = "1.0"

repositories {
    mavenCentral()
}

kotlin {
    val darwinTargets = arrayOf(
        "macosX64", "macosArm64",
        "iosArm64", "iosX64", "iosSimulatorArm64",
    )

    jvm()

    for (target in darwinTargets) {
        val darwinTarget = presets.getByName(target).createTarget(target) as KotlinNativeTarget
        val platform = when (target) {
            "iosArm64" -> "iphoneos"
            "iosX64", "iosSimulatorArm64" -> "iphonesimulator"
            "macosX64", "macosArm64" -> "macosx"
            else -> error("Unsupported target $name")
        }
        darwinTarget.apply {
            compilations.getByName("main") {
                cinterops.create("web3swift") {
                    val interopTask = tasks[interopProcessingTaskName]
                    interopTask.dependsOn(":web3swift:build${platform.capitalize()}")
                    if (platform == "macosx") {
                        includeDirs.headerFilterOnly("$rootDir/web3swift/build/Release/include")
                    } else {
                        includeDirs.headerFilterOnly("$rootDir/web3swift/build/Release-$platform/include")
                    }
                }
            }
        }
        targets.add(darwinTarget)
    }

    sourceSets {
        val commonMain by getting

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val darwinMain by creating {
            dependsOn(commonMain)
        }

        darwinTargets.forEach { target ->
            getByName("${target}Main") {
                dependsOn(darwinMain)
            }
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}
