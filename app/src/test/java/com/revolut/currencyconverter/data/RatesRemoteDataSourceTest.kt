package com.revolut.currencyconverter.data


import com.google.gson.Gson
import com.revolut.currencyconverter.model.RatesApiResponseModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.net.UnknownHostException

class RatesRemoteDataSourceTest {
    @Mock
    var ratesDataSource: RatesDataSource? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getRates() {
        ratesDataSource = RatesRemoteDataSource()
        val ratesUseCaseTest = ratesDataSource?.getRates("EUR")?.test()
        ratesUseCaseTest?.assertValue {
           it.base == "EUR"
        }
    }

    @Test
    fun getMockedRates() {

        val base = "EUR"
        val successResponse =
            "{\"base\":\"EUR\",\"date\":\"2018-09-06\",\"rates\":{\"AUD\":1.6214,\"BGN\":1.9618,\"BRL\":4.8065,\"CAD\":1.5385,\"CHF\":1.131,\"CNY\":7.9695,\"CZK\":25.794,\"DKK\":7.4796,\"GBP\":0.901,\"HKD\":9.1605,\"HRK\":7.4569,\"HUF\":327.49,\"IDR\":17377.0,\"ILS\":4.1834,\"INR\":83.975,\"ISK\":128.19,\"JPY\":129.95,\"KRW\":1308.8,\"MXN\":22.434,\"MYR\":4.8268,\"NOK\":9.806,\"NZD\":1.7687,\"PHP\":62.784,\"PLN\":4.3316,\"RON\":4.6528,\"RUB\":79.819,\"SEK\":10.623,\"SGD\":1.6049,\"THB\":38.247,\"TRY\":7.6516}}"

        val ratesModel =
            Gson().fromJson(successResponse, RatesApiResponseModel::class.java)

        Mockito.`when`(ratesDataSource?.getRates(base)).thenReturn(Single.just(ratesModel))

        val testObserver = ratesDataSource?.getRates(base)?.test()

        testObserver?.assertValue {
            it.rates?.get("CAD") == 1.5385
        }
    }

    @Test
    fun getMockedRatesError() {
        val base = "EUR"

        Mockito.`when`(ratesDataSource?.getRates(base))
            .thenReturn(Single.error(UnknownHostException()))

        val testObserver = ratesDataSource?.getRates(base)?.test()

        testObserver?.assertError {
            it is UnknownHostException
        }
    }

}