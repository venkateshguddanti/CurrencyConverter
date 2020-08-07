package com.venkat.currencyconverter

import android.app.Application
import com.venkat.currencyconverter.di.module.appModule
import com.venkat.currencyconverter.di.module.reposModule
import com.venkat.currencyconverter.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application()
{
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, reposModule, viewModelModule))
        }
    }
}