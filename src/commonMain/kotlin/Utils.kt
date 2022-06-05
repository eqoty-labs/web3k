package io.eqoty.web3

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger

internal fun BigInteger.toHexString() : String {
    val nonPrefixedAddress = toString(16)
    return if(nonPrefixedAddress.length % 2 == 0) {
        "0x$nonPrefixedAddress"
    } else{
        // force addresses to have even digit length
        "0x0$nonPrefixedAddress"
    }
}

val String.bi16 get() = this.split('x', ignoreCase = true).last().toBigInteger(16)
val String.bi10 get() = this.toBigInteger()