package com.revolut.currencyconverter.util


object UnitTestChecker {
    //workaround to check if unit test is running
    fun isTestMode(): Boolean {
        return  try {
            Class.forName("com.revolut.currencyconverter.viewmodel.RatesViewModelTest")
            true
        } catch (e: Exception) {
            false
        }
    }
}