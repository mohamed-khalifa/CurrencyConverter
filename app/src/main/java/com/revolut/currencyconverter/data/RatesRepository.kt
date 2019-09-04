package com.revolut.currencyconverter.data

import com.revolut.currencyconverter.model.RatesApiResponseModel
import io.reactivex.Single

class RatesRepository {
    lateinit var ratesRemoteDataSource: RatesDataSource
    fun getRates(baseCurrency: String): Single<RatesApiResponseModel> {
        ratesRemoteDataSource = RatesRemoteDataSource()
        return ratesRemoteDataSource.getRates(baseCurrency)
    }

}