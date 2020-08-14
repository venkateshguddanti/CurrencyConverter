package com.venkat.currencyconverter.data

import com.venkat.currencyconverter.domain.ApiHelper
import com.venkat.currencyconverter.domain.ApiService
import com.venkat.currencyconverter.model.ExchangeRates

class ApiHelperImpl(private val apiService: ApiService) :
    ApiHelper {
    override suspend fun getExchangeRates(): ExchangeRates = apiService.getExchangeRates()
}