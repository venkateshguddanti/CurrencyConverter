package com.venkat.currencyconverter.domain

import com.venkat.currencyconverter.model.ExchangeRates
import retrofit2.http.GET

interface ApiService {
    @GET("latest?base=USD")
    suspend fun getExchangeRates() : ExchangeRates
}