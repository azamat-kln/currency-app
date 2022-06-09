package com.example.recyclerviewhometask.recyclerview

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.Data
import com.example.recyclerviewhometask.ItemOperations
import com.example.recyclerviewhometask.R
import com.example.recyclerviewhometask.SortBy
import com.example.recyclerviewhometask.dragdrop.ItemTouchDelegate
import com.example.recyclerviewhometask.model.Item
import java.io.Serializable
import java.util.*

class MyAdapter(
    private val touchDelegateInterface: ItemTouchDelegate,
    private val addInterface: OnAddClicked,
    private val longClickInterface: OnLongClicked
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemOperations, Serializable {

    private val currenciesAndButton: MutableList<Item> = LinkedList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.add_button -> {
                val view: View = inflater.inflate(R.layout.add_button, parent, false)
                ButtonViewHolder(view)
            }
            R.layout.item_currency -> {
                val view: View = inflater.inflate(R.layout.item_currency, parent, false)
                val currencyViewHolder = CurrencyViewHolder(view)
                onItemDrag(currencyViewHolder)
                currencyViewHolder
            }
            else -> {
                val view: View = inflater.inflate(R.layout.add_button, parent, false)
                ButtonViewHolder(view)
            }
        }
    }

    private fun onItemDrag(currencyViewHolder: CurrencyViewHolder) {
        currencyViewHolder.itemView.findViewById<TextView>(R.id.currency_tv)
            .setOnTouchListener { _, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    touchDelegateInterface.startDragging(currencyViewHolder)
                }
                return@setOnTouchListener true
            }
    }

    override fun getItemViewType(position: Int): Int =
        when (currenciesAndButton[position]) {
            is Item.AddButton -> R.layout.add_button
            is Item.Currency -> R.layout.item_currency
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrencyViewHolder -> holder.bind(
                currenciesAndButton[position] as Item.Currency, longClickInterface
            )
            is ButtonViewHolder -> holder.bind(
                currenciesAndButton[position] as Item.AddButton,
                addInterface
            )
        }
    }

    override fun getItemCount() = currenciesAndButton.size

    override fun setItems(data: List<Item>) {
        currenciesAndButton.clear()
        currenciesAndButton.addAll(data)
        notifyDataSetChanged()
    }

    override fun add(currency: Item.Currency, chosenIndex: Int?) {
        val sortingType: SortBy? = when (chosenIndex) {
            0 -> SortBy.ALPHABET
            1 -> SortBy.COST
            else -> null
        }
        when (sortingType) {
            SortBy.ALPHABET -> {
                addItem(currency, sortingType)
            }
            SortBy.COST -> {
                addItem(currency, sortingType)
            }
            else -> addCurrencyToEnd(currency)
        }
    }

    private fun addItem(currency: Item.Currency, sortingType: SortBy) {
        val currencyItems = getCurrencies().toMutableList()
        currencyItems.add(currency)
        sortCurrencies(currencyItems, sortingType)
    }

    private fun addCurrencyToEnd(currency: Item.Currency) {
        val beforeAddBtn = currenciesAndButton.size - 1
        currenciesAndButton.add(beforeAddBtn, currency)
        notifyItemInserted(beforeAddBtn)
    }

    override fun addByIndex(currency: Item.Currency, index: Int) {
        currenciesAndButton.add(index, currency)
        notifyItemInserted(index)
    }

    override fun sortCurrencies(currencies: List<Item.Currency>, sortingType: SortBy) {
        val newList: List<Item.Currency> = when (sortingType) {
            SortBy.ALPHABET -> {
                currencies.sortedBy { it.currency }
            }
            SortBy.COST -> {
                currencies.sortedBy { it.amount }
            }
        }
        setSortedCurrencies(newList)
    }

    private fun setSortedCurrencies(sortedCurrencies: List<Item.Currency>) {
        val items = mutableListOf<Item>()
        items.addAll(0, sortedCurrencies)
        items.add(Item.AddButton("Добавить", R.drawable.plus_image))
        setItems(items)
    }

    override fun deleteItemAt(position: Int) {
        currenciesAndButton.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun deleteItem(currency: Item.Currency): Int {
        val removedIndex = currenciesAndButton.indexOf(currency)
        currenciesAndButton.remove(currency)
        notifyItemRemoved(removedIndex)
        notifyDataSetChanged()
        return removedIndex
    }

    fun sortItemsBy(sortingType: SortBy) {
        sortCurrencies(getCurrencies(), sortingType)
    }

    private fun getCurrencies(): List<Item.Currency> =
        currenciesAndButton.dropLast(1) as List<Item.Currency>

    override fun resetSorting() {
        val originalItems: List<Item> = Data.elements
        setItems(originalItems)
    }

    fun moveItem(from: Int, to: Int) {
        val fromList = currenciesAndButton[from]
        currenciesAndButton.removeAt(from)
        if (to < from) {
            currenciesAndButton.add(to, fromList)
        } else {
            currenciesAndButton.add(to - 1, fromList)
        }
    }

    fun convertCurrencies(tenge: String) {
        val list = getCurrencies()
        list.map {
            when (it.currency) {
                "USA" -> {
                    it.amount = tenge.toInt() / 450f
                }
                "CHN" -> {
                    it.amount = tenge.toInt() / 120f
                }
                "TRK" -> {
                    it.amount = tenge.toInt() / 4f
                }
                "KZ" -> {
                    it.amount = tenge.toInt() / 1f
                }
            }
        }
        setSortedCurrencies(list)
    }

}