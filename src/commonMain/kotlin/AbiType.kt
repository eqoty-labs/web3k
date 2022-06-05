package io.eqoty.web3;

import com.ionspin.kotlin.bignum.integer.BigInteger

sealed class AbiType <T>{
    abstract val value: T
}

data class AbiAddress(override val value: String): AbiType<String>()
data class AbiBool(override val value: Boolean): AbiType<Boolean>()
data class AbiString(override val value: String): AbiType<String>()
data class AbiUInt256(override val value: String): AbiType<String>(){
    constructor(bigInteger: BigInteger): this(bigInteger.toHexString())
}
data class AbiInt256(override val value: String): AbiType<String>(){
    constructor(bigInteger: BigInteger): this(bigInteger.toHexString())
}
data class AbiUInt64(override val value: Long): AbiType<Long>()
data class AbiUInt32(override val value: Int): AbiType<Int>()
data class AbiUInt16(override val value: Int): AbiType<Int>()
data class AbiUInt8(override val value: Int): AbiType<Int>()