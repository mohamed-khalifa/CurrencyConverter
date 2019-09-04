package com.revolut.currencyconverter.usecase

import com.revolut.currencyconverter.usecase.RatesUseCase

import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import RxImmediateSchedulerRule


class RatesUseCaseTest {
    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()
    lateinit var ratesUseCase: RatesUseCase

    @Before
    fun setUp() {
        ratesUseCase = RatesUseCase()
    }

    @Test
    fun getRatesNotEmpty() {
        val ratesUseCaseTest = ratesUseCase.getRates("EUR", 1.0).test()
        ratesUseCaseTest.assertValue {
            it.size > 0
        }
    }

    @Test
    fun getCurrencyNameForUSD() {
        Assert.assertEquals(ratesUseCase.getCurrencyName("USD"), "US Dollar")
    }

}