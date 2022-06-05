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

data class AbiAddressArray(override val value: List<String>): AbiType<List<String>>()
data class AbiBoolArray(override val value: List<Boolean>): AbiType<List<Boolean>>()
data class AbiStringArray(override val value: List<String>): AbiType<List<String>>()
data class AbiUInt256Array(override val value: List<String>): AbiType<List<String>>(){
    constructor(bigIntegers: List<BigInteger>): this(bigIntegers.map { it.toHexString() })
}
data class AbiInt256Array(override val value: List<String>): AbiType<List<String>>(){
    constructor(bigIntegers: List<BigInteger>): this(bigIntegers.map { it.toHexString() })
}
data class AbiUInt64Array(override val value: List<Long>): AbiType<List<Long>>()
data class AbiUInt32Array(override val value: List<Int>): AbiType<List<Int>>()
data class AbiUInt16Array(override val value: List<Int>): AbiType<List<Int>>()
data class AbiUInt8Array(override val value: List<Int>): AbiType<List<Int>>()

