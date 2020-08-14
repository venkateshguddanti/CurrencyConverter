package com.venkat.currencyconverter.di.module

import android.content.Context
import com.venkat.currencyconverter.BuildConfig
import com.venkat.currencyconverter.domain.ApiHelper
import com.venkat.currencyconverter.data.ApiHelperImpl
import com.venkat.currencyconverter.domain.ApiService
import com.venkat.currencyconverter.util.NetworkHelper
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(),BuildConfig.BASE_URL) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }
    single<ApiHelper>{
        return@single ApiHelperImpl(get())
    }
}
private fun provideOkHttpClient() = OkHttpClient.Builder().build()
private fun provideRetrofit(okHttpClient: OkHttpClient,BASE_URL :String) : Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
private fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
private fun provideNetworkHelper(context: Context) = NetworkHelper(context)