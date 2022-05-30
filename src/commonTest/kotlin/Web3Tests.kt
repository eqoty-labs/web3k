package io.eqoty.web3

import kotlin.test.Test
import kotlin.test.assertEquals

class Web3Tests {
    @Test fun `hex prefixed private key to address`() {

        val privKey = "0xeff8ddb99a4fa276e8e70f4c375d99964511b2ac1506ee7e48dcff43d6bc04b9"
        val expectedAddress = "0x2AD093baD5b62F51A2145B87bB0F2295b1f9d4Fd".lowercase()


        assertEquals(expectedAddress, Web3.getAddress(privKey) )
    }

    @Test fun `private key to address`() {

        val privKey = "eff8ddb99a4fa276e8e70f4c375d99964511b2ac1506ee7e48dcff43d6bc04b9"
        val expectedAddress = "0x2AD093baD5b62F51A2145B87bB0F2295b1f9d4Fd".lowercase()


        assertEquals(expectedAddress, Web3.getAddress(privKey) )
    }

}
