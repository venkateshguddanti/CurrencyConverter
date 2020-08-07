package com.venkat.currencyconverter.model

import java.math.BigDecimal

data class ExchangeRates(
    val rates : Map<String,BigDecimal>,
    val base : String,
    val date : String
)