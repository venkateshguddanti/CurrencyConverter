package com.venkat.currencyconverter.repo

import com.venkat.currencyconverter.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getExchangeRates() = apiHelper.getExchangeRates()
}