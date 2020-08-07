package com.venkat.currencyconverter.di.module

import com.venkat.currencyconverter.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(),get()) }
}