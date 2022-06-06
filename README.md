# web3k

A Kotlin multiplatform ETH web3 client that delegates to web3.swift and web3j (todo)


## Requirements
iOS: Requires a min iOS version of 13

`gradle.properties`
```
kotlin.native.osVersionMin.ios_x64 = 13.0
kotlin.native.osVersionMin.ios_arm64 = 13.0
kotlin.native.osVersionMin.ios_simulator_arm64 = 13.0
```

## Setup
```
dependencies {
    implementation("io.eqoty.web3k:client:0.1.0")
}
```

## Credits

- Swift interop with kotlin: [playground-SwiftLib-in-KMMLib](https://github.com/SalomonBrys/Demo-SwiftLib-in-KMMLib/) 
    - Used by example to interop kotlin with [web3.swift](https://github.com/argentlabs/web3.swift)
