package com.revolut.currencyconverter.usecase

import com.revolut.currencyconverter.data.RatesRepository
import com.revolut.currencyconverter.model.RatesApiResponseModel
import com.revolut.currencyconverter.model.RatesModel
import com.revolut.currencyconverter.util.UnitTestChecker
import com.revolut.currencyconverter.util.roundTwoDigits
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit


class RatesUseCase {
    private var baseCurrency: String = "EUR"
    fun getRates(
        baseCurrency: String, rate: Double = 1.0
    ): Flowable<MutableList<RatesModel>> {
        this.baseCurrency = baseCurrency
        val ratesRepository = RatesRepository()
        return ratesRepository.getRates(baseCurrency)
            .delay(1, TimeUnit.SECONDS)
            .repeat()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map {
                getRatesList(it, rate)
            }

    }


    private fun getRatesList(
        ratesApiResponseModel: RatesApiResponseModel, rate: Double
    ): MutableList<RatesModel> {
        val ratesList = mutableListOf<RatesModel>()
        ratesList.add(
            RatesModel(
                baseCurrency,
                getCurrencyName(baseCurrency),
                rate.roundTwoDigits(),
                getFlagUrl(baseCurrency)
            )
        )
        ratesApiResponseModel.rates?.map { currency ->
            ratesList.add(
                RatesModel(
                    currency.key,
                    getCurrencyName(currency.key),
                    (currency.value * rate).roundTwoDigits(),
                    getFlagUrl(currency.key)
                )
            )
        }

        return ratesList
    }

     fun getCurrencyName(currencyCode: String): String {
        val currency = Currency.getInstance(currencyCode)
        return currency.displayName
    }

    private fun getFlagUrl(currencyCode: String): String {
        return "https://currency.morgrowe.com/images/flag-icons-256/${currencyCode.toLowerCase()}.png"
    }


}