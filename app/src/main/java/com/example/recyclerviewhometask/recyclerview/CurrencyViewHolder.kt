package com.example.recyclerviewhometask.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.R
import com.example.recyclerviewhometask.model.Item
import com.google.android.material.textfield.TextInputLayout

class CurrencyViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {

    private val currencyTV: TextView = view.findViewById(R.id.currency_tv)
    private val flagIV: ImageView = view.findViewById(R.id.flag_IV)
    private val inputCurrency: TextInputLayout = view.findViewById(R.id.text_input_currency)

    fun bind(currency: Item.Currency, onImageLongClicked: OnLongClicked) {
        currencyTV.text = currency.currency
        flagIV.setImageResource(currency.flagDrawableRes)
        inputCurrency.editText?.setText(currency.amount.toString())
        flagIV.setOnLongClickListener {
            onImageLongClicked.changeToolbarState()
            onImageLongClicked.itemToDelete(currency)
            return@setOnLongClickListener true
        }
    }

}


