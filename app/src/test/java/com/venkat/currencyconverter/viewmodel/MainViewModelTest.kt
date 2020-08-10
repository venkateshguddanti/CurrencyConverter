package com.venkat.currencyconverter.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.venkat.currencyconverter.model.ExchangeRates
import com.venkat.currencyconverter.model.Resource
import com.venkat.currencyconverter.repo.MainRepository
import com.venkat.currencyconverter.util.NetworkHelper
import com.venkat.currencyconverter.util.Status
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{

    private val dispatcher = TestCoroutineDispatcher()
    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    private val testCoroutineRule = TestCoroutineRule()
    @Mock
    private lateinit var mainRepository: MainRepository

    private lateinit var exchangeRates: ExchangeRates
    @Mock
    private lateinit var apiObserver : Observer<Resource<ExchangeRates>>
    @Mock
    private lateinit var networkHelper : NetworkHelper
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        exchangeRates = ExchangeRates(mapOf(),"USD","10-08-2020")
    }
    @Test
    fun fetch_currency_with_success()
    {
        testCoroutineRule.runBlockingTest {
            doReturn(exchangeRates).`when`(mainRepository).getExchangeRates()
            `when`(networkHelper.isNetworkConnected()).thenReturn(true)
             assertTrue(networkHelper.isNetworkConnected())
            val viewModel = MainViewModel(mainRepository,networkHelper)
            viewModel.fetchExchangeRates()
            viewModel.getExchangeRates().observeForever(apiObserver)
            launch {
                verify(mainRepository).getExchangeRates()
            }
            verify(apiObserver).onChanged(Resource(status=Status.SUCCESS, data=exchangeRates, message=null))
            viewModel.getExchangeRates().removeObserver(apiObserver)
        }
    }
    @Test
    fun fetch_currency_with_failure()
    {
        testCoroutineRule.runBlockingTest {
            val errorMsg = "No network connection"
            val viewModel = MainViewModel(mainRepository,networkHelper)
            viewModel.fetchExchangeRates()
            viewModel.getExchangeRates().observeForever(apiObserver)
            verify(apiObserver).onChanged(Resource(status = Status.ERROR,data = null,message = errorMsg))
            viewModel.getExchangeRates().removeObserver(apiObserver)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}