package com.venkat.currencyconverter.api

import com.venkat.currencyconverter.model.ExchangeRates

interface ApiHelper
{
    suspend fun getExchangeRates(): ExchangeRates
}