package com.venkat.currencyconverter.domain

import com.venkat.currencyconverter.model.ExchangeRates

interface ApiHelper
{
    suspend fun getExchangeRates(): ExchangeRates
}