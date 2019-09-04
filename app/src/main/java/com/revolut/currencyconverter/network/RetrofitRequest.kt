package com.revolut.currencyconverter.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitRequest {
    private const val BASE_URL: String = "https://revolut.duckdns.org"

    private val retrofit: Retrofit = getRetrofitCall()
    fun <T> getRetrofitClient(apiClient: Class<T>): T {
        return retrofit.create(apiClient)
    }

    private fun getRetrofitCall(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


}