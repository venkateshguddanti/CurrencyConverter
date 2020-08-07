package com.venkat.currencyconverter.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.venkat.currencyconverter.R
import kotlinx.android.synthetic.main.list_item_currency.view.*
import java.math.BigDecimal

class CurrencyAdapter(private var input : BigDecimal,private val exchangeRates : MutableMap<String,BigDecimal>):
    RecyclerView.Adapter<CurrencyAdapter.DataHolder>() {

    class DataHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind( name : String,value : BigDecimal )
        {
            itemView.apply {
                currencyName.text = name
                currencyValue.text = value.toPlainString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        return DataHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_currency,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return exchangeRates.size
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
           holder.bind(exchangeRates.keys.elementAt(position),input*exchangeRates.values.elementAt(position))
    }

    fun addRows(input : BigDecimal,data : Map<String,BigDecimal>?)
    {
        this.input = input
         this.exchangeRates.apply {
              exchangeRates.clear()
              data?.let { putAll(data) }
         }
    }
}