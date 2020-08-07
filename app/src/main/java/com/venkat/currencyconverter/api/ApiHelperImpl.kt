package com.venkat.currencyconverter.api

import com.venkat.currencyconverter.model.ExchangeRates

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getExchangeRates(): ExchangeRates = apiService.getExchangeRates()
}