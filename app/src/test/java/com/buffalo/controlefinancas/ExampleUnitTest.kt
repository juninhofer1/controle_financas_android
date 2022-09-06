package com.buffalo.controlefinancas

import com.buffalo.controlefinancas.ui.Cryptography
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val teste = Cryptography().encrypt("sistema123", "2RFonFEbZ2t7cDFdzeN6AYZJnCBFm8ad")
        assertEquals(teste, "Il84HXcB3oA2L3wHNiJ/6A==")
    }
}