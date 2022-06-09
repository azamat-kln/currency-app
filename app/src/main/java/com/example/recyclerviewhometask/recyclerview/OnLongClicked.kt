package com.example.recyclerviewhometask.recyclerview

import com.example.recyclerviewhometask.model.Item

interface OnLongClicked {
    fun changeToolbarState()

    fun itemToDelete(currency: Item.Currency)
}