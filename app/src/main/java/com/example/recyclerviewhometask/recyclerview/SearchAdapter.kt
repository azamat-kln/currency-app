package com.example.recyclerviewhometask.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.R
import com.example.recyclerviewhometask.model.Item

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private val data: MutableList<Item.Currency> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val customView =
            LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return MyViewHolder(customView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val element = data[position]
        holder.bind(element)
    }

    override fun getItemCount(): Int = data.size

    fun setData(myLocalData: List<Item.Currency>) {
        data.clear()
        data.addAll(myLocalData)
        notifyDataSetChanged()
    }

    fun updateList(currency: String) {
        val newFilteredList = data.filter { it.currency.startsWith(currency, ignoreCase = true) }
        if (newFilteredList.isNotEmpty()) {
            setData(newFilteredList)
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.currency_name_text_view)
        private val flagImageView: ImageView = view.findViewById(R.id.flag_image_view)

        fun bind(element: Item.Currency) {
            nameTextView.text = element.currency
            flagImageView.setImageResource(element.flagDrawableRes)
        }

    }
}