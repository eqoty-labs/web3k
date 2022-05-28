listOf("iphoneos", "iphonesimulator", "macosx", "appletvos", "appletvsimulator", "watchos", "watchsimulator").forEach { sdk ->
    tasks.create<Exec>("build${sdk.capitalize()}") {
        group = "build"

        commandLine(
            "xcodebuild",
            "-project", "web3swift.xcodeproj",
            "-target", "web3swift",
            "-sdk", sdk,
        )
        workingDir(projectDir)

        inputs.files(
            fileTree("$projectDir/web3swift.xcodeproj") { exclude("**/xcuserdata") },
            fileTree("$projectDir/web3swift")
        )
        outputs.files(
            fileTree("$projectDir/build/Release-${sdk}")
        )
    }
}

tasks.create<Delete>("clean") {
    group = "build"

    delete("$projectDir/build")
}
