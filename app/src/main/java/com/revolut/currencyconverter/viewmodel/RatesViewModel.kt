package com.revolut.currencyconverter.viewmodel

import androidx.lifecycle.*
import com.revolut.currencyconverter.model.ErrorModel
import com.revolut.currencyconverter.model.RatesModel
import com.revolut.currencyconverter.usecase.RatesUseCase
import com.revolut.currencyconverter.util.ErrorHandler
import io.reactivex.disposables.Disposable

class RatesViewModel(
    private val ratesUseCase: RatesUseCase =
        RatesUseCase()
) : ViewModel() {
    lateinit var rates: MutableLiveData<MutableList<RatesModel>>
    lateinit var errorModel: MutableLiveData<ErrorModel>

    private var disposable: Disposable? = null


    fun getRates(baseCurrency: String = "EUR", rate: Double = 1.0) {
        disposable?.dispose()
        disposable = ratesUseCase.getRates(baseCurrency, rate)
            .subscribe(
                {
                    rates.postValue(it)
                },
                {
                    errorModel.postValue(
                        ErrorHandler.getError(throwable = it)
                    )
                })

    }


    fun initObservers() {
        rates = MutableLiveData()
        errorModel = MutableLiveData()
    }


    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun dispose() {
        disposable?.dispose()
    }

}
