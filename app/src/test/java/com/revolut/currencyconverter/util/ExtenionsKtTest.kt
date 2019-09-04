package com.revolut.currencyconverter.util

import org.junit.Test

import org.junit.Assert.*

class ExtensionsTest {

    @Test
    fun roundTwoDigits() {
        assertTrue(5.124689.roundTwoDigits().equals(5.12))
    }

}