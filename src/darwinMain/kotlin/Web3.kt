package org.demo.crypto


actual object Web3 {
    actual fun getAddress(privKey: String): String {
        return swift.web3swift.Web3Swift.getAddressWithPrivKey(privKey)
    }


}