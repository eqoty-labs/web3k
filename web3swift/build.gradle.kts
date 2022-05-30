listOf("iphoneos", "iphonesimulator", "macosx", "appletvos", "appletvsimulator", "watchos", "watchsimulator").forEach { sdk ->
    tasks.create<Exec>("build${sdk.capitalize()}") {
        group = "build"

        commandLine(
            "xcodebuild",
            "-project", "web3swift.xcodeproj",
            "-scheme", "Web3Swift",
            "-sdk", sdk,
            "-configuration", "Release",
            "-derivedDataPath", "build/derivedData",
        )
        workingDir(projectDir)

        inputs.files(
            fileTree("$projectDir/web3swift.xcodeproj") { exclude("**/xcuserdata") },
            fileTree("$projectDir/web3swift")
        )
        outputs.files(
            fileTree("$projectDir/build/Release-${sdk}")
        )
        doLast {
            copy {
                from("build/derivedData/Build/Products")
                into("build")
            }
        }
    }
}

tasks.create<Delete>("clean") {
    group = "build"

    delete("$projectDir/build")
}
