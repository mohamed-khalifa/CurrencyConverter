package com.revolut.currencyconverter.util

import com.revolut.currencyconverter.util.ErrorHandler
import org.junit.Test

import org.junit.Assert.*
import java.net.UnknownHostException

class ErrorHandlerTest {

    @Test
    fun testInternetError() {
        assertEquals(
            ErrorHandler.getError(UnknownHostException()).errorMessage,
            "No Internet Connection"
        )

    }


    @Test
    fun testGeneralError() {
        assertEquals(
            ErrorHandler.getError(IllegalArgumentException()).errorMessage,
            "Error Retrieving data "
        )

    }
}