package io.eqoty.web3

import swift.web3swift.Web3Swift

actual object Web3 {
    actual fun getAddress(privKey: String): String {
        return Web3Swift.getAddressWithPrivKey(privKey)
    }


}