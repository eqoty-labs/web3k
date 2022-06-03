@file:Suppress("VARIABLE_IN_SINGLETON_WITHOUT_THREAD_LOCAL")

package io.eqoty.web3

import swift.web3swift.Web3Swift

actual object Web3 {
    var ethereumAccount: Any? = null

    actual fun importAccount(privKey: String) {
        ethereumAccount = Web3Swift.importAccountWithPrivKey(privKey)
    }

    actual fun getAddress(): String? {
        return Web3Swift.getAddressWithEthereumAccount(ethereumAccount!!)
    }

}