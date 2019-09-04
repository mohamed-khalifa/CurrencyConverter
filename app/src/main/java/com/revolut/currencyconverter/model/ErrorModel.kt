package com.revolut.currencyconverter.model

data class ErrorModel(val throwable: Throwable, val errorMessage: String?, val statusCode: Int = -1)


