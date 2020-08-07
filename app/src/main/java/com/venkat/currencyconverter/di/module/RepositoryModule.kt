package com.venkat.currencyconverter.di.module

import com.venkat.currencyconverter.repo.MainRepository
import org.koin.dsl.module

val reposModule = module {
    single { MainRepository(get()) }
}