package com.revolut.currencyconverter.data

import com.revolut.currencyconverter.model.RatesApiResponseModel
import io.reactivex.Single

interface RatesDataSource {
    fun getRates(baseCurrency: String): Single<RatesApiResponseModel>
}


