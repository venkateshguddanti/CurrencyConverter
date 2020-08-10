package com.venkat.currencyconverter.ui.view

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.venkat.currencyconverter.R
import com.venkat.currencyconverter.ui.adapter.CurrencyAdapter
import com.venkat.currencyconverter.util.Status
import com.venkat.currencyconverter.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModel()
    private lateinit var adapter : CurrencyAdapter
    private var input = BigDecimal(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleIntent()
        setUpUi()
        setUpObservers()
    }

    private fun handleIntent() {
        if(Intent.ACTION_SEARCH == intent.action)
        {
            val searchValue = intent.getStringExtra(SearchManager.QUERY)
            if(searchValue.toString() != ".")
            input = BigDecimal(searchValue)
        }
    }

    private fun setUpObservers() {

        mainViewModel.exchangeRates.observe(this, Observer {
            when(it.status)
            {
                Status.LOADING-> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS->
                {
                    progressBar.visibility = View.GONE
                    it.data?.let { data -> renderList(data.rates,data.base)}
                    title = getString(R.string.text_app_bar)
                    dateLabel.text = getString(R.string.text_date,it.data?.date)
                }
                Status.ERROR-> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }

            }
        })
    }

    private fun setUpUi() {
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            listCurrency.layoutManager = GridLayoutManager(this, 2)
        }else
        {
            listCurrency.layoutManager = GridLayoutManager(this, 4)
        }
        adapter = CurrencyAdapter(input, mutableMapOf())
        listCurrency.adapter = adapter
    }

    private fun renderList(data: Map<String, BigDecimal>?,base : String?)
    {
        if(input.equals(BigDecimal(0)))
        {
            return
        }
        adapter.apply {
            dateLayout.visibility = View.VISIBLE
            baseCurrency.text = getString(R.string.text_currency,"$input $base")
            addRows(input, data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.search)?.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        val searchMenu = menu.findItem(R.id.search)
        val search =searchMenu.actionView as SearchView
        search.queryHint = getString(R.string.text_enter_value)
        val inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        searchMenu.setOnActionExpandListener(object : MenuItem.OnActionExpandListener
        {
            override fun onMenuItemActionExpand(p: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p: MenuItem?): Boolean {
                inputManager.hideSoftInputFromWindow(currentFocus?.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
                return true
            }

        })
        return true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent()
        renderList(mainViewModel.exchangeRates.value?.data?.rates,
            mainViewModel.exchangeRates.value?.data?.base)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.fetchExchangeRates()
    }


}
