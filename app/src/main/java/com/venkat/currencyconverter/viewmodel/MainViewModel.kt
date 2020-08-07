package com.venkat.currencyconverter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venkat.currencyconverter.model.ExchangeRates
import com.venkat.currencyconverter.model.Resource
import com.venkat.currencyconverter.repo.MainRepository
import com.venkat.currencyconverter.util.NetworkHelper
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MainViewModel(private val mainRepository: MainRepository,
                    private val networkHelper: NetworkHelper) : ViewModel()
{
     val exchangeRates = MutableLiveData<Resource<ExchangeRates>>()
     private var currencyMap : Map<String,BigDecimal> = mapOf()

    init {
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        viewModelScope.launch {
            exchangeRates.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected())
            {
                 mainRepository.getExchangeRates().let {
                     try {
                         exchangeRates.postValue(Resource.success(it))
                         currencyMap = it.rates
                     } catch (e: Exception) {
                         exchangeRates.postValue(Resource.error(null,"Error fetching data"))
                     }
                 }
            }else
            {
                exchangeRates.postValue(Resource.error(null,"No network connection"))
            }
        }
    }
}