package org.demo.crypto

expect object Web3 {

    fun getAddress(privKey: String): String

}
