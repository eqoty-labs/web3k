package io.eqoty.web3

import com.ionspin.kotlin.bignum.integer.BigInteger

expect object Web3 {

    fun createClient(url: String)
    fun importAccount(privKey: String)
    fun getAddress(): String?
    suspend fun estimateGas(contractAddress: String, functionName: String, functionParams: List<AbiType<*>>): BigInteger
    suspend fun executeTx(
        contractAddress: String,
        functionName: String,
        functionParams: List<AbiType<*>>,
        gasLimit: BigInteger,
        sendValue: BigInteger?
    ): String
}
