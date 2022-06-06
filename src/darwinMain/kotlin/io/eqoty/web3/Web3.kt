@file:Suppress("VARIABLE_IN_SINGLETON_WITHOUT_THREAD_LOCAL")

package io.eqoty.web3

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import swift.web3swift.*

private val AbiType<*>.swiftAbiType: ABIType
    get() =
        when (this) {
            is AbiAddress -> ABITypeAddress
            is AbiBool -> ABITypeBool
            is AbiInt256 -> ABITypeInt256
            is AbiString -> ABITypeString
            is AbiUInt16 -> ABITypeUint16
            is AbiUInt256 -> ABITypeUint256
            is AbiUInt32 -> ABITypeUint32
            is AbiUInt64 -> ABITypeUint64
            is AbiUInt8 -> ABITypeUint8
            is AbiAddressArray -> ABITypeAddressArray
            is AbiBoolArray -> ABITypeBoolArray
            is AbiInt256Array -> ABITypeInt256Array
            is AbiStringArray -> ABITypeStringArray
            is AbiUInt16Array -> ABITypeUint16Array
            is AbiUInt256Array -> ABITypeUint256Array
            is AbiUInt32Array -> ABITypeUint32Array
            is AbiUInt64Array -> ABITypeUint64Array
            is AbiUInt8Array -> ABITypeUint8Array
        }

actual object Web3 {
    var client: Any? = null
    var ethereumAccount: Any? = null

    actual fun createClient(url: String) {
        client = Web3Swift.createClientWithUrl(url)
    }

    actual fun importAccount(privKey: String) {
        ethereumAccount = Web3Swift.importAccountWithPrivKey(privKey)
    }

    actual fun getAddress(): String? {
        return Web3Swift.getAddressWithEthereumAccount(ethereumAccount!!)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    actual suspend fun estimateGas(
        contractAddress: String,
        functionName: String,
        functionParams: List<AbiType<*>>,
        sendValue: BigInteger?
    ): BigInteger =
        suspendCancellableCoroutine { continuation ->
            val functionParamsData = functionParams.map { it.value }
            val functionParamsTypes = functionParams.map { it.swiftAbiType }
            try {
                Web3Swift.estimateGasWithEthereumClient(
                    client!!,
                    ethereumAccount!!,
                    contractAddress,
                    functionName,
                    functionParamsData,
                    functionParamsTypes,
                    sendValue?.toHexString()
                ) { error, estimate ->
                    if (error != null) {
                        continuation.cancel(Error(error.toString()))
                    } else {
                        continuation.resume(estimate!!.bi16, null)
                    }
                }
            } catch (t: Throwable){
                continuation.cancel(t)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    actual suspend fun executeTx(
        contractAddress: String,
        functionName: String,
        functionParams: List<AbiType<*>>,
        gasLimit: BigInteger,
        sendValue: BigInteger?
    ): TxResult =
        suspendCancellableCoroutine { continuation ->
            val functionParamsData = functionParams.map { it.value }
            val functionParamsTypes = functionParams.map { it.swiftAbiType }
            try {
                Web3Swift.sendRawTransactionWithEthereumClient(
                    client!!,
                    ethereumAccount!!,
                    contractAddress,
                    functionName,
                    functionParamsData,
                    functionParamsTypes,
                    gasLimit.toHexString(),
                    sendValue?.toHexString()
                    ) { error, txHash, nonce ->
                    if (error != null) {
                        continuation.cancel(Error(error.toString()))
                    } else {
                        continuation.resume(TxResult(error?.let{ toString() }, txHash, nonce.toBigInteger()),null)
                    }
                }
            } catch (t: Throwable){
                continuation.cancel(t)
            }
        }

}