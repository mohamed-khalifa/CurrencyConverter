package com.revolut.currencyconverter.network

import com.revolut.currencyconverter.model.RatesApiResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesClient {
    @GET("/latest")
    fun getRates(@Query("base") baseCurrency: String): Single<RatesApiResponseModel>
}