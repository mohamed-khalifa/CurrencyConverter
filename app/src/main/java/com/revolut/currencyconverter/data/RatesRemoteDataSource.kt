package com.revolut.currencyconverter.data

import com.revolut.currencyconverter.model.RatesApiResponseModel
import com.revolut.currencyconverter.network.RatesClient
import com.revolut.currencyconverter.network.RetrofitRequest
import io.reactivex.Single


open class RatesRemoteDataSource : RatesDataSource {
    override fun getRates(baseCurrency: String): Single<RatesApiResponseModel> {
        return RetrofitRequest.getRetrofitClient(RatesClient::class.java).getRates(baseCurrency)
    }

}