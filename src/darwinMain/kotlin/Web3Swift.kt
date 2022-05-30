package org.demo.crypto


actual object Web3Swift {
    actual fun getAddress(privKey: String): String {
        return swift.web3swift.Web3Swift.getAddressWithPrivKey(privKey)
    }


}