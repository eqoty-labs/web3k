package io.eqoty.web3

expect object Web3 {

    fun importAccount(privKey: String)
    fun getAddress(): String?

}
