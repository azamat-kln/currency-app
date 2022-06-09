package com.example.recyclerviewhometask

import com.example.recyclerviewhometask.model.Item

object Data {

    private val currency1 = Item.Currency(23450f, "USA", R.drawable.usa_flag)
    private val currency2 = Item.Currency(245f, "CHN", R.drawable.ic_launcher_background)
    private val currency3 = Item.Currency(100f, "KZ", R.drawable.kz_flag)
    private val currency4 = Item.Currency(6450f, "TRK", R.drawable.ic_launcher_foreground)
    private val currency5 = Item.Currency(100f, "USA", R.drawable.usa_flag)
    private val currency6 = Item.Currency(911f, "KZ", R.drawable.kz_flag)
    private val currency7 = Item.Currency(51843f, "TRK", R.drawable.ic_launcher_foreground)
    private val currency8 = Item.Currency(9070f, "CHN", R.drawable.ic_launcher_background)

    val elements: List<Item> = listOf(
        currency1,
        currency2,
        currency3,
        currency4,
        currency5,
        currency6,
        currency7,
        currency8,
        Item.AddButton("Добавить", R.drawable.plus_image)
    )

}