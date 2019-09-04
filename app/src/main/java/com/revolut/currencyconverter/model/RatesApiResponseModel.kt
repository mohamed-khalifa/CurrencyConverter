package com.revolut.currencyconverter.model

data class RatesApiResponseModel(val date: String?, val rates: Map<String, Double>?, val base: String?)