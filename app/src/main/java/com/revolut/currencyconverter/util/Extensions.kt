package com.revolut.currencyconverter.util

import kotlin.math.roundToLong


fun Double.roundTwoDigits(): Double {
    return (this * 100).roundToLong() / 100.0
}




