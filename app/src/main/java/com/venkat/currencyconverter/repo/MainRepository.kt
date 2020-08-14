package com.venkat.currencyconverter.repo

import com.venkat.currencyconverter.domain.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getExchangeRates() = apiHelper.getExchangeRates()
}