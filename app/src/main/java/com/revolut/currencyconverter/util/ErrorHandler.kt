package com.revolut.currencyconverter.util

import com.revolut.currencyconverter.model.ErrorModel
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


object ErrorHandler {

    fun getError(throwable: Throwable): ErrorModel = when (throwable) {
        is SocketTimeoutException -> {
            ErrorModel(throwable, "Socket Time Out")
        }
        is HttpException -> {
            ErrorModel(
                throwable,
                throwable.message(),
                throwable.code()
            )
        }

        is UnknownHostException -> {
            ErrorModel(throwable, "No Internet Connection")
        }

        else -> {
            ErrorModel(throwable, "Error Retrieving data ")
        }

    }
}
