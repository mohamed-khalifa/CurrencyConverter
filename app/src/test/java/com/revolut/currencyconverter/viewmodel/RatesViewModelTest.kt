package com.revolut.currencyconverter.viewmodel

import com.revolut.currencyconverter.usecase.RatesUseCase
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import RxImmediateSchedulerRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule


class RatesViewModelTest {
    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()
    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()
    lateinit var ratesUseCase: RatesUseCase
    lateinit var ratesViewModel: RatesViewModel


    @Before
    fun setUp() {
        ratesUseCase = RatesUseCase()
        ratesViewModel = RatesViewModel(ratesUseCase)
        ratesViewModel.initObservers()

    }


    @Test
    fun testGetRatesNotEmpty() {
        ratesViewModel.getRates()
        assertNotEquals(ratesViewModel.rates.value?.size, 0)
    }

    @Test
    fun testGetRatesResponseFirstItemWithSelectedBase() {
        ratesViewModel.getRates("AUD", 2.0)
        assertEquals(ratesViewModel.rates.value?.get(0)?.currencySymbol, "AUD")
    }



}